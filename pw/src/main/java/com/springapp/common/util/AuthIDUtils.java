package com.springapp.common.util;

import java.security.InvalidKeyException;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.apache.commons.lang.StringUtils;

import com.springapp.mvc.vo.SessionInfo;

public class AuthIDUtils {
	
	public static String decrypt(String data){
		try {
			return new String(EncrypDES.getInstance().Decryptor(hexStr2Bytes(data)));
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String encrypt(SessionInfo sessionInfo){
		return byte2HexStr(EncrypDES.getInstance().Encrytor((String.valueOf(sessionInfo.getUserId())+"&"+DateTimeUtil.getCurrentDate_String())));
	}
	
	/** 
	 * bytes转换成十六进制字符串 
	 */
	public static String byte2HexStr(byte[] bArr) {  
	    StringBuffer hs = new StringBuffer();
	    for(byte b:bArr){
	    	hs.append(StringUtils.leftPad(Integer.toHexString(b & 0XFF), 2, '0'));
	    }
	    return hs.toString().toUpperCase();
	}

	private static byte uniteBytes(String src0, String src1) {  
	    byte b0 = Byte.decode("0x" + src0).byteValue();  
	    b0 = (byte) (b0 << 4);  
	    byte b1 = Byte.decode("0x" + src1).byteValue();  
	    byte ret = (byte) (b0 | b1);  
	    return ret;  
	}
	
	/** 
	 * bytes转换成十六进制字符串 
	 */  
	public static byte[] hexStr2Bytes(String src) {
	    int m = 0, n = 0;  
	    int l = src.length() / 2;
	    byte[] ret = new byte[l];  
	    for (int i = 0; i < l; i++) {  
	        m = i * 2 + 1;  
	        n = m + 1;
	        try{
	        	ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
	        }catch(NumberFormatException e){
	        	System.out.println("=============="+src);
	        }
	    }
	    return ret;
	}
	
	public static Optional<String> getUserId(String data){
		data = decrypt(data);
		if(data==null)return Optional.empty();
		return Optional.of(data.split("&")[0]);
	}
}
