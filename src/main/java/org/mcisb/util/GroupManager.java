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
package org.mcisb.util;

import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Neil Swainston
 */
public class GroupManager extends PropertyChangeSupported
{
	/**
	 * 
	 */
	public static final String UPDATED = "UPDATED"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private final Map<Object,Collection<Object>> groupToObjects = new LinkedHashMap<>();
	
	/**
	 *
	 * @return Collection
	 */
	public Map<Object,Collection<Object>> getGroups()
	{
		return new LinkedHashMap<>( groupToObjects );
	}
	
	/**
	 * 
	 */
	public void clear()
	{
		final Set<Entry<Object, Collection<Object>>> removedGroups = getGroups().entrySet();
		groupToObjects.clear();
		
		for( Iterator<Map.Entry<Object, Collection<Object>>> iterator = removedGroups.iterator(); iterator.hasNext(); )
		{
			support.firePropertyChange( UPDATED, iterator.next(), null );
		}
	}

	/**
	 *
	 * @param group
	 * @param objects
	 */
	public void addGroup( final Object group, final Collection<Object> objects )
	{
		for( Iterator<Map.Entry<Object, Collection<Object>>> iterator = groupToObjects.entrySet().iterator(); iterator.hasNext(); )
		{
			final Map.Entry<Object, Collection<Object>> entry = iterator.next();
			
			for( Iterator<Object> iterator2 = entry.getValue().iterator(); iterator2.hasNext(); )
			{
				if( objects.contains( iterator2.next() ) )
				{
					iterator2.remove();
				}
			}
			
			if( entry.getValue().size() == 0 )
			{
				iterator.remove();
				support.firePropertyChange( UPDATED, entry.getKey(), null );
			}
		}
		
		groupToObjects.put( group, objects );
		support.firePropertyChange( UPDATED, null, group );
	}
	
	/**
	 *
	 * @param object
	 * @return Object
	 */
	public Object getGroup( final Object object )
	{
		for( Iterator<Map.Entry<Object, Collection<Object>>> iterator = groupToObjects.entrySet().iterator(); iterator.hasNext(); )
		{
			final Map.Entry<Object, Collection<Object>> entry = iterator.next();
			
			if( entry.getValue().contains( object ) )
			{
				return entry.getKey();
			}
		}
		
		return null;
	}
}