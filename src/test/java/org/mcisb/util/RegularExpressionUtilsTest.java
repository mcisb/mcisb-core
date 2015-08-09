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

import java.io.*;
import java.net.*;
import java.util.*;
import org.junit.*;

/**
 * 
 * 
 * @author Neil Swainston
 */
public class RegularExpressionUtilsTest
{
	/**
	 * 
	 */
	private final Set<String> expectedInchiMatches = new HashSet<>( Arrays.asList( "InChI=1/C6H12O6/c7-1-3(9)5(11)6(12)4(10)2-8/h1,3-6,8-12H,2H2/t3-,4+,5+,6+/m1/s1", "InChI=1S/H2O/h1H2" ) ); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * 
	 */
	private final Set<String> expectedEcMatches = new HashSet<>( Arrays.asList( "1.1.1.5", "100.100.237.4949" ) ); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * 
	 */
	private final static String inchiText = "This text contains two inchi terms and some dummies: \"^^^^^^^^^^InChI=1S/H2O/h1H2;;*** InChI=1/H%3O/h1H3/ InChI=1\\H3O/h1H3 INCHI=1/P2O/h1H2 \"InChI=1/C6H12O6/c7-1-3(9)5(11)6(12)4(10)2-8/h1,3-6,8-12H,2H2/t3-,4+,5+,6+/m1/s1\","; //$NON-NLS-1$

	/**
	 * 
	 */
	private final static String ecText = "This text contains two ec terms and some dummies: EC        100.100.237.4949 100.1060.237.y 100.100.237.4949rt100.100.237.4949 100.100. 237.05 100.10 0.2447.4949 \"^^^^^^^^^^InChI=1/H2O/h1H2;;*** EC1.1.1.5 EC1.1.1.blahblah"; //$NON-NLS-1$

	/**
	 */
	@Test
	public void getMatchesString()
	{
		Assert.assertEquals( expectedInchiMatches, RegularExpressionUtils.getMatches( inchiText, RegularExpressionUtils.INCHI_REGEX ) );
		Assert.assertEquals( expectedEcMatches, RegularExpressionUtils.getMatches( ecText, RegularExpressionUtils.EC_REGEX ) );
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void getMatchesInputStream() throws Exception
	{
		InputStream is = null;

		try
		{
			is = new ByteArrayInputStream( inchiText.getBytes() );
			Assert.assertEquals( expectedInchiMatches, RegularExpressionUtils.getMatches( is, RegularExpressionUtils.INCHI_REGEX ) );
			is.close();

			is = new ByteArrayInputStream( ecText.getBytes() );
			Assert.assertEquals( expectedEcMatches, RegularExpressionUtils.getMatches( is, RegularExpressionUtils.EC_REGEX ) );
		}
		finally
		{
			if( is != null )
			{
				is.close();
			}
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getMatchesFormula()
	{
		final String sdFormula = "C3H6OFULLRCO2FULLR2CO2"; //$NON-NLS-1$
		final String formula = "C3H6ORCO2RCO2"; //$NON-NLS-1$
		Assert.assertEquals( formula, sdFormula.replaceAll( "FULLR(\\d*)", "R" ) ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getMatchesUrl() throws Exception
	{
		final URL url = new URL( "http://www.ebi.ac.uk/chebi/searchId.do?chebiId=CHEBI:16236" ); //$NON-NLS-1$
		final String inchi = "InChI=1S/C2H6O/c1-2-3/h3H,2H2,1H3"; //$NON-NLS-1$
		Assert.assertTrue( RegularExpressionUtils.getMatches( url, RegularExpressionUtils.INCHI_REGEX ).contains( inchi ) );
	}
}