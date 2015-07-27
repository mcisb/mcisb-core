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
package org.mcisb.util.net;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;

/**
 * 
 * @author Neil Swainston
 */
public class BrowserLauncher
{
	/**
	 * 
	 * @param url
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public static void openURL( URL url )  throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException
	{
		final String osName = System.getProperty( "os.name" ); //$NON-NLS-1$

		if( osName.startsWith( "Mac OS" ) ) //$NON-NLS-1$
		{
            final Class<?> fileManager = Class.forName( "com.apple.eio.FileManager" ); //$NON-NLS-1$
            final Method openURL = fileManager.getDeclaredMethod( "openURL", new Class[] { String.class } ); //$NON-NLS-1$
            openURL.invoke( null, new Object[] { url.toString() } );
        }
		else if( osName.startsWith( "Windows" ) ) //$NON-NLS-1$
		{
            Runtime.getRuntime().exec( "rundll32 url.dll,FileProtocolHandler " + url ); //$NON-NLS-1$
		}
		else //assume Unix or Linux
		{ 
            final String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
            
            for( int i = 0; i < browsers.length; i++ )
            {
            	try
            	{
	            	Runtime.getRuntime().exec( new String[] { browsers[ i ], url.toString() } );
            	}
            	catch( IOException e )
            	{
            		if( i == browsers.length - 1 )
            		{
            			throw e;
            		}
            	}
            }
		}
	}
	
	/**
	 * 
	 * @param args
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public static void main( String[] args ) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException
	{
		BrowserLauncher.openURL( new URL( "mailto:neil.swainston@manchester.ac.uk?subject=Error&body=org.xml.sax.SAXParseException" ) ); //$NON-NLS-1$
	}
}
