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

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.zip.*;

/**
 * 
 * @author Neil Swainston
 */
public class StreamReader implements Runnable
{
	/**
	 * Output stream.
	 */
	protected final OutputStream os;
	
	/**
	 * Error stream.
	 */
	protected final OutputStream es;
	
	/**
	 * Input stream.
	 */
	protected InputStream is;
	
	/**
	 *
	 * @param os
	 */
	public StreamReader( final OutputStream os )
	{
		this( null, os, System.err );
	}
	
	/**
	 *
	 * @param is
	 * @param os
	 */
	public StreamReader( final InputStream is, final OutputStream os )
	{
		this( is, os, System.err );
	}
	
	/**
	 *
	 * @param is
	 * @param os
	 * @param es
	 */
	public StreamReader( final InputStream is, final OutputStream os, final OutputStream es )
	{
		setInputStream( is );
		this.os = new BufferedOutputStream( os );
		this.es = new BufferedOutputStream( es );
	}
	
	/**
	 * 
	 * @param is
	 */
	public void setInputStream( final InputStream is )
	{
		this.is = new BufferedInputStream( is );
	}
	
	/**
	 *
	 * @throws IOException
	 */
	public void read() throws IOException
	{
		final int BUFFER_SIZE = 1048576;
		final ByteBuffer buffer = ByteBuffer.allocate( BUFFER_SIZE );
        
        try( final ReadableByteChannel source = Channels.newChannel( is ); final WritableByteChannel destination = Channels.newChannel( os ) )
        {
        	while( source.read( buffer ) > 0 )
        	{
        		buffer.flip(); 
        		destination.write( buffer );
        		buffer.clear();
        	}
        }
	}
	
	/* 
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		try
		{
			read();
		}
		catch( IOException e )
		{
			e.printStackTrace( new PrintStream( es ) );
		}
	}
	
	/**
	 * 
	 * @param is
	 * @return String
	 * @throws IOException
	 */
	public static byte[] read( final InputStream is ) throws IOException
	{
		ByteArrayOutputStream os = null;
		
		try
		{
			os = new ByteArrayOutputStream();
			new StreamReader( is, os ).read();
			final byte[] b = os.toByteArray();
			os.close();
			return b;
		}
		finally
		{
			if( os != null )
			{
				os.close();
			}
		}
	}
	
	/**
	 * 
	 * @param zipUrl
	 * @param root 
	 * @throws IOException
	 */
	public static void unzip( final URL zipUrl, final File root ) throws IOException
	{
		try( @SuppressWarnings("resource") final InputStream is = new BufferedInputStream( zipUrl.openStream() );
			@SuppressWarnings("resource") final ZipInputStream zis = new ZipInputStream( is ) )
		{
			ZipEntry entry;

			while( ( entry = zis.getNextEntry() ) != null )
			{
				final File entryFile = new File( root, entry.getName() );
				
				if( entry.getName().endsWith( "/" ) ) //$NON-NLS-1$
				{
					if( !entryFile.mkdir() )
					{
						throw new IOException();
					}
				}
				else
				{
					try( final OutputStream os = new FileOutputStream( entryFile ) )
					{
						new StreamReader( zis, os ).read();
					}
				}
			}
		}
	}
}