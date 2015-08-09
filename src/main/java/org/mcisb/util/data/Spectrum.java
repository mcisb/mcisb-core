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
package org.mcisb.util.data;

import java.util.*;
import org.mcisb.util.*;
import org.mcisb.util.math.*;

/**
 * 
 * @author Neil Swainston
 */
public class Spectrum
{
	/**
	 * 
	 */
	public static final char SEPARATOR = ',';

	/**
	 * 
	 */
	private final boolean background;

	/**
	 * 
	 */
	private final double[] xValues;

	/**
	 * 
	 */
	private final double[] yValues;

	/**
	 * 
	 */
	private double xValuesHighlightFrom = NumberUtils.UNDEFINED;

	/**
	 * 
	 */
	private double xValuesHighlightTo = NumberUtils.UNDEFINED;

	/**
	 * 
	 */
	private String label;

	/**
	 * 
	 */
	private Object object;

	/**
	 * 
	 * @param label
	 * @param background
	 * @param xValues
	 * @param yValues
	 */
	public Spectrum( final String label, final boolean background, final double[] xValues, final double[] yValues )
	{
		this.label = label;
		this.background = background;
		this.xValues = new double[ xValues.length ];
		this.yValues = new double[ yValues.length ];

		System.arraycopy( xValues, 0, this.xValues, 0, xValues.length );
		System.arraycopy( yValues, 0, this.yValues, 0, yValues.length );
	}

	/**
	 * 
	 * @param label
	 * @param xValues
	 * @param yValues
	 */
	public Spectrum( final String label, final double[] xValues, final double[] yValues )
	{
		this( label, false, xValues, yValues );
	}

	/**
	 * 
	 * @param label
	 */
	public void setLabel( final String label )
	{
		this.label = label;
	}

	/**
	 * 
	 * @param xValuesHighlightFrom
	 * @param xValuesHighlightTo
	 */
	public void setXValuesHighlight( final double xValuesHighlightFrom, final double xValuesHighlightTo )
	{
		this.xValuesHighlightFrom = xValuesHighlightFrom;
		this.xValuesHighlightTo = xValuesHighlightTo;
	}

	/**
	 * @return the xValuesHighlightFrom
	 */
	public double getXValuesHighlightFrom()
	{
		return xValuesHighlightFrom;
	}

	/**
	 * @return the xValuesHighlightTo
	 */
	public double getXValuesHighlightTo()
	{
		return xValuesHighlightTo;
	}

	/**
	 * 
	 * @return label
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * 
	 * @return background
	 */
	public boolean isBackground()
	{
		return background;
	}

	/**
	 * 
	 * @return xValues
	 */
	public double[] getXValues()
	{
		final double[] xValuesCopy = new double[ xValues.length ];
		System.arraycopy( xValues, 0, xValuesCopy, 0, xValuesCopy.length );
		return xValuesCopy;
	}

	/**
	 * 
	 * @return yValues
	 */
	public double[] getYValues()
	{
		final double[] yValuesCopy = new double[ yValues.length ];
		System.arraycopy( yValues, 0, yValuesCopy, 0, yValuesCopy.length );
		return yValuesCopy;
	}

	/**
	 * 
	 * @return matchingObject
	 */
	public Object getObject()
	{
		return object;
	}

	/**
	 * 
	 * @param object
	 */
	public void setObject( final Object object )
	{
		this.object = object;
	}

	/**
	 * 
	 * @param bigEndian
	 * @param doublePrecision
	 * @param labels
	 * @param encodedXValues
	 * @param encodedYValues
	 * @return java.util.List
	 */
	public static java.util.List<Spectrum> getSpectra( final boolean bigEndian, final boolean doublePrecision, final String labels, final String encodedXValues, final String encodedYValues )
	{
		return getSpectra( bigEndian, doublePrecision, labels, null, encodedXValues, encodedYValues );
	}

	/**
	 * 
	 * @param bigEndian
	 * @param doublePrecision
	 * @param labels
	 * @param backgrounds
	 * @param encodedXValues
	 * @param encodedYValues
	 * @return java.util.List
	 */
	public static java.util.List<Spectrum> getSpectra( final boolean bigEndian, final boolean doublePrecision, final String labels, final String backgrounds, final String encodedXValues, final String encodedYValues )
	{
		return getSpectra( Boolean.toString( bigEndian ), Boolean.toString( doublePrecision ), labels, backgrounds, encodedXValues, encodedYValues );
	}

	/**
	 * 
	 * @param bigEndians
	 * @param doublePrecisions
	 * @param labels
	 * @param background
	 * @param encodedXValues
	 * @param encodedYValues
	 * @return java.util.List
	 */
	public static java.util.List<Spectrum> getSpectra( final String bigEndians, final String doublePrecisions, final String labels, final boolean background, final String encodedXValues, final String encodedYValues )
	{
		return getSpectra( bigEndians, doublePrecisions, labels, Boolean.toString( background ), encodedXValues, encodedYValues );
	}

	/**
	 * 
	 * @param bigEndians
	 * @param doublePrecisions
	 * @param labels
	 * @param backgrounds
	 * @param encodedXValues
	 * @param encodedYValues
	 * @return java.util.List
	 */
	public static java.util.List<Spectrum> getSpectra( final String bigEndians, final String doublePrecisions, final String labels, final String backgrounds, final String encodedXValues, final String encodedYValues )
	{
		final int FIRST = 0;

		final String[] bigEndiansArray = SpectrumUtils.split( bigEndians, SEPARATOR );
		final String[] doublePrecisionsArray = SpectrumUtils.split( doublePrecisions, SEPARATOR );
		final String[] labelsArray = SpectrumUtils.split( labels, SEPARATOR );
		final String[] encodedXValuesArray = SpectrumUtils.split( encodedXValues, SEPARATOR );
		final String[] encodedYValuesArray = SpectrumUtils.split( encodedYValues, SEPARATOR );

		final java.util.List<Spectrum> spectra = new ArrayList<>();

		String[] backgroundArray = null;

		if( backgrounds != null )
		{
			backgroundArray = SpectrumUtils.split( backgrounds, SEPARATOR );
		}

		for( int i = 0; i < encodedXValuesArray.length; i++ )
		{
			final boolean bigEndian = Boolean.parseBoolean( ( bigEndiansArray.length == 1 ) ? bigEndiansArray[ FIRST ] : bigEndiansArray[ i ] );
			final boolean doublePrecision = Boolean.parseBoolean( ( doublePrecisionsArray.length == 1 ) ? doublePrecisionsArray[ FIRST ] : doublePrecisionsArray[ i ] );
			final boolean background = ( backgroundArray == null || backgroundArray.length == 0 ) ? false : Boolean.valueOf( ( backgroundArray.length == 1 ) ? backgroundArray[ FIRST ] : backgroundArray[ i ] ).booleanValue();
			final String label = ( labelsArray.length == 1 ) ? labelsArray[ FIRST ] : labelsArray[ i ];

			// TODO: why would this NEVER be the case? Why is this check needed?
			if( encodedXValuesArray.length > i && encodedYValuesArray.length > i )
			{
				spectra.add( new Spectrum( label, background, MathUtils.decode( encodedXValuesArray[ i ].getBytes(), bigEndian, doublePrecision ), MathUtils.decode( encodedYValuesArray[ i ].getBytes(), bigEndian, doublePrecision ) ) );
			}
		}

		return spectra;
	}
}