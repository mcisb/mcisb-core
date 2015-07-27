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
package org.mcisb.util;

import java.util.*;
import org.junit.*;

/**
 * 
 *
 * @author Neil Swainston
 */
public class CollectionUtilsTest
{
	/**
	 * 
	 */
	private final Object CHAIR = "Chair"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private final Object CUPBOARD = "Cupboard"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private final Object SOFA = "Sofa"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private final Object TABLE = "Table"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private final Object[] array = new Object[] { TABLE, CUPBOARD, CHAIR, SOFA };
	
	/**
	 * 
	 */
	private final Object[] array2 = new Object[] { TABLE, CUPBOARD, TABLE, TABLE };
	
	/**
	 * 
	 */
	private final Object[] array3 = new Object[] { CHAIR, CUPBOARD, TABLE, SOFA };
	
	/**
	 * 
	 */
	private final Object[] array4 = new Object[] { CUPBOARD, CUPBOARD, TABLE, CUPBOARD };
	
	/**
	 * 
	 */
	private final Double[] doubleArray = new Double[] { Double.valueOf( 34.34534 ), Double.valueOf( -12e-7 ) };
	
	/**
	 *
	 */
	@Test 
	public void getFirstObject()
	{
		Assert.assertEquals( CollectionUtils.getFirst( new TreeSet<>( Arrays.asList( array ) ) ), CHAIR  );
	}
	
	/**
	 *
	 */
	@Test 	
	public void getFirstArray()
	{
		Assert.assertEquals( CollectionUtils.getFirst( array ), TABLE );
	}
	
	/**
	 *
	 */
	@Test 	
	public void getIntersection()
	{
		final Collection<? extends Collection<?>> collections = Arrays.asList( Arrays.asList( array ), Arrays.asList( array2 ), Arrays.asList( array3 ), Arrays.asList( array4 ) );
		final Collection<?> intersection = CollectionUtils.getIntersection( collections );
		final Collection<Object> expectedIntersection = new HashSet<>( Arrays.asList( TABLE, CUPBOARD ) );
		Assert.assertEquals( intersection, expectedIntersection );
	}
	
	/**
	 * 
	 */
	@Test
	public void toDoubleArray()
	{
		Assert.assertTrue( CollectionUtils.toDoubleArray( doubleArray ).length == 2 );
	}
}