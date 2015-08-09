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
package org.mcisb.tracking;

import java.io.*;

/**
 * 
 * @author Neil Swainston
 */
public class SpotReading implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final Object userObject;

	/**
	 * 
	 */
	private final Object data;

	/**
	 * 
	 * @param userObject
	 * @param data
	 */
	public SpotReading( final Object userObject, final Object data )
	{
		this.userObject = userObject;
		this.data = data;
	}

	/**
	 * 
	 * @return Object
	 */
	public Object getUserObject()
	{
		return userObject;
	}

	/**
	 * 
	 * @return Object
	 */
	public Object getData()
	{
		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return userObject.toString();
	}
}