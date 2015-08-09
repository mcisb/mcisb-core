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
package org.mcisb.ui.util.dnd;

import java.awt.datatransfer.*;

/**
 * 
 * @author Neil Swainston
 */
public class TransferableObject implements Transferable
{
	/**
	 * 
	 */
	private final Object object;

	/**
	 * 
	 */
	private final DataFlavor dataFlavor;

	/**
	 * 
	 * @param object
	 */
	public TransferableObject( final Object object )
	{
		this.object = object;
		dataFlavor = new DataFlavor( object.getClass(), object.getClass().getSimpleName() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer
	 * .DataFlavor)
	 */
	@Override
	public Object getTransferData( final DataFlavor flavor ) throws UnsupportedFlavorException
	{
		if( isDataFlavorSupported( flavor ) )
		{
			return object;
		}

		throw new UnsupportedFlavorException( flavor );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
	 */
	@Override
	public DataFlavor[] getTransferDataFlavors()
	{
		return new DataFlavor[] { dataFlavor };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.
	 * datatransfer.DataFlavor)
	 */
	@Override
	public boolean isDataFlavorSupported( DataFlavor flavor )
	{
		return dataFlavor.equals( flavor );
	}
}