package test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.uispec4j.Panel;

import blackjack.Apple;


public class TestBbActionPerformed {
	Apple apple;
	Panel appleWrapper;
	
	@Before
	public void setUp(){
		apple = new Apple(null);
		appleWrapper = new Panel(apple);
		appleWrapper.getButton("hit");
	}
	
	@Test
	public void testActionPerformed1(){
		apple.setFieldText("hello");
		Assert.assertEquals("hello", appleWrapper.getTextBox("field").getText());
	}
	
	@Test
	public void testActionPerformed2(){
		
	}

}
