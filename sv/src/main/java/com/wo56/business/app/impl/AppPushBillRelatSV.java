package com.wo56.business.app.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.wo56.business.app.vo.AppPushBillRelat;

public class AppPushBillRelatSV {
	
	/**
	 * 推送的公共代码
	 * @param pushChannelId 推送系统channelId
	 * @param billId 用户手机
	 * @param pushAppId 推送系统APPID
	 * @param pushUserId 推送系统user_id
	 * @return
	 */
	public String pushSms(String pushChannelId,String billId,String pushAppId,String pushUserId){
		Session session = SysContexts.getEntityManager();
		String pushRelatId="";
		Criteria appca = session.createCriteria(AppPushBillRelat.class);
		appca.add(Restrictions.eq("pushChannelId", pushChannelId));
		AppPushBillRelat app = null;
		List<AppPushBillRelat> appList = appca.list();
		if (appList.size() > 0) {
			//删除该设备之前绑定的手机号码
			for (AppPushBillRelat relat : appList) {
				if (!relat.getBillId().equals(billId)) {
					session.delete(relat);
				} else {
					app = relat;
				}
			}
		}
		List<AppPushBillRelat> listApp = session.createCriteria(AppPushBillRelat.class).add(Restrictions.eq("billId", billId)).list();
		if (appList!=null && appList.size() == 1) {
			app = appList.get(0);
		}else if (appList!=null && appList.size() > 1) {
			session.delete(app);
		}
		if (app == null) {
			app = new AppPushBillRelat();
			app.setBillId(billId);
			app.setPushAppId(pushAppId);
			app.setPushChannelId(pushChannelId);
			if(StringUtils.isNotEmpty(pushUserId)){
			app.setPushUserId(pushUserId);
			}
			session.save(app);
		}
		pushRelatId= app.getPushRelatId()+"";
		return pushRelatId;
	}
}
