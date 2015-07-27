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
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class SearchableTree extends JTree implements TreeModelListener, Disposable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private final Map<Object,TreeNode> objectToNode = new HashMap<>();
	
	/**
	 * 
	 */
	public SearchableTree()
	{
		init();
	}

	/**
	 * 
	 * @param value
	 */
	public SearchableTree( final Object[] value )
	{
		super( value );
		init();
	}

	/**
	 * 
	 * @param value
	 */
	public SearchableTree( final Vector<?> value )
	{
		super( value );
		init();
	}

	/**
	 * 
	 * @param value
	 */
	public SearchableTree( final Hashtable<?, ?> value )
	{
		super( value );
		init();
	}

	/**
	 * 
	 * @param root
	 */
	public SearchableTree( final TreeNode root )
	{
		super( root );
		init();
	}

	/**
	 * 
	 * @param newModel
	 */
	public SearchableTree( final TreeModel newModel )
	{
		super( newModel );
		init();
	}

	/**
	 * 
	 * @param root
	 * @param asksAllowsChildren
	 */
	public SearchableTree( final TreeNode root, final boolean asksAllowsChildren )
	{
		super( root, asksAllowsChildren );
		init();
	}
	
	/**
	 * 
	 * @param o
	 * @return TreeNode
	 */
	public TreeNode getTreeNode( final Object o )
	{
		return objectToNode.get( o );
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.TreeModelListener#treeNodesChanged(javax.swing.event.TreeModelEvent)
	 */
	@Override
	public void treeNodesChanged( @SuppressWarnings("unused") final TreeModelEvent e )
	{
		// No implementation.
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.TreeModelListener#treeNodesInserted(javax.swing.event.TreeModelEvent)
	 */
	@Override
	public void treeNodesInserted( final TreeModelEvent e )
	{
		final Object component = e.getTreePath().getLastPathComponent();
		
		if( component instanceof MutableTreeNode )
		{
			final Object userObject = ( (DefaultMutableTreeNode)component ).getUserObject();
			objectToNode.put( userObject, (TreeNode)component );
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.TreeModelListener#treeNodesRemoved(javax.swing.event.TreeModelEvent)
	 */
	@Override
	public void treeNodesRemoved( final TreeModelEvent e )
	{
		final Object component = e.getTreePath().getLastPathComponent();
		
		if( component instanceof MutableTreeNode )
		{
			final Object[] children = e.getChildren();
			
			for( int i = 0; i < children.length; i++ )
			{
				if( children[ i ] instanceof DefaultMutableTreeNode )
				{
					final Object userObject = ( (DefaultMutableTreeNode)children[ i ] ).getUserObject();
					objectToNode.remove( userObject );
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.TreeModelListener#treeStructureChanged(javax.swing.event.TreeModelEvent)
	 */
	@Override
	public void treeStructureChanged( @SuppressWarnings("unused") final TreeModelEvent e )
	{
		// No implementation.
	}

	/*
	 * (non-Javadoc)
	 * @see org.mcisb.util.Disposable#dispose()
	 */
	@Override
	public void dispose()
	{
		getModel().removeTreeModelListener( this );
	}
	
	/**
	 * 
	 */
	private void init()
	{
		final TreeModel model = getModel();
		update( model.getRoot() );
		model.addTreeModelListener( this );
	}
	
	/**
	 * 
	 * @param o
	 */
	private void update( Object o )
	{
		if( o instanceof MutableTreeNode )
		{
			final DefaultMutableTreeNode node = (DefaultMutableTreeNode)o;
			objectToNode.put( node.getUserObject(), node );
			
			for( int i = 0; i < node.getChildCount(); i++ )
			{
				update( node.getChildAt( i ) );
			}
		}
	}
}