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

import java.util.*;
import javax.swing.*;

/**
 * 
 * @author Neil Swainston
 */
public class ListModelUtils
{
	/**
	 *
	 * @param model
	 * @param collection
	 */
	public static void add( final DefaultListModel<Object> model, final Collection<Object> collection )
	{
		model.clear();
		
		for( Iterator<Object> iterator = collection.iterator(); iterator.hasNext(); )
		{
			model.addElement( iterator.next() );
		}
	}
	
	/**
	 *
	 * @param model
	 * @param array
	 */
	public static void add( final DefaultListModel<Object> model, final Object[] array )
	{
		add( model, Arrays.asList( array ) );
	}
}