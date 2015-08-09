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
package org.mcisb.db.xml;

import org.xmldb.api.base.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class StatementExecutorFactory
{
	/**
	 * 
	 * @param dbDriver
	 * @param dbServerName
	 * @param dbCollectionName
	 * @param username
	 * @param password
	 * @return StatementExecutor
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws XMLDBException
	 */
	public static StatementExecutor getInstance( final String dbDriver, final String dbServerName, final String dbCollectionName, final String username, final String password ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException
	{
		return new StatementExecutor( dbDriver, dbServerName, dbCollectionName, username, password );
	}
}