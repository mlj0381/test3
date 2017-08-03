package com.wo56.business.cm.intf;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.framework.components.citys.City;
import com.framework.components.citys.Province;
import com.framework.core.SysContexts;
import com.framework.core.encrypt.K;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.ac.impl.AcWalletRelSV;
import com.wo56.business.ac.vo.AcWalletRel;
import com.wo56.business.cm.impl.CmCustomerSV;
import com.wo56.business.cm.impl.CmUserInfoPullSV;
import com.wo56.business.cm.impl.CmUserInfoYunQiSV;
import com.wo56.business.cm.impl.CmUserSV;
import com.wo56.business.cm.impl.ICmUserInfoIntf;
import com.wo56.business.cm.vo.CmUserAreaRel;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserInfoPull;
import com.wo56.business.cm.vo.CmUserOrgRel;
import com.wo56.business.cm.vo.in.UserAreaParam;
import com.wo56.business.cm.vo.out.CmUserInfoOut;
import com.wo56.business.cm.vo.out.CmUserInfoOutParam;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.sys.intf.SysRoleOperRelTF;
import com.wo56.business.sys.vo.SysRole;
import com.wo56.business.sys.vo.SysRoleOperRel;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.LoginConst;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.EncryPwdUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;
import com.wo56.common.utils.SmsYQUtil;
import com.wo56.common.utils.SysRoleCacheUtil;
import com.wo56.business.statistic.vo.StaticDataSet;
import java.math.BigInteger;
/**
 * @author zjy
 * 
 * 
 * */
public class CmUserInfoTF implements ICmUserInfoIntf{
	private static final Log log = LogFactory.getLog(CmUserInfoTF.class);

	private CmUserSV cmUserSv;
	
//	private CmServAreaInstTF cmServAreaInstTF;
//	
//	public void setCmServAreaInstTF(CmServAreaInstTF cmServAreaInstTF) {
//		this.cmServAreaInstTF = cmServAreaInstTF;
//	}
	public CmUserSV getCmUserSv() {
		return cmUserSv;
	}
	public void setCmUserSv(CmUserSV cmUserSv) {
		this.cmUserSv = cmUserSv;
	}
	
	public IPage<Object[]> queryCmSfUserFriendsPage(Map<String,Object> inParam)throws Exception{
		long sfUserId = DataFormat.getLongKey(inParam, "sfUserId");
		if(sfUserId<0){
			throw new BusinessException("输入用户ID不能为空");
		}
		IPage<Object[]>  userInfos = cmUserSv.queryUserList(inParam);
		return userInfos;
	}
	
	/**
	 * 接口编码：110000
	 * 查询网点的开单员/业务员
	 * @param inParam
	 * 入参：userType 用户类型  1 操作员 2 业务员 3 开单员
	 * 		tenantCode 租户id
	 * 
	 * */
	public List doQueryOrgUser(Map<String, String> inParam) throws Exception{
//		Session session = SysContexts.getEntityManager(true);
//		String tenantCode = DataFormat.getStringKey(inParam, "tenantCode");
//		if(StringUtils.isEmpty(tenantCode)){
//			throw new BusinessException("租户id不能为空");
//		}
//		Query ca = session.createQuery("from CmUserInfo user ,Organization org where user.orgCode=org.orgCode and user.tenantCode=:tenantCode and user.userType=:userType");
//		ca.setParameter("tenantCode", tenantCode);
//		ca.setParameter("userType", 1);
//
//		List<Object[]> objs = ca.list();
//		
//        CmUserInfoOutParam outParam=null;
        List<CmUserInfoOutParam> outList = new ArrayList<CmUserInfoOutParam>();
//        if(objs.size()>0){
//        	for (Object[] obj : objs) {
//        		CmUserInfo cmUserInfo =(CmUserInfo)obj[0];
//        		Organization org =(Organization)obj[1];
//        		outParam=new CmUserInfoOutParam();
//        		outParam.setUserType(cmUserInfo.getUserType());
//        		outParam.setUserName(cmUserInfo.getUserName());
//        		outParam.setUserId(cmUserInfo.getUserId());
//        		outParam.setLoginAcct(cmUserInfo.getLoginAcct());
//        		outParam.setOrgName(org.getOrgName());
//        		outList.add(outParam);
//			}
//        }
		return outList;
	}
	/**
	 * lyh
	 * 
	 * 根据登录的账号查询用户密码
	 * @param inParam
	 * @return
	 *      
	 * @throws Exception
	 */
	public CmUserInfo queryUserPwd(Map<String, String> inParam)throws Exception{
		
		String loginAcct=DataFormat.getStringKey(inParam, "loginAcct");
		Long tenantId=DataFormat.getLongKey(inParam, "tenantId");
		String userType=DataFormat.getStringKey(inParam, "userType");//用户类型
		String loginType=DataFormat.getStringKey(inParam, "loginType");//登录类型
		String orgCode=DataFormat.getStringKey(inParam, LoginConst.BaseUserAttr.ORG_CODE);

		Session session = SysContexts.getEntityManager();
		
		StringBuffer hql =new StringBuffer();
		
		hql.append("select u from CmUserInfo u,CmUserOrgRel r where u.userId=r.userId and u.state=1 ");
		
		if(StringUtils.isEmpty(loginAcct)){
			throw new BusinessException("未传入账户");
		}else{
			hql.append(" and u.loginAcct=:loginAcct");
		}
		
		if(tenantId!=null && tenantId>0){
			hql.append(" and r.tenantId=:tenantId");
		}
		
		if(StringUtils.isNotEmpty(userType)){
			String[] userTypes=userType.split(",");
			if(userTypes.length>1){
				
				hql.append(" and u.userType in (:userType)");
			}else{
				hql.append(" and u.userType =:userType");
			}
		}
		
		if(StringUtils.isNotEmpty(loginType)){
			String[] loginTypes=loginType.split(",");
			if(loginTypes.length>1){
				hql.append(" and u.loginType in (:loginType)");
			}else{
				hql.append(" and u.loginType =:loginType)");
			}
		}
		
		Query query=session.createQuery(hql.toString());
		
		if(StringUtils.isEmpty(loginAcct)){
			throw new BusinessException("未传入账户");
		}else{
			query.setParameter("loginAcct", loginAcct);
		}
		
		if(tenantId!=null && tenantId>0){
			query.setParameter("tenantId", tenantId);
		}
		
		if(StringUtils.isNotEmpty(userType)){
			String[] userTypes=userType.split(",");
			if(userTypes.length>1){
				Integer[] userTypeInt=new Integer[userTypes.length];
				for(int i=0;i<userTypes.length;i++){
					userTypeInt[i]=Integer.valueOf(userTypes[i]);
				}
				query.setParameterList("userType", userTypeInt);
			}else{
				query.setParameter("userType", Integer.valueOf(userType));
			}
		}
		
		if(StringUtils.isNotEmpty(loginType)){
			String[] loginTypes=loginType.split(",");
			if(loginTypes.length>1){
				Integer[] loginTypeInt=new Integer[loginTypes.length];
				for(int i=0;i<loginTypeInt.length;i++){
					loginTypeInt[i]=Integer.valueOf(loginTypes[i]);
				}
				query.setParameterList("loginType", loginTypeInt);
			}else{
				query.setParameter("loginType", Integer.valueOf(loginType));

			}
		}
		
		List acctList = query.list();
		if(acctList!=null&&acctList.size()>0){
			return (CmUserInfo)acctList.get(0);
		}
		return null;
	}
	
