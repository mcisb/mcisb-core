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

/**
 * 
 * @author Neil Swainston
 */
public class PropertyChangeSupported
{
	/**
	 * 
	 */
	protected final PropertyChangeSupport support = new PropertyChangeSupport( this );

	/**
	 * 
	 * @param l
	 */
	public void addPropertyChangeListener( PropertyChangeListener l )
	{
		support.addPropertyChangeListener( l );
	}

	/**
	 * 
	 * @param l
	 */
	public void removePropertyChangeListener( PropertyChangeListener l )
	{
		support.removePropertyChangeListener( l );
	}
}
