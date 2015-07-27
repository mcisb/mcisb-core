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
package org.mcisb.util;

/**
 * 
 * @author Neil Swainston
 */
public class Value
{
	/**
	 * 
	 */
	public static final String UNITLESS = "UNITLESS"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private static final double DEFAULT_ERROR = 0.0;
	
	/**
	 * 
	 */
	private final String name;
	
	/**
	 * 
	 */
	private final double value;
	
	/**
	 * 
	 */
	private final double start;
	
	/**
	 * 
	 */
	private final double end;
	
	/**
	 * 
	 */
	private final double error;
	
	/**
	 * 
	 */
	private final String unit;
	
	/**
	 * 
	 */
	private final boolean constant;
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @param start
	 * @param end
	 * @param error
	 * @param unit
	 * @param constant
	 */
	public Value( final String name, final double value, final double start, final double end, final double error, final String unit, final boolean constant )
	{
		this.name = name;
		this.value = value;
		this.start = start;
		this.end = end;
		this.error = error;
		this.unit = unit;
		this.constant = constant;
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @param unit
	 * @param constant
	 */
	public Value( final String name, final double value, final String unit, final boolean constant )
	{
		this( name, value, value, value, DEFAULT_ERROR, unit, constant );
	}
	
	/**
	 *
	 * @param name
	 * @param value
	 * @param constant
	 */
	public Value( final String name, final double value, final boolean constant )
	{
		this( name, value, value, value, DEFAULT_ERROR, UNITLESS, constant );
	}

	/**
	 * 
	 * @return constant
	 */
	public boolean isConstant()
	{
		return constant;
	}

	/**
	 * 
	 * @return end
	 */
	public double getEnd()
	{
		return end;
	}

	/**
	 * 
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 
	 * @return error
	 */
	public double getError()
	{
		return error;
	}

	/**
	 * 
	 * @return start
	 */
	public double getStart()
	{
		return start;
	}

	/**
	 * 
	 * @return unit
	 */
	public String getUnit()
	{
		return unit;
	}

	/**
	 * 
	 * @return value
	 */
	public double getValue()
	{
		return value;
	}
}