	/**
	 * app登录的判断逻辑
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public CmUserInfo queryAppUserPwd(Map<String, String> inParam)throws Exception{
		
		String loginAcct=DataFormat.getStringKey(inParam, "loginAcct");
		String tenantCode=DataFormat.getStringKey(inParam, "tenantCode");
		String userType=DataFormat.getStringKey(inParam, "userType");//用户类型
		String loginType=DataFormat.getStringKey(inParam, "loginType");//登录类型
		String orgCode=DataFormat.getStringKey(inParam, LoginConst.BaseUserAttr.ORG_CODE);

		Session session = SysContexts.getEntityManager();
		Criteria ca = session.createCriteria(CmUserInfo.class);
		
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		
		if(StringUtils.isEmpty(loginAcct)){
			throw new BusinessException("未传入账户");
		}else{
			ca.add(Restrictions.eq("loginAcct", loginAcct));
		}
		if(StringUtils.isNotEmpty(tenantCode)){
			ca.add(Restrictions.eq("tenantCode", tenantCode));
		}
		if(StringUtils.isNotEmpty(orgCode)){
			ca.add(Restrictions.eq("orgCode", orgCode));
		}
		if(StringUtils.isNotEmpty(userType)){
			String[] userTypes=userType.split(",");
			if(userTypes.length>1){
				Integer[] userTypeInt=new Integer[userTypes.length];
				for(int i=0;i<userTypes.length;i++){
					userTypeInt[i]=Integer.valueOf(userTypes[i]);
				}
				ca.add(Restrictions.in("userType", userTypeInt));
			}else{
				ca.add(Restrictions.eq("userType", Integer.valueOf(userType)));
			}
		}
		
		if(StringUtils.isNotEmpty(loginType)){
			String[] loginTypes=loginType.split(",");
			if(loginTypes.length>1){
				Integer[] loginTypeInt=new Integer[loginTypes.length];
				for(int i=0;i<loginTypeInt.length;i++){
					loginTypeInt[i]=Integer.valueOf(loginTypes[i]);
				}
				ca.add(Restrictions.in("loginType", loginTypeInt));
			}else{
				ca.add(Restrictions.eq("loginType", Integer.valueOf(loginType)));
			}
		}
		
		
		
		List acctList = ca.list();
		if(acctList!=null&&acctList.size()>0){
			return (CmUserInfo)acctList.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * @param inParam
	 * 			key:terantCode 公司编码
	 * @return
	 * @throws Exception
	 */
	public List<CmUserInfo> queryLoginUser(Map<String, String> inParam)throws Exception{
		String tenantCode = DataFormat.getStringKey(inParam, "terantCode");
		if(StringUtils.isEmpty(tenantCode)){
			throw new BusinessException("公司编码不能为空");
		}
		List CmUserInfos = SysContexts.getEntityManager(true).createCriteria(CmUserInfo.class).add(Restrictions.eq("tenantCode", tenantCode)).list();
		return CmUserInfos;
	}
	
	/**
	 * 根据用户ID、类型查询
	 * @param userId 
	 * 		  userType
	 * @return CmUserInfo
	 * @throws Exception
	 */
	public CmUserInfo getUserInfo(long userId,int userType)throws Exception{
		if(userId<0){
			return null;
		}
		return cmUserSv.getUserInfo(userId, userType);
	}
	/**
	 * 用户信息更新
	 * @param 
	 *     inParam Map<String , Object> 
	 *     		key:userType
	 *     		key:isLogin
	 *     		key:userPhone
	 *   		key:updateDate
	 */
	public void doUpdateCmUserInfo(Map<String , Object> inParam)throws Exception{
		int userType = DataFormat.getIntKey(inParam, "userType");
		if(userType<0){
			throw new BusinessException("未传入用户类型");
		}
		BaseUser user = SysContexts.getCurrentOperator();
		CmUserInfo cmUserInfo = cmUserSv.getUserInfo(user.getUserId(), userType);
		int isLogin = DataFormat.getIntKey(inParam, "isLogin");
		cmUserSv.doUpdateCmUserInfo(cmUserInfo);
	}
	/**
	 * APP注册服务
	 * 新用户组织默认挂在公司
	 * @param inParam 
	 *  key:billId 手机号码  
     *	key:passWord 密码
	 * @throws Exception
	 */
	public CmUserInfo regAppUserInfo(Map<String, Object> inParam)throws Exception{
		String billId = DataFormat.getStringKey(inParam, "billId");
		String passWord = DataFormat.getStringKey(inParam, "password");
		

		if(StringUtils.isEmpty(billId)){
			throw new BusinessException("未传入手机号码");
		}
		if(StringUtils.isEmpty(passWord)){
			throw new BusinessException("未传入登录密码");
		}
		
		Map<String ,String> checkParam = new HashMap<String ,String>();
		checkParam.put("loginAcct", billId);
		if(checkUserPhoneIfRegByType(billId,SysStaticDataEnum.USER_TYPE.MASTER )!=null){
			throw new BusinessException("您所使用号码已注册");
		}
		//解密 
		passWord=EncryPwdUtil.pwdDecryption(passWord);
		//加密
		passWord=K.j_s(passWord);
		CmUserInfo cmUserInfo = new CmUserInfo();
		cmUserInfo.setLoginAcct(billId);
		cmUserInfo.setLoginPwd(passWord);
		cmUserInfo.setUserName(billId);
		
		cmUserInfo.setCreateTime(new Date());
		cmUserInfo.setState(SysStaticDataEnum.USER_STATE.USER_STATE_VAILDATE);
		cmUserInfo.setLoginType(SysStaticDataEnum.LOGIN_TYPE.APP);
		cmUserInfo.setUserType(SysStaticDataEnum.USER_TYPE.MASTER);
		cmUserSv.doSaveCmUserInfo(cmUserInfo);
		
		HashMap<String , Object> inSfParam = new HashMap();
		inSfParam.put("phone", billId);
		inSfParam.put("sfUserId", cmUserInfo.getUserId());
		inSfParam.put("auditState", SysStaticDataEnum.ADUIT_TYPE.SF_USER_UN_AUDIT );
		
		//CmSfUserInfo sfuser = cmSfUserTF.saveSfUser(inSfParam);
		
		
		return cmUserInfo;
	}
	/**
	 * lyh
	 * 校验号码是否已注册
	 * @param inParam
	 * 			key:loginAcct
	 * @throws Exception
	 * @return true 已注册
	 * 		   false 未注册
	 */
	public boolean checkUserPhoneIfReg(Map<String, String> inParam)throws Exception{
		String loginAcct = DataFormat.getStringKey(inParam, "loginAcct");
		if(StringUtils.isEmpty(loginAcct)){
			throw new BusinessException("未传入手机号码");
		}
		CmUserInfo cmUser = checkUserPhoneIfReg(loginAcct);
		if(cmUser!=null){
			return true;
		}
		return false;
	}
	
