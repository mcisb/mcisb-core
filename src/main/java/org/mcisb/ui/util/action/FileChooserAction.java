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
package org.mcisb.ui.util.action;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * 
 * @author Neil Swainston
 */
public class FileChooserAction extends AbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String FILES = "FILES"; //$NON-NLS-1$

	/**
	 * 
	 */
	private final static ResourceBundle resourceBundle = ResourceBundle.getBundle( "org.mcisb.ui.util.action.messages" ); //$NON-NLS-1$

	/**
	 * 
	 */
	private final Component parent;

	/**
	 * 
	 */
	private final JFileChooser fileChooser;

	/**
	 * 
	 */
	private final boolean multiSelectionEnabled;

	/**
	 * 
	 */
	private final transient javax.swing.filechooser.FileFilter fileFilter;

	/**
	 * 
	 */
	private int fileSelectionMode;

	/**
	 * 
	 */
	private File[] files;

	/**
	 * 
	 * @param parent
	 * @param fileChooser
	 * @param multiSelectionEnabled
	 * @param fileFilter
	 * @param fileSelectionMode
	 */
	public FileChooserAction( final Component parent, final JFileChooser fileChooser, final boolean multiSelectionEnabled, final javax.swing.filechooser.FileFilter fileFilter, final int fileSelectionMode )
	{
		super( resourceBundle.getString( "FileChooserAction.name" ) ); //$NON-NLS-1$
		this.parent = parent;
		this.fileChooser = fileChooser;
		this.multiSelectionEnabled = multiSelectionEnabled;
		this.fileFilter = fileFilter;
		this.fileSelectionMode = fileSelectionMode;
	}

	/**
	 * 
	 * @param fileSelectionMode
	 */
	public void setFileSelectionMode( int fileSelectionMode )
	{
		this.fileSelectionMode = fileSelectionMode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed( final ActionEvent e )
	{
		File[] oldFiles = files;

		if( fileSelectionMode != JFileChooser.DIRECTORIES_ONLY )
		{
			fileChooser.setFileFilter( fileFilter );
			fileChooser.setMultiSelectionEnabled( multiSelectionEnabled );
		}

		fileChooser.setFileSelectionMode( fileSelectionMode );

		int returnVal = fileChooser.showOpenDialog( parent );

		if( returnVal == JFileChooser.APPROVE_OPTION )
		{
			File blankFile = new File( "" ); //$NON-NLS-1$

			if( fileChooser.isMultiSelectionEnabled() )
			{
				files = fileChooser.getSelectedFiles();
				fileChooser.setSelectedFiles( new File[] { blankFile } );
			}
			else
			{
				files = new File[] { fileChooser.getSelectedFile() };
				fileChooser.setSelectedFile( blankFile );
			}
		}

		firePropertyChange( FILES, oldFiles, files );
	}
}
