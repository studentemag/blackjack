/**
 * 
 */
package test;

import java.awt.SystemColor;

import blackjack.Apple;

import test.util.BjOracle;
import junit.framework.TestCase;


/**
 * @author Mario
 *
 */
public class testCpu extends TestCase {
	
	/**
	 * @throws java.lang.Exception
	 */
	protected static void setUpBeforeClass() throws Exception {
		BjOracle.setcardIndex(0);
	}

	/**
	 * @throws java.lang.Exception
	 */
	protected static void tearDownAfterClass() throws Exception {
		BjOracle.setcardIndex(0);
	}

	/* (non Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		BjOracle.setcardIndex(0);
	}

	/* (non Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		BjOracle.setcardIndex(0);
	}

	/**
	 * Metodo di verifica per {@link test.BlackJackOracleUtil#getCard()}.
	 */
	public final void testGetCard() {
		int cards[] = {1,2,3,4,5,6,7,8,9,10,11,12};
		BjOracle.setCards(cards);
		
		for (int i = 0; i < cards.length; i++) {
			assertEquals(i + 1, (int) (BjOracle.getCard() * 12) + 1);
		}
	}

	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 */
	public final void testCpu01() {
		int cards[] = {2,2};
		BjOracle.setCards(cards);
		
		Apple a = new Apple();
		a.cpu_TESTABLE(4, 4, 4);
		
		//assertEquals("Punteggio CPU non corretto!",5,4);
		
		for (int i = 0; i < cards.length; i++) {
			System.out.println((int) (BjOracle.getCard() * 12) + 1);
		}
	}
}
