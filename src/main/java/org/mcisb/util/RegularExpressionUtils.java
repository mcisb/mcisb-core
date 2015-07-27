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
import java.net.*;
import java.util.*;
import java.util.regex.*;
import org.mcisb.util.io.*;

/**
 * 
 * @author Neil Swainston
 */
public class RegularExpressionUtils
{
	/**
	 * 
	 */
	public static final String EC_REGEX = "(?=.*)[\\d]+\\.[\\d]+\\.[\\d]+\\.[\\d]+(?=.*)"; //$NON-NLS-1$

	/**
	 * 
	 */
	public static final String INCHI_REGEX = "(?=.*)InChI=[\\d]+S?/[A-Z[\\d]+]+/[\\w/-[,][+][\\(][\\)]]+(?=.*)"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	public static final String YEAST_ORF_REGEX = "(?=.*)Y[A-Z]{2}[\\d]{3}[A-Z](?=.*)"; //$NON-NLS-1$
	
	/**
	 * 
	 * @param url
	 * @param regex
	 * @return Collection<String>
	 * @throws IOException
	 */
	public static Collection<String> getMatches( final URL url, final String regex ) throws IOException
	{
		return getMatches( url, regex, 0 );
	}
	
	/**
	 *
	 * @param is
	 * @param regex
	 * @return Collection
	 * @throws IOException
	 */
	public static Collection<String> getMatches( final InputStream is, final String regex ) throws IOException
	{
		return getMatches( is, regex, 0 );
	}
	
	/**
	 * 
	 * @param input
	 * @param regex
	 * @return Collection
	 */
	public static Collection<String> getMatches( final String input, final String regex )
	{
		return getMatches( input, regex, 0 );
	}
	
	/**
	 * 
	 * @param url
	 * @param regex
	 * @return Collection<String>
	 * @throws IOException
	 */
	public static Collection<String> getAllMatches( final URL url, final String regex ) throws IOException
	{
		return getAllMatches( url, regex, 0 );
	}
	
	/**
	 *
	 * @param is
	 * @param regex
	 * @return Collection
	 * @throws IOException
	 */
	public static Collection<String> getAllMatches( final InputStream is, final String regex ) throws IOException
	{
		return getAllMatches( is, regex, 0 );
	}
	
	/**
	 * 
	 * @param input
	 * @param regex
	 * @return Collection
	 */
	public static Collection<String> getAllMatches( final String input, final String regex )
	{
		return getAllMatches( input, regex, 0 );
	}
	
	/**
	 *
	 * @param url
	 * @param regex
	 * @param flags 
	 * @return Collection
	 * @throws IOException
	 */
	public static Collection<String> getMatches( final URL url, final String regex, final int flags ) throws IOException
	{
		return new LinkedHashSet<>( getAllMatches( url, regex, flags ) );
	}
	
	/**
	 *
	 * @param url
	 * @param regex
	 * @param flags 
	 * @return Collection
	 * @throws IOException
	 */
	public static Collection<String> getAllMatches( final URL url, final String regex, final int flags ) throws IOException
	{
		try( final InputStream is = url.openStream() )
		{
			final Collection<String> matches = getAllMatches( is, regex, flags );
			is.close();
			return matches;
		}
	}
	
	/**
	 *
	 * @param is
	 * @param regex
	 * @param flags 
	 * @return Collection
	 * @throws IOException
	 */
	public static Collection<String> getMatches( final InputStream is, final String regex, final int flags ) throws IOException
	{
		return getMatches( new String( StreamReader.read( is ) ), regex, flags );
	}
	
	/**
	 *
	 * @param is
	 * @param regex
	 * @param flags 
	 * @return Collection
	 * @throws IOException
	 */
	public static Collection<String> getAllMatches( final InputStream is, final String regex, final int flags ) throws IOException
	{
		return getAllMatches( new String( StreamReader.read( is ) ), regex, flags );
	}
	
	/**
	 * 
	 * @param input
	 * @param regex
	 * @param flags 
	 * @return Collection
	 */
	public static Collection<String> getMatches( final String input, final String regex, final int flags )
	{
		return new LinkedHashSet<>( getAllMatches( input, regex, flags ) );
	}
	
	/**
	 * 
	 * @param input
	 * @param regex
	 * @param flags 
	 * @return Collection
	 */
	public static Collection<String> getAllMatches( final String input, final String regex, final int flags )
	{
		final Collection<String> matches = new ArrayList<>();
		final Pattern pattern = Pattern.compile( regex, flags );
		final Matcher matcher = pattern.matcher( input );
		
        while( matcher.find() )
		{
        	matches.add( input.substring( matcher.start(), matcher.end() ) );
		}
		
        return matches;
	}
}