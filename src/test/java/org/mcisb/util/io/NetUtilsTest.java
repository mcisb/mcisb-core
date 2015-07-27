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
import org.junit.*;

/**
 * 
 *
 * @author Neil Swainston
 */
public class NetUtilsTest
{
	/**
	 *
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test 
	public void doPost() throws IOException
	{
		final URL url = new URL( "http://www.ebi.ac.uk/miriam/main/XMLExport" ); //$NON-NLS-1$
		final Map<String,Object> nameValuePairs = new HashMap<>();
		nameValuePairs.put( "fileName", "MiriamXML" ); //$NON-NLS-1$ //$NON-NLS-2$
		
		try( final InputStream is = NetUtils.doPost( url, nameValuePairs ) )
		{
			final String content = new String( StreamReader.read( is ) );
			Assert.assertTrue( content.contains( "ChEBI" ) ); //$NON-NLS-1$
			is.close();
		}
	}
}