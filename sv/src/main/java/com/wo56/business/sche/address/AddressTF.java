package com.wo56.business.sche.address;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.multitab.CommonCol;
import com.framework.core.multitab.CommonVO;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;

public class AddressTF {

	
	public Map<String,Object> doQuery(Map<String,Object> inParam) throws Exception{
		long provinceId = DataFormat.getLongKey(inParam, "provinceId");
		long cityId = DataFormat.getLongKey(inParam, "cityId");
		long countyId = DataFormat.getLongKey(inParam, "countyId");
		long streetId = DataFormat.getLongKey(inParam, "streetId");
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT p.name AS provinceName,c.name as cityName,d.name as countyName,s.name as streetName FROM province as p inner join city as c on p.id=c.PROV_ID inner join district as d on c.id = d.city_id left join street as s on d.id=s.district_id WHERE 1=1   ");
		if(provinceId>0){
			sb.append(" AND   p.id=:provinceId  ");
		}
		if(cityId>0){
			sb.append(" AND   c.id=:cityId  ");
		}
		if(countyId>0){
			sb.append(" AND   d.id=:countyId  ");
		}
		if(streetId>0){
			sb.append(" AND   s.id=:streetId  ");
		}
		sb.append(" ORDER BY p.id asc");
		Query query = session.createSQLQuery(sb.toString()).addScalar("provinceName", StandardBasicTypes.STRING).addScalar("cityName", StandardBasicTypes.STRING).addScalar("countyName", StandardBasicTypes.STRING).addScalar("streetName", StandardBasicTypes.STRING);
		if(provinceId>0){
			query.setParameter("provinceId", provinceId);
		}
		if(cityId>0){
			query.setParameter("cityId", cityId);
		}
		if(countyId>0){
			query.setParameter("countyId", countyId);
		}
		if(streetId>0){
			query.setParameter("streetId", streetId);
		}
		IPage p = PageUtil.gridPage(query);
		List list = (List) p.getThisPageElements();
		CommonVO commVO = null;
		for (int i = 0; i < list.size(); i++) {
			Object[] objAry = (Object[]) list.get(i);   
	    	commVO = new CommonVO();
			commVO.addColunm(new CommonCol("provinceName", objAry[0]));
	    	commVO.addColunm(new CommonCol("cityName", objAry[1]));
	    	commVO.addColunm(new CommonCol("countyName", objAry[2]));
	    	commVO.addColunm(new CommonCol("streetName", objAry[3]));
	    	list.set(i, commVO);
		}
		Pagination<Object[]> page = new Pagination<Object[]>(p);
		Map<String,Object> rtnMap = new HashMap<String,Object>();
		rtnMap=JsonHelper.parseJSON2Map(JsonHelper.json(page));
		return rtnMap;
	}
}
