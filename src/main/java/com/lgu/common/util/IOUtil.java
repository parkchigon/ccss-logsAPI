package com.lgu.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtil 
{
	public static byte[] readFully(InputStream istream) throws IOException
	{
		ByteArrayOutputStream bout = null;
		try
		{
			bout = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int num = 0;
			while( (num = istream.read(buf)) != -1)
			{
				bout.write(buf, 0, num);
			}
			byte[] ret = bout.toByteArray();

			return ret;
		}
		catch(IOException ioe)
		{
			if( bout != null)
			throw ioe;
		}
		finally
		{
			if( bout != null )
			{
				bout.close();
			}
		}
		return null;
	}
	
	public static byte[] readFully(InputStream istream, int dataLength) throws IOException
	{
		ByteArrayOutputStream bout = null;
		try
		{
			int bufferSize = 1024;
			if( dataLength <= 1024)
				bufferSize = dataLength;
			
			bout = new ByteArrayOutputStream();
			byte[] buf = new byte[bufferSize];
			
			int num = 0;
			while( (num = istream.read(buf)) != -1)
			{
				bout.write(buf, 0, num);
				if( bout.size() >= dataLength )
					break;
			}
			
			byte[] ret = bout.toByteArray();
			return ret;
		}
		catch(IOException ioe)
		{
			if( bout != null)
			throw ioe;
		}
		finally
		{
			if( bout != null )
			{
				bout.close();
			}
		}
		return null;
	}
	
	public static byte[] readFullyLine(InputStream istream) throws IOException
	{
		BufferedReader br = null;
		ByteArrayOutputStream bout = null;
		
		try
		{
			br = new BufferedReader(new InputStreamReader(istream));
			bout = new ByteArrayOutputStream();
			
			String str = null;
			while( (str = br.readLine()) != null ) {
				if( str.trim().length() == 0){
					break;
				}
				bout.write(str.getBytes());
			}
			
			byte[] ret = bout.toByteArray();
			return ret;
		}
		catch(IOException ioe)
		{
			if( bout != null)
			throw ioe;
		}
		finally
		{
			if( bout != null )
			{
				bout.close();
			}
		}
		return null;
	}
}
