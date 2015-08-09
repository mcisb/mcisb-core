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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @author Neil Swainston
 */
public class ManipulatorPanel extends JPanel implements MouseListener, MouseMotionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final transient Manipulatable manipulatable;

	/**
	 * 
	 */
	private Point dragStart = null;

	/**
	 * 
	 */
	private Point dragCurrent = null;

	/**
	 * 
	 * @param manipulatable
	 */
	public ManipulatorPanel( final Manipulatable manipulatable )
	{
		this.manipulatable = manipulatable;
		setOpaque( false );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent( Graphics g0 )
	{
		super.paintComponent( g0 );

		if( dragStart != null && dragCurrent != null )
		{
			final int LINE_LENGTH = 3;
			final float FACTOR = 0.6667f;
			final int height = (int)( manipulatable.getHeight() * FACTOR );

			if( g0 instanceof Graphics2D )
			{
				final Graphics2D g = (Graphics2D)g0;
				g.setColor( Color.RED );
				g.drawLine( dragStart.x, height, dragCurrent.x, height );
				g.drawLine( dragStart.x, height - LINE_LENGTH, dragStart.x, height + LINE_LENGTH );
				g.drawLine( dragCurrent.x, height - LINE_LENGTH, dragCurrent.x, height + LINE_LENGTH );
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked( MouseEvent e )
	{
		if( e.getButton() == MouseEvent.BUTTON1 )
		{
			manipulatable.reset();
		}
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased( MouseEvent e )
	{
		if( e.getButton() == MouseEvent.BUTTON1 && dragStart != null && dragCurrent != null )
		{
			manipulatable.setXRangeByPosition( Math.min( dragStart.x, dragCurrent.x ), Math.max( dragStart.x, dragCurrent.x ) );
		}

		dragStart = null;
		dragCurrent = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent
	 * )
	 */
	@Override
	public void mouseDragged( MouseEvent e )
	{
		if( e.getButton() == MouseEvent.BUTTON1 )
		{
			if( dragStart == null )
			{
				dragStart = getValidPoint( e.getPoint() );
			}

			dragCurrent = getValidPoint( e.getPoint() );
			repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved( @SuppressWarnings("unused") MouseEvent e )
	{
		// No implementation.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered( @SuppressWarnings("unused") MouseEvent e )
	{
		// No implementation.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited( @SuppressWarnings("unused") MouseEvent e )
	{
		// No implementation.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed( @SuppressWarnings("unused") MouseEvent e )
	{
		// No implementation.
	}

	/**
	 * 
	 * @param invalid
	 * @return Point
	 */
	private Point getValidPoint( final Point invalid )
	{
		int x = Math.min( Math.max( manipulatable.getAxesBorder(), invalid.x ), manipulatable.getWidth() - manipulatable.getAxesBorder() );
		return new Point( x, invalid.y );
	}
}