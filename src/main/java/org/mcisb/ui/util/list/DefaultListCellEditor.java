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
package org.mcisb.ui.util.list;

import java.awt.*;
import javax.swing.*;

/**
 * 
 * @author Neil Swainston
 */
public class DefaultListCellEditor extends DefaultCellEditor implements ListCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param textField
	 */
	public DefaultListCellEditor( JTextField textField )
	{
		super( textField );
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mcisb.ui.util.list.ListCellEditor#getListCellEditorComponent(java.lang.Object)
	 */
	@Override
	public Component getListCellEditorComponent( Object value )
	{
		delegate.setValue( value ); 
        return editorComponent; 
	}
}
