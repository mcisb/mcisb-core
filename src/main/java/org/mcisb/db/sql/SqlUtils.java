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
package org.mcisb.db.sql;

import java.util.*;

/**
 *
 * @author Neil Swainston
 */
public class SqlUtils
{
	/**
	 * 
	 */
	public static final String FIELD_SEPARATOR = "."; //$NON-NLS-1$
	
	/**
	 * 
	 * @param value
	 * @return String
	 */
	public static String getValueString( final Object value )
	{
		final StringBuffer buffer = new StringBuffer();
		
		if( value instanceof Number || value instanceof Boolean || value == null )
		{
			buffer.append( value );
		}
		else
		{
			final String QUOTE = "'"; //$NON-NLS-1$
			final String ESCAPED_QUOTE = "''"; //$NON-NLS-1$
			buffer.append( "'" ); //$NON-NLS-1$
			buffer.append( value.toString().replaceAll( QUOTE, ESCAPED_QUOTE ) );
			buffer.append( "'" ); //$NON-NLS-1$
		}
		
		return buffer.toString();
	}
	
	/**
	 * Takes a Collection of values and returns them in the form name1,name2,nameN.
	 * 
	 * This can then be used in the FROM SQL command.
	 * 
	 * @param values
	 * @return String
	 */
	static String getValuesString( final Collection<String> values )
	{
		final String SEPARATOR = ","; //$NON-NLS-1$
		return concatenate( values, SEPARATOR );
	}
	
	/**
	 * 
	 * @param objects
	 * @param separator
	 * @return String
	 */
	static String concatenate( final Collection<String> objects, final String separator )
	{
		final StringBuffer buffer = new StringBuffer();
		
		if( objects != null )
		{
			for( Iterator<String> iterator = objects.iterator(); iterator.hasNext(); )
			{
				buffer.append( iterator.next() );
				buffer.append( separator ); 
			}
		}
		
		// Strip out final separator:
		return concatenate( objects, null, separator );
	}
	
	/**
	 *
	 * @param objects
	 * @param prefix
	 * @param separator
	 * @return String
	 */
	private static String concatenate( final Collection<String> objects, final String prefix, final String separator )
	{
		final StringBuffer buffer = new StringBuffer();
		
		if( objects != null )
		{
			for( Iterator<String> iterator = objects.iterator(); iterator.hasNext(); )
			{
				if( prefix != null )
				{
					buffer.append( prefix );
				}
				buffer.append( iterator.next() );
				buffer.append( separator ); 
			}
		}
		
		// Strip out final separator:
		return buffer.substring( 0, ( buffer.length() == 0 ) ? 0 : buffer.lastIndexOf( separator ) );
	}
}