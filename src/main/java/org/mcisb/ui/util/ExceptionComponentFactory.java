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
package org.mcisb.ui.util;

import java.awt.*;
import javax.swing.*;
import org.mcisb.ui.util.action.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class ExceptionComponentFactory
{
	/**
	 * 
	 */
	private final boolean reportErrors;
	
	/**
	 * 
	 * @param reportErrors
	 */
	public ExceptionComponentFactory( final boolean reportErrors )
	{
		this.reportErrors = reportErrors;
	}
	
	/**
	 * 
	 */
	public ExceptionComponentFactory()
	{
		this( false );
	}
	
	/**
	 * 
	 * @param error
	 * @param e
	 * @return Container
	 */
	public Container getExceptionPanel( final String error, final Exception e )
	{
		final String text = ExceptionUtils.toString( e );
		
		final InformationPanel informationPanel = new InformationPanel( error );
		informationPanel.setText( text );

		final JPanel panel = new JPanel( new BorderLayout() );
		panel.add( informationPanel, BorderLayout.CENTER );
		
		if( reportErrors )
		{
			final ReportErrorAction reportErrorAction = new ReportErrorAction();
			reportErrorAction.setError( text );
			
			final JToolBar toolbar = new JToolBar();
			toolbar.add( reportErrorAction );
			toolbar.setFloatable( false );
			toolbar.setOpaque( false );
			toolbar.setBorderPainted( false );
			
			final JPanel toolbarPanel = new JPanel( new BorderLayout() );
			toolbarPanel.setBorder( BorderFactory.createEmptyBorder( GridBagPanel.DEFAULT_INSET, GridBagPanel.DEFAULT_INSET, GridBagPanel.DEFAULT_INSET, GridBagPanel.DEFAULT_INSET ) );
			toolbarPanel.add( toolbar, BorderLayout.EAST );
			
			panel.add( toolbarPanel, BorderLayout.SOUTH );
		}
		
		return panel;
	}
	
	/**
	 * 
	 * @param owner
	 * @param error
	 * @param e
	 * @return JDialog
	 */
	public JDialog getExceptionDialog( final Container owner, final String error, final Exception e )
	{
		JDialog dialog;
		
		if( owner instanceof Dialog )
		{
			dialog = new JDialog( (Dialog)owner, error, true );
		}
		else if( owner instanceof Frame )
		{
			dialog = new JDialog( (Frame)owner, error, true );
		}
		else
		{
			dialog = new JDialog();
			dialog.setTitle( error );
			dialog.setModal( true );
		}
		
		dialog.setContentPane( getExceptionPanel( error, e ) );
		dialog.pack();
		return dialog;
	}
}