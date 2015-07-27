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

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import org.mcisb.ui.util.action.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class FileChooserPanel extends ParameterPanel implements PropertyChangeListener, DocumentListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	final JTextField textField;
	
	/**
	 * 
	 */
	private final static ResourceBundle resourceBundle = ResourceBundle.getBundle( "org.mcisb.ui.util.messages" ); //$NON-NLS-1$

	/**
	 * 
	 */
	protected final static String DEFAULT_PROMPT = resourceBundle.getString( "FileChooserPanel.label" ); //$NON-NLS-1$
	
	/**
	 * 
	 */
	private final static String SEPARATOR = ";"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	protected JLabel label = new JLabel();
	
	/**
	 * 
	 */
	protected final FileChooserAction fileChooserAction;
	
	/**
	 * 
	 */
	private final boolean existingFilesOnly;
	
	/**
	 * 
	 */
	private final boolean optional;
	
	/**
	 * 
	 */
	private transient MouseListener mouseListener;
	
	/**
	 *
	 * @param parent
	 * @param title
	 * @param columns
	 * @param fileChooser
	 * @param multiSelectionEnabled
	 * @param existingFilesOnly
	 * @param optional
	 * @param fileSelectionMode
	 */
	public FileChooserPanel( final Component parent, final String title, final int columns, final JFileChooser fileChooser, final boolean multiSelectionEnabled, final boolean existingFilesOnly, final boolean optional, final int fileSelectionMode )
	{
		this( parent, title, columns, fileChooser, multiSelectionEnabled, existingFilesOnly, optional, new CustomFileFilter( new ArrayList<String>() ), fileSelectionMode );
	}
	
	/**
	 * 
	 * @param parent
	 * @param title
	 * @param columns
	 * @param fileChooser
	 * @param multiSelectionEnabled
	 * @param existingFilesOnly
	 * @param optional
	 * @param fileSelectionMode
	 * @param extensions
	 */
	public FileChooserPanel( final Component parent, final String title, final int columns, final JFileChooser fileChooser, final boolean multiSelectionEnabled, final boolean existingFilesOnly, final boolean optional, final int fileSelectionMode, final Collection<String> extensions )
	{
		this( parent, title, columns, fileChooser, multiSelectionEnabled, existingFilesOnly, optional, new CustomFileFilter( extensions ), fileSelectionMode );
	}
	
	/**
	 *
	 * @param parent
	 * @param title
	 * @param columns
	 * @param fileChooser
	 * @param multiSelectionEnabled
	 * @param existingFilesOnly
	 * @param optional
	 * @param fileFilter
	 * @param fileSelectionMode
	 */
	private FileChooserPanel( final Component parent, final String title, final int columns, final JFileChooser fileChooser, final boolean multiSelectionEnabled, final boolean existingFilesOnly, final boolean optional, final javax.swing.filechooser.FileFilter fileFilter, final int fileSelectionMode )
	{
		super( title );
		
		this.textField = new JTextField( columns );
		this.existingFilesOnly = existingFilesOnly;
		this.optional = optional;
		
		setValid( optional );
		
		fileChooserAction = new FileChooserAction( parent, fileChooser, multiSelectionEnabled, fileFilter, fileSelectionMode );
		final Component browseButton = new JButton( fileChooserAction );
		
		textField.addMouseListener( getMouseListener() );
		textField.getDocument().addDocumentListener( this );
		
		label.setText( DEFAULT_PROMPT );
		
		add( label, 0, 0, false, true );
		add( textField, 1, 0, true, false, false, true, GridBagConstraints.HORIZONTAL );
		add( browseButton, 2, 0, false, true, false, true, GridBagConstraints.NONE );
		
		fileChooserAction.addPropertyChangeListener( this );
	}

	/**
	 * 
	 * @return Collection
	 */
	public Collection<File> getFiles()
	{
		Collection<File> files = new ArrayList<>();
		StringTokenizer tokenizer = new StringTokenizer( textField.getText(), SEPARATOR );
		
		while( tokenizer.hasMoreTokens() )
		{
			files.add( new File( tokenizer.nextToken() ) );
		}
		
		return files;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange( PropertyChangeEvent e )
	{
		if( e.getPropertyName().equals( FileChooserAction.FILES ) )
		{
			final String text = validate( (File[])e.getNewValue() );
			textField.setText( text );
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void changedUpdate( final DocumentEvent e )
	{
		try
		{
			validate( e );
		}
		catch( BadLocationException ex )
		{
			final JDialog errorDialog = new ExceptionComponentFactory().getExceptionDialog( getParent(), ExceptionUtils.toString( ex ), ex );
			ComponentUtils.setLocationCentral( errorDialog );
			errorDialog.setVisible( true );
			setValid( optional || false );
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void insertUpdate( final DocumentEvent e )
	{
		try
		{
			validate( e );
		}
		catch( BadLocationException ex )
		{
			final JDialog errorDialog = new ExceptionComponentFactory().getExceptionDialog( getParent(), ExceptionUtils.toString( ex ), ex );
			ComponentUtils.setLocationCentral( errorDialog );
			errorDialog.setVisible( true );
			setValid( optional || false );
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void removeUpdate( final DocumentEvent e )
	{
		try
		{
			validate( e );
		}
		catch( BadLocationException ex )
		{
			final JDialog errorDialog = new ExceptionComponentFactory().getExceptionDialog( getParent(), ExceptionUtils.toString( ex ), ex );
			ComponentUtils.setLocationCentral( errorDialog );
			errorDialog.setVisible( true );
			setValid( optional || false );
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.mcisb.ui.util.Disposable#dispose()
	 */
	@Override
	public void dispose()
	{
		fileChooserAction.removePropertyChangeListener( this );
		textField.removeMouseListener( getMouseListener() );
		textField.getDocument().removeDocumentListener( this );
	}
	
	/**
	 * 
	 *
	 * @param e
	 * @throws BadLocationException
	 */
	private synchronized void validate( final DocumentEvent e ) throws BadLocationException
	{
		final int START = 0;
		final Document document = e.getDocument();
		final StringTokenizer tokenizer = new StringTokenizer( document.getText( START, document.getLength() ), SEPARATOR );
		final File[] files = new File[ tokenizer.countTokens() ];
		int i = 0;
		
		while( tokenizer.hasMoreTokens() )
		{
			files[ i++ ] = new File( tokenizer.nextToken() );
		}
		
		validate( files );
	}
	
	/**
	 * 
	 *
	 * @param files
	 * @return String
	 */
	private String validate( final File[] files )
	{
		final StringBuffer buffer = new StringBuffer();
		
		if( files != null )
		{	
			for( int i = 0; i < files.length; i++ )
			{
				buffer.append( files[ i ].getAbsolutePath() );
				
				if( i < files.length - 1 )
				{
					buffer.append( SEPARATOR );
				}
			}
			
			if( existingFilesOnly )
			{
				for( int i = 0; i < files.length; i++ )
				{
					if( !files[ i ].exists() )
					{
						setValid( false );
						return buffer.toString();
					}
				}
			}
			
			setValid( optional || files.length > 0 );
			return buffer.toString();
		}
		
		setValid( optional || false );
		return buffer.toString();
	}
	
	/**
	 * 
	 * @return MouseListener
	 */
	private MouseListener getMouseListener()
	{
		if( mouseListener == null )
		{
			mouseListener = new JMenuMouseListener( new JTextComponentMenu() );
		}
		
		return mouseListener;
	}
}