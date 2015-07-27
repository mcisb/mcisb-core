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
package org.mcisb.ui.util.dnd;

import javax.swing.*;

/**
 * 
 * @author Neil Swainston
 */
public class DraggableLabel extends JLabel
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
	 * @param userObject
	 */
	public DraggableLabel( final Object userObject )
	{
		super( userObject.toString() );
		this.userObject = userObject;
	}

	/**
	 * 
	 * @return Object
	 */
	public Object getUserObject()
	{
		return userObject;
	}
}