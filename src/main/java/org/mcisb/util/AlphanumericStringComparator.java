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

import java.io.*;
import java.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class AlphanumericStringComparator implements Serializable, Comparator<String>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare( final String o1, final String o2 )
	{
		final List<Comparable<?>> chunks1 = getChunks( o1 );
		final List<Comparable<?>> chunks2 = getChunks( o2 );

		for( int i = 0; i < Math.min( chunks1.size(), chunks2.size() ); i++ )
		{
			if( chunks1.get( i ) instanceof Double && chunks2.get( i ) instanceof Double )
			{
				final int compare = ( (Double)chunks1.get( i ) ).compareTo( (Double)chunks2.get( i ) );

				if( compare != 0 )
				{
					return compare;
				}
			}
			else
			{
				final int compare = chunks1.get( i ).toString().compareTo( chunks2.get( i ).toString() );

				if( compare != 0 )
				{
					return compare;
				}
			}
		}

		return chunks1.size() - chunks2.size();
	}

	/**
	 * 
	 * @param s
	 * @return List<Comparable<?>>
	 */
	private static List<Comparable<?>> getChunks( final String s )
	{
		final List<Comparable<?>> chunks = new ArrayList<>();
		String chunk = EMPTY_STRING;

		for( int i = 0; i < s.length(); i++ )
		{
			char c = s.charAt( i );

			if( !inChunk( c, chunk ) )
			{
				chunks.add( NumberUtils.isDecimal( chunk ) ? Double.valueOf( chunk ) : chunk );
				chunk = EMPTY_STRING;
			}

			chunk = chunk + c;
		}

		chunks.add( NumberUtils.isDecimal( chunk ) ? Double.valueOf( chunk ) : chunk );

		return chunks;
	}

	/**
	 * 
	 * @param ch
	 * @param s
	 * @return boolean
	 */
	private static boolean inChunk( char ch, String s )
	{
		if( s.length() == 0 )
		{
			return true;
		}

		return !( ( Character.isDigit( s.charAt( 0 ) ) || s.charAt( 0 ) == '.' ) ^ ( Character.isDigit( ch ) || ch == '.' ) );
	}
}