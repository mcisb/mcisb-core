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
package org.mcisb.db.sql;

import java.util.*;

/**
 * 
 * @author Neil Swainston
 */
class DatabaseLinker
{
	/**
	 * 
	 */
	private final KeyMap keyMap;
	
	/**
	 * 
	 */
	private final List<String> links = new ArrayList<>();
	
	/**
	 * 
	 *
	 * @param keyMap
	 */
	DatabaseLinker( final KeyMap keyMap )
	{
		this.keyMap = keyMap;
	}
	
	/**
	 * 
	 * @param sourceTableName
	 * @param targetTableName
	 * @return Collection
	 */
	synchronized Collection<String> getLinks( final Object sourceTableName, final Object targetTableName )
	{
		investigateKeys( keyMap.getKeys( sourceTableName ), targetTableName );
		return returnResult();
	}
	
	/**
	 * 
	 * @param sourceTableName
	 * @param maxDepth
	 * @return Collection
	 */
	synchronized Collection<String> getLinks( final Object sourceTableName, final int maxDepth )
	{
		investigateKeys( keyMap.getKeys( sourceTableName ), maxDepth, 0 );
		return returnResult();
	}
	
	/**
	 * 
	 * @param tableName
	 * @param fieldName
	 * @return boolean
	 */
	boolean isKey( final String tableName, final String fieldName )
	{
		return keyMap.isKey( tableName, fieldName );
	}
	
	/**
	 * 
	 *
	 * @return Collection
	 */
	private Collection<String> returnResult()
	{
		final Collection<String> result = new ArrayList<>( links );
		links.clear();
		return result;
	}
	
	/**
	 * 
	 *
	 * @param keys
	 * @param targetTableName
	 * @return boolean
	 */
	private boolean investigateKeys( final Collection<Key> keys, final Object targetTableName )
	{
		for( Iterator<Key> iterator = keys.iterator(); iterator.hasNext(); )
		{
			final Key key = iterator.next();
			final Object tableName = key.getTargetTableName();
			links.add( key.toString() );
			
			if( tableName.equals( targetTableName ) )
			{	
				return true;
			}
			
			// Remove the current Key from next Keys, to prevent the recursion from going back and forth:
			final Collection<Key> nextKeys = new ArrayList<>( keyMap.getKeys( tableName ) );
			nextKeys.remove( key );
			
			if( investigateKeys( nextKeys, targetTableName ) )
			{
				return true;
			}
			
			links.remove( key.toString() );
		}
		
		return false;
	}
	
	/**
	 * 
	 *
	 * @param keys
	 * @param maxDepth
	 * @param currentDepth
	 */
	private void investigateKeys( final Collection<Key> keys, final int maxDepth, int currentDepth )
	{
		for( Iterator<Key> iterator = keys.iterator(); iterator.hasNext(); )
		{
			final Key key = iterator.next();
			final Object tableName = key.getTargetTableName();
			links.add( key.toString() );
			
			if( currentDepth + 1 == maxDepth )
			{	
				continue;
			}
			
			// Remove the current Key from next Keys, to prevent the recursion from going back and forth:
			final Collection<Key> nextKeys = new ArrayList<>( keyMap.getKeys( tableName ) );
			nextKeys.remove( key );
			
			investigateKeys( nextKeys, maxDepth, currentDepth + 1 );
		}
	}
}