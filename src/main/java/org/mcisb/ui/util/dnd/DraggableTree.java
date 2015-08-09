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

import java.awt.dnd.*;
import javax.swing.tree.*;
import org.mcisb.ui.util.tree.*;

/**
 * 
 * @author Neil Swainston
 */
public class DraggableTree extends SearchableTree implements DragSourceListener, DragGestureListener
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
	private DefaultMutableTreeNode node = null;

	/**
	 * 
	 * @param newModel
	 * @param actions
	 */
	public DraggableTree( final DefaultTreeModel newModel, final int actions )
	{
		super( newModel );
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
		final TreePath path = getSelectionPath();

		if( path != null )
		{
			node = (DefaultMutableTreeNode)path.getLastPathComponent();
			source.startDrag( dge, DragSource.DefaultMoveNoDrop, new TransferableObject( node.getUserObject() ), this );
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
			( (DefaultTreeModel)treeModel ).removeNodeFromParent( node );
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
	public void dragEnter( @SuppressWarnings("unused") final DragSourceDragEvent dsde )
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
	public void dragExit( @SuppressWarnings("unused") final DragSourceEvent dse )
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
	public void dragOver( @SuppressWarnings("unused") final DragSourceDragEvent dsde )
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
	public void dropActionChanged( @SuppressWarnings("unused") final DragSourceDragEvent dsde )
	{
		// No implementation.
	}
}