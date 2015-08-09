/*******************************************************************************
 * Manchester Centre for Integrative Systems Biology
 * University of Manchester
 * Manchester M1 7ND
 * United Kingdom
 * 
 * Copyright (C) 2011 University of Manchester
 * 
 * This program is released under the Academic Free License ("AFL") v3.0.
 * (http://www.opensource.org/licenses/academic.php)
 *******************************************************************************/
package org.mcisb.util.io;

import java.io.*;
import java.net.*;
import java.util.zip.*;
import org.apache.commons.compress.archivers.*;
import org.apache.commons.compress.archivers.tar.*;

// import org.apache.tools.tar.*;

/**
 * @author Neil Swainston
 * 
 */
public class TarUtils
{
	/**
	 * 
	 * @param tarUrl
	 * @param destinationDirectory
	 * @throws IOException
	 */
	public static void untar( final URL tarUrl, final File destinationDirectory ) throws IOException
	{
		untar( tarUrl.openStream(), destinationDirectory );
	}

	/**
	 * 
	 * @param tarFile
	 * @param destinationDirectory
	 * @throws IOException
	 */
	public static void untar( final File tarFile, final File destinationDirectory ) throws IOException
	{
		try ( final InputStream is = new FileInputStream( tarFile ) )
		{
			untar( is, destinationDirectory );
		}
	}

	/**
	 * 
	 * @param tarInputStream
	 * @param destinationDirectory
	 * @throws IOException
	 */
	public static void untar( final InputStream tarInputStream, final File destinationDirectory ) throws IOException
	{
		if( !destinationDirectory.exists() )
		{
			if( !destinationDirectory.mkdir() )
			{
				throw new IOException();
			}
		}

		TarArchiveInputStream is = null;

		try
		{
			is = new TarArchiveInputStream( new GZIPInputStream( tarInputStream ) );
			ArchiveEntry tarEntry = null;

			while( ( tarEntry = is.getNextEntry() ) != null )
			{
				final File destination = new File( destinationDirectory, tarEntry.getName() );

				if( tarEntry.isDirectory() )
				{
					if( !destination.mkdir() )
					{
						// Take no action.
						// throw new IOException();
					}
				}
				else
				{
					try ( final OutputStream os = new FileOutputStream( destination ) )
					{
						new StreamReader( is, os ).read();
					}
				}
			}
		}
		catch( IOException e )
		{
			if( !destinationDirectory.delete() )
			{
				throw new IOException();
			}
			throw e;
		}
		finally
		{
			if( is != null )
			{
				is.close();
			}
		}
	}
}