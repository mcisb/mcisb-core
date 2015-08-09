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
package org.mcisb.tracking;

import java.io.*;
import java.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class Plate implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
	protected final Spot[][] spots;

	/**
	 * 
	 * @param rows
	 * @param columns
	 */
	public Plate( final int rows, final int columns )
	{
		spots = new Spot[ rows ][ columns ];
	}

	/**
	 * 
	 * @param value
	 * @param row
	 * @param column
	 */
	public void setValueAt( Spot value, int row, int column )
	{
		spots[ row ][ column ] = value;
	}

	/**
	 * 
	 * @param row
	 * @param column
	 * @return Spot
	 */
	public Spot getValueAt( int row, int column )
	{
		return spots[ row ][ column ];
	}

	/**
	 * 
	 * @return Collection
	 */
	public Collection<Spot> getSpots()
	{
		final Collection<Spot> spotsList = new ArrayList<>();

		for( int row = 0; row < getNumberOfRows(); row++ )
		{
			for( int column = 0; column < getNumberOfColumns(); column++ )
			{
				final Spot spot = getValueAt( row, column );

				if( spot != null )
				{
					spotsList.add( spot );
				}
			}
		}

		return spotsList;
	}

	/**
	 * 
	 * @return Collection
	 */
	public Collection<SpotReading> getSpotReadings()
	{
		final Collection<SpotReading> spotReadings = new ArrayList<>();

		for( Iterator<Spot> iterator = getSpots().iterator(); iterator.hasNext(); )
		{
			final SpotReading spotReading = iterator.next().getUserValue();

			if( spotReading != null )
			{
				spotReadings.add( spotReading );
			}
		}

		return spotReadings;
	}

	/**
	 * 
	 * @return int
	 */
	public int getNumberOfRows()
	{
		return spots.length;
	}

	/**
	 * 
	 * @return int
	 */
	public int getNumberOfColumns()
	{
		return spots.length == 0 ? 0 : spots[ 0 ].length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mcisb.util.UniqueObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object object )
	{
		if( object instanceof Plate )
		{
			return super.equals( object );
		}

		return false;
	}
}