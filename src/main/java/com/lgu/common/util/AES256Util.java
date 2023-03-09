package com.lgu.common.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES256Util {
	
	public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	
	public static String AESEncode(String key, String str) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		byte[] keyData = key.getBytes();
		SecretKey secureKey = new SecretKeySpec(keyData, "AES");
		 
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(ivBytes));
		 
		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		String encodedStr = new String(Base64.encodeBase64(encrypted));		 
		 
		return encodedStr;
	}
	
	public static String AESDecode(String key, String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {	
		byte[] keyData = key.getBytes();
		SecretKey secureKey = new SecretKeySpec(keyData, "AES");
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(ivBytes));
		
		byte[] decrypted = Base64.decodeBase64(str.getBytes());
		
		String decodedStr = new String(c.doFinal(decrypted), "UTF-8");
		return decodedStr;
	}
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
		//neSAbrzy/0T8jB2+egCDRw==
		String encStr ="neSAbrzy/0T8jB2+egCDRw==";
		System.out.println(AESDecode("secure1234567890",encStr));
//		System.out.println(AESEncode("secure12345678901234",AESDecode("secure1234567890",encStr)));
		System.out.println("GPS 인코딩");
		System.out.println(AESEncode("DY0ODA1Mzg1NzQ=1","37.5181679"));
		System.out.println(AESEncode("DY0ODA1Mzg1NzQ=1","126.9110370"));
		System.out.println(AESEncode("DY0MzE2Njk2Nzl=1","37.5181679"));
		System.out.println(AESEncode("DY0MzE2Njk2Nzl=1","126.9110370"));
		System.out.println(AESDecode("DY0MzE2Njk2NzI=1",AESEncode("DY0MzE2Njk2NzI=1","36.1332456")));
		System.out.println(AESDecode("DY0MzE2Njk2NzI=1",AESEncode("DY0MzE2Njk2NzI=1","127.3132456")));
		
		System.out.println(AESDecode("DY0MzE2Njk2NzI=1","LaWphNEo7ocxegaUKghwOg=="));
		System.out.println(AESDecode("DY0MzE2Njk2NzI=1","A4X60/97vI5NgD+6t93Q+A=="));
		
	}
}
