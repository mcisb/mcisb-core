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
import java.awt.dnd.*;
import java.util.*;
import javax.swing.*;

/**
 * @author Neil Swainston
 */
public abstract class DraggablePanel extends JPanel implements DragSourceListener, DragGestureListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final DragSource source = new DragSource();

	/**
	 * 
	 */
	private DraggableLabel draggableLabel;

	/**
	 * 
	 * @param supportedClasses
	 * @param actions
	 */
	public DraggablePanel( final Collection<Class<?>> supportedClasses, final int actions )
	{
		super( new FlowLayout( FlowLayout.LEFT, 0, 0 ) );
		setBackground( Color.WHITE );

		setDropTarget( new DropTarget( this, DnDConstants.ACTION_COPY_OR_MOVE, new PanelDropTargetListener( this, supportedClasses ), true, null ) );

		source.createDefaultDragGestureRecognizer( this, actions, this );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(java.awt.dnd.
	 * DragGestureEvent)
	 */
	@Override
	public void dragGestureRecognized( final DragGestureEvent dge )
	{
		final Component component = getComponentAt( dge.getDragOrigin() );

		if( component instanceof DraggableLabel )
		{
			draggableLabel = (DraggableLabel)component;
			source.startDrag( dge, DragSource.DefaultMoveNoDrop, new TransferableObject( draggableLabel.getUserObject() ), this );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.dnd.DragSourceListener#dragDropEnd(java.awt.dnd.DragSourceDropEvent
	 * )
	 */
	@Override
	public void dragDropEnd( final DragSourceDropEvent dsde )
	{
		if( dsde.getDropSuccess() && ( dsde.getDropAction() == DnDConstants.ACTION_MOVE ) )
		{
			remove( draggableLabel );
			validate();
			repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.dnd.DragSourceListener#dragEnter(java.awt.dnd.DragSourceDragEvent
	 * )
	 */
	@Override
	public void dragEnter( final DragSourceDragEvent dsde )
	{
		// No implementation.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.dnd.DragSourceListener#dragExit(java.awt.dnd.DragSourceEvent)
	 */
	@Override
	public void dragExit( final DragSourceEvent dse )
	{
		// No implementation.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.dnd.DragSourceListener#dragOver(java.awt.dnd.DragSourceDragEvent
	 * )
	 */
	@Override
	public void dragOver( final DragSourceDragEvent dsde )
	{
		// No implementation.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.dnd.DragSourceListener#dropActionChanged(java.awt.dnd.
	 * DragSourceDragEvent)
	 */
	@Override
	public void dropActionChanged( final DragSourceDragEvent dsde )
	{
		// No implementation.
	}
}