package com.lgu.common.util;

public class ByteUtil {

	public static void makeLongMsg(byte[] _msg, int _offset, long _data) {
		_msg[_offset++] = (byte) (_data >>> 56);
		_msg[_offset++] = (byte) ((_data << 8) >>> 56);
		_msg[_offset++] = (byte) ((_data << 16) >>> 56);
		_msg[_offset++] = (byte) ((_data << 24) >>> 56);
		_msg[_offset++] = (byte) ((_data << 32) >>> 56);
		_msg[_offset++] = (byte) ((_data << 40) >>> 56);
		_msg[_offset++] = (byte) ((_data << 48) >>> 56);
		_msg[_offset++] = (byte) ((_data << 56) >>> 56);
	}

	public static void makeIntMsg(byte[] _msg, int _offset, int _data) {
		_msg[_offset++] = (byte) (_data >>> 24);
		_msg[_offset++] = (byte) ((_data << 8) >>> 24);
		_msg[_offset++] = (byte) ((_data << 16) >>> 24);
		_msg[_offset++] = (byte) ((_data << 24) >>> 24);
	}

	public static void makeIntMsg2(byte[] _msg, int _offset, int _data) {
		_msg[_offset++] = (byte) ((_data << 24) >>> 24);
		_msg[_offset++] = (byte) ((_data << 16) >>> 24);
		_msg[_offset++] = (byte) ((_data << 8) >>> 24);
		_msg[_offset++] = (byte) (_data >>> 24);
	}

	public static void makeShortMsg(byte[] _msg, int _offset, short _data) {
		_msg[_offset++] = (byte) (_data >>> 8);
		_msg[_offset++] = (byte) ((_data << 8) >>> 8);
	}

	public static void makeBytesMsg(
			byte[] _msg,
			int _offset,
			byte[] _data,
			int _len) {

		if (_data == null) {
			makeNullBytes(_msg, _offset, _len);
			return;
		}

		for (int i = 0; i < _data.length; i++) {
			_msg[_offset++] = _data[i];
		}

		if (_data.length < _len) {
			for (int i = 0; i < (_len - _data.length); i++) {
				_msg[_offset++] = (byte) 0x00;
			}
		}
	} //end makeBytesMsg()

	public static void makeBytes20Msg(
			byte[] _msg,
			int _offset,
			byte[] _data,
			int _len) {

		if (_data == null) {
			makeNullBytes(_msg, _offset, _len);
			return;
		}

		for (int i = 0; i < _data.length; i++) {
			_msg[_offset++] = _data[i];
		}

		if (_data.length < _len) {
			for (int i = 0; i < (_len - _data.length); i++) {
				_msg[_offset++] = (byte) 0x20;
			}
		}
	} //end makeBytes20Msg()

	public static void makeBytesDecimal20Msg(
			byte[] _msg,
			int _offset,
			byte[] _data,
			int _len) {

		if (_data == null) {
			makeNullBytes(_msg, _offset, _len);
			return;
		}

		if (_data.length < _len) {
			for (int i = 0; i < (_len - _data.length); i++) {
				_msg[_offset++] = (byte) 0x20;
			}
		}

		for (int i = 0; i < _data.length; i++) {
			_msg[_offset++] = _data[i];
		}


	} //end makeBytes20Msg()

	public static void makeBytes30Msg(
			byte[] _msg,
			int _offset,
			byte[] _data,
			int _len) {

		if (_data == null) {
			makeNullBytes(_msg, _offset, _len);
			return;
		}

		if (_data.length < _len) {
			for (int i = 0; i < (_len - _data.length); i++) {
				_msg[_offset++] = (byte) 0x30;
			}
		}

		for (int i = 0; i < _data.length; i++) {
			_msg[_offset++] = _data[i];
		}
	} //end makeBytes20Msg()

