package com.wo56.common.utils;

import org.apache.commons.net.util.Base64;

import com.framework.core.encrypt.K;
import com.framework.core.exception.BusinessException;

public class EncryPwdUtil {

	/**
	 * 密码加密
	 * 
	 * @param pwd
	 * @return
	 */
	public static String pwdEncryption(String pwdExpress) {
		long first = Math.round((Math.random()*80))+10;
		long last = Math.round(Math.random()*80)+10;
		String pwd=EncryPwdUtil.encode((first+""+pwdExpress+""+last+"{zx}").getBytes());
		return pwd;
	}

	/**
	 * 登录密码解密兼容
	 * 
	 * @param pwd
	 * @return
	 */
	public static String pwdDecryption(String pwdCiphertext) {
		String pwd = new String(EncryPwdUtil.decode(pwdCiphertext));
		if(pwd.indexOf("{zx}")>0){
			pwd=pwd.substring(2, (pwd.length()-6));
		}else{
			pwd=pwdCiphertext;
		}
		return pwd;
	}
	
	/**
	 * 密码解密
	 * @param pwdCiphertext
	 * @return
	 */
	public static String pwdDecryp(String pwdCiphertext)throws Exception {
		String pwd = new String(EncryPwdUtil.decode(pwdCiphertext));
		try {
			pwd=pwd.substring(2, (pwd.length()-6));
		} catch (Exception e) {
			throw new BusinessException("密码格式不正确");
		}
		return pwd;
	}

	/**
	 * base64加密
	 * 
	 * @param bstr
	 * @return String
	 */
	private static String encode(byte[] bstr) {
		return new String(Base64.encodeBase64String(bstr));
	}

	/**
	 * base64解密
	 * 
	 * @param str
	 * @return string
	 */
	private static byte[] decode(String str) {
		byte[] bt = null;
		bt = Base64.decodeBase64(str);
		return bt;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(K.k_s("{RC2}TDsiTJMi"));
	}
}
