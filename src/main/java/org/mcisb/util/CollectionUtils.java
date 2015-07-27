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
package org.mcisb.util;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.stream.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/**
 * 
 * @author Neil Swainston
 */
public class CollectionUtils
{
	/**
	 * 
	 */
	public static final String SCHEMA_LOCATION = "http://www.mcisb.org/software/schema/map.xsd"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	public static final String ELEMENT_SEPARATOR = ",(\\s)*"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private static final String ARRAY_SEPARATOR = ";"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private static final String ARRAY_OPEN_TAG = "["; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private static final String ARRAY_CLOSE_TAG = "]"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private static final String KEY = "key"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private static final String VALUE = "value"; //$NON-NLS-1$
	
	/**
	 *
	 */
	private static final String ARRAY = "array"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private static final int FIRST = 0;
	
	/**
	 * 
	 * @param ints
	 * @return int
	 */
	public static int getFirst( int[] ints )
	{
		return ( ints == null || ints.length == 0 ) ? NumberUtils.UNDEFINED : ints[ FIRST ];
	}
	
	/**
	 * 
	 * @param <T>
	 * @param objects
	 * @return Object
	 */
	public static <T> T getFirst( final Collection<T> objects )
	{
		return (T)getFirst( objects.toArray() );
	}
	
	/**
	 * 
	 * @param <T>
	 * @param objects
	 * @return Object
	 */
	public static <T> T getFirst( final T[] objects )
	{
		return ( objects == null || objects.length == 0 ) ? null : objects[ FIRST ];
	}
	
 	/**
	 * 
	 * @param collections
	 * @return Collection
	 */
	public static Collection<?> getIntersection( final Collection<? extends Collection<? extends Object>> collections )
	{
		final List<Collection<?>> collectionsList = new ArrayList<Collection<?>>( collections );
		final Collection<Object> intersection = new HashSet<>();
		
		if( collectionsList.size() != 0 )
		{
			intersection.addAll( collectionsList.get( 0 ) );
		}
		
		for( int i = 1; i < collections.size() && intersection.size() > 0; i++ )
		{
			intersection.retainAll( new HashSet<>( collectionsList.get( i ) ) );
		}
		
		return intersection;
	}
	
	/**
	 * 
	 * @param value
	 * @return Object[]
	 */
	public static Object[] toArray( final String value )
	{
		if( value == null )
		{
			return new Object[] {};
		}
		
		final int BRACKET_LENGTH = 1;
		final String strippedValue = value.substring( BRACKET_LENGTH, value.length() - BRACKET_LENGTH );
		final StringTokenizer tokenizer = new StringTokenizer( strippedValue, ELEMENT_SEPARATOR );
		final Collection<Object> collection = new ArrayList<>();
		
		while( tokenizer.hasMoreTokens() )
		{
			collection.add( tokenizer.nextToken().trim() );
		}
		
		return collection.toArray();
	}
	
	/**
	 * 
	 * @param value
	 * @return int[]
	 */
	public static int[] toIntArray( final String value )
	{
		if( value == null )
		{
			return new int[] {};
		}
		
		final String strippedValue = value.replace( ARRAY_OPEN_TAG, EMPTY_STRING ).replace( ARRAY_CLOSE_TAG, EMPTY_STRING );
		final StringTokenizer tokenizer = new StringTokenizer( strippedValue, ELEMENT_SEPARATOR );
		final Collection<Integer> intCollection = new ArrayList<>();
		
		while( tokenizer.hasMoreTokens() )
		{
			intCollection.add( Integer.valueOf( tokenizer.nextToken().trim() ) );
		}
		
		return toIntArray( intCollection );
	}
	
