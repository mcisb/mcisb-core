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
package org.mcisb.ui.util.action;

import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import org.mcisb.ui.util.*;
import org.mcisb.util.*;
import org.mcisb.util.net.*;

/**
 * 
 * @author Neil Swainston
 */
public class ReportErrorAction extends AbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final static ResourceBundle resourceBundle = ResourceBundle.getBundle( "org.mcisb.ui.util.action.messages" ); //$NON-NLS-1$

	/**
	 * 
	 */
	private final static String TAB = "\t"; //$NON-NLS-1$

	/**
	 * 
	 */
	private final static String osDetails = System.getProperty( "line.separator" ) + System.getProperty( "os.name" ) + TAB + System.getProperty( "os.arch" ) + TAB + System.getProperty( "os.version" ); //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$ //$NON-NLS-4$

	/**
	 * 
	 */
	private String error;

	/**
	 * 
	 */
	public ReportErrorAction()
	{
		super( resourceBundle.getString( "ReportErrorAction.name" ), new ResourceFactory().getImageIcon( resourceBundle.getString( "ReportErrorAction.icon" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
		final Integer keyEventValue = Integer.valueOf( KeyEvent.VK_M );
		putValue( ACCELERATOR_KEY, keyEventValue );
		putValue( MNEMONIC_KEY, keyEventValue );
		putValue( SHORT_DESCRIPTION, getValue( NAME ) );
		setEnabled( false );
	}

	/**
	 * 
	 * 
	 * @param error
	 */
	public void setError( final String error )
	{
		this.error = error;
		setEnabled( error != null && error.length() > 0 );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed( @SuppressWarnings("unused") final ActionEvent e )
	{
		final String EMAIL_ADDRESS = resourceBundle.getString( "ReportErrorAction.eMailAddress" ); //$NON-NLS-1$
		final String ERROR_MESSAGE = resourceBundle.getString( "ReportErrorAction.errorMessage" ); //$NON-NLS-1$
		URL url;

		try
		{
			url = new URL( "mailto:" + EMAIL_ADDRESS + "?subject=" + ERROR_MESSAGE + "&body=" + error + osDetails ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			BrowserLauncher.openURL( url );
		}
		catch( Exception ex )
		{
			final JDialog errorDialog = new ExceptionComponentFactory().getExceptionDialog( null, ExceptionUtils.toString( ex ), ex );
			ComponentUtils.setLocationCentral( errorDialog );
			errorDialog.setVisible( true );
		}
	}
}