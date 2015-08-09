/**
 * 
 */
package org.mcisb.util.xml;

/**
 * @author Neil Swainston
 * 
 */
public class ProcessingInstruction
{
	/**
	 * 
	 */
	private final String target;

	/**
	 * 
	 */
	private final String data;

	/**
	 * 
	 * @param target
	 * @param data
	 */
	public ProcessingInstruction( final String target, final String data )
	{
		this.target = target;
		this.data = data;
	}

	/**
	 * @return the target
	 */
	public String getTarget()
	{
		return target;
	}

	/**
	 * @return the data
	 */
	public String getData()
	{
		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		if( !( obj instanceof ProcessingInstruction ) )
		{
			return false;
		}

		return ( (ProcessingInstruction)obj ).target.equals( target ) && ( (ProcessingInstruction)obj ).data.equals( data );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return target.hashCode() * data.hashCode();
	}
}