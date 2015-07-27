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
package org.mcisb.ui.util;

import javax.swing.*;

/**
 * 
 * @author Neil Swainston
 */
public class ResourceFactory
{
	/**
	 * 
	 * @param name
	 * @return ImageIcon
	 */
	public ImageIcon getImageIcon( String name )
	{
		return new ImageIcon( getClass().getClassLoader().getResource( name ) );
	}
}
