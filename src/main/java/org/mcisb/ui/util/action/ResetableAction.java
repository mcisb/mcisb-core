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
package org.mcisb.ui.util.action;

import java.awt.event.*;
import javax.swing.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class ResetableAction extends AbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final transient Resetable resetable;

	/**
	 * 
	 * @param name
	 * @param resetable
	 */
	public ResetableAction( final String name, final Resetable resetable )
	{
		super( name );
		this.resetable = resetable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed( final ActionEvent e )
	{
		try
		{
			resetable.reset();
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
}