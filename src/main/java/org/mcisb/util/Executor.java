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
import java.util.*;
import org.mcisb.util.io.*;
import org.mcisb.util.task.*;

/**
 * 
 * @author Neil Swainston
 */
public class Executor extends AbstractTask
{
	/**
	 * 
	 */
	public static final int SUCCESS = 0;
	
	/**
	 * 
	 */
	private final String[] commandArray;
	
	/**
	 * 
	 */
	private final StreamReader osStreamReader;
	
	/**
	 * 
	 */
	private final StreamReader esStreamReader;
	
	/**
	 * 
	 */
	private final File workingDirectory;
	
	/**
	 * 
	 */
	private final Map<String,String> additionalEnvs;
	
	/**
	 * 
	 */
	private Process process = null;
	
	/**
	 * 
	 * @param commandArray
	 */
	public Executor( final String[] commandArray )
	{
		this( commandArray, System.out, System.err );
	}
	
	/**
	 * 
	 * @param commandArray
	 * @param os
	 * @param es
	 */
	public Executor( final String[] commandArray, final OutputStream os, final OutputStream es )
	{
		this( Arrays.asList( commandArray ).toArray( new String[ commandArray.length ] ), os, es, null, new HashMap<String,String>() );
	}

	/**
	 * 
	 * @param commandArray
	 * @param workingDirectory 
	 */
	public Executor( final String[] commandArray, final File workingDirectory )
	{
		this( commandArray, System.out, System.err, workingDirectory, new HashMap<String,String>() );
	}
	
	/**
	 * 
	 * @param commandArray
	 * @param workingDirectory 
	 */
	public Executor( final String[] commandArray, final File workingDirectory, final Map<String,String> additionalEnvs )
	{
		this( commandArray, System.out, System.err, workingDirectory, additionalEnvs );
	}
	
	/**
	 * 
	 * @param commandArray
	 * @param os
	 * @param es
	 * @param workingDirectory 
	 */
	public Executor( final String[] commandArray, final OutputStream os, final OutputStream es, final File workingDirectory, final Map<String,String> additionalEnvs )
	{
		this( commandArray, new StreamReader( os ), new StreamReader( es ), workingDirectory, additionalEnvs );
	}
	
	/**
	 * 
	 * @param commandArray
	 * @param osStreamReader
	 * @param esStreamReader
	 * @param workingDirectory 
	 */
	public Executor( final String[] commandArray, final StreamReader osStreamReader, final StreamReader esStreamReader, final File workingDirectory, final Map<String,String> additionalEnvs )
	{
		this.commandArray = Arrays.asList( commandArray ).toArray( new String[ commandArray.length ] );
		this.osStreamReader = osStreamReader;
		this.esStreamReader = esStreamReader;
		this.workingDirectory = workingDirectory;
		this.additionalEnvs = additionalEnvs;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.mcisb.util.task.TaskImpl#doTask()
	 */
	@Override
	public Serializable doTask() throws Exception
	{
		final ProcessBuilder pb = new ProcessBuilder( commandArray );
		final Map<String,String> env = pb.environment();
		
		for( Map.Entry<String,String> additionalEnv : additionalEnvs.entrySet() )
		{
			env.put( additionalEnv.getKey(), additionalEnv.getValue() );
		}
		
		if( workingDirectory != null )
		{
			pb.directory( workingDirectory );
		}
		
		process = pb.start();
		
		osStreamReader.setInputStream( process.getInputStream() );
		esStreamReader.setInputStream( process.getErrorStream() );
		
		final Thread osThread = new Thread( osStreamReader );
		final Thread esThread = new Thread( esStreamReader );
		
		osThread.start();
		esThread.start();
		osThread.join();
		esThread.join();
		
		return Integer.valueOf( process.exitValue() );
	}
	
	/**
	 * 
	 * @param command
	 * @throws IOException
	 */
	public void write( final String command ) throws IOException
	{
		if( process != null )
		{
			BufferedWriter writer = null;
			
			try
			{
				final String LINE_SEPARATOR = System.getProperty( "line.separator" ); //$NON-NLS-1$
				writer = new BufferedWriter( new OutputStreamWriter( process.getOutputStream() ) );
				writer.write( command );
				writer.write( LINE_SEPARATOR );
				writer.flush();
			}
			finally
			{
				if( writer != null )
				{
					writer.close();
				}
			}
		}
	}
	
	/**
	 * 
	 * @author Neil Swainston
	 *
	 */
	class ExecutorStreamReader extends StreamReader
	{
		/**
		 * 
		 */
		private static final String MESSAGE_LABEL = "MESSAGE:"; //$NON-NLS-1$
		
		/**
		 * 
		 */
		private static final String PROGRESS_LABEL = "PROGRESS:"; //$NON-NLS-1$
		
		/**
		 * 
		 * @param is
		 * @param os
		 */
		ExecutorStreamReader( final OutputStream os )
		{
			super( os );
		}
		
		/* 
		 * (non-Javadoc)
		 * @see org.mcisb.util.io.StreamReader#read()
		 */
		@Override
		public void read() throws IOException
		{
			final String LINE_SEPARATOR = System.getProperty( "line.separator" ); //$NON-NLS-1$
			String line = null;
	        
			final BufferedWriter outputStreamWriter = new BufferedWriter( new OutputStreamWriter( os ) );
	        final BufferedReader inputStreamReader = new BufferedReader( new InputStreamReader( is ) );
	        
	        while( ( line = inputStreamReader.readLine() ) != null )
	        {
	        	if( line.startsWith( MESSAGE_LABEL ) )
	        	{
	        		setMessage( line.substring( MESSAGE_LABEL.length() ).trim() );
	        	}
	        	else if( line.startsWith( PROGRESS_LABEL ) )
	        	{
	        		setProgress( (int)Float.parseFloat( line.substring( PROGRESS_LABEL.length() ).trim() ) );
	        	}
	        	
	        	outputStreamWriter.write( line );
	        	outputStreamWriter.write( LINE_SEPARATOR );
	        	outputStreamWriter.flush();
	        }
		}
	}
}