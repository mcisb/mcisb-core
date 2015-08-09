/**
 * 
 */
package org.mcisb.util.xml;

import java.io.*;
import java.nio.charset.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import net.sf.saxon.s9api.*;

/**
 * 
 * @author Neil Swainston
 */
public class XQueryUtils
{
	/**
     * 
     */
	private final Processor processor = new Processor( false );

	/**
     * 
     */
	private final XQueryCompiler xQueryCompiler = processor.newXQueryCompiler();

	/**
	 * 
	 * @param name
	 * @param xml
	 * @param query
	 * @return XdmValue
	 * @throws SaxonApiException
	 * @throws IOException
	 */
	public XdmValue runXQuery( final String name, final String xml, final String query ) throws SaxonApiException, IOException
	{
		return runXQuery( name, getXdmNode( xml ), getXQueryEvaluator( query ) );
	}

	/**
	 * 
	 * @param name
	 * @param xml
	 * @param query
	 * @return XdmValue
	 * @throws SaxonApiException
	 */
	public XdmValue runXQuery( final String name, final InputStream xml, final String query ) throws SaxonApiException
	{
		return runXQuery( name, getXdmNode( xml ), getXQueryEvaluator( query ) );
	}

	/**
	 * 
	 * @param name
	 * @param xml
	 * @param query
	 * @return XdmValue
	 * @throws SaxonApiException
	 */
	public XdmValue runXQuery( final String name, final XdmNode xml, final String query ) throws SaxonApiException
	{
		return runXQuery( name, xml, getXQueryEvaluator( query ) );
	}

	/**
	 * 
	 * @param xml
	 * @return XdmNode
	 * @throws SaxonApiException
	 * @throws IOException
	 */
	public XdmNode getXdmNode( final String xml ) throws SaxonApiException, IOException
	{
		InputStream is = null;

		try
		{
			is = new ByteArrayInputStream( xml.getBytes( Charset.defaultCharset() ) );
			XdmNode node = getXdmNode( is );
			is.close();
			return node;
		}
		finally
		{
			if( is != null )
			{
				is.close();
			}
		}
	}

	/**
	 * 
	 * @param is
	 * @return XdmNode
	 * @throws SaxonApiException
	 */
	public XdmNode getXdmNode( final InputStream is ) throws SaxonApiException
	{
		final Source source = new StreamSource( is );
		return processor.newDocumentBuilder().build( source );
	}

	/**
	 * 
	 * @param query
	 * @return XQueryEvaluator
	 * @throws SaxonApiException
	 */
	public XQueryEvaluator getXQueryEvaluator( final String query ) throws SaxonApiException
	{
		return xQueryCompiler.compile( query ).load();
	}

	/**
	 * 
	 * @param node
	 * @param xQueryEvaluator
	 * @return XdmValue
	 * @throws SaxonApiException
	 */
	private static XdmValue runXQuery( final String name, final XdmNode node, final XQueryEvaluator xQueryEvaluator ) throws SaxonApiException
	{
		xQueryEvaluator.setExternalVariable( new QName( name ), node );
		return xQueryEvaluator.evaluate();
	}
}
