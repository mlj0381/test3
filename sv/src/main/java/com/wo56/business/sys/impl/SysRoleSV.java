package com.wo56.business.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.interceptor.vo.SysUrl;
import com.framework.core.util.IPage;
import com.framework.core.util.PageUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.sys.vo.SysEntity;
import com.wo56.business.sys.vo.SysMenu;
import com.wo56.business.sys.vo.SysRole;
import com.wo56.business.sys.vo.SysRoleGrant;
import com.wo56.business.sys.vo.SysRoleOperRel;
import com.wo56.business.sys.vo.out.SysRoleOut;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.SysStaticDataEnum;

public class SysRoleSV {
    /**
     * 角色查询
     * @param roleName
     * @param orgId
     * @return
     */
	public Pagination doQuerySysRole(String roleName,int orgId){
		Session session = SysContexts.getEntityManager(true);
		BaseUser base = SysContexts.getCurrentOperator();
		Criteria  ca  = session.createCriteria(SysRole.class);
		if(StringUtils.isNotEmpty(roleName)){
		ca.add(Restrictions.like("roleName", "%"+roleName+"%"));
		}
         if(orgId>0){
        	 ca.add(Restrictions.eq("orgId", orgId));
         }
         ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
         ca.add(Restrictions.eq("tenantId",Long.valueOf(base.getTenantId())));
         ca.addOrder(Order.desc("createDate"));
         IPage p = PageUtil.gridPage(ca);
 		Pagination page = new Pagination(p);
		return page;
	}
	/**
	 * 角色保存
	 * @param sysRole
	 * @return
	 */
	public String doSaveSysRole(SysRole sysRole){
		Session session = SysContexts.getEntityManager();
		Criteria  ca  = session.createCriteria(SysRole.class);
		ca.add(Restrictions.eq("roleName", sysRole.getRoleName()));
		ca.add(Restrictions.eq("tenantId", sysRole.getTenantId()));
		List<SysRole> list=ca.list();
		if(ca.list().size()>0 && (sysRole.getRoleId()==null || list.get(0).getRoleId().intValue()!=sysRole.getRoleId().intValue())){
			throw new BusinessException("该角色已经存在");
		}
		session.saveOrUpdate(sysRole);
		return "Y";
	}
	/**
	 * 查询用户拥有的角色
	 * @param userId
	 * @return
	 */
	public List queryUserRole(Long userId){
		 Session session  = SysContexts.getEntityManager();
		 Criteria  roleCa = session.createCriteria(SysRoleOperRel.class);
		 roleCa.add(Restrictions.eq("operatorId", userId));
		 roleCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		 List<SysRoleOperRel> roleList = new ArrayList();
		 if(roleCa.list().size()>0){
			 roleList=roleCa.list();
		 }
		 return roleList;
	}
	/**
	 * 保存用户角色
	 * @param roleIds
	 * @param userId
	 */
	public void saveUserRole(List roleIds,Long userId){
		 Session session  = SysContexts.getEntityManager();
		 BaseUser base =  SysContexts.getCurrentOperator();
	     SysRoleOperRel roleRel = null; 
	     List<SysRoleOperRel> roleList = new ArrayList();
	     roleList = queryUserRole(userId);
	     if(roleList.size()>0){
	    	 for(SysRoleOperRel operRel:roleList){
	    		 session.delete(operRel);
	    	 }
	     }
	     if(roleIds.size()>0){
	    	 for(int j=0;j<roleIds.size();j++){
	    		 roleRel=new SysRoleOperRel(); 
		    	 roleRel.setCreateDate(new Date(System.currentTimeMillis()));
		    	 roleRel.setOperatorId(userId);
		    	 roleRel.setRemark("用户添加角色");
		    	 roleRel.setRoleId(Integer.valueOf(roleIds.get(j).toString())); 
		    	 roleRel.setState(SysStaticDataEnum.STS.VALID);
		    	 roleRel.setLastModifyDate(new Date(System.currentTimeMillis()));
		    	 roleRel.setLastModifyOperatorId(base.getUserId());
		    	 session.saveOrUpdate(roleRel);
		    	 session.flush();
		    	 session.clear();
	    	 }
	     }
	}
	/**
	 * 查询菜单
	 * @param inParam
	 * @return
	 */
	public Pagination querySysMenu(Map inParam){
		Session session  = SysContexts.getEntityManager(true);
		Criteria menuCa  = session.createCriteria(SysMenu.class);
		menuCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		String menuName = inParam.get("menuName").toString();
		if(StringUtils.isNotEmpty(menuName)){
			//menuCa.add(Restrictions.eq("menuName", menuName));
			menuCa.add(Restrictions.ilike("menuName", "%"+menuName+"%"));
		}
		menuCa.addOrder(Order.desc("createDate"));
		IPage p = PageUtil.gridPage(menuCa);
	 	Pagination page = new Pagination(p);
		return page;
		
	}
	
