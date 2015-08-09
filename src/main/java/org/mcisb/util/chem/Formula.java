/**
 * 
 */
package org.mcisb.util.chem;

import java.util.*;
import org.mcisb.util.*;

/**
 * @author neilswainston
 * 
 */
public class Formula
{
	/**
	 * 
	 */
	public static final String R_GROUP = "R"; //$NON-NLS-1$

	/**
	 * 
	 */
	public static final Formula R_GROUP_EXPANSION = Formula.getFormula( "CH3(CH2)n" ); //$NON-NLS-1$

	/**
	 * 
	 */
	private final Map<String,Integer> elementMap = new LinkedHashMap<>();

	/**
	 * 
	 */
	private final LinkedHashSet<Formula> repeatingUnits = new LinkedHashSet<>();

	/**
	 * 
	 * @param formula
	 * @return Formula
	 */
	public static Formula getFormula( final String formula )
	{
		if( formula == null )
		{
			return null;
		}

		return new Formula( formula );
	}

	/**
	 * 
	 * @param formula
	 */
	private Formula( final String formula )
	{
		init( formula );
	}

	/**
	 * 
	 * @return elementMap copy
	 */
	public Map<String,Integer> getElementMap()
	{
		return new LinkedHashMap<>( elementMap );
	}

	/**
	 * 
	 * @return repeatingUnits copy
	 */
	public Set<Formula> getRepeatingUnits()
	{
		return new LinkedHashSet<>( repeatingUnits );
	}

	/**
	 * 
	 * @param element
	 * @return int
	 */
	public int get( final String element )
	{
		final Integer count = elementMap.get( element );
		return count == null ? 0 : count.intValue();
	}

