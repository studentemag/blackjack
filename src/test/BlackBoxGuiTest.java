package test;

import org.uispec4j.Button;
import org.uispec4j.TextBox;
import org.uispec4j.Trigger;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.MainClassAdapter;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;

import test.util.BlackJackOracleUtil;
import blackjack.black;

public class BlackBoxGuiTest extends UISpecTestCase {
	Window window, dialog;
	TextBox area, area_score, field;
	Button startButton, hitButton, standButton;
	
	private enum State {
		START, ERROR, PLAY, UNDEFINED
	};
	
	protected void setUp() throws Exception {
		//	We then tell this class that it needs to run the blackjack application 
		//	using the main() found in the Black class, 
		//	and that it can run this application with no arguments.
		setAdapter(new MainClassAdapter(black.class, new String[0]));
	    
	    //	Retrieve the components
	    window = getMainWindow();
	    startButton = window.getButton("start");
	    hitButton = window.getButton("hit");
	    standButton = window.getButton("stand");
	    
	    area = window.getTextBox("area");
	    area_score = window.getTextBox("area_score");
	    field = window.getTextBox("field");
	    
	}
	
	private State currentState(){
		State state;
		
		if (dialog != null && 
			dialog.isVisible().isTrue() && 
			dialog.containsLabel("Push start!").isTrue()
		)
			state = State.ERROR;
		
		else if (!area.getText().isEmpty() &&
			(field.getText().equalsIgnoreCase("Blackjack") || field.getText().isEmpty())
		)
			state = State.PLAY;
		
		else if (area.getText().isEmpty() ||
				 field.getText().equalsIgnoreCase("Win!")    ||
				 field.getText().equalsIgnoreCase("Lose!")   ||
				 field.getText().equalsIgnoreCase("Busted!")
				 
		)
			state = State.START;
		else
			state = State.UNDEFINED;
		
		return state;
	}
	
	
	public void testErrorDialog1(){
		//Check that current state is start
		assertEquals(State.START,currentState());
		
		//	Modal dialogs are more difficult to handle than their non-modal counterparts, 
		//	because the application code is blocked while the dialog is being shown, 
		//	waiting for a result.
		//	To manage this, the WindowInterceptor class provides a means for registering 
		//	a set of handlers that will be executed within the context of the dialog:
		
		WindowInterceptor
			//	The interception is started with a Trigger. 
			//	As for non-modal windows, it is responsible for triggering the display of 
			//	the dialog.
			.init(hitButton.triggerClick())
			
			//	The WindowHandler provided to the WindowInterceptor.process() method is
			//	given the shown window in its process() call. The body of this method is
			//	responsible for dealing with that window, and then returning a trigger 
			//	that will close it. In our example we return a trigger that will click 
			//	on the "OK" button displayed in the dialog.
			.process( new WindowHandler() {
				public Trigger process(Window arg0) {
					// ... perform some operations on the shown window ...
					dialog=arg0;
					assertEquals(State.ERROR,currentState());
				    // return a trigger that will close it
				    return arg0.getButton("OK").triggerClick();
				}
			}
			)
			// The interception is actually started only when the run() method is called.
			.run();
		
		// Check that the dialog is now hidden and that current state is start
		assertFalse(dialog.isVisible().isTrue());
		assertEquals(State.START,currentState());
		
	}
	
	public void testErrorDialog2(){
		assertEquals(State.START,currentState());
		WindowInterceptor
			.init(standButton.triggerClick())
			.process( new WindowHandler() {
				public Trigger process(Window arg0) {
					dialog=arg0;
					assertEquals(State.ERROR,currentState());
				    return arg0.getButton("OK").triggerClick();
				}
			}
			)
			.run();
		assertFalse(dialog.isVisible().isTrue());
		assertEquals(State.START,currentState());
	}
	
	public void testStart1(){
		assertEquals(State.START,currentState());
		do {
			startButton.click();
			assertFalse(area.getText().isEmpty());
			assertEquals(State.PLAY,currentState());
			if (!BlackJackOracleUtil.blackJack(area.getText()))
				assertTrue(field.getText().isEmpty());
		}
		while (BlackJackOracleUtil.blackJack(area.getText()));
	}
	
	public void testStart2(){
		assertEquals(State.START,currentState());
		do {
			startButton.click();
			assertFalse(area.getText().isEmpty());
			assertEquals(State.PLAY,currentState());
			if (BlackJackOracleUtil.blackJack(area.getText()))
				assertEquals("Blackjack",field.getText());
		}
		while (!BlackJackOracleUtil.blackJack(area.getText()));
	}
	
	public void testStart3(){
		assertEquals(State.START,currentState());
		
		do {
			startButton.click();
			assertEquals(State.PLAY,currentState());
			assertFalse(area.getText().isEmpty());
		
			startButton.click();
			assertEquals(State.PLAY,currentState());
			assertFalse(area.getText().isEmpty());
			if (!BlackJackOracleUtil.blackJack(area.getText()))
				assertTrue(field.getText().isEmpty());
		}
		while (BlackJackOracleUtil.blackJack(area.getText()));
	}
	
	public void testStart4(){
		assertEquals(State.START,currentState());
		
		do {
			startButton.click();
			assertEquals(State.PLAY,currentState());
			assertFalse(area.getText().isEmpty());
		
			startButton.click();
			assertEquals(State.PLAY,currentState());
			assertFalse(area.getText().isEmpty());
			if (BlackJackOracleUtil.blackJack(area.getText()))
				assertEquals("Blackjack",field.getText());
		}
		while (!BlackJackOracleUtil.blackJack(area.getText()));
		
	}
	
	public void testHit1(){
		assertEquals(State.START,currentState());	
		do{
			startButton.click();
			assertEquals(State.PLAY,currentState());
			
			hitButton.click();
			if (!BlackJackOracleUtil.playerBusted(area.getText()))
				assertEquals(State.PLAY,currentState());
		}
		while (BlackJackOracleUtil.playerBusted(area.getText()));
	}
	
	public void testHit2(){
		assertEquals(State.START,currentState());
		do{
			startButton.click();
			assertEquals(State.PLAY,currentState());
			
			hitButton.click();
			if (BlackJackOracleUtil.playerBusted(area.getText()))
				assertEquals(State.START,currentState());
		}
		while (!BlackJackOracleUtil.playerBusted(area.getText()));
		assertEquals("Busted!",field.getText());
	}
	
	public void testStand1(){
		assertEquals(State.START,currentState());
		do{
			startButton.click();
			assertEquals(State.PLAY,currentState());

			standButton.click();
			assertEquals(State.START,currentState());
		}
		while (BlackJackOracleUtil.cpuWon(area.getText()));
		assertEquals("Win!",field.getText());
	}
	
	public void testStand2(){
		assertEquals(State.START,currentState());
		do{
			startButton.click();
			assertEquals(State.PLAY,currentState());

			standButton.click();
			assertEquals(State.START,currentState());
		}
		while (!BlackJackOracleUtil.cpuWon(area.getText()));
		assertEquals("Lose!",field.getText());
	}
	
}
