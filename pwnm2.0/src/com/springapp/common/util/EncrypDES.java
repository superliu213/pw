package com.springapp.common.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class EncrypDES {
	private String secretKeyBase64 = "VzHWdcJAkg4=";
	// KeyGenerator 提供对称密钥生成器的功能，支持各种算法
	private KeyGenerator keygen;
	// SecretKey 负责保存对称密钥
	private SecretKey deskey;
	// Cipher负责完成加密或解密工作
	private Cipher c;
	// 该字节数组负责保存加密的结果
	private byte[] cipherByte;

	public EncrypDES() throws NoSuchAlgorithmException, NoSuchPaddingException {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());

		// 实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
		keygen = KeyGenerator.getInstance("DES");
		// 得到密钥
		deskey = new SecretKeySpec(Base64.decodeBase64(secretKeyBase64.getBytes()), "DES");
		// 生成密钥
		// deskey = keygen.generateKey();
		// System.out.println(Base64.encodeBase64String(deskey.getEncoded()));
		// 生成Cipher对象,指定其支持的DES算法
		c = Cipher.getInstance("DES");
	}

	/**
	 * 对字符串加密
	 * 
	 * @param str
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] Encrytor(String str) {
		// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
		try {
			c.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] src = str.getBytes();
			// 加密，结果保存进cipherByte
			return c.doFinal(src);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对字符串解密
	 * 
	 * @param buff
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] Decryptor(byte[] buff) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
		c.init(Cipher.DECRYPT_MODE, deskey);
		return c.doFinal(buff);
	}

	/**
	 * @param args
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidKeyException
	 */
	public static void main(String[] args) throws Exception {
		EncrypDES de1 = new EncrypDES();
		String msg = "124&2015-11-27 15:55:40";
		byte[] encontent = de1.Encrytor(msg);
		byte[] decontent = de1.Decryptor(encontent);
		System.out.println("明文是:" + msg);
		System.out.println("加密后:" + Base64.encodeBase64String(encontent));

		System.out.println(new String(de1.Decryptor(Base64.decodeBase64("Qv38Z3CV1Rs="))));
		System.out.println("解密后:" + new String(decontent));
	}

	private static EncrypDES instance = null;
	private static byte[] object = new byte[0];

	public static EncrypDES getInstance() {
		if (instance == null)
			synchronized (object) {
				if (instance == null)
					try {
						instance = new EncrypDES();
					} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
						e.printStackTrace();
					}
			}
		return instance;
	}
}
