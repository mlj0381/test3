package com.wo56.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;

import com.framework.components.redis.RemoteCacheUtil;
import com.framework.core.inter.impl.BaseHttpServlet;
import com.wo56.common.consts.EnumConsts;

/**
 * 生成验证码
 * 
 * @author GenValidateCode
 */
public class GenValidateCode extends BaseHttpServlet {

	private static final long serialVersionUID = 633593813872361588L;

	/**APP注册*/
	public static final String REGINSTER="1";
	/**支付*/
	public static final String PAY="2";
	/**修改密码*/
	public static final String UPDATEPWD="3";
	/**忘记密码*/
	public static final String FORGETPW="4";
	/**微信注册*/
	public static final String WECHAT="5";
	
	
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int width = 150;// 验证码图片宽度
		int height = 40;// 验证码图片高度

		String validCode = GenValidateCode.getRandomNumber(4);
		HttpSession session = request.getSession();
		session.setAttribute(EnumConsts.Common.VALID_CODE, validCode);
		BufferedImage buffimg = new BufferedImage(90, 32,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = buffimg.createGraphics();
		Random rd = new Random();
		int cr, cg, cb;
		cr = rd.nextInt(255);
		cg = rd.nextInt(255);
		cb = rd.nextInt(255);
		Color mycolor = new Color(cr, cg, cb);
		// 干扰线
		// g.setColor(mycolor);
		for (int i = 0; i < 10; i++) {
			int x1 = rd.nextInt(60);
			int x2 = rd.nextInt(60);
			int y1 = rd.nextInt(20);
			int y2 = rd.nextInt(20);
			// g.drawLine(x1, y1, x2, y2);
		}
		// 显示随机码
		Font myfont = new Font("times new roman", Font.ITALIC, 25);
		g.setFont(myfont);
		g.setColor(new Color(225, 232, 215));// 背景颜色要偏淡
		g.fillRect(0, 0, width, height);// 画背景
		g.setColor(new Color(60, 186, 37));// 边框颜色
		g.drawRect(0, 0, width - 1, height - 1);// 画边框

		g.drawString(validCode, 10, 20);
		// 将图像输出到servlet输出流中。
		ServletOutputStream sos = response.getOutputStream();
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ImageIO.write(buffimg, "jpeg", sos);
		sos.close();
		g.dispose();
	}

	public static String getRandomNumber(int length) {
		return RandomStringUtils.randomNumeric(length);
	}

	public static boolean isCheckPhone(String billId) throws Exception {
		Pattern pat = Pattern
				.compile("^(13[0-9]|15[012356789]|18[0-9]|14[57]|170)[0-9]{8}$");
		Matcher mat = pat.matcher(billId);
		return mat.matches();
	}

	/**
	 * 生成验证码
	 * 
	 * @param billId
	 * @param busiType
	 *            1、支付验证码，2、注册、重置验证码
	 * @throws Exception
	 */
	public static String setPhoneCode(String billId,String busiType)
			throws Exception {
		String randomCode = SysMagUtil.getRandomNumber(6);
		RemoteCacheUtil.setex(billId+"_"+busiType, 120, randomCode);
		return randomCode;
	}
	/**
	 * 检查验证码
	 * 
	 * @param billId
	 * @param code
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public static boolean checkCode(String billId,String busiType, String code) throws Exception {
		String randomCode = "";
		boolean isOk = false;
		String key=billId+"_"+busiType;
		randomCode = RemoteCacheUtil.get(key);

		if (code != null && code.equals(randomCode)) {
			isOk = true;
			RemoteCacheUtil.del(key);
		}
		return isOk;
	}
}
