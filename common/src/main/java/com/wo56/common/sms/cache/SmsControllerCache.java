package com.wo56.common.sms.cache;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.cache.AbstractCache;
import com.framework.core.cache.CacheFactory;
import com.wo56.common.sms.vo.SmsController;

public class SmsControllerCache extends AbstractCache{

	@Override
	public void loadDate() throws Exception {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria( SmsController.class);
		List<SmsController> dataList = ca.list();
		for(SmsController smsController:dataList){
			if(CacheFactory.get(this.getClass().getName(), "SYS_SMS_CONTROLLER")==null){
				List<SmsController> valueList = new ArrayList<SmsController>();
				valueList.add(smsController);
				CacheFactory.put(this.getClass().getName(), "SYS_SMS_CONTROLLER", valueList);
			}else{
				List<SmsController> valueList = (List)CacheFactory.get(this.getClass().getName(), "SYS_SMS_CONTROLLER");
				valueList.add(smsController);
			}
		} 
	}

}
