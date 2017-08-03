package com.wo56.business.match.test;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.framework.components.match.common.MatchConsts;
import com.framework.components.match.common.MatchDataUtils;
import com.framework.components.match.vo.BaseMatchObject;
import com.framework.components.redis.RemoteCacheUtil;
import com.framework.core.SysContexts;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.RandomGenerator;
import com.wo56.business.match.impl.BusiMatchConsts;
import com.wo56.business.match.impl.BusiMatchControl;

public class MatchTest {
	private String[] area = {"广东", "广西", "福建", "珠海", "浙江", "桂林"};
	private String[] prodType = {"床", "办工桌", "皮沙发", "童床", "灯具"};
	private String[] servType = {"维修", "配送安装", "配送", "安装"};
	private String[] servAttr = {"皮革", "板材"};
	private String[] carType = {"小三轮", "小卡车", "大卡车"};
	private String[] carCubel = {"10", "15", "20", "25", "30", "35"};
	private String[] storeCub = {"100", "150", "200", "250", "300", "350"};
	private String[] maxLoad = {"0", "10"};
	private String[] tenantId = {"10000", "20000"};
	private int queueSize = 0;
		
	public void testAddObj() throws Exception {
		Random rd = new Random();
		BaseMatchObject aObj = null;
		BaseMatchObject zObj = null;
		Map<String, String> aAttrs = null;
		Map<String, String> zAttrs = null;
		for (int i=0; i<queueSize; i++) {
			aObj = new BaseMatchObject();
			aObj.setId(rd.nextInt(100000000));
			aObj.setTenantId(tenantId[rd.nextInt(tenantId.length)]);
			aObj.setTel("13500" + rd.nextInt(1000000));
			aObj.setLinkMan(RandomGenerator.random(RandomGenerator.ALPHA, 8));
			aAttrs = new HashMap<String, String>();
			aAttrs.put(BusiMatchConsts.Fields.area, area[rd.nextInt(area.length)]);
			aAttrs.put(BusiMatchConsts.Fields.carCub, carCubel[rd.nextInt(carCubel.length)]);
			aAttrs.put(BusiMatchConsts.Fields.carType, carType[rd.nextInt(carType.length)]);
			aAttrs.put(BusiMatchConsts.Fields.credit, String.valueOf(rd.nextInt(500)));
			aAttrs.put(BusiMatchConsts.Fields.maxLoad, maxLoad[rd.nextInt(maxLoad.length)]);
			aAttrs.put(BusiMatchConsts.Fields.servExtAttr, servType[rd.nextInt(servType.length)]+"|"+servAttr[rd.nextInt(servAttr.length)]);
			aAttrs.put(BusiMatchConsts.Fields.servType, prodType[rd.nextInt(prodType.length)]+"|"+servType[rd.nextInt(servType.length)]);
			aAttrs.put(BusiMatchConsts.Fields.storeCub, storeCub[rd.nextInt(storeCub.length)]);
			aObj.setAttrs(aAttrs);
		
			zObj = new BaseMatchObject();
			zObj.setId(rd.nextInt(100000000));
			zObj.setTenantId(tenantId[rd.nextInt(tenantId.length)]);
			zObj.setTel("18800" + rd.nextInt(1000000));
			zObj.setLinkMan(RandomGenerator.random(RandomGenerator.ALPHA, 8));
			zAttrs = new HashMap<String, String>();
			zAttrs = new HashMap<String, String>();
			zAttrs.put(BusiMatchConsts.Fields.area, area[rd.nextInt(area.length)]);
			zAttrs.put(BusiMatchConsts.Fields.carCub, carCubel[rd.nextInt(carCubel.length)]);
			zAttrs.put(BusiMatchConsts.Fields.carType, carType[rd.nextInt(carType.length)]);
			zAttrs.put(BusiMatchConsts.Fields.credit, String.valueOf(rd.nextInt(500)));
			zAttrs.put(BusiMatchConsts.Fields.maxLoad, maxLoad[rd.nextInt(maxLoad.length)]);
			zAttrs.put(BusiMatchConsts.Fields.servExtAttr, servType[rd.nextInt(servType.length)]+"|"+servAttr[rd.nextInt(servAttr.length)]);
			zAttrs.put(BusiMatchConsts.Fields.servType, prodType[rd.nextInt(prodType.length)]+"|"+servType[rd.nextInt(servType.length)]);
			zAttrs.put(BusiMatchConsts.Fields.storeCub, storeCub[rd.nextInt(storeCub.length)]);
			zObj.setAttrs(zAttrs);
			
			System.out.println("a>>>" +JsonHelper.json(aObj));
			System.out.println("z===" +JsonHelper.json(zObj));
			BusiMatchControl.addTask2Cache(aObj);
			BusiMatchControl.addWoker2Cache(zObj);
			
			
//			aObj = new BaseMatchObject();
//			aObj.setId(rd.nextInt(100000000));
//			aObj.setTenantId(tenantId[0]);
//			aObj.setTel("13500" + rd.nextInt(1000000));
//			aObj.setLinkMan(RandomGenerator.random(RandomGenerator.ALPHA, 8));
//			aAttrs = new HashMap<String, String>();
//			aAttrs.put(BusiMatchConsts.Fields.area, area[0]);
//			aAttrs.put(BusiMatchConsts.Fields.carCub, carCubel[0]);
//			aAttrs.put(BusiMatchConsts.Fields.carType, carType[0]);
//			aAttrs.put(BusiMatchConsts.Fields.credit, "50");
//			aAttrs.put(BusiMatchConsts.Fields.maxLoad, maxLoad[1]);
//			aAttrs.put(BusiMatchConsts.Fields.servExtAttr, servType[0]+"|"+servAttr[0]);
//			aAttrs.put(BusiMatchConsts.Fields.servType, prodType[0]+"|"+servType[0]);
//			aAttrs.put(BusiMatchConsts.Fields.storeCub, storeCub[0]);
//			aObj.setAttrs(aAttrs);
//		
//			zObj = new BaseMatchObject();
//			zObj.setId(rd.nextInt(100000000));
//			zObj.setTenantId(tenantId[0]);
//			zObj.setTel("18800" + rd.nextInt(1000000));
//			zObj.setLinkMan(RandomGenerator.random(RandomGenerator.ALPHA, 8));
//			zAttrs = new HashMap<String, String>();
//			zAttrs = new HashMap<String, String>();
//			zAttrs.put(BusiMatchConsts.Fields.area, area[0]);
//			zAttrs.put(BusiMatchConsts.Fields.carCub, carCubel[0]);
//			zAttrs.put(BusiMatchConsts.Fields.carType, carType[0]);
//			zAttrs.put(BusiMatchConsts.Fields.credit, "50");
//			zAttrs.put(BusiMatchConsts.Fields.maxLoad, maxLoad[1]);
//			zAttrs.put(BusiMatchConsts.Fields.servExtAttr, servType[0]+"|"+servAttr[0]);
//			zAttrs.put(BusiMatchConsts.Fields.servType, prodType[0]+"|"+servType[0]);
//			zAttrs.put(BusiMatchConsts.Fields.storeCub, storeCub[0]);
//			zObj.setAttrs(zAttrs);
//			
//			System.out.println("a>>>" +JsonHelper.json(aObj));
//			System.out.println("z===" +JsonHelper.json(zObj));
//			BusiMatchControl.addTask2Cache(aObj);
//			BusiMatchControl.addWoker2Cache(zObj);
		}
	}
	
