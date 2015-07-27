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

import java.beans.*;
import java.io.*;
import java.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class GenericBean
{
	/**
	 * 
	 */
	protected Map<Object,Object> properties = new TreeMap<>();
	
	/**
	 * 
	 */
	protected final PropertyChangeSupport support = new PropertyChangeSupport( this );
	
	/**
	 * 
	 * @return Map
	 */
	public Map<Object,Object> getProperties()
	{
		return new TreeMap<>( properties );
	}
	
	/**
	 *
	 * @param name
	 * @return Object
	 */
	public Object getProperty( final Object name )
	{
		return properties.get( name );
	}
	
	/**
	 * 
	 * @param name
	 * @param object
	 */
	public void setProperty( Object name, Object object )
	{
		Object oldObject = properties.get( name );
		properties.put( name, object );
		support.firePropertyChange( name.toString(), oldObject, object );
	}
	
	/**
	 * 
	 * @return Collection
	 */
	public Collection<Object> getPropertyNames()
	{
		return properties.keySet();
	}
	
	/**
	 *
	 * @param propertyName
	 * @return boolean
	 */
	public boolean getBoolean( String propertyName )
	{
		return ( (Boolean)( CollectionUtils.getFirst( (Object[])getProperty( propertyName ) ) ) ).booleanValue();
	}
	
	/**
	 *
	 * @param propertyName
	 * @return int
	 */
	public int getInteger( String propertyName )
	{
		return ( (Number)CollectionUtils.getFirst( (Object[])getProperty( propertyName ) ) ).intValue();
	}
	
	/**
	 *
	 * @param name
	 * @param value
	 */
	public void setDouble( final String name, final double value )
	{
		setProperty( name, new Double[] { Double.valueOf( value ) } );
	}
	
	/**
	 *
	 * @param propertyName
	 * @return double
	 */
	public double getDouble( String propertyName )
	{
		final Object property = getProperty( propertyName );
		
		if( property == null )
		{
			return NumberUtils.UNDEFINED;
		}
		
		return ( (Double)( CollectionUtils.getFirst( (Object[])property ) ) ).doubleValue();
	}
	
	/**
	 *
	 * @param propertyName
	 * @return String
	 */
	public String getString( String propertyName )
	{
		final Object property = getProperty( propertyName );
		
		if( property instanceof Object[] )
		{
			return (String)CollectionUtils.getFirst( (Object[])property );
		}
		
		return ( property == null ) ? null : property.toString();
	}
	
	/**
	 *
	 * @param propertyName
	 * @return File
	 */
	public File getFile( String propertyName )
	{
		return CollectionUtils.getFirst( getFiles( propertyName ) );
	}
	
	/**
	 *
	 * @param propertyName
	 * @return Collection
	 */
	public Collection<File> getFiles( String propertyName )
	{
		Collection<File> files = new ArrayList<>();
		final Object property = getProperty( propertyName );
		
		if( property instanceof Collection<?> )
		{
			Collection<?> filepaths = (Collection<?>)property;
			
			for( Iterator<?> iterator = filepaths.iterator(); iterator.hasNext(); )
			{
				files.add( new File( iterator.next().toString() ) );
			}
		}
		
		return files;
	}
}