	/**
	 * 查询菜单
	 * @param inParam
	 * @return
	 */
	public Pagination querySysUrl(Map inParam){
		Session session  = SysContexts.getEntityManager(true);
	    Criteria urlCa  = session.createCriteria(SysUrl.class);
		String urlName = inParam.get("urlName").toString();
		if(StringUtils.isNotEmpty(urlName)){
			urlCa.add(Restrictions.ilike("urlName", "%"+urlName+"%"));
		}
		urlCa.add(Restrictions.eq("state", true));
		//urlCa.setProjection(Projections.groupProperty("groupName"));
		urlCa.addOrder(Order.desc("createDate"));
		IPage p = PageUtil.gridPage(urlCa);
	 	Pagination page = new Pagination(p);
		//criteria.setProjection(Projections.groupProperty(“age”));
		/*List<SysUrl> urlList = new ArrayList<SysUrl>();
		if(urlCa.list().size()>0){
			urlList=urlCa.list();
		}*/
		return page;
	}
	
	
	
	/**
	 * 系统Url保存
	 * @param sysRole
	 * @return
	 */
	public String doSaveSysUrl(SysUrl sysUrl){
		Session session = SysContexts.getEntityManager();
		Criteria  ca  = session.createCriteria(SysUrl.class);
		ca.add(Restrictions.eq("urlName", sysUrl.getUrlName()));
		ca.add(Restrictions.eq("urlPath",sysUrl.getUrlPath() ));
		ca.add(Restrictions.eq("groupName",sysUrl.getGroupName()));
		if(ca.list().size()>0){
			throw new BusinessException("该Url已经存在");
		}
		 if(sysUrl.getId()<=0){
			BaseUser baseUser = SysContexts.getCurrentOperator();
			SysEntity sysEntity = new SysEntity(); 
			sysEntity.setCreateDate(new Date(System.currentTimeMillis()));
			sysEntity.setEntityName(sysUrl.getUrlName());
			sysEntity.setEntityType(3);
			sysEntity.setLastModifyDate(new Date(System.currentTimeMillis()));
			sysEntity.setLastModifyOperatorId(baseUser.getUserId());
			sysEntity.setState(SysStaticDataEnum.STS.VALID);
			sysEntity.setRemark("系统URL添加的ENTITY");
			session.save(sysEntity);
			sysUrl.setCreateDate(new Date(System.currentTimeMillis()));
			sysUrl.setGroupId(1L);
			sysUrl.setState(true);
			//sysUrl.setGroupName("系统Url");
			sysUrl.setOpId(baseUser.getUserId());
			sysUrl.setEntityId(Long.valueOf(sysEntity.getEntityId()+""));
			session.save(sysUrl);
			SysRoleGrant grant = new SysRoleGrant();
			grant.setEntityId(Integer.valueOf(sysEntity.getEntityId()+""));
			grant.setRoleId(SysStaticDataEnum.ADMIN_ROLE.SUPP_ADMINISTRATOR);
			grant.setState(SysStaticDataEnum.STS.VALID);
			grant.setLastModifyDate(new Date(System.currentTimeMillis()));
			grant.setLastModifyOperatorId(baseUser.getUserId());
			grant.setCreateDate(new Date(System.currentTimeMillis()));
			session.save(grant);
		 }else{
			 session.saveOrUpdate(sysUrl);
		 }
		
		return "Y";
	}
//	
//	
//	
//	/**
//	 * 系统菜单保存
//	 * @param sysRole
//	 * @return
//	 */
	public String doSaveSysMenu(SysMenu sysMenu){
		Session session = SysContexts.getEntityManager();
		Criteria  ca  = session.createCriteria(SysMenu.class);
		ca.add(Restrictions.eq("menuName", sysMenu.getMenuName()));
		ca.add(Restrictions.eq("menuPath", sysMenu.getMenuPath()));
		ca.add(Restrictions.eq("parentId", sysMenu.getParentId()));
		if(ca.list().size()>0){
			throw new BusinessException("该菜单已经存在");
		}
		if(sysMenu.getMenuId()<=0){
			BaseUser baseUser = SysContexts.getCurrentOperator();
			SysEntity sysEntity = new SysEntity();
			sysEntity.setCreateDate(new Date(System.currentTimeMillis()));
			sysEntity.setEntityName(sysMenu.getMenuName());
			sysEntity.setEntityType(1);
			sysEntity.setLastModifyDate(new Date(System.currentTimeMillis()));
			sysEntity.setLastModifyOperatorId(baseUser.getUserId());
			sysEntity.setState(SysStaticDataEnum.STS.VALID);
			sysEntity.setRemark("菜单添加的ENTITY");
			session.save(sysEntity);
			sysMenu.setCreateDate(new Date(System.currentTimeMillis()));
			sysMenu.setLastModifyDate(new Date(System.currentTimeMillis()));
			sysMenu.setLastModifyOperatorId(baseUser.getUserId());
			sysMenu.setDomainCode("1");
			sysMenu.setState(1);
			sysMenu.setEntityId(sysEntity.getEntityId());
			session.save(sysMenu);
			
			SysRoleGrant grant = new SysRoleGrant();
			grant.setEntityId(Integer.valueOf(sysEntity.getEntityId()+""));
			grant.setRoleId(SysStaticDataEnum.ADMIN_ROLE.SUPP_ADMINISTRATOR);
			grant.setState(SysStaticDataEnum.STS.VALID);
			grant.setLastModifyDate(new Date(System.currentTimeMillis()));
			grant.setLastModifyOperatorId(baseUser.getUserId());
			grant.setCreateDate(new Date(System.currentTimeMillis()));
			session.save(grant);
			/*sysMenu.setMenuSeq(sysMenu.getMenuId());
			session.update(sysMenu);*/
		}else{
			session.saveOrUpdate(sysMenu);
		}
		return "Y";
	}
	
