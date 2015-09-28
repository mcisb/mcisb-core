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
package org.mcisb.ui.util.dnd;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.io.*;
import java.util.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class DefaultDropTargetListener extends PropertyChangeSupported implements DropTargetListener
{
	/**
	 * 
	 */
	private final Collection<Class<?>> supportedClasses;

	/**
	 * 
	 */
	private DataFlavor targetFlavor;

	/**
	 * 
	 * @param supportedClasses
	 */
	public DefaultDropTargetListener( final Collection<Class<?>> supportedClasses )
	{
		this.supportedClasses = supportedClasses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent
	 * )
	 */
	@Override
	public void dragEnter( final DropTargetDragEvent dtde )
	{
		// Accept or reject the drag.
		acceptOrRejectDrag( dtde );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
	 */
	@Override
	public void dragExit( final DropTargetEvent dte )
	{
		// No implementation
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent
	 * )
	 */
	@Override
	public void dragOver( final DropTargetDragEvent dtde )
	{
		// Accept or reject the drag
		acceptOrRejectDrag( dtde );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.
	 * DropTargetDragEvent)
	 */
	@Override
	public void dropActionChanged( final DropTargetDragEvent dtde )
	{
		// Accept or reject the drag
		acceptOrRejectDrag( dtde );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
	 */
	@Override
	public void drop( final DropTargetDropEvent dtde )
	{
		// Check the drop action
		if( ( dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE ) != 0 )
		{
			// Accept the drop and get the transfer data
			dtde.acceptDrop( dtde.getDropAction() );

			try
			{
				dtde.dropComplete( dropComponent( dtde.getTransferable().getTransferData( targetFlavor ), dtde.getLocation() ) );
			}
			catch( Exception e )
			{
				e.printStackTrace();
				dtde.dropComplete( false );
			}
		}
		else
		{
			dtde.rejectDrop();
		}
	}

	/**
	 * 
	 * @param o
	 * @param point
	 * @return boolean
	 * @throws IOException
	 * @throws UnsupportedFlavorException
	 */
	protected abstract boolean dropComponent( final Object o, final Point point ) throws IOException, UnsupportedFlavorException;

	/**
	 * 
	 * @param dtde
	 */
	private void acceptOrRejectDrag( final DropTargetDragEvent dtde )
	{
		final DataFlavor[] flavors = dtde.getCurrentDataFlavors();

		for( int i = 0; i < flavors.length; i++ )
		{
			final Class<?> dataClass = flavors[ i ].getRepresentationClass();

			for( Iterator<Class<?>> iterator = supportedClasses.iterator(); iterator.hasNext(); )
			{
				if( iterator.next().isAssignableFrom( dataClass ) )
				{
					targetFlavor = flavors[ i ];

					if( ( dtde.getSourceActions() & DnDConstants.ACTION_COPY_OR_MOVE ) == 0 )
					{
						dtde.rejectDrag();
					}
					else if( ( dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE ) == 0 )
					{
						// Not offering copy or move - suggest a copy
						dtde.acceptDrag( DnDConstants.ACTION_COPY );
					}
					else
					{
						// Offering an acceptable operation: accept
						dtde.acceptDrag( dtde.getDropAction() );
					}

					return;
				}
			}
		}

		dtde.rejectDrag();
	}
}