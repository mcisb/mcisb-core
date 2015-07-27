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
package org.mcisb.util.io;

import java.beans.*;
import java.io.*;
import org.mcisb.util.*;
import org.mcisb.util.task.*;

/**
 * 
 * @author Neil Swainston
 */
public class ProgressStreamReader extends StreamReader
{
	/**
	 * 
	 */
	private final PropertyChangeSupport support = new PropertyChangeSupport( this );
	
	/**
	 * 
	 * @param is
	 * @param os
	 */
	public ProgressStreamReader( final InputStream is, final OutputStream os )
	{
		super( is, os );
	}

	/*
	 * (non-Javadoc)
	 * @see org.mcisb.util.io.StreamReader#read()
	 */
	@Override
	public void read() throws IOException
	{
		final String PROGRESS_REGEXP = "(?<=.*)[\\d]+(?=%)"; //$NON-NLS-1$
		final byte[] LINE_SEPARATOR = System.getProperty( "line.separator" ).getBytes(); //$NON-NLS-1$
        final BufferedReader reader = new BufferedReader( new InputStreamReader( is ) );
        String line = null;
        int oldProgress = 0;
        
		while( ( line = reader.readLine() ) != null )
		{
			final String progress = CollectionUtils.getFirst( RegularExpressionUtils.getMatches( line, PROGRESS_REGEXP ) );
			
			if( progress != null )
			{
				final int newProgress = Integer.parseInt( progress );
				support.firePropertyChange( Task.PROGRESS, oldProgress, newProgress );
				oldProgress = newProgress;
			}
			
			os.write( line.getBytes() );
			os.write( LINE_SEPARATOR );
			os.flush();
		}
	}
	
	/**
	 * 
	 * @param is
	 * @param listeners
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] read( final InputStream is, final PropertyChangeListener[] listeners ) throws IOException
	{
		ProgressStreamReader streamReader = null;
		
		try
		{
			@SuppressWarnings("resource")
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			streamReader = new ProgressStreamReader( is, os );
			
			for( int i = 0; i < listeners.length; i++ )
			{
				streamReader.addPropertyChangeListener( listeners[ i ] );
			}
			
			streamReader.read();
			final byte[] b = os.toByteArray();
			os.close();
			return b;
		}
		finally
		{
			if( streamReader != null )
			{
				for( int i = 0; i < listeners.length; i++ )
				{
					streamReader.removePropertyChangeListener( listeners[ i ] );
				}
			}
		}
	}

	/**
	 * 
	 * @param listener
	 */
	private void addPropertyChangeListener( final PropertyChangeListener listener )
	{
		support.addPropertyChangeListener( listener );
	}
	
	/**
	 * 
	 * @param listener
	 */
	private void removePropertyChangeListener( final PropertyChangeListener listener )
	{
		support.removePropertyChangeListener( listener );
	}
}