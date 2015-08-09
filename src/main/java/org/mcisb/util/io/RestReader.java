/**
 * 
 */
package org.mcisb.util.io;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author neilswainston
 * 
 */
public abstract class RestReader
{
	/**
	 * 
	 */
	private final String location;

	/**
	 * 
	 * @param url
	 * @param parameters
	 */
	public RestReader( final String url, final Map<String,String> parameters )
	{
		this.location = parse( url, parameters );
	}

	/**
	 * 
	 * @return Object
	 * @throws Exception
	 */
	public Object read() throws Exception
	{
		final int MILLISECONDS = 1000;

		HttpURLConnection connection = null;

		try
		{
			final URL url = new URL( location );

			do
			{
				connection = (HttpURLConnection)url.openConnection();
				connection.setDoInput( true );
				connection.connect();

				int wait = 0;

				final String header = connection.getHeaderField( "Retry-After" ); //$NON-NLS-1$

				if( header != null )
				{
					wait = Integer.parseInt( header );
				}

				if( wait == 0 )
				{
					if( connection.getResponseCode() == HttpURLConnection.HTTP_OK )
					{
						return processInputStream( connection.getInputStream() );
					}

					return null;
				}

				connection.disconnect();
				Thread.sleep( wait * MILLISECONDS );
			}
			while( true );
		}
		finally
		{
			if( connection != null )
			{
				connection.disconnect();
			}
		}
	}

	/**
	 * 
	 * @param is
	 * @return Object
	 * @throws Exception
	 */
	protected abstract Object processInputStream( final InputStream is ) throws Exception;

	/**
	 * 
	 * @param url
	 * @param parameters
	 * @return String
	 */
	private static String parse( final String url, final Map<String,String> parameters )
	{
		final StringBuilder stringBuilder = new StringBuilder( url );
		stringBuilder.append( '?' );

		boolean appendAmpersand = false;

		for( Map.Entry<String,String> parameter : parameters.entrySet() )
		{
			if( appendAmpersand )
			{
				stringBuilder.append( '&' );
			}
			else
			{
				appendAmpersand = true;
			}

			stringBuilder.append( parameter.getKey() );
			stringBuilder.append( '=' );
			stringBuilder.append( parameter.getValue() );
		}

		return stringBuilder.toString();
	}
}