	/**
	 * lyh
	 * 校验号码是否已注册
	 * @param inParam
	 * 			key:loginAcct
	 * @throws Exception
	 * @return true 已注册
	 * 		   false 未注册
	 */
	public CmUserInfo checkUserPhoneIfReg(String loginAcct)throws Exception{
		if(StringUtils.isEmpty(loginAcct)){
			throw new BusinessException("未传入手机号码");
		}
		CmUserInfo cmUser = cmUserSv.queryUserByAcct(loginAcct);
		return cmUser;
	}
	/**
	 * 根据手机号码，类型判断手机号码是否已经存在
	 * 
	 * @param loginAcct
	 * @param type  1 判断在联运到家（师傅端）是否存在相同的号码
	 *              2 判断在物流管家（专线，商家端）是否存在相同的号码
	 * @return 
	 *   true 表示存在，false表示不存在
	 * @throws Exception
	 */
	public boolean checkUserPhoneExitForApp(String loginAcct)throws Exception{
		CmUserInfo cmUser = cmUserSv.queryUserByAcctYQ(loginAcct, new Integer[]{2,3});
		if(cmUser==null){
			return false;
		}
		return true;
	}
	
	/**
	 * lyh
	 * 校验号码是否已注册
	 * @param inParam
	 * 			key:loginAcct
	 * @throws Exception
	 * @return true 已注册
	 * 		   false 未注册
	 */
	public CmUserInfo checkUserPhoneIfRegByType(String loginAcct,Integer userType)throws Exception{
		if(StringUtils.isEmpty(loginAcct)){
			throw new BusinessException("未传入手机号码");
		}
		CmUserInfo cmUser = cmUserSv.queryUserByAcctType(loginAcct,userType);
		return cmUser;
	}
	
	
	/**
	 * 忘记密码
	 * @param inParam
	 * 			key:loginAcct
	 * 			key:password
	 * @throws Exception
	 */
	public void setPassword(Map<String, String> inParam)throws Exception{
		String loginAcct = DataFormat.getStringKey(inParam, "loginAcct");
		String password = DataFormat.getStringKey(inParam, "password");

		if(StringUtils.isEmpty(loginAcct)){
			throw new BusinessException("未传入手机号码");
		}
		
		if(StringUtils.isEmpty(password)){
			throw new BusinessException("未传入密码");
		}
		
		//解密 
		password=EncryPwdUtil.pwdDecryption(password);
		//加密
		password=K.j_s(password);

		CmUserInfo cmUser = cmUserSv.queryUserByAcct(loginAcct);
		cmUser.setLoginPwd(password);
		cmUser.setOpId(cmUser.getUserId());
		cmUserSv.doUpdateCmUserInfo(cmUser);
	}
	
	/**
	 * lyh
	 * 修改密码
	 * @param inParam
	 * 			key:userId
	 * 			type: 1 修改密码，2忘记密码
	 * 			key:password 新密码
	 * 			key:oldPassword 旧密码
	 * @throws Exception
	 */
	public void resetPassword(Map<String, Object> inParam)throws Exception{
		long userId = DataFormat.getLongKey(inParam, "userId");
		String password = DataFormat.getStringKey(inParam, "password");
		String oldPassword = DataFormat.getStringKey(inParam, "oldPassword");
		int type=DataFormat.getIntKey(inParam, "type");

		if(userId<0){
			throw new BusinessException("未传入用户ID");
		}
		
		if(StringUtils.isEmpty(password)){
			throw new BusinessException("未传入新密码");
		}

		if(type==1 && StringUtils.isEmpty(oldPassword)){
			throw new BusinessException("未传入旧密码");
		}
		CmUserInfo cmUser = cmUserSv.getUserInfo(userId,-1);
		if(type==1){
			//解密 
			oldPassword=EncryPwdUtil.pwdDecryption(oldPassword);
			//加密
			oldPassword=K.j_s(oldPassword);
		}
		
		//解密 
		password=EncryPwdUtil.pwdDecryption(password);
		//加密
		password=K.j_s(password);
		
		if(type==1 && !oldPassword.equals(cmUser.getLoginPwd())){
			throw new BusinessException("旧密码输入不正确");
		}
		cmUser.setLoginPwd(password);
		cmUser.setOpId(cmUser.getUserId());
		cmUserSv.doUpdateCmUserInfo(cmUser);
	}
	/**
	 * 保存用户信息
	 * @param inParam 
	 * 			<CmUserInfo>
	 * @throws Exception
	 */
	public void saveUserInfo(Map<String, Object> inParam)throws Exception{
		String password = DataFormat.getStringKey(inParam, "loginPwd");
		if(StringUtils.isEmpty(password)){
			throw new BusinessException("未传入新密码");
		}
		String loginAcct = DataFormat.getStringKey(inParam, "loginAcct");
		int userType = DataFormat.getIntKey(inParam, "userType");
		if(StringUtils.isEmpty(loginAcct)){
			throw new BusinessException("未传入登陆账号");
		}
		if(userType<0){
			throw new BusinessException("未传入用户类型");
		}
		CmUserInfo cmUserInfo = new CmUserInfo();
		BeanUtils.populate(cmUserInfo, inParam);
		cmUserSv.doSaveCmUserInfo(cmUserInfo);
	}
	
