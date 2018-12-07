package com.s.t.m.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.border.EmptyBorder;


/**
 * 加解密相关的安全组件
 * @author 
 */
public class SecurityKit {
	
	/**
	 * Base64算法
	 */
	public static final class Base64 {
	    static private final int     BASELENGTH           = 128;
	    static private final int     LOOKUPLENGTH         = 64;
	    static private final int     TWENTYFOURBITGROUP   = 24;
	    static private final int     EIGHTBIT             = 8;
	    static private final int     SIXTEENBIT           = 16;
	    static private final int     FOURBYTE             = 4;
	    static private final int     SIGN                 = -128;
	    static private final char    PAD                  = '=';
	    static private final boolean fDebug               = false;
	    static final private byte[]  base64Alphabet       = new byte[BASELENGTH];
	    static final private char[]  lookUpBase64Alphabet = new char[LOOKUPLENGTH];

	    static {
	        for (int i = 0; i < BASELENGTH; ++i) {
	            base64Alphabet[i] = -1;
	        }
	        for (int i = 'Z'; i >= 'A'; i--) {
	            base64Alphabet[i] = (byte) (i - 'A');
	        }
	        for (int i = 'z'; i >= 'a'; i--) {
	            base64Alphabet[i] = (byte) (i - 'a' + 26);
	        }

	        for (int i = '9'; i >= '0'; i--) {
	            base64Alphabet[i] = (byte) (i - '0' + 52);
	        }

	        base64Alphabet['+'] = 62;
	        base64Alphabet['/'] = 63;

	        for (int i = 0; i <= 25; i++) {
	            lookUpBase64Alphabet[i] = (char) ('A' + i);
	        }

	        for (int i = 26, j = 0; i <= 51; i++, j++) {
	            lookUpBase64Alphabet[i] = (char) ('a' + j);
	        }

	        for (int i = 52, j = 0; i <= 61; i++, j++) {
	            lookUpBase64Alphabet[i] = (char) ('0' + j);
	        }
	        lookUpBase64Alphabet[62] = (char) '+';
	        lookUpBase64Alphabet[63] = (char) '/';

	    }

	    private static boolean isWhiteSpace(char octect) {
	        return (octect == 0x20 || octect == 0xd || octect == 0xa || octect == 0x9);
	    }

	    private static boolean isPad(char octect) {
	        return (octect == PAD);
	    }

	    private static boolean isData(char octect) {
	        return (octect < BASELENGTH && base64Alphabet[octect] != -1);
	    }

