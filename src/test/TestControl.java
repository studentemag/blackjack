package test;

import junit.framework.TestCase;
import test.util.BjOracle;
import blackjack.Apple;
import blackjack.Randomizable;
import blackjack.Randomizer;

public class TestControl extends TestCase {
	Apple unitUnderTest;
	
	protected void setUp() throws Exception {
		super.setUp();
		Randomizable rand=new Randomizer();
		unitUnderTest = new Apple(rand);
	}
	
	public int runTestWithParameters(int card, String areaText){
		unitUnderTest.getArea().setText(areaText);
		int result = unitUnderTest.control(card);
		return result;
	}
	
	public void testControl1() throws Exception {
		try {
			int card = 5;
			String areaText = "hello";
			int result = runTestWithParameters(card, areaText);
			assertEquals(BjOracle.fromCardToScore(card), result);
			assertEquals(areaText + " " + BjOracle.fromCartToSymbol(card),
					unitUnderTest.getArea().getText());
		} catch (Exception e) {
			throw e;
		}

	}
	
	public void testControl2() throws Exception {
		try {
			int card = 1;
			String areaText = "hello";
			int result = runTestWithParameters(card, areaText);
			assertEquals(BjOracle.fromCardToScore(card), result);
			assertEquals(areaText + " " + BjOracle.fromCartToSymbol(card),
					unitUnderTest.getArea().getText());
		} catch (Exception e) {
			throw e;
		}

	}
	
	public void testControl3() throws Exception {
		try {
			int card = 11;
			String areaText = "hello";
			int result = runTestWithParameters(card, areaText);
			assertEquals(BjOracle.fromCardToScore(card), result);
			assertEquals(areaText + " " + BjOracle.fromCartToSymbol(card),
					unitUnderTest.getArea().getText());
		} catch (Exception e) {
			throw e;
		}

	}
	
	public void testControl4() throws Exception {
		try {
			int card = 12;
			String areaText = "hello";
			int result = runTestWithParameters(card, areaText);
			assertEquals(BjOracle.fromCardToScore(card), result);
			assertEquals(areaText + " " + BjOracle.fromCartToSymbol(card),
					unitUnderTest.getArea().getText());
		} catch (Exception e) {
			throw e;
		}

	}

	public void testControl5() throws Exception {
		try {
			int card = 13;
			String areaText = "hello";
			int result = runTestWithParameters(card, areaText);
			assertEquals(BjOracle.fromCardToScore(card), result);
			assertEquals(areaText + " " + BjOracle.fromCartToSymbol(card),
					unitUnderTest.getArea().getText());
		} catch (Exception e) {
			throw e;
		}

	}

	public void testControl6() throws Exception {
		try {
			int card = 10;
			String areaText = "hello";
			int result = runTestWithParameters(card, areaText);
			assertEquals(BjOracle.fromCardToScore(card), result);
			assertEquals(areaText + " " + BjOracle.fromCartToSymbol(card),
					unitUnderTest.getArea().getText());
		} catch (Exception e) {
			throw e;
		}

	}

	public void testControl7() throws Exception {
		try {
			int card = 2;
			String areaText = "hello";
			int result = runTestWithParameters(card, areaText);
			assertEquals(BjOracle.fromCardToScore(card), result);
			assertEquals(areaText + " " + BjOracle.fromCartToSymbol(card),
					unitUnderTest.getArea().getText());
		} catch (Exception e) {
			throw e;
		}

	}
	
	public void testControl8() throws Exception {
		try {
			int card = 0;
			String areaText = "hello";
			runTestWithParameters(card, areaText);
		} catch (Exception e) {
			throw e;
		}

	}
	
	public void testControl9() throws Exception {
		try {
			int card = 14;
			String areaText = "hello";
			runTestWithParameters(card, areaText);
		} catch (Exception e) {
			throw e;
		}

	}
		
}
