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
import java.nio.charset.*;
import java.util.*;
import javax.xml.bind.*;
import javax.xml.namespace.*;
import javax.xml.parsers.*;
import javax.xml.stream.*;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.events.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.mcisb.util.*;
import org.mcisb.util.io.*;
import org.w3c.dom.*;
import org.w3c.dom.Element;
import org.xml.sax.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class XmlUtils
{
	/**
	 * 
	 */
	public static final int FIRST_VALUE_ONLY = 1;

	/**
     * 
     */
	public static final int ALL_VALUES = 2;

	/**
	 * 
	 * @param xmlIs
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void validateXml( final InputStream xmlIs ) throws SAXException, IOException
	{
		validateXml( xmlIs, SchemaFactory.newInstance( "http://www.w3.org/2001/XMLSchema" ).newSchema() ); //$NON-NLS-1$
	}

	/**
	 * 
	 * @param file
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void validateXml( final File file ) throws SAXException, IOException
	{
		try ( final InputStream is = new FileInputStream( file ) )
		{
			validateXml( is, SchemaFactory.newInstance( "http://www.w3.org/2001/XMLSchema" ).newSchema() ); //$NON-NLS-1$
		}
	}

	/**
	 * 
	 * @param is
	 * @param os
	 * @param elementNames
	 * @throws XMLStreamException
	 * @throws FactoryConfigurationError
	 * @throws UnsupportedEncodingException
	 */
	public static void stripElements( final InputStream is, final OutputStream os, final Collection<String> elementNames ) throws XMLStreamException, UnsupportedEncodingException, FactoryConfigurationError
	{
		final XMLEventReader xmlEventReader = XMLInputFactory.newInstance().createXMLEventReader( new InputStreamReader( is, Charset.defaultCharset().name() ) );
		final XMLEventWriter xmlEventWriter = XMLOutputFactory.newInstance().createXMLEventWriter( os );

		String elementName = null;

		while( xmlEventReader.peek() != null )
		{
			final XMLEvent event = (XMLEvent)xmlEventReader.next();

			switch( event.getEventType() )
			{
				case XMLStreamConstants.START_DOCUMENT:
				case XMLStreamConstants.END_DOCUMENT:
				{
					// Ignore.
					break;
				}
				case XMLStreamConstants.START_ELEMENT:
				{
					final StartElement startElement = event.asStartElement();
					final QName name = startElement.getName();

					if( elementNames.contains( name.getLocalPart() ) )
					{
						elementName = name.getLocalPart();
					}

					if( elementName == null )
					{
						xmlEventWriter.add( event );
					}

					break;
				}
				case XMLStreamConstants.END_ELEMENT:
				{
					final EndElement endElement = event.asEndElement();
					final QName name = endElement.getName();

					if( elementName == null )
					{
						xmlEventWriter.add( event );
					}
					else if( elementName.equals( name.getLocalPart() ) )
					{
						elementName = null;
					}

					break;
				}
				default:
				{
					if( elementName == null )
					{
						xmlEventWriter.add( event );
					}
				}
			}
		}

		xmlEventWriter.flush();
	}

	/**
	 * 
	 * @param elementName
	 * @param is
	 * @return Collection
	 * @throws XMLStreamException
	 * @throws UnsupportedEncodingException
	 */
	public static Collection<String> getElements( final String elementName, final InputStream is ) throws XMLStreamException, UnsupportedEncodingException
	{
		return getElements( elementName, is, false );
	}

	/**
	 * 
	 * @param elementName
	 * @param is
	 * @param onlyValues
	 * @return Collection
	 * @throws XMLStreamException
	 * @throws UnsupportedEncodingException
	 */
	public static Collection<String> getElements( final String elementName, final InputStream is, final boolean onlyValues ) throws XMLStreamException, UnsupportedEncodingException
	{
		final Collection<String> elements = new ArrayList<>();
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		final XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader( new InputStreamReader( is, Charset.defaultCharset().name() ) );
		final XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter( new OutputStreamWriter( os, Charset.defaultCharset().name() ) );
		boolean read = false;
		String characters = null;

		while( reader.peek() != null )
		{
			final XMLEvent event = (XMLEvent)reader.next();

			switch( event.getEventType() )
			{
				case XMLStreamConstants.START_DOCUMENT:
				case XMLStreamConstants.END_DOCUMENT:
				{
					// Ignore.
					break;
				}
				case XMLStreamConstants.START_ELEMENT:
				{
					read = read || elementName.equals( event.asStartElement().getName().getLocalPart() );

					if( read && !onlyValues )
					{
						writer.add( event );
					}

					break;
				}
				case XMLStreamConstants.ATTRIBUTE:
				{
					if( read && !onlyValues )
					{
						writer.add( event );
					}
					break;
				}
				case XMLStreamConstants.CHARACTERS:
				{
					if( read && !onlyValues )
					{
						writer.add( event );
					}
					characters = event.asCharacters().getData();
					break;
				}
				case XMLStreamConstants.END_ELEMENT:
				{
					if( read && !onlyValues )
					{
						writer.add( event );
					}
					if( elementName.equals( event.asEndElement().getName().getLocalPart() ) )
					{
						writer.flush();

						if( characters != null )
						{
							elements.add( characters );
						}

						os.reset();
						read = false;
					}
					break;
				}
				default:
				{
					// Ignore
					break;
				}
			}
		}

		return elements;
	}

	/**
	 * 
	 * @param elementName
	 * @param attributeValue
	 * @param is
	 * @return Collection
	 * @throws XMLStreamException
	 * @throws FactoryConfigurationError
	 * @throws UnsupportedEncodingException
	 */
	public static Collection<String> getElementValues( final String elementName, final String attributeValue, final InputStream is ) throws XMLStreamException, UnsupportedEncodingException, FactoryConfigurationError
	{
		final Collection<String> elementValues = new ArrayList<>();
		final XMLEventReader xmlEventReader = XMLInputFactory.newInstance().createXMLEventReader( new InputStreamReader( is, Charset.defaultCharset().name() ) );
		final StringBuffer characters = new StringBuffer();
		boolean read = false;

		while( xmlEventReader.peek() != null )
		{
			final XMLEvent event = (XMLEvent)xmlEventReader.next();

			switch( event.getEventType() )
			{
				case XMLStreamConstants.START_DOCUMENT:
				case XMLStreamConstants.END_DOCUMENT:
				{
					// Ignore.
					break;
				}
				case XMLStreamConstants.START_ELEMENT:
				{
					read = elementName.equals( event.asStartElement().getName().getLocalPart() );

					if( read && attributeValue != null )
					{
						read = false;

						for( @SuppressWarnings("unchecked")
						Iterator<Attribute> iterator = event.asStartElement().getAttributes(); iterator.hasNext(); )
						{
							Attribute attribute = iterator.next();

							if( attribute.getValue().equals( attributeValue ) )
							{
								read = true;
								break;
							}
						}
					}

					break;
				}
				case XMLStreamConstants.CHARACTERS:
				{
					if( read )
					{
						characters.append( event.asCharacters().getData() );
					}
					break;
				}
				case XMLStreamConstants.END_ELEMENT:
				{
					if( read )
					{
						elementValues.add( characters.toString() );
						characters.setLength( 0 );
					}

					read = false;
					break;
				}
				default:
				{
					// Ignore
					break;
				}
			}
		}

		return elementValues;
	}

	/**
	 * 
	 * 
	 * @param xml
	 * @return Node
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static Node getNode( final String xml ) throws IOException, SAXException, ParserConfigurationException
	{
		final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( new ByteArrayInputStream( xml.getBytes( Charset.defaultCharset().name() ) ) );
		return document.getFirstChild();
	}

	/**
	 * 
	 * @param element
	 * @param nodeName
	 * @return Element
	 */
	public static Element getFirstElement( final Element element, final String nodeName )
	{
		final NodeList list = element.getElementsByTagName( nodeName );

		if( list.getLength() == 0 )
		{
			return null;
		}

		return (Element)list.item( 0 );
	}

	/**
	 * 
	 * @param element
	 * @param nodeName
	 * @return String
	 */
	public static String getSimpleElementText( final Element element, final String nodeName )
	{
		final Element namedElement = getFirstElement( element, nodeName );

		if( namedElement != null )
		{
			return getSimpleElementText( namedElement );
		}

		return null;
	}

	/**
	 * 
	 * @param node
	 * @return String
	 */
	public static String getSimpleElementText( final Node node )
	{
		final NodeList children = node.getChildNodes();

		for( int i = 0; i < children.getLength(); i++ )
		{
			final Node child = children.item( i );

			if( child instanceof Text )
			{
				return child.getNodeValue();
			}
		}

		return null;
	}

	/**
	 * 
	 * @param element
	 * @param tagname
	 * @param valueRange
	 * @return List
	 */
	public static List<String> getDOMElementTextByTagName( final Element element, String tagname, final int valueRange )
	{
		final List<String> list = new ArrayList<>();

		if( element != null )
		{
			// Just to ensure we pick off by the name of the child node...
			final NodeList nodeList = element.getElementsByTagName( tagname.substring( tagname.lastIndexOf( "/" ) + 1 ) ); //$NON-NLS-1$

			if( nodeList.getLength() == 0 )
			{
				list.add( null );
			}

			for( int i = 0; i < ( ( valueRange == ALL_VALUES ) ? nodeList.getLength() : 1 ); i++ )
			{
				final Node node = nodeList.item( i );

				if( node != null )
				{
					final Node text = node.getFirstChild();
					list.add( text.getNodeValue() );
				}
			}
		}
		else
		{
			list.add( null );
		}

		return list;
	}

	/**
	 * 
	 * @param element
	 * @param identifyingProperties
	 * @return Map
	 */
	public static Map<String,String> getResult( final Element element, final Collection<String> identifyingProperties )
	{
		// for each identifying property get that property value for this
		// document
		final String SEPARATOR = "; "; //$NON-NLS-1$
		final Map<String,String> result = new TreeMap<>();

		for( Iterator<String> identifyingPropertiesIterator = identifyingProperties.iterator(); identifyingPropertiesIterator.hasNext(); )
		{
			final String identifyingProperty = identifyingPropertiesIterator.next();
			final List<String> newValues = getDOMElementTextByTagName( element, identifyingProperty, ALL_VALUES );
			final String existingValue = result.get( identifyingProperty );

			if( existingValue != null )
			{
				newValues.add( existingValue );
			}

			final StringBuffer buffer = new StringBuffer();

			boolean needsSeperator = false;

			for( Iterator<String> newValuesIterator = newValues.iterator(); newValuesIterator.hasNext(); )
			{
				if( needsSeperator )
				{
					buffer.append( SEPARATOR );
				}

				final String value = newValuesIterator.next();

				if( value != null )
				{
					buffer.append( value );
				}

				needsSeperator = true;
			}

			result.put( identifyingProperty, buffer.toString() );
		}

		return result;
	}

	/**
	 * 
	 * @param elementName
	 * @param is
	 * @param contextPath
	 * @param template
	 * @return List<?>
	 * @throws JAXBException
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	public static List<?> unmarshallList( final String elementName, final InputStream is, final String contextPath, final Class<?> template ) throws JAXBException, XMLStreamException, IOException
	{
		final List<Object> unmarshalledList = new ArrayList<>();
		final Unmarshaller unmarshaller = JAXBContext.newInstance( contextPath ).createUnmarshaller();

		// Original input must be prefixed and suffixed with start and end tags,
		// as document must be valid for getElements method:
		final String uniqueId = StringUtils.getUniqueId();
		final StringBuffer buffer = new StringBuffer( "<" + uniqueId + ">" ); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append( new String( StreamReader.read( is ), Charset.defaultCharset().name() ) );
		buffer.append( "</" + uniqueId + ">" ); //$NON-NLS-1$ //$NON-NLS-2$

		for( final String element : getElements( elementName, new ByteArrayInputStream( buffer.toString().getBytes( Charset.defaultCharset() ) ) ) )
		{
			unmarshalledList.add( unmarshaller.unmarshal( new StreamSource( new ByteArrayInputStream( element.getBytes( Charset.defaultCharset().name() ) ) ), template ).getValue() );
		}

		return unmarshalledList;
	}

	/**
	 * 
	 * @param contextPath
	 * @return Marshaller
	 * @throws JAXBException
	 */
	public static Marshaller getMarshaller( final String contextPath ) throws JAXBException
	{
		final JAXBContext jaxbContext = JAXBContext.newInstance( contextPath );
		final Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty( Marshaller.JAXB_FRAGMENT, Boolean.TRUE );
		marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		return marshaller;
	}

	/**
	 * 
	 * @param tagged
	 * @return String
	 */
	public static String stripTags( final String tagged )
	{
		final StringBuffer stripped = new StringBuffer();
		boolean inTag = false;

		for( int i = 0; i < tagged.length(); i++ )
		{
			final char c = tagged.charAt( i );

			if( c == '<' )
			{
				inTag = true;
				continue;
			}

			if( c == '>' )
			{
				inTag = false;
				continue;
			}

			if( inTag )
			{
				continue;
			}

			stripped.append( c );
		}

		return stripped.toString().trim();
	}

	/**
	 * 
	 * @param str
	 * @return String
	 */
	public static String encode( final String str )
	{
		StringBuffer encoded = new StringBuffer();

		for( int i = 0; i < str.length(); i++ )
		{
			char c = str.charAt( i );

			if( c == '<' )
			{
				encoded.append( "&lt;" ); //$NON-NLS-1$
			}
			else if( c == '\"' )
			{
				encoded.append( "&quot;" ); //$NON-NLS-1$
			}
			else if( c == '>' )
			{
				encoded.append( "&gt;" ); //$NON-NLS-1$
			}
			else if( c == '\'' )
			{
				encoded.append( "&apos;" ); //$NON-NLS-1$
			}
			else if( c == '&' )
			{
				encoded.append( "&amp;" ); //$NON-NLS-1$
			}
			else
			{
				encoded.append( c );
			}
		}

		return encoded.toString();
	}

	/**
	 * 
	 * @param xmlIs
	 * @param schema
	 * @throws SAXException
	 * @throws IOException
	 */
	private static void validateXml( final InputStream xmlIs, final Schema schema ) throws SAXException, IOException
	{
		schema.newValidator().validate( new StreamSource( xmlIs ) );
	}
}