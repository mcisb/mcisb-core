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
package org.mcisb.ui.tracking;

import java.awt.event.*;
import javax.swing.*;
import org.mcisb.ui.util.*;
import org.mcisb.ui.util.action.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class NewAction extends AbstractNewAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private final Manager manager;
	
	/**
	 * 
	 * @param manager
	 */
	public NewAction( final Manager manager )
	{
		this.manager = manager;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed( @SuppressWarnings("unused") ActionEvent e )
	{
		try
		{
			manager.newObject();
		}
		catch( Exception ex )
		{
			final JDialog errorDialog = new ExceptionComponentFactory().getExceptionDialog( null, ExceptionUtils.toString( ex ), ex );
			ComponentUtils.setLocationCentral( errorDialog );
			errorDialog.setVisible( true );
		}
	}
}
