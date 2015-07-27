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

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import org.mcisb.ui.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class TablePanel extends ComponentPanel implements ListSelectionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private final int column;
	
	
	/**
	 * 
	 * @param title
	 * @param table
	 * @param column
	 * @param valid
	 */
	public TablePanel( final String title, final JTable table, int column, boolean valid )
	{
		super( title, table );
		
		this.column = column;
		
		setValid( valid );
		table.getSelectionModel().addListSelectionListener( this );
	}
	
	/**
	 * 
	 * @return Collection
	 */
	public Collection<Object> getSelection()
	{
		final Collection<Object> selection = new ArrayList<>();
		
		if( component instanceof JTable )
		{
			final JTable table = (JTable)component;
			final ListSelectionModel listSelectionModel = table.getSelectionModel();
			
			for( int row = 0; row < table.getRowCount(); row++ )
			{
				if( listSelectionModel.isSelectedIndex( row ) )
				{
					selection.add( table.getValueAt( row, table.convertColumnIndexToView( column ) ) );
				}
			}
		}
		
		return selection;
    }
	
	/*
	 *  
	 * (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged( ListSelectionEvent e )
	{
		if( !e.getValueIsAdjusting() )
		{
			final ListSelectionModel listSelectionModel = (ListSelectionModel)e.getSource();
			setValid( !listSelectionModel.isSelectionEmpty() );
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see org.mcisb.util.Disposable#dispose()
	 */
	@Override
	public void dispose()
	{
		if( component instanceof JTable )
		{
			( (JTable)component ).getSelectionModel().removeListSelectionListener( this );
		}
	}
}