	/** �߱��� ���忡 ���߱� ���� ����   */
	public static void makeOctetBytesMsg(
			byte[] _msg,
			int _offset,
			byte[] _data,
			int _len) {

		if (_data == null) {
			makeNullBytes(_msg, _offset, _len);
			return;
		}

		if (_data.length < _len) {
			for (int i = 0; i < (_len - _data.length); i++) {
				//_msg[_offset++] = (byte) 0x20;
				_msg[_offset++] = (byte) 0x30;
			}
		}

		for (int i = 0; i < _data.length; i++) {
			_msg[_offset++] = _data[i];
		}

	} //end makeBytesMsg()

	public static void makeByteMsg(byte[] _msg, int _offset, byte _data) {
		_msg[_offset++] = _data;
	} //end makeByteMsg

	public static void makeNullBytes(byte[] _msg, int _offset, int _len) {
		for (int i = 0; i < _len; i++) {
			_msg[_offset++] = 0;
		}
	} //end makeNullBytes

	/**********************************************************************************************
	* 이하 소켓으로 읽어들인 데이터를 프로토콜에 맞게 분석하는 메소드들                          *
	**********************************************************************************************/

	public static long readLong(byte[] _data, int _offset) {
		return (
				(_data[_offset++] << 56)
				| ((_data[_offset++] << 56) >>> 8)
				| ((_data[_offset++] << 56) >>> 16)
				| ((_data[_offset++] << 56) >>> 24)
				| ((_data[_offset++] << 56) >>> 32)
				| ((_data[_offset++] << 56) >>> 40)
				| ((_data[_offset++] << 56) >>> 48)
				| ((_data[_offset++] << 56) >>> 56));
	}
	/*
	   public static long readUnsignedInt(byte[] _data, int _offset) {
		   byte[] buffer = ByteUtil.readBytes(_data, _offset, 4);

		   String temp = "";
		   long total = 0;
		   for(int i=0; i<buffer.length; i++) {
			   temp += Util.toHexaString(buffer[i]);
		   }

		   for(int i=0; i<temp.length(); i++) {
			   long l1 = 1;
			   for(int j=0; j<(8-i-1); j++) {
				   l1 *= 16;
			   }
			   total += (Long.parseLong(temp.substring(i, i+1)) *l1);
		   }
		   return total;
	   }
	 */
	/*
	   public static long readUnsignedInt(int _data) {
		   byte[] b = new byte[4];
		   makeIntMsg(b, 0, _data);
		   return readUnsignedInt(b, 0);
	   }
	 */
	public static int readInt(byte[] _data, int _offset) {
		return (
				(_data[_offset++] << 24)
				| ((_data[_offset++] << 24) >>> 8)
				| ((_data[_offset++] << 24) >>> 16)
				| ((_data[_offset++] << 24) >>> 24));
	}

	public static short readShort(byte[] _data, int _offset) {
		return (short)
				((_data[_offset++] << 8) | ((_data[_offset++] << 8) >>> 8));
	}

	// unsigned
	public static int convertTwoBytesToInt2 (byte b1, byte b2)      
	{
		return (b2 & 0xFF) << 8 | (b1 & 0xFF);
	}

	public static byte[] readBytes(byte[] _data, int _offset, int _size) {
		byte[] result = new byte[_size];

		for (int i = 0; i < _size; i++) {
			result[i] = (byte) _data[_offset++];
		}

		return result;
	} //end readBytes()

	/*public static byte[] readBytes2(byte[] _data, int _offset, int _size) {
		byte[] result = new byte[_data.length - _offset];

		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) _data[_offset++];
		}

		result = StringUtil.cutFirstStrInByte(new String(result), _size).getBytes();

		return result;
	} //end readBytes()
	 */
	public static byte[] readOctetBytes(byte[] _data, int _offset, int _size) {
		byte[] result = new byte[_size];

		for (int i = _size - _data.length; i < _size; i++) {
			result[i] = (byte) _data[_offset++];
		}

		return result;
	} //end readBytes()

