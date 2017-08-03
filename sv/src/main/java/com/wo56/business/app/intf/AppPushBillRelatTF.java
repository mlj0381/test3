package com.wo56.business.app.intf;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.wo56.business.app.impl.AppPushBillRelatSV;
import com.wo56.business.app.vo.AppPushBillRelat;
import com.wo56.common.utils.CommonUtil;

public class AppPushBillRelatTF {
	private static final Log log = LogFactory.getLog(AppPushBillRelatTF.class);
	/**
	 * 处理手机登录绑定关系
	 * @param    pushChannelId   推送系统channelId
	 * @param    billId          用户手机
	 * @param    pushAppId       推送系统APPID
	 * @param    pushUserId      推送系统user_id
	 * @return   pushRelatId     绑定成功的关系ID
	 */
	public String  dealPushRealet(String pushChannelId,String billId,String pushAppId,String pushUserId) throws Exception {
		Session session = SysContexts.getEntityManager();
		AppPushBillRelatSV appPushBillRelatSV = (AppPushBillRelatSV)SysContexts.getBean("appPushBillRelatSV");
		String pushRelatId = "";
		try {
			if (StringUtils.isNotEmpty(pushChannelId)) {
                pushRelatId = appPushBillRelatSV.pushSms(pushChannelId, billId, pushAppId, pushUserId);
			} else {
				//绑定失败，删除号码相关绑定，保证信息传达
				log.info("未传递绑定关系，删除已存在的绑定，保证信息传达!");
				delRealetByBill(billId);
			}
		} catch (Exception e) {
			log.error("App登陆接口插入推送关系异常:",e);
		}
		return pushRelatId;
 
	}
	/**
	 * 删除手机号码的推送关系
	 * @param billId
	 * @throws Exception
	 */
	public void delRealetByBill(String billId) throws Exception{
		if(StringUtils.isEmpty(billId)){
			return ;
		}
		if(!CommonUtil.isCheckPhone(billId)){
			return ;
		}
		Session session = SysContexts.getEntityManager();
		Criteria appca = session.createCriteria(AppPushBillRelat.class);
		appca.add(Restrictions.eq("billId", billId));
		List<AppPushBillRelat> appList = appca.list();
		for (AppPushBillRelat appPush : appList) {
			session.delete(appPush);
		}
		
	}
}