	    /**
	     * Encodes hex octects into Base64
	     *
	     * @param binaryData Array containing binaryData
	     * @return Encoded Base64 array
	     */
	    public static String encode(byte[] binaryData) {

	        if (binaryData == null) {
	            return null;
	        }

	        int lengthDataBits = binaryData.length * EIGHTBIT;
	        if (lengthDataBits == 0) {
	            return "";
	        }

	        int fewerThan24bits = lengthDataBits % TWENTYFOURBITGROUP;
	        int numberTriplets = lengthDataBits / TWENTYFOURBITGROUP;
	        int numberQuartet = fewerThan24bits != 0 ? numberTriplets + 1 : numberTriplets;
	        char encodedData[] = null;

	        encodedData = new char[numberQuartet * 4];

	        byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;

	        int encodedIndex = 0;
	        int dataIndex = 0;
	        if (fDebug) {
	            System.out.println("number of triplets = " + numberTriplets);
	        }

	        for (int i = 0; i < numberTriplets; i++) {
	            b1 = binaryData[dataIndex++];
	            b2 = binaryData[dataIndex++];
	            b3 = binaryData[dataIndex++];

	            if (fDebug) {
	                System.out.println("b1= " + b1 + ", b2= " + b2 + ", b3= " + b3);
	            }

	            l = (byte) (b2 & 0x0f);
	            k = (byte) (b1 & 0x03);

	            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
	            byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);
	            byte val3 = ((b3 & SIGN) == 0) ? (byte) (b3 >> 6) : (byte) ((b3) >> 6 ^ 0xfc);

	            if (fDebug) {
	                System.out.println("val2 = " + val2);
	                System.out.println("k4   = " + (k << 4));
	                System.out.println("vak  = " + (val2 | (k << 4)));
	            }

	            encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
	            encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | (k << 4)];
	            encodedData[encodedIndex++] = lookUpBase64Alphabet[(l << 2) | val3];
	            encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 0x3f];
	        }

	        // form integral number of 6-bit groups
	        if (fewerThan24bits == EIGHTBIT) {
	            b1 = binaryData[dataIndex];
	            k = (byte) (b1 & 0x03);
	            if (fDebug) {
	                System.out.println("b1=" + b1);
	                System.out.println("b1<<2 = " + (b1 >> 2));
	            }
	            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
	            encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
	            encodedData[encodedIndex++] = lookUpBase64Alphabet[k << 4];
	            encodedData[encodedIndex++] = PAD;
	            encodedData[encodedIndex++] = PAD;
	        } else if (fewerThan24bits == SIXTEENBIT) {
	            b1 = binaryData[dataIndex];
	            b2 = binaryData[dataIndex + 1];
	            l = (byte) (b2 & 0x0f);
	            k = (byte) (b1 & 0x03);

	            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
	            byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);

	            encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
	            encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | (k << 4)];
	            encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2];
	            encodedData[encodedIndex++] = PAD;
	        }

	        return new String(encodedData);
	    }

	    /**
	     * Decodes Base64 data into octects
	     *
	     * @param encoded string containing Base64 data
	     * @return Array containind decoded data.
	     */
	    public static byte[] decode(String encoded) {

	        if (encoded == null) {
	            return null;
	        }

	        char[] base64Data = encoded.toCharArray();
	        // remove white spaces
	        int len = removeWhiteSpace(base64Data);

	        if (len % FOURBYTE != 0) {
	            return null;//should be divisible by four
	        }

	        int numberQuadruple = (len / FOURBYTE);

	        if (numberQuadruple == 0) {
	            return new byte[0];
	        }

	        byte decodedData[] = null;
	        byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;
	        char d1 = 0, d2 = 0, d3 = 0, d4 = 0;

	        int i = 0;
	        int encodedIndex = 0;
	        int dataIndex = 0;
	        decodedData = new byte[(numberQuadruple) * 3];

	        for (; i < numberQuadruple - 1; i++) {

	            if (!isData((d1 = base64Data[dataIndex++])) || !isData((d2 = base64Data[dataIndex++]))
	                || !isData((d3 = base64Data[dataIndex++]))
	                || !isData((d4 = base64Data[dataIndex++]))) {
	                return null;
	            }//if found "no data" just return null

	            b1 = base64Alphabet[d1];
	            b2 = base64Alphabet[d2];
	            b3 = base64Alphabet[d3];
	            b4 = base64Alphabet[d4];

	            decodedData[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
	            decodedData[encodedIndex++] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
	            decodedData[encodedIndex++] = (byte) (b3 << 6 | b4);
	        }

	        if (!isData((d1 = base64Data[dataIndex++])) || !isData((d2 = base64Data[dataIndex++]))) {
	            return null;//if found "no data" just return null
	        }

	        b1 = base64Alphabet[d1];
	        b2 = base64Alphabet[d2];

	        d3 = base64Data[dataIndex++];
	        d4 = base64Data[dataIndex++];
	        if (!isData((d3)) || !isData((d4))) {//Check if they are PAD characters
	            if (isPad(d3) && isPad(d4)) {
	                if ((b2 & 0xf) != 0)//last 4 bits should be zero
	                {
	                    return null;
	                }
	                byte[] tmp = new byte[i * 3 + 1];
	                System.arraycopy(decodedData, 0, tmp, 0, i * 3);
	                tmp[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
	                return tmp;
	            } else if (!isPad(d3) && isPad(d4)) {
	                b3 = base64Alphabet[d3];
	                if ((b3 & 0x3) != 0)//last 2 bits should be zero
	                {
	                    return null;
	                }
	                byte[] tmp = new byte[i * 3 + 2];
	                System.arraycopy(decodedData, 0, tmp, 0, i * 3);
	                tmp[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
	                tmp[encodedIndex] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
	                return tmp;
	            } else {
	                return null;
	            }
	        } else { //No PAD e.g 3cQl
	            b3 = base64Alphabet[d3];
	            b4 = base64Alphabet[d4];
	            decodedData[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
	            decodedData[encodedIndex++] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
	            decodedData[encodedIndex++] = (byte) (b3 << 6 | b4);

	        }

	        return decodedData;
	    }

	    /**
	     * remove WhiteSpace from MIME containing encoded Base64 data.
	     *
	     * @param data  the byte array of base64 data (with WS)
	     * @return      the new length
	     */
	    private static int removeWhiteSpace(char[] data) {
	        if (data == null) {
	            return 0;
	        }

	        // count characters that's not whitespace
	        int newSize = 0;
	        int len = data.length;
	        for (int i = 0; i < len; i++) {
	            if (!isWhiteSpace(data[i])) {
	                data[newSize++] = data[i];
	            }
	        }
	        return newSize;
	    }
	}


	/**
	 * MD5摘要算法
	 */
	public static final class MD5 {
		static char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f' };
		/**
		 * @param password
		 * @return encryptPassword
		 * @author chenming
		 */
		public static String convert32(String str) {
			try {
				byte[] bytes = str.getBytes();
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(bytes);
				bytes = md.digest();
				int j = bytes.length;
				char[] chars = new char[j * 2];
				int k = 0;
				for (int i = 0; i < bytes.length; i++) {
					byte b = bytes[i];
					chars[k++] = hexChars[b >>> 4 & 0xf];
					chars[k++] = hexChars[b & 0xf];
				}
				return new String(chars);
			} catch (Exception e) {
				return null;
			}
		}
		
		public String convert16(String s) {
			String ns = convert32(s).substring(8, 24);
			return ns;
		}
	}
	
	/**
	 * SHA签名算法类
	 */
	public static final class SHA{
		//SHA1签名
		public static String sign(SortedMap<String, String> signParams, String charset) throws Exception {
			StringBuffer sb = new StringBuffer();
			Set es = signParams.entrySet();
			Iterator it = es.iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String k = (String) entry.getKey();
				String v = (String) entry.getValue();
				sb.append(k + "=" + v + "&");
				//要采用URLENCODER的原始值！
			}
			String params = sb.substring(0, sb.lastIndexOf("&"));
			return sign(params, charset);
		}
		
		//SHA1签名
		public static String sign(String str, String charset) {
			if (str == null || str.length() == 0) {
				return null;
			}
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };

			try {
				MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
				mdTemp.update(str.getBytes( charset ));

				byte[] md = mdTemp.digest();
				int j = md.length;
				char buf[] = new char[j * 2];
				int k = 0;
				for (int i = 0; i < j; i++) {
					byte byte0 = md[i];
					buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
					buf[k++] = hexDigits[byte0 & 0xf];
				}
				return new String(buf);
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	
	/**
	 * AES对称加解密算法类
	 */
	public static final class AES{
		/**
		 * AES加密
		 * @param content 需要加密的内容
		 * @param password  加密密码
		 * @return 16进制字符串
		 */
		public static String encrypt(String content, String password) {
			try {
				KeyGenerator kgen = KeyGenerator.getInstance("AES");
				kgen.init(128, new SecureRandom(password.getBytes()));
				SecretKey secretKey = kgen.generateKey();
				byte[] enCodeFormat = secretKey.getEncoded();
				SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
				Cipher cipher = Cipher.getInstance("AES");// 创建密码器
				byte[] byteContent = content.getBytes("utf-8");
				cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
				byte[] result = cipher.doFinal(byteContent);
				return byte2Hex(result);//加密
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
			return null;
		}


		/**
		 * AES加密
		 * @param content 需要加密的内容
		 * @param password  加密密码
		 * @return 16进制字符串
		 */
		public static String encrypt(byte[] content, String password) {
			try {
				KeyGenerator kgen = KeyGenerator.getInstance("AES");
				kgen.init(128, new SecureRandom(password.getBytes()));
				SecretKey secretKey = kgen.generateKey();
				byte[] enCodeFormat = secretKey.getEncoded();
				SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
				Cipher cipher = Cipher.getInstance("AES");// 创建密码器
				cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
				byte[] result = cipher.doFinal(content);//加密
				return byte2Hex(result);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
			return null;
		}


	    /**
	     * 解密 
	     * @param ciphertext  待解密内容 
	     * @param password 解密密钥 
	     * @return 
	     */  
	    public static String decrypt(String ciphertext, String password) {
	    	byte[] content = hex2Byte(ciphertext);
	    	try {
	    		KeyGenerator kgen = KeyGenerator.getInstance("AES");
				kgen.init(128, new SecureRandom(password.getBytes()));//要求秘钥长度为128
	    		SecretKey secretKey = kgen.generateKey();
	    		byte[] enCodeFormat = secretKey.getEncoded();  
	    		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");              
	    		Cipher cipher = Cipher.getInstance("AES");// 创建密码器   
	    		cipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
	    		byte[] result = cipher.doFinal(content);//解密
	    		return new String(result); // 解密
	    	} catch (NoSuchAlgorithmException e) {  
	    		e.printStackTrace();  
	    	} catch (NoSuchPaddingException e) {  
	    		e.printStackTrace();  
	    	} catch (InvalidKeyException e) {  
	    		e.printStackTrace();  
	    	} catch (IllegalBlockSizeException e) {  
	    		e.printStackTrace();  
	    	} catch (BadPaddingException e) {  
	    		e.printStackTrace();  
	    	}  
	    	return null;  
	    }



	}
	
	
	/**
	 * RSA非对称加解密及签名算法类
	 */
	public static final class RSA{
		/** 密钥算法名 */
		public static final String KEY_ALGORITHM = "RSA";  
		/** 签名算法名 */
		public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
		/** RSA最大加密明文大小 */
	    private static final int MAX_ENCRYPT_BLOCK = 117;
	    /** RSA最大解密密文大小 */
	    private static final int MAX_DECRYPT_BLOCK = 128;
		
		/** 公钥类型 */
		public static final Integer PUBLIC_KEY = 1;
		/** 私钥类型 */
		public static final Integer PRIVATE_KEY = 2;
		
		
		/** 
	     * 使用密钥对数据进行加密处理
	     * @param data 待加密的数据
	     * @param key 密钥
	     * @param keyType 密钥类型
	     * @param charset 待加密的数据的字符集
	     * @return 加密后的字符数据
	     * @throws Exception 
	     */  
	    public static String encrypt(String data, String key, Integer keyType, String charset){  
	    	try {
				if( keyType == null || ( keyType != PUBLIC_KEY && keyType != PRIVATE_KEY ) )
					return null;
				// 对key解密  
				byte[] keyBytes = Base64.decode(key);
				KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
				Key realKey = null;
				if( PUBLIC_KEY == keyType ){//公钥
					X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
					realKey = keyFactory.generatePublic(x509KeySpec);
					
				}else if( PRIVATE_KEY == keyType ){//私钥
					PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
					realKey = keyFactory.generatePrivate(pkcs8KeySpec);  
				}
				
				if( realKey == null ) return null;
				
				//对数据加密
				Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
				cipher.init(Cipher.ENCRYPT_MODE, realKey);
				byte[] datas = data.getBytes(charset);
				int inputLen = datas.length;
		        ByteArrayOutputStream out = new ByteArrayOutputStream();
		        int offSet = 0;
		        byte[] cache;
		        int i = 0;
		        // 对数据分段加密
		        while (inputLen - offSet > 0) {
		            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
		                cache = cipher.doFinal(datas, offSet, MAX_ENCRYPT_BLOCK);
		            } else {
		                cache = cipher.doFinal(datas, offSet, inputLen - offSet);
		            }
		            out.write(cache, 0, cache.length);
		            i++;
		            offSet = i * MAX_ENCRYPT_BLOCK;
		        }
		        byte[] encryptedData = out.toByteArray();
		        out.close();
		        return Base64.encode(encryptedData);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	    }
	    
	    /** 
	     * 使用密钥对数据进行加密处理
	     * @param data 待加密的数据
	     * @param key 密钥
	     * @param keyType 密钥类型
	     * @param charset 待加密的数据的字符集
	     * @return 加密后的字符数据
	     * @throws Exception 
	     */  
	    public static String encrypt(byte[] data, String key, Integer keyType){  
	    	try {
	    		if( keyType == null || ( keyType != PUBLIC_KEY && keyType != PRIVATE_KEY ) )
	    			return null;
	    		// 对key解密  
	    		byte[] keyBytes = Base64.decode(key);
	    		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
	    		Key realKey = null;
	    		if( PUBLIC_KEY == keyType ){//公钥
	    			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
	    			realKey = keyFactory.generatePublic(x509KeySpec);
	    			
	    		}else if( PRIVATE_KEY == keyType ){//私钥
	    			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
	    			realKey = keyFactory.generatePrivate(pkcs8KeySpec);  
	    		}
	    		
	    		if( realKey == null ) return null;
	    		
	    		//对数据加密
	    		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
	    		cipher.init(Cipher.ENCRYPT_MODE, realKey);
	    		int inputLen = data.length;
	    		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    		int offSet = 0;
	    		byte[] cache;
	    		int i = 0;
	    		// 对数据分段加密
	    		while (inputLen - offSet > 0) {
	    			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
	    				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
	    			} else {
	    				cache = cipher.doFinal(data, offSet, inputLen - offSet);
	    			}
	    			out.write(cache, 0, cache.length);
	    			i++;
	    			offSet = i * MAX_ENCRYPT_BLOCK;
	    		}
	    		byte[] encryptedData = out.toByteArray();
	    		out.close();
	    		return Base64.encode(encryptedData);
	    	} catch (Exception e) {
	    		throw new RuntimeException(e);
	    	}
	    }
	    
	    /** 
	     * 使用密钥对数据进行解密处理
	     * @param data 待解密的数据
	     * @param key 密钥
	     * @param keyType 密钥类型
	     * @param charset 待解密的数据的字符集
	     * @return 解密后的字符数据
	     * @throws Exception 
	     */  
	    public static String decrypt(String data, String key, Integer keyType, String charset){  
	    	try {
				if( keyType == null || ( keyType != PUBLIC_KEY && keyType != PRIVATE_KEY ) )
					return null;
				// 对密钥解密  
				byte[] keyBytes = Base64.decode(key);

				KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
				Key realKey = null;
				if( PUBLIC_KEY == keyType ){//公钥
					X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
					realKey = keyFactory.generatePublic(x509KeySpec);
					
				}else if( PRIVATE_KEY == keyType ){//私钥
					PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
					realKey = keyFactory.generatePrivate(pkcs8KeySpec);  
				}
				
				if( realKey == null ) return null;
  
				// 对数据解密  
				Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
				cipher.init(Cipher.DECRYPT_MODE, realKey);
				byte[] encryptedData = Base64.decode(data);
				int inputLen = encryptedData.length;
		        ByteArrayOutputStream out = new ByteArrayOutputStream();
		        int offSet = 0;
		        byte[] cache;
		        int i = 0;
		        // 对数据分段解密
		        while (inputLen - offSet > 0) {
		            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
		                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
		            } else {
		                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
		            }
		            out.write(cache, 0, cache.length);
		            i++;
		            offSet = i * MAX_DECRYPT_BLOCK;
		        }
		        byte[] decryptedData = out.toByteArray();
		        out.close();
		        return new String( decryptedData, charset );
			} catch (Exception e) {
				throw new RuntimeException(e);
			}  
	    }
	    
	    /** 
	     * RSA签名:用私钥对信息生成数字签名 
	     * @param content 待签名数据
	     * @param privateKey 私钥 
	     * @param charset 字符集
	     * @return 
	     * @throws Exception 
	     */  
	    public static String sign(String content, String privateKey, String charset){  
	        try {
				// 解密由base64编码的私钥  
				byte[] keyBytes = Base64.decode(privateKey);  
				// 构造PKCS8EncodedKeySpec对象  
				PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
				// KEY_ALGORITHM 指定的加密算法  
				KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
				// 取私钥匙对象  
				PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);  
				
				// 用私钥对信息生成数字签名  
				Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
				signature.initSign(priKey);  
				signature.update( content.getBytes(charset) );  
				return Base64.encode(signature.sign());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}  
	    }
	    
	    /** 
	     * RSA签名校验
	     * @param content 已签名数据
	     * @param sign 当前数据的签名
	     * @param publicKey 公钥 
	     * @param charset 字符集
	     * @return 校验成功返回true 失败返回false
	     * @throws Exception 
	     *  
	     */  
	    public static boolean verify(String content, String sign, String publicKey, String charset){  
	        try {
				// 解密由base64编码的公钥  
				byte[] keyBytes = Base64.decode(publicKey);  
				// 构造X509EncodedKeySpec对象  
				X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
				// KEY_ALGORITHM 指定的加密算法  
				KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
				// 取公钥匙对象  
				PublicKey pubKey = keyFactory.generatePublic(keySpec);  
				
				Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
				signature.initVerify(pubKey);  
				signature.update( content.getBytes( charset ) );  
  
				//验证签名是否正常  
				return signature.verify( Base64.decode(sign));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}  
	    }
	    
	    /**
	     * 生成RSA密钥对
	     * @return publicKey -- 公钥, privateKey -- 私钥
	     */
	    public static Map<String, String> genKeyPair(){
	    	try {
				KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
				keyPairGen.initialize(1024);
				KeyPair keyPair = keyPairGen.generateKeyPair();  
				//公钥  
				RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
				//私钥  
				RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
				String pubKey = Base64.encode(publicKey.getEncoded());
				String priKey = Base64.encode(privateKey.getEncoded());
				Map<String, String> keyPair4Result = new HashMap<String, String>();
				keyPair4Result.put("publicKey", pubKey);
				keyPair4Result.put("privateKey", priKey);
				return keyPair4Result;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
	    	return null;
	    }
	    
		/**
	     * 生成RSA密钥对
	     * @param password 秘钥对原始随机密码
	     * @return publicKey -- 公钥, privateKey -- 私钥
	     */
	    public static Map<String, String> genKeyPair(String password){
	    	try {
				KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
				SecureRandom secureRandom = new SecureRandom(password.getBytes());
				keyPairGen.initialize(1024,secureRandom);
				KeyPair keyPair = keyPairGen.generateKeyPair();  
				//公钥  
				RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
				//私钥  
				RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
				String pubKey = Base64.encode(publicKey.getEncoded());
				String priKey = Base64.encode(privateKey.getEncoded());
				Map<String, String> keyPair4Result = new HashMap<String, String>();
				keyPair4Result.put("publicKey", pubKey);
				keyPair4Result.put("privateKey", priKey);
				return keyPair4Result;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
	    	return null;
	    }
	}
	


	/**
     * 将二进制转换成16进制 
     * @param buf 
     * @return 
     */  
    private static String byte2Hex(byte buf[]) {  
    	StringBuffer sb = new StringBuffer();  
    	for (int i = 0; i < buf.length; i++) {  
    		String hex = Integer.toHexString(buf[i] & 0xFF);  
    		if (hex.length() == 1) {  
    			hex = '0' + hex;  
    		}  
    		sb.append(hex.toUpperCase());  
    	}  
    	return sb.toString();  
    } 
    
    /**
     * 将16进制转换为二进制 
     * @param hexStr 
     * @return 
     */  
    private static byte[] hex2Byte(String hexStr) {  
    	if (hexStr.length() < 1)  
    		return null;  
    	byte[] result = new byte[hexStr.length()/2];  
    	for (int i = 0;i< hexStr.length()/2; i++) {  
    		int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
    		int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
    		result[i] = (byte) (high * 16 + low);  
    	}  
    	return result;  
    }


	public static void main(String[] args) throws Exception {
    	//加密内容，加密密码
    	/*String encryptPassword = SecurityKit.AES.encrypt("root", "vX6LicvxUgZoVPUB");
    	System.out.println( encryptPassword );
		String encode = Base64.encode(encryptPassword.getBytes());
		System.out.println(encode);*/


    	/*//解密
    	String decrypt = SecurityKit.AES.decrypt(encryptPassword,"ryan-is-shuai-ge");
    	System.out.println(decrypt);*/
    	//测试AES128
		/*String ht_aesKey = AES.decrypt("mLGFyVlLJVDpjZ/BGlGT0c5BEQM+3XRfWYsPhOIXUlc=", "vX6LicvxUgZoVPUB");
		System.err.println(ht_aesKey);*/

		// 解密
		/*String DeString = Decrypt("mLGFyVlLJVDpjZ/BGlGT0c5BEQM+3XRfWYsPhOIXUlc=", "vX6LicvxUgZoVPUB");
		System.out.println("解密后的字串是：" + DeString);*/
    	/*Map<String, String> keyPair = RSA.genKeyPair();
        String pubKey = keyPair.get("publicKey");
        String priKey = keyPair.get("privateKey");
        
        System.err.println("公钥: \n\r" + pubKey +"\n\r");
        System.err.println("私钥： \n\r" + priKey );
//        
        System.out.println( "==========================私钥加密--公钥解密===============================");
        String data = "吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB";
        System.out.println( "原始数据: " + data );
        
        String encryptData = RSA.encrypt(data, priKey, RSA.PRIVATE_KEY, "UTF-8");
        System.out.println( "加密数据: " + encryptData );
        
        String sign = RSA.sign(encryptData, priKey, "UTF-8");
        System.out.println( "私钥签名: " + sign);
        
        boolean isPass = RSA.verify(encryptData, sign, pubKey, "UTF-8");
        System.out.println( "签名验证: " + isPass );
        
        String decryptData = RSA.decrypt( encryptData, pubKey, RSA.PUBLIC_KEY, "UTF-8" );
        System.out.println( "解密数据: " + decryptData );
        
        System.out.println( "数据对比: " + data.equals(decryptData) );
        System.out.println( "===============================================================");
        
        System.out.println();
        System.out.println();
        System.out.println();
        
        System.out.println( "==========================公钥加密--私钥解密===============================");
        data = "吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB吴永宏 是个 SB";
        System.out.println( "原始数据: " + data );
        
        encryptData = RSA.encrypt(data, pubKey, RSA.PUBLIC_KEY, "UTF-8");
        System.out.println( "加密数据: " + encryptData );
        
        decryptData = RSA.decrypt( encryptData, priKey, RSA.PRIVATE_KEY, "UTF-8" );
        System.out.println( "解密数据: " + decryptData );
        
        System.out.println( "数据对比: " + data.equals(decryptData) );
        System.out.println( "===============================================================");*/
	}

	/**
	 * 解密
	 * @param String src 解密字符串
	 * @param String key 密钥
	 * @return 解密后的字符串
	 */
	public static String Decrypt(String src, String key) throws Exception {
		try {
			// 判断Key是否正确
			if (key == null) {
				System.out.print("Key为空null");
				return null;
			}

			// 密钥补位
			int plus= 16-key.length();
			byte[] data = key.getBytes("utf-8");
			byte[] raw = new byte[16];
			byte[] plusbyte={ 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
			for(int i=0;i<16;i++)
			{
				if (data.length > i)
					raw[i] = data[i];
				else
					raw[i] = plusbyte[plus];
			}

			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);

			//byte[] encrypted1 = new Base64().decode(src);//base64
			byte[] encrypted1 = toByteArray(src);//十六进制

			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original,"utf-8");
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}
	/**
	 * 将byte[]转为各种进制的字符串
	 * @param bytes byte[]
	 * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
	 * @return 转换后的字符串
	 */
	public static String binary(byte[] bytes, int radix){
		return new BigInteger(1, bytes).toString(radix);	// 这里的1代表正数
	}

	/**
	 * 16进制的字符串表示转成字节数组
	 *
	 * @param hexString 16进制格式的字符串
	 * @return 转换后的字节数组
	 **/
	public static byte[] toByteArray(String hexString) {
		if (hexString.isEmpty())
			throw new IllegalArgumentException("this hexString must not be empty");

		hexString = hexString.toLowerCase();
		final byte[] byteArray = new byte[hexString.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
			byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}


}