	/**
	 * 提供给TMS同步接口
	 * 保存用户信息
	 * @param inParam 
	 * 			<CmUserInfo>
	 * @throws Exception
	 */
	public void saveOrUpdateUserInfo(Map<String, Object> inParam)throws Exception{
		Long userId = DataFormat.getLongKey(inParam, "userId");
		
		if(userId==null){
			throw new BusinessException("未传入userId!");
		}
		
		CmUserInfo cmUserInfo=  cmUserSv.getUserInfo(userId, SysStaticDataEnum.USER_TYPE.OPERATOR);
		
		if(cmUserInfo==null){
			cmUserInfo=new CmUserInfo();
			BeanUtils.populate(cmUserInfo, inParam);
			
//			String orgCode=cmUserInfo.getOrgCode();
			OrganizationTF organizationTF = (OrganizationTF)SysContexts.getBean("organizationTF");
//			CmCompanyInfoOut cmCompanyInfoOut=  organizationTF.queryCompanyByOrgCode(orgCode);
//			cmUserInfo.setTenantId(cmCompanyInfoOut.getTenantId());
//			cmUserInfo.setTenantCode(cmCompanyInfoOut.getTenantCode());
			cmUserSv.doSaveCmUserInfo(cmUserInfo);
			
			SysRoleOperRel operRel = new SysRoleOperRel();
			operRel.setRoleId(SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER);
			operRel.setCreateDate(new Date(System.currentTimeMillis()));
			operRel.setLastModifyDate(new Date(System.currentTimeMillis()));
			operRel.setLastModifyOperatorId(cmUserInfo.getUserId());
			operRel.setOperatorId(cmUserInfo.getUserId());
			operRel.setRoleId(SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER);
			operRel.setState(SysStaticDataEnum.STS.VALID);
			SysContexts.getEntityManager().saveOrUpdate(operRel);
		}else{
			BeanUtils.populate(cmUserInfo, inParam);
			cmUserSv.doUpdateCmUserInfo(cmUserInfo);
		}
		
	}
	/**
	 * 根据账号列表查询用户To APP
	 * @param loginAcct[] String
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> queryUserByAcctList(Object[] logAccts )throws Exception{
		if(logAccts==null){
			throw new BusinessException("为传入账号列表");
		}
		return cmUserSv.queryUserByAcctList(logAccts);
		
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 查询用户的信息
	 * @param inParam
	 * @return
	 */
	public Map doQueryCmUser(Map<String, String> inParam){
		String userName=DataFormat.getStringKey(inParam, "userName");
		int userType=DataFormat.getIntKey(inParam, "userType");
		Long orgId=DataFormat.getLongKey(inParam, "orgId");
		int roleId=DataFormat.getIntKey(inParam, "roleId");
		CmUserSV cmUserSv = (CmUserSV)SysContexts.getBean("cmUserSv");
		int userRole = getUserRole();
		Pagination page = cmUserSv.doQueryCmUser(userName, userType, orgId,userRole);
		return JsonHelper.parseJSON2Map(JsonHelper.json(page));
		
	}
	