	/**
	 * 检索菜单信息
	 * @param userName  姓名
	 * @return list  返回值
	 *         OBJECT[0] 姓名
	 *         OBJECT[1] 用户ID id
	 * @throws Exception
	 */
	public List searchSysMenu(String menuName) throws Exception{
		if(menuName==null || menuName.equals("")){
			throw new  BusinessException ("请输入菜单名称!");
		}
		Session session = SysContexts.getEntityManager(true);
		Map queryMap = new HashMap();
		Criteria query  = session.createCriteria(SysMenu.class);
		query.add(Restrictions.like("menuName","%"+menuName+"%"));
		query.addOrder(Order.desc("createDate"));
		List<SysMenu> list = query.list();
		return list;
	}
	
	
	/**
	 * lyh
	 * 根据角色查询 角色授权的信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<SysRoleGrant> queryUrlByRoleId(int roleId){
		 Session session  = SysContexts.getEntityManager();
		 Criteria  roleCa = session.createCriteria(SysRoleGrant.class);
		 roleCa.add(Restrictions.eq("roleId", roleId));
		 roleCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		 List<SysRoleGrant> roleList  = roleCa.list();
		 
		 return roleList;
	}
	
	
	/**
	 * 保存用户角色
	 * @param roleIds
	 * @param userId
	 */
	public void saveRoleUrl(List urlIds,Integer roleId){
		 Session session  = SysContexts.getEntityManager();
		 BaseUser base =  SysContexts.getCurrentOperator();
	    
		List<Long> entityIds = new ArrayList<Long>();
		if(urlIds.size()>0){
			 Criteria ca  = session.createCriteria(SysUrl.class); 
			 ca.add(Restrictions.eq("state", true));
			 ca.add(Restrictions.in("groupName", urlIds));
			 if(ca.list().size()>0){
				 List<SysUrl> urlList = ca.list();
				 for(SysUrl sysUrl:urlList){
					 entityIds.add(sysUrl.getEntityId()); 
				 }
			 }
		}
		 SysRoleGrant roleGrant = null; 
	     List<SysRoleGrant> roleList = new ArrayList();
	     String sql = "SELECT g.* FROM Sys_Role_Grant g,sys_entity  e WHERE g.role_Id=:roleId AND g.entity_id=e.entity_id AND e.ENTITY_TYPE=3";
	     SQLQuery query  =  session.createSQLQuery(sql).addEntity("g", SysRoleGrant.class);
	     query.setParameter("roleId", roleId);
	     if(query.list().size()>0){
	    	 roleList=query.list();
	     }
	     if(roleList.size()>0){
	    	 for(SysRoleGrant sysRole:roleList){
	    		 session.delete(sysRole);
	    	 }
	     }
	     if(entityIds.size()>0){
	    	 for(int j=0;j<entityIds.size();j++){
	    		 Long entityId = entityIds.get(j);
	    		 roleGrant=new SysRoleGrant(); 
	    		 roleGrant.setCreateDate(new Date(System.currentTimeMillis()));
	    		 roleGrant.setLastModifyDate(new Date(System.currentTimeMillis()));
	    		 roleGrant.setLastModifyOperatorId(base.getUserId());
	    		 roleGrant.setRoleId(roleId);
	    		 roleGrant.setState(SysStaticDataEnum.STS.VALID);
	    		
	    		// SysUrl sysUrl = (SysUrl)session.get(SysUrl.class, id);
	    		 roleGrant.setEntityId(Integer.valueOf(entityId+""));
	    		
	    		/* Criteria urlCa = session.createCriteria(SysUrl.class);
	    		 urlCa.add(Restrictions.eq("id",id));*/
		    	 session.saveOrUpdate(roleGrant);
		    	 session.flush();
		    	 session.clear();
	    	 }
	     }
	}
	
