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
import java.util.*;
import javax.swing.*;
import org.mcisb.ui.util.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class AbstractNewAction extends AbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private final static ResourceBundle resourceBundle = ResourceBundle.getBundle( "org.mcisb.ui.util.action.messages" ); //$NON-NLS-1$
	
	/**
	 * 
	 */
	public AbstractNewAction()
	{
		super( resourceBundle.getString( "AbstractNewAction.name" ), new ResourceFactory().getImageIcon( resourceBundle.getString( "AbstractNewAction.icon" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
		Integer keyEventValue = Integer.valueOf( KeyEvent.VK_N );
		putValue( ACCELERATOR_KEY, keyEventValue );
		putValue( MNEMONIC_KEY, keyEventValue );
		putValue( SHORT_DESCRIPTION, getValue( NAME ) );
	}
}
