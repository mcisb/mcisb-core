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

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import org.mcisb.ui.util.edit.*;

/**
 * 
 * @author Neil Swainston
 */
public class JMutableList extends JList<Object> implements MouseListener, CellEditorListener, Editable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	static final String TERMINATE_EDIT_ON_FOCUS_LOST = "terminateEditOnFocusLost"; //$NON-NLS-1$

	/**
	 * 
	 */
	private static final String PERMANANT_FOCUS_OWNER = "permanentFocusOwner"; //$NON-NLS-1$

	/**
     * 
     */
	private final ListCellEditor editor;

	/**
	 * 
	 */
	private Component editorComp;

	/**
     * 
     */
	private transient PropertyChangeListener editorRemover;

	/**
     * 
     */
	private int editingIndex = -1;

	/**
	 * 
	 * 
	 * @param dataModel
	 * @param editor
	 */
	public JMutableList( final MutableListModel dataModel, final ListCellEditor editor )
	{
		super( dataModel );

		this.editor = editor;

		final Action cancelEditingAction = new CancelEditingAction();
		cancelEditingAction.setEnabled( editor != null );

		final String START_EDITING = "start_editing"; //$NON-NLS-1$
		final String CANCEL_EDITING = "cancel_editing"; //$NON-NLS-1$
		ActionMap actionMap = getActionMap();
		actionMap.put( START_EDITING, new StartEditingAction() );
		actionMap.put( CANCEL_EDITING, cancelEditingAction );
		addMouseListener( this );
		getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_F2, 0 ), START_EDITING );
		getInputMap( JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT ).put( KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0 ), CANCEL_EDITING );
		putClientProperty( TERMINATE_EDIT_ON_FOCUS_LOST, Boolean.TRUE );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#removeNotify()
	 */
	@Override
	public void removeNotify()
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removePropertyChangeListener( PERMANANT_FOCUS_OWNER, editorRemover ); // NOI18N
		super.removeNotify();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.event.CellEditorListener#editingStopped(javax.swing.event
	 * .ChangeEvent)
	 */
	@Override
	public void editingStopped( final ChangeEvent e )
	{
		setValueAt( editor.getCellEditorValue(), editingIndex );
		removeEditor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.edit.Editable#edit()
	 */
	@Override
	public void edit()
	{
		if( !hasFocus() )
		{
			if( !editor.stopCellEditing() )
			{
				return;
			}
			requestFocus();
			return;
		}

		editCellAt( getSelectionModel().getAnchorSelectionIndex(), null );

		if( editorComp != null )
		{
			editorComp.requestFocus();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.Editable#getEditorComponent()
	 */
	@Override
	public Component getEditorComponent()
	{
		return editorComp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.Editable#terminateEditOnFocusLost()
	 */
	@Override
	public boolean terminateEditOnFocusLost()
	{
		return ( (Boolean)getClientProperty( JMutableList.TERMINATE_EDIT_ON_FOCUS_LOST ) ).booleanValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.Editable#getRoot()
	 */
	@Override
	public Component getRoot()
	{
		return SwingUtilities.getRoot( this );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.Editable#stopEditing()
	 */
	@Override
	public boolean stopEditing()
	{
		return editor.stopCellEditing();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.Editable#cancelEditing()
	 */
	@Override
	public void cancelEditing()
	{
		editor.cancelCellEditing();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.event.CellEditorListener#editingCanceled(javax.swing.event
	 * .ChangeEvent)
	 */
	@Override
	public void editingCanceled( final ChangeEvent e )
	{
		removeEditor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.Editable#removeEditor()
	 */
	@Override
	public void removeEditor()
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removePropertyChangeListener( PERMANANT_FOCUS_OWNER, editorRemover );
		editorRemover = null;

		editor.removeCellEditorListener( this );

		if( editorComp != null )
		{
			remove( editorComp );
		}

		Rectangle cellRect = getCellBounds( editingIndex, editingIndex );

		editingIndex = -1;
		editorComp = null;

		repaint( cellRect );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed( MouseEvent e )
	{
		if( e.isConsumed() || ( !( SwingUtilities.isLeftMouseButton( e ) && isEnabled() ) ) )
		{
			return;
		}

		Point p = e.getPoint();
		int index = locationToIndex( p );

		if( editCellAt( index, e ) )
		{
			Point p2 = SwingUtilities.convertPoint( this, p, editorComp );
			final Component dispatchComponent = SwingUtilities.getDeepestComponentAt( editorComp, p2.x, p2.y );

			// Check for isEditing() in case another event has caused the editor
			// to be removed. See bug #4306499.
			/*
			 * if( dispatchComponent == null || editorComp == null ) { return; }
			 */

			MouseEvent e2 = SwingUtilities.convertMouseEvent( this, e, dispatchComponent );
			dispatchComponent.dispatchEvent( e2 );
		}
		else if( isRequestFocusEnabled() )
		{
			requestFocus();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked( final MouseEvent e )
	{
		// No implementation.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered( final MouseEvent e )
	{
		// No implementation.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited( final MouseEvent e )
	{
		// No implementation.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased( final MouseEvent e )
	{
		// No implementation.
	}

	/**
	 * 
	 * @param value
	 * @param index
	 */
	protected void setValueAt( final Object value, final int index )
	{
		( (MutableListModel)getModel() ).setValueAt( value, index );
	}

	/**
	 * 
	 * @param index
	 * @param e
	 * @return boolean
	 */
	private boolean editCellAt( int index, EventObject e )
	{
		if( !editor.stopCellEditing() )
		{
			return false;
		}

		if( index < 0 || index >= getModel().getSize() )
		{
			return false;
		}

		ListModel<?> model = getModel();

		if( ( (MutableListModel)model ).isCellEditable( index ) )
		{
			if( editorRemover == null )
			{
				KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
				editorRemover = new EditorRemover( this, fm );
				fm.addPropertyChangeListener( PERMANANT_FOCUS_OWNER, editorRemover );
			}

			if( editor.isCellEditable( e ) )
			{
				requestFocusInWindow();
				editorComp = editor.getListCellEditorComponent( getModel().getElementAt( index ) );

				if( editorComp == null )
				{
					removeEditor();
					return false;
				}

				editorComp.setBounds( getCellBounds( index, index ) );
				add( editorComp );
				editorComp.validate();

				editingIndex = index;
				editor.addCellEditorListener( this );

				return true;
			}
		}

		return false;
	}
}