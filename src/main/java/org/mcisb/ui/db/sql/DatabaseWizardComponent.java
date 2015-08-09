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
package org.mcisb.ui.db.sql;

import java.util.*;
import java.util.prefs.*;
import org.mcisb.ui.util.*;
import org.mcisb.ui.wizard.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class DatabaseWizardComponent
{
	/**
	 * 
	 */
	public static final String SQL = "SQL"; //$NON-NLS-1$

	/**
	 * 
	 */
	public static final String NON_SQL = "NON_SQL"; //$NON-NLS-1$

	/**
	 * 
	 */
	public static final String NO_USERNAME_PASSWORD = "NO_USERNAME_PASSWORD"; //$NON-NLS-1$

	/**
	 * 
	 * @param bean
	 * @param title
	 * @param preferences
	 * @return DefaultWizardComponent
	 */
	public static DefaultWizardComponent getInstance( final GenericBean bean, final String title, final Preferences preferences )
	{
		return getInstance( bean, title, preferences, SQL );
	}

	/**
	 * 
	 * @param bean
	 * @param title
	 * @param preferences
	 * @param type
	 * @return DefaultWizardComponent
	 */
	public static DefaultWizardComponent getInstance( final GenericBean bean, final String title, final Preferences preferences, final String type )
	{
		final ResourceBundle resourceBundle = ResourceBundle.getBundle( "org.mcisb.ui.db.sql.messages" ); //$NON-NLS-1$

		// Determine parameter methods:
		final Map<Object,Object> parameterPropertyNameToKey = new HashMap<>();
		final Map<Object,Object> parameterOptions = new LinkedHashMap<>();
		final Map<Object,Boolean> passwordMap = new LinkedHashMap<>();

		final String dbServerNamePrompt = resourceBundle.getString( "DatabaseWizardComponent.serverNamePrompt" ); //$NON-NLS-1$
		parameterPropertyNameToKey.put( org.mcisb.db.PropertyNames.DB_SERVER_NAME, dbServerNamePrompt );
		parameterOptions.put( dbServerNamePrompt, null );
		passwordMap.put( dbServerNamePrompt, Boolean.FALSE );

		if( type.equals( SQL ) )
		{
			final String dbDriverPrompt = resourceBundle.getString( "DatabaseWizardComponent.driverPrompt" ); //$NON-NLS-1$
			parameterPropertyNameToKey.put( org.mcisb.db.PropertyNames.DB_DRIVER, dbDriverPrompt );
			parameterOptions.put( dbDriverPrompt, null );
			passwordMap.put( dbDriverPrompt, Boolean.FALSE );

			final String dbCollectionNamePrompt = resourceBundle.getString( "DatabaseWizardComponent.collectionNamePrompt" ); //$NON-NLS-1$
			parameterPropertyNameToKey.put( org.mcisb.db.PropertyNames.DB_COLLECTION_NAME, dbCollectionNamePrompt );
			parameterOptions.put( dbCollectionNamePrompt, null );
			passwordMap.put( dbCollectionNamePrompt, Boolean.FALSE );
		}

		if( !type.equals( NO_USERNAME_PASSWORD ) )
		{
			final String userPrompt = resourceBundle.getString( "DatabaseWizardComponent.userPrompt" ); //$NON-NLS-1$
			parameterPropertyNameToKey.put( org.mcisb.db.PropertyNames.USERNAME, userPrompt );
			parameterOptions.put( userPrompt, null );
			passwordMap.put( userPrompt, Boolean.FALSE );

			final String passwordPrompt = resourceBundle.getString( "DatabaseWizardComponent.passwordPrompt" ); //$NON-NLS-1$
			parameterPropertyNameToKey.put( org.mcisb.db.PropertyNames.PASSWORD, passwordPrompt );
			parameterOptions.put( passwordPrompt, null );
			passwordMap.put( passwordPrompt, Boolean.TRUE );
		}

		return new DefaultWizardComponent( bean, new DefaultParameterPanel( title, parameterOptions, passwordMap, preferences ), parameterPropertyNameToKey );
	}
}