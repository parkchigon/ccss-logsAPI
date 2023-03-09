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

public class AES256LogUtil {
	
	//public static byte[] ivBytes = { 0x73, 0x6D, 0x6F, 0x74, 0x6F, 0x72, 0x31, 0x32, 0x33, 0x34, 0x61, 0x73, 0x65, 0x32, 0x35, 0x36 };
	public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	
	public static String AESEncode(String key, String str) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		
		// 32byte key
		if (key.length() > 32) key = key.substring(0, 32);
		else if (key.length() < 32) key = rpad(key, 32, "0");

		byte[] keyData = key.getBytes();
		SecretKey secureKey = new SecretKeySpec(keyData, "AES");
		 
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(ivBytes));
		 
		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		String encodedStr = new String(Base64.encodeBase64(encrypted));
		 
		return encodedStr;
	}
	
	public static String AESDecode(String key, String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {	

		// 32byte key
		if (key.length() > 32) key = key.substring(0, 32);
		else if (key.length() < 32) key = rpad(key, 32, "0");
		
		byte[] keyData = key.getBytes();
		SecretKey secureKey = new SecretKeySpec(keyData, "AES");
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(ivBytes));
		
		byte[] decrypted = Base64.decodeBase64(str.getBytes());
		
		String decodedStr = new String(c.doFinal(decrypted), "UTF-8");
		return decodedStr;
	}
	
	  /**
     * rpad 함수
     * @param str   대상문자열, len 길이, addStr 대체문자
     * @return      문자열
     */

    public static String rpad(String str, int len, String addStr) {
        String result = str;
        int templen   = len - result.length();

        for (int i = 0; i < templen; i++){
              result = result + addStr;
        }
        
        return result;
    }
}
