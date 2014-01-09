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
	
	boolean out,outExp, valid;
	int player,state_player,state_cpu;
	Integer cards[];
	int playerExp, state_cpuExp,area_score_playerExp, area_score_cpuExp;

	ActionEvent e;
	String fieldExp="";
	
	public TestBbActionPerformed(
		boolean out, int player, Integer cards[], String command, int state_player, int state_cpu,
		int playerExp, boolean outExp, String fieldExp, int state_cpuExp, 
		int area_score_playerExp, int area_score_cpuExp, boolean valid
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
		this.valid=valid;
	}
	
	@Parameters
	public static Collection<Object[]> generateData() {
		// In this example, the parameter generator returns a List of
		// arrays. Each array has two elements: { datum, expected }.
		// These data are hard-coded into the class, but they could be
		// generated or loaded in any way you like.
		return Arrays.asList(new Object[][] { 
				{false,12,new Integer[] {8},"hit",0,0,20,false,"",0,0,0,true}, //0
				{true,12,new Integer[] {8}, "hit",0,0,0,false,null,0,0,0,true}, //1
				{false,24,new Integer[] {8}, "hit",0,0,0,false,null,0,0,0,false}, //2
				{false,-1, new Integer[] {8},"hit",0,0,0,false,null,0,0,0,false}, //3
				{false,12, new Integer[] {-1},"hit",0,0,0,false,null,0,0,0,false}, //4
				{false,12, new Integer[] {15},"hit",0,0,0,false,null,0,0,0,false}, //5
				{false,12,new Integer[] {8,8},"stand",0,0,12,true,BjOracle.loseString,1,0,1,true}, //6
				{false,12,new Integer[] {8,8},"start",0,0,16,false,"",0,0,0,true}, //7
				{false,12,new Integer[] {8},"hello",0,0,0,false,null,0,0,0,false}, //8
				{false,12,new Integer[] {8},"hit",-1,0,0,false,null,0,0,0,false}, //9
				{false,12,new Integer[] {8},"hit",0,-1,0,false,null,0,0,0,false} //10
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
		
		apple.getAscoltatore().actionPerformed(e);
		
		Assert.assertEquals(playerExp, apple.getPlayer());
		Assert.assertEquals(outExp, apple.isOut());
		Assert.assertEquals(fieldExp, apple.getFieldText() );
		Assert.assertEquals(state_cpuExp, apple.getState_cpu());
		Assert.assertEquals(area_score_playerExp, BjOracle.playerGlobalScore(apple.getArea_scoreText()));
		Assert.assertEquals(area_score_cpuExp, BjOracle.cpuGlobalScore(apple.getArea_scoreText()));	
	}
	
	public void testInvalid(){
		EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[0])).anyTimes();
		EasyMock.replay(randeasymock);
		
		apple.setOut(out);
		apple.setPlayer(player);
		apple.setState_player(state_player);
		apple.setState_cpu(state_cpu);
	
		apple.getAscoltatore().actionPerformed(e);
		
	}
	
	public void testDialog(){
		dialogShowerMock.showErrorMessage();
		EasyMock.replay(randeasymock);
		
		apple.setOut(out);
		apple.setPlayer(player);
		apple.setState_player(state_player);
		apple.setState_cpu(state_cpu);
		
		apple.getAscoltatore().actionPerformed(e);	
	}
	
}