	/**
	 * 
	 * @param value
	 * @return boolean[]
	 */
	public static boolean[] toBooleanArray( final String value )
	{
		if( value == null )
		{
			return new boolean[] {};
		}
		
		final String strippedValue = value.replace( ARRAY_OPEN_TAG, EMPTY_STRING ).replace( ARRAY_CLOSE_TAG, EMPTY_STRING );
		final StringTokenizer tokenizer = new StringTokenizer( strippedValue, ELEMENT_SEPARATOR );
		final Collection<Boolean> intCollection = new ArrayList<>();
		
		while( tokenizer.hasMoreTokens() )
		{
			intCollection.add( Boolean.valueOf( tokenizer.nextToken().trim() ) );
		}
		
		return toBooleanArray( intCollection );
	}
	
	/**
	 * 
	 * @param value
	 * @return double[][]
	 */
	public static double[][] to2dDoubleArray( final String value )
	{
		final String strippedValue = value.replace( ARRAY_OPEN_TAG, EMPTY_STRING ).replace( ARRAY_CLOSE_TAG, EMPTY_STRING );
		final StringTokenizer tokenizer = new StringTokenizer( strippedValue, ARRAY_SEPARATOR );
		final double[][] array = new double[ tokenizer.countTokens() ][];
		int i = 0;
		
		while( tokenizer.hasMoreTokens() )
		{
			array[ i++ ] = toDoubleArray( tokenizer.nextToken() );
		}
		
		return array;
	}
	
	/**
	 * 
	 * @param value
	 * @return double[]
	 */
	public static double[] toDoubleArray( final String value )
	{
		if( value == null )
		{
			return new double[] {};
		}
		
		final int BRACKET_LENGTH = 1;
		final String strippedValue = value.substring( BRACKET_LENGTH, value.length() - BRACKET_LENGTH );
		final StringTokenizer tokenizer = new StringTokenizer( strippedValue, ELEMENT_SEPARATOR );
		final Collection<Double> doubleCollection = new ArrayList<>();
		
		while( tokenizer.hasMoreTokens() )
		{
			doubleCollection.add( Double.valueOf( tokenizer.nextToken().trim() ) );
		}
		
		return toDoubleArray( doubleCollection );
	}
	
	/**
	 * 
	 * @param stringArray
	 * @return double[]
	 */
	public static double[] toDoubleArray( final String[] stringArray )
	{
		final Collection<Double> doubleCollection = new ArrayList<>();
		
		for( String value : stringArray )
		{
			doubleCollection.add( Double.valueOf( value ) );
		}
		
		return toDoubleArray( doubleCollection );
	}
	
	/**
	 * 
	 * @param collection
	 * @return int[]
	 */
	public static boolean[] toBooleanArray( final Collection<Boolean> collection )
	{
		final boolean[] array = new boolean[ collection == null ? 0 : collection.size() ];
		
		if( collection != null )
		{
			int i = 0;
			
			for( Iterator<Boolean> iterator = collection.iterator(); iterator.hasNext(); )
			{
				array[ i++ ] = iterator.next().booleanValue();
			}
		}
		
		return array;
	}
	
	/**
	 * 
	 * @param collection
	 * @return int[]
	 */
	public static int[] toIntArray( final Collection<Integer> collection )
	{
		final int[] array = new int[ collection == null ? 0 : collection.size() ];
		
		if( collection != null )
		{
			int i = 0;
			
			for( Iterator<Integer> iterator = collection.iterator(); iterator.hasNext(); )
			{
				array[ i++ ] = iterator.next().intValue();
			}
		}
		
		return array;
	}
	
