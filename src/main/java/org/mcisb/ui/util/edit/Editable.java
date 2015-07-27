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

import java.awt.*;

/**
 *
 * @author Neil Swainston
 */
public interface Editable
{
	/**
	 * 
	 * @return Component
	 */
	public Component getEditorComponent();
	
	/**
	 * 
	 *
	 */
	public void edit();
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean terminateEditOnFocusLost();
	
	/**
	 * 
	 * @return Component
	 */
	public Component getRoot();
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean stopEditing();
	
	/**
	 *
	 */
	public void cancelEditing();
	
	/**
	 * 
	 */
	public void removeEditor();
}