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
package org.mcisb.ui.util;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import org.mcisb.ui.tracking.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class ObjectParameterPanel extends ParameterPanel implements ListSelectionListener, Manager
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected final static ResourceBundle resourceBundle = ResourceBundle.getBundle( "org.mcisb.ui.tracking.messages" ); //$NON-NLS-1$

	/**
	 * 
	 */
	protected final JList<?> objectList;

	/**
	 * 
	 */
	protected int previousIndex = -1;

	/**
	 * 
	 * @param title
	 * @param objectList
	 * @param hasToolbar
	 */
	public ObjectParameterPanel( final String title, final JList<?> objectList, final boolean hasToolbar )
	{
		super( title );
		this.objectList = objectList;

		// Create and configure components:
		objectList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

		// Listen to objectList:
		objectList.addListSelectionListener( this );

		// Create and add toolbar:
		if( hasToolbar )
		{
			JToolBar toolbar = new JToolBar( SwingConstants.VERTICAL );
			toolbar.add( new NewAction( this ) );
			toolbar.add( new DeleteAction( this ) );
			toolbar.setFloatable( false );
			toolbar.setMargin( new Insets( GridBagPanel.DEFAULT_INSET, 0, 0, GridBagPanel.DEFAULT_INSET ) );
			innerPanel.add( toolbar, BorderLayout.EAST );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.Disposable#dispose()
	 */
	@SuppressWarnings("unused")
	@Override
	public void dispose() throws Exception
	{
		objectList.removeListSelectionListener( this );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event
	 * .ListSelectionEvent)
	 */
	@Override
	public void valueChanged( @SuppressWarnings("unused") ListSelectionEvent e )
	{
		if( previousIndex != -1 )
		{
			save( objectList.getModel().getElementAt( previousIndex ) );
		}

		previousIndex = objectList.getSelectedIndex();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.tracking.Manager#saveObject()
	 */
	@Override
	public void saveObject()
	{
		save( objectList.getSelectedValue() );
	}

	/**
	 * 
	 * @return Object
	 * @throws Exception
	 */
	public abstract Object getObject() throws Exception;

	/**
	 * 
	 * @param name
	 * @param model
	 * @return String
	 */
	protected String getUniqueName( String name, ListModel<?> model )
	{
		int[] count = new int[] { 0 };
		return getUniqueName( name, getNewName( name, count ), model, count );
	}

	/**
	 * 
	 * @return UniqueObject
	 */
	protected static UniqueObject getUniqueObject()
	{
		return new UniqueObject( StringUtils.getUniqueId() );
	}

	/**
	 * 
	 * @param label
	 */
	protected abstract void save( Object label );

	/**
	 * 
	 * @param name
	 * @param newName
	 * @param model
	 * @param count
	 * @return String
	 */
	private String getUniqueName( String name, String newName, ListModel<?> model, int[] count )
	{
		for( int i = 0; i < model.getSize(); i++ )
		{
			String existingName = model.getElementAt( i ).toString();

			if( newName.equals( existingName ) )
			{
				return getUniqueName( name, getNewName( name, count ), model, count );
			}
		}

		return newName;
	}

	/**
	 * 
	 * @param name
	 * @param count
	 * @return String
	 */
	private static String getNewName( String name, int[] count )
	{
		final int FIRST = 0;
		count[ FIRST ]++;
		return name + " " + count[ FIRST ]; //$NON-NLS-1$
	}
}