	/**
	 * 
	 * @param element
	 */
	public void remove( final String element )
	{
		elementMap.remove( element );

		for( Formula repeatingUnit : repeatingUnits )
		{
			repeatingUnit.remove( element );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( final Object obj )
	{
		if( this == obj )
		{
			return true;
		}

		if( obj == null )
		{
			return false;
		}

		if( !( obj instanceof Formula ) )
		{
			return false;
		}

		final Formula objFormula = (Formula)obj;
		objFormula.clean();
		this.clean();

		if( elementMap.size() != objFormula.elementMap.size() )
		{
			return false;
		}

		if( !elementMap.equals( objFormula.elementMap ) )
		{
			return false;
		}

		if( !repeatingUnits.equals( objFormula.repeatingUnits ) )
		{
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + elementMap.hashCode();
		result = PRIME * result + repeatingUnits.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final int ZERO_COUNT = 0;
		final int DEFAULT_COUNT = 1;
		final StringBuffer buffer = new StringBuffer();

		for( Map.Entry<String,Integer> entry : elementMap.entrySet() )
		{
			final int count = entry.getValue().intValue();

			if( count == ZERO_COUNT )
			{
				continue;
			}

			buffer.append( entry.getKey() );

			if( count != DEFAULT_COUNT )
			{
				buffer.append( count );
			}
		}

		for( Formula repeatingUnit : repeatingUnits )
		{
			buffer.append( "(" ); //$NON-NLS-1$
			buffer.append( repeatingUnit );
			buffer.append( ")n" ); //$NON-NLS-1$
		}

		return buffer.toString();
	}

	/**
	 * 
	 * @param element
	 * @param count
	 */
	private void init( final String formula )
	{
		final char N_PREFIX = '(';
		final String N_SUFFIX = ")"; //$NON-NLS-1$
		final StringBuffer number = new StringBuffer();
		final StringBuffer element = new StringBuffer();

		for( int i = 0; i < formula.length(); i++ )
		{
			char c = formula.charAt( i );

			if( c == N_PREFIX )
			{
				final String repeatingUnit = formula.substring( i + 1, formula.indexOf( N_SUFFIX, i ) );
				final Formula repeatingUnitFormula = new Formula( repeatingUnit );

				if( repeatingUnitFormula.size() > 0 )
				{
					repeatingUnits.add( repeatingUnitFormula );
				}

				i += repeatingUnit.length() + 2;
				number.setLength( 0 );
				element.setLength( 0 );
				continue;
			}

			if( c >= '0' && c <= '9' )
			{
				number.append( c );
			}
			else if( ( c >= 'a' && c <= 'z' ) || ( c >= 'A' && c <= 'Z' ) )
			{
				element.append( c );
			}

			if( i == formula.length() - 1 || ( formula.charAt( i + 1 ) >= 'A' && formula.charAt( i + 1 ) <= 'Z' ) || formula.charAt( i + 1 ) == N_PREFIX )
			{
				final int count = number.length() == 0 ? 1 : Integer.parseInt( number.toString() );

				if( count > 0 && element.toString().length() > 0 )
				{
					final int current = elementMap.get( element.toString() ) == null ? 0 : elementMap.get( element.toString() ).intValue();
					elementMap.put( element.toString(), Integer.valueOf( current + count ) );
				}

				number.setLength( 0 );
				element.setLength( 0 );
			}
		}
	}

	/**
	 * 
	 * @return int
	 */
	private int size()
	{
		return elementMap.size() + repeatingUnits.size();
	}

	/**
	 * 
	 */
	private void clean()
	{
		for( Iterator<Map.Entry<String,Integer>> iterator = elementMap.entrySet().iterator(); iterator.hasNext(); )
		{
			final Map.Entry<String,Integer> entry = iterator.next();

			if( entry.getValue().intValue() == 0 )
			{
				iterator.remove();
			}
		}
	}

	/**
	 * 
	 * @param formula1
	 * @param formula2
	 * @param ignoreH
	 * @return boolean
	 */
	public static boolean match( final String formula1, final String formula2, final boolean ignoreH )
	{
		final Formula formula1Obj = new Formula( formula1 );
		final Formula formula2Obj = new Formula( formula2 );

		if( ignoreH )
		{
			final String HYDROGEN = "H"; //$NON-NLS-1$
			formula1Obj.remove( HYDROGEN );
			formula2Obj.remove( HYDROGEN );
		}

		return formula1.equals( formula2 );
	}

	/**
	 * 
	 * @param formula
	 * @param charge
	 * @return String
	 */
	public static String neutralise( final String formula, final int charge )
	{
		if( formula == null )
		{
			return null;
		}

		final int checkedCharge = ( charge == NumberUtils.UNDEFINED ) ? 0 : charge;
		final Formula formulaObj = new Formula( formula );
		final Integer hCountInteger = formulaObj.elementMap.get( "H" ); //$NON-NLS-1$
		final int hCount = ( hCountInteger == null ) ? 0 : hCountInteger.intValue();
		formulaObj.elementMap.put( "H", Integer.valueOf( hCount - checkedCharge ) ); //$NON-NLS-1$

		return formulaObj.toString();
	}

	/**
	 * 
	 * @param formula
	 * @param target
	 * @param replacement
	 * @return Formula
	 */
	public static Formula replace( final Formula formula, final String target, final String replacement )
	{
		return replace( formula, target, new Formula( replacement ), Integer.MAX_VALUE );
	}

	/**
	 * 
	 * @param formula
	 * @param target
	 * @param replacement
	 * @return Formula
	 */
	public static Formula replace( final Formula formula, final String target, final Formula replacement )
	{
		return replace( formula, target, replacement, Integer.MAX_VALUE );
	}

	/**
	 * 
	 * @param formula
	 * @param target
	 * @param replacement
	 * @return Formula
	 */
	public static Formula replace( final Formula formula, final Formula target, final Formula replacement )
	{
		return replace( formula, target, replacement, Integer.MAX_VALUE );
	}

	/**
	 * 
	 * @param formula
	 * @param target
	 * @param replacement
	 * @param count
	 * @return Formula
	 */
	public static Formula replace( final Formula formula, final String target, final String replacement, final int count )
	{
		return replace( formula, target, new Formula( replacement ), count );
	}

	/**
	 * 
	 * @param formula
	 * @param target
	 * @param replacement
	 * @param count
	 * @return Formula
	 */
	public static Formula replace( final Formula formula, final Formula target, final Formula replacement, @SuppressWarnings("unused") final int count )
	{
		final Formula updatedFormula = Formula.getFormula( formula.toString() );

		for( Map.Entry<String,Integer> entry : target.elementMap.entrySet() )
		{
			if( remove( updatedFormula, entry.getKey(), entry.getValue().intValue() ) == 0 )
			{
				return formula;
			}
		}

		add( updatedFormula, replacement, Integer.MAX_VALUE );

		return updatedFormula;
	}

	/**
	 * 
	 * @param formula
	 * @param target
	 * @param replacement
	 * @param count
	 * @return Formula
	 */
	public static Formula replace( final Formula formula, final String target, final Formula replacement, final int count )
	{
		final int targetCount = remove( formula, target, count );

		if( targetCount > 0 )
		{
			add( formula, replacement, count == Integer.MAX_VALUE ? targetCount : count );
		}

		return formula;
	}

	/**
	 * 
	 * @param formula
	 * @param n
	 * @return Formula
	 */
	public static Formula expand( final Formula formula, final int n )
	{
		if( formula.repeatingUnits.size() == 0 )
		{
			throw new UnsupportedOperationException();
		}

		for( Iterator<Formula> iterator = formula.repeatingUnits.iterator(); iterator.hasNext(); )
		{
			add( formula, iterator.next(), n );
			iterator.remove();
			return formula;
		}

		return formula;
	}

	/**
	 * 
	 * @param formula
	 * @param formulaToAdd
	 * @param n
	 */
	private static void add( final Formula formula, final Formula formulaToAdd, final int n )
	{
		if( n > 0 )
		{
			for( Map.Entry<String,Integer> entry : formulaToAdd.elementMap.entrySet() )
			{
				Integer existingCount = formula.elementMap.get( entry.getKey() );
				final int count = ( existingCount == null ) ? 0 : existingCount.intValue();
				formula.elementMap.put( entry.getKey(), Integer.valueOf( count + ( entry.getValue().intValue() * n ) ) );
			}

			for( Formula repeatingUnit : formulaToAdd.repeatingUnits )
			{
				formula.repeatingUnits.add( repeatingUnit );
			}
		}
	}

	/**
	 * 
	 * @param formula
	 * @param target
	 * @param replacement
	 * @param count
	 * @return Formula
	 */
	private static int remove( final Formula formula, final String elementToRemove, final int count )
	{
		final int targetCount = ( formula.elementMap.get( elementToRemove ) == null ) ? 0 : formula.elementMap.get( elementToRemove ).intValue();

		if( targetCount > 0 )
		{
			formula.elementMap.put( elementToRemove, Integer.valueOf( Math.max( 0, targetCount - count ) ) );
		}

		return targetCount;
	}
}