/**
 * 
 */
package org.mcisb.util.io;

import java.io.*;
import java.util.*;

/**
 * @author neilswainston
 *
 */
public class RestStringReader extends RestReader
{
	/**
	 * @param url
	 * @param parameters
	 */
	public RestStringReader( final String url, final Map<String,String> parameters )
	{
		super( url, parameters );
	}

	/* 
	 * (non-Javadoc)
	 * @see org.mcisb.gwt.server.RestReader#processInputStream(java.io.InputStream)
	 */
	@Override
	protected Object processInputStream( final InputStream is ) throws IOException
	{
		try( InputStreamReader reader = new InputStreamReader( is ) )
		{
			final int BUFFER_SIZE = 1024 * 8;
			final char[] buffer = new char[ BUFFER_SIZE ];
			final StringBuilder stringBuilder = new StringBuilder();
			int read = -1;
			
			while( ( read = reader.read( buffer ) ) != -1 )
			{
				stringBuilder.append( buffer, 0, read );
			}
			
			return stringBuilder.toString();
		}
	}
}