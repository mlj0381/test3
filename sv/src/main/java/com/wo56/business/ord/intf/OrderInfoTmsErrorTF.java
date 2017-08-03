package com.wo56.business.ord.intf;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.wo56.business.ord.vo.OrderInfoTmsError;

public class OrderInfoTmsErrorTF {
	public void doSaveOrderInfoTmsError(OrderInfoTmsError orderInfoTmsError){
		Session session = SysContexts.getEntityManager();
		Criteria ca = session.createCriteria(OrderInfoTmsError.class);
		ca.add(Restrictions.eq("orderNumber", orderInfoTmsError.getOrderNumber()));
		ca.add(Restrictions.eq("orderState", orderInfoTmsError.getOrderState()));
		List<OrderInfoTmsError> orderInfoTmsErrors =  ca.list();
		if (orderInfoTmsErrors == null || orderInfoTmsErrors.size() <= 0) {
			orderInfoTmsError.setSendFlag(0);
			session.save(orderInfoTmsError);
		}
	}
}
