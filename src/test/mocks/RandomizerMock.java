/**
 * 
 */
package test.mocks;

import blackjack.Randomizable;
import test.util.BjOracle;

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
	public double getRandom() {
		return BjOracle.randomDouble();
	}

}