	/**
	소켓에서 읽어들인 데이타로 부터 한 바이트를 읽기 위한 메소프.
	<p>
	@param _data: 소켓에서 읽어들인 전체 데이타
	@param _offset: 각 바이트가 세팅되어야 할 위치를 나타내는 포인트
	@return: 데이타로부터 읽어들인 바이트
	*/
	public static byte readByte(byte[] _data, int _offset) {
		//byte result = 0x00;
		//result = (byte) _data[_offset++];
		//return result;
		return (byte) _data[_offset++];
	}

	public static String toHexaString(int trans) {
		String temp = Integer.toHexString(trans);
		if (temp.length() == 1) {
			temp = "0" + temp;
		}
		return temp;
	}
	public static String writeHexaLog(byte[] msg, boolean writeChar) {
		StringBuffer sb = new StringBuffer();

		int index = 0;
		byte[] buf = new byte[16];

		try {
			for (int i=0; i<msg.length; i++) {

				if((i & 0x0000000f) == 0x00000000){
					sb.append(calucLineNumber(i)).append(" ");
				}

				sb.append(forDigit((msg[i] >> 4) & 0x0f, 16));
				sb.append(forDigit(msg[i] & 0x0f, 16));

				buf[index] = toMarkByte(msg[i]);

				if((i & 0x00000001) == 0x00000001)
					sb.append(" ");

				if((i & 0x0000000f) == 0x0000000f){
					if (writeChar) {
						sb.append(" ");
						sb.append(new String(buf));  
					}
					sb.append("\n");
					index = 0;
				} else {
					index++;
				}
			}

			if(index > 0){
				for(int i=15; i>=index; i--){
					buf[i] = 0x20;
					sb.append("  ");
					if((i & 0x00000001) == 0x00000001)
						sb.append(" ");
				}
				sb.append(" ").append(new String(buf));
			}

		} catch (Exception e) {
			// catch everything as this is for debug
		}

		return sb.toString();
	}

	public static String writeHexaLog(byte[] msg) {
		return writeHexaLog(msg, true);
	}

	public static char toCharacter(byte b) {
		int digit = b;
		char temp = 0x00;
		if ((digit < 32) || (digit > 126)) {
			temp = '.';
		} else {
			temp = (char) digit;
		}
		return temp;
	}

	public static byte toMarkByte(byte b) {
		int digit = b;
		byte temp = 0x00;
		if ((digit < 32) || (digit > 126)) {
			temp = '.';
		} else {
			temp = (byte)digit;
		}
		return temp;
	}

	public static String toHexaString(byte b) {
		String hexa = Integer.toHexString(b);
		int offset = hexa.lastIndexOf("f");

		String temp = hexa;
		if (temp.length() > 2) {
			temp = hexa.substring(hexa.length() - 2, hexa.length());
		}

		if (temp.length() == 1) {
			temp = "0" + temp;
		}
		return temp.toUpperCase();
	}

	public static String calucLineNumber(int lineNumber) {
		String hexa = Integer.toHexString(lineNumber).toUpperCase();
		StringBuffer sb = new StringBuffer();

		int hexLength = hexa.length();
		for(int i=0; i<(7-hexLength); i++){
			sb.append("0");
		}

		sb.append(hexa).append(":");

		return sb.toString();
	}

	private static char forDigit(int digit, int radix) {
		if ((digit >= radix) || (digit < 0)) {
			return '\0';
		}
		if ((radix < Character.MIN_RADIX) || (radix > Character.MAX_RADIX)) {
			return '\0';
		}
		if (digit < 10) {
			return (char)('0' + digit);
		}
		return (char)('A' - 10 + digit);
	}

	public static boolean compareByteArray(byte[] org, byte[] dest)
	{
		if( org == null || dest == null || org.length != dest.length)
			return false;

		for( int i = 0; i < org.length; i++ )
		{
			if( org[i] != dest[i] )
				return false;
		}

		return true;
	}

	public static void main(String[] args) {
		byte[] haha = new byte[8];
		System.out.println(ByteUtil.calucLineNumber(0));
		//System.out.println(new String(haha));
	}
} //end class