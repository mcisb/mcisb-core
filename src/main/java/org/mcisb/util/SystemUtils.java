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

import java.util.*;

/**
 * @author Neil Swainston
 * 
 */
public class SystemUtils
{
	/**
	 * 
	 * @author Neil Swainston
	 */
	public enum OperatingSystem
	{
		/**
		 * 
		 */
		WINDOWS,

		/**
		 * 
		 */
		MAC,

		/**
		 * 
		 */
		LINUX
	}

	/**
	 * 
	 * @return OperatingSystem
	 */
	public static OperatingSystem getOperatingSystem()
	{
		final String os = System.getProperty( "os.name" ).toLowerCase( Locale.getDefault() ); //$NON-NLS-1$

		if( os.contains( OperatingSystem.WINDOWS.toString().toLowerCase( Locale.getDefault() ) ) )
		{
			return OperatingSystem.WINDOWS;
		}

		if( os.contains( OperatingSystem.MAC.toString().toLowerCase( Locale.getDefault() ) ) )
		{
			return OperatingSystem.MAC;
		}

		if( os.contains( OperatingSystem.LINUX.toString().toLowerCase( Locale.getDefault() ) ) )
		{
			return OperatingSystem.LINUX;
		}

		throw new IllegalArgumentException();
	}
}
