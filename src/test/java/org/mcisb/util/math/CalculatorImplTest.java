/*******************************************************************************
 * Manchester Centre for Integrative Systems Biology
 * University of Manchester
 * Manchester M1 7ND
 * United Kingdom
 * 
 * Copyright (C) 2007 University of Manchester
 * 
 * This program is released under the Academic Free License ("AFL") v3.0.
 * (http://www.opensource.org/licenses/academic.php)
 *******************************************************************************/
package org.mcisb.util.math;

import org.junit.*;
// import org.mcisb.util.math.matlab.*;
// import org.mcisb.util.math.octave.*;

/**
 *
 * @author Neil Swainston
 */
public class CalculatorImplTest
{	
	/**
	 * 
	 */
	@Test
	public void getMatlabResults() // throws Exception
	{
		// getResults( MatlabImpl.getInstance() );
	}
	
	/**
	 * 
	 */
	@Test
	public void getOctaveResults() // throws Exception
	{
		// getResults( OctaveImpl.getInstance() );
	}
	
	/**
	 * 
	 * @param calculator
	 * @throws Exception
	 */
	/*
	private void getResults( final CalculatorImpl calculator ) throws Exception
	{
		try
		{
			final java.util.Map<String,Double> results = calculator.getResults( "A=sqrt(5)" ); //$NON-NLS-1$
			Assert.assertEquals( results.get( "A" ).doubleValue(), 2.2361, 0.0 ); //$NON-NLS-1$
		}
		finally
		{
			if( calculator != null )
			{
				calculator.close();
			}
		}
	}
	*/
}