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

import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class AbstractGenericBeanTask extends AbstractTask implements GenericBeanTask
{
	/**
	 * 
	 */
	protected GenericBean bean;

	/**
	 * 
	 * @param bean
	 */
	public void setBean( final GenericBean bean )
	{
		this.bean = bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.util.task.Task#runTask(org.mcisb.util.GenericBean)
	 */
	@Override
	public Object runTask( GenericBean b ) throws Exception
	{
		setBean( b );
		return runTask();
	}
}
