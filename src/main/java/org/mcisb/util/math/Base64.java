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
package org.mcisb.util.math;

/**
 * Encodes and decodes to and from Base64 notation.
 * 
 * <p>
 * Change Log:
 * </p>
 * <ul>
 * <li>v1.4 - Added helper methods to read/write files.</li>
 * <li>v1.3.6 - Fixed OutputStream.flush() so that 'position' is reset.</li>
 * <li>v1.3.5 - Added flag to turn on and off line breaks. Fixed bug in input
 * stream where last buffer being read, if not completely full, was not
 * returned.</li>
 * <li>v1.3.4 - Fixed when "improperly padded stream" error was thrown at the
 * wrong time.</li>
 * <li>v1.3.3 - Fixed I/O streams which were totally messed up.</li>
 * </ul>
 * 
 * <p>
 * I am placing this code in the Public Domain. Do with it as you will. This
 * software comes with no guarantees or warranties but with plenty of
 * well-wishing instead! Please visit <a
 * href="http://iharder.net/xmlizable">http://iharder.net/xmlizable</a>
 * periodically to check for updates or to contribute improvements.
 * </p>
 * 
 * @author Robert Harder
 * @author rob@iharder.net
 * @version 1.4
 */
final class Base64
{
	/** The equals sign (=) as a byte. */
	private final static byte EQUALS_SIGN = (byte)'=';

	/** The 64 valid Base64 values. */
	private final static byte[] ALPHABET = { (byte)'A', (byte)'B', (byte)'C', (byte)'D', (byte)'E', (byte)'F', (byte)'G', (byte)'H', (byte)'I', (byte)'J', (byte)'K', (byte)'L', (byte)'M', (byte)'N', (byte)'O', (byte)'P', (byte)'Q', (byte)'R', (byte)'S', (byte)'T', (byte)'U', (byte)'V', (byte)'W', (byte)'X', (byte)'Y', (byte)'Z', (byte)'a', (byte)'b', (byte)'c', (byte)'d', (byte)'e', (byte)'f', (byte)'g', (byte)'h', (byte)'i', (byte)'j', (byte)'k', (byte)'l', (byte)'m', (byte)'n', (byte)'o', (byte)'p', (byte)'q', (byte)'r', (byte)'s', (byte)'t', (byte)'u', (byte)'v', (byte)'w', (byte)'x', (byte)'y', (byte)'z', (byte)'0', (byte)'1', (byte)'2', (byte)'3', (byte)'4', (byte)'5', (byte)'6', (byte)'7', (byte)'8', (byte)'9', (byte)'+', (byte)'/' };

