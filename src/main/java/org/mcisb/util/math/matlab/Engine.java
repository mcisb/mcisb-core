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

/**
 * 
 * @author Neil Swainston
 */
class Engine
{
	/**
	 * Calls the function <code>engOpen</code> of Matlab's C Engine library.
	 * Excerpts from the Matlab documentation: "On UNIX systems, if startcmd is
	 * NULL or the empty string, <code>engOpen</code> starts MATLAB on the
	 * current host using the command <code>matlab</code>. If startcmd is a
	 * hostname, <code>engOpen
	 * </code> starts MATLAB on the designated host by embedding the specified
	 * hostname string into the larger string:
	 * <code>rsh hostname \"/bin/csh -c 'setenv DISPLAY\ hostname:0; matlab'\"
	 * </code>. If startcmd is any other string (has white space in it, or
	 * nonalphanumeric characters), the string is executed literally to start
	 * MATLAB. On Windows, the startcmd string must be NULL."<br>
	 * See the Matlab documentation, chapter "External Interfaces/API
	 * Reference/C Engine Functions" for further information.
	 * 
	 * @param startcmd
	 *            The start command string.
	 * @throws java.io.IOException
	 *             Thrown if starting the process was not successful.
	 */
	native void open( String startcmd ) throws java.io.IOException;

	/**
	 * Calls the function <code>engClose</code> of Matlab's C Engine library.
	 * This routine allows you to quit a MATLAB engine session.
	 * 
	 * @throws java.io.IOException
	 *             Thrown if Matlab could not be closed.
	 */
	native void close() throws java.io.IOException;

	/**
	 * Calls the function <code>engEvalString</code> of Matlab's C Engine
	 * library. Evaluates the expression contained in string for the MATLAB
	 * engine session previously started by <code>open</code>. See the Matlab
	 * documentation, chapter "External Interfaces/API Reference/C Engine
	 * Functions" for further information.
	 * 
	 * @param str
	 *            Expression to be evaluated.
	 * @throws java.io.IOException
	 *             Thrown if data could not be sent to Matlab. This may happen
	 *             if Matlab is no longer running.
	 * @see #open
	 */
	native void evalString( String str ) throws java.io.IOException;

	/**
	 * Reads a maximum of <code>numberOfChars</code> characters from the Matlab
	 * output buffer and returns the result as String.<br>
	 * If the number of available characters exceeds <code>numberOfChars</code>,
	 * the additional data is discarded. The Matlab process must be opened
	 * previously with <code>open()</code>.<br>
	 * 
	 * @param numberOfChars
	 * @return String containing the Matlab output.
	 * @see #open
	 */
	native String getOutputString( int numberOfChars );

	/**
	 * Initialise the native shared library.
	 */
	static
	{
		System.loadLibrary( "Matlab" ); //$NON-NLS-1$
	}
}