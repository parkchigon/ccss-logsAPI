package com.lgu.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import sun.misc.BASE64Decoder;

public class SecureManager {

	private static final Logger logger = LoggerFactory.getLogger(SecureManager.class);
    /**
     * UserKey Byte Array
     */
    private static final byte[] USER_KEY = {
        78, 80, 101, 102, 77, 115, 51, 117, 70,
        49, 65, 107, 125, 43, 118, 67, 74, 120,
        90, 87, 48, 65, 38, 57
    };

    /**
     * SecretKeySpec 인스턴스
     */
    private static SecretKeySpec KEYSPEC = new SecretKeySpec(USER_KEY, "DESede");


    /**
     * 파라미터 인코딩 메서드
     *
     * @param plainStr 인코딩할 파라미터 String
     * @return 인코딩 결과값
     * @throws Exception 인코딩 Exception
     */
    public static String encodeParam( String plainStr ) throws Exception {
        String ret = null;
        try {
        	Base64 bs = new Base64();
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(1, KEYSPEC);
            byte plainText[] = plainStr.getBytes("UTF-8");
            byte cipherText[] = cipher.doFinal(plainText);
            ret = new String(bs.encode(cipherText));
        }
        catch (Exception e) {
        	logger.error("Exception", e);
            throw e;
        }
        return ret;
    }

    public static String encodeParam( String plainStr, String encodingChar ) throws Exception {
        String ret = null;
        try {
        	Base64 bs = new Base64();
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(1, KEYSPEC);
            byte plainText[] = plainStr.getBytes(encodingChar);
            byte cipherText[] = cipher.doFinal(plainText);
            ret = new String(bs.encode(cipherText));
        }
        catch (Exception e) {
        	logger.error("Exception", e);
            throw e;
        }
        return ret;
    }

    /**
     * 파라미터 디코딩 메서드
     *
     * @param encodedStr 디코딩할 파라미터 String
     * @return 디코딩 결과값
     * @throws Exception 디코딩 Exception
     */
    public static String decodeParam( String encodedStr ) throws Exception {
        String ret = null;
        try {
        	Base64 bs = new Base64();
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(2, KEYSPEC);          
            byte base64bytes[] =  bs.decodeBase64(encodedStr.getBytes());
            byte decryptedText[] = cipher.doFinal(base64bytes);
            ret = new String(decryptedText, "UTF-8");
            ret = ret.trim();
        }
        catch (Exception e) {
        	logger.error("Exception", e);
            throw e;
        }
        return ret;
    }

    public static String decodeParam( String encodedStr, String encodingChar ) throws Exception {
        String ret = null;
        try {
        	Base64 bs = new Base64();
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(2, KEYSPEC);
            byte base64bytes[] =  bs.decodeBase64(encodedStr.getBytes());
            byte decryptedText[] = cipher.doFinal(base64bytes);
            ret = new String(decryptedText, encodingChar);
            ret = ret.trim();
        }
        catch (Exception e) {
        	logger.error("Exception", e);
            throw e;
        }
        return ret;
    }
    