	/**
	 * 查询角色已授权的URl
	 * @param userId
	 * @return
	 */
	public List queryRoleByUrl(Integer roleId){
		 Session session  = SysContexts.getEntityManager();
		 Criteria  roleCa = session.createCriteria(SysRoleGrant.class);
		 roleCa.add(Restrictions.eq("roleId", roleId));
		 roleCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		 List<SysRoleGrant> roleList = new ArrayList();
		 if(roleCa.list().size()>0){
			 roleList=roleCa.list();
		 }
		 return roleList;
	}
	
	public String delUrlAndEntity(List urlIds){
		 Session session  = SysContexts.getEntityManager();
		 List<Integer> entityIds= new ArrayList();
		if(urlIds.size()>0){
			for(int j=0;j<urlIds.size();j++){
				long id= Long.valueOf(urlIds.get(j)+"");
				Criteria urlCa = session.createCriteria(SysUrl.class);
				urlCa.add(Restrictions.eq("id", id));
				urlCa.add(Restrictions.eq("state", true));
				List<SysUrl> urlList = urlCa.list();
				if(urlList.size()>0){
					SysUrl sysUrl = urlList.get(0);
					if(sysUrl!=null){
						sysUrl.setState(false);
						session.update(sysUrl);
						entityIds.add(Integer.valueOf(sysUrl.getEntityId()+""));
					}
				}
				
			}
		}
		if(entityIds.size()>0){
			for(int i=0;i<entityIds.size();i++){
				int entityId = Integer.valueOf(entityIds.get(i)+"");
				 Criteria  ca= session.createCriteria(SysEntity.class);
				 ca.add(Restrictions.eq("entityId", entityId));
				 ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
				 List<SysEntity> entityList = ca.list();
				 if(entityList.size()>0){
					 SysEntity sysEntity = entityList.get(0); 
					 if(sysEntity!=null){
						 sysEntity.setState(SysStaticDataEnum.STS.NULLITY);
						 session.update(sysEntity);
					 }
				 }
			}
			
			Criteria  grantCa= session.createCriteria(SysRoleGrant.class);
			 grantCa.add(Restrictions.in("entityId", entityIds));
			 grantCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
			 List<SysRoleGrant>  grantList  = grantCa.list();  
			 if(grantList.size()>0){
				 for(SysRoleGrant sysRoleGrant:grantList){
					 session.delete(sysRoleGrant);
				 }
			 }
		}
	   
		return "Y"; 
		
	}
	
	
	
