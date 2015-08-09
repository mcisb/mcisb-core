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
package org.mcisb.ui.wizard.parameter;

import java.util.*;
import javax.swing.*;
import org.mcisb.ui.app.*;
import org.mcisb.ui.util.*;
import org.mcisb.ui.wizard.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class DefaultParameterApp extends App
{
	/**
	 * 
	 */
	private final ResourceBundle resourceBundle = ResourceBundle.getBundle( "org.mcisb.ui.wizard.parameter.messages" ); //$NON-NLS-1$

	/**
	 * 
	 */
	private final Wizard wizard;

	/**
	 * 
	 * @param dialog
	 * @param title
	 * @param bean
	 * @param component
	 * @param propertyNameToKey
	 */
	public DefaultParameterApp( final JDialog dialog, final String title, final GenericBean bean, final DefaultParameterPanel component, final Map<Object,Object> propertyNameToKey )
	{
		super( dialog, bean );
		wizard = new DefaultParameterWizard( bean, component, propertyNameToKey );
		init( title, resourceBundle.getString( "DefaultParameterApp.error" ), new ResourceFactory().getImageIcon( resourceBundle.getString( "DefaultParameterApp.icon" ) ).getImage() ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.app.App#getWizard(org.mcisb.util.GenericBean)
	 */
	@Override
	protected Wizard getWizard()
	{
		return wizard;
	}
}