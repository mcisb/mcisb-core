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

import java.beans.*;
import javax.swing.*;
import org.mcisb.util.*;

/**
 *
 * @author Neil Swainston
 */
public class PropertyChangeListenerListModel extends DefaultListModel<Object> implements PropertyChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* 
	 * (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange( final PropertyChangeEvent evt )
	{
		final Object source = evt.getSource();
		fireContentsChanged( source, indexOf( source ), indexOf( source ) );
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListModel#add(int, java.lang.Object)
	 */
	@Override
	public void add( int index, Object element )
	{
		addPropertyChangeListener( element );
		super.add( index, element );
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListModel#addElement(java.lang.Object)
	 */
	@Override
	public void addElement( Object obj )
	{
		addPropertyChangeListener( obj );
		super.addElement( obj );
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListModel#insertElementAt(java.lang.Object, int)
	 */
	@Override
	public void insertElementAt( Object obj, int index )
	{
		addPropertyChangeListener( obj );
		super.insertElementAt( obj, index );
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListModel#remove(int)
	 */
	@Override
	public Object remove( int index )
	{
		removePropertyChangeListener( elementAt( index ) );
		return super.remove( index );
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListModel#removeAllElements()
	 */
	@Override
	public void removeAllElements()
	{
		for( int i = 0; i < size(); i++ )
		{
			removePropertyChangeListener( elementAt( i ) );
		}
		
		super.removeAllElements();
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListModel#removeElement(java.lang.Object)
	 */
	@Override
	public boolean removeElement( Object obj )
	{
		removePropertyChangeListener( obj );
		return super.removeElement( obj );
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListModel#removeElementAt(int)
	 */
	@Override
	public void removeElementAt( int index )
	{
		removePropertyChangeListener( elementAt( index ) );
		super.removeElementAt( index );
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListModel#removeRange(int, int)
	 */
	@Override
	public void removeRange( int fromIndex, int toIndex )
	{
		for( int i = fromIndex; i < toIndex; i++ )
		{
			removePropertyChangeListener( elementAt( i ) );
		}
		
		super.removeRange( fromIndex, toIndex );
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListModel#setElementAt(java.lang.Object, int)
	 */
	@Override
	public void setElementAt( Object obj, int index )
	{
		addPropertyChangeListener( elementAt( index ) );
		super.setElementAt( obj, index );
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListModel#clear()
	 */
	@Override
	public void clear()
	{
		for( int i = 0; i < size(); i++ )
		{
			removePropertyChangeListener( elementAt( i ) );
		}
		
		super.clear();
	}

	/**
	 *
	 * @param element
	 */
	private void addPropertyChangeListener( final Object element )
	{
		if( element instanceof PropertyChangeSupported )
		{
			( (PropertyChangeSupported)element ).addPropertyChangeListener( this );
		}
	}
	
	/**
	 *
	 * @param element
	 */
	private void removePropertyChangeListener( final Object element )
	{
		if( element instanceof PropertyChangeSupported )
		{
			( (PropertyChangeSupported)element ).removePropertyChangeListener( this );
		}
	}
}