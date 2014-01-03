/**
 * 
 */
package blackjack;


/**
 * @author Mario
 *
 */
public class RandomizerMock implements Randomizable {

	/**
	 * 
	 */
	public RandomizerMock() {
		// TODO Stub di costruttore generato automaticamente
	}

	/* (non Javadoc)
	 * @see blackjack.Randomizable#getRandom()
	 */
	@Override
	public double getCard() {
		return cards[cardIndex++];
	}
	
	/**
	 * @return cardIndex
	 */
	public int getCardIndex() {
		return cardIndex;
	}

	/**
	 * @param cardIndex cardIndex da impostare
	 */
	public void setcardIndex(int cardIndex) {
		this.cardIndex = cardIndex;
	}

	/**
	 * @param cards cards da impostare
	 */
	public void setCards(int cardsInt[]) {
		cards = new double[cardsInt.length];
		
		for (int i = 0; i < cardsInt.length; i++) {
			cards[i] = (double) (cardsInt[i] - 1) / 12;
			//System.out.println(cards[i]);
		}
		
	}

	private int cardIndex = 0;
	private double cards[];
}
