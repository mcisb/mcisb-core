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
package org.mcisb.ui.wizard;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import org.mcisb.ui.util.*;
import org.mcisb.util.*;
import org.mcisb.util.task.*;

/**
 * 
 * @author Neil Swainston
 */
public abstract class Wizard extends JPanel implements PropertyChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String STATUS = "STATUS"; //$NON-NLS-1$

	/**
	 * 
	 */
	final static ResourceBundle resourceBundle = ResourceBundle.getBundle( "org.mcisb.ui.wizard.messages" ); //$NON-NLS-1$

	/**
	 * 
	 */
	protected static final int CONFIRMATION_PANEL = Integer.MIN_VALUE;

	/**
	 * 
	 */
	protected final java.util.List<WizardComponent> wizardComponents = new ArrayList<>();

	/**
	 * 
	 */
	protected final GenericBean bean;

	/**
	 * 
	 */
	protected final GenericBeanTask task;

	/**
	 * 
	 */
	protected final ProgressPanel confirmationPanel;

	/**
	 * 
	 */
	protected int currentWizardComponent = 0;

	/**
	 * 
	 */
	protected Object returnValue;

	/**
	 * 
	 */
	final Action finishAction = new FinishAction();

	/**
	 * 
	 */
	final Action cancelAction = new CancelAction();

	/**
	 * 
	 */
	final Action nextAction = new NextAction();

	/**
	 * 
	 */
	final boolean showReturnValue;

	/**
	 * 
	 */
	private final Action backAction = new BackAction();

	/**
	 * 
	 */
	private Component resultsComponent;

	/**
	 * 
	 * @param bean
	 * @param task
	 */
	public Wizard( final GenericBean bean, final GenericBeanTask task )
	{
		this( bean, task, null, false );
	}

	/**
	 * 
	 * @param bean
	 * @param task
	 * @param textAreaPreferredSize
	 */
	public Wizard( final GenericBean bean, final GenericBeanTask task, final Dimension textAreaPreferredSize )
	{
		this( bean, task, textAreaPreferredSize, false );
	}

	/**
	 * 
	 * @param bean
	 * @param task
	 * @param showReturnValue
	 */
	public Wizard( final GenericBean bean, final GenericBeanTask task, final boolean showReturnValue )
	{
		this( bean, task, null, showReturnValue );
	}

	/**
	 * 
	 * @param bean
	 * @param task
	 * @param textAreaPreferredSize
	 * @param showReturnValue
	 */
	public Wizard( final GenericBean bean, final GenericBeanTask task, final Dimension textAreaPreferredSize, final boolean showReturnValue )
	{
		this( bean, task, showReturnValue, new TextProgressPanel( resourceBundle.getString( "Wizard.progressTitle" ), textAreaPreferredSize, false ) ); //$NON-NLS-1$
	}

	/**
	 * 
	 * @param bean
	 * @param task
	 * @param showReturnValue
	 * @param confirmationPanel
	 */
	public Wizard( final GenericBean bean, final GenericBeanTask task, final boolean showReturnValue, final ProgressPanel confirmationPanel )
	{
		this.bean = bean;
		this.task = task;
		this.confirmationPanel = confirmationPanel;
		this.showReturnValue = showReturnValue;
	}

	/**
	 * 
	 * @return Object
	 */
	public Object getReturnValue()
	{
		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
	 * PropertyChangeEvent)
	 */
	@Override
	public void propertyChange( PropertyChangeEvent evt )
	{
		if( evt.getPropertyName().equals( ParameterPanel.VALID ) )
		{
			updateActions();
		}
	}

	/**
	 * 
	 * @param wizardComponent
	 */
	protected void addWizardComponent( WizardComponent wizardComponent )
	{
		wizardComponents.add( wizardComponent );
	}

	/**
	 * 
	 *
	 */
	protected void init()
	{
		final JPanel buttonPanel = new JPanel();

		if( task != null )
		{
			buttonPanel.add( new JButton( backAction ) );
			buttonPanel.add( new JButton( nextAction ) );
			finishAction.setEnabled( false );
		}
		else
		{
			finishAction.setEnabled( true );
		}

		buttonPanel.add( new JButton( finishAction ) );
		buttonPanel.add( new JButton( cancelAction ) );

		setLayout( new BorderLayout() );

		WizardComponent current = wizardComponents.get( currentWizardComponent );
		updateUI( null, current.getComponent() );
		add( buttonPanel, BorderLayout.SOUTH );

		// Determine preferredSize:
		int width = confirmationPanel.getPreferredSize().width;
		int height = confirmationPanel.getPreferredSize().height;

		for( Iterator<WizardComponent> iterator = wizardComponents.iterator(); iterator.hasNext(); )
		{
			WizardComponent wizardComponent = iterator.next();
			Dimension preferredSize = wizardComponent.getComponent().getPreferredSize();
			width = Math.max( preferredSize.width, width );
			height = Math.max( preferredSize.height, height );
		}

		Dimension preferredSize = buttonPanel.getPreferredSize();
		setPreferredSize( new Dimension( Math.max( width, preferredSize.width ), height + preferredSize.height ) );
	}

	/**
	 * 
	 * @return WizardComponent
	 */
	protected WizardComponent getBackReplacement()
	{
		return wizardComponents.get( --currentWizardComponent );
	}

	/**
	 * 
	 * @return WizardComponent
	 */
	protected WizardComponent getForwardReplacement()
	{
		return wizardComponents.get( ++currentWizardComponent );
	}

	/**
	 * 
	 * @return Component
	 * @throws Exception
	 */
	@SuppressWarnings({ "static-method", "unused" })
	protected Component getResultsComponent() throws Exception
	{
		return null;
	}

	/**
	 * 
	 * @param status
	 * @throws Exception
	 */
	protected void close( int status ) throws Exception
	{
		firePropertyChange( STATUS, Integer.valueOf( Task.READY ), Integer.valueOf( status ) );

		// Clean up:
		for( Iterator<WizardComponent> iterator = wizardComponents.iterator(); iterator.hasNext(); )
		{
			WizardComponent wizardComponent = iterator.next();
			Object component = wizardComponent.getComponent();

			try
			{
				( (Disposable)component ).dispose();
			}
			catch( Exception e )
			{
				showException( e );
			}
		}

		if( confirmationPanel != null )
		{
			confirmationPanel.dispose();
		}

		if( resultsComponent != null && resultsComponent instanceof Disposable )
		{
			( (Disposable)resultsComponent ).dispose();
		}

		dispose();
	}

	/**
	 */
	protected void dispose()
	{
		// Override if necessary.
	}

	/**
	 */
	void back()
	{
		WizardComponent current = wizardComponents.get( currentWizardComponent );
		updateUI( current.getComponent(), getBackReplacement().getComponent() );
	}

	/**
	 * 
	 */
	protected void forward()
	{
		try
		{
			if( currentWizardComponent == CONFIRMATION_PANEL )
			{
				resultsComponent = getResultsComponent();
				updateUI( confirmationPanel, resultsComponent );
				finishAction.setEnabled( true );
			}
			else
			{
				final WizardComponent current = wizardComponents.get( currentWizardComponent );
				current.update();

				if( currentWizardComponent == wizardComponents.size() - 1 )
				{
					runTask();
				}
				else
				{
					final WizardComponent replacement = getForwardReplacement();
					replacement.display();
					updateUI( current.getComponent(), replacement.getComponent() );
				}
			}
		}
		catch( Exception e )
		{
			showException( e );
		}
	}

	/**
	 * 
	 * @param e
	 */
	protected void showException( Exception e )
	{
		final JDialog dialog = new ExceptionComponentFactory().getExceptionDialog( getTopLevelAncestor(), resourceBundle.getString( "Wizard.error" ), e ); //$NON-NLS-1$
		ComponentUtils.setLocationCentral( dialog );
		dialog.setVisible( true );
	}

	/**
	 * 
	 * 
	 * @param title
	 */
	void updateActions( final String title )
	{
		confirmationPanel.setTitle( title );

		if( showReturnValue )
		{
			nextAction.setEnabled( true );
		}
		else
		{
			finishAction.setEnabled( true );
			cancelAction.setEnabled( false );
		}
	}

	/**
	 * 
	 *
	 */
	private void runTask()
	{
		WizardComponent current = wizardComponents.get( currentWizardComponent );

		try
		{
			if( task == null )
			{
				close( Task.FINISHED );
			}
			else
			{
				task.addPropertyChangeListener( confirmationPanel );

				currentWizardComponent = CONFIRMATION_PANEL;
				updateUI( current.getComponent(), confirmationPanel );

				Runnable runnable = new Runnable()
				{
					/*
					 * (non-Javadoc)
					 * 
					 * @see java.lang.Runnable#run()
					 */
					@Override
					public void run()
					{
						String title;

						try
						{
							returnValue = task.runTask( bean );
							title = resourceBundle.getString( "Wizard.successTitle" ); //$NON-NLS-1$
							confirmationPanel.setProgress( Task.FINISHED );
						}
						catch( Exception e )
						{
							title = resourceBundle.getString( "Wizard.failureTitle" ); //$NON-NLS-1$
							confirmationPanel.setException( e );
						}
						finally
						{
							try
							{
								task.removePropertyChangeListener( confirmationPanel );
							}
							catch( Exception e )
							{
								showException( e );
							}
						}

						final String confirmedTitle = title;

						SwingUtilities.invokeLater( new Runnable()
						{
							/*
							 * (non-Javadoc)
							 * 
							 * @see java.lang.Runnable#run()
							 */
							@Override
							public void run()
							{
								updateActions( confirmedTitle );
							}
						} );
					}
				};

				new Thread( runnable ).start();
			}
		}
		catch( Exception e )
		{
			showException( e );
		}
	}

	/**
	 * 
	 * @param current
	 * @param replacement
	 */
	private void updateUI( Component current, Component replacement )
	{
		if( current != null )
		{
			current.removePropertyChangeListener( this );
			remove( current );
		}

		replacement.addPropertyChangeListener( this );
		add( replacement, BorderLayout.CENTER );

		updateActions();
		validate();
		repaint();
	}

	/**
	 * 
	 *
	 */
	private void updateActions()
	{
		backAction.setEnabled( currentWizardComponent > 0 );
		nextAction.setEnabled( currentWizardComponent != CONFIRMATION_PANEL && currentWizardComponent != wizardComponents.size() && wizardComponents.get( currentWizardComponent ).getComponent().isValidated() );
	}

	/**
	 * 
	 * @author Neil Swainston
	 */
	private class BackAction extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public BackAction()
		{
			putValue( NAME, resourceBundle.getString( "Wizard.back" ) ); //$NON-NLS-1$
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed( final ActionEvent e )
		{
			try
			{
				back();
			}
			catch( Exception ex )
			{
				showException( ex );
			}
		}
	}

	/**
	 * 
	 * @author Neil Swainston
	 */
	private class NextAction extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public NextAction()
		{
			putValue( NAME, resourceBundle.getString( "Wizard.next" ) ); //$NON-NLS-1$
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed( final ActionEvent e )
		{
			forward();
		}
	}

	/**
	 * 
	 * @author Neil Swainston
	 */
	private class FinishAction extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public FinishAction()
		{
			putValue( NAME, resourceBundle.getString( "Wizard.finish" ) ); //$NON-NLS-1$
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed( final ActionEvent e )
		{
			if( task == null )
			{
				forward();
			}
			else
			{
				try
				{
					close( Task.FINISHED );
				}
				catch( Exception ex )
				{
					showException( ex );
				}
			}
		}
	}

	/**
	 * 
	 * @author Neil Swainston
	 */
	private class CancelAction extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public CancelAction()
		{
			putValue( NAME, resourceBundle.getString( "Wizard.cancel" ) ); //$NON-NLS-1$
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed( final ActionEvent e )
		{
			try
			{
				if( task != null )
				{
					task.cancel();
				}

				close( Task.CANCELLED );
			}
			catch( Exception ex )
			{
				showException( ex );
			}
		}
	}
}
