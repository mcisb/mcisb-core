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

import org.mcisb.util.*;
import org.xmldb.api.*;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;

/**
 * 
 * @author Neil Swainston
 */
public class StatementExecutor implements Disposable
{
	/**
	 * 
	 */
	private final static String LINE_SEPARATOR = System.getProperty( "line.separator" ); //$NON-NLS-1$
	
	/**
	 * 
	 */
	private final Collection collection;
	
	/**
	 * 
	 */
	private final XPathQueryService service;
	
	/**
	 *
	 * @param dbDriver
	 * @param dbServerName
	 * @param dbCollectionName
	 * @param username
	 * @param password
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws XMLDBException
	 */
	public StatementExecutor( String dbDriver, String dbServerName, String dbCollectionName, String username, String password ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException
	{
		final Database db = (Database)Class.forName( dbDriver ).newInstance();
		DatabaseManager.registerDatabase( db );
		collection = DatabaseManager.getCollection( dbServerName + dbCollectionName, username, password );
		service = (XPathQueryService)collection.getService( "XQueryService", "1.0" ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/* 
	 * (non-Javadoc)
	 * @see org.mcisb.util.Disposable#dispose()
	 */
	@Override
	public void dispose() throws Exception
	{
		collection.close();
	}
	
	/**
	 *
	 * @param query
	 * @return String
	 * @throws Exception
	 */
	public String getResult( final String query ) throws Exception
	{
		final StringBuffer buffer = new StringBuffer();
		final ResourceSet resultSet = service.query( query );

		if( resultSet != null )
		{
			final ResourceIterator iterator = resultSet.getIterator();
			
			while( iterator.hasMoreResources() )
			{
				final Resource currentResult = iterator.nextResource();
				final Object content = currentResult.getContent();
				buffer.append( content.toString() );
				buffer.append( LINE_SEPARATOR );
			}
		}
		
		return buffer.toString();
	}
}