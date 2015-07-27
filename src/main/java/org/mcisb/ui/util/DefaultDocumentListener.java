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

import javax.swing.event.*;
import javax.swing.text.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class DefaultDocumentListener extends PropertyChangeSupported implements DocumentListener
{
	/**
	 * 
	 */
	public static final String TEXT = "TEXT"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private String text = null;
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void changedUpdate( DocumentEvent e )
	{
		try
		{
			update( e );
		}
		catch( BadLocationException ex )
		{
			ex.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void insertUpdate( DocumentEvent e )
	{
		try
		{
			update( e );
		}
		catch( BadLocationException ex )
		{
			ex.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void removeUpdate( DocumentEvent e )
	{
		try
		{
			update( e );
		}
		catch( BadLocationException ex )
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param e
	 * @throws BadLocationException 
	 */
	private void update( final DocumentEvent e ) throws BadLocationException
	{
		final String oldText = text;
		text = e.getDocument().getText( 0, e.getDocument().getLength() );
		support.firePropertyChange( TEXT, oldText, text );
	}
}