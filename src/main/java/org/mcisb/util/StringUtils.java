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
package org.mcisb.util;

import java.text.*;
import java.util.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class StringUtils
{
	/**
	 * 
	 */
	private final static String E0 = "E0"; //$NON-NLS-1$

	/**
	 * 
	 */
	private final static NumberFormat engineeringFormat = new DecimalFormat( "##0.#E0" ); //$NON-NLS-1$

	/**
	 * 
	 * @return String
	 */
	public static String getUniqueId()
	{
		final String DASH = "-"; //$NON-NLS-1$
		final String UNDERSCORE = "_"; //$NON-NLS-1$
		return UNDERSCORE + UUID.randomUUID().toString().replaceAll( DASH, UNDERSCORE );
	}

	/**
	 * 
	 * @param d
	 * @return String
	 */
	public static String getEngineeringNotation( final double d )
	{
		final String engineeringNotation = engineeringFormat.format( d );

		if( engineeringNotation.endsWith( E0 ) )
		{
			return engineeringNotation.substring( 0, engineeringNotation.length() - E0.length() );
		}

		return engineeringNotation;
	}
}