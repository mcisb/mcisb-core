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
package org.mcisb.ui.util.tree;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import org.mcisb.ui.util.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class SelectableTree extends JTree implements Chooser
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param model
	 */
	public SelectableTree( final SelectableTreeModel model )
	{
		super( model );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JTree#getToolTipText(java.awt.event.MouseEvent)
	 */
	@Override
	public final String getToolTipText( final MouseEvent evt )
	{
		final TreePath path = getPathForLocation( evt.getX(), evt.getY() );

		if( path != null )
		{
			final Object node = path.getLastPathComponent();

			if( node instanceof DefaultMutableTreeNode )
			{
				final DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)node;
				final Object userObject = treeNode.getUserObject();

				if( userObject instanceof UniqueObject )
				{
					return ( (UniqueObject)userObject ).getDescription();
				}
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#createToolTip()
	 */
	@Override
	public JToolTip createToolTip()
	{
		// TODO: sort out tooltip
		final JToolTip toolTip = super.createToolTip();
		toolTip.setMaximumSize( new Dimension( 100, Integer.MAX_VALUE ) );
		return toolTip;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.Chooser#setSelection(java.lang.Object)
	 */
	@Override
	public void setSelection( Object object )
	{
		if( object != null )
		{
			if( treeModel instanceof DefaultTreeModel )
			{
				TreePath treePath = new TreePath( ( (DefaultTreeModel)treeModel ).getPathToRoot( getNode( object ) ) );
				setSelectionPath( treePath );
				scrollPathToVisible( treePath );
			}
			else
			{
				throw new UnsupportedOperationException();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.Chooser#getSelection()
	 */
	@Override
	public Object getSelection()
	{
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)getLastSelectedPathComponent();

		if( treeNode != null )
		{
			return treeNode.getUserObject();
		}

		return null;
	}

	/**
	 * 
	 * @param object
	 * @return TreeNode
	 */
	private TreeNode getNode( final Object object )
	{
		if( treeModel instanceof SelectableTreeModel )
		{
			return ( (SelectableTreeModel)treeModel ).getNode( object );
		}
		// else
		throw new UnsupportedOperationException();
	}
}