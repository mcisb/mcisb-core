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
package org.mcisb.excel;

/**
 * 
 * @author Neil Swainston
 */
class ExcelCell
{
	/**
	 * 
	 */
	private final Object value;
	
	/**
	 * 
	 */
	private final int row;
	
	/**
	 * 
	 */
	private final int column;
	
	/**
	 * 
	 * @param value
	 * @param row
	 * @param column
	 */
	ExcelCell( final Object value, final int row, final int column )
	{
		this.value = value;
		this.row = row;
		this.column = column;
	}

	/**
	 * 
	 * @return Object
	 */
	public Object getValue()
	{
		return value;
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
	 * @return short
	 */
	public int getColumn()
	{
		return column;
	}
}