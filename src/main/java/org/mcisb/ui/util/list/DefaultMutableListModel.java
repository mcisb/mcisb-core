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
package org.mcisb.ui.util.list;

import javax.swing.*;

/**
 * 
 * @author Neil Swainston
 */
public class DefaultMutableListModel extends DefaultListModel<Object> implements MutableListModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.list.MutableListModel#isCellEditable(int)
	 */
	@Override
	public boolean isCellEditable( final int index )
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.list.MutableListModel#setValueAt(java.lang.Object,
	 * int)
	 */
	@Override
	public void setValueAt( Object value, int index )
	{
		setElementAt( value, index );
	}
}
