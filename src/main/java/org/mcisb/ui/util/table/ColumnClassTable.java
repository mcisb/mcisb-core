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
public class ColumnClassTable extends JTable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param model
	 */
	public ColumnClassTable( final TableModel model )
	{
		super( model );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JTable#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass( final int column )
	{
		if( getRowCount() > 0 )
		{
			final int FIRST = 0;
			final Object object = getValueAt( FIRST, column );

			if( object != null )
			{
				return object.getClass();
			}
		}

		return Object.class;
	}
}