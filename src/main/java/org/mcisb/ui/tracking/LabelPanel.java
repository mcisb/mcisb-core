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
import javax.swing.event.*;
import org.mcisb.ui.util.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class LabelPanel extends ObjectParameterPanel implements SampleParameterPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private final Map<Object,Object> labelToSample = new HashMap<>();
	
	/**
	 * 
	 */
	private final JList<?> sampleList = new JList<>( new DefaultListModel<>() );

	/**
	 * 
	 * @param title
	 * @param labelNames
	 */
	public LabelPanel( final String title, final String[] labelNames )
	{
		super( title, new JList<>( new DefaultListModel<>() ), false );
		
		setValid( true );
		
		// Populate label list:
		final ListModel<?> listModel = objectList.getModel();
		
		if( listModel instanceof DefaultListModel )
		{
			for( int i = 0; i < labelNames.length; i++ )
			{
				final UniqueObject object = getUniqueObject();
				object.setName( labelNames[ i ] );
				( (DefaultListModel<Object>)listModel ).addElement( object );
			}
		}
		
		// Create and configure components:
		sampleList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		sampleList.addListSelectionListener( this );
		
		// Add components:
		add( new JLabel( resourceBundle.getString( "LabelPanel.label" ) ), 0, 0, false, false, true, false, GridBagConstraints.HORIZONTAL ); //$NON-NLS-1$
		add( new JScrollPane( objectList ), 1, 0, true, true, true, false, GridBagConstraints.BOTH );
		add( new JLabel( resourceBundle.getString( "LabelPanel.sample" ) ), 0, 1, false, false, true, true, GridBagConstraints.HORIZONTAL ); //$NON-NLS-1$
		add( new JScrollPane( sampleList ), 1, 1, true, true, true, true, GridBagConstraints.BOTH );
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.mcisb.ui.tracking.Manager#newObject()
	 */
	@Override
	public void newObject()
	{
		// Not implemented.
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.mcisb.ui.tracking.Manager#deleteObject()
	 */
	@Override
	public void deleteObject()
	{
		// Not implemented.
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mcisb.ui.util.ObjectParameterPanel#dispose()
	 */
	@Override
	public void dispose() throws Exception
	{
		super.dispose();
		sampleList.removeListSelectionListener( this );
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mcisb.ui.tracking.SampleParameterPanel#setSamples(java.util.Collection)
	 */
	@Override
	public void setSamples( Collection<?> samples )
	{
		final DefaultListModel<Object> model = ( (DefaultListModel<Object>)sampleList.getModel() );
		model.clear();
		
		for( Iterator<?> iterator = samples.iterator(); iterator.hasNext(); )
		{
			model.addElement( iterator.next() );
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.mcisb.ui.util.ObjectParameterPanel#getObject()
	 */
	@Override
	public Object getObject()
	{
		return labelToSample;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.mcisb.ui.util.ObjectParameterPanel#save(java.lang.Object)
	 */
	@Override
	protected void save( Object object )
	{
		if( object != null )
		{
			labelToSample.put( object, sampleList.getSelectedValue() );
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged( ListSelectionEvent e )
	{
		super.valueChanged( e );
		
		final Object label = objectList.getSelectedValue();
		
		if( e.getSource().equals( objectList ) )
		{
			
			final Object sample = label == null ? null : labelToSample.get( label );
			sampleList.setSelectedValue( sample, true );
			
			if( sample == null )
			{
				sampleList.clearSelection();
			}
		}
		else
		{
			save( label );
		}
	}
}