package com.wo56.common.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.cache.AbstractCache;
import com.framework.core.cache.CacheFactory;
import com.wo56.business.sys.vo.SysMenu;
import com.wo56.common.consts.PermissionConsts;
import com.wo56.common.consts.SysStaticDataEnum;

/**
 * 菜单缓存
 * @author zhouchao
 *
 */
public class SysMenuCache extends AbstractCache {

	@Override
	public void loadDate() {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(SysMenu.class);
		ca.add(Restrictions.eq("state", 1));
		List<SysMenu> list = ca.list();
		Collections.sort(list, new Comparator<SysMenu>() {
			@Override
			public int compare(SysMenu menu1, SysMenu menu2) {
				if(menu1.getMenuSeq()==null || menu2.getMenuSeq()==null){
					return 0;
				}
				return (int) (menu1.getMenuSeq() - menu2.getMenuSeq());
			};
		});
		CacheFactory.put(SysMenuCache.class.getName(), PermissionConsts.GrantConstant.SYS_MENU, list);
		
		List<SysMenu> platform=new ArrayList<SysMenu>();
		
		List<SysMenu> company=new ArrayList<SysMenu>();
		
		List<SysMenu> org=new ArrayList<SysMenu>();
		
		List<SysMenu> master=new ArrayList<SysMenu>();

		List<SysMenu> pullPagCompany=new ArrayList<SysMenu>();
		
		if(list!=null){
			for(SysMenu menu:list){
				if(StringUtils.isNotEmpty(menu.getMenuType())){
					if(menu.getMenuType().equals(SysStaticDataEnum.MENU_TYPE.PLATFORM)){
						platform.add(menu);
					}else if(menu.getMenuType().equals(SysStaticDataEnum.MENU_TYPE.COMPANY)){
						company.add(menu);
					}else if(menu.getMenuType().equals(SysStaticDataEnum.MENU_TYPE.ORG)){
						org.add(menu);
					}else if(menu.getMenuType().equals(SysStaticDataEnum.MENU_TYPE.MASTER)){
						master.add(menu);
					}else if(menu.getMenuType().equals(SysStaticDataEnum.MENU_TYPE.PULL_PAG_COMPANY)){
						pullPagCompany.add(menu);
					}
				}
			}
			
			CacheFactory.put(SysMenuCache.class.getName(), PermissionConsts.GrantConstant.SYS_MENU+SysStaticDataEnum.MENU_TYPE.PLATFORM, platform);
			CacheFactory.put(SysMenuCache.class.getName(), PermissionConsts.GrantConstant.SYS_MENU+SysStaticDataEnum.MENU_TYPE.COMPANY, company);
			CacheFactory.put(SysMenuCache.class.getName(), PermissionConsts.GrantConstant.SYS_MENU+SysStaticDataEnum.MENU_TYPE.ORG, org);
			CacheFactory.put(SysMenuCache.class.getName(), PermissionConsts.GrantConstant.SYS_MENU+SysStaticDataEnum.MENU_TYPE.MASTER, master);
			CacheFactory.put(SysMenuCache.class.getName(), PermissionConsts.GrantConstant.SYS_MENU+SysStaticDataEnum.MENU_TYPE.PULL_PAG_COMPANY, pullPagCompany);
			
		}
	}

}
