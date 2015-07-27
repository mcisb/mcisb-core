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
package org.mcisb.ui.util.edit;

import java.applet.*;
import java.awt.*;
import java.beans.*;

/**
 * 
 * @author Neil Swainston
 */
public class EditorRemover implements PropertyChangeListener
{
	/**
	 * 
	 */
	private final Editable editable;
	
	/**
	 *
	 */
	private final KeyboardFocusManager focusManager; 

   /**
    * 
    * @param editable
    * @param focusManager
    */
	public EditorRemover( final Editable editable, final KeyboardFocusManager focusManager )
	{ 
		this.editable = editable;
		this.focusManager = focusManager; 
	} 

   /* 
    * (non-Javadoc)
    * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
    */
	@Override
	public void propertyChange( @SuppressWarnings("unused") PropertyChangeEvent ev )
	{ 
		if( editable.getEditorComponent() == null || !editable.terminateEditOnFocusLost() )
		{
			return; 
		} 

		Component c = focusManager.getPermanentFocusOwner(); 
       
		while( c != null )
		{ 
			if( c == editable )
			{ 
				// focus remains inside the table 
				return; 
			}
			else if( ( c instanceof Window ) || ( c instanceof Applet && c.getParent() == null ) )
			{ 
				if( c == editable.getRoot() )
				{ 
					if( !editable.stopEditing() )
					{ 
						editable.cancelEditing(); 
					} 
				} 
				break; 
			}
           
			c = c.getParent(); 
		} 
	} 
}