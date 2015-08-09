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
public class JMenuMouseListener extends MouseAdapter
{
	/**
	 * 
	 */
	private final JPopupMenu menu;

	/**
	 * 
	 * @param menu
	 */
	public JMenuMouseListener( final JPopupMenu menu )
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
		switch( e.getModifiers() )
		{
			case InputEvent.BUTTON3_MASK:
			{
				menu.show( e.getComponent(), e.getX(), e.getY() );
				break;
			}
			default:
			{
				break;
			}
		}
	}
}