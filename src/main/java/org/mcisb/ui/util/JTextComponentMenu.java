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
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * 
 * @author Neil Swainston
 */
public class JTextComponentMenu extends JPopupMenu implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final static ResourceBundle resourceBundle = ResourceBundle.getBundle( "org.mcisb.ui.util.messages" ); //$NON-NLS-1$

	/**
	 * 
	 */
	final JMenuItem cutMenuItem = new JMenuItem( resourceBundle.getString( "JTextFieldMenu.cut" ) ); //$NON-NLS-1$

	/**
	 * 
	 */
	final JMenuItem copyMenuItem = new JMenuItem( resourceBundle.getString( "JTextFieldMenu.copy" ) ); //$NON-NLS-1$

	/**
	 * 
	 */
	final JMenuItem pasteMenuItem = new JMenuItem( resourceBundle.getString( "JTextFieldMenu.paste" ) ); //$NON-NLS-1$

	/**
	 * 
	 * @param editable
	 */
	public JTextComponentMenu( final boolean editable )
	{
		cutMenuItem.addActionListener( this );
		copyMenuItem.addActionListener( this );
		pasteMenuItem.addActionListener( this );

		if( editable )
		{
			add( cutMenuItem );
		}
		add( copyMenuItem );

		if( editable )
		{
			add( pasteMenuItem );
		}
	}

	/**
	 * 
	 */
	public JTextComponentMenu()
	{
		this( true );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed( final ActionEvent evt )
	{
		final Object source = evt.getSource();
		final Component component = getInvoker();

		if( component instanceof JTextComponent )
		{
			final JTextComponent textComponent = (JTextComponent)component;

			if( source == cutMenuItem )
			{
				textComponent.cut();
			}
			if( source == copyMenuItem )
			{
				textComponent.copy();
			}
			if( source == pasteMenuItem )
			{
				textComponent.paste();
			}
		}
	}
}