    public static String encryptSHA256(String encodedStr){
		String rtnSHA = "";
		
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(encodedStr.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			
			for(int i = 0 ; i < byteData.length ; i++){
			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			rtnSHA = sb.toString();
			    
		}catch(NoSuchAlgorithmException e){
			logger.error("Exception", e);
			rtnSHA = null; 
		}
		return rtnSHA;
    }

    
	public static String getSecureKey(int length) {
		// TODO Auto-generated method stub

		Random random = new Random();
		// String str = "";
		StringBuffer str = new StringBuffer();

		for (int i = 0; i < length; i++) {
			if (random.nextBoolean()) {
				// str = str +
				// String.valueOf((char)((int)(random.nextInt(26))+97)).toUpperCase();
				str.append(String.valueOf(
						(char) ((int) (random.nextInt(26)) + 97)).toUpperCase());
			} else {
				// str = str + random.nextInt(length);
				str.append((random.nextInt(10)));
			}
		}
		
		return str.toString();
		
	}

	
	
    public static void main(String[] args) throws Exception {
    	System.out.println("#############################");
    	System.out.println(encryptSHA256("abcdefgh"));
    	System.out.println(encryptSHA256("field_lg110"));
    	System.out.println("##############################");
        StringBuilder encodeStr = new StringBuilder();
        StringBuilder decodeStr = new StringBuilder();

        long startTime = 0L;
        long endTime = 0L;

        //System.out.println( encodeStr.append( TmallSecureManager.encodeParam("한글테스트") ) );
        //System.out.println( decodeStr.append( TmallSecureManager.decodeParam(encodeStr.toString()) ) );

//        startTime = System.currentTimeMillis();
//        for( int idx=0; idx<100; idx++ ) {
//            encodeStr.append( CctvSecureManager.encodeParam("kwon") );
//            decodeStr.append( CctvSecureManager.decodeParam(encodeStr.toString()) );
//        }
//        endTime = System.currentTimeMillis();
//
//        endTime -= startTime;

        String testStr = "01040437310";	
        
        String enStr = encodeParam(testStr);
        System.out.println("01040437310:" + enStr);
        
        String testStr1 = "01093069480";
        String enStr1 = encodeParam(testStr1);
        System.out.println("01093069480:" + enStr1);
        
        
        String testStr2 = "01058415690";
        String enStr2 = encodeParam(testStr2);
        System.out.println("01058415690:" + enStr2);
        
        String testStr3 = "01091270054";
        String enStr3 = encodeParam(testStr3);
        System.out.println("01091270054:" + enStr3);
        
        String deStr = decodeParam(enStr);
        System.out.println(deStr);
        
        String d = "hB4haqiSi5c2koxsQlCX7w==";
        System.out.println("dddd :"+decodeParam(d));
        
      /*  String decA ="dB5lGIfX/Cj6TikO9CEBgw==";
        String decB ="yf6V+FWM9mpnzurSF8ZHkA==";
        
        System.out.println("decAAAA"+decodeParam(decA));
        System.out.println("decB"+decodeParam(decB));*/
    /*    
        String sha256Str = "kkhtest11";
        String ensha256 = encryptSHA256(sha256Str);
        System.out.println("kkhtest11:"  + ensha256);
        
        String decodeMdn = decodeParam("CcYkAU/wYHxY+9W1GDOpkw==");
        System.out.println("$$$$$$$$$$" +decodeMdn);
        */
        //byte base64bytes[] = (new BASE64Decoder()).decodeBuffer(testStr);
        //System.out.println(" encoding str : "+testStr);
        //System.out.println(" base64 str : "+ new String(base64bytes,"euc-kr"));
        //System.out.println(" decoding str : "+TmallSecureManager.decodeParam(testStr,"euc-kr"));

        //System.out.println( "Total Elapsed Time : " + (endTime) );
        
        /*String dec1 	="U7JY+cd4SmSw7xJD+n4LQw==";
        String dec2 	="tGbycfKTCNI4MWfP2t9lIw==";
        String dec3 	="RqrtNF16TOvApfNiiOwusA=="; 
        String dec4 	="vQaHwcvN+sRzbMV2t4rtpg==";
        String dec5 	="vQaHwcvN+sRzbMV2t4rtpg==";
        String dec6 	="AriG5/r99Sbg4EGKSgQCaQ==";
        String dec7 	="GEWpQwZc8HY7KEoqxSx+4A==";
        String dec8 	="l6OLK0bY3IafDc4TC49cVw==";
        String dec9 	="L9YMzApO7HJ5Pq2gCbbNNg==";
        String dec10	="yf6V+FWM9mpnzurSF8ZHkA==";
        String dec11	="vrjhf7zQogsYFF07fDTM6g==";
        String dec12	="tvjmoreAOujVwQHoGpScaQ==";
        String dec13	="ngAIv2o/AK/BpSqqI2NQWg==";
        String dec14    ="f1GgJnkPDbcaNf5dHXeJgQ==";
        String dec15    ="OY2gOufv5HcZty81GKVNOQ==";
        String dec16    ="0Ol4KTE7laVmsUUBAPgj4Q==";
        String dec17    ="0Ol4KTE7laVmsUUBAPgj4Q==";
        String dec18    ="0Ol4KTE7laVmsUUBAPgj4Q==";
        String dec19    ="K/PdFhKOxRME0UFlrKOXdQ==";
        String dec20    ="hilJN8TTl1+VDTGT6MNMZg==";
        String dec21    ="32VIsQ4ZvW0rHyUNYIvr5A==";
        String dec22    ="M48qCoeXdgEUSNJQCG+AOQ==";
        String dec23    ="Hd53DVhqAIF/vk9nZqrn2w==";
        String dec24	="Z0wYBeaNDXKqH75FAB0/rg==";
        String dec25	="Z0wYBeaNDXKqH75FAB0/rg==";
        String dec26	="DsJc1ciSNu9oQ76l1R9m7A==";
        String dec27	="L/Tt6Ob9vQTbWwAhLDW5JA==";

        System.out.println("dec1:  "+decodeParam(dec1));
        System.out.println("dec2:  "+decodeParam(dec2));
        System.out.println("dec3:  "+decodeParam(dec3));
        System.out.println("dec4:  "+decodeParam(dec4));
        System.out.println("dec5:  "+decodeParam(dec5));
        System.out.println("dec6:  "+decodeParam(dec6));
        System.out.println("dec7:  "+decodeParam(dec7));
        System.out.println("dec8:  "+decodeParam(dec8));
        System.out.println("dec9:  "+decodeParam(dec9));
        System.out.println("dec10: "+decodeParam(dec10));
        System.out.println("dec11: "+decodeParam(dec11));
        System.out.println("dec12: "+decodeParam(dec12));
        System.out.println("dec13: "+decodeParam(dec13));
        System.out.println("dec14: "+decodeParam(dec14));
        System.out.println("dec15: "+decodeParam(dec15));
        System.out.println("dec16: "+decodeParam(dec16));
        System.out.println("dec17: "+decodeParam(dec17));
        System.out.println("dec18: "+decodeParam(dec18));
        System.out.println("dec19: "+decodeParam(dec19));
        System.out.println("dec20: "+decodeParam(dec20));
        System.out.println("dec21: "+decodeParam(dec21));
        System.out.println("dec22: "+decodeParam(dec22));
        System.out.println("dec23: "+decodeParam(dec23));
        System.out.println("dec24: "+decodeParam(dec24));
        System.out.println("dec25: "+decodeParam(dec25));
        System.out.println("dec26: "+decodeParam(dec26));
        System.out.println("dec27: "+decodeParam(dec27));*/


        
    }
    //
    //pXWHup+5EkcrdQhquqBL8Q==
    //yf6V+FWM9mpnzurSF8ZHkA==
    // 주석된 getUserKey() 메서드 삭제
    // encodeParamSiteKey 메서드 삭제
    // decodeParamSiteKey 메서드 삭제

}


