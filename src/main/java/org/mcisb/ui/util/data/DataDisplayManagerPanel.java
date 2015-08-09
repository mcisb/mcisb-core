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
package org.mcisb.ui.util.data;

import java.awt.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import org.mcisb.ui.util.*;
import org.mcisb.util.*;
import org.mcisb.util.data.*;

/**
 * 
 * @author Neil Swainston
 */
public class DataDisplayManagerPanel extends JPanel implements PropertyChangeListener, Disposable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected final JLabel label = new JLabel();

	/**
	 * 
	 */
	protected final DataDisplayPanel[] dataDisplayPanels;

	/**
	 * 
	 */
	private final boolean resetXRange;

	/**
	 * 
	 */
	private final ListManager[] listManagers;

	/**
	 * 
	 */
	private final ResizableJLayeredPane[] layeredPanes;

	/**
	 * 
	 */
	private final ManipulatorPanel[] manipulatorPanels;

	/**
	 * 
	 * @param dataDisplayPanel
	 * @param data
	 * @param manipulatable
	 * @param resetXRange
	 */
	public DataDisplayManagerPanel( final DataDisplayPanel dataDisplayPanel, final java.util.List<Spectra> data, final boolean manipulatable, final boolean resetXRange )
	{
		this( new DataDisplayPanel[] { dataDisplayPanel }, Arrays.asList( data ), new boolean[] { manipulatable }, resetXRange );
	}

	/**
	 * 
	 * @param dataDisplayPanels
	 * @param data
	 * @param manipulatables
	 * @param resetXRange
	 */
	public DataDisplayManagerPanel( final DataDisplayPanel[] dataDisplayPanels, final java.util.List<java.util.List<Spectra>> data, final boolean[] manipulatables, final boolean resetXRange )
	{
		super( new BorderLayout() );
		this.dataDisplayPanels = Arrays.copyOf( dataDisplayPanels, dataDisplayPanels.length );
		this.resetXRange = resetXRange;
		listManagers = new ListManager[ dataDisplayPanels.length ];
		layeredPanes = new ResizableJLayeredPane[ dataDisplayPanels.length ];
		manipulatorPanels = new ManipulatorPanel[ dataDisplayPanels.length ];

		setBackground( Color.WHITE );

		label.setHorizontalAlignment( SwingConstants.CENTER );
		add( label, BorderLayout.NORTH );

		final JTabbedPane tabbedPane = new JTabbedPane();

		for( int i = 0; i < dataDisplayPanels.length; i++ )
		{
			if( manipulatables[ i ] )
			{
				manipulatorPanels[ i ] = new ManipulatorPanel( dataDisplayPanels[ i ] );
				dataDisplayPanels[ i ].addMouseListener( manipulatorPanels[ i ] );
				dataDisplayPanels[ i ].addMouseMotionListener( manipulatorPanels[ i ] );

				layeredPanes[ i ] = new ResizableJLayeredPane();
				layeredPanes[ i ].add( dataDisplayPanels[ i ], JLayeredPane.DEFAULT_LAYER );
				layeredPanes[ i ].add( manipulatorPanels[ i ], JLayeredPane.DRAG_LAYER );

				tabbedPane.addTab( dataDisplayPanels[ i ].getName(), null, layeredPanes[ i ], dataDisplayPanels[ i ].getToolTipText() );
			}
			else
			{
				tabbedPane.addTab( dataDisplayPanels[ i ].getName(), null, dataDisplayPanels[ i ], dataDisplayPanels[ i ].getToolTipText() );
			}

			listManagers[ i ] = new ListManager( data.get( i ) );
			listManagers[ i ].addPropertyChangeListener( this );
		}

		final JComponent component = new ListManagerPanel( listManagers );
		component.setOpaque( false );
		add( component, BorderLayout.SOUTH );

		for( int i = 0; i < listManagers.length; i++ )
		{
			listManagers[ i ].init();
		}

		final int FIRST = 0;
		add( ( dataDisplayPanels.length > 1 ) ? tabbedPane : tabbedPane.getComponent( FIRST ), BorderLayout.CENTER );
	}

	/**
	 * 
	 * @param title
	 */
	public void setTitle( final String title )
	{
		label.setText( title );
		repaint();
	}

	/**
	 * 
	 * @param index
	 * @return DataDisplayPanel
	 */
	public DataDisplayPanel getDataDisplayPanel( int index )
	{
		return dataDisplayPanels[ index ];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
	 * PropertyChangeEvent)
	 */
	@Override
	public void propertyChange( final PropertyChangeEvent e )
	{
		final String propertyName = e.getPropertyName();

		if( propertyName.equals( ListManager.OBJECT ) )
		{
			final Object data = e.getNewValue();

			if( data instanceof Spectra )
			{
				try
				{
					final Spectra newSpectra = (Spectra)data;
					final Collection<String> labels = new LinkedHashSet<>();

					for( Iterator<Spectrum> iterator = newSpectra.iterator(); iterator.hasNext(); )
					{
						labels.add( iterator.next().getLabel() );
					}

					final int BRACKET_LENGTH = 1;
					final String title = Arrays.toString( labels.toArray() );
					setTitle( title.substring( BRACKET_LENGTH, title.length() - BRACKET_LENGTH ) );

					dataDisplayPanels[ Arrays.asList( listManagers ).indexOf( e.getSource() ) ].setSpectra( newSpectra );

					if( resetXRange )
					{
						dataDisplayPanels[ Arrays.asList( listManagers ).indexOf( e.getSource() ) ].reset();
					}
				}
				catch( Exception ex )
				{
					final JDialog errorDialog = new ExceptionComponentFactory().getExceptionDialog( null, ExceptionUtils.toString( ex ), ex );
					ComponentUtils.setLocationCentral( errorDialog );
					errorDialog.setVisible( true );
				}
			}
		}
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.util.Disposable#dispose()
	 */
	@Override
	public void dispose()
	{
		for( int i = 0; i < dataDisplayPanels.length; i++ )
		{
			if( listManagers[ i ] != null )
			{
				listManagers[ i ].removePropertyChangeListener( this );
			}
			if( layeredPanes[ i ] != null )
			{
				layeredPanes[ i ].dispose();
			}
			if( manipulatorPanels[ i ] != null )
			{
				dataDisplayPanels[ i ].removeMouseListener( manipulatorPanels[ i ] );
			}

			dataDisplayPanels[ i ].dispose();
		}
	}
}