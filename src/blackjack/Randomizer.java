/**
 * 
 */
package blackjack;

/**
 * @author Mario
 *
 */
public class Randomizer implements Randomizable {

	/**
	 * 
	 */
	public Randomizer() {
		// TODO Stub di costruttore generato automaticamente
	}

	/* (non Javadoc)
	 * @see blackjack.Randomizable#getRandom()
	 */
	@Override
	public double getCard() {
		return Math.random(); //necessita azzeramento del seme?
	}

}
