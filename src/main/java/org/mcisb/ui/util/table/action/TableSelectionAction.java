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
package org.mcisb.ui.util.table.action;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import org.mcisb.util.*;

/**
 *
 * @author Neil Swainston
 */
public abstract class TableSelectionAction extends AbstractAction implements ListSelectionListener, Disposable
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
	 * @param table
	 * @param name
	 */
	public TableSelectionAction( final JTable table, final String name )
	{
		super( name );
		this.table = table;
		setEnabled( false );
		table.getSelectionModel().addListSelectionListener( this );
	}

	/**
	 * 
	 * @return Collection
	 */
	protected Collection<Object> getSelection()
	{
		final Collection<Object> selection = new ArrayList<>();
		final ListSelectionModel rowSelectionModel = table.getSelectionModel();
		final ListSelectionModel columnSelectionModel = table.getColumnModel().getSelectionModel();
		
		for( int row = 0; row < table.getRowCount(); row++ )
		{
			if( rowSelectionModel.isSelectedIndex( row ) )
			{
    			for( int column = 0; column < table.getColumnCount(); column++ )
    			{
    				if( columnSelectionModel.isSelectedIndex( column ) )
        			{
        				final Object object = table.getValueAt( row, column );
        				
        				if( object != null )
        				{
        					selection.add( object );
        				}
        			}
    			}
			}
		}
		
		return selection;
    }
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged( ListSelectionEvent e )
	{
		if( !e.getValueIsAdjusting() )
		{
			setEnabled( getSelection().size() > 0 );
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed( ActionEvent e )
	{
		performAction( e );
		table.clearSelection();
	}

	/*
	 * (non-Javadoc)
	 * @see org.mcisb.util.Disposable#dispose()
	 */
	@Override
	public void dispose()
	{
		table.getSelectionModel().removeListSelectionListener( this );
	}
	
	/**
	 *
	 * @param e
	 */
	protected abstract void performAction( final ActionEvent e );
}