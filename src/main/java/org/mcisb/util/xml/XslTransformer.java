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
package org.mcisb.util.xml;

import java.io.*;
import java.util.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class XslTransformer
{
	/**
	 * @param xsl
	 * @param xml
	 * @param os
	 * @throws TransformerException
	 * @throws IOException
	 */
	public static void transform( final File xsl, final File xml, final OutputStream os ) throws TransformerException, IOException
	{
		try ( final InputStream xslInputStream = new FileInputStream( xsl ); final InputStream xmlInputStream = new FileInputStream( xml ) )
		{
			transform( xslInputStream, xmlInputStream, os, null );
		}
	}

	/**
	 * 
	 * @param xslInputStream
	 * @param xmlInputStream
	 * @param outputStream
	 * @param parameters
	 * @throws TransformerException
	 */
	public static void transform( final InputStream xslInputStream, final InputStream xmlInputStream, final OutputStream outputStream, final Map<String,Object> parameters ) throws TransformerException
	{
		final Source xslSource = new StreamSource( xslInputStream );
		final Source xmlSource = new StreamSource( xmlInputStream );
		final Result outputTarget = new StreamResult( outputStream );
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();

		final Transformer transformer = transformerFactory.newTransformer( xslSource );

		if( parameters != null )
		{
			for( Iterator<Map.Entry<String,Object>> iterator = parameters.entrySet().iterator(); iterator.hasNext(); )
			{
				final Map.Entry<String,Object> entry = iterator.next();
				final Object value = entry.getValue();
				final Object parameterValue = ( value instanceof String ) ? value : ( ( value instanceof String[] ) ? CollectionUtils.getFirst( (String[])value ) : value );
				transformer.setParameter( entry.getKey(), parameterValue );
			}
		}

		transformer.transform( xmlSource, outputTarget );
	}
}