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
package org.mcisb.ui.util;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import org.mcisb.util.*;
import org.mcisb.util.net.*;

/**
 * 
 * @author Neil Swainston
 */
public class UrlMouseListener extends MouseAdapter implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private final URL url;
	
	/**
	 * 
	 * @param url
	 */
	public UrlMouseListener( URL url )
	{
		this.url = url;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked( @SuppressWarnings("unused") MouseEvent e )
	{
		try
		{
			BrowserLauncher.openURL( url );
		}
		catch( Exception ex )
		{
			final JDialog errorDialog = new ExceptionComponentFactory().getExceptionDialog( null, ExceptionUtils.toString( ex ), ex );
			ComponentUtils.setLocationCentral( errorDialog );
			errorDialog.setVisible( true );
		}
    }
}