	public String delEntityAndMenu(List menuIds){
		 Session session  = SysContexts.getEntityManager();
		 List<Integer> entityIds= new ArrayList();
		if(menuIds.size()>0){
			for(int j=0;j<menuIds.size();j++){
				int menuId= Integer.valueOf(menuIds.get(j)+"");
				Criteria urlCa = session.createCriteria(SysMenu.class);
				urlCa.add(Restrictions.eq("menuId", menuId));
				urlCa.add(Restrictions.eq("state",SysStaticDataEnum.STS.VALID));
				List<SysMenu> menuList = urlCa.list();
				if(menuList.size()>0){
					SysMenu sysMenu = menuList.get(0);
					if(sysMenu!=null){
						sysMenu.setState(SysStaticDataEnum.STS.NULLITY);
						session.update(sysMenu);
						entityIds.add(Integer.valueOf(sysMenu.getEntityId()+""));
					}
				}
				
			}
		}
		if(entityIds.size()>0){
			for(int i=0;i<entityIds.size();i++){
				int entityId = Integer.valueOf(entityIds.get(i)+"");
				 Criteria  ca= session.createCriteria(SysEntity.class);
				 ca.add(Restrictions.eq("entityId", entityId));
				 ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
				 List<SysEntity> entityList = ca.list();
				 if(entityList.size()>0){
					 SysEntity sysEntity = entityList.get(0); 
					 if(sysEntity!=null){
						 sysEntity.setState(SysStaticDataEnum.STS.NULLITY);
						 session.update(sysEntity);
					 }
				 }
				
			}
			 Criteria  grantCa= session.createCriteria(SysRoleGrant.class);
			 grantCa.add(Restrictions.in("entityId", entityIds));
			 grantCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
			 List<SysRoleGrant>  grantList  = grantCa.list();  
			 if(grantList.size()>0){
				 for(SysRoleGrant sysRoleGrant:grantList){
					 session.delete(sysRoleGrant);
				 }
			 }
		}
		return "Y"; 
		
	}
	public String doSaveRoleMenu(List entityIds,int roleId){
		BaseUser base = SysContexts.getCurrentOperator();
		Session session  = SysContexts.getEntityManager();
		Criteria ca  = session.createCriteria(SysRoleGrant.class);
		ca.add(Restrictions.eq("roleId", roleId));
	//	ca.add(Restrictions.in("entityId", entityIds));
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		if(ca.list().size()>0){
			List grantLists = new ArrayList();
			List<SysRoleGrant> grantList = new ArrayList<SysRoleGrant>();
			//grantList=ca.list();
			 String sql = "SELECT g.* FROM Sys_Role_Grant g,sys_entity e WHERE g.role_Id=:roleId AND g.entity_id=e.entity_id AND e.ENTITY_TYPE=1";
		     SQLQuery query  =  session.createSQLQuery(sql).addEntity("g", SysRoleGrant.class);
		     query.setParameter("roleId", roleId);
                  query.list();
		     
		     if(query.list().size()>0){
		    	 grantList=query.list();
		     }
			for(SysRoleGrant sysRoleGrant:grantList){
				session.delete(sysRoleGrant);
			}
		}
		if(entityIds.size()>0){
			for(int j=0;j<entityIds.size();j++){
				int entityId =Integer.valueOf(entityIds.get(j)+""); 
				SysRoleGrant roleGrant= new SysRoleGrant();
				roleGrant.setCreateDate(new Date(System.currentTimeMillis()));
				roleGrant.setEntityId(entityId);
				roleGrant.setLastModifyDate(new Date(System.currentTimeMillis()));
				roleGrant.setLastModifyOperatorId(base.getUserId());
				roleGrant.setRoleId(roleId);
				roleGrant.setState(SysStaticDataEnum.STS.VALID);
				session.save(roleGrant);
			}
		}
		return null;
	}
	
