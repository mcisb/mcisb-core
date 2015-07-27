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
import javax.swing.table.*;
import org.mcisb.tracking.*;

/**
 *
 * @author Neil Swainston
 */
public class PlateTableModel extends DefaultTableModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	static final int ROW_IDENTIFIER = 0;
	
	/**
	 * 
	 */
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	
	/**
	 *
	 * @param plate
	 */
	public PlateTableModel( final Plate plate )
	{
		setRowCount( plate.getNumberOfRows() );
		setColumnCount( plate.getNumberOfColumns() );
		
		for( Iterator<Spot> iterator = plate.getSpots().iterator(); iterator.hasNext(); )
		{
			final Spot spot = iterator.next();
			setValueAt( spot, spot.getRow(), spot.getColumn() );
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName( int column )
	{
		if( column == ROW_IDENTIFIER )
		{
			return EMPTY_STRING;
		}
		
		return Integer.toString( column );
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt( int row, int column )
	{
		if( column == ROW_IDENTIFIER )
		{
			return Character.valueOf( (char)( 'A' + row ) );
		}
		
		return super.getValueAt( row, column );
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt( Object value, int row, int column )
	{
		if( column == ROW_IDENTIFIER )
		{
			throw new UnsupportedOperationException();
		}
		
		super.setValueAt( value, row, column );
	}
}