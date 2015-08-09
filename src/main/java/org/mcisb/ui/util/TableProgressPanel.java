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
package org.mcisb.ui.util;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import org.mcisb.util.*;
import org.mcisb.util.task.*;

/**
 * @author Neil Swainston
 * 
 */
public class TableProgressPanel extends ProgressPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String SEPARATOR = "\t"; //$NON-NLS-1$

	/**
	 * 
	 */
	private final DefaultTableModel model;

	/**
	 * @param title
	 * @param columnIdentifiers
	 * @param displayPreferredSize
	 */
	public TableProgressPanel( final String title, final String[] columnIdentifiers, final Dimension displayPreferredSize )
	{
		super( title, displayPreferredSize );
		model = new DefaultTableModel();
		model.setColumnIdentifiers( columnIdentifiers );
		display.setViewportView( new JTable( model ) );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.ProgressPanel#setException(java.lang.Exception)
	 */
	@Override
	public void setException( final Exception e )
	{
		final String error = ExceptionUtils.toString( e );
		setProgress( Task.ERROR );
		reportErrorAction.setError( error );
		toolbar.add( reportErrorAction );

		final JDialog errorDialog = new ExceptionComponentFactory().getExceptionDialog( getParent(), error, e );
		ComponentUtils.setLocationCentral( errorDialog );
		errorDialog.setVisible( true );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.ProgressPanel#setMessage(java.lang.String)
	 */
	@Override
	protected void setMessage( final String message )
	{
		model.addRow( message.split( SEPARATOR ) );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.ProgressPanel#clearMessage()
	 */
	@Override
	protected void clearMessage()
	{
		final int ZERO = 0;

		while( model.getRowCount() > ZERO )
		{
			model.removeRow( ZERO );
		}
	}
}