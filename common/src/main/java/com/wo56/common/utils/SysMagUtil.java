package com.wo56.common.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

import com.framework.core.SysContexts;
import com.framework.core.inter.vo.OutParamVO;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;

/**
 * 系统管理工具类
 * 
 * @author liuzj
 * 
 */
public class SysMagUtil {

	private SysMagUtil() {
	}

	public static Object getObjById(Class cla, Object id) {
		if (id instanceof Integer) {
			return SysContexts.getEntityManager().get(cla, (Integer) id);
		}
		if (id instanceof Long) {
			return SysContexts.getEntityManager().get(cla, (Long) id);
		}
		return null;
	}

	public static String getRandomCode(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}
	
	public static String getRandomNumber(int length){
		return RandomStringUtils.randomNumeric(length);
	}
	
	public  static String Md5(String plainText) {
		StringBuffer buf = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buf.toString();
	}
	
	public static String toFormatJSON(Map inParam){
		OutParamVO outParamVO = new OutParamVO();
		outParamVO.setMessage(DataFormat.getStringKey(inParam,"message"));
		outParamVO.setStatus(DataFormat.getIntKey(inParam,"status"));
		outParamVO.setContent((Map)inParam.get("content"));
		return JsonHelper.json(outParamVO);
	}
}
