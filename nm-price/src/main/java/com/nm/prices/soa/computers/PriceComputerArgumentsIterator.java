package com.nm.prices.soa.computers;

/***
 * 
 * @author nabilmansouri
 *
 */
public interface PriceComputerArgumentsIterator {

	public void reset();

	public boolean hasNext();

	public PriceComputerArguments next();
}
