/**
 * 
 */
package test;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import test.mocks.RandomizerMock;
import test.util.BjOracle;
import blackjack.Apple;
import blackjack.Randomizable;


/**
 * @author Mario
 *
 */
public class TestCpu extends TestCase {
	
	Apple a, a2;
	Randomizable randmock, randeasymock;
	
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
		
		randmock = new RandomizerMock();
		a = new Apple(randmock);
		
		// Istanzio un mock per l'interfaccia Randomizable
		randeasymock = EasyMock.createMock(Randomizable.class);
		a2 = new Apple(randeasymock);
		
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
		BjOracle.setCards(cards);

		for (int i = 0; i < cards.length; i++) {
			assertEquals(i + 1, (int) (BjOracle.getCard() * 12) + 1);
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
		BjOracle.setCards(cards);
		
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
	
	public final void testCpu01easymock(){
		int cards[]={2,2};
		BjOracle.setCards(cards);
		for (int i=0;i<cards.length;i++)
			EasyMock.expect(randeasymock.getRandom()).andReturn(BjOracle.getCard()).once();
		EasyMock.replay(randeasymock);
		
		a2.setPlayer(4);
		a2.setState_cpu(5);
		a2.setState_player(5);
		a2.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", 5, a2.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", 6, a2.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", "Lose!", a2.getField());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		String s = new String("Player: " + 5 + "\n\nCpu: " + 6);
		assertEquals("Campo area_score non corretto!", s, a2.getArea_score());
	}
}
