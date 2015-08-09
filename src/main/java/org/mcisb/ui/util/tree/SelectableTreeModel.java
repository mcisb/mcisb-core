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

import java.util.*;
import javax.swing.tree.*;

/**
 * @author Neil Swainston
 * 
 */
public class SelectableTreeModel extends DefaultTreeModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final Map<Object,TreeNode> objectToTreeNode = new HashMap<>();

	/**
	 * @param root
	 */
	public SelectableTreeModel( final TreeNode root )
	{
		super( root );

		if( root instanceof DefaultMutableTreeNode )
		{
			objectToTreeNode.put( ( (DefaultMutableTreeNode)root ).getUserObject(), root );
		}
	}

	/**
	 * 
	 * @param userObject
	 * @return TreeNode
	 */
	public TreeNode getNode( final Object userObject )
	{
		return objectToTreeNode.get( userObject );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.DefaultTreeModel#insertNodeInto(javax.swing.tree.
	 * MutableTreeNode, javax.swing.tree.MutableTreeNode, int)
	 */
	@Override
	public void insertNodeInto( final MutableTreeNode newChild, final MutableTreeNode parent, final int index )
	{
		super.insertNodeInto( newChild, parent, index );

		if( newChild instanceof DefaultMutableTreeNode )
		{
			objectToTreeNode.put( ( (DefaultMutableTreeNode)newChild ).getUserObject(), newChild );
		}
	}

	/**
	 * 
	 * @param newChild
	 * @param parent
	 */
	public void insertNodeInto( MutableTreeNode newChild, MutableTreeNode parent )
	{
		insertNodeInto( newChild, parent, parent.getChildCount() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.tree.DefaultTreeModel#removeNodeFromParent(javax.swing.tree
	 * .MutableTreeNode)
	 */
	@Override
	public void removeNodeFromParent( MutableTreeNode node )
	{
		super.removeNodeFromParent( node );

		if( node instanceof DefaultMutableTreeNode )
		{
			objectToTreeNode.remove( ( (DefaultMutableTreeNode)node ).getUserObject() );
		}
	}
}