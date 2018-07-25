package com.jrj.wx.json.util;

/** 
 *  
 */

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.springframework.stereotype.Component;

import com.jrj.wx.json.bean.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * @author bin.wang
 * @date 2018.6.1
 * 这个类主要是生成公钥和私钥，解密
 */
@Component
@Slf4j
public class RSAUtil {

	private static String RSAKeyStore = "/data/App/wx2018-json/RSAKey.txt";// 上线的地址：/data/App/wx2018-json/RSAKey.txt

	/**
	 * * 生成密钥对 *
	 * 
	 * @return KeyPair *
	 * @throws EncryptException
	 */
	public static KeyPair generateKeyPair() throws Exception {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			final int KEY_SIZE = 1024;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();

			System.out.println("Private" + keyPair.getPrivate());
			System.out.println("Public" + keyPair.getPublic());

			saveKeyPair(keyPair);
			return keyPair;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public static KeyPair getKeyPair() throws Exception {
		FileInputStream fis = new FileInputStream(RSAKeyStore);
		ObjectInputStream oos = new ObjectInputStream(fis);
		KeyPair kp = (KeyPair) oos.readObject();
		oos.close();
		fis.close();
		return kp;
	}

	public static void saveKeyPair(KeyPair kp) throws Exception {

		FileOutputStream fos = new FileOutputStream(RSAKeyStore);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		// 生成密钥
		oos.writeObject(kp);
		oos.close();
		fos.close();
	}

	/**
	 * * 生成公钥 *
	 * 
	 * @param modulus
	 *            *
	 * @param publicExponent
	 *            *
	 * @return RSAPublicKey *
	 * @throws Exception
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 生成私钥 *
	 * 
	 * @param modulus
	 *            *
	 * @param privateExponent
	 *            *
	 * @return RSAPrivateKey *
	 * @throws Exception
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 加密 *
	 * 
	 * @param key
	 *            加密的密钥 *
	 * @param data
	 *            待加密的明文数据 *
	 * @return 加密后的数据 *
	 * @throws Exception
	 */
	public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize)
					cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
				else
					cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
				i++;
			}
			return raw;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * * 解密 *
	 * 
	 * @param key
	 *            解密的密钥 *
	 * @param raw
	 *            已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(cipher.DECRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;

			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 这个方法是获得公钥的模数和指数
	 * @return
	 */
	public static Result getPublicKeyExponentAndModulus(){
		PublicKey publicKey =null;
		try {
			publicKey = RSAUtil.getKeyPair().getPublic();
		} catch (Exception e) {
			
		}
		BigInteger modulus = ((RSAKey) publicKey).getModulus();
		BigInteger exponent = ((RSAPublicKey) publicKey).getPublicExponent();
		System.out.println(publicKey+"-----------------");
		String str = new BigInteger(""+modulus, 10).toString(16);
		String str1 = new BigInteger(""+exponent, 10).toString(16);
		log.debug("--RSAUtil.getPublicKeyExponentAndModulus--exponent:{},modulus:{}",str,str1);
		System.out.println("exponent:"+str1);
		System.out.println("modulus:"+str);
		Result re=new Result();
		re.setExponent(str);
		re.setModulus(str1);
		return re;
	}
	
	/**
	 * 对前台加密的字符串进行解密
	 * code 加密的字符串
	 */
	public static String decryptPrivate(String code){
		log.debug("解码之前--code：{}",code);
		byte[] en_result =HexUtil.hexStringToBytes(code.trim());
		System.out.println("转成byte[]" + new String(en_result));
		byte[] de_result;
		try {
			de_result = decrypt(getKeyPair().getPrivate(),
					en_result);
			System.out.println("还原密文：");
			System.out.println(new String(de_result));
	        StringBuffer sb = new StringBuffer();  
	        sb.append(new String(de_result)); 
	        code = sb.reverse().toString();  
	        System.out.println(code+"++++++");
	        code = URLDecoder.decode(code,"UTF-8");//
			log.debug("解码之后--code：{}",code);
		} catch (Exception e) {
			log.error("解密失败-----");
			e.printStackTrace();
		}
		return code;
	}
	/**
	 * * *
	 * 
	 * @param args
	 *            *
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// RSAUtil rSAUtil=new RSAUtil();
		RSAUtil.generateKeyPair();
		System.out.println(RSAUtil.RSAKeyStore);
		System.out.println("---------------" + RSAUtil.getKeyPair().getPublic());
		System.out.println(
				"---------------" + MyJsonConverter.objectToString(RSAUtil.getKeyPair().getPublic().getClass()));
		PublicKey private1 = RSAUtil.getKeyPair().getPublic();
		System.out.println("--:" + private1.getAlgorithm() + "---：" + private1.getEncoded());
		BigInteger modulus = ((RSAKey) private1).getModulus();
		BigInteger exponent = ((RSAPublicKey) private1).getPublicExponent();
		System.out.println("modulus:"+modulus+"---exponent:"+exponent);
		
		String str = new BigInteger(""+modulus, 10).toString(16);
		System.out.println("modulus:"+str);
	}

}
