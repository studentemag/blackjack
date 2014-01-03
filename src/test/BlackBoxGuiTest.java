package test;

import org.uispec4j.Button;
import org.uispec4j.TextBox;
import org.uispec4j.Trigger;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.MainClassAdapter;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;

import test.util.BjOracle;
import blackjack.Black;

public class BlackBoxGuiTest extends UISpecTestCase {
	Window window, dialog;
	TextBox area, area_score, field;
	Button startButton, hitButton, standButton;
	
	private enum State {
		INIT, ERROR, PLAY, UNDEFINED
	};
	
	
	protected void setUp() throws Exception {
		//	We then tell this class that it needs to run the blackjack application 
		//	using the main() found in the Black class, 
		//	and that it can run this application with no arguments.
		setAdapter(new MainClassAdapter(Black.class, new String[0]));
	    
	    //	Retrieve the components
	    window = getMainWindow();
	    startButton = window.getButton("start");
	    hitButton = window.getButton("hit");
	    standButton = window.getButton("stand");
	    
	    area = window.getTextBox("area");
	    area_score = window.getTextBox("area_score");
	    field = window.getTextBox("field");
	    
	}
	
	/*
	 * Riconosce lo stato corrente analizzando gli elementi dell'interfaccia grafica
	 */
	private State currentState(){
		State state;
		
		if (dialog != null && 
			dialog.isVisible().isTrue() && 
			dialog.containsLabel("push start!").isTrue()
		)
			state = State.ERROR;
		
		else if (!area.getText().isEmpty() &&
			(field.getText().equalsIgnoreCase("BlackJack!") || field.getText().isEmpty())
		)
			state = State.PLAY;
		
		else if (area.getText().isEmpty() ||
				 field.getText().equalsIgnoreCase("Win!")    ||
				 field.getText().equalsIgnoreCase("Lose!")   ||
				 field.getText().equalsIgnoreCase("Busted!")  ||
				 field.getText().equalsIgnoreCase("21 Vittoria Grande Baldoria!")		 
		)
			state = State.INIT;
		else
			state = State.UNDEFINED;
		
		return state;
	}
	
	/*
	 * Transition 1
	 */
	
	private void moveToPlay(){
		boolean traversed = false;
		do {
			startButton.click();
			if ( BjOracle.blackJack(area.getText()) )
				startButton.click();
			else
				traversed=true;
			
		} while ( !traversed );
	}
	
	public void testTransition1(){	
		assertEquals(State.INIT,currentState());
		
		moveToPlay();
		
		assertFalse(area.getText().isEmpty());
		assertEquals(State.PLAY,currentState());
		assertTrue(field.getText().isEmpty());
	}
	
	/*
	 * Transition 2
	 */
	public void testTransition2(){
		assertEquals(State.INIT,currentState());
		int cpu_global_score=0;
		boolean traversed = false;
		
		do {
			cpu_global_score=BjOracle.cpuGlobalScore(area_score.getText());
			startButton.click();
			if ( !BjOracle.blackJack(area.getText()) )
				startButton.click();
			else
				traversed=true;
			
		} while ( !traversed );
		
		assertEquals(State.INIT,currentState());
		assertEquals(BjOracle.bjWinString,field.getText());
		assertEquals(cpu_global_score+1,BjOracle.cpuGlobalScore(area_score.getText()));
	}
	
	
	/*
	 * Transitions 3 and 5
	 */
	public void testTransitions3and5(){
		//Check that current state is start
		assertEquals(State.INIT,currentState());
		
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
		assertEquals(State.INIT,currentState());
		
	}
	
	/*
	 * Transitions 4 and 5
	 */
	public void testTransitions4and5(){
		assertEquals(State.INIT,currentState());
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
		assertEquals(State.INIT,currentState());
	}
	
	/*
	 * Transition 6
	 */
	public void testTransition6(){
		moveToPlay();
		assertEquals(State.PLAY,currentState());		
		do {
			startButton.click();
		}
		while (BjOracle.blackJack(area.getText()));
		
		assertEquals(State.PLAY,currentState());

		assertFalse(area.getText().isEmpty());
		assertTrue(field.getText().isEmpty());
	}
	