	/**
	 * 
	 * @param array
	 * @return List<Integer>
	 */
	public static List<Integer> toList( final int[] array )
	{
		final List<Integer> list = new ArrayList<>();
		
		for( final int value : array )
		{
			list.add( Integer.valueOf( value ) );
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param array
	 * @return List<Double>
	 */
	public static List<Double> toList( final double[] array )
	{
		final List<Double> list = new ArrayList<>();
		
		for( final double value : array )
		{
			list.add( Double.valueOf( value ) );
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param doubles
	 * @return float[]
	 */
	public static float[] toFloatArray( final double[] doubles )
	{
		final float[] floats = new float[ doubles.length ];
		
		for( int i = 0; i < doubles.length; i++ )
		{
			floats[ i ] = (float)doubles[ i ];
		}
		
		return floats;
	}
	
	/**
	 * 
	 * @param collection
	 * @return float[]
	 */
	public static float[] toFloatArray( final Collection<Float> collection )
	{
		final float[] array = new float[ collection == null ? 0 : collection.size() ];
		
		if( collection != null )
		{
			int i = 0;
			
			for( Iterator<Float> iterator = collection.iterator(); iterator.hasNext(); )
			{
				array[ i++ ] = iterator.next().floatValue();
			}
		}
		
		return array;
	}
	
	/**
	 * 
	 * @param doubleArray
	 * @return double[]
	 */
	public static double[] toDoubleArray( final Double[] doubleArray )
	{
		return toDoubleArray( Arrays.asList( doubleArray ) );
	}
	
	/**
	 * 
	 * @param collection
	 * @return double[]
	 */
	public static double[] toDoubleArray( final Collection<Double> collection )
	{
		final double[] array = new double[ collection == null ? 0 : collection.size() ];
		
		if( collection != null )
		{
			int i = 0;
			
			for( Iterator<Double> iterator = collection.iterator(); iterator.hasNext(); )
			{
				array[ i++ ] = iterator.next().doubleValue();
			}
		}
		
		return array;
	}
	
	/**
	 * 
	 * @param collection
	 * @return double[][]
	 */
	public static double[][] to2dDoubleArray( final List<double[]> collection )
	{
		final double[][] array = new double[ collection.size() ][];
		int i = 0;
		
		for( Iterator<double[]> iterator = collection.iterator(); iterator.hasNext(); )
		{
			array[ i++ ] = iterator.next();
		}
		
		return array;
	}
	
	/**
	 * 
	 * @param array
	 * @return String
	 */
	public static String toString( double[][] array )
	{
		final StringBuffer buffer = new StringBuffer( ARRAY_OPEN_TAG );
		
		for( int i = 0; i < array.length; i++ )
		{
			final StringBuffer tempBuffer = new StringBuffer( Arrays.toString( array[ i ] ) );
			buffer.append( tempBuffer.substring( 1, tempBuffer.length() - 1 ) );
			buffer.append( ARRAY_SEPARATOR );
		}
		
		buffer.append( ARRAY_CLOSE_TAG );
		return buffer.toString();
	}
	
	/**
	 *
	 * @param src
	 * @return double[][]
	 */
	public static double[][] copy( final double[][] src )
	{
		final double[][] copy = new double[ src.length ][];
		
		for( int i = 0; i < copy.length; i++ )
		{
			copy[ i ] = new double[ src[ i ].length ];
			System.arraycopy( src[ i ], 0, copy[ i ], 0, src[ i ].length );
		}
		
		return copy;
	}
	
	/**
	 * 
	 * @param values
	 * @param startValue
	 * @param endValue
	 * @return double[]
	 */
	public static double[] getRange( final double[] values, final float startValue, final float endValue )
	{
		final List<Double> range = new ArrayList<>();
		
		for( double value : values )
		{
			if( value >= startValue && value <= endValue )
			{
				range.add( Double.valueOf( value ) );
			}
		}
		
		return CollectionUtils.toDoubleArray( range );
	}
	
	/**
	 * 
	 * @param values
	 * @param value
	 * @return int
	 */
	public static int getNearestIndex( double[] values, double value )
	{
		return getNearestIndex( values, value, Double.MAX_VALUE );
	}
	
	/**
	 * @param values
	 * @param value
	 * @param tolerance
	 * @return int
	 */
	public static int getNearestIndex( final double[] values, final double value, final double tolerance )
	{
		int index = NumberUtils.UNDEFINED;
		double bestDelta = tolerance;
		
		for( int i = 0; i < values.length; i++ )
		{
			final double delta = Math.abs( value - values[ i ] );

			if( delta <= bestDelta )
			{
				bestDelta = delta;
				index = i;
			}
		}

		return index;
	}
	
	/**
	 * @param values
	 * @param value
	 * @param tolerance
	 * @return int
	 */
	public static int getNearestIndex( final float[] values, final float value, final float tolerance )
	{
		int index = NumberUtils.UNDEFINED;
		double bestDelta = tolerance;
		
		for( int i = 0; i < values.length; i++ )
		{
			final double delta = Math.abs( value - values[ i ] );

			if( delta <= bestDelta )
			{
				bestDelta = delta;
				index = i;
			}
		}

		return index;
	}
	
	/**
	 * @param values
	 * @param value
	 * @param tolerance
	 * @return int
	 */
	public static int getNearestIndex( final int[] values, final int value, final int tolerance )
	{
		int index = NumberUtils.UNDEFINED;
		int bestDelta = tolerance;
		
		for( int i = 0; i < values.length; i++ )
		{
			final int delta = Math.abs( value - values[ i ] );

			if( delta <= bestDelta )
			{
				bestDelta = delta;
				index = i;
			}
		}

		return index;
	}

	/**
	 * 
	 * @param values
	 * @param value
	 * @return double
	 */
	public static double getNearestValue( final double[] values, final double value )
	{
		return values[ getNearestIndex( values, value ) ];
	}
	
	/**
	 * 
	 * @param values
	 * @param value
	 * @return boolean
	 */
	public static boolean contains( final int[] values, final int value )
	{
		return getNearestIndex( values, value, 0 ) != NumberUtils.UNDEFINED;
	}
	
	/**
	 * 
	 * @param collection
	 * @return String
	 */
	public static String toToolTip( final Collection<?> collection )
	{
		if( collection == null || collection.size() == 0 )
		{
			return null;
		}
		
		final String START_SEPARATOR = "<p>"; //$NON-NLS-1$
		final String END_SEPARATOR = "</p>"; //$NON-NLS-1$
		final StringBuffer buffer = new StringBuffer( "<html>" ); //$NON-NLS-1$
		
		for( Iterator<?> iterator = collection.iterator(); iterator.hasNext(); )
		{
			buffer.append( START_SEPARATOR );
			buffer.append( iterator.next() );
			buffer.append( END_SEPARATOR );
		}
		
		buffer.append( "</html>" ); //$NON-NLS-1$
		
		return buffer.toString();
	}
	
	/**
	 * 
	 * @param map
	 * @return String
	 * @throws XMLStreamException
	 */
	public static String toXml( final Map<Object,Object> map ) throws XMLStreamException
	{
		final String PREFIX = "xsi"; //$NON-NLS-1$
		final String URI = "http://www.w3.org/2001/XMLSchema-instance"; //$NON-NLS-1$
		final String NO_NAMESPACE_SCHEMA_LOCATION = "xsi:noNamespaceSchemaLocation"; //$NON-NLS-1$
		
		final String MAP = "map"; //$NON-NLS-1$
		final String VERSION = "version"; //$NON-NLS-1$
		final String ENTRY = "entry"; //$NON-NLS-1$
		
		final StringWriter stringWriter = new StringWriter();
		final XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter( stringWriter );
		
		xmlStreamWriter.writeStartDocument();

		xmlStreamWriter.writeStartElement( MAP );
		xmlStreamWriter.writeNamespace( PREFIX, URI );
		xmlStreamWriter.writeAttribute( NO_NAMESPACE_SCHEMA_LOCATION, SCHEMA_LOCATION );
		xmlStreamWriter.writeAttribute( VERSION, "1.0" ); //$NON-NLS-1$
		
		for( Iterator<Map.Entry<Object,Object>> iterator = map.entrySet().iterator(); iterator.hasNext(); )
		{
			final Map.Entry<Object,Object> entry = iterator.next();
			xmlStreamWriter.writeStartElement( ENTRY );
			xmlStreamWriter.writeAttribute( KEY, entry.getKey().toString() );
			
			Object value = entry.getValue();
			boolean array = false;
			
			if( value instanceof Object[] )
			{
				final Object[] objectArray = (Object[])value;
				array = objectArray.length > 1;
				value = ( array ) ? Arrays.toString( objectArray ) : CollectionUtils.getFirst( objectArray );
			}
			
			xmlStreamWriter.writeAttribute( ARRAY, Boolean.toString( array ) );
			xmlStreamWriter.writeAttribute( VALUE, value.toString() );
			xmlStreamWriter.writeEndElement(); // ENTRY
		}
		
		xmlStreamWriter.writeEndElement(); // MAP
		
		return stringWriter.toString();
	}
	
	/**
	 * 
	 * @param is
	 * @return Map
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static Map<Object,Object> fromXml( final InputStream is ) throws ParserConfigurationException, IOException, SAXException
	{
		try
		{
			final Map<Object,Object> map = new TreeMap<>();
			final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( is );
			
			final NodeList childNodes = document.getChildNodes();
			
			for( int i = 0; i < childNodes.getLength(); i++ )
			{
				Node childNode = childNodes.item( i );
				
				final NodeList grandChildNodes = childNode.getChildNodes();
				
				for( int j = 0; j < grandChildNodes.getLength(); j++ )
				{
					final Node grandChildNode = grandChildNodes.item( j );
					final NamedNodeMap attributes = grandChildNode.getAttributes();
					final String value = attributes.getNamedItem( VALUE ).getNodeValue();
					final boolean array = ( attributes.getNamedItem( ARRAY ) == null ) ? false : Boolean.parseBoolean( attributes.getNamedItem( ARRAY ).getNodeValue() );
					
					map.put( attributes.getNamedItem( KEY ).getNodeValue(), array ? toArray( value ) : value );
				}
			}
			
			return map;
		}
		finally
		{
			is.close();
		}
	}
	
	/**
	 * 
	 * @param collection
	 * @return String
	 */
	public static String toString( final Collection<?> collection )
	{
		return toString( collection.toArray() );
	}
	
	/**
	 * 
	 * @param array
	 * @return String
	 */
	public static String toString( final Object[] array )
	{
		return Arrays.toString( array ).replace( ARRAY_OPEN_TAG, EMPTY_STRING ).replace( ARRAY_CLOSE_TAG, EMPTY_STRING );
	}
	
	/**
	 * 
	 * @param values
	 * @return String[]
	 */
	public static String[] fromString( final String values )
	{
		return fromString( values, ELEMENT_SEPARATOR );
	}
	
	/**
	 * 
	 * @param values
	 * @param separator
	 * @return String[]
	 */
	private static String[] fromString( final String values, final String separator )
	{
		if( values == null )
		{
			return new String[ 0 ];
		}
		
		String formattedValues = values.trim();
		
		if( formattedValues.startsWith( ARRAY_OPEN_TAG ) && formattedValues.endsWith( ARRAY_CLOSE_TAG ) )
		{
			formattedValues = formattedValues.substring( ARRAY_OPEN_TAG.length(), formattedValues.length() - ARRAY_OPEN_TAG.length() );
		}
		
		final ArrayList<String> list = new ArrayList<>( Arrays.asList( formattedValues.split( separator ) ) );
		
		for( Iterator<String> iterator = list.iterator(); iterator.hasNext(); )
		{
			final String value = iterator.next();
			
			if( value.length() == 0 )
			{
				iterator.remove();
			}
		}
		
		return list.toArray( new String[ list.size() ] );
	}
}