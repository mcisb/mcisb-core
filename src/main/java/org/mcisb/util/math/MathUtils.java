/*
 *	manchester-core
 *
 *	Copyright (C) 2014 Neil Swainston
 *
 *	This program is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	This program is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.mcisb.util.math;

import java.math.*;
import java.nio.*;
import java.util.*;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.stat.descriptive.moment.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class MathUtils
{
	/**
	 * 
	 */
	public final static int INTERSECTION = 0;

	/**
	 * 
	 */
	public final static int GRADIENT = 1;

	/**
	 * 
	 */
	public final static int DOUBLE_LENGTH = 8;

	/**
	 * 
	 */
	public final static int FLOAT_LENGTH = 4;

	/**
	 * 
	 */
	private static final Mean mean = new Mean();

	/**
	 * 
	 */
	private static final StandardDeviation sd = new StandardDeviation();

	/**
	 * 
	 * @param array
	 * @return double
	 */
	public static double sum( final double[] array )
	{
		double count = 0;

		for( int j = 0; j < array.length; j++ )
		{
			count += array[ j ];
		}

		return count;
	}

	/**
	 * 
	 * @param values
	 * @param expected
	 * @return double
	 */
	public static double getRootMeanSquareError( final double[] values, final double expected )
	{
		return getRootMeanSquareError( values, 0, values.length, expected );
	}

	/**
	 * 
	 * @param values
	 * @param start
	 * @param end
	 * @param expected
	 * @return double
	 */
	public static double getRootMeanSquareError( final double[] values, final int start, final int end, final double expected )
	{
		double rmse = 0;

		for( int i = start; i < end; i++ )
		{
			rmse += Math.pow( values[ i ] - expected, 2 );
		}

		return Math.sqrt( rmse / end - start );
	}

	/**
	 * 
	 * @param positives
	 * @param negatives
	 * @return double
	 */
	public static double getZFactor( final double[] positives, final double[] negatives )
	{
		if( negatives.length == 0 )
		{
			return 1;
		}

		final double positivesMean = mean.evaluate( positives );
		final double positivesSd = sd.evaluate( positives, positivesMean );
		final double negativesMean = mean.evaluate( negatives );
		final double negativesSd = sd.evaluate( negatives, negativesMean );

		return 1 - ( 3 * ( positivesSd + negativesSd ) / Math.abs( positivesMean - negativesMean ) );
	}

	/**
	 * 
	 * @param value
	 * @param places
	 * @return double
	 */
	public static double round( final double value, final int places )
	{
		if( places < 0 )
		{
			throw new IllegalArgumentException();
		}

		BigDecimal bd = new BigDecimal( value );
		bd = bd.setScale( places, BigDecimal.ROUND_HALF_UP );
		return bd.doubleValue();
	}

	/**
	 * 
	 * @param value
	 * @return roundRobin
	 */
	public static int roundRobin( final int value )
	{
		int roundRobin = 0;

		for( int i = 1; i < value; i++ )
		{
			roundRobin += i;
		}

		return roundRobin;
	}

	/**
	 * 
	 * @param values
	 * @param bigEndian
	 * @return String
	 */
	public static byte[] getBytes( final double[] values, final boolean bigEndian )
	{
		final byte[] byteArray = new byte[ values.length * DOUBLE_LENGTH ];
		ByteBuffer buffer = ByteBuffer.wrap( byteArray );
		buffer = buffer.order( bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN );

		for( int i = 0; i < values.length; i++ )
		{
			buffer.putDouble( values[ i ] );
		}

		return buffer.array();
	}

	/**
	 * 
	 * @param values
	 * @param bigEndian
	 * @return String
	 */
	public static String encode( final double[] values, final boolean bigEndian )
	{
		return Base64.encode( getBytes( values, bigEndian ) );
	}

	/**
	 * 
	 * 
	 * @param values
	 * @param bigEndian
	 * @return String
	 */
	public static String encode( final float[] values, final boolean bigEndian )
	{
		final byte[] byteArray = new byte[ values.length * FLOAT_LENGTH ];
		ByteBuffer buffer = ByteBuffer.wrap( byteArray );
		buffer = buffer.order( bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN );

		for( int i = 0; i < values.length; i++ )
		{
			buffer.putFloat( values[ i ] );
		}

		// make the base64 strings from the bytes
		return Base64.encode( buffer.array() );
	}

	/**
	 * 
	 * @param encoded
	 * @param bigEndian
	 * @param doublePrecision
	 * @return double[]
	 */
	public static double[] decode( final byte[] encoded, final boolean bigEndian, final boolean doublePrecision )
	{
		final byte[] bytes = Base64.decode( encoded );
		ByteBuffer byteBuffer = ByteBuffer.wrap( bytes );
		byteBuffer = byteBuffer.order( bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN );

		final int limit = byteBuffer.limit();
		double[] decoded = new double[ ( ( doublePrecision ) ? limit / DOUBLE_LENGTH : limit / FLOAT_LENGTH ) ];
		int i = 0;

		while( byteBuffer.hasRemaining() )
		{
			if( doublePrecision )
			{
				decoded[ i++ ] = byteBuffer.getDouble();
			}
			else
			{
				decoded[ i++ ] = byteBuffer.getFloat();
			}
		}

		return decoded;
	}

	/**
	 * @param x
	 * @param y
	 * @return double[]
	 */
	public static double[] linearFit( final double[] x, final double[] y )
	{
		return linearFit( x, y, x.length );
	}

	/**
	 * @param x
	 * @param y
	 * @param numPoints
	 * @return double
	 */
	public static double[] linearFit( final double[] x, final double[] y, final int numPoints )
	{
		final int VARIABLES = 2;
		final double[] fit = new double[ VARIABLES ];
		final double meanX = mean.evaluate( x, 0, numPoints );
		final double meanY = mean.evaluate( y, 0, numPoints );
		final double sxx = dot( x, x, numPoints ) - numPoints * meanX * meanX;
		final double sxy = dot( x, y, numPoints ) - numPoints * meanX * meanY;

		fit[ GRADIENT ] = sxy / sxx;
		fit[ INTERSECTION ] = meanY - fit[ GRADIENT ] * meanX;

		return fit;
	}

	/**
	 * 
	 * @param array
	 * @return double
	 */
	public static double norm( final double[] array )
	{
		return MatrixUtils.createRealVector( array ).getNorm();
	}

	/**
	 * 
	 * @param array
	 * @return double
	 */
	public static double twoNorm( final double[] array )
	{
		return Math.sqrt( MathUtils.dot( array, array ) );
	}

	/**
	 * 
	 * @param array
	 * @param value
	 */
	public static void add( final int[] array, final int value )
	{
		for( int i = 0; i < array.length; i++ )
		{
			array[ i ] += value;
		}
	}

	/**
	 * 
	 * @param array
	 * @param value
	 */
	public static void add( final double[] array, final double value )
	{
		for( int i = 0; i < array.length; i++ )
		{
			array[ i ] += value;
		}
	}

	/**
	 * 
	 * @param array1
	 * @param array2
	 * @return double[]
	 */
	public static double[] add( final double[] array1, final double[] array2 )
	{
		final int FIRST = 0;
		return MatrixUtils.createColumnRealMatrix( array1 ).add( MatrixUtils.createColumnRealMatrix( array2 ) ).getColumn( FIRST );
	}

	/**
	 * 
	 * @param array1
	 * @param array2
	 * @return double[]
	 */
	public static double[][] add( final double[][] array1, final double[][] array2 )
	{
		return MatrixUtils.createRealMatrix( array1 ).add( MatrixUtils.createRealMatrix( array2 ) ).getData();
	}

	/**
	 * 
	 * @param array1
	 * @param array2
	 * @return double[][]
	 */
	public static double[][] multiply( final double[][] array1, final double[][] array2 )
	{
		return MatrixUtils.createRealMatrix( array1 ).multiply( MatrixUtils.createRealMatrix( array2 ) ).getData();
	}

	/**
	 * 
	 * @param array1
	 * @param array2
	 * @return double[]
	 */
	public static double[] multiply( final double[][] array1, final double[] array2 )
	{
		final int FIRST = 0;
		return MatrixUtils.createRealMatrix( array1 ).multiply( MatrixUtils.createColumnRealMatrix( array2 ) ).getColumn( FIRST );
	}

	/**
	 * 
	 * @param array1
	 * @param factor
	 * @return double[][]
	 */
	public static double[] scalarMultiply( final double[] array1, final double factor )
	{
		final int FIRST = 0;
		return MatrixUtils.createColumnRealMatrix( array1 ).scalarMultiply( factor ).getColumn( FIRST );
	}

	/**
	 * 
	 * @param array1
	 * @param factor
	 * @return double[][]
	 */
	public static double[][] scalarMultiply( final double[][] array1, final double factor )
	{
		return MatrixUtils.createRealMatrix( array1 ).scalarMultiply( factor ).getData();
	}

	/**
	 * 
	 * @param array1
	 * @param array2
	 * @return double[]
	 */
	public static double[] ebeMultiply( final double[] array1, final double[] array2 )
	{
		return MatrixUtils.createRealVector( array1 ).ebeMultiply( MatrixUtils.createRealVector( array2 ) ).toArray();
	}

	/**
	 * 
	 * @param array1
	 * @param array2
	 * @return double[]
	 */
	public static double[] ebeDivide( final double[] array1, final double[] array2 )
	{
		return MatrixUtils.createRealVector( array1 ).ebeDivide( MatrixUtils.createRealVector( array2 ) ).toArray();
	}

	/**
	 * 
	 * @param array1
	 * @param array2
	 * @return double[]
	 */
	public static double[] subtract( final double[] array1, final double[] array2 )
	{
		final int FIRST = 0;
		return MatrixUtils.createColumnRealMatrix( array1 ).subtract( MatrixUtils.createColumnRealMatrix( array2 ) ).getColumn( FIRST );
	}

	/**
	 * 
	 * @param array
	 * @return double[]
	 */
	public static double[][] transpose( final double[][] array )
	{
		return MatrixUtils.createRealMatrix( array ).transpose().getData();
	}

	/**
	 * 
	 * @param matrix
	 * @return double[][]
	 */
	public static double[][] transpose( final List<List<Integer>> matrix )
	{
		final int FIRST = 0;
		final double[][] transpose = new double[ matrix.get( FIRST ).size() ][];

		for( int i = 0; i < transpose.length; i++ )
		{
			transpose[ i ] = new double[ matrix.size() ];
		}

		for( int i = 0; i < matrix.size(); i++ )
		{
			final List<Integer> row = matrix.get( i );

			for( int j = 0; j < row.size(); j++ )
			{
				transpose[ j ][ i ] = row.get( j ).intValue();
			}
		}

		return transpose;
	}

	/**
	 * 
	 * @param array
	 * @return double[]
	 */
	public static double[][] inverse( final double[][] array )
	{
		return new LUDecomposition( MatrixUtils.createRealMatrix( array ) ).getSolver().getInverse().getData();
	}

	/**
	 * 
	 * @param array
	 * @param singularityThreshold
	 * @return double[]
	 */
	public static double[][] inverse( final double[][] array, final double singularityThreshold )
	{
		return new LUDecomposition( MatrixUtils.createRealMatrix( array ), singularityThreshold ).getSolver().getInverse().getData();
	}

	/**
	 * Get maximum value in array of doubles.
	 * 
	 * @param d
	 * @return double
	 */
	public static double max( double[] d )
	{
		double max = Double.MIN_VALUE;

		for( int i = 0; i < d.length; i++ )
		{
			max = Math.max( max, d[ i ] );
		}

		return max;
	}

	/**
	 * 
	 * @param dimension
	 * @return double[][]
	 */
	public static double[][] getIdentityMatrix( final int dimension )
	{
		return MatrixUtils.createRealIdentityMatrix( dimension ).getData();
	}

	/**
	 * 
	 * @param data
	 * @param power
	 * @return double[]
	 */
	public static double[] mapPow( final double[] data, final double power )
	{
		final double[] newData = new double[ data.length ];

		for( int i = 0; i < data.length; i++ )
		{
			newData[ i ] = Math.pow( data[ i ], power );
		}

		return newData;
	}

	/**
	 * @param a
	 * @param b
	 * @return double
	 */
	public static double dot( final double[] a, final double[] b )
	{
		return dot( a, b, a.length );
	}

	/**
	 * 
	 * @param values
	 * @return double[]
	 */
	public static double[] derivative( final double[] values )
	{
		final double[] derivative = new double[ values.length ];

		for( int j = 0; j < derivative.length; j++ )
		{
			final int nextIndex = ( j == derivative.length - 1 ) ? j : j + 1;
			final int previousIndex = ( j == 0 ) ? j : j - 1;
			derivative[ j ] = ( values[ nextIndex ] - values[ previousIndex ] ) / 2;
		}

		return derivative;
	}

	/**
	 * 
	 * @param d
	 * @return double
	 */
	public static double getSignificantFigures( final double d )
	{
		return Double.parseDouble( StringUtils.getEngineeringNotation( d ) );
	}

	/**
	 * 
	 * @param values
	 * @param start
	 * @param end
	 * @param binSize
	 * @return int[]
	 */
	public static int[] toHistogram( final double[] values, final double start, final double end, final double binSize )
	{
		final BigDecimal remainder = BigDecimal.valueOf( end - start ).remainder( BigDecimal.valueOf( binSize ) );

		if( remainder.equals( BigDecimal.ZERO ) )
		{
			throw new IllegalArgumentException( "Invalid bin size for range of " + ( end - start ) ); //$NON-NLS-1$
		}

		Arrays.sort( values );

		if( values[ 0 ] < start )
		{
			throw new IllegalArgumentException( "Value " + values[ 0 ] + " out of range" ); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if( values[ values.length - 1 ] > end )
		{
			throw new IllegalArgumentException( "Value " + values[ values.length - 1 ] + " out of range" ); //$NON-NLS-1$ //$NON-NLS-2$
		}

		final int numBins = (int)( ( end - start ) / binSize );
		final int[] bins = new int[ numBins ];

		for( double value : values )
		{
			final int index = BigDecimal.valueOf( value ).equals( BigDecimal.valueOf( end ) ) ? numBins - 1 : (int)( ( value - start ) / binSize );
			bins[ index ]++;
		}

		return bins;
	}

	/**
	 * @param a
	 * @param b
	 * @param numPoints
	 * @return double
	 */
	private static double dot( final double[] a, final double[] b, final int numPoints )
	{
		double dot = 0;

		for( int i = 0; i < numPoints; i++ )
		{
			dot += a[ i ] * b[ i ];
		}

		return dot;
	}
}