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

import java.io.*;

/**
 * 
 * @author Neil Swainston
 */
public interface Resetable extends Serializable
{
	/**
	 * 
	 * @throws Exception
	 */
	public void reset() throws Exception;
}