	/**
	 * lyh
	 * 保存用户信息
	 *    1 保存用户的信息
	 *    2 保存用户跟组织的关系
	 *    3 保存用户跟角色的关系
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map doSaveCmUserInfo(Map<String, String> inParam) throws Exception{
		BaseUser base = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager();
		String userName=DataFormat.getStringKey(inParam, "userName");
		Integer roleId=DataFormat.getIntKey(inParam, "roleId");
		Long userId=DataFormat.getLongKey(inParam, "userId");
		String loginAcct=DataFormat.getStringKey(inParam, "loginAcct");
		String loginPwd=DataFormat.getStringKey(inParam, "password");
		Integer loginType = DataFormat.getIntKey(inParam,"loginType");
		//用户类型
		Integer userType=DataFormat.getIntKey(inParam,"userType");
		boolean isUpdatePwd=false;
		if(StringUtils.isNotBlank(loginPwd)){
			//如果是修改用户信息，页面是不展示用户的密码，如果传入过来的密码为空，表示不修改原来的密码 
			isUpdatePwd=true;
		}
		Long tenantId=DataFormat.getLongKey(inParam, "tenantId");
		Long orgId=DataFormat.getLongKey(inParam, "orgId");
		if (orgId!=null && orgId > 0) {
			if (OraganizationCacheDataUtil.getOrganization(orgId).getParentOrgId() == -1 && (loginType == 2 || loginType == 3)) {
				throw new BusinessException("总公司账号登陆类型不能是【app】或者【web和app用户】！");
			}
		}
		if (userType == 2 && (loginType == 2 || loginType == 3)) {
			throw new BusinessException("专线账号登陆类型不能是【app】或者【web和app用户】！");
		}
		if (userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION && OraganizationCacheDataUtil.getOrganization(orgId).getParentOrgId() == -1) {
			throw new BusinessException("总公司账号不能选择【开单与配送员角色】");
		}
		if(userId==null  || userId<=0){
			if(!CommonUtil.isCheckPwd(EncryPwdUtil.pwdDecryption(loginPwd))){
				throw new BusinessException ("密码长度6～16个字符，字母不区分大小写!");
			}
		}
		if(StringUtils.isEmpty(userName)){
			throw new BusinessException("用户名不能为空");
		}
		if(StringUtils.isEmpty(loginAcct)){
			throw new BusinessException("账户名不能为空");
		}
		if(userId==null  || userId <= 0){
			if(StringUtils.isEmpty(loginPwd)){
				throw new BusinessException("密码不能为空");
			}
		}
		if(tenantId==null){
			throw new BusinessException("公司不能为空");
		}
		if(loginType != SysStaticDataEnum.LOGIN_TYPE.WEB){
			if(!CommonUtil.isCheckPhone(loginAcct)){
				throw new BusinessException("非WEB用户，账户名必须是手机号码！");
			}
			
			if(userId>0){
				//修改的时候，如果手机号码没有修改，就不需要校验，修改了再进行校验
				CmUserInfo cmUserInfo =cmUserSv.getUserInfo(userId, -1);
				//手机号码如果没有修改不需要校验
				if(!loginAcct.equals(cmUserInfo.getLoginAcct())){
					//如果是手机号码，需要判断该手机号码跟专线，商家那边的手机号码是否冲突，如果已经添加了，就不能再添加，会影响app登陆
					if(this.checkUserPhoneExitForApp(loginAcct)){
						throw new BusinessException("该手机号码已经被占用了，请输入其他号码！");
					}
				}
			}else{
				if(this.checkUserPhoneExitForApp(loginAcct)){
					throw new BusinessException("该手机号码已经被占用了，请输入其他号码！");
				}
			}
		}
		if ((loginType == SysStaticDataEnumYunQi.LOGIN_TYPE.WEB_APP || loginType == SysStaticDataEnumYunQi.LOGIN_TYPE.WEB) && userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION) {
			throw new BusinessException("配送员与开单员类型不能在web里登陆，只能注册app登陆类型！");
		}
		Organization organization=  null;
		List<Organization> list =session.createCriteria(Organization.class).add(Restrictions.eq("tenantId", tenantId)).add(Restrictions.eq("parentOrgId", -1L)).list();
		if (list!=null&&list.size()>0) {
			organization = list.get(0);
		}
		CmUserSV cmUserSv = (CmUserSV)SysContexts.getBean("cmUserSv");
		if(!cmUserSv.checkUserAcct(loginAcct, tenantId,userId)){
			throw new BusinessException("该登录账号已经存在，请重新修改！");
		}

		if(organization==null){
			throw new BusinessException("公司信息不对");
		}
		
		SysRoleOperRelTF sysRoleOperRelTF = (SysRoleOperRelTF)SysContexts.getBean("sysRoleOperRelTF");
		if(userId>0){
//			List<CmUserInfoOutQuery> ccList = cmUserSv.checkUpdateUser(tenantId);
//			for(CmUserInfoOutQuery temp : ccList){
//				if(temp.getUserId() != userId){
//					 if(temp.getLoginAcct() == loginAcct){
//						 throw new BusinessException("该登录账号已经存在，请重新修改！");
//					 }
//				}
//			}
			//更新
			CmUserInfo cmUserInfo =cmUserSv.getUserInfo(userId, -1);
			if(cmUserInfo==null){
				throw new BusinessException("用户不存在!");
			}
			loginPwd=EncryPwdUtil.pwdDecryp(loginPwd);
			String pwd = "";
			if(StringUtils.isEmpty(loginPwd) || loginPwd == ""){
				loginPwd = cmUserInfo.getLoginPwd();
				pwd = loginPwd;
			}else{
				pwd=K.j_s(EncryPwdUtil.pwdDecryption(loginPwd));   
			}
			cmUserInfo.setUserName(userName);
			cmUserInfo.setLoginAcct(loginAcct);
			if(userType < 0){
				cmUserInfo.setUserType(Integer.valueOf(organization.getTenantType()));
			}else{
				cmUserInfo.setUserType(userType);
			}
			cmUserInfo.setLoginType(loginType);
			if(isUpdatePwd){
				cmUserInfo.setLoginPwd(pwd);
			}
			cmUserInfo.setOpId(base.getUserId());
			cmUserInfo.setCreateTime(new Date());
			session.update(cmUserInfo);
			//cmUserSv.doSaveCmUserInfo(cmUserInfo);
			//更新角色关系
			SysRoleOperRel sysRoleOperRel= sysRoleOperRelTF.querySysRoleOperRel(userId);
			if(sysRoleOperRel.getRoleId()!=roleId.intValue()){
				sysRoleOperRel.setRoleId(roleId);
				sysRoleOperRelTF.saveSysRoleOperRel(sysRoleOperRel);
			}
		}else{
			if(!cmUserSv.checkUserAcct(loginAcct, tenantId,userId)){
				throw new BusinessException("该登录账号已经存在，请重新修改！");
			}
			String pwd=K.j_s(EncryPwdUtil.pwdDecryption(loginPwd));   
			loginPwd=EncryPwdUtil.pwdDecryp(loginPwd);
			//新增用户信息
			CmUserInfo cmUserInfo= null;
			if(userType < 0){
				cmUserInfo= cmUserSv.doSaveCmUserInfo(userName, loginAcct, pwd,loginType,userId,Integer.valueOf(organization.getTenantType()));
			}else{
				cmUserInfo= cmUserSv.doSaveCmUserInfo(userName, loginAcct, pwd,loginType,userId,userType);
			}
			
			CmUserOrgRelTF cmUserOrgRelTF=(CmUserOrgRelTF)SysContexts.getBean("cmUserOrgRelTF");
			//保存用户跟组织的关系
			CmUserOrgRel cmUserOrgRel=new CmUserOrgRel();
			
			cmUserOrgRel.setUserId(cmUserInfo.getUserId());
			cmUserOrgRel.setOrgId(organization.getOrgId());
			if(orgId!=null && orgId.longValue()>0){
				cmUserOrgRel.setOrgId(orgId);
			}
			cmUserOrgRel.setTenantId(organization.getTenantId());
			cmUserOrgRel.setOpId(base.getUserId());
			cmUserOrgRel.setOpDate(new Date());
			cmUserOrgRelTF.doSave(cmUserOrgRel);
			
			//保存用户跟角色的关系
			SysRoleOperRel operRel = new SysRoleOperRel();
			operRel.setRoleId(roleId);
			operRel.setCreateDate(new Date());
			operRel.setLastModifyDate(new Date());
			operRel.setLastModifyOperatorId(base.getUserId());
			operRel.setOperatorId(cmUserInfo.getUserId());
			operRel.setState(SysStaticDataEnum.STS.VALID);
		    session.saveOrUpdate(operRel);
		    //如果员工是拉包工类型
		    if(userType  == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
                //如果员工是拉包工类型
                CmUserInfoPull pull = new CmUserInfoPull();
                pull.setUserId(cmUserInfo.getUserId());
                pull.setTenantId(tenantId);
                pull.setPullState(SysStaticDataEnumYunQi.PULL_STATE.CERTIFIED);
                pull.setPullWork(SysStaticDataEnumYunQi.PULL_WORK_YUNQI.PULL_WORK0);
                
                if(StringUtils.isEmpty(organization.getRegionId()) || 
                		StringUtils.isEmpty(organization.getProvinceId()) ||
                		StringUtils.isEmpty(organization.getCountyId())){
                	throw new BusinessException("网点的省市区为空，请设置一下网点的省市区信息！");
                }
                
                pull.setCity(Long.parseLong(organization.getRegionId()));
                pull.setProvince(Long.parseLong(organization.getProvinceId()));
                pull.setDistrict(Long.parseLong(organization.getCountyId()));
                pull.setProvinceName(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", organization.getProvinceId()).getName());
                pull.setCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", organization.getRegionId()).getName());
                pull.setDistrictName(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT",organization.getCountyId()).getName());
                // --------
                AcWalletRelSV acWalletRelSV = (AcWalletRelSV) SysContexts.getBean("acWalletRelSV");
                List<AcWalletRel> listRel = acWalletRelSV.checkAcWalletRel(cmUserInfo.getUserId(), organization.getTenantId());
                if(listRel == null || listRel.size() <= 0){
                      AcWalletRel acWalletRel = new AcWalletRel();
                      acWalletRel.setCreateTime(new Date());
                      acWalletRel.setPullTenantId(tenantId);
                      acWalletRel.setTenantId(tenantId);
                     acWalletRel.setUserId(cmUserInfo.getUserId());
                      acWalletRelSV.doSave(acWalletRel);
                }
                ((CmUserInfoPullSV) SysContexts.getBean("cmUserInfoPullSV")).doSaveOrUpdate(pull);
                
                
                //发送短信
                SmsYQUtil.sendRegisterWoker(tenantId, loginAcct, loginPwd, CommonUtil.getTennatNameById(tenantId));
		    }
			
		}
		
		return null;
		
	}
	
	public Map doQueryCmUserByUserId(Map<String, String> inParam) throws Exception{
		Long userId=DataFormat.getLongKey(inParam, "userId");
		Session session = SysContexts.getEntityManager(true);
	/*	CmUserInfo cmUser = (CmUserInfo)session.get(CmUserInfo.class, userId);
		if(cmUser==null){
			throw new BusinessException("查询不到该用户的信息");	
		}*/
		StringBuffer buff = new StringBuffer();
		buff.append("select u.*,r.* from cm_user_info u,sys_role_oper_rel r,cm_user_org_rel l where u.user_id=l.user_id and u.user_id=r.OPERATOR_ID and u.user_id=:userId");
		SQLQuery query  = session.createSQLQuery(buff.toString()).addEntity("u", CmUserInfo.class).addEntity("r", SysRoleOperRel.class);
		if(userId!=null && userId>0){
			query.setParameter("userId", userId);
		}
		CmUserInfoOut cmUserInfoOut = new CmUserInfoOut();
		List<Object[]> list = (List<Object[]>)query.list();
		Map iparmMap = new HashMap();
		List<CmUserInfoOut> rtnList = new ArrayList<CmUserInfoOut>();
		if(list!= null && list.size()>0){
			
			for(Object[] obj : list){
				CmUserInfo cmuserInfo = (CmUserInfo)obj[0];
				SysRoleOperRel sysRoleOperRel = (SysRoleOperRel)obj[1];
				BeanUtils.copyProperties(cmUserInfoOut, cmuserInfo);
				BeanUtils.copyProperties(cmUserInfoOut, sysRoleOperRel);
				if(cmUserInfoOut.getOrgId()!=null && cmUserInfoOut.getOrgId()>0){
				    cmUserInfoOut.setOrgName(OraganizationCacheDataUtil.getOrgName(cmUserInfoOut.getOrgId()));
				}
				if( cmUserInfoOut.getRoleId()>0){
					cmUserInfoOut.setRoleName(SysRoleCacheUtil.getSysRoleName(cmUserInfoOut.getRoleId()));
				}
				
				if(cmUserInfoOut.getOpId()!=null &&cmUserInfoOut.getOpId()>0 ){
					CmUserInfo cmUser = (CmUserInfo) session.get(CmUserInfo.class, cmUserInfoOut.getOpId());
					if(cmUser!=null){
						cmUserInfoOut.setOpName(cmUser.getUserName());	
					}
				}
				cmUserInfoOut.setLoginPwd((cmUserInfoOut.getLoginPwd()));
				//cmUserInfoOut.setOpName(opName);
				rtnList.add(cmUserInfoOut);
			}
		}
		//EncryPwd.pwdEncryption(K.k_s(objAry[11]+""))
		
