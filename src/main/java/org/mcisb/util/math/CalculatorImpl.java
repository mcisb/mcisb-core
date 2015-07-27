/*******************************************************************************
 * Manchester Centre for Integrative Systems Biology
 * University of Manchester
 * Manchester M1 7ND
 * United Kingdom
 * 
 * Copyright (C) 2008 University of Manchester
 * 
 * This program is released under the Academic Free License ("AFL") v3.0.
 * (http://www.opensource.org/licenses/academic.php)
 *******************************************************************************/
package org.mcisb.util.math;

/**
 * @author Neil Swainston
 */
public interface CalculatorImpl
{
	/**
	 * 
	 *
	 * @param command
	 * @return HashMap
	 * @throws java.lang.Exception
	 */
	public abstract java.util.HashMap<String,Double> getResults( final String command ) throws java.lang.Exception;
	
	/**
	 * 
	 * @throws java.lang.Exception
	 */
	public abstract void close() throws java.lang.Exception;
}