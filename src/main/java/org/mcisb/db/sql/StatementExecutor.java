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

import java.sql.*;
import java.util.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class StatementExecutor implements Disposable
{	
	/**
	 * 
	 */
	protected final SqlUtils sqlUtils = new SqlUtils();
	
	/**
	 * 
	 */
	protected final DatabaseLinker databaseLinker;

	/**
	 * 
	 */
	protected final Connection connection;
	
	/**
	 * 
	 */
	private final String schema;
	
	/**
	 *
	 * @param connection
	 * @param schema
	 * @throws SQLException
	 */
	public StatementExecutor( final Connection connection, final String schema ) throws SQLException
	{
		this( connection, true, schema );
	}
	
	/**
	 * 
	 * @param connection
	 * @throws SQLException
	 */
	public StatementExecutor( final Connection connection ) throws SQLException
	{
		this( connection, false, null );
	}
	
	/**
	 *
	 * @param connection
	 * @param useDatabaseLinker
	 * @param schema
	 * @throws SQLException
	 */
	private StatementExecutor( final Connection connection, boolean useDatabaseLinker, final String schema ) throws SQLException
	{
		this.connection = connection;
		this.databaseLinker = ( useDatabaseLinker ) ? new DatabaseLinker( getKeyMap() ) : null;
		this.schema = schema;
	}
	
	/**
	 * 
	 * @param tableName
	 * @param values
	 * @throws SQLException
	 */
	public void insert( final String tableName, final List<?> values ) throws SQLException
	{
		final StringBuffer statement = new StringBuffer( "INSERT INTO " ); //$NON-NLS-1$
		statement.append( getSchema() + tableName );
		statement.append( " VALUES (" ); //$NON-NLS-1$
		
		for( Iterator<?> iterator = values.iterator(); iterator.hasNext(); )
		{
			statement.append( SqlUtils.getValueString( iterator.next() ) );
			statement.append( "," ); //$NON-NLS-1$
		}
		
		statement.setLength( statement.length() - 1 );
		statement.append( ");" ); //$NON-NLS-1$
		
		executeUpdate( statement.toString() );
	}
	
	/**
	 * 
	 * @param nameValuePairs
	 * @param conditions
	 * @throws SQLException
	 */
	public void update( final Map<String,Object> nameValuePairs, final Collection<String> conditions ) throws SQLException
	{
		final Collection<String> tableNames = getTableNames( nameValuePairs.keySet() );
		
		final StringBuffer statement = new StringBuffer( "UPDATE " ); //$NON-NLS-1$
		statement.append( getSchema() + CollectionUtils.getFirst( tableNames ) );
		statement.append( " SET " ); //$NON-NLS-1$
		
		Collection<String> updates = new ArrayList<>();
		
		for( Iterator<Map.Entry<String,Object>> iterator = nameValuePairs.entrySet().iterator(); iterator.hasNext(); )
		{
			final Map.Entry<String,Object> entry = iterator.next();
			final String column = entry.getKey().substring( entry.getKey().lastIndexOf( SqlUtils.FIELD_SEPARATOR ) + 1 );
			updates.add( column + "=" + SqlUtils.getValueString( entry.getValue() ) ); //$NON-NLS-1$
		}
		
		statement.append( SqlUtils.getValuesString( updates ) );
		statement.append( " WHERE " ); //$NON-NLS-1$
		statement.append( getTableLinks( tableNames, conditions ) );
		statement.append( ";" ); //$NON-NLS-1$
		
		executeUpdate( statement.toString() );
	}
	
	/**
	 * 
	 * @param columnNamesRequested
	 * @return List
	 * @throws SQLException
	 */
	public List<List<Object>> getValues( final List<String> columnNamesRequested ) throws SQLException
	{
		return getValues( columnNamesRequested, new ArrayList<String>() );
	}
	
	/**
	 * 
	 * @param columnNamesRequested
	 * @param distinct
	 * @return List
	 * @throws SQLException
	 */
	public List<List<Object>> getValues( final List<String> columnNamesRequested, boolean distinct ) throws SQLException
	{
		return getValues( columnNamesRequested, new ArrayList<String>(), distinct );
	}
	
	/**
	 * 
	 * @param columnNamesRequested
	 * @param conditions
	 * @return List
	 * @throws SQLException
	 */
	public List<List<Object>> getValues( final List<String> columnNamesRequested, final Collection<String> conditions ) throws SQLException
	{
		return getValues( columnNamesRequested, conditions, false );
	}
	
	/**
	 * 
	 * @param columnNamesRequested
	 * @param conditions
	 * @param distinct
	 * @return List
	 * @throws SQLException
	 */
	public List<List<Object>> getValues( final List<String> columnNamesRequested, final Collection<String> conditions, final boolean distinct ) throws SQLException
	{
		return getValuesFromStatement( getQuery( columnNamesRequested, conditions, distinct ), new ArrayList<String>() );
	}
	
	/**
	 * 
	 *
	 * @param tableName
	 * @param conditions
	 * @param columnNamesReturned
	 * @return List
	 * @throws SQLException
	 */
	public List<List<Object>> getValues( final String tableName, final Collection<String> conditions, final List<String> columnNamesReturned ) throws SQLException
	{
		final Collection<String> tableNames = getTableNames( conditions );
		final Collection<String> tableLinks = getTableLinks( conditions );
		
		for( Iterator<String> iterator = tableLinks.iterator(); iterator.hasNext(); )
		{
			final String[] conditionTags = iterator.next().split( "=" ); //$NON-NLS-1$
			
			if( conditionTags[ 0 ].contains( "." ) ) //$NON-NLS-1$
			{
				tableNames.add( conditionTags[ 0 ].substring( 0, conditionTags[ 0 ].lastIndexOf( "." ) ) ); //$NON-NLS-1$
			}
			if( !NumberUtils.isDecimal( conditionTags[ 1 ] ) && conditionTags[ 1 ].contains( "." ) ) //$NON-NLS-1$
			{
				tableNames.add( conditionTags[ 1 ].substring( 0, conditionTags[ 1 ].lastIndexOf( "." ) ) ); //$NON-NLS-1$
			}
		}
		
		tableNames.add( getSchema() + tableName );
		
		// Select all:
		final StringBuffer query = new StringBuffer( "SELECT * FROM " ); //$NON-NLS-1$
		query.append( SqlUtils.getValuesString( tableNames ) );

		final String SEPARATOR = " AND "; //$NON-NLS-1$
		
		tableLinks.addAll( conditions );
		
		final String tableLinksString = SqlUtils.concatenate( tableLinks, SEPARATOR );
		
		if( tableLinksString.length() > 0 )
		{
			query.append( " WHERE " ); //$NON-NLS-1$
			query.append( tableLinksString );
		}
		
		query.append( ";" ); //$NON-NLS-1$
	  
		return getValuesFromStatement( query.toString().trim(), columnNamesReturned );
	}
	
	/**
	 * 
	 *
	 * @param preparedStatement
	 * @param columnNamesReturned
	 * @return List
	 * @throws SQLException
	 */
	public static List<List<Object>> getValuesFromStatement( final PreparedStatement preparedStatement, final List<String> columnNamesReturned ) throws SQLException
	{
		try( final ResultSet resultSet = preparedStatement.executeQuery() )
		{
			columnNamesReturned.clear();
			columnNamesReturned.addAll( getColumnNames( resultSet ) );
			final List<List<Object>> values = new ArrayList<>();
	
			while( resultSet.next() )
			{
				final List<Object> row = new ArrayList<>();
				
				for( int i = 0; i < columnNamesReturned.size(); i++ )
				{
					row.add( resultSet.getObject( i + 1 ) );
				}
				
				values.add( row );
			}
		  
			return values;
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see org.mcisb.util.Disposable#dispose()
	 */
	@Override
	public void dispose() throws Exception
	{
		connection.close();
	}

	/**
	 *
	 * @param sqlStatement
	 * @param columnNamesReturned
	 * @return List
	 * @throws SQLException
	 */
	private List<List<Object>> getValuesFromStatement( final String sqlStatement, final List<String> columnNamesReturned ) throws SQLException
	{
		try( final PreparedStatement statement = connection.prepareStatement( sqlStatement ) )
		{
			return getValuesFromStatement( statement, columnNamesReturned );
		}
	}

	/**
	 * 
	 * @param columnNamesRequested
	 * @param conditions
	 * @param distinct
	 * @return String
	 */
	private String getQuery( final List<String> columnNamesRequested, final Collection<String> conditions, final boolean distinct )
	{
		final String DISTINCT = "DISTINCT "; //$NON-NLS-1$
		final String EMPTY_STRING = ""; //$NON-NLS-1$
		final Collection<String> potentialTableNames = new LinkedHashSet<>();
		
		if( columnNamesRequested != null )
		{
			potentialTableNames.addAll( columnNamesRequested );
		}
		if( conditions != null )
		{
			potentialTableNames.addAll( conditions );
		}
		
		Collection<String> tableNames = getTableNames( potentialTableNames );
		
		final StringBuffer query = new StringBuffer( "SELECT " ); //$NON-NLS-1$
		query.append( distinct ? DISTINCT : EMPTY_STRING );
		
		if( columnNamesRequested == null )
		{
			// Select all:
			query.append( "*" ); //$NON-NLS-1$
		}
		else
		{
			for( Iterator<String> iterator = columnNamesRequested.iterator(); iterator.hasNext(); )
			{
				query.append( iterator.next() );
				query.append( "," ); //$NON-NLS-1$
			}
			query.setLength( query.length() - 1 );
		}
		
		final String tableLinks = getTableLinks( tableNames, conditions );

		if( tableLinks.length() > 0 )
		{
			final String[] strings1 = tableLinks.split( "AND" ); //$NON-NLS-1$

			for( int i = 0; i < strings1.length; i++ )
			{
				final StringTokenizer linkTableNames2 = new StringTokenizer( strings1[ i ].trim(), "=" ); //$NON-NLS-1$
				final String token2 = linkTableNames2.nextToken();

				if( token2.contains( "." ) ) //$NON-NLS-1$
				{
					tableNames.add( getSchema() + new StringTokenizer( token2, "." ).nextToken() ); //$NON-NLS-1$
				}
			}
		}

		query.append( " FROM " ); //$NON-NLS-1$
		query.append( SqlUtils.getValuesString( tableNames ) );

		if( tableLinks.length() > 0 )
		{
			query.append( " WHERE " ); //$NON-NLS-1$
			query.append( tableLinks );
		}

		query.append( ";" ); //$NON-NLS-1$
		
		return query.toString();
	}
	
	/**
	 * 
	 * @return Collection
	 * @throws SQLException
	 */
	private Collection<String> getTableNames() throws SQLException
	{
		final int TABLE_NAME_COLUMN_INDEX = 3;
		final Collection<String> tableNames = new ArrayList<>();
		
		final DatabaseMetaData databaseMetaData = connection.getMetaData();

        // Specify the type of object; in this case we want tables:
		final String[] types = { "TABLE" }; //$NON-NLS-1$
		
		try( final ResultSet resultSet = databaseMetaData.getTables( null, null, "%", types ) ) //$NON-NLS-1$
		{
	        // Get the table names
	        while( resultSet.next() )
	        {
	        	tableNames.add( getSchema() + resultSet.getString( TABLE_NAME_COLUMN_INDEX ) );
	        }
		}
		
		return tableNames;
	}
	
	/**
	 * 
	 * @return KeyMap
	 * @throws SQLException
	 */
	private KeyMap getKeyMap() throws SQLException
	{
		final KeyMap keyMap = new KeyMap();
		final DatabaseMetaData databaseMetaData = connection.getMetaData();
		
		for( Iterator<String> iterator = getTableNames().iterator(); iterator.hasNext(); )
		{
			try( final ResultSet rs = databaseMetaData.getImportedKeys( null, null, iterator.next() ) )
			{
	            while( rs.next() )
	            {
	                keyMap.add( rs.getString( "pktable_name" ), rs.getString( "pkcolumn_name" ), rs.getString( "fktable_name" ), rs.getString( "fkcolumn_name" ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	            }
			}
		}
		
		return keyMap;
	}
	
	/**
	 * 
	 *
	 * @return String
	 */
	private String getSchema()
	{
		final String EMPTY_STRING = ""; //$NON-NLS-1$
		return ( schema == null ) ? EMPTY_STRING : schema + SqlUtils.FIELD_SEPARATOR;
	}
	
	/**
	 * 
	 * @param sqlStatement
	 * @return int
	 * @throws SQLException
	 */
	private int executeUpdate( final String sqlStatement ) throws SQLException
	{
		try( final Statement statement = connection.createStatement() )
		{
			return statement.executeUpdate( sqlStatement );
		}
	}
	
	/**
	 * 
	 * @param resultSet
	 * @return List
	 * @throws SQLException
	 */
	private static List<String> getColumnNames( final ResultSet resultSet ) throws SQLException
	{
		final List<String> columnNames = new ArrayList<>();
		
		final ResultSetMetaData metaData = resultSet.getMetaData();
		
		for( int i = 0; i < metaData.getColumnCount(); i++ )
		{
			columnNames.add( metaData.getColumnName( i + 1 ) );
		}
		
		return columnNames;
	}
	
	/**
	 *
	 * @param fields
	 * @return Collection
	 */
	private Collection<String> getTableNames( final Collection<String> fields )
	{
		final Set<String> tableNames = new LinkedHashSet<>();
		
		for( Iterator<String> iterator = fields.iterator(); iterator.hasNext(); )
		{
			tableNames.add( getSchema() + new StringTokenizer( iterator.next(), SqlUtils.FIELD_SEPARATOR ).nextToken() );
		}
		
		return tableNames;
	}

	/**
	 *
	 * @param tableNames
	 * @param conditions
	 * @return String
	 */
	private String getTableLinks( final Collection<String> tableNames, final Collection<String> conditions )
	{
		final Collection<String> links = new LinkedHashSet<>();
		
		if( conditions != null )
		{
			links.addAll( conditions );
		}
		
		for( Iterator<String> sourceIterator = tableNames.iterator(); sourceIterator.hasNext(); )
		{
			final Object sourceTable = sourceIterator.next();
			
			for( Iterator<String> targetIterator = tableNames.iterator(); targetIterator.hasNext(); )
			{
				final Object targetTable = targetIterator.next();
				
				if( !sourceTable.equals( targetTable ) )
				{
					links.addAll( databaseLinker.getLinks( sourceTable, targetTable ) );
				}
			}
		}
		
		final String SEPARATOR = " AND "; //$NON-NLS-1$
		return SqlUtils.concatenate( links, SEPARATOR );
	}
	
	/**
	 * 
	 * @param conditions
	 * @return Collection
	 */
	private Collection<String> getTableLinks( final Collection<String> conditions )
	{
		final String EMPTY_STRING = ""; //$NON-NLS-1$
		final Set<String> tableLinks = new LinkedHashSet<>();
		final Collection<String> newConditions = new ArrayList<>();
		
		for( Iterator<String> iterator = conditions.iterator(); iterator.hasNext(); )
		{
			final String[] conditionTags = iterator.next().split( "=" ); //$NON-NLS-1$
			
			final String firstTag = ( ( conditionTags[ 0 ].contains( "." ) ) ? getSchema() : EMPTY_STRING ) + conditionTags[ 0 ]; //$NON-NLS-1$
			final String secondTag = ( ( !NumberUtils.isDecimal( conditionTags[ 1 ] ) && conditionTags[ 1 ].contains( "." ) ) ? getSchema() : EMPTY_STRING ) + conditionTags[ 1 ]; //$NON-NLS-1$
			newConditions.add( firstTag + "=" + secondTag ); //$NON-NLS-1$
		}
		
		tableLinks.addAll( newConditions );
		
		return tableLinks;
	}
}