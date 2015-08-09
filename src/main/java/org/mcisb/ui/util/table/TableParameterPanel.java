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
package org.mcisb.ui.util.table;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import org.mcisb.ui.tracking.*;
import org.mcisb.ui.util.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class TableParameterPanel extends ParameterPanel implements Manager, ListSelectionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected final JTable table;

	/**
	 * 
	 */
	private final JToolBar toolbar = new JToolBar( SwingConstants.VERTICAL );

	/**
	 * 
	 */
	private final Action deleteAction = new DeleteAction( this );

	/**
	 * 
	 * @param title
	 * @param table
	 */
	public TableParameterPanel( final String title, final JTable table )
	{
		super( title );
		this.table = table;

		// Create and configure components:
		table.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		table.getSelectionModel().addListSelectionListener( this );
		innerPanel.add( new JScrollPane( table ), BorderLayout.CENTER );

		// Create and add toolbar:
		deleteAction.setEnabled( false );
		toolbar.add( new NewAction( this ) );
		toolbar.add( deleteAction );
		toolbar.setFloatable( false );
		toolbar.setMargin( new Insets( 0, GridBagPanel.DEFAULT_INSET, 0, GridBagPanel.DEFAULT_INSET ) );
		innerPanel.add( toolbar, BorderLayout.EAST );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event
	 * .ListSelectionEvent)
	 */
	@Override
	public void valueChanged( @SuppressWarnings("unused") ListSelectionEvent e )
	{
		final int selectedRow = table.getSelectedRow();
		deleteAction.setEnabled( selectedRow != -1 );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.Disposable#dispose()
	 */
	@Override
	public void dispose()
	{
		table.getSelectionModel().removeListSelectionListener( this );
		toolbar.removeAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.tracking.Manager#saveObject()
	 */
	@Override
	public void saveObject()
	{
		// Not implemented.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.tracking.Manager#deleteObject()
	 */
	@Override
	public void deleteObject()
	{
		final int selectedRow = table.getSelectedRow();

		if( selectedRow != -1 )
		{
			final TableModel model = table.getModel();

			if( model instanceof DefaultTableModel )
			{
				( (DefaultTableModel)model ).removeRow( selectedRow );
			}
		}
	}
}