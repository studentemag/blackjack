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
import org.uispec4j.Panel;

import test.util.BjOracle;
import blackjack.Apple;
import blackjack.Randomizable;

@RunWith(Parameterized.class)
public class TestBbActionPerformed {
	Apple apple;
	Panel appleWrapper;
	Randomizable randeasymock;
	
	boolean out,outExp;
	int player,state_player,state_cpu;
	Integer cards[];
	int playerExp, state_cpuExp,area_score_playerExp, area_score_cpuExp;

	ActionEvent e;
	String fieldExp="";
	
	public TestBbActionPerformed(
		boolean out, int player, Integer cards[], String command, int state_player, int state_cpu,
		int playerExp, boolean outExp, String fieldExp, int state_cpuExp, 
		int area_score_playerExp, int area_score_cpuExp
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
	}
	
	@Parameters
	public static Collection<Object[]> generateData() {
		// In this example, the parameter generator returns a List of
		// arrays. Each array has two elements: { datum, expected }.
		// These data are hard-coded into the class, but they could be
		// generated or loaded in any way you like.
		return Arrays.asList(new Object[][] { 
				{false,12,new Integer[] {8,1},"hit",0,0,20,false,"",0,0,0}
		});
		
		//{false,12,new Object[] {8},"hit",0,0,20,false,"",0,0,0}
	}

	@Before
	public void setUp(){
		randeasymock = EasyMock.createMock(Randomizable.class);
		apple = new Apple(randeasymock);
		appleWrapper = new Panel(apple);
	}
	
	@Test
	public void testActionPerformed(){
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
	
}