	/**
	 * 查询角色已授权的URl
	 * @param userId
	 * @return
	 */
	public List queryRoleByMenu(Integer roleId){
		Session session = SysContexts.getEntityManager(true);
		List<SysRoleGrant> roleList =queryRoleByUrl(roleId);
		List<Long> menuList =new ArrayList<Long>();
		List<SysMenu> sysList = new ArrayList<SysMenu>();
		if(roleList.size()>0){
			for(SysRoleGrant sysRole:roleList){
				menuList.add(Long.valueOf(sysRole.getEntityId()+""));
			}
		}
		if(menuList.size()>0){
			Criteria ca  = session.createCriteria(SysMenu.class);
			ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
			ca.add(Restrictions.in("entityId", menuList));
			if(ca.list().size()>0){
				sysList=ca.list();
			}
		}
		 return sysList;
	}
	/**
	 * 同时删除SYS_ROLE,sys_role_grant,sys_role_oper_rel 角色对应的数据
	 * @param roleIds
	 * @return
	 */
	public String delSysRole(List<Integer> roleIds){
		 Session session  = SysContexts.getEntityManager();
		 List<Integer> roList = new ArrayList<Integer>();
		// List entityIds= new ArrayList();
		if(roleIds.size()>0){
			for(int i=0;i<roleIds.size();i++){
				roList.add(Integer.valueOf(roleIds.get(i)+""));	
			}
			Criteria ca  = session.createCriteria(SysRole.class);
			ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
			ca.add(Restrictions.in("roleId", roList));
			if(ca.list().size()>0){
				List<SysRole> roleList =  ca.list();
				for(SysRole role:roleList){
					role.setState(SysStaticDataEnum.STS.NULLITY);
					session.update(role);
				}
			}
			Criteria grantCa  = session.createCriteria(SysRoleGrant.class);
			grantCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
			grantCa.add(Restrictions.in("roleId", roList));
			if(grantCa.list().size()>0){
				List<SysRoleGrant> grantList =  grantCa.list();
				for(SysRoleGrant sysRoleGrant:grantList){
					sysRoleGrant.setState(SysStaticDataEnum.STS.NULLITY);
					session.update(sysRoleGrant);
				}
			}
			Criteria operRelCa  = session.createCriteria(SysRoleOperRel.class);
			operRelCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
			operRelCa.add(Restrictions.in("roleId", roList));
			if(operRelCa.list().size()>0){
				List<SysRoleOperRel> operList =  operRelCa.list();
				for(SysRoleOperRel sysRoleOperRel:operList){
					sysRoleOperRel.setState(SysStaticDataEnum.STS.NULLITY);
					session.update(sysRoleOperRel);
				}
			}
		}
		return "Y"; 
	}
	
	
	/**
	 * 用户查询
	 * @param roleId
	 * @return
	 */
	public Pagination queryRoleUser(int roleId){
		Session session  = SysContexts.getEntityManager(true);
		Criteria ca  = session.createCriteria(SysRoleOperRel.class);
		ca.add(Restrictions.eq("roleId",roleId));
		ca.add(Restrictions.eq("state",SysStaticDataEnum.STS.VALID));
		Criteria userCa  = session.createCriteria(CmUserInfo.class);
		if(ca.list().size()>0){
			List<SysRoleOperRel> operList = ca.list();
			List<Long> objList = new ArrayList<Long>();
			for(SysRoleOperRel sysRel:operList){
				objList.add(sysRel.getOperatorId());
			}
			if(objList.size()>0){
				userCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
				userCa.add(Restrictions.in("userId", objList));
			}
		}
		    IPage p = PageUtil.gridPage(userCa);
	 		Pagination page = new Pagination(p);
		return page;
	}

