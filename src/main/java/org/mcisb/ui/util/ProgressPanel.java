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
package org.mcisb.ui.util;

import java.awt.*;
import java.beans.*;
import javax.swing.*;
import org.mcisb.ui.util.action.*;
import org.mcisb.util.*;
import org.mcisb.util.task.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class ProgressPanel extends ParameterPanel implements PropertyChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected final JProgressBar progressBar = new JProgressBar();

	/**
	 * 
	 */
	protected final JToolBar toolbar = new JToolBar();

	/**
	 * 
	 */
	protected final JScrollPane display = new JScrollPane();

	/**
	 * 
	 */
	protected final ReportErrorAction reportErrorAction = new ReportErrorAction();

	/**
	 * 
	 */
	private final Color defaultColor = progressBar.getForeground();

	/**
	 * 
	 * 
	 * @param title
	 * @param displayPreferredSize
	 */
	public ProgressPanel( final String title, final Dimension displayPreferredSize )
	{
		super( title );

		display.setPreferredSize( displayPreferredSize );

		progressBar.setIndeterminate( true );

		toolbar.setFloatable( false );
		toolbar.setOpaque( false );
		toolbar.setBorderPainted( false );

		final JPanel toolbarPanel = new JPanel( new BorderLayout() );
		toolbarPanel.add( toolbar, BorderLayout.EAST );

		add( display, 0, 0, true, true, true, false, GridBagConstraints.BOTH );
		add( toolbarPanel, 0, 1, true, true, false, false, GridBagConstraints.HORIZONTAL );
		add( progressBar, 0, 2, true, true, false, true, GridBagConstraints.HORIZONTAL );
	}

	/**
	 * 
	 * 
	 * @param e
	 */
	public void setException( final Exception e )
	{
		final String error = ExceptionUtils.toString( e );
		setMessage( error );
		setProgress( Task.ERROR );
		reportErrorAction.setError( error );
		toolbar.add( reportErrorAction );
	}

	/**
	 * 
	 * @param progress
	 */
	public void setProgress( int progress )
	{
		boolean indeterminate = false;
		Color foreground = defaultColor;

		switch( progress )
		{
			case Task.INDETERMINATE:
			{
				indeterminate = true;
				break;
			}
			case Task.ERROR:
			{
				foreground = Color.RED;
				break;
			}
			case Task.CANCELLED:
			{
				foreground = Color.YELLOW;
				break;
			}
			case Task.FINISHED:
			{
				foreground = Color.GREEN;
				break;
			}
			default:
			{
				break;
			}
		}

		progressBar.setIndeterminate( indeterminate );
		progressBar.setForeground( foreground );
		progressBar.setValue( progress );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.Disposable#dispose()
	 */
	@Override
	public void dispose() throws Exception
	{
		// No implementation.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
	 * PropertyChangeEvent)
	 */
	@Override
	public void propertyChange( PropertyChangeEvent e )
	{
		if( e.getPropertyName().equals( Task.MESSAGE ) )
		{
			setMessage( e.getNewValue().toString() );
		}
		else if( e.getPropertyName().equals( Task.PROGRESS ) )
		{
			setProgress( ( (Integer)e.getNewValue() ).intValue() );
		}
	}

	/**
	 * 
	 * @param message
	 */
	protected abstract void setMessage( String message );

	/**
	 * 
	 *
	 */
	protected abstract void clearMessage();
}