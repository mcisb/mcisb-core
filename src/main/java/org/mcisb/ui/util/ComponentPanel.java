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
public class ComponentPanel extends ParameterPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected final Component component;

	/**
	 * 
	 * @param title
	 * @param component
	 */
	public ComponentPanel( final String title, final Component component )
	{
		super( title );

		this.component = component;

		final JScrollPane scrollPane = new JScrollPane( component );
		scrollPane.getViewport().setBackground( Color.WHITE );
		fill( scrollPane );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.util.Disposable#dispose()
	 */
	@SuppressWarnings("unused")
	@Override
	public void dispose() throws Exception
	{
		// No implementation
	}
}