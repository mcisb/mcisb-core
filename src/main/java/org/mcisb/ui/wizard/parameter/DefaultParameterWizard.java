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
import org.mcisb.ui.util.*;
import org.mcisb.ui.wizard.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class DefaultParameterWizard extends Wizard
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 * @param bean
	 * @param component
	 * @param propertyNameToKey
	 */
	public DefaultParameterWizard( final GenericBean bean, final DefaultParameterPanel component, final Map<Object,Object> propertyNameToKey )
	{
		super( bean, null );
		addWizardComponent( new DefaultWizardComponent( bean, component, propertyNameToKey ) );
		init();
	}
	
	/**
	 *
	 * @param propertyName
	 * @return Object
	 */
	public Object getProperty( final String propertyName )
	{
		return bean.getProperty( propertyName );
	}
}