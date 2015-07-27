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

/**
 * 
 * @author Neil Swainston
 */
public abstract class ConnectionManager
{
	/**
	 *
	 * @param driverClassName
	 * @param server
	 * @param database
	 * @param user
	 * @param password
	 * @return Connection
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection( String driverClassName, String server, String database, String user, String password ) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException
	{
		final String ORACLE = "oracle"; //$NON-NLS-1$
		final String ORACLE_SEPARATOR = ":"; //$NON-NLS-1$
		final String STANDARD_SEPARATOR = "/"; //$NON-NLS-1$
		
		Class.forName( driverClassName ).newInstance();
		
		final boolean isOracle = driverClassName.toLowerCase( Locale.getDefault() ).contains( ORACLE );
		
		final StringBuffer buffer = new StringBuffer();
		buffer.append( server );
		buffer.append( isOracle ? ORACLE_SEPARATOR : STANDARD_SEPARATOR );
		buffer.append( database );
		
		return DriverManager.getConnection( buffer.toString(), user, password );
	}
}