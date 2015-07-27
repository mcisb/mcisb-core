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

import javax.swing.table.*;

/**
 *
 * @author Neil Swainston
 */
public class NonEditableTable extends ColumnClassTable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 * @param model
	 */
	public NonEditableTable( final TableModel model )
	{
		super( model );
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.JTable#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable( @SuppressWarnings("unused") final int row, @SuppressWarnings("unused") final int column )
	{
		return false;
	}
}