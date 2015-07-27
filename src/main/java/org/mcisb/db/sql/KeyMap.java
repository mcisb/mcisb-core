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

import java.io.*;
import java.util.*;

/**
 * 
 * @author Neil Swainston
 */
class KeyMap
{	
	/**
	 * 
	 */
	private static final String SEPARATOR = ","; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private final Map<Object,Collection<Key>> map = new HashMap<>();
	
	/**
	 * 
	 * @param os
	 * @throws IOException
	 */
	void write( final OutputStream os ) throws IOException
	{
		try( final BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( os ) ) )
		{
			for( Iterator<Collection<Key>> keysIterator = map.values().iterator(); keysIterator.hasNext(); )
			{
				final Collection<Key> keys = keysIterator.next();
				
				for( Iterator<Key> keyIterator = keys.iterator(); keyIterator.hasNext(); )
				{
					final Key key = keyIterator.next();
					writer.write( key.getSourceTableName() + SEPARATOR + key.getSourceFieldName() + SEPARATOR + key.getTargetTableName() + SEPARATOR + key.getTargetFieldName() );
					writer.newLine();
				}
			}
			
			writer.flush();
		}
	}
	
	/**
	 * 
	 * @param tableName1
	 * @param fieldName1
	 * @param tableName2
	 * @param fieldName2
	 */
	void add( final Object tableName1, final Object fieldName1, final Object tableName2, final Object fieldName2 )
	{
		addKey( tableName1, fieldName1, tableName2, fieldName2 );
		addKey( tableName2, fieldName2, tableName1, fieldName1 );
	}
	
	/**
	 * 
	 *
	 * @param tableName
	 * @return Collection
	 */
	Collection<Key> getKeys( final Object tableName )
	{
		return map.get( tableName );
	}
	
	/**
	 * 
	 * @param tableName
	 * @param fieldName
	 * @return boolean
	 */
	boolean isKey( final String tableName, final String fieldName )
	{
		final Collection<Key> keys = getKeys( tableName );
		
		for( Iterator<Key> keyIterator = keys.iterator(); keyIterator.hasNext(); )
		{
			final Key key = keyIterator.next();
			
			if( ( key.getSourceTableName().equals( tableName ) && key.getSourceFieldName().equals( fieldName ) )
				|| ( key.getTargetTableName().equals( tableName ) && key.getTargetFieldName().equals( fieldName ) ) )
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param sourceTableName
	 * @param sourceFieldName
	 * @param targetTableName
	 * @param targetFieldName
	 */
	private void addKey( final Object sourceTableName, final Object sourceFieldName, final Object targetTableName, final Object targetFieldName )
	{
		Collection<Key> keys = map.get( sourceTableName );
		
		if( keys == null )
		{
			keys = new LinkedHashSet<>();
			map.put( sourceTableName, keys );
		}
		
		keys.add( new Key( sourceTableName, sourceFieldName, targetTableName, targetFieldName ) );
	}
}