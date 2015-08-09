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
public class GridBagPanel extends JPanel
{
	/**
	 * 
	 */
	public final static int DEFAULT_INSET = 10;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected final int inset;

	/**
	 * 
	 */
	private final GridBagConstraints constraints = new GridBagConstraints();

	/**
	 * 
	 * 
	 * @param inset
	 */
	public GridBagPanel( final int inset )
	{
		super( new GridBagLayout() );
		this.inset = inset;
		constraints.anchor = GridBagConstraints.NORTHWEST;
	}

	/**
	 * 
	 *
	 */
	public GridBagPanel()
	{
		this( DEFAULT_INSET );
	}

	/**
	 * 
	 * 
	 * @param component
	 * @param x
	 * @param y
	 * @param mainX
	 * @param lastX
	 * @param mainY
	 * @param lastY
	 * @param fill
	 * @param gridwidth
	 */
	public void add( Component component, int x, int y, boolean mainX, boolean lastX, boolean mainY, boolean lastY, int fill, int gridwidth )
	{
		constraints.fill = fill;
		constraints.gridwidth = gridwidth;
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.weightx = mainX ? 1 : 0;
		constraints.weighty = mainY ? 1 : 0;
		constraints.insets = new Insets( inset, inset, ( lastY ? inset : 0 ), ( lastX ? inset : 0 ) );
		add( component, constraints );
	}

	/**
	 * 
	 * 
	 * @param component
	 */
	public void fill( Component component )
	{
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets = new Insets( inset, inset, inset, inset );
		add( component, constraints );
	}
}