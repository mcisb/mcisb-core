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
public class JPopupMenuListener extends MouseAdapter
{
	/**
	 * 
	 */
	private final JPopupMenu menu;

	/**
	 * 
	 * 
	 * @param menu
	 */
	public JPopupMenuListener( final JPopupMenu menu )
	{
		this.menu = menu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed( final MouseEvent e )
	{
		showPopup( e );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased( final MouseEvent e )
	{
		showPopup( e );
	}

	/**
	 * 
	 * @param e
	 */
	private void showPopup( final MouseEvent e )
	{
		if( e.isPopupTrigger() )
		{
			menu.show( e.getComponent(), e.getX(), e.getY() );
		}
	}
}