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
package org.mcisb.ui.util.data;

import java.awt.event.*;
import javax.swing.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class ResizableJLayeredPane extends JLayeredPane implements ComponentListener, Disposable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ResizableJLayeredPane()
	{
		addComponentListener( this );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.util.Disposable#dispose()
	 */
	@Override
	public void dispose()
	{
		removeComponentListener( this );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.
	 * ComponentEvent)
	 */
	@Override
	public void componentResized( final ComponentEvent e )
	{
		update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent
	 * )
	 */
	@Override
	public void componentShown( final ComponentEvent e )
	{
		update();
	}

	/**
	 * 
	 */
	private synchronized void update()
	{
		for( int i = 0; i < getComponentCount(); i++ )
		{
			getComponent( i ).setSize( getSize() );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.
	 * ComponentEvent)
	 */
	@Override
	public void componentHidden( final ComponentEvent e )
	{
		// No implementation.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent
	 * )
	 */
	@Override
	public void componentMoved( final ComponentEvent e )
	{
		// No implementation.
	}
}