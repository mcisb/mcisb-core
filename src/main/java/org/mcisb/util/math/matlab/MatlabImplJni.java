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
import org.mcisb.util.math.*;

/**
 * 
 * @author Neil Swainston
 */
class MatlabImplJni extends MatlabImpl
{
	/**
	 * 
	 */
	private Engine engine;
	
	/*
	 * (non-Javadoc)
	 * @see org.mcisb.util.math.CalculatorImpl#getResults(java.lang.String)
	 */
	@Override
	public synchronized java.util.HashMap<String,Double> getResults( final String command ) throws java.lang.Exception
	{
		evalString( command );
		return getResults();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mcisb.util.math.CalculatorImpl#close()
	 */
	@Override
	public void close() throws java.io.IOException
	{
		getEngine().close();
	}
	
	/**
	 * 
	 * @return Engine
	 * @throws java.io.IOException
	 */
	private Engine getEngine() throws java.io.IOException
	{
		if( engine == null )
		{
			engine = new Engine();
			
			// Matlab start command:
			engine.open( "matlab -nosplash -nojvm" ); //$NON-NLS-1$
			
			// Add the current path to Matlab's path:
			engine.evalString( "addpath '" + new File( "" ).getAbsolutePath() + "';" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		
		return engine;
	}
	
	/**
	 * 
	 * @param command
	 * @throws java.io.IOException
	 */
	private void evalString( final String command ) throws java.io.IOException
	{
		getEngine().evalString( command );
	}
	
	/**
	 * 
	 *
	 * @return String
	 * @throws java.io.IOException
	 */
	private String getOutputString() throws java.io.IOException
	{
		final int NUMBER_OF_CHARACTERS = 512;
		return getEngine().getOutputString( NUMBER_OF_CHARACTERS );
	}
	
	/**
	 * 
	 * @return HashMap
	 * @throws java.lang.Exception
	 */
	private java.util.HashMap<String,Double> getResults() throws java.lang.Exception
	{
		return CalculatorUtils.processOutput( getOutputString() );
	}
}