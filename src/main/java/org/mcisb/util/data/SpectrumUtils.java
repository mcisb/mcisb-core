/*******************************************************************************
 * Manchester Centre for Integrative Systems Biology
 * University of Manchester
 * Manchester M1 7ND
 * United Kingdom
 * 
 * Copyright (C) 2008 University of Manchester
 * 
 * This program is released under the Academic Free License ("AFL") v3.0.
 * (http://www.opensource.org/licenses/academic.php)
 *******************************************************************************/
package org.mcisb.util.data;

import java.util.*;

/**
 * 
 * @author Daniel Jameson
 */
public class SpectrumUtils
{
	/**
	 * 
	 */
	public static final int START = 0;
	
	/**
	 * 
	 */
	public static final int END = 1;
	
	/**
	 * 
	 */
	public static final int SPECTRUM_DIMENSIONS = 2;
	
	/**
	 * 
	 */
	public static final int X = 0;
	
	/**
	 * 
	 */
	public static final int Y = 1;

	/**
	 * 
	 * @param spectra
	 * @param binSize
	 * @return Spectrum
	 */
	public static double[][] combine( final double[][][] spectra, final double binSize )
	{
		final int ZERO = 0;
		double minValue = Double.MAX_VALUE;
		double maxValue = Double.MIN_VALUE;
		
		for( int i = 0; i < spectra.length; i++ )
		{
			final double[][] spectrum = spectra[ i ];
			final double[] xValues = spectrum[ X ];
			
			for( int j = 0; j < xValues.length; j++ )
			{
				final double x = xValues[ j ];
				minValue = Math.min( minValue, x );
				maxValue = Math.max( maxValue, x );
			}
		}
		
		final int numberOfBins = (int)Math.max( ZERO, ( maxValue - minValue ) / binSize ) + 1;
		final double[] combinedXValues = new double[ numberOfBins ];
		final double[] combinedYValues = new double[ numberOfBins ];
		
		for( int i = 0; i < numberOfBins; i++ )
		{
			combinedXValues[ i ] = minValue + ( i * binSize );
		}
	
		for( int i = 0; i < spectra.length; i++ )
		{
			final double[][] spectrum = spectra[ i ];
			final double[] xValues = spectrum[ X ];
			final double[] yValues = spectrum[ Y ];
			
			for( int j = 0; j < xValues.length; j++ )
			{
				final double x = xValues[ j ];
				final int bin = (int)( ( x - minValue ) / binSize );
				combinedYValues[ bin ] += yValues[ j ];
			}
		}
		
		final double[] strippedCombinedXValues = new double[ combinedXValues.length ];
		final double[] strippedCombinedYValues = new double[ combinedXValues.length ];
		int index = 0;
		
		for( int i = 0; i < combinedXValues.length; i++ )
		{
			if( combinedYValues[ i ] > 0 )
			{
				strippedCombinedXValues[ index ] = combinedXValues[ i ];
				strippedCombinedYValues[ index ] = combinedYValues[ i ];
				index++;
			}
		}
		
		final double[][] spectrum = new double[ SPECTRUM_DIMENSIONS ][];
		spectrum[ X ] = Arrays.copyOf( strippedCombinedXValues, index );
		spectrum[ Y ] = Arrays.copyOf( strippedCombinedYValues, index );
		return spectrum;
	}
	
	/**
	 * @param spectrum
	 * @param startX
	 * @param endX
	 * @return double[][]
	 */
	public static double[][] getSubSpectrum( double[][] spectrum, double startX, double endX )
	{
		final double[] subXValues = new double[ spectrum[ X ].length ];
		final double[] subYValues = new double[ spectrum[ Y ].length ];
		int index = 0;
		
		final double[] xValues = spectrum[ X ];
		final double[] yValues = spectrum[ Y ];

		for( int i = 0; i < xValues.length; i++ )
		{
			if( xValues[ i ] > startX && xValues[ i ] < endX )
			{
				subXValues[ index ] = xValues[ i ];
				subYValues[ index ] = yValues[ i ];
				index++;
			}
		}
		
		final double[][] subSpectrum = new double[ SPECTRUM_DIMENSIONS ][];
		subSpectrum[ X ] = Arrays.copyOf( subXValues, index );
		subSpectrum[ Y ] = Arrays.copyOf( subYValues, index );
		return subSpectrum;
	}
	
	/**
	 * 
	 * @param str
	 * @param delim
	 * @return String[]
	 */
	public static String[] split( final String str, final char delim )
	{
		final List<String> tokens = new ArrayList<>();
		final char[] charArray = str.toCharArray();
		final StringBuffer token = new StringBuffer();
		
		for( int i = 0; i < charArray.length; i++ )
		{
			if( charArray[ i ] == delim )
			{
				tokens.add( token.toString() );
				token.setLength( 0 );
			}
			else
			{
				token.append( charArray[ i ] );
			}
		}
		
		if( token.length() > 0 )
		{
			tokens.add( token.toString() );
		}
		
		return tokens.toArray( new String[ tokens.size() ] );
	}
}