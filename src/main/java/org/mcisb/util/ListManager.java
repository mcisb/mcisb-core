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

/**
 *
 * @author Neil Swainston
 */
public class ListManager extends PropertyChangeSupported implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final String INDEX = "INDEX"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	public static final String OBJECT = "OBJECT"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private final List<?> list;
	
	/**
	 * 
	 */
	private int index = -1;
	
	/**
	 * 
	 */
	private Object object = null;
	
	/**
	 * 
	 *
	 * @param list
	 */
	public ListManager( final List<?> list )
	{
		this.list = list;
	}
	
	/**
	 *
	 * @return int
	 */
	public int getListSize()
	{
		return list.size();
	}
	
	/**
	 * 
	 */
	public void init()
	{
		next();
	}
	
	/**
	 * 
	 */
	public void next()
	{
		update( 1 );
	}
	
	/**
	 * 
	 */
	public void previous()
	{
		update( -1 );
	}
	
	/**
	 *
	 * @param increment
	 */
	private void update( final int increment )
	{
		final int ZERO = 0;
		
		if( list.size() > ZERO )
		{
			final int newIndex = ( index + list.size() + increment ) % list.size();
			final Object newObject = list.get( newIndex );
			support.firePropertyChange( INDEX, index, newIndex );
			support.firePropertyChange( OBJECT, object, newObject );
			index = newIndex;
			object = newObject;
		}
	}
}