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
package org.mcisb.ui.util;

import java.io.*;
import java.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class CustomFileFilter extends javax.swing.filechooser.FileFilter implements FileFilter
{
	/**
	 * 
	 */
	private final Collection<String> extensions;

	/**
	 * 
	 * @param extensions
	 */
	public CustomFileFilter( final Collection<String> extensions )
	{
		this.extensions = extensions;
	}

	/**
	 * 
	 * @param extension
	 */
	public CustomFileFilter( final String extension )
	{
		extensions = new ArrayList<>();
		extensions.add( extension );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept( File file )
	{
		if( file.isDirectory() )
		{
			return true;
		}

		String name = file.getName();
		int i = name.lastIndexOf( '.' );

		if( i != -1 )
		{
			String extension = name.substring( i + 1 ).toUpperCase( Locale.getDefault() );

			for( Iterator<String> iterator = extensions.iterator(); iterator.hasNext(); )
			{
				String allowedExtensions = iterator.next();

				if( extension.equalsIgnoreCase( allowedExtensions ) )
				{
					return true;
				}
			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	@Override
	public String getDescription()
	{
		final String WILDCARD = "*."; //$NON-NLS-1$
		final String SEPARATOR = ";"; //$NON-NLS-1$

		if( extensions.size() == 0 )
		{
			return ""; //$NON-NLS-1$
		}

		StringBuffer description = new StringBuffer();

		for( Iterator<String> iterator = extensions.iterator(); iterator.hasNext(); )
		{
			description.append( WILDCARD );
			description.append( iterator.next() );
			description.append( SEPARATOR );
		}

		return description.substring( 0, description.lastIndexOf( SEPARATOR ) );
	}
}
