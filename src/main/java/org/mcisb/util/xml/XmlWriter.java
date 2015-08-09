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

import java.util.*;
import javax.xml.*;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import org.apache.commons.lang3.*;

/**
 * 
 * @author Neil Swainston
 * 
 */
public class XmlWriter
{
	/**
	 *
	 */
	private final static String WHITE_SPACE = "[\\s]*"; //$NON-NLS-1$

	/**
	 * 
	 */
	private final static String LINE_SEPARATOR = System.getProperty( "line.separator" ); //$NON-NLS-1$

	/**
	 * 
	 */
	private final XMLEventFactory eventFactory = XMLEventFactory.newInstance();

	/**
	 * 
	 */
	private final XMLEventWriter writer;

	/**
	 * 
	 * @param writer
	 */
	public XmlWriter( final XMLEventWriter writer )
	{
		this.writer = writer;
	}

	/**
	 * 
	 * @throws XMLStreamException
	 */
	public void writeStartDocument() throws XMLStreamException
	{
		writer.add( eventFactory.createStartDocument() );
		flush();
	}

	/**
	 * 
	 * @param prefix
	 * @param namespaceUri
	 * @throws XMLStreamException
	 */
	public void writeNamespace( final String prefix, final String namespaceUri ) throws XMLStreamException
	{
		writer.add( eventFactory.createNamespace( prefix, namespaceUri ) );
		flush();
	}

	/**
	 * 
	 * @throws XMLStreamException
	 */
	public void writeEndDocument() throws XMLStreamException
	{
		writer.add( eventFactory.createEndDocument() );
		flush();
	}

	/**
	 * 
	 * @param name
	 * @throws XMLStreamException
	 */
	public void writeStartElement( final String name ) throws XMLStreamException
	{
		writeStartElement( name, new HashMap<String,String>() );
	}

	/**
	 * 
	 * @param characters
	 * @throws XMLStreamException
	 */
	public void writeCharacters( final String characters ) throws XMLStreamException
	{
		writeCharacters( eventFactory.createCharacters( StringEscapeUtils.escapeXml10( characters ) ) );
	}

	/**
	 * 
	 * @param characters
	 * @throws XMLStreamException
	 */
	public void writeCharacters( final Characters characters ) throws XMLStreamException
	{
		writer.add( characters );
		flush();
	}

	/**
	 * 
	 * @param name
	 * @param value
	 * @throws XMLStreamException
	 */
	public void writeAttribute( final String name, final String value ) throws XMLStreamException
	{
		writer.add( eventFactory.createAttribute( name, value ) );
		flush();
	}

	/**
	 * 
	 * @param name
	 * @throws XMLStreamException
	 */
	public void writeEndElement( final String name ) throws XMLStreamException
	{
		writeEndElement( XMLConstants.DEFAULT_NS_PREFIX, XMLConstants.NULL_NS_URI, name );
	}

	/**
	 * 
	 * @param name
	 * @param attributesMap
	 * @throws XMLStreamException
	 */
	protected void writeStartElement( final String name, final Map<String,String> attributesMap ) throws XMLStreamException
	{
		writeStartElement( XMLConstants.DEFAULT_NS_PREFIX, XMLConstants.NULL_NS_URI, name, attributesMap );
	}

	/**
	 * 
	 * @param prefix
	 * @param namespaceUri
	 * @param localName
	 * @throws XMLStreamException
	 */
	protected void writeStartElement( final String prefix, final String namespaceUri, final String localName ) throws XMLStreamException
	{
		writeStartElement( prefix, namespaceUri, localName, new HashMap<String,String>() );
	}

	/**
	 * 
	 * @param prefix
	 * @param namespaceUri
	 * @param localName
	 * @param attributesMap
	 * @throws XMLStreamException
	 */
	protected void writeStartElement( final String prefix, final String namespaceUri, final String localName, final Map<String,String> attributesMap ) throws XMLStreamException
	{
		writeStartElement( eventFactory.createStartElement( prefix, namespaceUri, localName, getAttributes( attributesMap ), null ) );
	}

	/**
	 * 
	 * 
	 * @param event
	 * @throws XMLStreamException
	 */
	protected void writeStartElement( final XMLEvent event ) throws XMLStreamException
	{
		writer.add( event );
		flush();
	}

	/**
	 * 
	 * @param attributesMap
	 * @return Iterator
	 */
	protected Iterator<Attribute> getAttributes( final Map<String,String> attributesMap )
	{
		final Collection<Attribute> attributes = new ArrayList<>();

		for( Iterator<Map.Entry<String,String>> iterator = attributesMap.entrySet().iterator(); iterator.hasNext(); )
		{
			final Map.Entry<String,String> attribute = iterator.next();
			attributes.add( eventFactory.createAttribute( attribute.getKey(), attribute.getValue() ) );
		}

		return attributes.iterator();
	}

	/**
	 * 
	 * @param prefix
	 * @param namespaceUri
	 * @param localName
	 * @throws XMLStreamException
	 */
	protected void writeEndElement( final String prefix, final String namespaceUri, final String localName ) throws XMLStreamException
	{
		writeEndElement( eventFactory.createEndElement( prefix, namespaceUri, localName ) );
	}

	/**
	 * 
	 * @param endElement
	 * @throws XMLStreamException
	 */
	protected void writeEndElement( final XMLEvent endElement ) throws XMLStreamException
	{
		writer.add( endElement );
		flush();
	}

	/**
	 * 
	 * @throws XMLStreamException
	 */
	protected void writeNewLine() throws XMLStreamException
	{
		writeCharacters( LINE_SEPARATOR );
	}

	/**
	 * 
	 * @param events
	 * @throws XMLStreamException
	 */
	protected void write( final Collection<XMLEvent> events ) throws XMLStreamException
	{
		for( Iterator<XMLEvent> iterator = events.iterator(); iterator.hasNext(); )
		{
			write( iterator.next() );
		}
	}

	/**
	 * 
	 * 
	 * @param event
	 * @throws XMLStreamException
	 */
	protected void write( final XMLEvent event ) throws XMLStreamException
	{
		if( event.isStartDocument() )
		{
			writeStartDocument();
		}
		else if( event.isEndDocument() )
		{
			writeEndDocument();
		}
		else if( event.isStartElement() )
		{
			writeStartElement( event.asStartElement() );
		}
		else if( event.isEndElement() )
		{
			writeEndElement( event.asEndElement() );
		}
		else if( event.isCharacters() )
		{
			if( event.asCharacters().getData().matches( WHITE_SPACE ) )
			{
				// Ignore...
				return;
			}

			writeCharacters( event.asCharacters() );
		}
	}

	/**
	 * 
	 * @throws XMLStreamException
	 */
	protected void flush() throws XMLStreamException
	{
		writer.flush();
	}

	/**
	 * 
	 * @throws XMLStreamException
	 */
	public void close() throws XMLStreamException
	{
		writer.close();
	}
}