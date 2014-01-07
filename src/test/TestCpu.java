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
	

	RandomizerMock rand;
	Apple a, a2;
	Randomizable randeasymock;

	

	/* (non Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		rand = new RandomizerMock();
		rand.setcardIndex(0);
		a = new Apple(rand);
		
		// Istanzio un mock per l'interfaccia Randomizable
		randeasymock = EasyMock.createMock(Randomizable.class);
		// Lo passo al costruttore di Apple
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
	/*public final void testGetCard() {
		int cards[] = {1,2,3,4,5,6,7,8,9,10,11,12,13};
		rand.setCards(cards);

		for (int i = 0; i < cards.length; i++) {
			assertEquals(i + 1, (int) (rand.getCard() * 12) + 1);
		}
	}*/

	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 4 CPU= 4
	 * 
	 * Output attesi
	 * 		In caso di parita'  vince la CPU
	 * 		-Punteggio globale: Player= 5 CPU= 6
	 * 		-Aree di testo: field= "Lose!" area_score= "Player: 5\n\nCpu: 6"
	 */
	/*public final void testCpu01() {
		int cards[] = {2,2};
		rand.setCards(cards);
		
		a.setPlayer(4);
		a.setState_cpu(5);
		a.setState_player(5);
		a.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", 5, a.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", 6, a.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", "Lose!", a.getFieldText());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		String s = new String("Player: " + 5 + "\n\nCpu: " + 6);
		assertEquals("Campo area_score non corretto!", s, a.getArea_scoreText());
	}*/
	
	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 4 CPU= 4
	 * 
	 * Output attesi
	 * 		In caso di parita'  vince la CPU
	 * 		-Punteggio globale: Player= 5 CPU= 6
	 * 		-Aree di testo: field= "Lose!" area_score= "Player: 5\n\nCpu: 6"
	 */
	public final void testCpu01easymock() {
		//Precondizioni sull'oggetto di tipo Apple
		int playerIn = 4;
		int cards[] = {2, 2}; //Punteggio CPU pari a 4
		int state_playerIn = 5;
		int state_cpuIn = 5;
		
		//Postcondizioni sull'oggetto di tipo Apple
		int state_playerExp = 5;
		int state_cpuExp = 6;
		String fieldExp = new String(BjOracle.loseString);
		//String area_scoreExp = new String();
		
		//Definizione del comportamento del mock
		for (int i = 0; i < cards.length; i++)
			EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();
		EasyMock.replay(randeasymock);
		
		//Settaggio dello stato dell'oggetto Apple
		a2.setPlayer(playerIn);
		a2.setState_player(state_playerIn);
		a2.setState_cpu(state_cpuIn);
		
		//Invocazione del metodo sotto test
		a2.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", state_playerExp, a2.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", state_cpuExp, a2.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", fieldExp, a2.getFieldText());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		assertEquals("Visualizzazione punteggio globale Player non corretto!", state_playerExp, BjOracle.playerGlobalScore((a2.getArea_scoreText())));
		assertEquals("Visualizzazione punteggio globale CPU non corretto!", state_cpuExp, BjOracle.cpuGlobalScore(a2.getArea_scoreText()));
	}
	
	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 4 CPU= 12
	 * 
	 * Output attesi
	 * 		-Punteggio globale: Player= 5 CPU= 6
	 * 		-Aree di testo: field= "Lose!" area_score= "Player: 5\n\nCpu: 6"
	 */
	public final void testCpu02easymock() {
		//Precondizioni sull'oggetto di tipo Apple
		int playerIn = 4;
		int cards[] = {10, 2}; //Punteggio CPU pari a 12
		int state_playerIn = 5;
		int state_cpuIn = 5;
		
		//Postcondizioni sull'oggetto di tipo Apple
		int state_playerExp = 5;
		int state_cpuExp = 6;
		String fieldExp = new String(BjOracle.loseString);
		
		//Definizione del comportamento del mock
		for (int i = 0; i < cards.length; i++)
			EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();
		EasyMock.replay(randeasymock);
		
		//Settaggio dello stato dell'oggetto Apple
		a2.setPlayer(playerIn);
		a2.setState_player(state_playerIn);
		a2.setState_cpu(state_cpuIn);
		
		//Invocazione del metodo sotto test
		a2.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", state_playerExp, a2.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", state_cpuExp, a2.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", fieldExp, a2.getFieldText());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		assertEquals("Visualizzazione punteggio globale Player non corretto!", state_playerExp, BjOracle.playerGlobalScore((a2.getArea_scoreText())));
		assertEquals("Visualizzazione punteggio globale CPU non corretto!", state_cpuExp, BjOracle.cpuGlobalScore(a2.getArea_scoreText()));
	}
	
	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 12 CPU= 12
	 * 
	 * Output attesi
	 * 		-Punteggio globale: Player= 5 CPU= 6
	 * 		-Aree di testo: field= "Lose!" area_score= "Player: 5\n\nCpu: 6"
	 */
	public final void testCpu03easymock() {
		//Precondizioni sull'oggetto di tipo Apple
		int playerIn = 12;
		int cards[] = {6, 6}; //Punteggio CPU pari a 12
		int state_playerIn = 5;
		int state_cpuIn = 5;
		
		//Postcondizioni sull'oggetto di tipo Apple
		int state_playerExp = 5;
		int state_cpuExp = 6;
		String fieldExp = new String(BjOracle.loseString);
		
		//Definizione del comportamento del mock
		for (int i = 0; i < cards.length; i++)
			EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();
		EasyMock.replay(randeasymock);
		
		//Settaggio dello stato dell'oggetto Apple
		a2.setPlayer(playerIn);
		a2.setState_player(state_playerIn);
		a2.setState_cpu(state_cpuIn);
		
		//Invocazione del metodo sotto test
		a2.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", state_playerExp, a2.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", state_cpuExp, a2.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", fieldExp, a2.getFieldText());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		assertEquals("Visualizzazione punteggio globale Player non corretto!", state_playerExp, BjOracle.playerGlobalScore((a2.getArea_scoreText())));
		assertEquals("Visualizzazione punteggio globale CPU non corretto!", state_cpuExp, BjOracle.cpuGlobalScore(a2.getArea_scoreText()));
	}
	
	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 12 CPU= 17
	 * 
	 * Output attesi
	 * 		-Punteggio globale: Player= 5 CPU= 6
	 * 		-Aree di testo: field= "Lose!" area_score= "Player: 5\n\nCpu: 6"
	 */
	public final void testCpu04easymock() {
		//Precondizioni sull'oggetto di tipo Apple
		int playerIn = 12;
		int cards[] = {6, 11}; //Punteggio CPU pari a 17
		int state_playerIn = 5;
		int state_cpuIn = 5;
		
		//Postcondizioni sull'oggetto di tipo Apple
		int state_playerExp = 5;
		int state_cpuExp = 6;
		String fieldExp = new String(BjOracle.loseString);
		
		//Definizione del comportamento del mock
		for (int i = 0; i < cards.length; i++)
			EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();
		EasyMock.replay(randeasymock);
		
		//Settaggio dello stato dell'oggetto Apple
		a2.setPlayer(playerIn);
		a2.setState_player(state_playerIn);
		a2.setState_cpu(state_cpuIn);
		
		//Invocazione del metodo sotto test
		a2.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", state_playerExp, a2.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", state_cpuExp, a2.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", fieldExp, a2.getFieldText());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		assertEquals("Visualizzazione punteggio globale Player non corretto!", state_playerExp, BjOracle.playerGlobalScore((a2.getArea_scoreText())));
		assertEquals("Visualizzazione punteggio globale CPU non corretto!", state_cpuExp, BjOracle.cpuGlobalScore(a2.getArea_scoreText()));
	}
	
	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 12 CPU= 22
	 * 
	 * Output attesi
	 * 		-Punteggio globale: Player= 6 CPU= 5
	 * 		-Aree di testo: field= "Win!" area_score= "Player: 6\n\nCpu: 5"
	 */
	public final void testCpu05easymock() {
		//Precondizioni sull'oggetto di tipo Apple
		int playerIn = 12;
		int cards[] = {1, 1}; //Punteggio CPU pari a 22 (coppia di assi)
		int state_playerIn = 5;
		int state_cpuIn = 5;
		
		//Postcondizioni sull'oggetto di tipo Apple
		int state_playerExp = 6;
		int state_cpuExp = 5;
		String fieldExp = new String(BjOracle.winString);
		
		//Definizione del comportamento del mock
		for (int i = 0; i < cards.length; i++)
			EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();
		EasyMock.replay(randeasymock);
		
		//Settaggio dello stato dell'oggetto Apple
		a2.setPlayer(playerIn);
		a2.setState_player(state_playerIn);
		a2.setState_cpu(state_cpuIn);
		
		//Invocazione del metodo sotto test
		a2.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", state_playerExp, a2.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", state_cpuExp, a2.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", fieldExp, a2.getFieldText());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		assertEquals("Visualizzazione punteggio globale Player non corretto!", state_playerExp, BjOracle.playerGlobalScore((a2.getArea_scoreText())));
		assertEquals("Visualizzazione punteggio globale CPU non corretto!", state_cpuExp, BjOracle.cpuGlobalScore(a2.getArea_scoreText()));
	}
	
	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 12 CPU= 21
	 * 
	 * Output attesi
	 * 		-Punteggio globale: Player= 5 CPU= 6
	 * 		-Aree di testo: field= "Lose!" area_score= "Player: 5\n\nCpu: 6"
	 */
	public final void testCpu06easymock() {
		//Precondizioni sull'oggetto di tipo Apple
		int playerIn = 12;
		int cards[] = {10, 1}; //Punteggio CPU pari a 21 (black jack)
		int state_playerIn = 5;
		int state_cpuIn = 5;
		
		//Postcondizioni sull'oggetto di tipo Apple
		int state_playerExp = 5;
		int state_cpuExp = 6;
		String fieldExp = new String(BjOracle.loseString);
		
		//Definizione del comportamento del mock
		for (int i = 0; i < cards.length; i++)
			EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();
		EasyMock.replay(randeasymock);
		
		//Settaggio dello stato dell'oggetto Apple
		a2.setPlayer(playerIn);
		a2.setState_player(state_playerIn);
		a2.setState_cpu(state_cpuIn);
		
		//Invocazione del metodo sotto test
		a2.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", state_playerExp, a2.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", state_cpuExp, a2.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", fieldExp, a2.getFieldText());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		assertEquals("Visualizzazione punteggio globale Player non corretto!", state_playerExp, BjOracle.playerGlobalScore((a2.getArea_scoreText())));
		assertEquals("Visualizzazione punteggio globale CPU non corretto!", state_cpuExp, BjOracle.cpuGlobalScore(a2.getArea_scoreText()));
	}
	
	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 12 CPU= 12
	 * 
	 * Output attesi
	 * 		-Punteggio globale: Player= 5 CPU= 6
	 * 		-Aree di testo: field= "Lose!" area_score= "Player: 5\n\nCpu: 6"
	 */
	public final void testCpu07easymock() {
		//Precondizioni sull'oggetto di tipo Apple
		int playerIn = 12;
		int cards[] = {2, 2, 8}; //Punteggio CPU pari a 12
		int state_playerIn = 5;
		int state_cpuIn = 5;
		
		//Postcondizioni sull'oggetto di tipo Apple
		int state_playerExp = 5;
		int state_cpuExp = 6;
		String fieldExp = new String(BjOracle.loseString);
		
		//Definizione del comportamento del mock
		for (int i = 0; i < cards.length; i++)
			EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();
		EasyMock.replay(randeasymock);
		
		//Settaggio dello stato dell'oggetto Apple
		a2.setPlayer(playerIn);
		a2.setState_player(state_playerIn);
		a2.setState_cpu(state_cpuIn);
		
		//Invocazione del metodo sotto test
		a2.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", state_playerExp, a2.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", state_cpuExp, a2.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", fieldExp, a2.getFieldText());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		assertEquals("Visualizzazione punteggio globale Player non corretto!", state_playerExp, BjOracle.playerGlobalScore((a2.getArea_scoreText())));
		assertEquals("Visualizzazione punteggio globale CPU non corretto!", state_cpuExp, BjOracle.cpuGlobalScore(a2.getArea_scoreText()));
	}
	
	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 12 CPU= 17
	 * 
	 * Output attesi
	 * 		-Punteggio globale: Player= 5 CPU= 6
	 * 		-Aree di testo: field= "Lose!" area_score= "Player: 5\n\nCpu: 6"
	 */
	public final void testCpu08easymock() {
		//Precondizioni sull'oggetto di tipo Apple
		int playerIn = 12;
		int cards[] = {2, 2, 3, 4, 6}; //Punteggio CPU pari a 17
		int state_playerIn = 5;
		int state_cpuIn = 5;
		
		//Postcondizioni sull'oggetto di tipo Apple
		int state_playerExp = 5;
		int state_cpuExp = 6;
		String fieldExp = new String(BjOracle.loseString);
		
		//Definizione del comportamento del mock
		for (int i = 0; i < cards.length; i++)
			EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();
		EasyMock.replay(randeasymock);
		
		//Settaggio dello stato dell'oggetto Apple
		a2.setPlayer(playerIn);
		a2.setState_player(state_playerIn);
		a2.setState_cpu(state_cpuIn);
		
		//Invocazione del metodo sotto test
		a2.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", state_playerExp, a2.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", state_cpuExp, a2.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", fieldExp, a2.getFieldText());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		assertEquals("Visualizzazione punteggio globale Player non corretto!", state_playerExp, BjOracle.playerGlobalScore((a2.getArea_scoreText())));
		assertEquals("Visualizzazione punteggio globale CPU non corretto!", state_cpuExp, BjOracle.cpuGlobalScore(a2.getArea_scoreText()));
	}
	
	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 12 CPU= 22
	 * 
	 * Output attesi
	 * 		-Punteggio globale: Player= 6 CPU= 5
	 * 		-Aree di testo: field= "Win!" area_score= "Player: 6\n\nCpu: 5"
	 */
	public final void testCpu09easymock() {
		//Precondizioni sull'oggetto di tipo Apple
		int playerIn = 12;
		int cards[] = {2, 2, 7, 1}; //Punteggio CPU pari a 22
		int state_playerIn = 5;
		int state_cpuIn = 5;
		
		//Postcondizioni sull'oggetto di tipo Apple
		int state_playerExp = 6;
		int state_cpuExp = 5;
		String fieldExp = new String(BjOracle.winString);
		
		//Definizione del comportamento del mock
		for (int i = 0; i < cards.length; i++)
			EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();
		EasyMock.replay(randeasymock);
		
		//Settaggio dello stato dell'oggetto Apple
		a2.setPlayer(playerIn);
		a2.setState_player(state_playerIn);
		a2.setState_cpu(state_cpuIn);
		
		//Invocazione del metodo sotto test
		a2.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", state_playerExp, a2.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", state_cpuExp, a2.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", fieldExp, a2.getFieldText());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		assertEquals("Visualizzazione punteggio globale Player non corretto!", state_playerExp, BjOracle.playerGlobalScore((a2.getArea_scoreText())));
		assertEquals("Visualizzazione punteggio globale CPU non corretto!", state_cpuExp, BjOracle.cpuGlobalScore(a2.getArea_scoreText()));
	}
	
	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 12 CPU= 21
	 * 
	 * Output attesi
	 * 		-Punteggio globale: Player= 5 CPU= 6
	 * 		-Aree di testo: field= "Lose!" area_score= "Player: 5\n\nCpu: 6"
	 */
	public final void testCpu10easymock() {
		//Precondizioni sull'oggetto di tipo Apple
		int playerIn = 12;
		int cards[] = {2, 2, 7, 13}; //Punteggio CPU pari a 21 (black jack)
		int state_playerIn = 5;
		int state_cpuIn = 5;
		
		//Postcondizioni sull'oggetto di tipo Apple
		int state_playerExp = 5;
		int state_cpuExp = 6;
		String fieldExp = new String(BjOracle.loseString);
		
		//Definizione del comportamento del mock
		for (int i = 0; i < cards.length; i++)
			EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();
		EasyMock.replay(randeasymock);
		
		//Settaggio dello stato dell'oggetto Apple
		a2.setPlayer(playerIn);
		a2.setState_player(state_playerIn);
		a2.setState_cpu(state_cpuIn);
		
		//Invocazione del metodo sotto test
		a2.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", state_playerExp, a2.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", state_cpuExp, a2.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", fieldExp, a2.getFieldText());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		assertEquals("Visualizzazione punteggio globale Player non corretto!", state_playerExp, BjOracle.playerGlobalScore((a2.getArea_scoreText())));
		assertEquals("Visualizzazione punteggio globale CPU non corretto!", state_cpuExp, BjOracle.cpuGlobalScore(a2.getArea_scoreText()));
	}
	
	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 21 CPU= 23
	 * 
	 * Output attesi
	 * 		-Punteggio globale: Player= 6 CPU= 5
	 * 		-Aree di testo: field= "Win!" area_score= "Player: 6\n\nCpu: 5"
	 */
	public final void testCpu11easymock() {
		//Precondizioni sull'oggetto di tipo Apple
		int playerIn = 21;
		int cards[] = {2, 2, 6, 5, 8}; //Punteggio CPU pari a 23
		int state_playerIn = 5;
		int state_cpuIn = 5;
		
		//Postcondizioni sull'oggetto di tipo Apple
		int state_playerExp = 6;
		int state_cpuExp = 5;
		String fieldExp = new String(BjOracle.winString);
		
		//Definizione del comportamento del mock
		for (int i = 0; i < cards.length; i++)
			EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();
		EasyMock.replay(randeasymock);
		
		//Settaggio dello stato dell'oggetto Apple
		a2.setPlayer(playerIn);
		a2.setState_player(state_playerIn);
		a2.setState_cpu(state_cpuIn);
		
		//Invocazione del metodo sotto test
		a2.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", state_playerExp, a2.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", state_cpuExp, a2.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", fieldExp, a2.getFieldText());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		assertEquals("Visualizzazione punteggio globale Player non corretto!", state_playerExp, BjOracle.playerGlobalScore((a2.getArea_scoreText())));
		assertEquals("Visualizzazione punteggio globale CPU non corretto!", state_cpuExp, BjOracle.cpuGlobalScore(a2.getArea_scoreText()));
	}
	
	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 21 CPU= 22
	 * 
	 * Output attesi
	 * 		-Punteggio globale: Player= 6 CPU= 5
	 * 		-Aree di testo: field= "Win!" area_score= "Player: 6\n\nCpu: 5"
	 */
	public final void testCpu12easymock() {
		//Precondizioni sull'oggetto di tipo Apple
		int playerIn = 21;
		int cards[] = {1, 1}; //Punteggio CPU pari a 22 (coppia d'assi)
		int state_playerIn = 5;
		int state_cpuIn = 5;
		
		//Postcondizioni sull'oggetto di tipo Apple
		int state_playerExp = 6;
		int state_cpuExp = 5;
		String fieldExp = new String(BjOracle.winString);
		
		//Definizione del comportamento del mock
		for (int i = 0; i < cards.length; i++)
			EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();
		EasyMock.replay(randeasymock);
		
		//Settaggio dello stato dell'oggetto Apple
		a2.setPlayer(playerIn);
		a2.setState_player(state_playerIn);
		a2.setState_cpu(state_cpuIn);
		
		//Invocazione del metodo sotto test
		a2.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", state_playerExp, a2.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", state_cpuExp, a2.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", fieldExp, a2.getFieldText());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		assertEquals("Visualizzazione punteggio globale Player non corretto!", state_playerExp, BjOracle.playerGlobalScore((a2.getArea_scoreText())));
		assertEquals("Visualizzazione punteggio globale CPU non corretto!", state_cpuExp, BjOracle.cpuGlobalScore(a2.getArea_scoreText()));
	}
	
	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 21 CPU= 21
	 * 
	 * Output attesi
	 * 		In caso di parita' vince la CPU
	 * 		-Punteggio globale: Player= 5 CPU= 6
	 * 		-Aree di testo: field= "Lose!" area_score= "Player: 5\n\nCpu: 6"
	 */
	public final void testCpu13easymock() {
		//Precondizioni sull'oggetto di tipo Apple
		int playerIn = 21;
		int cards[] = {1, 12}; //Punteggio CPU pari a 21
		int state_playerIn = 5;
		int state_cpuIn = 5;
		
		//Postcondizioni sull'oggetto di tipo Apple
		int state_playerExp = 5;
		int state_cpuExp = 6;
		String fieldExp = new String(BjOracle.loseString);
		
		//Definizione del comportamento del mock
		for (int i = 0; i < cards.length; i++)
			EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();
		EasyMock.replay(randeasymock);
		
		//Settaggio dello stato dell'oggetto Apple
		a2.setPlayer(playerIn);
		a2.setState_player(state_playerIn);
		a2.setState_cpu(state_cpuIn);
		
		//Invocazione del metodo sotto test
		a2.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", state_playerExp, a2.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", state_cpuExp, a2.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", fieldExp, a2.getFieldText());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		assertEquals("Visualizzazione punteggio globale Player non corretto!", state_playerExp, BjOracle.playerGlobalScore((a2.getArea_scoreText())));
		assertEquals("Visualizzazione punteggio globale CPU non corretto!", state_cpuExp, BjOracle.cpuGlobalScore(a2.getArea_scoreText()));
	}
	
	/**
	 * Metodo di verifica per {@link blackjack.Apple#cpu()}.
	 * 
	 * Scenario
	 * 		-Punteggio globale: Player= 5 CPU= 5
	 * 		-Punteggio partita: Player= 21 CPU= 21
	 * 
	 * Output attesi
	 * 		In caso di parita' vince la CPU
	 * 		-Punteggio globale: Player= 5 CPU= 6
	 * 		-Aree di testo: field= "Lose!" area_score= "Player: 5\n\nCpu: 6"
	 */
	public final void testCpu14easymock() {
		//Precondizioni sull'oggetto di tipo Apple
		int playerIn = 21;
		int cards[] = {2, 2, 6, 5, 6}; //Punteggio CPU pari a 21
		int state_playerIn = 5;
		int state_cpuIn = 5;
		
		//Postcondizioni sull'oggetto di tipo Apple
		int state_playerExp = 5;
		int state_cpuExp = 6;
		String fieldExp = new String(BjOracle.loseString);
		
		//Definizione del comportamento del mock
		for (int i = 0; i < cards.length; i++)
			EasyMock.expect(randeasymock.getCard()).andReturn(BjOracle.fromCardToDouble(cards[i])).once();
		EasyMock.replay(randeasymock);
		
		//Settaggio dello stato dell'oggetto Apple
		a2.setPlayer(playerIn);
		a2.setState_player(state_playerIn);
		a2.setState_cpu(state_cpuIn);
		
		//Invocazione del metodo sotto test
		a2.cpu();
		
		//Verifichiamo il corretto aggiornamento dei punteggi globali
		assertEquals("Punteggio globale Player non corretto!", state_playerExp, a2.getState_player());
		assertEquals("Punteggio globale CPU non corretto!", state_cpuExp, a2.getState_cpu());
		
		//Verifichiamo che l'esito della partita sia visualizato correttamente
		assertEquals("Campo field non corretto!", fieldExp, a2.getFieldText());
		
		//Verifichiamo che i nuovi punteggi globali siano visualizzati correttamente
		assertEquals("Visualizzazione punteggio globale Player non corretto!", state_playerExp, BjOracle.playerGlobalScore((a2.getArea_scoreText())));
		assertEquals("Visualizzazione punteggio globale CPU non corretto!", state_cpuExp, BjOracle.cpuGlobalScore(a2.getArea_scoreText()));
	}
}
