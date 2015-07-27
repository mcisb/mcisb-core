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

import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @author Neil Swainston
 */
public class InformationPanel extends ParameterPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private final JTextArea textArea = new JTextArea();
	
	/**
	 * 
	 */
	private transient MouseListener mouseListener;
	
	/**
	 * 
	 *
	 * @param title
	 */
	public InformationPanel( final String title )
	{
		this( title, false );
	}
	
	/**
	 * 
	 *
	 * @param title
	 * @param editable
	 */
	public InformationPanel( final String title, final boolean editable ) 
	{
		super( title );
		setValid( true );
		textArea.setEditable( editable );
		
		if( editable )
		{
			textArea.addMouseListener( getMouseListener() );
		}

		JComponent scrollPane = new JScrollPane( textArea );
		fill( scrollPane );
	}
	
	/**
	 * 
	 * @param text
	 */
	public void setText( String text )
	{
		textArea.setText( text );
	}
	
	/**
	 *
	 * @return String
	 */
	public String getText()
	{
		return textArea.getText();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.mcisb.ui.util.Disposable#dispose()
	 */
	@Override
	public void dispose()
	{
		if( textArea.isEditable() )
		{
			textArea.removeMouseListener( getMouseListener() );
		}
	}
	
	/**
	 * 
	 * @return MouseListener
	 */
	private MouseListener getMouseListener()
	{
		if( mouseListener == null )
		{
			mouseListener = new JMenuMouseListener( new JTextComponentMenu() );
		}
		
		return mouseListener;
	}
}
