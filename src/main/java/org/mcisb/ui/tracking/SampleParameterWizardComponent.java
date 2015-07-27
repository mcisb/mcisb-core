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

import java.beans.*;
import java.util.*;
import org.mcisb.ui.util.*;
import org.mcisb.ui.wizard.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class SampleParameterWizardComponent extends CustomWizardComponent
{
	/**
	 * @param bean
	 * @param parameterPanel
	 * @param propertyName
	 */
	public SampleParameterWizardComponent( GenericBean bean, ParameterPanel parameterPanel, String propertyName )
	{
		super( bean, parameterPanel, propertyName );
	}

	/*
	 * (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange( PropertyChangeEvent e )
	{
		if( e.getPropertyName().equals( PropertyNames.SAMPLES ) )
		{
			final Object newValue = e.getNewValue();
			
			if( newValue instanceof Collection<?> )
			{
				SampleParameterPanel sampleParameterPanel = (SampleParameterPanel)parameterPanel;
				sampleParameterPanel.setSamples( (Collection<?>)newValue );
			}
		}
	}
}
