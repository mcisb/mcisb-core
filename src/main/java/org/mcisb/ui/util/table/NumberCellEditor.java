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
package org.mcisb.ui.util.table;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import javax.swing.text.*;
import org.mcisb.util.*;

/**
 * 
 * 
 * @author Neil Swainston
 */
public class NumberCellEditor extends DefaultCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NumberCellEditor()
	{
		this( NumberUtils.UNDEFINED, NumberUtils.UNDEFINED );
	}

	/**
	 * 
	 * @param min
	 * @param max
	 */
	public NumberCellEditor( double min, double max )
	{
		super( new JFormattedTextField() );

		final NumberFormat numberFormat = NumberFormat.getInstance();
		final NumberFormatter formatter = new NumberFormatter( numberFormat );
		formatter.setFormat( numberFormat );

		if( min != NumberUtils.UNDEFINED && max != NumberUtils.UNDEFINED )
		{
			formatter.setMinimum( Double.valueOf( min ) );
			formatter.setMaximum( Double.valueOf( max ) );
		}

		final JFormattedTextField textField = (JFormattedTextField)getComponent();
		textField.setFormatterFactory( new DefaultFormatterFactory( formatter ) );
		textField.setHorizontalAlignment( SwingConstants.TRAILING );
		textField.setFocusLostBehavior( JFormattedTextField.PERSIST );

		final String COMMIT = "COMMIT"; //$NON-NLS-1$
		textField.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 ), COMMIT );
		textField.getActionMap().put( COMMIT, new AbstractAction()
		{
			/**
        	 * 
        	 */
			private static final long serialVersionUID = 1L;

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.ActionListener#actionPerformed(java.awt.event.
			 * ActionEvent)
			 */
			@Override
			public void actionPerformed( @SuppressWarnings("unused") ActionEvent e )
			{
				stopCellEditing();
			}
		} );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.DefaultCellEditor#getTableCellEditorComponent(javax.swing
	 * .JTable, java.lang.Object, boolean, int, int)
	 */
	@Override
	public Component getTableCellEditorComponent( final JTable table, final Object value, final boolean isSelected, final int row, final int column )
	{
		final JFormattedTextField textField = (JFormattedTextField)super.getTableCellEditorComponent( table, value, isSelected, row, column );
		textField.setValue( value );
		return textField;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.DefaultCellEditor#getCellEditorValue()
	 */
	@Override
	public Object getCellEditorValue()
	{
		final JFormattedTextField textField = (JFormattedTextField)getComponent();
		return textField.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.DefaultCellEditor#stopCellEditing()
	 */
	@Override
	public boolean stopCellEditing()
	{
		final JFormattedTextField textField = (JFormattedTextField)getComponent();

		try
		{
			textField.commitEdit();
		}
		catch( ParseException e )
		{
			// Take no action.
		}

		if( textField.isEditValid() )
		{
			return super.stopCellEditing();
		}

		return false;
	}
}