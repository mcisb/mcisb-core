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

import java.awt.*;

/**
 * 
 * @author Neil Swainston
 */
public class ComponentUtils
{
	/**
	 * 
	 * @param component
	 */
	public static void setLocationCentral( final Component component )
	{
		final Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		final Dimension frameDimension = component.getSize();
		final int x = ( screenDimension.width - frameDimension.width ) / 2;
		final int y = ( screenDimension.height - frameDimension.height )/ 2;
		component.setLocation( x, y );
	}
}
