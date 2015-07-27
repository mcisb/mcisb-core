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
package org.mcisb.ui.wizard;

import java.io.*;
import java.util.*;
import org.mcisb.ui.util.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class FileChooserWizardComponent extends WizardComponent
{
	/**
	 * 
	 */
	private final String propertyName;
	
	/**
	 *
	 * @param bean
	 * @param component
	 * @param propertyName
	 */
	public FileChooserWizardComponent( GenericBean bean, FileChooserPanel component, String propertyName )
	{
		super( bean, component );
		this.propertyName = propertyName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mcisb.ui.WizardComponent#update()
	 */
	@Override
	public void update()
	{
		FileChooserPanel fileChooserPanel = (FileChooserPanel)parameterPanel;
		Collection<File> files = fileChooserPanel.getFiles();
		Collection<String> filepaths = new ArrayList<>();
			
		for( Iterator<File> iterator = files.iterator(); iterator.hasNext(); )
		{
			File file = iterator.next();
			filepaths.add( file.getAbsolutePath() ); 
		}
		
		bean.setProperty( propertyName, filepaths );
	}
}