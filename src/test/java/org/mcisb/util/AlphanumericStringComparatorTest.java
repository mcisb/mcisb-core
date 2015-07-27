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
public class AlphanumericStringComparatorTest
{
	/**
	 *
	 */
	@SuppressWarnings("static-method")
	@Test 
	public void sort()
	{
		final Collection<String> set = new TreeSet<>( new AlphanumericStringComparator() );
		set.addAll( Arrays.asList( "QWERTY.19QQ", "QWERTY19", "QWERTY0.18QQ", "QWERTY20.2T", "QWERTY190Q", "QWERTY19Q", "QWERTY20.00001" ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
		Assert.assertEquals( new ArrayList<>( set ), Arrays.asList( "QWERTY0.18QQ", "QWERTY.19QQ", "QWERTY19", "QWERTY19Q", "QWERTY20.00001", "QWERTY20.2T", "QWERTY190Q" ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
	}
}