		iparmMap.put("cmUser", rtnList);
		return iparmMap;
	}
	
	/**
	 * lyh
	 * 查询平台用户的信息
	 * @param inParam
	 * @return
	 * @throws Exception 
	 */
	public Map doQueryPlateFormCmUser(Map<String, String> inParam) throws Exception{
		String userName=DataFormat.getStringKey(inParam, "userName");
		int roleId=DataFormat.getIntKey(inParam, "roleId");
		long tenantId=DataFormat.getLongKey(inParam, "tenantId");
		long orgId=DataFormat.getLongKey(inParam, "orgId");
		String loginAcct=DataFormat.getStringKey(inParam, "loginAcct");
		int roleType=DataFormat.getIntKey(inParam, "roleType");
		CmUserSV cmUserSv = (CmUserSV)SysContexts.getBean("cmUserSv");
		Pagination page = cmUserSv.queryPlateUser(userName,orgId,roleId,tenantId,loginAcct,roleType);
		return JsonHelper.parseJSON2Map(JsonHelper.json(page));
	}
	
	/**
	 * lyh
	 * 失效用户
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map doDelCmUser(Map<String, String> inParam) throws Exception{
		long userId=DataFormat.getLongKey(inParam, "userId");
	     Session session  = SysContexts.getEntityManager();
		if(userId<=0){
			throw new BusinessException("用户id为空，请重新输入！");	
		}
		CmUserSV cmUserSv = (CmUserSV)SysContexts.getBean("cmUserSv");
		cmUserSv.doDelCmUser(userId);
		return null;
	}
	
	/**
	 * 判断用户的角色类型
	 * @return
	 */
	public Integer getUserRole(){
		BaseUser user=SysContexts.getCurrentOperator();
		Map map = new HashMap();
		Map<String,Object> attrs= user.getAttrs();
		List<Integer> rolds=(List<Integer>) attrs.get(EnumConsts.Common.SESSION_ROLES);
		int roleId=rolds.get(0); 
		int roleType=0;
		if(rolds.size()>0){
		     List<SysRole> roleList = SysRoleCacheUtil.getSysList();
			if(roleList.size()>0){
				for(SysRole sysRole:roleList){
					if(roleId==sysRole.getRoleId()){
						return sysRole.getRoleType();
					}
				}
			}
		}
		return roleType;
	}
	/**
	 * 给操作员添加区域
	 * @return
	 */
	public Map doSaveUserArea(Map<String, Object> inParam){
		Session session = SysContexts.getEntityManager();
		JSONArray jsonArray=(JSONArray)inParam.get("userAreaList");
		if(jsonArray!=null){
			Iterator iterator=  jsonArray.iterator();
			List<Map<String,String>> list=new ArrayList<Map<String,String>>();
			while (iterator.hasNext()) {
				Map<String,String > map=(Map<String,String >)iterator.next();
				CmUserAreaRel areaRel = new CmUserAreaRel();
				String userAreaReId = map.get("userAreaReId");
				String userAreaId = map.get("userAreaId");
				String userId = map.get("userId");
				String type = map.get("dataType");
				Province  province =  SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", userAreaId);
				long proId=Long.valueOf(userAreaId);
				if(StringUtils.isNotEmpty(type)&& type.equals("Y")){
					List<City> lists = SysStaticDataUtil.getCityData("SYS_CITY", userAreaId);
					if(lists.size()>0){
						for(City city:lists){
							areaRel = new CmUserAreaRel();
							areaRel.setProvinceId(proId);
							areaRel.setProvinceName(province.getName());
							areaRel.setUserId(Long.valueOf(userId));
							long cityId=city.getId();
							areaRel.setCityId(cityId);
							areaRel.setCityName(city.getName());
							session.saveOrUpdate(areaRel);
						}
					}
				}else if(StringUtils.isNotEmpty(userAreaReId)){
					String[] regId = userAreaReId.split(",");
					areaRel.setProvinceId(proId);
					areaRel.setProvinceName(province.getName());
					areaRel.setUserId(Long.valueOf(userId));
					if(regId.length>0){
						for(int i=0;i<regId.length;i++){
							areaRel = new CmUserAreaRel();
							areaRel.setProvinceId(proId);
							areaRel.setProvinceName(province.getName());
							areaRel.setUserId(Long.valueOf(userId));
							long cityId=Long.valueOf(regId[i]);
							areaRel.setCityId(cityId);
							City city = SysStaticDataUtil.getCityDataList("SYS_CITY", cityId+"");
							areaRel.setCityName(city.getName());
							session.saveOrUpdate(areaRel);
						}
					}else{
						session.saveOrUpdate(areaRel);
					}
				}
			}
		}
		Map map=new HashMap();
		map.put("info", "Y");
		return map;
	}
	/**
	 * 获取操作区域列表
	 * @param inParam
	 * @return
	 */
	public List queryAreaList(Map<String, String> inParam){
		Session session = SysContexts.getEntityManager();
		Long userId = DataFormat.getLongKey(inParam, "userId");
		if(userId==null || userId<=0){
			throw new BusinessException("请输入用户编号");
		}
		List areaList = new ArrayList();
		List<UserAreaParam> relList= new ArrayList<UserAreaParam>();
		String sql="SELECT PROVINCE_ID ,PROVINCE_NAME, GROUP_CONCAT(distinct city_name) as cityName, GROUP_CONCAT(distinct city_id) as cityId FROM cm_user_area_rel  WHERE user_id=:userId GROUP BY province_name";
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter("userId", userId);
		if(query.list().size()>0){
			areaList=query.list();
			for(int i=0;i<areaList.size();i++){
				UserAreaParam users = new UserAreaParam();
				Object[]  obj = (Object[])areaList.get(i);
				if(obj!=null && obj.length>0){
					users.setUserAreaId(obj[0].toString());
					users.setProName(obj[1].toString());
					users.setCityIds(obj[3].toString());
					users.setUserAreaName(obj[2].toString());
					relList.add(users);
				}
				
			}
			
		}
		return relList;
	}
	
	public Map delArea(Map<String, Object> inParam){
		Long userId = DataFormat.getLongKey(inParam, "userId");
		String provinceId = DataFormat.getStringKey(inParam, "provinceId");
		Session session  = SysContexts.getEntityManager();
		Criteria ca = session.createCriteria(CmUserAreaRel.class);
		ca.add(Restrictions.eq("userId", userId));
		ca.add(Restrictions.eq("provinceId", Long.valueOf(provinceId)));
		List<CmUserAreaRel> relList = new ArrayList<CmUserAreaRel>();
		relList=ca.list();
		if(relList.size()>0){
			for(CmUserAreaRel cmUserAreaRel:relList){
				session.delete(cmUserAreaRel);
			}
		}
		Map map= new HashMap();
		map.put("info", "Y");
		return map;
	}
	
	/**
	 * lyh
	 * 
	 * 修改用户的头像，昵称
	 * @param inParam 
	 * 			key:userName
	 * 			key:userPicture 图片全路径
	 * @throws Exception
	 */
	public void updateUserInfo(String userName,long userPic)throws Exception{
		BaseUser baseUser = SysContexts.getCurrentOperator();
		CmUserInfo cmUser = cmUserSv.getUserInfo(baseUser.getUserId(), -1);
		cmUser.setUserName(userName);
		cmUser.setUserPic(String.valueOf(userPic));
		cmUserSv.doUpdateCmUserInfo(cmUser);
	}
	
	/**
	 * lyh
	 * 获取用户信息
	 */
	public CmUserInfo getUserInfo()throws Exception{
		BaseUser baseUser = SysContexts.getCurrentOperator();
		CmUserInfo cmUser = cmUserSv.getUserInfo(baseUser.getUserId(), -1);
		return cmUser;
	}
	
	
	/**
	 * lyh
	 * 保存用户信息
	 *    1 保存用户的信息
	 *    2 保存用户跟组织的关系
	 *    3 保存用户跟角色的关系
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map modifyUserInfoPwd(Map<String, Object> inParam) throws Exception{
		BaseUser base = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager();
	
		String password=DataFormat.getStringKey(inParam, "password");
		String  confirmPassword = DataFormat.getStringKey(inParam,"confirmPassword");
		
		if(StringUtils.isEmpty(password)){
			throw new BusinessException("密码不能为空");
		}
		if(StringUtils.isEmpty(confirmPassword)){
			throw new BusinessException("确认密码不能为空");
		}
		
		if(!password.equals(confirmPassword)){
			throw new BusinessException("密码跟确认密码不一致，请重新输入！");
		}
		
		
		CmUserSV cmUserSv = (CmUserSV)SysContexts.getBean("cmUserSv");
		
		CmUserInfo cmUserInfo =cmUserSv.getUserInfo(base.getUserId(), -1);
		if(cmUserInfo==null){
				throw new BusinessException("用户不存在!");
		}
		password=EncryPwdUtil.pwdDecryp(password);
		//解密后，需要再判断
		if(StringUtils.isEmpty(password)){
			throw new BusinessException("密码不能为空");
		}
		
		if(!CommonUtil.isCheckPwd(password)){
			throw new BusinessException ("密码长度6～16个字符，字母不区分大小写!");
		}
		password=K.j_s(password);   
		cmUserInfo.setLoginPwd(password);
		
		session.update(cmUserInfo);
		
		return null;
		
	}
	
	
	
	/**
	 *根据一个租户id获取一个租户下面的公司管理员信息
	 * 
	 * @param tenantId
	 * @return
	 * @throws Exception
	 */
