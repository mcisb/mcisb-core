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

import java.beans.*;
import java.io.*;
import java.util.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.mcisb.util.*;

/**
 * 
 * @author Neil Swainston
 */
public class ExcelReader extends PropertyChangeSupported implements PropertyChangeListener
{
	/**
	 * 
	 */
	public final static String EXCEL_CELL = "EXCEL_CELL"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private final List<Vector<Object>> data = new ArrayList<>();
	
	/**
	 * 
	 */
	private int rowNumber = NumberUtils.UNDEFINED;
	
	/**
	 * 
	 */
	// private final InputStream is;
	
	/**
	 * 
	 */
	protected final HSSFWorkbook workbook;
	
	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	public ExcelReader( final File file ) throws IOException
	{
		try( final InputStream is = new FileInputStream( file ) )
		{
			this.workbook = new HSSFWorkbook( is );
		}
	}
	
	/**
	 * 
	 * @return List<String>
	 */
	public List<String> getSheetNames()
	{
		final List<String> sheetNames = new ArrayList<>();
		
		for( int i = 0; i < workbook.getNumberOfSheets(); i++ )
		{
			sheetNames.add( workbook.getSheetName( i ) );
		}
		
		return sheetNames;
	}
	
	/**
	 * 
	 * @param sheetName
	 * @return List
	 */
	public List<Object> getColumnNames( String sheetName )
	{
		final List<Object> list = new ArrayList<>();
        final HSSFRow row = getColumns( workbook.getSheet( sheetName ) );
		
		for( Iterator<?> iterator = row.cellIterator(); iterator.hasNext(); )
		{
			final Object o = iterator.next();
			
			if( o instanceof HSSFCell )
			{
				final HSSFCell cell = (HSSFCell)o;
				list.add( getValue( cell ) );
			}
		}
		
		return list;
	}
	
	/**
	 *
	 * @param sheetName
	 * @return List
	 */
	public List<List<Object>> getData( String sheetName )
	{
		data.clear();
		rowNumber = NumberUtils.UNDEFINED;
		
		addPropertyChangeListener( this );
		read( sheetName );
		removePropertyChangeListener( this );
        
        return new ArrayList<List<Object>>( data );
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange( PropertyChangeEvent e )
	{
		if( e.getPropertyName().equals( EXCEL_CELL ) )
		{
			final Object newValue = e.getNewValue();
			
			if( newValue instanceof ExcelCell )
			{
				final ExcelCell excelCell = (ExcelCell)newValue;
				
				if( rowNumber != excelCell.getRow() )
				{
					data.add( new Vector<>() );
				}
				
				rowNumber = excelCell.getRow();
				
				final Vector<Object> rowData = data.get( rowNumber );
				rowData.setSize( Math.max( rowData.size(), excelCell.getColumn() + 1 ) );
				rowData.add( excelCell.getColumn(), excelCell.getValue() );
			}
		}
	}
	
	/**
	 * 
	 * @param sheetName
	 */
	private void read( final String sheetName )
	{
		final HSSFSheet sheet = workbook.getSheet( sheetName );
		ExcelCell excelCell = null;
		HSSFRow row = null;
        int currentRowNumber = 0;
        
        while( ( row = sheet.getRow( currentRowNumber ) ) != null )
        {
        	for( Iterator<?> iterator = row.cellIterator(); iterator.hasNext(); )
        	{
        		final Object o = iterator.next();
    			
    			if( o instanceof HSSFCell )
    			{
		        	final HSSFCell cell = (HSSFCell)o;
		        	final ExcelCell newExcelCell = new ExcelCell( getValue( cell ), currentRowNumber, cell.getColumnIndex() );
		        	support.firePropertyChange( EXCEL_CELL, excelCell, newExcelCell );
		        	excelCell = newExcelCell;
    			}
        	}
        	
        	currentRowNumber++;
        }
	}
	
	/**
	 * 
	 * @param sheet
	 * @return HSSFRow
	 */
	private static HSSFRow getColumns( HSSFSheet sheet )
	{
		final int COLUMN_NAME_ROW = 0;
		return sheet.getRow( COLUMN_NAME_ROW );
	}
	
	/**
	 * 
	 * @param cell
	 * @return Object
	 */
	private static Object getValue( HSSFCell cell )
	{
		if( cell == null )
		{
			return null;
		}
		
		switch( cell.getCellType() )
		{
			case Cell.CELL_TYPE_BOOLEAN:
			{
				return Boolean.valueOf( cell.getBooleanCellValue() );
			}
			case Cell.CELL_TYPE_NUMERIC:
			{
				return Double.valueOf( cell.getNumericCellValue() );
			}
			case Cell.CELL_TYPE_STRING:
			{
				return cell.getRichStringCellValue().getString().trim();
			}
			default:
			{
				return null;
			}
		}
	}
}