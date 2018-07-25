package com.jrj.wx.json.util;

/**
 * 
 * @author bin.wang 这是一个工具类
 *
 */
public class HexUtil {

	/**
	 * 字节流转成十六进制表示
	 */
	public static String encode(byte[] src) {
		String strHex = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < src.length; n++) {
			strHex = Integer.toHexString(src[n] & 0xFF);
			sb.append(strHex.length() == 1 ? "0" + strHex : strHex);
		}
		return sb.toString().trim();
	}

	/**
	 * 字符串转成字节流
	 */
	public static byte[] decode(String src) {
		int m = 0;
		int n = 0;
		int byteLen = src.length() / 2;
		byte[] ret = new byte[byteLen];
		for (int i = 0; i < byteLen; i++) {
			m = i * 2 + 1;
			n = m + 1;
			int intVal = Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)).intValue();
			ret[i] = Byte.valueOf((byte) intVal).byteValue();
		}
		return ret;
	}

	/**
	 * 16进制 To byte[]<br>
	 * 
	 * @param hexString
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static void main(String[] args) {
		String str1 = "abcedefghijklmnopqrstuvwxyz";

		String hexStr = encode(str1.getBytes());
		System.out.println(hexStr);
		String str2 = new String(decode(hexStr));
		System.out.println(str2);
		System.out.println(str1.equals(str2));
	}
}
