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
package org.mcisb.ui.wizard;

import org.mcisb.ui.util.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class CustomWizardComponent extends WizardComponent
{
	/**
	 * 
	 */
	private final String propertyName;

	/**
	 * 
	 * @param bean
	 * @param parameterPanel
	 * @param propertyName
	 */
	public CustomWizardComponent( final GenericBean bean, final ParameterPanel parameterPanel, final String propertyName )
	{
		super( bean, parameterPanel );
		this.propertyName = propertyName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.wizard.WizardComponent#update()
	 */
	@Override
	public void update() throws Exception
	{
		ObjectParameterPanel customParameterPanel = (ObjectParameterPanel)parameterPanel;
		customParameterPanel.saveObject();
		bean.setProperty( propertyName, customParameterPanel.getObject() );
	}
}