	/**
	 * Translates a Base64 value to either its 6-bit reconstruction value or a
	 * negative number indicating some other meaning.
	 **/
	private final static byte[] DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal
																					// 0
																					// -
																					// 8
			-5, -5, // Whitespace: Tab and Linefeed
			-9, -9, // Decimal 11 - 12
			-5, // Whitespace: Carriage Return
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 14 -
																// 26
			-9, -9, -9, -9, -9, // Decimal 27 - 31
			-5, // Whitespace: Space
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 33 - 42
			62, // Plus sign at decimal 43
			-9, -9, -9, // Decimal 44 - 46
			63, // Slash at decimal 47
			52, 53, 54, 55, 56, 57, 58, 59, 60, 61, // Numbers zero through nine
			-9, -9, -9, // Decimal 58 - 60
			-1, // Equals sign at decimal 61
			-9, -9, -9, // Decimal 62 - 64
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, // Letters 'A' through
															// 'N'
			14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, // Letters 'O'
															// through 'Z'
			-9, -9, -9, -9, -9, -9, // Decimal 91 - 96
			26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, // Letters 'a'
																// through 'm'
			39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, // Letters 'n'
																// through 'z'
			-9, -9, -9, -9 // Decimal 123 - 126 */
	};

	/**
     * 
     */
	private final static byte WHITE_SPACE_ENC = -5; // Indicates white space in
													// encoding

	/**
     * 
     */
	private final static byte EQUALS_SIGN_ENC = -1; // Indicates equals sign in
													// encoding

	/**
	 * Encodes a byte array into Base64 notation.
	 * 
	 * @param source
	 *            The data to convert
	 * @return String
	 */
	static String encode( byte[] source )
	{
		int off = 0;
		int len = source.length;
		int len43 = len * 4 / 3;
		byte[] outBuff = new byte[ ( len43 ) // Main 4:3
				+ ( ( len % 3 ) > 0 ? 4 : 0 ) ]; // Account for padding
		int d = 0;
		int e = 0;
		int len2 = len - 2;

		for( ; d < len2; d += 3, e += 4 )
		{
			encode3to4( source, d + off, 3, outBuff, e );
		} // en dfor: each piece of array

		if( d < len )
		{
			encode3to4( source, d + off, len - d, outBuff, e );
			e += 4;
		} // end if: some padding needed

		return new String( outBuff, 0, e );
	} // end encodeBytes

	/**
	 * Decodes data from Base64 notation.
	 * 
	 * @param bytes
	 *            the byte array to decode
	 * @return the decoded data
	 */
	static byte[] decode( byte[] bytes )
	{
		return decode( bytes, 0, bytes.length );
	} // end decode

	/**
	 * Encodes up to three bytes of the array <var>source</var> and writes the
	 * resulting four Base64 bytes to <var>destination</var>. The source and
	 * destination arrays can be manipulated anywhere along their length by
	 * specifying <var>srcOffset</var> and <var>destOffset</var>. This method
	 * does not check to make sure your arrays are large enough to accomodate
	 * <var>srcOffset</var> + 3 for the <var>source</var> array or
	 * <var>destOffset</var> + 4 for the <var>destination</var> array. The
	 * actual number of significant bytes in your array is given by
	 * <var>numSigBytes</var>.
	 * 
	 * @param source
	 *            the array to convert
	 * @param srcOffset
	 *            the index where conversion begins
	 * @param numSigBytes
	 *            the number of significant bytes in your array
	 * @param destination
	 *            the array to hold the conversion
	 * @param destOffset
	 *            the index where output will be put
	 * @return the <var>destination</var> array
	 */
	private final static byte[] encode3to4( byte[] source, int srcOffset, int numSigBytes, byte[] destination, int destOffset )
	{
		// 1 2 3
		// 01234567890123456789012345678901 Bit position
		// --------000000001111111122222222 Array position from threeBytes
		// --------| || || || | Six bit groups to index ALPHABET
		// >>18 >>12 >> 6 >> 0 Right shift necessary
		// 0x3f 0x3f 0x3f Additional AND

		// Create buffer with zero-padding if there are only one or two
		// significant bytes passed in the array.
		// We have to shift left 24 in order to flush out the 1's that appear
		// when Java treats a value as negative that is cast from a byte to an
		// int.
		int inBuff = ( numSigBytes > 0 ? ( ( source[ srcOffset ] << 24 ) >>> 8 ) : 0 ) | ( numSigBytes > 1 ? ( ( source[ srcOffset + 1 ] << 24 ) >>> 16 ) : 0 ) | ( numSigBytes > 2 ? ( ( source[ srcOffset + 2 ] << 24 ) >>> 24 ) : 0 );

		switch( numSigBytes )
		{
			case 3:
				destination[ destOffset ] = ALPHABET[ ( inBuff >>> 18 ) ];
				destination[ destOffset + 1 ] = ALPHABET[ ( inBuff >>> 12 ) & 0x3f ];
				destination[ destOffset + 2 ] = ALPHABET[ ( inBuff >>> 6 ) & 0x3f ];
				destination[ destOffset + 3 ] = ALPHABET[ ( inBuff ) & 0x3f ];
				return destination;

			case 2:
				destination[ destOffset ] = ALPHABET[ ( inBuff >>> 18 ) ];
				destination[ destOffset + 1 ] = ALPHABET[ ( inBuff >>> 12 ) & 0x3f ];
				destination[ destOffset + 2 ] = ALPHABET[ ( inBuff >>> 6 ) & 0x3f ];
				destination[ destOffset + 3 ] = EQUALS_SIGN;
				return destination;

			case 1:
				destination[ destOffset ] = ALPHABET[ ( inBuff >>> 18 ) ];
				destination[ destOffset + 1 ] = ALPHABET[ ( inBuff >>> 12 ) & 0x3f ];
				destination[ destOffset + 2 ] = EQUALS_SIGN;
				destination[ destOffset + 3 ] = EQUALS_SIGN;
				return destination;

			default:
				return destination;
		} // end switch
	} // end encode3to4

	/**
	 * Decodes four bytes from array <var>source</var> and writes the resulting
	 * bytes (up to three of them) to <var>destination</var>. The source and
	 * destination arrays can be manipulated anywhere along their length by
	 * specifying <var>srcOffset</var> and <var>destOffset</var>. This method
	 * does not check to make sure your arrays are large enough to accomodate
	 * <var>srcOffset</var> + 4 for the <var>source</var> array or
	 * <var>destOffset</var> + 3 for the <var>destination</var> array. This
	 * method returns the actual number of bytes that were converted from the
	 * Base64 encoding.
	 * 
	 * 
	 * @param source
	 *            the array to convert
	 * @param srcOffset
	 *            the index where conversion begins
	 * @param destination
	 *            the array to hold the conversion
	 * @param destOffset
	 *            the index where output will be put
	 * @return the number of decoded bytes converted
	 */
	private static int decode4to3( byte[] source, int srcOffset, byte[] destination, int destOffset )
	{
		// Example: Dk==
		if( source[ srcOffset + 2 ] == EQUALS_SIGN )
		{
			// Two ways to do the same thing. Don't know which way I like best.
			// int outBuff = ( ( DECODABET[ source[ srcOffset ] ] << 24 ) >>> 6
			// )
			// | ( ( DECODABET[ source[ srcOffset + 1] ] << 24 ) >>> 12 );
			int outBuff = ( ( DECODABET[ source[ srcOffset ] ] & 0xFF ) << 18 ) | ( ( DECODABET[ source[ srcOffset + 1 ] ] & 0xFF ) << 12 );

			destination[ destOffset ] = (byte)( outBuff >>> 16 );
			return 1;
		}

		// Example: DkL=
		else if( source[ srcOffset + 3 ] == EQUALS_SIGN )
		{
			// Two ways to do the same thing. Don't know which way I like best.
			// int outBuff = ( ( DECODABET[ source[ srcOffset ] ] << 24 ) >>> 6
			// )
			// | ( ( DECODABET[ source[ srcOffset + 1 ] ] << 24 ) >>> 12 )
			// | ( ( DECODABET[ source[ srcOffset + 2 ] ] << 24 ) >>> 18 );
			int outBuff = ( ( DECODABET[ source[ srcOffset ] ] & 0xFF ) << 18 ) | ( ( DECODABET[ source[ srcOffset + 1 ] ] & 0xFF ) << 12 ) | ( ( DECODABET[ source[ srcOffset + 2 ] ] & 0xFF ) << 6 );

			destination[ destOffset ] = (byte)( outBuff >>> 16 );
			destination[ destOffset + 1 ] = (byte)( outBuff >>> 8 );
			return 2;
		}

		// Example: DkLE
		else
		{
			// Two ways to do the same thing. Don't know which way I like best.
			// int outBuff = ( ( DECODABET[ source[ srcOffset ] ] << 24 ) >>> 6
			// )
			// | ( ( DECODABET[ source[ srcOffset + 1 ] ] << 24 ) >>> 12 )
			// | ( ( DECODABET[ source[ srcOffset + 2 ] ] << 24 ) >>> 18 )
			// | ( ( DECODABET[ source[ srcOffset + 3 ] ] << 24 ) >>> 24 );
			int outBuff = ( ( DECODABET[ source[ srcOffset ] ] & 0xFF ) << 18 ) | ( ( DECODABET[ source[ srcOffset + 1 ] ] & 0xFF ) << 12 ) | ( ( DECODABET[ source[ srcOffset + 2 ] ] & 0xFF ) << 6 ) | ( ( DECODABET[ source[ srcOffset + 3 ] ] & 0xFF ) );

			destination[ destOffset ] = (byte)( outBuff >> 16 );
			destination[ destOffset + 1 ] = (byte)( outBuff >> 8 );
			destination[ destOffset + 2 ] = (byte)( outBuff );

			return 3;
		}
	} // end decodeToBytes

	/**
	 * Decodes Base64 content in byte array format and returns the decoded byte
	 * array.
	 * 
	 * @param source
	 *            The Base64 encoded data
	 * @param off
	 *            The offset of where to begin decoding
	 * @param len
	 *            The length of characters to decode
	 * @return decoded data
	 */
	private static byte[] decode( byte[] source, int off, int len )
	{
		int len34 = len * 3 / 4;
		byte[] outBuff = new byte[ len34 ];
		int outBuffPosn = 0;

		byte[] b4 = new byte[ 4 ];
		int b4Posn = 0;
		int i = 0;
		byte sbiCrop = 0;
		byte sbiDecode = 0;
		for( i = off; i < off + len; i++ )
		{
			sbiCrop = (byte)( source[ i ] & 0x7f );
			sbiDecode = DECODABET[ sbiCrop ];

			if( sbiDecode >= WHITE_SPACE_ENC )
			{
				if( sbiDecode >= EQUALS_SIGN_ENC )
				{
					b4[ b4Posn++ ] = sbiCrop;

					if( b4Posn > 3 )
					{
						outBuffPosn += decode4to3( b4, 0, outBuff, outBuffPosn );
						b4Posn = 0;

						// If that was the equals sign, break out of 'for' loop
						if( sbiCrop == EQUALS_SIGN )
						{
							break;
						}
					}
				}
			}
			else
			{
				throw new IllegalArgumentException();
			} // end else:
		}

		byte[] out = new byte[ outBuffPosn ];
		System.arraycopy( outBuff, 0, out, 0, outBuffPosn );
		return out;
	}
}