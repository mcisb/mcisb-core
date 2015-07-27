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
public class ChooserWizardComponent extends WizardComponent
{
	/**
	 * 
	 * @param bean
	 * @param parameterPanel
	 */
	public ChooserWizardComponent( GenericBean bean, ParameterPanel parameterPanel )
	{
		super( bean, parameterPanel );
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mcisb.ui.wizard.WizardComponent#update()
	 */
	@Override
	public void update()
	{
		bean.setProperty( PropertyNames.CHOOSER_OBJECTS, ( (Chooser)parameterPanel ).getSelection() );
	}
}