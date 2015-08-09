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

import java.util.*;
import org.mcisb.ui.util.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class DefaultWizardComponent extends WizardComponent
{
	/**
	 * 
	 */
	protected final Map<Object,Object> propertyNameToKey;

	/**
	 * 
	 * @param bean
	 * @param component
	 * @param propertyNameToKey
	 */
	public DefaultWizardComponent( final GenericBean bean, final DefaultParameterPanel component, final Map<Object,Object> propertyNameToKey )
	{
		super( bean, component );
		this.propertyNameToKey = propertyNameToKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.wizard.WizardComponent#update()
	 */
	@Override
	public void update()
	{
		DefaultParameterPanel defaultParameterPanel = (DefaultParameterPanel)parameterPanel;

		for( Iterator<Object> iterator = propertyNameToKey.keySet().iterator(); iterator.hasNext(); )
		{
			Object propertyName = iterator.next();
			Object key = propertyNameToKey.get( propertyName );
			bean.setProperty( propertyName, defaultParameterPanel.getSelectedValues( key ) );
		}
	}
}
