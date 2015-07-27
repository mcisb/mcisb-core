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
package org.mcisb.util.math.octave;

import java.io.*;
import java.util.*;
import org.mcisb.util.*;
import org.mcisb.util.math.*;

/**
 * 
 * @author Neil Swainston
 */
public class OctaveImpl implements CalculatorImpl
{
	/**
	 * 
	 */
	private static OctaveImpl octaveImpl;
	
	/**
	 * 
	 */
	private OctaveImpl()
	{
		// No implementation;
	}
	
	/**
	 * 
	 * @return OctaveImpl
	 */
	public synchronized static OctaveImpl getInstance()
	{
		if( octaveImpl == null )
		{
			octaveImpl = new OctaveImpl();
		}
		
		return octaveImpl;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.mcisb.util.math.CalculatorImpl#close()
	 */
	@Override
	public void close()
	{
		// No implementation.
	}

	/* 
	 * (non-Javadoc)
	 * @see org.mcisb.util.math.CalculatorImpl#getResults(java.lang.String)
	 */
	@Override
	public HashMap<String, Double> getResults( String command ) throws Exception
	{
		// final String QUIT = ",quit"; //$NON-NLS-1$
		OutputStream os = null;
		
		try
		{
			os = new ByteArrayOutputStream();
			final Executor executor = new Executor( new String[] { "/Applications/Octave.app/Contents/Resources/bin/octave", "--silent", "--eval", "'" + command /* + QUIT */ + "'" }, os, System.err ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			
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
}
