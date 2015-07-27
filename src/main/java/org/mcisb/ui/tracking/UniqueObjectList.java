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
package org.mcisb.ui.tracking;

import org.mcisb.ui.util.list.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class UniqueObjectList extends JMutableList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param dataModel
	 * @param editor
	 */
	public UniqueObjectList( final MutableListModel dataModel, final ListCellEditor editor )
	{
		super( dataModel, editor );
	}

    /**
     * 
     * @param value
     * @param index
     */
    @Override
	protected void setValueAt( Object value, int index )
    {
    	UniqueObject object = (UniqueObject)( (MutableListModel)getModel() ).getElementAt( index );
    	object.setName( (String)value );
    }
}