	/**
	 * 查询其他公司的url
	 * @return
	 */
	public List queryOperSysUrl(){
		Session session  = SysContexts.getEntityManager(true);
		BaseUser base = SysContexts.getCurrentOperator();
		Map map = base.getAttrs();
		Set<Integer> entitySet = new HashSet<Integer>();
		List<Integer> list  = (List<Integer>)map.get(EnumConsts.Common.SESSION_ROLES);
		if(list.size()>0){
			Criteria grantCa  = session.createCriteria(SysRoleGrant.class);	
			grantCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
			grantCa.add(Restrictions.in("roleId", list));
			if(grantCa.list().size()>0){
				List<SysRoleGrant> grantList = grantCa.list();
				for(SysRoleGrant sysRoleGrant:grantList){
					entitySet.add(sysRoleGrant.getEntityId());
				}
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT u.* FROM SYS_URL u WHERE u.state=:state and entity_id in:entityId GROUP BY u.group_name");
		SQLQuery query  = session.createSQLQuery(buffer.toString()).addEntity("u", SysUrl.class);
		//query.setP
		query.setParameter("state", true);
		query.setParameterList("entityId", entitySet);
		List<SysUrl> urlList = new ArrayList<SysUrl>();
		if(query.list().size()>0){
			urlList=query.list();
		}
		return urlList;
	}
	/**
	 * lyh
	 * 根据传入的角色的租户，组织查询角色
	 * 
	 * @param tenantId  －1 表示查询的是公司管理员的角色
	 * @param orgId
	 * @return
	 */
	public List<SysRole> queryRoleByOrg(Long tenantId,Long orgId,Integer roleType,Integer menuType){
		Session session = SysContexts.getEntityManager(true);
		Criteria  ca  = session.createCriteria(SysRole.class);
		
        if(orgId!=null && orgId>0){
        	 ca.add(Restrictions.eq("orgId", orgId));
         }
        ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
        ca.add(Restrictions.eq("tenantId",tenantId));
        if(roleType!=null && roleType.intValue()>0){
        	ca.add(Restrictions.eq("roleType",roleType));
        }
        if(menuType>0){
        	ca.add(Restrictions.eq("menuType",menuType));
        }
        
		return ca.list();
	}
	
	/**
	 * lyh
	 * 删除租户下 失效的角色授权
	 * @param entityIds
	 * @param tenantId
	 * @return
	 */
	public void delRoleGrantByTenantId(List entityIds,long tenantId){
		Session session  = SysContexts.getEntityManager();
		String sql = "delete FROM Sys_Role_Grant   where role_Grant_Id in (select t.role_Grant_Id from (select k.role_Grant_Id from  sys_role r,Sys_Role_Grant k  WHERE  r.role_id=k.role_id and r.tenant_id=:tenantId and k.entity_id in (:entityIds)) as t)";
		SQLQuery query  =  session.createSQLQuery(sql);
		query.setParameter("tenantId", tenantId);
		query.setParameterList("entityIds", entityIds);
		query.executeUpdate();
	}
	
	/**
	 * lyh
	 * 删除角色下 失效的角色授权
	 * @param entityIds
	 * @param tenantId
	 * @return
	 */
	public void delRoleGrantByRoleId(List entityIds,long roleId){
		Session session  = SysContexts.getEntityManager();
		String sql = "delete FROM Sys_Role_Grant  where role_Grant_Id in (select t.role_Grant_Id from (select g.role_Grant_Id from sys_role r,Sys_Role_Grant g WHERE r.role_id=g.role_id and r.role_id=:roleId and g.entity_id in (:entityIds)) as t)";
		SQLQuery query  =  session.createSQLQuery(sql);
		query.setParameter("roleId", roleId);
		query.setParameterList("entityIds", entityIds);
		query.executeUpdate();
	}
	/**
	 * lyh
	 * 查询角色
	 * @return
	 */
	public Pagination getSysRoleAll(Long tenantId , Integer roleType,String roleName,Integer menuType){
		Session session= SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer(" select s.ROLE_ID,s.ROLE_NAME,s.ROLE_TYPE,c.USER_NAME,s.CREATE_DATE,s.TENANT_ID,s.MENU_TYPE ");
		sb.append(" from sys_role s LEFT JOIN cm_user_info c ON s.LAST_MODIFY_OPERATOR_ID = c.USER_ID  ");
		sb.append(" where s.TENANT_ID = :tenantId and s.state = :state");
		if(roleType!=null && roleType > 0){
			sb.append(" and s.role_type = :roleType");
		}
		if(StringUtils.isNotEmpty(roleName)){
			sb.append(" and s.role_name like :roleName");
		}
		if(menuType != null && menuType > 0){
			sb.append(" and s.MENU_TYPE = :menuType");
		}
		sb.append(" ORDER BY s.create_date desc ");
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.setParameter("tenantId", tenantId);
		query.setParameter("state", SysStaticDataEnum.STS.VALID);
		if(roleType!=null && roleType > 0){
			query.setParameter("roleType", roleType);
		}
		if(StringUtils.isNotEmpty(roleName)){
			query.setParameter("roleName", "%"+roleName+"%");
		}
		if(menuType != null && menuType > 0){
			query.setParameter("menuType", menuType);
		}
		IPage p = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>)p.getThisPageElements();
		List<SysRoleOut> out = new ArrayList<SysRoleOut>();
		if(list != null && list.size() > 0){
			for (Object[] temp : list) {
				SysRoleOut sout = new SysRoleOut();
				sout.setRoleId(Integer.parseInt(String.valueOf(temp[0])));
				sout.setRoleName(String.valueOf(temp[1]));
				Integer roType = Integer.parseInt(String.valueOf(temp[2]));
				sout.setRoleType(roType);
				if(roType == SysStaticDataEnum.ROLE_TYPE.COMMON){
					sout.setRoleTypeName("普通员工");
				}else{
					sout.setRoleTypeName(SysStaticDataUtil.getSysStaticData("ROLE_TYPE", String.valueOf(String.valueOf(temp[2]))).getCodeName());
				}
				sout.setUserName(String.valueOf(temp[3]));
				sout.setCreateDate((Date)temp[4]);
				sout.setTenantId(Long.parseLong(String.valueOf(temp[5])));
				sout.setMenuType(Integer.parseInt(String.valueOf(temp[6])));
				out.add(sout);
			}
		}
		p.setThisPageElements(out);
    	Pagination<Object[]> pages = new Pagination<Object[]>(p);
		return pages;
	}
	/**
	 * 验证角色是否有用户在用
	 * false 不存在
	 * true 存在
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean checkSysROR(Integer roleId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(SysRoleOperRel.class);
		ca.add(Restrictions.eq("roleId", roleId));
		ca.add(Restrictions.eq("state",SysStaticDataEnum.STS.VALID));
		List<SysRoleOperRel> rorList = (List<SysRoleOperRel>)ca.list();
		if(rorList != null && rorList.size() > 0){
			return true;
		}
		return false;
	}
	
}
