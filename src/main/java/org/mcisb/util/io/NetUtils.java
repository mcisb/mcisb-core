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
import java.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class NetUtils
{
	/**
	 * 
	 */
	private final static Random random = new Random();

	/**
	 * 
	 * @param url
	 * @param parameters
	 * @return InputStream
	 * @throws IOException
	 */
	public static String getUrl( final String url, final Map<String,Object> parameters ) throws IOException
	{
		final String SEPARATOR = "?"; //$NON-NLS-1$
		return url + SEPARATOR + getQueryString( parameters, false );
	}

	/**
	 * 
	 * @param url
	 * @param parameters
	 * @return InputStream
	 * @throws IOException
	 */
	public static InputStream doPost( final URL url, final Map<String,Object> parameters ) throws IOException
	{
		return doPost( url, parameters, true );
	}

	/**
	 * 
	 * @param url
	 * @param parameters
	 * @param encode
	 * @return InputStream
	 * @throws IOException
	 */
	public static InputStream doPost( final URL url, final Map<String,Object> parameters, final boolean encode ) throws IOException
	{
		final URLConnection urlConn = url.openConnection();
		urlConn.setDoInput( true );
		urlConn.setDoOutput( true );
		urlConn.setUseCaches( false );
		urlConn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" ); //$NON-NLS-1$ //$NON-NLS-2$

		OutputStream os = null;

		try
		{
			os = new DataOutputStream( urlConn.getOutputStream() );
			os.write( getQueryString( parameters, encode ).getBytes() );
			os.flush();
		}
		finally
		{
			if( os != null )
			{
				os.close();
			}
		}

		return urlConn.getInputStream();
	}

	/**
	 * 
	 * @param parameters
	 * @param encode
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public static String getQueryString( final Map<String,Object> parameters, final boolean encode ) throws UnsupportedEncodingException
	{
		final String ENCODING = "UTF-8"; //$NON-NLS-1$
		final String EQUALS = "="; //$NON-NLS-1$
		final String AMPERSAND = "&"; //$NON-NLS-1$
		final String EQUALS_ENCODED = encode ? URLEncoder.encode( EQUALS, ENCODING ) : EQUALS;
		final String AMPERSAND_ENCODED = encode ? URLEncoder.encode( AMPERSAND, ENCODING ) : AMPERSAND;

		final StringBuffer queryString = new StringBuffer();

		for( Iterator<Map.Entry<String,Object>> iterator = parameters.entrySet().iterator(); iterator.hasNext(); )
		{
			final Map.Entry<String,Object> entry = iterator.next();
			queryString.append( encode ? URLEncoder.encode( entry.getKey(), ENCODING ) : entry.getKey() );
			queryString.append( EQUALS_ENCODED );
			queryString.append( encode ? URLEncoder.encode( entry.getValue().toString(), ENCODING ) : entry.getValue().toString() );
			queryString.append( AMPERSAND_ENCODED );
		}

		if( queryString.length() > 0 )
		{
			queryString.setLength( queryString.length() - AMPERSAND_ENCODED.length() );
		}

		return queryString.toString();
	}

	/**
	 * 
	 * @param url
	 * @param parameters
	 * @return HttpURLConnection
	 * @throws IOException
	 */
	public static HttpURLConnection doPostMultipart( final URL url, final Map<String,Object> parameters ) throws IOException
	{
		final int RADIX = 36;
		final String SEPARATOR = "--"; //$NON-NLS-1$
		final String BOUNDARY = Long.toString( random.nextLong(), RADIX );
		final String NEW_LINE = "\r\n"; //$NON-NLS-1$

		BufferedWriter writer = null;

		try
		{
			final HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setRequestMethod( "POST" ); //$NON-NLS-1$
			http.setRequestProperty( "Host", url.getHost() ); //$NON-NLS-1$
			http.setRequestProperty( "Content-Type", "multipart/form-data, boundary=" + BOUNDARY ); //$NON-NLS-1$ //$NON-NLS-2$
			http.setRequestProperty( "Cache-Control", "no-cache" ); //$NON-NLS-1$ //$NON-NLS-2$
			http.setRequestProperty( "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8" ); //$NON-NLS-1$ //$NON-NLS-2$
			http.setDoOutput( true );

			writer = new BufferedWriter( new OutputStreamWriter( http.getOutputStream() ) );

			for( Iterator<Map.Entry<String,Object>> i = parameters.entrySet().iterator(); i.hasNext(); )
			{
				final Map.Entry<String,Object> entry = i.next();
				Object value = null;

				writer.write( SEPARATOR );
				writer.write( BOUNDARY );
				writer.write( NEW_LINE );
				writer.write( "Content-Disposition: form-data; name=\"" ); //$NON-NLS-1$
				writer.write( entry.getKey() );
				writer.write( "\"" ); //$NON-NLS-1$

				if( entry.getValue() instanceof File )
				{
					final File file = (File)entry.getValue();
					writer.write( "; filename=\"" ); //$NON-NLS-1$
					writer.write( file.getName() );
					writer.write( "\"" ); //$NON-NLS-1$
					writer.write( NEW_LINE );
					writer.write( "Content-Type:" ); //$NON-NLS-1$

					String contentType = URLConnection.guessContentTypeFromName( file.getPath() );

					if( contentType == null )
					{
						contentType = "text/plain"; //$NON-NLS-1$
					}

					writer.write( contentType );

					value = new String( FileUtils.read( file.toURI().toURL() ) );
				}
				else
				{
					value = entry.getValue();
				}

				writer.write( NEW_LINE );
				writer.write( NEW_LINE );
				writer.write( value.toString() );
				writer.write( NEW_LINE );
			}

			writer.write( SEPARATOR );
			writer.write( BOUNDARY );
			writer.write( SEPARATOR );
			writer.write( NEW_LINE );
			writer.close();

			return http;
		}
		finally
		{
			if( writer != null )
			{
				writer.close();
			}
		}
	}
}