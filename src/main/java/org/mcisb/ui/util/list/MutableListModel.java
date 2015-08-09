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
public interface MutableListModel extends ListModel<Object>
{
	/**
	 * 
	 * @param index
	 * @return boolean
	 */
	public boolean isCellEditable( int index );

	/**
	 * 
	 * @param value
	 * @param index
	 */
	public void setValueAt( Object value, int index );
}
