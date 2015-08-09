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
package org.mcisb.ui.util.dnd;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * 
 * @author Neil Swainston
 */
public class PanelDropTargetListener extends DefaultDropTargetListener
{
	/**
	 * 
	 */
	private final JPanel panel;

	/**
	 * 
	 * @param panel
	 * @param supportedClasses
	 */
	public PanelDropTargetListener( final JPanel panel, final Collection<Class<?>> supportedClasses )
	{
		super( supportedClasses );
		this.panel = panel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mcisb.ui.util.dnd.DefaultDropTargetListener#dropComponent(java.lang
	 * .Object, java.awt.Point)
	 */
	@Override
	protected boolean dropComponent( final Object o, final Point point )
	{
		panel.add( getComponent( o ), Arrays.asList( panel.getComponents() ).indexOf( panel.getComponentAt( point ) ) );
		panel.validate();
		return true;
	}

	/**
	 * 
	 * @param object
	 * @return Component
	 */
	@SuppressWarnings("static-method")
	protected DraggableLabel getComponent( final Object object )
	{
		final DraggableLabel label = new DraggableLabel( object );
		// label.setFont( Font.getFont( Font.MONOSPACED ) );
		label.setBorder( BorderFactory.createLineBorder( Color.GRAY ) );
		return label;
	}
}