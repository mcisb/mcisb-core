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

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * 
 * @author Neil Swainston
 */
public class SamplePanel extends UniqueObjectPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param title
	 */
	public SamplePanel( String title )
	{
		super( title, "SamplePanel.defaultSampleName" ); //$NON-NLS-1$

		// Add components:
		add( new JLabel( resourceBundle.getString( "SamplePanel.name" ) ), 0, 0, false, true ); //$NON-NLS-1$
		add( new JScrollPane( objectList ), 1, 0, true, true, GridBagConstraints.HORIZONTAL );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.CustomParameterPanel#getObject()
	 */
	@Override
	public Object getObject()
	{
		Collection<Object> samples = new ArrayList<>();
		ListModel<?> model = objectList.getModel();

		for( int i = 0; i < model.getSize(); i++ )
		{
			samples.add( model.getElementAt( i ) );
		}

		return samples;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.util.CustomParameterPanel#save(java.lang.Object)
	 */
	@Override
	protected void save( final Object object )
	{
		// Take no action.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.ui.tracking.UniqueObjectPanel#setValid()
	 */
	@Override
	protected void setValid()
	{
		setValid( objectList.getModel().getSize() > 0 );
	}
}
