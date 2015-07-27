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
package org.mcisb.ui.app;

import java.awt.*;
import java.beans.*;
import javax.swing.*;
import org.mcisb.ui.util.*;
import org.mcisb.ui.wizard.*;
import org.mcisb.util.*;
import org.mcisb.util.task.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class App implements PropertyChangeListener
{
	/**
	 * 
	 */
	protected final GenericBean bean;
	
	/**
	 * 
	 */
	protected final Window window;
	
	/**
	 * 
	 */
	protected final JDialog dialog;
	
	/**
	 * 
	 */
	protected final JFrame frame;
	
	/**
	 * 
	 */
	private String error;
	
	/**
	 * 
	 */
	private int status;
	
	/**
	 * 
	 * @param dialog
	 * @param bean
	 */
	public App( final JDialog dialog, final GenericBean bean )
	{
		this.window = dialog;
		this.dialog = dialog;
		this.frame = null;
		this.bean = bean;
	}
	
	/**
	 * 
	 * @param frame
	 * @param bean
	 */
	public App( final JFrame frame, final GenericBean bean )
	{
		this.window = frame;
		this.frame = frame;
		this.dialog = null;
		this.bean = bean;
	}
	
	/**
	 * 
	 * @param title
	 * @param errorTitle
	 * @param icon
	 */
	public void init( final String title, final String errorTitle, final Image icon )
	{
		this.error = errorTitle;
		
		Container contentPane;
		Wizard w = null;
		
		try
		{
			w = getWizard();
			w.addPropertyChangeListener( this );
			contentPane = w;
		}
		catch( Exception e )
		{
			contentPane = new ExceptionComponentFactory().getExceptionPanel( error, e );
		}

		if( dialog != null )
		{
			dialog.setTitle( title );
			dialog.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
			dialog.setContentPane( contentPane );
		}
		else if( frame != null )
		{
			frame.setTitle( title );
			frame.setIconImage( icon );
			frame.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
			frame.setContentPane( contentPane );
		}
		
		window.pack();
	}
	
	/**
	 * 
	 * @return int
	 */
	public int show()
	{
		if( frame != null )
		{
			frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		}
		
		ComponentUtils.setLocationCentral( window );
		window.setVisible( true );
		return status;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.mcisb.util.task.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange( PropertyChangeEvent e )
	{
		if( e.getPropertyName().equals( Task.PROGRESS ) )
		{
			final int progress = ( (Integer)e.getNewValue() ).intValue();
			
			if( progress == Task.CANCELLED || progress == Task.FINISHED || progress == Task.ERROR )
			{
				Object source = e.getSource();
				
				if( source instanceof AbstractTask )
				{
					AbstractTask task = (AbstractTask)source;
					
					try
					{
						task.removePropertyChangeListener( this );
						
						if( task.getException() != null )
						{
							showException( task.getException() );
						}
					}
					catch( Exception ex )
					{
						showException( ex );
					}
				}
			}
		}
		else if( e.getPropertyName().equals( Wizard.STATUS ) )
		{
			status = ( (Integer)e.getNewValue() ).intValue();
			window.dispose();
		}
	}
	
	/**
	 * 
	 * @return Wizard
	 * @throws Exception
	 */
	protected abstract Wizard getWizard() throws Exception;
	
	/**
	 * 
	 * @param e
	 */
	private void showException( Exception e )
	{
		final Container contentPane = new ExceptionComponentFactory().getExceptionPanel( error, e );
		
		if( dialog != null  )
		{
			dialog.setContentPane( contentPane );
		}
		else if( frame != null )
		{
			frame.setContentPane( contentPane );
		}
		
		window.validate();
	}
}