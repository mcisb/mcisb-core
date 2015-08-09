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
package org.mcisb.ui.util.data;

import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import org.mcisb.ui.util.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class ListManagerPanel extends JPanel implements ActionListener, PropertyChangeListener, Disposable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final String NEXT_INDEX = "NEXT_INDEX"; //$NON-NLS-1$

	/**
	 * 
	 */
	private static final String PREVIOUS_INDEX = "PREVIOUS_INDEX"; //$NON-NLS-1$

	/**
	 * 
	 */
	private final JButton previous = new JButton( new ResourceFactory().getImageIcon( "toolbarButtonGraphics/navigation/Back16.gif" ) ); //$NON-NLS-1$

	/**
	 * 
	 */
	private final JButton next = new JButton( new ResourceFactory().getImageIcon( "toolbarButtonGraphics/navigation/Forward16.gif" ) ); //$NON-NLS-1$

	/**
	 * 
	 */
	private final JLabel label = new JLabel();

	/**
	 * 
	 */
	private final Collection<ListManager> listManagers = new ArrayList<>();

	/**
	 * 
	 * @param listManager
	 */
	public ListManagerPanel( final ListManager listManager )
	{
		this( new ListManager[] { listManager } );
	}

	/**
	 * Assumes all ListManagers manage lists of the SAME length.
	 * 
	 * @param listManagers
	 */
	public ListManagerPanel( final ListManager[] listManagers )
	{
		this.listManagers.addAll( Arrays.asList( listManagers ) );

		for( ListManager listManager : this.listManagers )
		{
			listManager.addPropertyChangeListener( this );
		}

		previous.setActionCommand( PREVIOUS_INDEX );
		previous.addActionListener( this );
		next.setActionCommand( NEXT_INDEX );
		next.addActionListener( this );

		add( previous );
		add( label );
		add( next );
	}

	/**
	 * 
	 * @param e
	 */
	@Override
	public void propertyChange( final PropertyChangeEvent e )
	{
		if( e.getPropertyName().equals( ListManager.INDEX ) )
		{
			final String SEPARATOR = " / "; //$NON-NLS-1$
			label.setText( ( ( (Integer)e.getNewValue() ).intValue() + 1 ) + SEPARATOR + CollectionUtils.getFirst( listManagers ).getListSize() );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed( final ActionEvent e )
	{
		final String actionCommand = e.getActionCommand();

		if( actionCommand.equals( PREVIOUS_INDEX ) )
		{
			for( ListManager listManager : this.listManagers )
			{
				listManager.previous();
			}
		}
		else if( actionCommand.equals( NEXT_INDEX ) )
		{
			for( ListManager listManager : this.listManagers )
			{
				listManager.next();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.util.Disposable#dispose()
	 */
	@Override
	public void dispose()
	{
		for( ListManager listManager : this.listManagers )
		{
			listManager.removePropertyChangeListener( this );
		}

		previous.removeActionListener( this );
		next.removeActionListener( this );
	}
}