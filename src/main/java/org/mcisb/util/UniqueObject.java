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

/**
 * 
 * @author Neil Swainston
 */
public class UniqueObject extends NamedObject
{
	/**
	 * 
	 */
	protected String id;
	
	/**
	 * 
	 */
	public UniqueObject()
	{
		// Default constructor.
	}
	
	/**
	 * 
	 * @param id
	 */
	public UniqueObject( final String id )
	{
		this.id = id;
	}
	
	/**
	 *
	 * @param id
	 * @param name
	 */
	public UniqueObject( final String id, final String name )
	{
		this.id = id;
		this.name = name;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getId()
	{
		return id;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object object )
	{
		if( object instanceof UniqueObject )
		{
			return id.equals( ( (UniqueObject)object ).id );
		}
		
		return false;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return id.hashCode();
	}
}