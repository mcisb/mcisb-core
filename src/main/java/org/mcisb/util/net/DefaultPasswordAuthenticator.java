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
package org.mcisb.util.net;

import java.net.*;

/**
 * @author Neil Swainston
 */
public class DefaultPasswordAuthenticator extends Authenticator
{
	/**
	 * 
	 */
	private final String username;

	/**
	 * 
	 */
	private final String password;

	/**
	 * 
	 * @param username
	 * @param password
	 */
	public DefaultPasswordAuthenticator( final String username, final String password )
	{
		this.username = username;
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.Authenticator#getPasswordAuthentication()
	 */
	@Override
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication( username, password.toCharArray() );
	}
}