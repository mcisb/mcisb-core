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
package org.mcisb.ui.util.table.action;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.mcisb.ui.util.*;
import org.mcisb.ui.wizard.parameter.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class GroupAction extends TableSelectionAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle( "org.mcisb.ui.util.table.action.messages" ); //$NON-NLS-1$

	/**
	 * 
	 */
	private final GroupManager manager;

	/**
	 * 
	 * @param table
	 * @param manager
	 */
	public GroupAction( final JTable table, final GroupManager manager )
	{
		super( table, resourceBundle.getString( "GroupAction.title" ) ); //$NON-NLS-1$
		this.manager = manager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mcisb.ui.util.table.action.TableSelectionAction#performAction(java
	 * .awt.event.ActionEvent)
	 */
	@Override
	public void performAction( final ActionEvent e )
	{
		final String GROUP_NAME = "GROUP_NAME"; //$NON-NLS-1$
		final GenericBean bean = new GenericBean();
		final Map<Object,Object> propertyNameToKey = new HashMap<>();
		final Map<Object,Object> options = new LinkedHashMap<>();
		final String prompt = resourceBundle.getString( "GroupAction.namePrompt" ); //$NON-NLS-1$
		propertyNameToKey.put( GROUP_NAME, prompt );
		options.put( prompt, null );
		final DefaultParameterPanel component = new DefaultParameterPanel( resourceBundle.getString( "GroupAction.dialogTitle" ), options ); //$NON-NLS-1$

		try
		{
			final Container topLevelAncestor = table.getTopLevelAncestor();
			final JDialog dialog = new JDialog( ( topLevelAncestor instanceof Frame ) ? (Frame)topLevelAncestor : null, true );
			new DefaultParameterApp( dialog, resourceBundle.getString( "GroupAction.dialogTitle" ), bean, component, propertyNameToKey ).show(); //$NON-NLS-1$

			final Object group = bean.getString( GROUP_NAME );

			if( group != null )
			{
				if( manager.getGroup( group ) != null )
				{
					JOptionPane.showMessageDialog( dialog, resourceBundle.getString( "GroupAction.duplicateErrorMessage" ), resourceBundle.getString( "GroupAction.error" ), JOptionPane.ERROR_MESSAGE ); //$NON-NLS-1$ //$NON-NLS-2$
				}

				manager.addGroup( group, getSelection() );
			}
		}
		catch( Exception ex )
		{
			final JDialog dialog = new ExceptionComponentFactory().getExceptionDialog( null, resourceBundle.getString( "GroupAction.error" ), ex ); //$NON-NLS-1$
			ComponentUtils.setLocationCentral( dialog );
			dialog.setVisible( true );
		}
	}
}