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

import java.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class NumberUtils
{
	/**
	 * 
	 */
	public static final int UNDEFINED = Integer.MIN_VALUE;
	
	/**
	 * 
	 * @param s
	 * @return boolean
	 */
	public static boolean isInteger( String s )
	{
		try
		{
			Integer.parseInt( s );
			return true;
		}
		catch( NumberFormatException e )
		{
			return false;
		}
	}
	
	/**
	 * 
	 * @param s
	 * @return boolean
	 */
	public static boolean isDecimal( String s )
	{
		try
		{
			Double.parseDouble( s );
			return true;
		}
		catch( NumberFormatException e )
		{
			return false;
		}
	}
	
	/**
	 * 
	 * @param s
	 * @return boolean
	 */
	public static boolean containsInteger( String s )
	{
		final StringTokenizer tokenizer = new StringTokenizer( s );
		
		while( tokenizer.hasMoreTokens() )
		{
			if( isInteger( tokenizer.nextToken() ) )
			{
				return true;
			}
		}
		
		return false;
	}
}
