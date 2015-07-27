/*******************************************************************************
 * Manchester Centre for Integrative Systems Biology
 * University of Manchester
 * Manchester M1 7ND
 * United Kingdom
 * 
 * Copyright (C) 2008 University of Manchester
 * 
 * This program is released under the Academic Free License ("AFL") v3.0.
 * (http://www.opensource.org/licenses/academic.php)
 *******************************************************************************/
package org.mcisb.ui.util.data;

/**
 * 
 * @author Neil Swainston
 */
public class DataDisplayPanelSpectrumIncrementer implements Runnable
{
	/**
	 * 
	 */
	private final DataDisplayPanel dataDisplayPanel;
	
	/**
	 * 
	 */
	private final long pause;
	
	/**
	 * 
	 */
	private boolean go = true;
	
	/**
	 * 
	 * @param dataDisplayPanel
	 * @param pause
	 */
	public DataDisplayPanelSpectrumIncrementer( final DataDisplayPanel dataDisplayPanel, final long pause )
	{
		this.dataDisplayPanel = dataDisplayPanel;
		this.pause = pause;
	}
	
	/**
	 * 
	 */
	public synchronized void stop()
	{
		go = false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		try
		{
			while( go() )
			{
				Thread.sleep( pause );
				dataDisplayPanel.incrementSpectrumIndex();
				dataDisplayPanel.repaint();
			}
		}
		catch( InterruptedException e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return go
	 */
	private synchronized boolean go()
	{
		return go;
	}
}