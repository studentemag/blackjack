package test;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collection;

import org.easymock.EasyMock;
import org.junit.Assert;
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
public class TestBbActionPerformed {
	Apple apple;
	ModalDialogShower dialogShowerMock;
	Randomizable randeasymock;
	
	// Input variables
	boolean out;
	int player, state_player, state_cpu;
	Integer cards[];
	ActionEvent e;
	String area;
	
	// Expected output variables
	boolean outExp;
	int playerExp, state_cpuExp,area_score_playerExp, area_score_cpuExp;
	String fieldExp, areaExp;
	

	
	// Utility variables
	boolean valid;
	
	public TestBbActionPerformed(
		boolean out, int player, Integer cards[], String command, 
		int state_player, int state_cpu, String area,
		int playerExp, boolean outExp, String fieldExp, int state_cpuExp, 
		int area_score_playerExp, int area_score_cpuExp, String areaExp, boolean valid
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
		this.e = new ActionEvent(this,0,command);
		this.fieldExp = fieldExp;
		this.area_score_cpuExp=area_score_cpuExp;
		this.area=area;
		this.areaExp=areaExp;
		this.valid=valid;
	}
	
	@Parameters
	public static Collection<Object[]> generateData() {
		// In this example, the parameter generator returns a List of
		// arrays. Each array has two elements: { datum, expected }.
		// These data are hard-coded into the class, but they could be
		// generated or loaded in any way you like.
		return Arrays.asList(new Object[][] { 
				{false,7,new Integer[] {8},"hit",0,0,"hello",15,false,"",0,0,0,"hello 8",true}, //0
				{true,7,new Integer[] {8},"hit",0,0,"hello",0,false,null,0,0,0,null,true}, //1
				{false,24,new Integer[]{8},"hit",0,0,"hello",0,false,null,0,0,0,null,false}, //2
				{false,-1,new Integer[]{8},"hit",0,0,"hello",0,false,null,0,0,0,null,false}, //3
				{false,7,new Integer[]{1},"hit",0,0,"hello",18,false,"",0,0,0,"hello A",true}, //4
				{false,7,new Integer[]{2},"hit",0,0,"hello",9,false,"",0,0,0,"hello 2",true}, //5
				{false,7,new Integer[]{3},"hit",0,0,"hello",10,false,"",0,0,0,"hello 3",true}, //6
				{false,7,new Integer[]{4},"hit",0,0,"hello",11,false,"",0,0,0,"hello 4",true}, //7
				{false,7,new Integer[]{5},"hit",0,0,"hello",12,false,"",0,0,0,"hello 5",true}, //8
				{false,7,new Integer[]{6},"hit",0,0,"hello",13,false,"",0,0,0,"hello 6",true}, //9
				{false,7,new Integer[]{7},"hit",0,0,"hello",14,false,"",0,0,0,"hello 7",true}, //10
				{false,7,new Integer[]{9},"hit",0,0,"hello",16,false,"",0,0,0,"hello 9",true}, //11
				{false,7,new Integer[]{10},"hit",0,0,"hello",17,false,"",0,0,0,"hello 10",true}, //12
				{false,7,new Integer[]{11},"hit",0,0,"hello",17,false,"",0,0,0,"hello J",true}, //13
				{false,7,new Integer[]{12},"hit",0,0,"hello",17,false,"",0,0,0,"hello Q",true}, //14
				{false,7,new Integer[]{13},"hit",0,0,"hello",17,false,"",0,0,0,"hello K",true}, //15
				{false,7,new Integer[]{-1},"hit",0,0,"hello",0,false,null,0,0,0,null,false}, //16
				{false,7,new Integer[]{15},"hit",0,0,"hello",0,false,null,0,0,0,null,false}, //17
				{false,7,new Integer[] {8,8},"stand",0,0,"hello",7,true,"Lose!",1,0,1,"hello\n\nCpu\n 8 8",true}, //18
				{false,7,new Integer[] {8,8},"start",0,0,"hello",16,false,"",0,0,0,"Player\n 8 8",true}, //19
				{false,7,new Integer[]{8},"hello",0,0,"hello",0,false,null,0,0,0,null,false}, //20
				{false,7,new Integer[]{8},"hit",-1,0,"hello",0,false,null,0,0,0,null,false}, //21
				{false,7,new Integer[]{8},"hit",0,-1,"hello",0,false,null,0,0,0,null,false} //22
		});
	}

	@Before
	public void setUp(){
		randeasymock = EasyMock.createMock(Randomizable.class);
		dialogShowerMock = EasyMock.createMock(ModalDialogShower.class);
		apple = new Apple(randeasymock,dialogShowerMock);
	}
	
	@Test
	public void testActionPerformed(){
		if (valid && fieldExp==null)
			testDialog();
		else if (valid)
			testValid();
		else
			testInvalid();
	}
	
	public void testValid(){
		for (int i = 0; i < cards.length; i++)
			EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();	
		EasyMock.replay(randeasymock);
		
		apple.setOut(out);
		apple.setPlayer(player);
		apple.setState_player(state_player);
		apple.setState_cpu(state_cpu);
		apple.setAreaText(area);
		
		apple.getAscoltatore().actionPerformed(e);
		
		Assert.assertEquals(playerExp, apple.getPlayer());
		Assert.assertEquals(outExp, apple.isOut());
		Assert.assertEquals(fieldExp, apple.getFieldText() );
		Assert.assertEquals(state_cpuExp, apple.getState_cpu());
		Assert.assertEquals(area_score_playerExp, BjOracle.playerGlobalScore(apple.getArea_scoreText()));
		Assert.assertEquals(area_score_cpuExp, BjOracle.cpuGlobalScore(apple.getArea_scoreText()));
		Assert.assertEquals(areaExp,apple.getAreaText());
	}
	
	public void testInvalid(){
		EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[0])).anyTimes();
		EasyMock.replay(randeasymock);
		
		apple.setOut(out);
		apple.setPlayer(player);
		apple.setState_player(state_player);
		apple.setState_cpu(state_cpu);
		apple.setAreaText(area);
		
		apple.getAscoltatore().actionPerformed(e);
		
	}
	
	public void testDialog(){
		dialogShowerMock.showErrorMessage();
		EasyMock.replay(randeasymock);
		
		apple.setOut(out);
		apple.setPlayer(player);
		apple.setState_player(state_player);
		apple.setState_cpu(state_cpu);
		apple.setAreaText(area);
		
		apple.getAscoltatore().actionPerformed(e);	
	}
	
}
