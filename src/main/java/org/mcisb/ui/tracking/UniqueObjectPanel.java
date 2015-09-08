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

import javax.swing.*;

import org.mcisb.ui.util.*;
import org.mcisb.ui.util.list.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class UniqueObjectPanel extends ObjectParameterPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final String defaultName;

	/**
	 * 
	 * @param title
	 * @param defaultNameKey
	 */
	public UniqueObjectPanel( final String title, final String defaultNameKey )
	{
		super( title, new UniqueObjectList( new DefaultMutableListModel(), new DefaultListCellEditor( new JTextField() ) ), true );
		defaultName = resourceBundle.getString( defaultNameKey );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.tracking.Manager#newObject()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void newObject() throws Exception
	{
		ListModel<?> listModel = objectList.getModel();

		if( listModel instanceof DefaultListModel )
		{
			UniqueObject object = getUniqueObject();
			object.setName( getUniqueName( defaultName, listModel ) );
			( (DefaultListModel<UniqueObject>)listModel ).addElement( object );
			objectList.setSelectedValue( object, true );
			setValid();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.tracking.Manager#deleteObject()
	 */
	@Override
	public void deleteObject() throws Exception
	{
		previousIndex = -1;

		ListModel<?> listModel = objectList.getModel();

		if( listModel instanceof DefaultListModel )
		{
			( (DefaultListModel<?>)listModel ).removeElement( objectList.getSelectedValue() );
			setValid();
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	protected abstract void setValid() throws Exception;
}