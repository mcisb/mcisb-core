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
class Key
{
	/**
	 * 
	 */
	private final Object sourceTableName;
	
	/**
	 * 
	 */
	private final Object sourceFieldName;
	
	/**
	 * 
	 */
	private final Object targetTableName;
	
	/**
	 * 
	 */
	private final Object targetFieldName;
	
	/**
	 * 
	 */
	private final String toString;
	
	/**
	 * 
	 */
	private boolean hashCodeDefined = false;
	
	/**
	 * 
	 */
	private int hashCode;
	
	/**
	 * 
	 * @param sourceTableName
	 * @param sourceFieldName
	 * @param targetTableName
	 * @param targetFieldName
	 */
	Key( final Object sourceTableName, final Object sourceFieldName, final Object targetTableName, final Object targetFieldName )
	{
		this.sourceTableName = sourceTableName;
		this.sourceFieldName = sourceFieldName;
		this.targetTableName = targetTableName;
		this.targetFieldName = targetFieldName;
		toString = sourceTableName + "." + sourceFieldName + "=" + targetTableName + "." + targetFieldName; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * 
	 * @return Object
	 */
	Object getSourceTableName()
	{
		return sourceTableName;
	}
	
	/**
	 * 
	 * @return Object
	 */
	Object getSourceFieldName()
	{
		return sourceFieldName;
	}

	/**
	 * 
	 * @return Object
	 */
	Object getTargetFieldName()
	{
		return targetFieldName;
	}

	/**
	 * 
	 * @return Object
	 */
	Object getTargetTableName()
	{
		return targetTableName;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object object )
	{
		if( object instanceof Key )
		{
			return hashCode() == object.hashCode();
		}
		
		return false;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		if( !hashCodeDefined )
		{
			final Collection<Object> tableNames = new TreeSet<>();
			tableNames.add( sourceTableName );
			tableNames.add( targetTableName );
			
			for( Iterator<Object> iterator = tableNames.iterator(); iterator.hasNext(); )
			{
				hashCode += iterator.next().hashCode();
			}
			
			hashCodeDefined = true;
		}
		
		return hashCode;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return toString;
	}
}