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
import java.io.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class AbstractTask implements Runnable, Task, PropertyChangeListener
{
	/**
	 * 
	 */
	protected final PropertyChangeSupport support = new PropertyChangeSupport( this );

	/**
	 * 
	 */
	protected Exception exception;

	/**
	 * 
	 */
	private String message;

	/**
	 * 
	 */
	private int progress = READY;

	/**
	 * 
	 */
	private Object returnValue;

	/**
	 * 
	 * @return Object
	 * @throws Exception
	 */
	public Object runTask() throws Exception
	{
		returnValue = doTask();
		return returnValue;
	}

	/**
	 * 
	 * @return Object
	 */
	public Object getReturnValue()
	{
		return returnValue;
	}

	/**
	 * 
	 * @return Exception
	 */
	public Exception getException()
	{
		return exception;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		try
		{
			returnValue = runTask();
		}
		catch( Exception e )
		{
			exception = e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
	 * PropertyChangeEvent)
	 */
	@Override
	public void propertyChange( final PropertyChangeEvent e )
	{
		firePropertyChange( e );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.util.Task#cancel()
	 */
	@SuppressWarnings("unused")
	@Override
	public void cancel() throws Exception
	{
		setProgress( CANCELLED );
	}

	/**
	 * 
	 * @param message
	 */
	public void setMessage( String message )
	{
		String oldMessage = this.message;
		this.message = message;
		support.firePropertyChange( MESSAGE, oldMessage, this.message );
	}

	/**
	 * 
	 * @param progress
	 */
	public void setProgress( int progress )
	{
		int oldProgress = this.progress;
		this.progress = progress;
		support.firePropertyChange( PROGRESS, Integer.valueOf( oldProgress ), Integer.valueOf( this.progress ) );
	}

	/**
	 * 
	 */
	@Override
	public void addPropertyChangeListener( final PropertyChangeListener listener )
	{
		support.addPropertyChangeListener( listener );
	}

	/**
	 * 
	 */
	@Override
	public void removePropertyChangeListener( final PropertyChangeListener listener )
	{
		support.removePropertyChangeListener( listener );
	}

	/**
	 * 
	 * @param e
	 */
	protected void firePropertyChange( PropertyChangeEvent e )
	{
		support.firePropertyChange( e.getPropertyName(), e.getOldValue(), e.getNewValue() );
	}

	/**
	 * 
	 * @return Serializable
	 * @throws Exception
	 */
	protected abstract Serializable doTask() throws Exception;
}
