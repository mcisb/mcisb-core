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

/**
 * 
 * @author Neil Swainston
 */
public class TextProgressPanel extends ProgressPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final String LINE_SEPARATOR = System.getProperty( "line.separator" ); //$NON-NLS-1$

	/**
	 * 
	 */
	protected final JTextArea textArea = new JTextArea();

	/**
	 * 
	 */
	private final boolean appendMessages;

	/**
	 * 
	 * 
	 * @param title
	 * @param displayPreferredSize
	 * @param appendMessages
	 */
	public TextProgressPanel( final String title, final Dimension displayPreferredSize, final boolean appendMessages )
	{
		super( title, displayPreferredSize );
		this.appendMessages = appendMessages;

		textArea.setEditable( false );

		display.setViewportView( textArea );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.ProgressPanel#setMessage(java.lang.String)
	 */
	@Override
	public void setMessage( String message )
	{
		final String existingText = textArea.getText();
		textArea.setText( ( appendMessages ) ? existingText + ( ( existingText.length() == 0 ) ? "" : LINE_SEPARATOR ) + message : message ); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.ProgressPanel#clearMessage()
	 */
	@Override
	public void clearMessage()
	{
		textArea.setText( "" ); //$NON-NLS-1$
	}
}