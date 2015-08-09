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

import java.io.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class CalculatorUtils
{
	/**
	 * 
	 * @param output
	 * @return HashMap
	 * @throws java.lang.Exception
	 */
	public static java.util.HashMap<String,Double> processOutput( final String output ) throws java.lang.Exception
	{
		final String INFINITY = "Inf"; //$NON-NLS-1$
		final String ERROR = "Error"; //$NON-NLS-1$
		final java.util.HashMap<String,Double> results = new java.util.HashMap<>();

		if( output.contains( ERROR ) )
		{
			throw new java.lang.Exception( output );
		}

		final BufferedReader reader = new BufferedReader( new StringReader( output ) );

		String line = null;
		String key = null;

		while( ( line = reader.readLine() ) != null )
		{
			line = line.replace( '=', '\0' ).trim();

			if( NumberUtils.isDecimal( line ) )
			{
				results.put( key, Double.valueOf( Double.parseDouble( line ) ) );
			}
			else if( line.equals( INFINITY ) )
			{
				results.put( key, Double.valueOf( Double.POSITIVE_INFINITY ) );
			}
			else if( line.length() > 0 )
			{
				key = line;
			}
		}

		return results;
	}
}