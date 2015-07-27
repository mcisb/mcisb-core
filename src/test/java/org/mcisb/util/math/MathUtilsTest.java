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
package org.mcisb.util.math;

import java.util.*;
import org.junit.*;

/**
 * 
 *
 * @author Neil Swainston
 */
public class MathUtilsTest
{
	/**
	 *
	 */
	@SuppressWarnings("static-method")
	@Test 
	public void encodeDecode()
	{
		testDouble( true );
		testDouble( false );
		testFloat( true );
		testFloat( false );
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAdd()
	{
		final double[] array = getRandomDoubleArray();
		final double[] addedArray = new double[ array.length ];
		System.arraycopy( array, 0, addedArray, 0, array.length );
		
		final double value = Math.random();
		MathUtils.add( addedArray, value );
		
		for( int i = 0; i < array.length; i++ )
		{
			Assert.assertTrue( addedArray[ i ] == array[ i ] + value );
		}
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSignificantFigures()
	{
		Assert.assertEquals( Double.toString( MathUtils.getSignificantFigures( 0.0012232323 ) ), "0.001223" ); //$NON-NLS-1$
		Assert.assertEquals( Double.toString( MathUtils.getSignificantFigures( 12232.3267673 ) ), "12230.0" ); //$NON-NLS-1$
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetHistogram()
	{
		final double[] values = new double[] { 15.3, 25.4, 22.1, 20, 2.5001, 29.999999 };
		
		final double start1 = 0;
		final double end1 = 30;
		final double binSize1 = 10;
		final int[] histogram1 = MathUtils.toHistogram( values, start1, end1, binSize1 );
		Assert.assertEquals( histogram1.length, 3 );
		Assert.assertEquals( histogram1[ 0 ], 1 );
		Assert.assertEquals( histogram1[ 1 ], 1 );
		Assert.assertEquals( histogram1[ 2 ], 4 );
		
		final double start2 = 2.5;
		final double end2 = 30;
		final double binSize2 = 1.25;
		final int[] histogram2 = MathUtils.toHistogram( values, start2, end2, binSize2 );
		Assert.assertEquals( histogram2.length, 22 );
		Assert.assertEquals( histogram2[ 0 ], 1 );
		Assert.assertEquals( histogram2[ 21 ], 1 );
	}
	
	/**
	 *
	 * @param bigEndian
	 */
	private static void testDouble( final boolean bigEndian )
	{
		final double[] randomArray = new double[] { 12.2, -3.4 }; // getRandomDoubleArray();
		final double[] encodedRandomArray = MathUtils.decode( MathUtils.encode( randomArray, bigEndian ).getBytes(), bigEndian, true );
		Assert.assertTrue( Arrays.equals( randomArray, encodedRandomArray ) );
	}
	
	/**
	 *
	 * @param bigEndian
	 */
	private static void testFloat( final boolean bigEndian )
	{
		final float[] randomArray = getRandomFloatArray();
		final double[] encodedRandomArray = MathUtils.decode( MathUtils.encode( randomArray, bigEndian ).getBytes(), bigEndian, false );
		
		for( int i = 0; i < randomArray.length; i++ )
		{
			Assert.assertTrue( randomArray[ i ] == encodedRandomArray[ i ] );
		}
	}
	
	/**
	 *
	 * @return double[]
	 */
	private static double[] getRandomDoubleArray()
	{
		final int MAX_LENGTH = 1037;
		final Random random = new Random();
		
		final double[] randomArray = new double[ random.nextInt( MAX_LENGTH ) ];
		
		for( int i = 0; i < randomArray.length; i++ )
		{
			randomArray[ i ] = random.nextDouble();
		}
		
		return randomArray;
	}
	
	/**
	 *
	 * @return float[]
	 */
	private static float[] getRandomFloatArray()
	{
		final int MAX_LENGTH = 1037;
		final Random random = new Random();
		
		final float[] randomArray = new float[ random.nextInt( MAX_LENGTH ) ];
		
		for( int i = 0; i < randomArray.length; i++ )
		{
			randomArray[ i ] = random.nextFloat();
		}
		
		return randomArray;
	}
}