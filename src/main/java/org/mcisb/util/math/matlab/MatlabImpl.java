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
package org.mcisb.util.math.matlab;

import org.mcisb.util.math.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class MatlabImpl implements CalculatorImpl
{
	/**
	 * 
	 * @return MatlabImpl
	 */
	public static MatlabImpl getInstance()
	{
		final String MAC = "Mac OS X"; //$NON-NLS-1$

		if( System.getProperty( "os.name" ).contains( MAC ) ) //$NON-NLS-1$
		{
			return new MatlabImplExecutor();
		}

		return new MatlabImplJni();
	}
}