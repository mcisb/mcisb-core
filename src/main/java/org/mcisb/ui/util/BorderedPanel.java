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
import javax.swing.border.*;

/**
 * 
 * @author Neil Swainston
 */
public class BorderedPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected final JPanel innerPanel = new JPanel( new BorderLayout() );
	
	/**
	 * 
	 */
	protected final GridBagPanel contentPanel = new GridBagPanel();
	
	/**
	 * 
	 */
	private final GridBagConstraints constraints = new GridBagConstraints();
	
	/**
	 * 
	 */
	private final TitledBorder border;
	
	/**
	 * 
	 * @param title
	 */
	public BorderedPanel( final String title )
	{
		super( new BorderLayout() );
		
		border = BorderFactory.createTitledBorder( title );
		innerPanel.setBorder( border );
		innerPanel.add( contentPanel, BorderLayout.CENTER );
		
		constraints.anchor = GridBagConstraints.NORTHWEST;
		
		setBorder( BorderFactory.createEmptyBorder( GridBagPanel.DEFAULT_INSET, GridBagPanel.DEFAULT_INSET, 0, GridBagPanel.DEFAULT_INSET ) );
		add( innerPanel, BorderLayout.CENTER );
	}
	
	/**
	 * 
	 * @param title
	 */
	public void setTitle( String title )
	{
		border.setTitle( title );
		repaint();
	}

	/**
	 * 
	 * @param component
	 * @param x
	 * @param y
	 * @param lastX
	 * @param lastY
	 */
	public void add( Component component, int x, int y, boolean lastX, boolean lastY )
	{
		add( component, x, y, lastX, lastY, GridBagConstraints.NONE );
	}
	
	/**
	 *
	 * @param component
	 * @param x
	 * @param y
	 * @param lastX
	 * @param lastY
	 * @param fill
	 */
	public void add( Component component, int x, int y, boolean lastX, boolean lastY, int fill )
	{
		add( component, x, y, lastX, lastY, fill, 1 );
	}
	
	/**
	 *
	 * @param component
	 * @param x
	 * @param y
	 * @param lastX
	 * @param lastY
	 * @param fill
	 * @param gridwidth
	 */
	public void add( Component component, int x, int y, boolean lastX, boolean lastY, int fill, int gridwidth )
	{
		contentPanel.add( component, x, y, lastX, lastX, lastY, lastY, fill, gridwidth );
	}
	
	/**
	 * 
	 * @param component
	 * @param x
	 * @param y
	 * @param mainX
	 * @param lastX
	 * @param mainY
	 * @param lastY
	 * @param fill
	 */
	public void add( Component component, int x, int y, boolean mainX, boolean lastX, boolean mainY, boolean lastY, int fill )
	{
		contentPanel.add( component, x, y, mainX, lastX, mainY, lastY, fill, 1 );
	}
	
	/**
	 * 
	 * @param component
	 */
	public void fill( Component component )
	{
		contentPanel.fill( component );
	}
}