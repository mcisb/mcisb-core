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

import java.beans.*;
import org.mcisb.ui.util.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class WizardComponent implements PropertyChangeListener
{
	/**
	 * 
	 */
	protected final GenericBean bean;
	
	/**
	 * 
	 */
	protected final ParameterPanel parameterPanel;
	
	/**
	 * 
	 * @param bean
	 * @param parameterPanel
	 */
	public WizardComponent( GenericBean bean, ParameterPanel parameterPanel )
	{
		this.bean = bean;
		this.parameterPanel = parameterPanel;
	}
	
	/**
	 * 
	 * @return ParameterPanel
	 */
	public ParameterPanel getComponent()
	{
		return parameterPanel;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange( @SuppressWarnings("unused") PropertyChangeEvent e )
	{
		// Override where necessary.
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void display() throws Exception
	{
		// Override where necessary.
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public abstract void update() throws Exception;
}
