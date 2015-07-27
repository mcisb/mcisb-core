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
package org.mcisb.util.task;

import java.beans.*;

/**
 * 
 * @author Neil Swainston
 */
public interface Task
{
	/**
	 * 
	 */
	public static final int READY = Integer.MIN_VALUE;
	
	/**
	 * 
	 */
	public static final int INDETERMINATE = -555;
	
	/**
	 * 
	 */
	public static final int STARTED = 0;
	
	/**
	 * 
	 */
	public static final int CANCELLED = 111;
	
	/**
	 * 
	 */
	public static final int ERROR = 666;
	
	/**
	 * 
	 */
	public static final int FINISHED = Integer.MAX_VALUE;
	
	/**
	 * 
	 */
	public static final String MESSAGE = "MESSAGE"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	public static final String PROGRESS = "PROGRESS"; //$NON-NLS-1$
	
	/**
	 * 
	 * @param l
	 * @throws Exception
	 */
	public abstract void addPropertyChangeListener( PropertyChangeListener l ) throws Exception;

	/**
	 * 
	 * @param l
	 * @throws Exception
	 */
	public abstract void removePropertyChangeListener( PropertyChangeListener l ) throws Exception;

	/**
	 * 
	 * @throws Exception
	 */
	public abstract void cancel() throws Exception;
}
