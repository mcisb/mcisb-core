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
package org.mcisb.util.chem;

import org.junit.*;

/**
 * 
 * 
 * @author Neil Swainston
 */
public class FormulaTest
{
	/**
	 *
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testEquals()
	{
		final Formula formula1 = Formula.getFormula( "C12H24(Na0)jO16(CH2O0S)m(CH2O0S)jP0(Fe2)nS0Au(H34)oX" ); //$NON-NLS-1$
		final Formula formula2 = Formula.getFormula( "C12H24O16AuX(CH2O0S)m(Fe2)z(H34)t" ); //$NON-NLS-1$

		Assert.assertEquals( formula1, formula2 );
	}

	/**
	 *
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testEquals2()
	{
		final Formula formula1 = Formula.getFormula( "C37H68NO19PRCO2RCO2" ); //$NON-NLS-1$
		final Formula formula2 = Formula.getFormula( "C39H68NO23PR2" ); //$NON-NLS-1$

		Assert.assertEquals( formula1, formula2 );
	}

	/**
	 *
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testEqualsUndefined()
	{
		final Formula formula1 = Formula.getFormula( "" ); //$NON-NLS-1$
		final Formula formula2 = Formula.getFormula( "." ); //$NON-NLS-1$

		Assert.assertEquals( formula1, formula2 );
	}

	/**
	 * 
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testToString()
	{
		final Formula formula1 = Formula.getFormula( "C12H24(Na0)jO16(CH2O0S)m(CH2O0S)jP0(Fe2)pS0Au(H34)dX" ); //$NON-NLS-1$
		final String formula2 = "C12H24O16AuX(CH2S)n(Fe2)n(H34)n"; //$NON-NLS-1$
		Assert.assertEquals( formula1.toString(), formula2 );
	}

	/**
	 * 
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testExpand()
	{
		final Formula formula1 = Formula.getFormula( "C12H24O16AuX(CH2S)n(Fe2)n(H34)n" ); //$NON-NLS-1$
		final int n = 5;
		final Formula formula2 = Formula.getFormula( "C17H34O16AuXS5(Fe2)n(H34)n" ); //$NON-NLS-1$

		Assert.assertEquals( Formula.expand( formula1, n ), formula2 );
	}

	/**
	 * 
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testReplace()
	{
		final Formula formula1 = Formula.getFormula( "Fe2R" ); //$NON-NLS-1$
		final Formula formula2 = Formula.getFormula( "CH3(CH2)n" ); //$NON-NLS-1$
		final Formula formula3 = Formula.getFormula( "Fe2CH3(CH2)n" ); //$NON-NLS-1$
		Assert.assertEquals( Formula.replace( formula1, "R", formula2 ), formula3 ); //$NON-NLS-1$
	}

	/**
	 * 
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testReplace2()
	{
		final Formula formula1 = Formula.getFormula( "Fe2R2" ); //$NON-NLS-1$
		final Formula formula2 = Formula.getFormula( "CH3(CH2)n" ); //$NON-NLS-1$
		final Formula formula3 = Formula.getFormula( "Fe2C2H6(CH2)n" ); //$NON-NLS-1$
		Assert.assertEquals( Formula.replace( formula1, "R", formula2 ), formula3 ); //$NON-NLS-1$
	}

	/**
	 * 
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testReplace3()
	{
		final Formula formula1 = Formula.getFormula( "Fe2R6" ); //$NON-NLS-1$
		final Formula formula2 = Formula.getFormula( "CH3(CH2)n" ); //$NON-NLS-1$
		final Formula formula3 = Formula.getFormula( "Fe2R4C2H6(CH2)n" ); //$NON-NLS-1$
		Assert.assertEquals( Formula.replace( formula1, "R", formula2, 2 ), formula3 ); //$NON-NLS-1$
	}
}