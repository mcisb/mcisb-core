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

import java.net.*;
import javax.swing.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class HyperlinkLabel extends JLabel implements Disposable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final UrlMouseListener urlMouseListener;

	/**
	 * 
	 * @param text
	 * @param icon
	 * @param horizontalAlignment
	 * @param url
	 */
	public HyperlinkLabel( final String text, final Icon icon, int horizontalAlignment, final URL url )
	{
		super( text, icon, horizontalAlignment );

		final int DEFAULT_ICON_TEXT_GAP = 8;
		setIconTextGap( DEFAULT_ICON_TEXT_GAP );
		setOpaque( false );
		urlMouseListener = new UrlMouseListener( url );
		addMouseListener( urlMouseListener );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.util.Disposable#dispose()
	 */
	@Override
	public void dispose()
	{
		removeMouseListener( urlMouseListener );
	}
}