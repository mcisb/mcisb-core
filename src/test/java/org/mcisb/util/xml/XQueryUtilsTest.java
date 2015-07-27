/**
 * 
 */
package org.mcisb.util.xml;

import java.io.*;
import net.sf.saxon.s9api.*;
import org.junit.*;

/**
 * 
 * @author Neil Swainston
 */
public class XQueryUtilsTest
{
	/**
	 * 
	 * @throws SaxonApiException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void runXQuery() throws SaxonApiException, IOException
	{
		final String query = "declare variable $params external;\n" + //$NON-NLS-1$
		        "let $result := for $param in $params/params/param\n" + //$NON-NLS-1$
		        "return <echo>{$param/string()}</echo>" + //$NON-NLS-1$
		        "return <echoes>{$result}</echoes>"; //$NON-NLS-1$
		    
		final XQueryUtils xq = new XQueryUtils();
		final String result = xq.runXQuery( "params", "<params><param>abc</param><param>123</param></params>", query ).toString(); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertTrue( result.contains( "<echo>abc</echo>" ) ); //$NON-NLS-1$
	}
}