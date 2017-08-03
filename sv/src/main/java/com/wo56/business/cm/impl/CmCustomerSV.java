package com.wo56.business.cm.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.encrypt.K;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.PageUtil;
import com.wo56.business.ac.vo.AcAccount;
import com.wo56.business.cm.intf.CmUserInfoTF;
import com.wo56.business.cm.vo.CmCustomer;
import com.wo56.business.cm.vo.CmDriverInfo;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmVehicleInfo;
import com.wo56.business.cm.vo.out.PRCustomerParamOut;
import com.wo56.business.cm.vo.out.QueryVehicleDriverOut;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.sys.vo.SysRoleOperRel;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.utils.ObjectCompareUtils;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class CmCustomerSV {
	private static final Log log = LogFactory.getLog(CmCustomerSV.class);

	/**
	 * 保存发货人和收货人信息
	 * @param prCustomerParam
	 * @param orgId
	 * @param  tenantId 专线的租户id
	 */
	public void doSave(PRCustomerParamOut prCustomerParam,long orgId,long tenantId){
		//发货人
		CmCustomer pCustomer = new CmCustomer();
		if(prCustomerParam.getpId()>0){
			pCustomer.setId(prCustomerParam.getpId());
		}
		pCustomer.setName(prCustomerParam.getpName());
		pCustomer.setLinkmanName(prCustomerParam.getpLinkmanName());
		pCustomer.setTelephone(prCustomerParam.getpTelephone());
		pCustomer.setBill(prCustomerParam.getpBill());
		pCustomer.setParentId(0L);
//		pCustomer.setTenantId(tenantId);
		pCustomer.setZxTenantId(tenantId);
		pCustomer.setOrgId(orgId);
		//收货人
		CmCustomer rCustomer = new CmCustomer();
		if(prCustomerParam.getpId()>0){
			rCustomer.setId(prCustomerParam.getrId());
		}
		rCustomer.setName(prCustomerParam.getrName());
		rCustomer.setLinkmanName(prCustomerParam.getrLinkmanName());
		rCustomer.setTelephone(prCustomerParam.getrTelephone());
		rCustomer.setBill(prCustomerParam.getrBill());
		rCustomer.setAddress(prCustomerParam.getrAddress());
//		rCustomer.setTenantId(tenantId);
		rCustomer.setZxTenantId(tenantId);
		rCustomer.setOrgId(orgId);
		
		Session session = SysContexts.getEntityManager();
		//判断发货人
		boolean pIsSave = false;
		if(pCustomer.getId()>0){
			CmCustomer oldPCm = (CmCustomer) session.get(CmCustomer.class, pCustomer.getId());
			if(oldPCm == null || !ObjectCompareUtils.isModifyObj(oldPCm, pCustomer, new String[]{"name","linkmanName","telephone","bill"})){
				pCustomer.setId(0);
				pCustomer.setType(SysStaticDataEnum.CUSTOMER_TYPE.PUB_CUSTOMER);
				session.save(pCustomer);
				pIsSave = true;
			}
		}else{
			//发货人不存在
			pCustomer.setId(0);
			pCustomer.setType(SysStaticDataEnum.CUSTOMER_TYPE.PUB_CUSTOMER);
			session.save(pCustomer);
			pIsSave = true;
		}
		//判断收货人
		if(!pIsSave && rCustomer.getId()>0){
			CmCustomer oldPCm = (CmCustomer) session.get(CmCustomer.class, rCustomer.getId());
			if(oldPCm == null || !ObjectCompareUtils.isModifyObj(oldPCm, rCustomer, new String[]{"name","linkmanName","telephone","bill","address"})){
				rCustomer.setId(0);
				rCustomer.setParentId(pCustomer.getId());
				rCustomer.setType(SysStaticDataEnum.CUSTOMER_TYPE.REC_CUSTOMER);
				session.save(rCustomer);
			}
		}else{
			//收货人不存在
			rCustomer.setId(0);
			rCustomer.setParentId(pCustomer.getId());
			rCustomer.setType(SysStaticDataEnum.CUSTOMER_TYPE.REC_CUSTOMER);
			session.save(rCustomer);
		}
	}
	/**
	 * 根据账号查询用户
	 * @param loginAcct
	 * @return
	 * @throws Exception
	 */
	public CmUserInfo queryUserByAcct(String loginAcct)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		if(StringUtils.isEmpty(loginAcct)){
			return null;
		}
		Criteria ca = session.createCriteria(CmUserInfo.class);
		ca.add(Restrictions.eq("loginAcct", loginAcct));
		
		List<CmUserInfo> list = ca.list();
		if(list!=null && list.size()==1){
			return list.get(0);
		}else if(list.size()>1){
			log.error("账号："+loginAcct+"数据异常");
		}
		return null;
	}
	/**
	 * 根据userId查询用户
	 * @param loginAcct
	 * @return
	 * @throws Exception
	 */
	public CmUserInfo queryUserByUserId(long userId)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		if(userId<=0){
			return null;
		}
		Criteria ca = session.createCriteria(CmUserInfo.class);
		ca.add(Restrictions.eq("userId", userId));
		
		List<CmUserInfo> list = ca.list();
		if(list!=null && list.size()==1){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 查询用户的信息
	 * @return
	 */
	public Pagination doQueryCmUser(String userName,int userType,long orgId,int userRole){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(CmUserInfo.class);
		BaseUser base = SysContexts.getCurrentOperator();
		if(StringUtils.isNotEmpty(userName)){
			ca.add(Restrictions.eq("userName", userName));
		}
		if(userType>0){
			ca.add(Restrictions.eq("userType", userType));	
		}
		if(orgId>0){
			ca.add(Restrictions.eq("orgId", orgId));	
		}
        if(userRole!=SysStaticDataEnum.ADMIN_ROLE.SUPP_ADMINISTRATOR){
        	ca.add(Restrictions.eq("tenantId", Long.valueOf(base.getTenantId())));
        	if(userRole==SysStaticDataEnum.ADMIN_ROLE.ORG_ADMIN_UPER){
        		ca.add(Restrictions.eq("orgId", base.getOrgId()));
        	}
		}
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		ca.addOrder(Order.desc("createTime"));
		
		//List<CmUserInfo>  userList = ca.list();
		IPage p = PageUtil.gridPage(ca);
		Pagination page = new Pagination(p);
		return page;
		
	}
	/**
	 *保存员工的信息
	 * @return
	 * @throws Exception 
	 */
	public String doSaveCmUserInfo(String userName,String loginAcct,String loginPwd,int userType,long orgId,long userId,Long tenantId) throws Exception{
		CmUserInfo cmUser; 
		Session session = SysContexts.getEntityManager();
		Criteria ca  = session.createCriteria(CmUserInfo.class);
		ca.add(Restrictions.eq("loginAcct", loginAcct));
		if(userId>0){
	    	   cmUser=(CmUserInfo)session.get(CmUserInfo.class, userId);
		}else{
			if(ca.list().size()>0){
				throw new BusinessException("该账号已被使用");	
			}
			cmUser= new CmUserInfo();
		}
		BaseUser base = SysContexts.getCurrentOperator();
		//cmUser.setCreateTime(new Date());
		cmUser.setLoginAcct(loginAcct);
		cmUser.setLoginPwd(K.j_s(loginPwd));
		cmUser.setOpId(base.getOperId());
		//TODO add by liyiye
//		cmUser.setOrgId(orgId);
		cmUser.setState(SysStaticDataEnum.STS.VALID);
		cmUser.setUserName(userName);
		cmUser.setCreateTime(new Date(System.currentTimeMillis()));
		cmUser.setUserType(userType);
//		if(tenantId!=null && tenantId>0){
//			cmUser.setTenantId(tenantId);
//			cmUser.setOrgId(tenantId);
//		}else{
//		    cmUser.setTenantId(Long.valueOf(base.getTenantId()));
//		    if(orgId>0){
//		    	cmUser.setOrgId(orgId);
//		    }else{
//		    	cmUser.setOrgId(Long.valueOf(base.getTenantId()));
//		    }
//		}
		session.saveOrUpdate(cmUser);
		if(userId<=0){
			if(tenantId!=null && tenantId>0){
				SysRoleOperRel operRel = new SysRoleOperRel();
				operRel.setCreateDate(new Date(System.currentTimeMillis()));
				operRel.setLastModifyDate(new Date(System.currentTimeMillis()));
				operRel.setLastModifyOperatorId(base.getUserId());
				operRel.setOperatorId(cmUser.getUserId());
				operRel.setRoleId(SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER);
				operRel.setState(SysStaticDataEnum.STS.VALID);
				session.saveOrUpdate(operRel);
			}
			 AcAccount account = new AcAccount();
			    account.setAccountPwd(K.j_s("888888"));
			    account.setObjType(2);
			    account.setBalance(0L);
			    account.setObjId(cmUser.getUserId());
			    account.setOpId(base.getUserId());
			    account.setCreateTime(new Date(System.currentTimeMillis()));
			    account.setAccountType(SysStaticDataEnum.ACCOUNT_TYPE.CASH_ACCOUNT);
			    account.setModPwdTime(new Date(System.currentTimeMillis()));
			  session.saveOrUpdate(account);
				  AcAccount plaAccount = new AcAccount();
				  plaAccount.setAccountPwd(K.j_s("888888"));
				  plaAccount.setObjType(2);
				  plaAccount.setBalance(0L);
				  plaAccount.setObjId(cmUser.getUserId());
				  plaAccount.setOpId(base.getUserId());
				  plaAccount.setCreateTime(new Date(System.currentTimeMillis()));
				  plaAccount.setAccountType(SysStaticDataEnum.ACCOUNT_TYPE.PLATFORM_ACCOUNT);
				  plaAccount.setModPwdTime(new Date(System.currentTimeMillis()));
			  session.saveOrUpdate(plaAccount);
				  AcAccount depAccount = new AcAccount();
				  depAccount.setAccountPwd(K.j_s("888888"));
				  depAccount.setObjType(2);
				  depAccount.setBalance(0L);
				  depAccount.setObjId(cmUser.getUserId());
				  depAccount.setOpId(base.getUserId());
				  depAccount.setCreateTime(new Date(System.currentTimeMillis()));
				  depAccount.setModPwdTime(new Date(System.currentTimeMillis()));
				  depAccount.setAccountType(SysStaticDataEnum.ACCOUNT_TYPE.DEPOSIT_ACCOUNT);
			  session.saveOrUpdate(depAccount);
			 /* Criteria elementCa = session.createCriteria(SysIndexElement.class);
			  elementCa.add(Restrictions.or(Restrictions.eq("label", "my-search"), Restrictions.or(Restrictions.eq("label", "my-Balance"), Restrictions.eq("label", "my-Shortcut"))));
			  if(elementCa.list().size()>0){
				  List<SysIndexElement> indexList = new ArrayList<SysIndexElement>();
				  indexList=elementCa.list();
				  for(SysIndexElement sysIndexElement:indexList){
					  SysIndexElementRel elementRel= new SysIndexElementRel();
					  elementRel.setElementId(sysIndexElement.getId());
					  elementRel.setUserId(cmUser.getUserId());
					  elementRel.setSeq(sysIndexElement.getId());
					  elementRel.setState(true);
				  }
			  }*/
		}
		return "Y";
	}
	
	
	
	/**
	 * 查询司机的信息
	 * @return
	 */
	public Pagination doQueryCmDriver(String name,String bill,long orgId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(CmDriverInfo.class);
		BaseUser base = SysContexts.getCurrentOperator();
		CmUserInfoTF cmUserInfoTF = (CmUserInfoTF)SysContexts.getBean("cmUserInfoTF");
		int userRole = cmUserInfoTF.getUserRole();
		if(StringUtils.isNotEmpty(name)){
			ca.add(Restrictions.like("name", "%"+name+"%"));
		}
		if(StringUtils.isNotEmpty(bill)){
			ca.add(Restrictions.eq("bill", bill));	
		}
		if(orgId > 0 ){
			ca.add(Restrictions.eq("orgId", orgId));	
		}
//		if(userRole==SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER || userRole==SysStaticDataEnum.ADMIN_ROLE.ORG_ADMIN_UPER){
			/*ca.add(Restrictions.eq("tenantId", Long.valueOf(base.getTenantId())));
			if(userRole==SysStaticDataEnum.ADMIN_ROLE.ORG_ADMIN_UPER){
				ca.add(Restrictions.eq("orgId", base.getOrgId()));		
			}*/
//		}
		if(orgId <= 0 ){
			Organization org = OraganizationCacheDataUtil.getOrganization(base.getOrgId());
			long parentOrgId = org.getParentOrgId();
			if(parentOrgId == EnumConsts.ROOT_ORG_ID){
				ca.add(Restrictions.eq("tenantId", base.getTenantId()));
			}else{
				ca.add(Restrictions.eq("orgId", base.getOrgId()));
			}
		}
		//ca.add(Restrictions.eq("tenantId", Long.valueOf(base.getTenantId())));
		ca.add(Restrictions.eq("status", SysStaticDataEnum.STS.VALID));
		ca.addOrder(Order.desc("createDate"));
		//List<CmUserInfo>  userList = ca.list();
		IPage p = PageUtil.gridPage(ca);
		Pagination page = new Pagination(p);
		return page;
	}
	
	/**
	 *保存司机的信息
	 * @return
	 */
	public CmDriverInfo doSaveCmDriverInfo(CmDriverInfo cmDriverInfo){
		Session session = SysContexts.getEntityManager();
		BaseUser base = SysContexts.getCurrentOperator();
		if(cmDriverInfo.getId()<=0){
			cmDriverInfo.setTenantId(Long.valueOf(base.getTenantId()));
			cmDriverInfo.setCurrentOrgId(base.getOrgId());
			if(cmDriverInfo.getOrgId() < 0){
				cmDriverInfo.setOrgId(base.getOrgId());
			}
			cmDriverInfo.setCreateDate(new Date(System.currentTimeMillis()));
			cmDriverInfo.setOpId(base.getUserId());
			cmDriverInfo.setStatus(SysStaticDataEnum.STS.VALID);
			cmDriverInfo.setOpName(base.getUserName());
			cmDriverInfo.setCreatorId(base.getUserId());
			cmDriverInfo.setCreatorName(base.getUserName());
		}else{
			if(cmDriverInfo.getOrgId() < 0){
				cmDriverInfo.setOrgId(base.getOrgId());
			}
		}
		
		session.saveOrUpdate(cmDriverInfo);
		return cmDriverInfo;
	}
	/**
	 *保存车辆的信息
	 * @return
	 */
	public String doSaveCmVehicleInfo(CmVehicleInfo cmVehicleInfo,String mainDriverName,String bill){
		Session session = SysContexts.getEntityManager();
		BaseUser base = SysContexts.getCurrentOperator();
		if(cmVehicleInfo.getVehicleId()<=0){
			if(StringUtils.isNotEmpty(cmVehicleInfo.getPlateNumber())){
				//throw new BusinessException("驾驶证不能为空");
				Criteria ca  = session.createCriteria(CmVehicleInfo.class);
				ca.add(Restrictions.eq("plateNumber", cmVehicleInfo.getPlateNumber()));
				ca.add(Restrictions.eq("orgId", cmVehicleInfo.getOrgId()));
				ca.add(Restrictions.eq("status", SysStaticDataEnum.STS.VALID));
				if(ca.list().size()>0){
					throw new BusinessException("已添加有该车辆的信息");
				}
			}
			cmVehicleInfo.setCreateDate(new Date(System.currentTimeMillis()));
			cmVehicleInfo.setTenantId(Long.valueOf(base.getTenantId()));
			cmVehicleInfo.setVehicleState(SysStaticDataEnum.VEH_STATE.FREE);
			cmVehicleInfo.setType(0);
			cmVehicleInfo.setStatus(SysStaticDataEnum.STS.VALID);
			//cmVehicleInfo.setOrgId(cmVehicleInfo.getO);
			cmVehicleInfo.setOpId(base.getUserId());
			//cmVehicleInfo.setMainDriverPhone(bill);
			cmVehicleInfo.setCurrentOrgId(base.getOrgId());
		}
		//添加司机
			if(StringUtils.isNotEmpty(mainDriverName)){
				Criteria driverCa = session.createCriteria(CmDriverInfo.class);
				driverCa.add(Restrictions.eq("bill", bill));
				driverCa.add(Restrictions.eq("orgId",base.getOrgId()));
				driverCa.add(Restrictions.eq("tenantId",Long.valueOf(base.getTenantId())));
				driverCa.add(Restrictions.eq("status", SysStaticDataEnum.STS.VALID));
				List lists = driverCa.list();
				if(lists.size()==0){
					CmDriverInfo driverInfo = new CmDriverInfo();
					driverInfo.setName(mainDriverName);
					driverInfo.setBill(bill);
					driverInfo.setTelephone(bill);
					//driverInfo.setIdentityCard("");
					driverInfo.setIdentityCardNo("");
					driverInfo.setDrivingLicense("");
					driverInfo.setBankAccount("");
					driverInfo.setBankName("");
					driverInfo.setCertificate("");
					driverInfo.setTenantId(Long.valueOf(base.getTenantId()));
					driverInfo.setCurrentOrgId(base.getOrgId());
					driverInfo.setOrgId(base.getOrgId());
					driverInfo.setCreateDate(new Date(System.currentTimeMillis()));
					driverInfo.setOpId(base.getUserId());
					driverInfo.setStatus(SysStaticDataEnum.STS.VALID);
					session.save(driverInfo);
					cmVehicleInfo.setMainDriverId(driverInfo.getId());
				}else{
					//如果存在司机要把司机添加上去
					CmDriverInfo driverInfo = (CmDriverInfo) lists.get(0);
					cmVehicleInfo.setMainDriverId(driverInfo.getId());
				}
		}
		session.saveOrUpdate(cmVehicleInfo);
		return "Y";
	}
	/**
	 * 新增车辆添加司机
	 * @param name
	 */
	public Long saveVehicleDriver(String name,String bill){
		Session session = SysContexts.getEntityManager();
		if(StringUtils.isNotEmpty(name)){
			Criteria driverCa = session.createCriteria(CmDriverInfo.class);
			driverCa.add(Restrictions.eq("name", name));
			driverCa.add(Restrictions.eq("status", SysStaticDataEnum.STS.VALID));
			if(driverCa.list().size()==0){
				CmDriverInfo driverInfo = new CmDriverInfo();
				driverInfo.setName(name);
				driverInfo.setBill(bill);
				driverInfo = doSaveCmDriverInfo(driverInfo);
				return driverInfo.getId();
			}	
		}
		return null;
	}
	
	
	
	/**
	 * 检索驾驶员信息
	 * @param userName  姓名
	 * @return list  返回值
	 *         OBJECT[0] 姓名
	 *         OBJECT[1] 用户ID id
	 * @throws Exception
	 */
	public List searchUser(String userName) throws Exception{
		/*if(userName==null || userName.equals("")){
			throw new  BusinessException ("请输入姓名!");
		}*/
		Session session = SysContexts.getEntityManager(true);
		BaseUser base = SysContexts.getCurrentOperator();
		Map queryMap = new HashMap();
		Criteria query  = session.createCriteria(CmDriverInfo.class);
		query.add(Restrictions.like("name","%"+userName+"%"));
		query.add(Restrictions.eq("orgId", base.getOrgId()));
		query.add(Restrictions.eq("tenantId", Long.valueOf(base.getTenantId())));
		query.add(Restrictions.eq("status", SysStaticDataEnum.STS.VALID));
		List<CmDriverInfo> list = query.list();
		List<QueryVehicleDriverOut> outList = new ArrayList();
		QueryVehicleDriverOut out = null;
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				CmDriverInfo driver = list.get(i);
				out=new QueryVehicleDriverOut();
				out.setName(driver.getName());
				out.setId(driver.getId());
				out.setBill(driver.getBill());
				outList.add(out);
			}
		}
		return outList;
	}
	
	
	
	
	/**
	 * 用户查询(查询公司管理员)
	 * @param roleId
	 * @return
	 */
	public Pagination queryGroupUrl(String userName,int userType,long orgId,int roleId){
		Session session  = SysContexts.getEntityManager(true);
		BaseUser base  = SysContexts.getCurrentOperator();
		Criteria ca  = session.createCriteria(SysRoleOperRel.class);
		ca.add(Restrictions.eq("roleId",SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER));
		ca.add(Restrictions.eq("state",SysStaticDataEnum.STS.VALID));
		if(ca.list().size()>0){
			Criteria userCa  = session.createCriteria(CmUserInfo.class);
			List<SysRoleOperRel> operList = ca.list();
			List<Long> objList = new ArrayList<Long>();
			for(SysRoleOperRel sysRel:operList){
				objList.add(sysRel.getOperatorId());
			}
			if(objList.size()>0){
				userCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
				//userCa.add(Restrictions.eq("tenantId", Long.valueOf(base.getTenantId()+"")));
				userCa.add(Restrictions.in("userId", objList));
				if(StringUtils.isNotEmpty(userName)){
					userCa.add(Restrictions.eq("userName", userName));
				}
				if(userType>0){
					userCa.add(Restrictions.eq("userType", userType));	
				}
				if(orgId>0){
					userCa.add(Restrictions.eq("orgId", orgId));	
				}
			}
			  userCa.addOrder(Order.desc("createTime"));
			    IPage p = PageUtil.gridPage(userCa);
		 		Pagination page = new Pagination(p);
			return page;
		}else{
			  IPage p = PageUtil.gridPage(ca);
		 		Pagination page = new Pagination(p);
		 		return  page;
		}
		//
	}
	public String doDelCmUser(List userIds){
		 Session session  = SysContexts.getEntityManager();
		 List<Long> usIdList = new ArrayList<Long>();
		 for(int i=0;i<userIds.size();i++){
			 usIdList.add(Long.valueOf(userIds.get(i)+""));	
			}
		 Criteria userCa = session.createCriteria(CmUserInfo.class);
		 userCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		 userCa.add(Restrictions.in("userId", usIdList));
		 List<CmUserInfo> cmUserList  =userCa.list();
		 if(cmUserList!=null && cmUserList.size()>0){
			 for(CmUserInfo cmUserInfo:cmUserList){
				 cmUserInfo.setState(SysStaticDataEnum.STS.NULLITY);
				 session.update(cmUserInfo);
			 }
		 }
		 
		 Criteria operCa = session.createCriteria(SysRoleOperRel.class);
		 operCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		 operCa.add(Restrictions.in("operatorId", usIdList));
		 List<SysRoleOperRel> operList = operCa.list();
		 if(operList!=null && operList.size()>0){
			 for(SysRoleOperRel operRel : operList){
				 session.delete(operRel);
			 }
		 }
		return "Y";
	}
	/**
	 * 删除车辆信息，只做逻辑删除
	 * @param vehicleIds
	 * @return
	 */
	public  String doDelVehicle(List vehicleIds){
		Session session  = SysContexts.getEntityManager();
		 List<Long> vehicleIdList = new ArrayList<Long>();
		 if(vehicleIds!=null && vehicleIds.size()>0){
			 for(int i=0;i<vehicleIds.size();i++){
				 long vehicleId = Long.valueOf(vehicleIds.get(i)+"");
				 CmVehicleInfo cmVehicleInfo = (CmVehicleInfo)session.get(CmVehicleInfo.class,vehicleId);
				 if(cmVehicleInfo!=null){
					 Query query = session.createSQLQuery("SELECT COUNT(1) FROM  ord_depart_info WHERE vehicle_id="+vehicleId);
					 BigInteger result = (BigInteger) query.uniqueResult();
					 if(result.intValue()>0){
						 throw new BusinessException("车辆信息在配载信息使用中，不允许删除!");
					 }
					 Query query1 = session.createSQLQuery("SELECT COUNT(1) FROM  ord_transfer_info WHERE vehicle_id="+vehicleId);
					 BigInteger result1 = (BigInteger) query1.uniqueResult();
					 if(result1.intValue()>0){
						 throw new BusinessException("车辆信息在中转信息使用中，不允许删除!");
					 }
					 Query query2 = session.createSQLQuery("SELECT COUNT(1) FROM  ord_delivery_goods WHERE vehicle_id="+vehicleId);
					 BigInteger result2 = (BigInteger) query2.uniqueResult();
					 if(result2.intValue()>0){
						 throw new BusinessException("车辆信息在配送上门信息使用中，不允许删除!");
					 }
						 //cmVehicleInfo.setStatus(SysStaticDataEnum.STS.NULLITY);
					 session.delete(cmVehicleInfo);
				 }
			 }
			 
		 }
		return "Y";
	}
	/**
	 * 删除信息，只做逻辑删除
	 * @param vehicleIds
	 * @return
	 */
	public  String doDelDriver(List driverIds){
		Session session  = SysContexts.getEntityManager();
		 List<Long> vehicleIdList = new ArrayList<Long>();
		 if(driverIds!=null && driverIds.size()>0){
			 for(int i=0;i<driverIds.size();i++){
				 long id = Long.valueOf(driverIds.get(i)+"");
				 CmDriverInfo cmDriverInfo = (CmDriverInfo)session.get(CmDriverInfo.class,id);
				 if(cmDriverInfo!=null){
					 checkVehicle(id);
					 Query query = session.createSQLQuery("SELECT COUNT(1) FROM  ord_depart_info WHERE DRIVER_ID="+id);
					 BigInteger result = (BigInteger) query.uniqueResult();
					 if(result.intValue()>0){
						 throw new BusinessException("司机信息在配载信息使用中，不允许删除!");
					 }
					 Query query1 = session.createSQLQuery("SELECT COUNT(1) FROM  ord_transfer_info WHERE DRIVER_ID="+id);
					 BigInteger result1 = (BigInteger) query1.uniqueResult();
					 if(result1.intValue()>0){
						 throw new BusinessException("司机信息在中转信息使用中，不允许删除!");
					 }
					 Query query2 = session.createSQLQuery("SELECT COUNT(1) FROM  ord_delivery_goods WHERE DRIVER_ID="+id);
					 BigInteger result2 = (BigInteger) query2.uniqueResult();
					 if(result2.intValue()>0){
						 throw new BusinessException("司机信息在配送上门信息使用中，不允许删除!");
					 }
					// cmDriverInfo.setStatus(SysStaticDataEnum.STS.NULLITY);
					 session.delete(cmDriverInfo);
				 }
			 }
			 
		 }
		return "Y";
	}
	/**
	 * 查询收货人、发货人列表
	 * @param inParamR
	 * @return
	 */
	public Pagination doQueryCustomer(Map<String, String> inParam){
		Session session = SysContexts.getEntityManager(true);
		BaseUser base = SysContexts.getCurrentOperator();
		Long orgId = DataFormat.getLongKey(inParam, "orgId");
		int userRole = DataFormat.getIntKey(inParam, "userRole");
		String name = DataFormat.getStringKey(inParam, "name");
		String telephone = DataFormat.getStringKey(inParam, "telephone");
		String bill = DataFormat.getStringKey(inParam, "bill");
		Integer type = DataFormat.getIntKey(inParam, "type");
		Criteria  ca = session.createCriteria(CmCustomer.class);
		if(orgId!=null && orgId>0){
			ca.add(Restrictions.eq("orgId", orgId));
		}
		if(StringUtils.isNotEmpty(name)){
			ca.add(Restrictions.like("name", "%"+name+"%"));
		}
		if(StringUtils.isNotEmpty(telephone)){
			ca.add(Restrictions.like("telephone", "%"+telephone+"%"));
		}
		if(StringUtils.isNotEmpty(bill)){
			ca.add(Restrictions.like("bill", "%"+bill+"%"));
		}
		if(type!=null && type>0){
			ca.add(Restrictions.eq("type", type));
		}
		if(userRole!=SysStaticDataEnum.ADMIN_ROLE.SUPP_ADMINISTRATOR){
        	ca.add(Restrictions.eq("tenantId", Long.valueOf(base.getTenantId())));
        	if(userRole==SysStaticDataEnum.ADMIN_ROLE.ORG_ADMIN_UPER){
        		ca.add(Restrictions.eq("orgId", base.getOrgId()));
        	}
		}
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		ca.addOrder(Order.desc("createDate"));
        IPage p = PageUtil.gridPage(ca);
 		Pagination page = new Pagination(p);
		return page;
	}
	
	
	
	/**
	 * 
	 * @param roleIds
	 * @return
	 */
	public String delCmCustomer(List<Integer> ids,int type){
		 Session session  = SysContexts.getEntityManager();
		 List<Long> roList = new ArrayList<Long>();
		// List entityIds= new ArrayList();
		if(ids.size()>0){
			for(int i=0;i<ids.size();i++){
				roList.add(Long.valueOf(ids.get(i)+""));	
			}
			Criteria ca  = session.createCriteria(CmCustomer.class);
			ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
			ca.add(Restrictions.eq("type", type));
			ca.add(Restrictions.in("id", roList));
			if(ca.list().size()>0){
				List<CmCustomer> roleList =  ca.list();
				for(CmCustomer cmCustomer:roleList){
					cmCustomer.setState(SysStaticDataEnum.STS.NULLITY);
					session.update(cmCustomer);
				}
			}
		
		}
		return "Y"; 
	}
	
	
	public void checkVehicle(long id){
		Session session = SysContexts.getEntityManager(true);
		if(id>0){
			Criteria ca  = session.createCriteria(CmVehicleInfo.class);
			ca.add(Restrictions.or(Restrictions.eq("mainDriverId", id), Restrictions.eq("secondDriverId", id)));
			ca.add(Restrictions.eq("status", SysStaticDataEnum.STS.VALID));
			if(ca.list().size()>0){
				List<CmVehicleInfo> vehicleList = new ArrayList<CmVehicleInfo>();
				vehicleList=ca.list();
				for(CmVehicleInfo cmVehicleInfo:vehicleList){
					if(cmVehicleInfo.getVehicleState()!=1){
						throw new BusinessException("司机工作中，不能删除或修改司机信息");	
					}
				}
			}
		}
		
	}
	/**
	 * 保存、修改收货人
	 * @param cmCustomer
	 * @return
	 */
	public String saveOrUpdateCustomer(CmCustomer cmCustomer){
	BaseUser baseUser = SysContexts.getCurrentOperator();
	Session session = SysContexts.getEntityManager();
	
	if(cmCustomer.getId()<=0){
		//cmCustomer.setSigningType(SysStaticDataEnum.SIGNING_TYPE.SIGNING_NO);
		cmCustomer.setCreateDate(new Date(System.currentTimeMillis()));
		if(cmCustomer.getTenantId()!=null && cmCustomer.getTenantId() > 0){
			cmCustomer.setTenantId(Long.valueOf(cmCustomer.getTenantId()));
			cmCustomer.setSigningType(SysStaticDataEnum.SIGNING_TYPE.SIGNING_IS);
		}else{
			//这个字段只是存商家的信息
//			cmCustomer.setTenantId(Long.valueOf(baseUser.getTenantId()));
			cmCustomer.setSigningType(SysStaticDataEnum.SIGNING_TYPE.SIGNING_NO);
		}
		cmCustomer.setParentId(0l);
		cmCustomer.setState(SysStaticDataEnum.STS.VALID);
	}
	
	cmCustomer.setZxTenantId(baseUser.getTenantId());
	
	session.saveOrUpdate(cmCustomer);
	return null;
	}
	
	public CmCustomer getCmCustomer(long tenantId){
		Session session = SysContexts.getEntityManager();
		Criteria ca = session.createCriteria(CmCustomer.class);
		ca.add(Restrictions.eq("tenantId", tenantId));
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		long parentId = SysStaticDataEnum.PARENT_ID.PARENT;
		ca.add(Restrictions.eq("parentId", parentId));
		List<CmCustomer> cmCustomer = ca.list();
		if(cmCustomer !=null && cmCustomer.size() == 1){
			return cmCustomer.get(0);
		}
		return null;
	}
	/**
	 * 根据账号 和 租户ID 查询用户
	 * @param loginAcct
	 * @return
	 * @throws Exception
	 */
	public CmUserInfo queryUserByAcctNew(String loginAcct, long tenantId) throws Exception {
		Session session = SysContexts.getEntityManager(true);
		if (StringUtils.isEmpty(loginAcct)) {
			return null;
		}
		Criteria ca = session.createCriteria(CmUserInfo.class);
		Integer[] login = new Integer[]{1,3};
		ca.add(Restrictions.eq("loginAcct", loginAcct));
		//ca.add(Restrictions.eq("tenantId", tenantId));
		ca.add(Restrictions.eq("state",SysStaticDataEnum.STS.VALID ));
		ca.add(Restrictions.in("loginType", login));
		List<CmUserInfo> list = ca.list();
		if(list!=null && list.size()==1){
			return list.get(0);
		}else if(list.size()>1){
			log.error("账号："+loginAcct+"数据异常");
		}
		return null;
	}
	/**
	 * 查询司机的信息
	 * @return
	 */
	public Pagination doQueryCmDriver(String name,String bill,long orgId,String telephone,String identityCardNo,String bankName,String bankAccount){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(CmDriverInfo.class);
		BaseUser base = SysContexts.getCurrentOperator();
		CmUserInfoTF cmUserInfoTF = (CmUserInfoTF)SysContexts.getBean("cmUserInfoTF");
		int userRole = cmUserInfoTF.getUserRole();
		if(StringUtils.isNotEmpty(name)){
			ca.add(Restrictions.like("name", "%"+name+"%"));
		}
		if(StringUtils.isNotEmpty(bill)){
			ca.add(Restrictions.eq("bill", bill));	
		}
		if(orgId > 0 ){
			ca.add(Restrictions.eq("orgId", orgId));	
		}
		if(orgId <= 0 ){
			Organization org = OraganizationCacheDataUtil.getOrganization(base.getOrgId());
			long parentOrgId = org.getParentOrgId();
			if(parentOrgId == EnumConsts.ROOT_ORG_ID){
				ca.add(Restrictions.eq("tenantId", base.getTenantId()));
			}else{
				ca.add(Restrictions.eq("orgId", base.getOrgId()));
			}
		}
		if(StringUtils.isNotEmpty(telephone)){
			ca.add(Restrictions.eq("telephone",telephone));
		}
		if(StringUtils.isNotEmpty(identityCardNo)){
			ca.add(Restrictions.eq("identityCardNo",identityCardNo));
		}
		if(StringUtils.isNotEmpty(bankName)){
			ca.add(Restrictions.eq("bankName", bankName));	
		}
		if(StringUtils.isNotBlank(bankAccount)){
			ca.add(Restrictions.eq("bankAccount", bankAccount));
		}
		ca.add(Restrictions.eq("status", SysStaticDataEnum.STS.VALID));
		ca.addOrder(Order.desc("createDate"));
		//List<CmUserInfo>  userList = ca.list();
		IPage p = PageUtil.gridPage(ca);
		Pagination page = new Pagination(p);
		return page;
	}
	
}
