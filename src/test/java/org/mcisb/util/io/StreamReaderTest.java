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
import org.junit.*;

/**
 * 
 * 
 * @author Neil Swainston
 */
public class StreamReaderTest
{
	/**
	 * 
	 */
	private final static String EXPECTED_CONTENT = "expected content"; //$NON-NLS-1$

	/**
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void readInputStream() throws IOException
	{
		try ( final InputStream is = getInputStream() )
		{
			final String content = new String( StreamReader.read( is ) );
			Assert.assertTrue( content.contains( EXPECTED_CONTENT ) );
		}
	}

	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void read() throws IOException, InterruptedException
	{
		read( null );
	}

	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void readFile() throws IOException, InterruptedException
	{
		final File file = File.createTempFile( "temp", ".txt" ); //$NON-NLS-1$ //$NON-NLS-2$
		read( file );
	}

	/**
	 * 
	 * @param file
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("resource")
	private static void read( final File file ) throws IOException, InterruptedException
	{
		InputStream fis = null;

		try ( final InputStream is = getInputStream(); final OutputStream os = ( file == null ) ? new ByteArrayOutputStream() : new FileOutputStream( file ) )
		{
			final StreamReader reader = new StreamReader( is, os );
			final Thread thread = new Thread( reader );
			thread.start();
			thread.join();

			if( file != null )
			{
				os.close();
				fis = new FileInputStream( file );
			}

			final String content = ( file == null ) ? os.toString() : new String( StreamReader.read( fis ) );
			Assert.assertTrue( content.contains( EXPECTED_CONTENT ) );
			is.close();
			os.close();
		}
		finally
		{
			if( fis != null )
			{
				fis.close();
			}
			if( file != null )
			{
				if( !file.delete() )
				{
					throw new IOException();
				}
			}
		}
	}

	/**
	 * 
	 * @return InputStream
	 */
	private static InputStream getInputStream()
	{
		final String CONTENT = "This string contains expected content for matching."; //$NON-NLS-1$ );
		return new ByteArrayInputStream( CONTENT.getBytes() );
	}
}