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
import org.mcisb.ui.util.*;
import org.mcisb.util.*;
import org.mcisb.util.data.*;

/**
 * 
 * @author Neil Swainston
 */
public class ContinuousDataDisplayPanel extends DataDisplayPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final static float DEFAULT_LABEL_THRESHOLD = Float.MAX_VALUE;

	/**
	 * 
	 */
	protected int lastXPosition = NumberUtils.UNDEFINED;

	/**
	 * 
	 */
	protected int lastYPosition = NumberUtils.UNDEFINED;

	/**
	 * 
	 */
	public ContinuousDataDisplayPanel()
	{
		super( DEFAULT_LABEL_THRESHOLD );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mcisb.ui.util.data.DataDisplayPanel#paintSpectrum(java.awt.Graphics2D
	 * , org.mcisb.util.data.Spectrum)
	 */
	@Override
	protected void paintSpectrum( Graphics2D g, Spectrum spectrum )
	{
		lastXPosition = NumberUtils.UNDEFINED;
		lastYPosition = NumberUtils.UNDEFINED;

		super.paintSpectrum( g, spectrum );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mcisb.ui.util.data.DataDisplayPanel#paintData(java.awt.Graphics2D,
	 * int, int)
	 */
	@Override
	protected void paintData( final Graphics2D g, final int xPosition, final int yPosition )
	{
		if( lastXPosition != NumberUtils.UNDEFINED && lastYPosition != NumberUtils.UNDEFINED )
		{
			g.drawLine( lastXPosition, lastYPosition, xPosition, yPosition );
		}

		lastXPosition = xPosition;
		lastYPosition = yPosition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.data.DataDisplayPanel#getColor(double, double)
	 */
	@Override
	protected Color getColor( @SuppressWarnings("unused") final double x, @SuppressWarnings("unused") final double y )
	{
		return SampleConstants.getColor( spectra.get( spectrumIndex ).isBackground() ? org.mcisb.tracking.SampleConstants.BLANK : org.mcisb.tracking.SampleConstants.SAMPLE );
	}
}