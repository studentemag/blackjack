package test;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collection;

import org.easymock.EasyMock;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import test.util.BjOracle;
import blackjack.Apple;
import blackjack.ModalDialogShower;
import blackjack.Randomizable;

@RunWith(Parameterized.class)
public class TestWbActionPerformed {
	Apple apple;
	ModalDialogShower dialogShowerMock;
	Randomizable randeasymock;
        
	boolean out,outExp;
	int player,state_player,state_cpu;
	Integer cards[];
	int playerExp, state_cpuExp, area_score_playerExp, area_score_cpuExp;

	ActionEvent e;
	String fieldExp = "";
	String area = "";
	String areaExp = "";
	boolean dialogExp;

	
	public TestWbActionPerformed(
			boolean out, int player, Integer cards[], String command, int state_player, int state_cpu, String area, 
			int playerExp, boolean outExp, String fieldExp, int state_cpuExp, 
			int area_score_playerExp, int area_score_cpuExp, String areaExp, boolean dialogExp
	) {
		super();
		this.out = out;
		this.outExp = outExp;
		this.player = player;
		this.playerExp = playerExp;
		this.state_player = state_player;
		this.state_cpu = state_cpu;
		this.state_cpuExp = state_cpuExp;
		this.area_score_playerExp = area_score_playerExp;
		this.cards = cards;
		this.e = new ActionEvent(this, 0, command);
		this.fieldExp = fieldExp;
		this.area_score_cpuExp = area_score_cpuExp;
		this.area = area;
		this.areaExp = areaExp;
		this.dialogExp = dialogExp;
	}
        
	@Parameters
	public static Collection<Object[]> generateData() {
	// In this example, the parameter generator returns a List of
	// arrays. Each array has two elements: { datum, expected }.
	// These data are hard-coded into the class, but they could be
	// generated or loaded in any way you like.
		return Arrays.asList(new Object[][] { 
				{false,0,new Integer[] {12,1},"start",0,0,"Hello",21,true,"21 Vittoria Grande Baldoria!",0,1,0,"Player\n Q A",false}, //00
				{false,0,new Integer[] {7,11},"start",0,0,"Hello",17,false,"",0,0,0,"Player\n 7 J",false}, //01
				{false,17,new Integer[] {6},"hit",0,0,"Hello",23,true,"Busted!",1,0,1,"Hello 6",false}, //02
				{false,17,new Integer[] {4},"hit",0,0,"Hello",21,false,"BlackJack!",0,0,0,"Hello 4",false}, //03
				{false,17,new Integer[] {2},"hit",0,0,"Hello",19,false,"",0,0,0,"Hello 2",false}, //04
				{true,22,new Integer[] {4},"hit",0,0,"Hello",22,true,"",0,0,0,"Hello",true}, //05
				{false,17,new Integer[] {8,5,7},"stand",0,0,"Hello",17,true,"Lose!",1,0,1,"Hello\n\nCpu\n 8 5 7",false}, //06
				{true,17,new Integer[] {4},"stand",0,0,"Hello",17,true,"",0,0,0,"Hello",true}, //07
		});
	}

	@Before
	public void setUp() {
		randeasymock = EasyMock.createMock(Randomizable.class);
		dialogShowerMock = EasyMock.createMock(ModalDialogShower.class);
		apple = new Apple(randeasymock, dialogShowerMock);
	}
        
	@Test
	public void testActionPerformed() {
		for (int i = 0; i < cards.length; i++)
            EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();
		EasyMock.replay(randeasymock);
		
		if (dialogExp) {
			dialogShowerMock.showErrorMessage();
            EasyMock.replay(dialogShowerMock);
		}
		
		apple.setOut(out);
		apple.setPlayer(player);
		apple.setState_player(state_player);
		apple.setState_cpu(state_cpu);
		apple.setAreaText(area);
		
		apple.getAscoltatore().actionPerformed(e);
		
		assertEquals(playerExp, apple.getPlayer());
		assertEquals(outExp, apple.isOut());
		assertEquals(fieldExp, apple.getFieldText());
		assertEquals(state_cpuExp, apple.getState_cpu());
		assertEquals(area_score_playerExp, BjOracle.playerGlobalScore(apple.getArea_scoreText()));
		assertEquals(area_score_cpuExp, BjOracle.cpuGlobalScore(apple.getArea_scoreText()));
		assertEquals(areaExp, apple.getAreaText());
	}
}