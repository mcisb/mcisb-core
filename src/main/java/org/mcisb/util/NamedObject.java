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
 * 
 * @author Neil Swainston
 */
public class NamedObject extends GenericBean
{
	/**
	 * 
	 */
	public static final String NAME = "NAME"; //$NON-NLS-1$

	/**
	 * 
	 */
	protected String name;

	/**
	 * 
	 */
	private final Collection<Object> ontologyTerms = new LinkedHashSet<>();

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	public NamedObject()
	{
		// No implementation
	}

	/**
	 * 
	 * @param name
	 */
	public NamedObject( final String name )
	{
		this();
		this.name = name;
	}

	/**
	 * 
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName( String name )
	{
		final String oldName = this.name;
		this.name = name;
		support.firePropertyChange( NAME, oldName, this.name );
	}

	/**
	 * 
	 * @return String
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * 
	 * @param description
	 */
	public void setDescription( final String description )
	{
		this.description = description;
	}

	/**
	 * 
	 * @param ontologyTerm
	 * @return boolean
	 */
	public boolean addOntologyTerm( final Object ontologyTerm )
	{
		return ontologyTerms.add( ontologyTerm );
	}

	/**
	 * 
	 * @param ontologyTerm
	 * @return boolean
	 */
	public boolean removeOntologyTerm( final Object ontologyTerm )
	{
		return ontologyTerms.remove( ontologyTerm );
	}

	/**
	 * 
	 * @return Collection
	 */
	public Collection<Object> getOntologyTerms()
	{
		return ontologyTerms;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return name;
	}
}