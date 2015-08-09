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

import java.io.*;
import org.mcisb.util.*;
import org.mcisb.util.math.*;

/**
 * 
 * @author Neil Swainston
 */
class MatlabImplExecutor extends MatlabImpl
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.util.math.CalculatorImpl#getResults(java.lang.String)
	 */
	@Override
	public synchronized java.util.HashMap<String,Double> getResults( final String command ) throws java.lang.Exception
	{
		final String QUIT = ",quit"; //$NON-NLS-1$
		OutputStream os = null;

		try
		{
			os = new ByteArrayOutputStream();
			final Executor executor = new Executor( new String[] { "matlab", "-nojvm", "-nosplash", "-r", command + QUIT }, os, System.err ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

			if( ( (Integer)executor.doTask() ).intValue() != Executor.SUCCESS )
			{
				os.close();
				throw new IOException();
			}

			final String s = os.toString();
			os.close();
			return CalculatorUtils.processOutput( s );
		}
		finally
		{
			if( os != null )
			{
				os.close();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.util.math.CalculatorImpl#close()
	 */
	@Override
	public void close()
	{
		// No implementation.
	}
}