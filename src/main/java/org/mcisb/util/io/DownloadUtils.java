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
public class DownloadUtils
{
	/**
	 * 
	 */
	private final URL remoteUrl;

	/**
	 * 
	 */
	private final Map<String,Object> nameValuePairs;

	/**
	 * 
	 * @param remoteUrl
	 * @param nameValuePairs
	 */
	public DownloadUtils( final URL remoteUrl, final Map<String,Object> nameValuePairs )
	{
		this.remoteUrl = remoteUrl;
		this.nameValuePairs = nameValuePairs;
	}

	/**
	 * 
	 * @return String
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public InputStream getFileContents() throws IOException
	{
		InputStream is = null;

		if( nameValuePairs != null )
		{
			is = NetUtils.doPost( remoteUrl, nameValuePairs );
		}
		else
		{
			is = remoteUrl.openStream();
		}

		return is;
	}
}