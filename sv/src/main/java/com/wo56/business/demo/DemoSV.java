package com.wo56.business.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.PageUtil;

public class DemoSV {

	public Object doSv(Map<String, String> inParam) throws Exception{
		String loginAcct = DataFormat.getStringKey(inParam, "loginAcct");
		String pwd = DataFormat.getStringKey(inParam, "pwd");
		
		if (pwd.equals("A")) {
			BaseUser user = new BaseUser();
			user.setOperId(1111L);
			user.setUserId(1111L);
			return user;
		} else if (pwd.equals("B")) {
			throw new BusinessException("10001", "哥们，出错了");
		} else if (pwd.equals("List")) {
			List<BaseUser> list = new ArrayList<BaseUser>();
			for (int i=0; i<10; i++) {
				BaseUser user = new BaseUser();
				user.setOperId(i);
				user.setUserId(Long.valueOf(i));
				user.setUserName("用户_" +i);
				list.add(user);
			}
			return list;
		} else if (pwd.equals("page")) {
			Session session = SysContexts.getEntityManager(true);
			Criteria ca = session.createCriteria(SysStaticData.class);
			IPage page = PageUtil.gridPage(ca);
			Pagination<SysStaticData> numPage = new Pagination<SysStaticData>(page);
			return  numPage;
		} else if (pwd.equals("map")) {
			Map map = new HashMap<String, Object>();
			map.put("test", Long.parseLong("1000"));
			map.put("isOk", true);
			return  map;
		}
		return null;
	}
}
