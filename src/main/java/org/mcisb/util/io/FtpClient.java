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
import java.util.*;
import org.apache.commons.net.ftp.*;

/**
 * @author Neil Swainston
 */
public class FtpClient
{
	/**
	 * 
	 */
	private final FTPClient client = new FTPClient();
	
	/**
	 * 
	 * @param hostname
	 * @param username
	 * @param password
	 * @throws IOException 
	 * @throws SocketException 
	 */
	public FtpClient( final String hostname, final String username, final String password ) throws SocketException, IOException
	{
		client.connect( hostname );
		check();
		
		if( username != null && password != null )
		{
			client.login( username, password );
			check();
		}
	}
	
	/**
	 * 
	 * @param fileType
	 * @throws IOException 
	 */
	public void setFileType( final int fileType ) throws IOException
	{
		client.setFileType( fileType );
	}
	
	/**
	 * @throws IOException 
	 */
	public void close() throws IOException
	{
		if( client.isConnected() )
		{
			client.logout();
	        client.disconnect();
		}
		
		check();
	}
	
	/**
	 * 
	 * @param directory
	 * @param filename
	 * @param local
	 * @return boolean
	 * @throws SocketException
	 * @throws IOException
	 */
	public boolean get( final String directory, final String filename, final File local ) throws SocketException, IOException
	{
		try( final OutputStream os = new FileOutputStream( local ) )
		{
			return get( directory, filename, os );
		}
	}
	
	/**
	 * 
	 * @param directory
	 * @param filename
	 * @param os
	 * @return boolean
	 * @throws SocketException
	 * @throws IOException
	 */
	public boolean get( final String directory, final String filename, final OutputStream os ) throws SocketException, IOException
	{
		boolean success = false;
	    
		try
	    {
			client.changeWorkingDirectory( directory );
			check();
			
			success = client.retrieveFile( filename, os );
			check();

			return success;
	    }
		finally
		{
			if( client != null && client.isConnected() )
			{
				client.disconnect();
	        }
	    }
	}
	
	/**
	 * 
	 * @param directory
	 * @return List<String>
	 * @throws IOException
	 */
	public List<String> getFilenames( final String directory ) throws IOException
	{
		final List<String> filenames = new ArrayList<>();
		client.changeWorkingDirectory( directory );
		check();
		
		for( FTPFile file : client.listFiles() )
		{
			filenames.add( file.getName() );
		}
		
		return filenames;
	}
	
	/**
	 * 
	 * @throws IOException 
	 */
	private void check() throws IOException
	{
		final int reply = client.getReplyCode();

		if( !FTPReply.isPositiveCompletion( reply ) )
		{
			final String response = client.getReplyString();
			client.disconnect();
			throw new IOException( response );
		}
	}
}