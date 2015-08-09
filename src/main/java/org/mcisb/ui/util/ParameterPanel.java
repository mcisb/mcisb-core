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
package org.mcisb.ui.util;

import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class ParameterPanel extends BorderedPanel implements Disposable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public final static String VALID = "VALID"; //$NON-NLS-1$

	/**
	 * 
	 */
	public final static int DEFAULT_COLUMNS = 20;

	/**
	 * 
	 */
	private boolean validated = false;

	/**
	 * 
	 * @param title
	 */
	public ParameterPanel( String title )
	{
		super( title );
	}

	/**
	 * 
	 * @param validated
	 */
	public void setValid( boolean validated )
	{
		final boolean oldValidated = this.validated;
		this.validated = validated;
		firePropertyChange( VALID, oldValidated, validated );
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isValidated()
	{
		return validated;
	}
}