	/*
	 * Transition 7
	 */
	public void testTransition7(){
		moveToPlay();
		assertEquals(State.PLAY,currentState());
		
		int player_global_score=BjOracle.playerGlobalScore(area_score.getText());
		do {
			startButton.click();
		}
		while (!BjOracle.blackJack(area.getText()));
		assertEquals(State.INIT,currentState());
		
		assertFalse(area.getText().isEmpty());
		assertEquals(BjOracle.bjWinString,field.getText());
		assertEquals(player_global_score+1,BjOracle.cpuGlobalScore(area_score.getText()));
	}
	
	/*
	 * Transition 8
	 */
	public void testTransition8(){
		int player_score;
		
		do {			
			moveToPlay();
			assertEquals(State.PLAY,currentState());
			
			player_score=BjOracle.playerScore(area.getText());
			hitButton.click();
		}
		while (BjOracle.playerBusted(area.getText()) || BjOracle.blackJack(area.getText()));
		
		assertEquals(State.PLAY,currentState());
		assertTrue(player_score<BjOracle.playerScore(area.getText()));
	}
	
	/*
	 * Transition 9
	 */
	public void testTransition9(){
		int cpu_global_score;
		do {
			moveToPlay();
			assertEquals(State.PLAY,currentState());
			
			cpu_global_score=BjOracle.cpuGlobalScore(area_score.getText());
			hitButton.click();
		}
		while (!BjOracle.playerBusted(area.getText()) || BjOracle.blackJack(area.getText()));
		assertEquals(State.INIT,currentState());
		assertEquals(BjOracle.bustedString,field.getText());
		assertEquals(cpu_global_score+1,BjOracle.cpuGlobalScore(area_score.getText()));
	}
	
	/*
	 * Transition 10
	 */
	public void testTransition10(){
		int player_score;
		do {
			moveToPlay();
			assertEquals(State.PLAY,currentState());
			
			player_score=BjOracle.playerScore(area.getText());
			hitButton.click();
		}
		while (BjOracle.playerBusted(area.getText()) || !BjOracle.blackJack(area.getText()));
		assertEquals(State.PLAY,currentState());
		assertEquals(BjOracle.bjString,field.getText());
		assertTrue(player_score<BjOracle.playerScore(area.getText()));
	}
	
	/*
	 * Transition 11
	 */
	public void testTransition11(){
		int player_global_score;
		int cpu_global_score;
		do {
			moveToPlay();
			assertEquals(State.PLAY,currentState());
			
			player_global_score=BjOracle.playerGlobalScore(area_score.getText());
			cpu_global_score=BjOracle.cpuGlobalScore(area_score.getText());
			standButton.click();
		}
		while ( BjOracle.cpuWins(area.getText()) );
		assertEquals(State.INIT,currentState());
		assertEquals(BjOracle.winString,field.getText());
		assertEquals(player_global_score+1, 
				BjOracle.playerGlobalScore(area_score.getText()));
		assertEquals(cpu_global_score,
				BjOracle.cpuGlobalScore(area_score.getText()));
	}
	
	/*
	 * Transition 12
	 */
	public void testTransition12(){
		int player_global_score;
		int cpu_global_score;
		do {
			moveToPlay();
			assertEquals(State.PLAY,currentState());
			
			player_global_score=BjOracle.playerGlobalScore(area_score.getText());
			cpu_global_score=BjOracle.cpuGlobalScore(area_score.getText());
			standButton.click();
		}
		while ( !BjOracle.cpuWins(area.getText()) );
		assertEquals(State.INIT,currentState());
		assertEquals(BjOracle.loseString,field.getText());
		assertEquals(player_global_score, 
				BjOracle.playerGlobalScore(area_score.getText()));
		assertEquals(cpu_global_score+1,
				BjOracle.cpuGlobalScore(area_score.getText()));
	}
}
