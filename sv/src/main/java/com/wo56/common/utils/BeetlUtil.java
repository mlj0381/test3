package com.wo56.common.utils;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import com.framework.core.util.DateUtil;
import com.wo56.business.statistic.vo.EmailHtmlBean;

public class BeetlUtil {
	public static GroupTemplate getGroupTemplate() throws IOException{
		ResourceLoader loader = new ClasspathResourceLoader();
		Configuration configuration = Configuration.defaultConfiguration();
		return new GroupTemplate(loader, configuration);
	}
	
	public static String getEmailHtmlContent(EmailHtmlBean bean) throws Exception {
		GroupTemplate gt = getGroupTemplate();
		Template template = gt.getTemplate("/com/business/wlpt/statistic/template/html-content.btl");
		template.binding("html", bean);
		template.binding("autoSendTip", "本邮件内容由LogBI系统自动发送，请勿回复！");
		template.binding("htmlCreateDateTip", "此邮件内容产生的时间为：" + DateUtil.formatDate(CommonUtil.getCurrentDate(), "yyyy年MM月dd日HH时mm分"));
		return template.render();
	}
}