//	public CmUserInfo getCompanyUserOfTenant(Long tenantId)throws Exception{
//		
//		
//		Session session = SysContexts.getEntityManager();
//		
//		StringBuffer hql =new StringBuffer();
//		
//		hql.append("select u from CmUserInfo u,CmUserOrgRel r,SysRole s, SysRoleOperRel rel");
//		hql.append("  where u.userId=r.userId and u.state=1 ");
//		
//		if(StringUtils.isEmpty(loginAcct)){
//			throw new BusinessException("未传入账户");
//		}else{
//			hql.append(" and u.loginAcct=:loginAcct");
//		}
//		
//		if(tenantId!=null && tenantId>0){
//			hql.append(" and r.tenantId=:tenantId");
//		}
//		
//		if(StringUtils.isNotEmpty(userType)){
//			String[] userTypes=userType.split(",");
//			if(userTypes.length>1){
//				
//				hql.append(" and u.userType in (:userType)");
//			}else{
//				hql.append(" and u.userType =:userType");
//			}
//		}
//		
//		if(StringUtils.isNotEmpty(loginType)){
//			String[] loginTypes=loginType.split(",");
//			if(loginTypes.length>1){
//				hql.append(" and u.loginType in (:loginType)");
//			}else{
//				hql.append(" and u.loginType =:loginType)");
//			}
//		}
//		
//		Query query=session.createQuery(hql.toString());
//		
//		if(StringUtils.isEmpty(loginAcct)){
//			throw new BusinessException("未传入账户");
//		}else{
//			query.setParameter("loginAcct", loginAcct);
//		}
//		
//		if(tenantId!=null && tenantId>0){
//			query.setParameter("tenantId", tenantId);
//		}
//		
//		if(StringUtils.isNotEmpty(userType)){
//			String[] userTypes=userType.split(",");
//			if(userTypes.length>1){
//				Integer[] userTypeInt=new Integer[userTypes.length];
//				for(int i=0;i<userTypes.length;i++){
//					userTypeInt[i]=Integer.valueOf(userTypes[i]);
//				}
//				query.setParameterList("userType", userTypeInt);
//			}else{
//				query.setParameter("userType", Integer.valueOf(userType));
//			}
//		}
//		
//		if(StringUtils.isNotEmpty(loginType)){
//			String[] loginTypes=loginType.split(",");
//			if(loginTypes.length>1){
//				Integer[] loginTypeInt=new Integer[loginTypes.length];
//				for(int i=0;i<loginTypeInt.length;i++){
//					loginTypeInt[i]=Integer.valueOf(loginTypes[i]);
//				}
//				query.setParameterList("loginType", loginTypeInt);
//			}else{
//				query.setParameter("loginType", Integer.valueOf(loginType));
//
//			}
//		}
//		
//		List acctList = query.list();
//		if(acctList!=null&&acctList.size()>0){
//			return (CmUserInfo)acctList.get(0);
//		}
//		return null;
//	}
	/**
	 * 根据登录的账号和租户ID查询用户密码
	 * @param inParam
	 * @return
	 *      
	 * @throws Exception
	 */
	public CmUserInfo queryUserPwdNew(Map<String, String> inParam) throws Exception {
		String loginAcct = DataFormat.getStringKey(inParam, "loginAcct");
		if(StringUtils.isEmpty(loginAcct)){
			return null;
		}
		long tenantId = DataFormat.getLongKey(inParam, "tenantId");
		if (tenantId <= 0)
			return null;
		CmCustomerSV cmCustomerSV = (CmCustomerSV)SysContexts.getBean("customerSV");
		return cmCustomerSV.queryUserByAcctNew(loginAcct, tenantId);
	}
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 商户查询yq
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> queryMerchan(Map<String,Object> inParam){
		Session session = SysContexts.getEntityManager(true);
		String name = DataFormat.getStringKey(inParam, "name");
		String bill = DataFormat.getStringKey(inParam, "bill");
		StringBuffer sb = new StringBuffer("SELECT ");
		sb.append(" c.USER_NAME,c.LOGIN_ACCT,c.CREATE_TIME,d.PROVINCE_NAME,d.CITY_NAME,d.DISTRICT_NAME,d.ADDRESS,c.USER_ID ");
		sb.append(" FROM cm_user_info c LEFT JOIN cm_address d ON c.USER_ID = d.USER_ID AND d.ADDERSS_DEFAULT = 1 AND c.STATE = 1 AND MERCHANT_ADDRESS_TYPE = 1 WHERE c.USER_TYPE = :userType  ");
		if (StringUtils.isNotEmpty(bill)) {
			sb.append(" AND c.LOGIN_ACCT like :bill ");
		}
		if(StringUtils.isNotEmpty(name)){
			sb.append(" AND c.USER_NAME like  :name");
		}
		sb.append(" ORDER BY c.USER_ID DESC ");
		Query query = session.createSQLQuery(sb.toString());
		query.setParameter("userType", SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS);
		if (StringUtils.isNotEmpty(bill)) {
			query.setParameter("bill", "%"+bill+"%");
		}
		if(StringUtils.isNotEmpty(name)){
			query.setParameter("name", "%"+name+"%");
		}
		IPage p = PageUtil.gridPage(query);
		List<Object[]> list = p.getThisPageElements();
		List<Map<String,Object>> mapLsit = new ArrayList<Map<String,Object>>();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("userName", obj[0] != null ? obj[0].toString() : "");
				map.put("loginAcct", obj[1] != null ? obj[1].toString() : "");
				map.put("createTimeString",obj[2] != null ? DateUtil.formatDate((Date)obj[2], "yyyy-MM-dd HH:mm:ss") : "");
				map.put("address", (obj[3] != null ? obj[3].toString() : "")+(obj[4] != null ? obj[4].toString() : "")+(obj[5] != null ? obj[5].toString() : "")+(obj[6] != null ? obj[6].toString() : ""));
				map.put("userId", obj[7]);
				mapLsit.add(map);
			}
		}
		Pagination page = new Pagination(p);
		page.setItems(mapLsit);
		return JsonHelper.parseJSON2Map(JsonHelper.json(page));
	}
	public Map<String, Object> setDefaultPackageAndproduct(
			Map<String, Object> inParam) {
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		String packageName = DataFormat.getStringKey(inParam, "packageName");//包装名称
		String product = DataFormat.getStringKey(inParam, "product");//品名
		String packageString = DataFormat.getStringKey(inParam, "packageString");
		String productString = DataFormat.getStringKey(inParam, "productString");//品名
		StaticDataSet staticDataSet = new StaticDataSet();
		staticDataSet.setTanentId(user.getTenantId());
		staticDataSet.setCodeTypeProduct("品名");
		staticDataSet.setCodeTypePackage("包装");
		staticDataSet.setOrgId(user.getOrgId());
		if(StringUtils.isNotEmpty(packageName)){
			staticDataSet.setCodeValuePackage(packageName);
	     }
	    if(StringUtils.isNotEmpty(product)){
		   staticDataSet.setCodeValueProduct(product);
	     }
		StringBuffer sb = new StringBuffer();
		sb.append(" select id from static_data_set where tanent_id = :tanentId and org_id = :orgId ");
		Query query = session.createSQLQuery(sb.toString());
		query.setParameter("tanentId",user.getTenantId() );
		query.setParameter("orgId",user.getOrgId() );
		List<BigInteger> list = query.list();
		if(list!=null && list.size()>0){
			long id =Long.valueOf(list.get(0)+"");
		    staticDataSet.setId(id);
			session.update(staticDataSet); 
		}else{
		    session.save(staticDataSet); 
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("info", "Y");
		return map;
	}
	public Map<String, Object> queryDefaultPackageAndproduct(
			Map<String, Object> inParam) {
		// TODO Auto-generated method stub
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlProduct = new StringBuffer();
		sql.append("select code_type_package, code_value_package,  code_type_product, code_value_product from static_data_set where tanent_id = :tanentId and org_id = :orgId");
		Query query = session.createSQLQuery(sql.toString());
		query.setParameter("tanentId", user.getTenantId());
		query.setParameter("orgId", user.getOrgId());
		List<Object[]> list = query.list();
		List<Map<String,Object>> mapLsit = new ArrayList<Map<String,Object>>();
		if(list==null&& list.size()<=0){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("packageString", "默认包装");
	    	map.put("packageName", "纤袋");
	    	map.put("productString", "默认品名");
	    	map.put("product","衣服");
	    	mapLsit.add(map);
		}
	
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				Map<String,Object> map = new HashMap<String, Object>();
			    String codeType = (String)obj[0];
			    String codeTypeProduct = (String)obj[2];
			    if(StringUtils.isNotEmpty(codeType )&& codeType.equals("包装")){
			    	map.put("packageString", "默认包装");
			    	map.put("packageName", obj[1]!=null?obj[1]:"");
			    	
			    }else{
			    	map.put("packageString", "默认包装");
			    	map.put("packageName", "");
			    }
			    if(StringUtils.isNotEmpty(codeTypeProduct )&&codeTypeProduct.equals("品名")){
			    	map.put("productString", "默认品名");
			    	map.put("product", obj[3]!=null?obj[3]:"");
			    }else{
			    	map.put("productString", "默认品名");
			    	map.put("product", "");
			    }
				
				mapLsit.add(map);
			}
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(mapLsit));
	}
	
	
	
	
}
