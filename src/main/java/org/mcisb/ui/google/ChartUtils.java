/*******************************************************************************
 * Manchester Centre for Integrative Systems Biology
 * University of Manchester
 * Manchester M1 7ND
 * United Kingdom
 * 
 * Copyright (C) 2008 University of Manchester
 * 
 * This program is released under the Academic Free License ("AFL") v3.0.
 * (http://www.opensource.org/licenses/academic.php)
 *******************************************************************************/
package org.mcisb.ui.google;

import java.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class ChartUtils
{
	/**
	 * 
	 * @param nameValuePairs
	 * @return String
	 */
	public static String getSrc( final Map<String,String> nameValuePairs )
	{
		final StringBuffer srcBuffer = new StringBuffer();
		srcBuffer.append( "<html xmlns=\"http://www.w3.org/1999/xhtml\">" ); //$NON-NLS-1$
		srcBuffer.append( "<head>" ); //$NON-NLS-1$
		srcBuffer.append( "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>" ); //$NON-NLS-1$
		srcBuffer.append( "<script type=\"application/javascript\">" ); //$NON-NLS-1$
		srcBuffer.append( "function loadGraph()" ); //$NON-NLS-1$
		srcBuffer.append( "{" ); //$NON-NLS-1$
		srcBuffer.append( "		var frm = document.getElementById('post_form');" ); //$NON-NLS-1$
		srcBuffer.append( "		if( frm )" ); //$NON-NLS-1$
		srcBuffer.append( "		{" ); //$NON-NLS-1$
		srcBuffer.append( "			frm.submit();" ); //$NON-NLS-1$
		srcBuffer.append( "		}" ); //$NON-NLS-1$
		srcBuffer.append( "}" ); //$NON-NLS-1$
		srcBuffer.append( "</script>" ); //$NON-NLS-1$
		srcBuffer.append( "</head>" ); //$NON-NLS-1$
		srcBuffer.append( "<body onload=\"loadGraph()\">" ); //$NON-NLS-1$
		srcBuffer.append( "<form action=\"http://chart.apis.google.com/chart\" method=\"POST\" id=\"post_form\">" ); //$NON-NLS-1$

		for( Map.Entry<String,String> entry : nameValuePairs.entrySet() )
		{
			srcBuffer.append( "<input type=\"hidden\" name=\"" + entry.getKey() + "\" value=\"" + entry.getValue() + "\"/>" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		// srcBuffer.append( "	<input type=\"submit\" value=\"Please wait...\"/>" ); //$NON-NLS-1$
		srcBuffer.append( "</form>" ); //$NON-NLS-1$
		srcBuffer.append( "</body>" ); //$NON-NLS-1$
		srcBuffer.append( "</html>" ); //$NON-NLS-1$

		return srcBuffer.toString();
	}
}