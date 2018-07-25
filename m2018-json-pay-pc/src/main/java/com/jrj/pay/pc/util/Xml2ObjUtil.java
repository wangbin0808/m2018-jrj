package com.jrj.pay.pc.util;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * @description xml和object转换类
 * @author bin.wang
 * @date 2018.06.20
 *
 */
public class Xml2ObjUtil {

	/**
	 * 将String类型的xml转换成对象
	 */
	public static Object convertXmlStrToObject(Class<?> clazz, String xmlStr) {
		Object xmlObject = null;
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshal = context.createUnmarshaller();
			StringReader sr = new StringReader(xmlStr);
			xmlObject = unmarshal.unmarshal(sr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlObject;
	}
}