	public void testQueueSize() throws Exception {
		List<String> list =  MatchDataUtils.getAQueue();
//		assertEquals(list.size(), queueSize);
	}
	
	public static void main(String[] args) {
		Session session = SysContexts.getEntityManager(true);
		//增加工人属性
		String attrSql = "	SELECT  		CONCAT('Z_ATTR_', a.id) AS key1,  		'area' AS field1,  		IFNULL(a.AREA, '') AS value1,  		'servType' AS field2,  		IFNULL(b.servType, '') AS value2,  		'servExtAttr' AS field3,  		IFNULL(c.servExtAttr, '') AS value3,  		'maxLoad' AS field4, 	 		IFNULL(d.maxLoad, '') AS value4,  		'credit' AS field5,  		IFNULL(d.credit, '') AS value5,  		'storeCub' AS field6,  		IFNULL(d.storeCub, '') AS value6, 		'carType' AS field7,  		IFNULL(d.carType, '') AS value7,  		'carCub' AS field8,  		IFNULL(d.carCub, '') AS value8,  		'pointX' AS field9,  		IFNULL(d.pointX, '') AS value9,  		'pointY' AS field10,  		IFNULL(d.pointY, '')  AS value10 	FROM 		(SELECT service_user_id AS id, GROUP_CONCAT(district_id) AS AREA FROM cm_serv_area_inst GROUP BY service_user_id) AS a LEFT JOIN 		(SELECT service_obj_id AS id, REPLACE(GROUP_CONCAT(CONCAT(IFNULL(product_name, ''), '|', service_name)), ',|', ',') AS servType FROM cm_service_inst GROUP BY service_obj_id) AS b  		ON a.id = b.id LEFT JOIN 		(SELECT service_user_id AS id, GROUP_CONCAT(service_name, '|', attr_name) AS servExtAttr FROM `cm_serv_attr_inst`GROUP BY service_user_id) AS c 		ON a.id = c.id LEFT JOIN 		(SELECT sf_user_id AS id,  		IFNULL(largest_accept_order, 0) AS maxLoad,  		IFNULL((SELECT credit_value FROM user_credit r WHERE r.`USER_ID` = u.sf_user_id), 0) AS credit, 		IFNULL(store_square, 0) AS storeCub,  		'' AS carType, 		'' AS carCub, 		store_NAND AS pointX,  		store_EAND AS pointY  		FROM cm_sf_user_info u) AS d 		ON a.id = d.id";
		SQLQuery query = session.createSQLQuery(attrSql);
		List list = query.list();
		Map map = new HashMap();
		for (Object obj : list) {
			Object[] colunms = (Object[])obj;
			String key = (String) colunms[0];
			if (StringUtils.isNotEmpty(key)) {
				for(int i=1; i<colunms.length; i++) {
					map.put(colunms[i], colunms[++i]);
				}
				RemoteCacheUtil.hmset(key, map);
			}
		}
		
		String sfSql = "SELECT b.tenant_code, a.sf_user_id, a.Name, IFNULL(a.Phone, a.tel_phone) FROM cm_sf_user_info a, cm_sf_user_org_rel b WHERE a.sf_user_id = b.sf_user_id";
		query = session.createSQLQuery(sfSql);
		list = query.list();
		BaseMatchObject zObj = null;
		for (Object obj : list) {
			zObj = new BaseMatchObject();
			Object[] colunms = (Object[])obj;
			zObj.setTenantId((String) colunms[0]);
			zObj.setId(((BigInteger) colunms[1]).longValue());
			zObj.setLinkMan((String) colunms[2]);
			zObj.setTel((String) colunms[3]);
			RemoteCacheUtil.sadd(MatchConsts.TENANT_Z_SET_PREFIX + zObj.getTenantId(), JsonHelper.json(zObj, new String[] { "attrs" }));
		}
	}
	
}
