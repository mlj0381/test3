package com.wo56.business.ord.intf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.ord.vo.OrdStock;
import com.wo56.business.ord.vo.out.OrderInfoSimpleOut;
import com.wo56.business.org.vo.Organization;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class OrdStockTF {
	private Session getEntityManagers(){
		return SysContexts.getEntityManager();
	}
	/**
	 * 入库
	 * inParam（orderId,orgId,stockInType）
	 * 需要校验是否存在此条入库记录
	 */
	private void putInStorage(Long orderId,Long orgId,Integer stockInType){
		Session session = SysContexts.getEntityManager();
		if(checkPutIn(orderId, orgId)){
			throw new BusinessException("已存在此入库记录，不能重复入库！");
		}
		BaseUser user = SysContexts.getCurrentOperator();
		OrdStock os = new OrdStock();
		os.setOrderId(orderId);
		os.setOrgId(orgId);
		os.setStockInOpId(user.getOperId());
		os.setStockInTime(new Date());
		os.setStockInType(stockInType);
		os.setTenantId(user.getTenantId());
		session.saveOrUpdate(os);
	}

	/**
	 * 出库
	 * inParam（orderId,stockOutTime,orgId）
	 * 需要校验是否出库
	 */
	private void outPutStorage(Long orderId,Long orgId,Integer stockOutType){
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		OrdStock outOs = getOrdStock(orderId, null, orgId, null);
		if(outOs == null){
			throw new BusinessException("还没有入库，不能操作出库！");
		}
		if(outOs.getStockOutType() != null && outOs.getStockOutType() >= 0){
			throw new BusinessException("已经出库，不能重复出库！");
		}
		outOs.setOrderId(orderId);
		outOs.setOrgId(orgId);
		outOs.setStockOutOpId(user.getOperId());
		outOs.setStockOutTime(new Date());
		outOs.setStockOutType(stockOutType);
		session.update(outOs);
	}
	
	/**
	 * 开单入库
	 * @param orderId 
	 * 订单ID
	 * @param orgId
	 * 组织ID
	 */
	public void putInOrgStorage(Long orderId,Long orgId){
		int stockInType = SysStaticDataEnum.STOCK_IN_TYPE.STOCK_IN_ORD;
		putInStorage(orderId, orgId, stockInType);
	}
	/**
	 * 到货入库
	 * @param orderId 
	 * 订单ID
	 * @param orgId
	 * 组织ID
	 */
	public void putInCarStorage(Long orderId,Long orgId){
		int stockInType = SysStaticDataEnum.STOCK_IN_TYPE.STOCK_IN_CAR;
		putInStorage(orderId, orgId, stockInType);
	}
	/**
	 * 发车出库
	 * @param orderId 
	 * 订单ID
	 * @param orgId
	 * 组织ID
	 */
	public void outPutStaStorage(Long orderId,Long orgId){
		int stockOutType = SysStaticDataEnum.STOCK_OUT_TYPE.STOCK_IN_STA;
		outPutStorage(orderId, orgId, stockOutType);
	}
	/**
	 * 中转出库
	 * @param orderId 
	 * 订单ID
	 * @param orgId
	 * 组织ID
	 */
	public void outPutTraStorage(Long orderId,Long orgId){
		int stockOutType = SysStaticDataEnum.STOCK_OUT_TYPE.STOCK_IN_TRA;
		outPutStorage(orderId, orgId, stockOutType);
	}
	/**
	 * 送货上门出库
	 * @param orderId 
	 * 订单ID
	 * @param orgId
	 * 组织ID
	 */
	public void outPutSavStorage(Long orderId,Long orgId){
		int stockOutType = SysStaticDataEnum.STOCK_OUT_TYPE.STOCK_IN_SAV;
		outPutStorage(orderId, orgId, stockOutType);
	}
	
	/**
	 * 是否存在入库记录
	 * @return true 存在入库记录
	 * @return false 不存在入库记录
	 */
	public boolean checkPutIn(Long orderId,Long orgId){
		if(getOrdStock(orderId,null,orgId,null) == null){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 查询数据
	 * @param orderId,stockInType,orgId,stockOutTime
	 */
	public OrdStock getOrdStock(Long orderId,Integer stockInType,Long orgId,Integer stockOutTime){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(OrdStock.class);
		ca.add(Restrictions.eq("orderId", orderId));
		ca.add(Restrictions.eq("orgId", orgId));
		if(stockInType != null){
			ca.add(Restrictions.eq("stockInType", stockInType));
		}
		if(stockOutTime != null){
			ca.add(Restrictions.eq("stockOutTime", stockOutTime));
		}
		List<OrdStock> osList = (List<OrdStock>)ca.list();
		if(osList != null && osList.size() == 1){
			return osList.get(0);
		}
		return null;
	}
	/**
	 * 取消开单入库
	 * @param orderId 
	 * 订单ID
	 * @param orgId
	 * 组织ID
	 */
	public void deletePutInStorage(Long orderId,Long orgId){
		OrdStock os = getOrdStock(orderId, null, orgId, null);
		if(os == null){
			throw new BusinessException("不存在开单数据！");
		}
		if(os.getStockInType() == SysStaticDataEnum.STOCK_IN_TYPE.STOCK_IN_CAR){
			throw new BusinessException("到车入库不能取消！");
		}
		if(os.getStockOutType()!=null && os.getStockOutType() >= 0){
			throw new BusinessException("已经出库了，不能取消开单！");
		}
		getEntityManagers().delete(os);
	}
	/**
	 * 取消出库操作
	 * @param orderId 
	 * 订单ID
	 * @param orgId
	 * 组织ID
	 */
	public void cancelOutPutStorage(Long orderId,Long orgId){
		OrdStock os = getOrdStock(orderId, null, orgId, null);
		if(os == null){
			throw new BusinessException("不存在开单数据！");
		}
		if(os.getStockOutType()!=null && os.getStockOutType() >= 0){
			os.setStockOutOpId(null);
			os.setStockOutTime(null);
			os.setStockOutType(null);
			getEntityManagers().update(os);
		}
//		else{
//			throw new BusinessException("不存在出库数据！");
//		}
		
	}
	/**
	 * 中转后运单的状态变成：待签收，并且运单的中转的状态变成已中转
	 * 需要清空出库数据：ord_stock 出库时间，出库网点
	 * @param orderId 
	 * 订单ID
	 * @param orgId
	 * 组织ID
	 */
	public void clearOutPutStorage(Long orderId,Long orgId){
		OrdStock os = getOrdStock(orderId, null, orgId, null);
		if(os == null){
			throw new BusinessException("不存在入库数据！");
		}
		if(os.getStockOutType()!=null && os.getStockOutType() >= 0){
			os.setStockOutTime(null);
			os.setStockOutOpId(null);
		}else{
			throw new BusinessException("不存在出库数据！");
		}
		getEntityManagers().update(os);
	}
	/**
	 * 帐户：需要取消之前记录的运费，装卸费车辆的状态变成待发车需要
	 * 把原来的出库数据的时间，类型修改为空，ord_stock
	 * @param orderId 
	 * 订单ID
	 * @param orgId
	 * 组织ID
	 */
	public void clearOutPutTimeStorage(Long orderId,Long orgId){
		OrdStock os = getOrdStock(orderId, null, orgId, null);
		if(os == null){
			throw new BusinessException("不存在入库数据！");
		}
		if(os.getStockOutType()!=null && os.getStockOutType() >= 0){
			os.setStockOutTime(null);
		}else{
			throw new BusinessException("不存在出库数据！");
		}
		getEntityManagers().update(os);
	}
	
	
	/**
	 * 发货/到货库存查询
	 * 接口编号:120244
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> doQueryDeliveyOrArriveOrd(Map<String,Object> inParam)throws Exception{
		String beginDateStr = DataFormat.getStringKey(inParam, "beginDate");//发货开始时间
		String endDateStr = DataFormat.getStringKey(inParam, "endDate");//发货结束时间
		String arrBeginStr = DataFormat.getStringKey(inParam, "arrBeginDate");
		String arrEndStr= DataFormat.getStringKey(inParam, "arrEndDate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String typeQuery = DataFormat.getStringKey(inParam, "typeQuery");
		long cur = new Date().getTime();
		//获取用户信息
		BaseUser baseUser = SysContexts.getCurrentOperator();
		Organization organization = OraganizationCacheDataUtil.getOrganization(baseUser.getOrgId());
		boolean queryAll=false;
		//顶级网点，查询全部
		if(organization.getParentOrgId()==-1L){
			queryAll=true;
		}
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer("SELECT o2.TRACKING_NUM,o1.STOCK_IN_TIME,o1.ORG_ID,o2.CONSIGNOR_LINKMAN_NAME, ");
		sb.append(" o2.CONSIGNOR_TELEPHONE,o2.DEST_PROVINCE,o2.DEST_CITY,o2.DEST_COUNTY,o2.CONSIGNEE_LINKMAN_NAME, ");
		sb.append(" o2.CONSIGNEE_BILL,o2.PRODUCTS,o2.WEIGHT,o2.VOLUME,o2.COUNT,o2.DELIVERY_TYPE,o3.CASH_MONEY, ");
		sb.append(" o3.CASH_MONEY2,o3.COLLECTING_MONEY,o3.FREIGHT,o3.PAYMENT_TYPE,o3.PAYMENT_TYPE2,o2.ORDER_ID FROM ord_stock o1,ord_order_info o2,ord_fee o3 ");
        sb.append(" WHERE o1.ORDER_ID = o2.ORDER_ID AND o1.ORDER_ID = o3.ORDER_ID  and o1.STOCK_OUT_TYPE is null AND o2.order_state != 10  AND o1.STOCK_IN_TYPE = :typeQuery");
		if(queryAll){
			sb.append(" and o2.tenant_id = :tenantId ");
		}else{
			sb.append(" and o1.org_id = :orgId ");
		}
		if(StringUtils.isNotEmpty(beginDateStr)){
			sb.append(" AND o1.STOCK_IN_TIME >=:beginDate");
		}
		if(StringUtils.isNotEmpty(endDateStr)){
			sb.append(" AND o1.STOCK_IN_TIME <=:endDate ");
		}
		
		sb.append(" GROUP BY o2.ORDER_ID  ORDER BY o2.create_date asc ");
		Query query = session.createSQLQuery(sb.toString());
		if(queryAll){
			query.setParameter("tenantId", baseUser.getTenantId());
		}else{
			query.setParameter("orgId", baseUser.getOrgId());
		}
		if(StringUtils.isNotEmpty(beginDateStr)){
			query.setParameter("beginDate", beginDateStr+" 00:00:00");
		}
		if(StringUtils.isNotEmpty(endDateStr)){
			query.setParameter("endDate", endDateStr+" 23:59:59");
		}
		query.setParameter("typeQuery", typeQuery);
        IPage  page = PageUtil.gridPage(query);
    	List<Object[]> list = (List<Object[]>)page.getThisPageElements();
		List<OrderInfoSimpleOut> rtnList = new ArrayList<OrderInfoSimpleOut>();
		
		/*TRACKING_NUM -- 运单号,STOCK_IN_TIME -- 入库时间,ORG_ID -- 入库网点
		stockDuration-- 在库时长,CONSIGNOR_LINKMAN_NAME -- 发货人,CONSIGNOR_BILL -- 发货人电话
		DEST_PROVINCE -- 收货人省,DEST_CITY -- 收货人市,DEST_COUNTY -- 收货人区
		CONSIGNEE_LINKMAN_NAME -- 收货人,CONSIGNEE_BILL -- 联系电话,PRODUCTS -- 货品
		WEIGHT -- 重量,VOLUME -- 体积,COUNT -- 件数,DELIVERY_TYPE -- 交接方式
		CASH_MONEY -- 付款方式1,CASH_MONEY2 -- 付款方式2,COLLECTING_MONEY -- 贷款*/
		
		if(list!= null && list.size()>0){
			for(Object[] obj : list){
				OrderInfoSimpleOut out = new OrderInfoSimpleOut();
				//0.TRACKING_NUM -- 运单号
				out.setTrackingNum(String.valueOf(obj[0]));
				//1.STOCK_IN_TIME -- 入库时间
				String sStockInTime = String.valueOf(obj[1]);
				out.setStockInTime(sStockInTime.substring(0, sStockInTime.indexOf(".")));
				//2.ORG_ID -- 入库网点
				long orgId = Long.parseLong(String.valueOf(obj[2]));
				out.setOrgId(orgId);
				Organization org = (Organization) session.get(Organization.class, orgId);
				out.setOrgIdName(org.getOrgName());
				//stockDuration-- 在库时长
				long stockInTime = sdf.parse(sStockInTime).getTime();
				long stockDuration = cur - stockInTime;
				String sStockDuration = CommonUtil.formatTime(stockDuration);
				out.setStockDuration(sStockDuration);
				//3.CONSIGNOR_LINKMAN_NAME -- 发货人
				out.setConsignorLinkmanName(String.valueOf(obj[3]));
				//4.CONSIGNOR_TELEPHONE -- 发货人电话
				out.setConsignorBill(String.valueOf(obj[4]));
				//5.DEST_PROVINCE -- 收货人省
				out.setDestProvinceName(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(obj[5])).getName());
				//6.DEST_CITY -- 收货人市
				out.setDestCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(obj[6])).getName());
				//7.DEST_COUNTY -- 收货人区
				out.setDestCountyName(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(obj[7])).getName());
				//8.CONSIGNEE_LINKMAN_NAME -- 收货人
				out.setConsigneeLinkmanName(String.valueOf(obj[8]));
				//9.CONSIGNEE_BILL -- 联系电话
				out.setConsigneeBill(String.valueOf(obj[9]));
				//10.PRODUCTS -- 货品
				out.setProducts(String.valueOf(obj[10]));
				//11.WEIGHT -- 重量
				out.setWeight(Double.parseDouble(String.valueOf(obj[11])));
				//12.VOLUME -- 体积
				out.setVolume(Double.parseDouble(String.valueOf(obj[12])));
				//13.COUNT -- 件数
				out.setCount(Integer.parseInt(String.valueOf(obj[13])));
				//14.DELIVERY_TYPE -- 交接方式
				int deliveryType = Integer.parseInt(String.valueOf(obj[14]));
				out.setDeliveryType(deliveryType);
				out.setDeliveryTypeName(SysStaticDataUtil.getSysStaticDataCodeName("DELIVERY_TYPE", String.valueOf(deliveryType)));
				//15.CASH_MONEY -- 付款方式1金额
				out.setCashMoney(Long.parseLong(String.valueOf(obj[15])));
				//16.CASH_MONEY2 -- 付款方式2金额
				if(obj[16] != null){
					out.setCashMoney2(Long.parseLong(String.valueOf(obj[16])));
				}
				//17.COLLECTING_MONEY -- 贷款
				out.setCollectingMoney(Long.parseLong(String.valueOf(obj[17])));
				//18.FREIGHT -- 运费
				out.setFreight(Long.parseLong(String.valueOf(obj[18])));
				//19.PAYMENT_TYPE -- 付款方式1
				out.setPaymentTypeName(SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE", String.valueOf(obj[19])));
				//20.PAYMENT_TYPE2 --付款方式2
				out.setPaymentType2Name(SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE", String.valueOf(obj[20])));
				out.setOrderId(Long.parseLong(String.valueOf(obj[21])));
				rtnList.add(out);
			}
		}
		page.setThisPageElements(rtnList);
    	Pagination<Object[]> pages = new Pagination<Object[]>(page);
    	Map<String,Object> rtnMap = new HashMap<String,Object>();
    	rtnMap=JsonHelper.parseJSON2Map(JsonHelper.json(pages));
    	return rtnMap;
	}
	/**
	 * 检验是否出库
	 * @param true 已经出库  false未出库
	 */
	@SuppressWarnings("unchecked")
	public boolean checkIsOut(Long orgId,Long orderId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(OrdStock.class);
		ca.add(Restrictions.eq("orgId",orgId));
		ca.add(Restrictions.eq("orderId", orderId));
		List<OrdStock> list = ca.list();
		if(list!=null&&list.size()>0){
			for(OrdStock temp : list){
				if(temp.getStockOutType() != null && temp.getStockOutType()>=0 ){
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
	/**
	 * 签收，检验是否出库，为出库就操作出库
	 */
	public void signOutStock(Long orderId,Long orgId){
		if(!checkIsOut(orgId,orderId)){
			outPutStorage(orderId,orgId,SysStaticDataEnum.STOCK_OUT_TYPE.STOCK_IN_SIGN);
		}
	}
	
	/**
	 * 取消到货库存
	 * @param childOrderId
	 * @param orgId
	 */
	public void cancelArrivalStock(long childOrderId,long orgId){
		Session session = SysContexts.getEntityManager();
		Criteria ca  = session.createCriteria(OrdStock.class);
		ca.add(Restrictions.eq("orgId", orgId));
		ca.add(Restrictions.eq("orderId", childOrderId));
		ca.add(Restrictions.eq("stockInType", SysStaticDataEnum.STOCK_IN_TYPE.STOCK_IN_CAR));
		List<OrdStock> ordStocks = ca.list();
		if (ordStocks != null && ordStocks.size() > 0) {
			session.delete(ordStocks.get(0));
		}
	}
}
