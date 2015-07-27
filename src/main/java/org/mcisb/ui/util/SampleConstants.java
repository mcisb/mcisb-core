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
public abstract class SampleConstants
{
	/**
	 * 
	 * @param sampleType
	 * @return Color
	 */
	public static Color getColor( final int sampleType )
	{
		switch( sampleType )
		{
			case org.mcisb.tracking.SampleConstants.SAMPLE:
				return Color.BLACK;
			case org.mcisb.tracking.SampleConstants.BLANK:
				return Color.BLUE;
			default:
				return Color.GRAY;
		}
	}
}
