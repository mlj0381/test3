package com.wo56.business.ord.intf;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.ord.vo.out.OrdOpLogYunQiOut;
import com.wo56.common.consts.SysStaticDataEnumYunQi;

public class OrdOpLogYunQiTF {

	/**
	 * 物流信息查询
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List<OrdOpLogYunQiOut> queryLog(Map<String, Object> inParam) throws Exception {
		String wayBill = DataFormat.getStringKey(inParam, "wayBill");
		if (StringUtils.isEmpty(wayBill)) {
			throw new BusinessException("请输入收货人号码或运单号");
		}
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		// 根据手机号或云单号查询订单号
		String sql = "SELECT distinct o.ORDER_NO,o.ORDER_ID,o.ORDER_STATE FROM ord_orders_info AS o WHERE o.CONSIGNEE_BILL=:consigneeBill OR o.TRACKING_NUM=:trackingNum";
		Query query1 = session.createSQLQuery(sql.toString());
		query1.setParameter("consigneeBill", wayBill);
		query1.setParameter("trackingNum", wayBill);
		List<Object> listObj = query1.list();
		if (listObj == null) {
			return null;
		}
		Set<Long> orderArray = new HashSet<Long>();
		Map<Long, OrdOpLogYunQiOut> data = new HashMap<Long, OrdOpLogYunQiOut>();
		List<OrdOpLogYunQiOut> log = new ArrayList<OrdOpLogYunQiOut>();
		for (int i = 0; i < listObj.size(); i++) {
			Object[] objs = (Object[]) listObj.get(i);
			OrdOpLogYunQiOut ordOpLogYunQiOut = new OrdOpLogYunQiOut();
			String orderNo = String.valueOf(objs[0]);
			ordOpLogYunQiOut.setOrderNo(orderNo);
			//map.put("orderNo", orderNo);
			BigInteger orderId = (BigInteger) objs[1];
			int state = Integer.valueOf(String.valueOf(objs[2]));
			String stateName = SysStaticDataUtil.getSysStaticDataCodeName("ORDERS_STATE", String.valueOf(state));
			ordOpLogYunQiOut.setStateName(stateName);
			//map.put("stateName", stateName);
			data.put(orderId.longValue(), ordOpLogYunQiOut);
			orderArray.add(orderId.longValue());
		}
		if(orderArray!=null&&orderArray.size()>0){
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("select p.out_content,p.create_date,p.order_id from "
					+ "ord_op_log as p where p.ORDER_ID in (:orderArray) "
					+ " and p.out_type = 1  and p.op_type in (:typeArray) ORDER BY p.order_id,p.id desc");
			// 物流日志
			Query query = session.createSQLQuery(sqlQuery.toString());
			List<Integer> listType = new ArrayList<>();
			listType.add(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.SAVE_COLLECT_ORDERS);
			listType.add(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.SAVE_DELIVERY_ORDERS);
			listType.add(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.DELIVER_GOODS);
			listType.add(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.CARRY_GOODS);
			listType.add(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.GX_ARRIVE_GOODS);
			listType.add(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.OPEN_ORDERS);
			listType.add(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.RELATION_WAYBILL);
			listType.add(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.DISTRIBUTION_ORDERS);
			listType.add(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.SIGN_ORDERS);
			listType.add(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.SEND_TIP_MONEY);
			query.setParameterList("typeArray", listType);
			query.setParameterList("orderArray", orderArray);
			List<Object[]> list = query.list();
			long orderId=-1;
			if (list != null && list.size() > 0) {
				for (Object[] obj : list) {
					String opContent = "";
					if (obj[0] != null) {
						opContent = String.valueOf(obj[0]);
					}
					String opDate = "";
					if (obj[1] != null) {
						opDate = DateUtil.formatDate((Date) obj[1], "yyyy-MM-dd HH:mm:ss");
					}
					OrdOpLogYunQiOut out =null;
					if(out==null||orderId!=((BigInteger)obj[2]).longValue()){
						out=data.get(((BigInteger)obj[2]).longValue());
					}
					out.setOrderId(((BigInteger)obj[2]).longValue());
					Map rtnMap = new HashMap();
					rtnMap.put("opContent", opContent);
					rtnMap.put("opDate", opDate);
					out.getItems().add(rtnMap);
					if(orderId!=((BigInteger)obj[2]).longValue()){
						log.add(out);
					}
					orderId=((BigInteger)obj[2]).longValue();
				
				}
			}
		} 
		Collections.sort(log, new Comparator<OrdOpLogYunQiOut>() {

			@Override
			public int compare(OrdOpLogYunQiOut o1, OrdOpLogYunQiOut o2) {
				// TODO Auto-generated method stub
				return (int) (Long.parseLong(o2.getOrderNo())-Long.parseLong(o1.getOrderNo()));
			}
		});
		return log;
	}

}
