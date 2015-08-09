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

/**
 * 
 * @author Neil Swainston
 */
public class Spot implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final static String SEPARATOR = ","; //$NON-NLS-1$

	/**
     * 
     */
	private final SpotReading userValue;

	/**
     * 
     */
	private int row;

	/**
     * 
     */
	private int column;

	/**
	 * 
	 * @param userValue
	 */
	public Spot( final SpotReading userValue )
	{
		this.userValue = userValue;
	}

	/**
	 * 
	 * @return SpotReading
	 */
	public SpotReading getUserValue()
	{
		return userValue;
	}

	/**
	 * 
	 * @return int
	 */
	public int getColumn()
	{
		return column;
	}

	/**
	 * 
	 * @param column
	 */
	public void setColumn( int column )
	{
		this.column = column;
	}

	/**
	 * 
	 * @return int
	 */
	public int getRow()
	{
		return row;
	}

	/**
	 * 
	 * @param row
	 */
	public void setRow( int row )
	{
		this.row = row;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return userValue == null ? row + SEPARATOR + column : userValue.toString();
	}
}