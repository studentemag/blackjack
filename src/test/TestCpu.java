/**
 * 
 */
package test;

import junit.framework.TestCase;
import test.mocks.RandomizerMock;
import test.util.BjOracle;
import blackjack.Apple;
import blackjack.Randomizable;
import blackjack.RandomizerStub;


/**
 * @author Mario
 *
 */
public class TestCpu extends TestCase {
	
	Apple a;
	RandomizerStub rand;
	

	/* (non Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		rand = new RandomizerStub();
		rand.setcardIndex(0);
		a = new Apple(rand);
	}

	/* (non Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Metodo di verifica per {@link test.BlackJackOracleUtil#getCard()}.
	 */
	public final void testGetCard() {
		int cards[] = {1,2,3,4,5,6,7,8,9,10,11,12};
		rand.setCards(cards);

		for (int i = 0; i < cards.length; i++) {
			assertEquals(i + 1, (int) (rand.getCard() * 12) + 1);
		}
	}

	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 4 CPU= 4
	 * 
	 * Output attesi
	 * 		In caso di parità vince la CPU
	 * 		-Punteggio globale: Player= 5 CPU= 6
	 * 		-Aree di testo: field= "Lose!" area_score= "Player: 5\n\nCpu: 6"
	 */
	public final void testCpu01() {
		int cards[] = {2,2};
		rand.setCards(cards);
		
		// La dichiarazione di a si trova in cima alla classe, la sua definizione nel metodo setUp, 
		// in quanto eseguita prima di ogni caso di test
		a.setPlayer(4);
		a.setState_cpu(5);
		a.setState_player(5);
		a.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", 5, a.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", 6, a.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", "Lose!", a.getField());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		String s = new String("Player: " + 5 + "\n\nCpu: " + 6);
		assertEquals("Campo area_score non corretto!", s, a.getArea_score());
	}
}
