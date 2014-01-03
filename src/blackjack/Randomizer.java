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
	public double getRandom() {
		return Math.random(); //necessita azzeramento del seme?
	}

}
