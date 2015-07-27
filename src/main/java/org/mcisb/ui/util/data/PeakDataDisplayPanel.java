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
package org.mcisb.ui.util.data;

import java.awt.*;

/**
 *
 * @author Neil Swainston
 */
public class PeakDataDisplayPanel extends DataDisplayPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	final static float DEFAULT_LABEL_THRESHOLD = 0.25f;
	
	/**
	 * 
	 */
	public PeakDataDisplayPanel()
	{
		super( DEFAULT_LABEL_THRESHOLD );
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mcisb.ui.util.data.DataDisplayPanel#paintData(java.awt.Graphics2D, int, int)
	 */
	@Override
	protected void paintData( final Graphics2D g, final int xPosition, final int yPosition )
	{
		g.drawLine( xPosition, getHeight() - AXES_BORDER, xPosition, yPosition );
	}
}