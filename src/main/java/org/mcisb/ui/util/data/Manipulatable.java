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

/**
 * 
 * @author Neil Swainston
 */
public interface Manipulatable
{
	/**
	 * 
	 * @param minXPosition
	 * @param maxXPosition
	 */
	public abstract void setXRangeByPosition( final double minXPosition, final double maxXPosition );

	/**
	 * 
	 */
	public abstract void reset();

	/**
	 * 
	 * @return int
	 */
	public abstract int getAxesBorder();

	/**
	 * 
	 * @return int
	 */
	public abstract int getHeight();

	/**
	 * 
	 * @return int
	 */
	public abstract int getWidth();
}