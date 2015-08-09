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

import javax.swing.*;
import javax.swing.table.*;

/**
 * 
 * @author Neil Swainston
 */
public class PlateTable extends JTable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public PlateTable()
	{
		getTableHeader().setReorderingAllowed( false );
		getTableHeader().setResizingAllowed( false );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JTable#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable( int row, int column )
	{
		if( column == PlateTableModel.ROW_IDENTIFIER )
		{
			return false;
		}

		return getValueAt( row, column ) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JTable#isCellSelected(int, int)
	 */
	@Override
	public boolean isCellSelected( int row, int column )
	{
		if( column == PlateTableModel.ROW_IDENTIFIER )
		{
			return false;
		}

		return super.isCellSelected( row, column );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JTable#getCellRenderer(int, int)
	 */
	@Override
	public TableCellRenderer getCellRenderer( int row, int column )
	{
		if( column == PlateTableModel.ROW_IDENTIFIER )
		{
			return getTableHeader().getDefaultRenderer();
		}

		return super.getCellRenderer( row, column );
	}
}