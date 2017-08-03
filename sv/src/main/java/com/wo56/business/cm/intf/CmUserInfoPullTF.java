package com.wo56.business.cm.intf;

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
import org.hibernate.criterion.Restrictions;

import com.framework.components.redis.RemoteCacheUtil;
import com.framework.components.session.JdkSerializer;
import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.encrypt.K;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.framework.core.util.RandomGenerator;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.ac.impl.AcWalletRelSV;
import com.wo56.business.ac.interfaces.AcPaymentMethodTF;
import com.wo56.business.ac.vo.AcPaymentMethod;
import com.wo56.business.ac.vo.AcWalletRel;
import com.wo56.business.ac.vo.out.AcAccountWalletTipOut;
import com.wo56.business.cm.impl.CmPullBlackSV;
import com.wo56.business.cm.impl.CmUserInfoPullSV;
import com.wo56.business.cm.impl.CmUserInfoYunQiSV;
import com.wo56.business.cm.impl.CmUserSV;
import com.wo56.business.cm.vo.CmPullBlack;
import com.wo56.business.cm.vo.CmPullWorkHis;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserInfoPull;
import com.wo56.business.cm.vo.CmUserOrgRel;
import com.wo56.business.cm.vo.out.CmUserInfoPullParamOut;
import com.wo56.business.grabOrder.out.BusiGrabControlOut;
import com.wo56.business.matchyq.impl.BusiMatchControlYq;
import com.wo56.business.ord.intf.OrdOrdersInfoTF;
import com.wo56.business.org.vo.Organization;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.sms.utils.TableSplitRule;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;
import com.wo56.common.utils.SysTenantDefCacheDataUtil;

public class CmUserInfoPullTF implements ICmUserInfoPullIntf {
	private static final String TABLE_SPLIT_RULE = "yyyyMM"; // 分表规则
	private static final int  userState[] = new int[]{0,1};
	private static final Log log = LogFactory.getLog(CmUserInfoPullTF.class);
	/**
	 * 保存拉包工关系
	 * 
	 * @param userId
	 * @param province
	 * @param city
	 * @param district
	 * @param tenantId
	 * @return
	 */
	public CmUserInfoPull doSaveOrUpdate(long userId, long province, long city, long district, long tenantId,CmUserInfo user)
			throws Exception {

		if (userId < 0) {
			throw new BusinessException("请传入用户ID！");
		}
		if (tenantId < 0) {
			throw new BusinessException("请选择归属公司或专线！");
		}
		if (province < 0) {
			throw new BusinessException("请选择所属区域");
		}
		
		CmUserInfoPull pull = new CmUserInfoPull();
		pull.setUserId(userId);
		pull.setProvince(province);
		pull.setCity(city);
		pull.setDistrict(district);
		pull.setProvinceName(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(province)).getName());
		pull.setCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(city)).getName());
		pull.setDistrictName(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(district)).getName());
		pull.setTenantId(tenantId);
		pull.setPullState(SysStaticDataEnumYunQi.PULL_STATE.NOT_CERTIFIED);
		pull.setPullWork(SysStaticDataEnumYunQi.PULL_WORK_YUNQI.PULL_WORK0);
		pull.setDefaultSingularNum(CommonUtil.getDefaultSingularNum(-1));
		pull.setSingularNum(0L);
		// 加租户与拉包工的关系
		CmUserOrgRelTF cmUserOrgRelTF = (CmUserOrgRelTF) SysContexts.getBean("cmUserOrgRelTF");
		CmUserOrgRel cmUserOrgRel = new CmUserOrgRel();
		cmUserOrgRel.setTenantId(tenantId);
		cmUserOrgRel.setUserId(userId);
		Organization org = OraganizationCacheDataUtil.getOrganizationByTenantId(tenantId);
		if(Integer.valueOf(org.getTenantType()) != SysStaticDataEnumYunQi.USER_TYPE_YUNQI.CHAIN && Integer.valueOf(org.getTenantType()) != SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULLCOM){
			throw new BusinessException("拉包工只能归属拉包工公司或者专线！");
		}
		cmUserOrgRel.setOrgId(org.getOrgId());
		cmUserOrgRel.setOpDate(new Date());
		cmUserOrgRel.setOpId(userId);
		cmUserOrgRelTF.doSave(cmUserOrgRel);
		// --------
		
		AcWalletRelSV acWalletRelSV = (AcWalletRelSV) SysContexts.getBean("acWalletRelSV");
		List<AcWalletRel> listRel = acWalletRelSV.checkAcWalletRel(userId, tenantId);
		if(listRel == null || listRel.size() <= 0){
			AcWalletRel acWalletRel = new AcWalletRel();
			acWalletRel.setCreateTime(new Date());
			acWalletRel.setPullTenantId(tenantId);
			acWalletRel.setTenantId(tenantId);
			acWalletRel.setUserId(userId);
			acWalletRelSV.doSave(acWalletRel);
		}
		

		if (((CmUserInfoPullSV) SysContexts.getBean("cmUserInfoPullSV")).doSaveOrUpdate(pull) == "Y") {
			return pull;
		}
		return null;
	}

	/**
	 * app注册用户--云企
	 * 
	 * @author qlf
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public CmUserInfo doSaveAppCmUserInfo(Map<String, Object> inParam) throws Exception {
		Integer[] loginType = new Integer[]{SysStaticDataEnum.LOGIN_TYPE.APP,SysStaticDataEnum.LOGIN_TYPE.WEB_AND_APP}; 
		int userType = DataFormat.getIntKey(inParam, "userType");
		if (userType < 0) {
			throw new BusinessException("请选择用户类型！");
		}
		String loginAcct = DataFormat.getStringKey(inParam, "loginAcct");
		if (StringUtils.isEmpty(loginAcct)) {
			throw new BusinessException("请输入手机号码！");
		}
		String loginPwd = DataFormat.getStringKey(inParam, "loginPwd");
		if (StringUtils.isEmpty(loginPwd)) {
			throw new BusinessException("请输入登陆密码");
		}
		String userName = DataFormat.getStringKey(inParam, "userName");
		if (StringUtils.isEmpty(userName)) {
			throw new BusinessException("请输入用户名");
		}
		CmUserInfo cmUserInfo = new CmUserInfo();
		
		CmUserSV cmUserSV = (CmUserSV) SysContexts.getBean("cmUserSv");

		CmUserInfo checkUser = cmUserSV.queryUserByAcctYQ(loginAcct,loginType);
		
		if (checkUser != null && (checkUser.getLoginType() == 2 || checkUser.getLoginType() == 3)) {
			throw new BusinessException("手机号码：" + loginAcct + "已经注册了，请去登录即可！");
		}
		//如果是虚拟用户逻辑
		boolean isVirtualUser = false;
		if(checkUser != null && checkUser.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.VIRTUAL_USER){
			cmUserInfo = checkUser;
			isVirtualUser = true;
		}
		//加密密码
		loginPwd = K.j_s(loginPwd);
		
		cmUserInfo.setUserType(userType);
		cmUserInfo.setLoginAcct(loginAcct);
		cmUserInfo.setLoginPwd(loginPwd);
		cmUserInfo.setUserName(userName);
		cmUserInfo.setLoginType(SysStaticDataEnum.LOGIN_TYPE.APP);
		cmUserInfo.setState(SysStaticDataEnum.STS.VALID);
		cmUserInfo.setCreateTime(new Date());
		if(isVirtualUser){
			cmUserSV.doUpdateCmUserInfo(cmUserInfo);
		}else{
			cmUserSV.doSaveCmUserInfo(cmUserInfo);
		}
		return cmUserInfo;
	}

	/**
	 * 获取登录tokenId
	 * 
	 * @param userId
	 */
	public String getTokenIdByUserId(BaseUser user) throws Exception {
		String rd = RandomGenerator.random(RandomGenerator.ALPHA, 32);
		RemoteCacheUtil.setex(EnumConsts.RemoteCache.SYS_PARTNER_INTF_TOKEN_PROFIX + rd, 7 * 24 * 60 * 60,
				new JdkSerializer().serialize(user));
		return rd;
	}

	/**
	 * 获取拉包工的信息
	 */
	@SuppressWarnings("unchecked")
	public CmUserInfoPull getCmUserInfoPull(long userId) {
		if (userId < 0) {
			return new CmUserInfoPull();
		}
		CmUserInfoPullSV pullSV = (CmUserInfoPullSV) SysContexts.getBean("cmUserInfoPullSV");
		Criteria ca = pullSV.getCmUserInfoPullByUserId(userId);
		List<CmUserInfoPull> list = ca.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 拉包工打卡上班/休息
	 * 
	 * @param inParam
	 * @throws Exception
	 */
	public Map<String, Object> doUpdatePullSate(Map<String, Object> inParam) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put(TABLE_SPLIT_RULE, TableSplitRule.yyyyMM());
		Session session = SysContexts.getEntityManager(map);
		BaseUser base = SysContexts.getCurrentOperator();
		long userId = base.getUserId();
		String pointX =DataFormat.getStringKey(inParam, "pointX");
		String pointY = DataFormat.getStringKey(inParam, "pointY");
		Integer state = DataFormat.getIntKey(inParam, "state");
		if(!CommonUtil.isContains(userState, state)){
			throw new BusinessException ("传入的拉包工状态不正确！");
		}
		if (userId < 0) {
			throw new BusinessException("未传入用户ID");
		}
		String sql = "update cm_user_info_pull set PULL_WORK=:state where USER_ID=:userId";
		session.createSQLQuery(sql).setParameter("state", state).setParameter("userId", userId).executeUpdate();
		CmPullWorkHis cmPullWorkHis = new CmPullWorkHis();
		Date date = new Date();
		cmPullWorkHis.setCreateTime(date);
		cmPullWorkHis.setPullWork(state);
		cmPullWorkHis.setUserId(userId);
		session.save(cmPullWorkHis);
		BusiMatchControlYq busiMatchControlYq = new BusiMatchControlYq();
		if (state == 1&&StringUtils.isNotEmpty(pointX)&&StringUtils.isNotEmpty(pointY)) {
			Criteria ca = session.createCriteria(CmUserInfoPull.class);
			ca.add(Restrictions.eq("userId", userId));
			CmUserInfoPull cmUserInfoPull = (CmUserInfoPull) ca.uniqueResult();
			if(cmUserInfoPull==null){
				throw new BusinessException("没有该用户信息！");
			}
			Integer loadNum=0;
			if(cmUserInfoPull.getSingularNum()!=null){
				loadNum = cmUserInfoPull.getSingularNum().intValue();
			}
			long tenantId=-1;
			if(cmUserInfoPull.getTenantId()>0){
			   tenantId=cmUserInfoPull.getTenantId();
			}
			List<SysStaticData> list = SysStaticDataUtil.getSysStaticDataList("DEFAULT_SINGULAR_NUM");
			long defaultSingularNum = 0;
			if (cmUserInfoPull.getDefaultSingularNum() != null) {
				defaultSingularNum = cmUserInfoPull.getDefaultSingularNum();
			}else {
				if (list != null && list.size() > 0) {
					defaultSingularNum = Long.valueOf(list.get(0).getCodeValue());
				}
			}
			//抢单添加逻辑
			BusiGrabControlOut.addUserInfo(userId, Double.parseDouble(pointX), Double.parseDouble(pointY),
						tenantId, loadNum.intValue(),defaultSingularNum, 1, base.getTelphone());
//			busiMatchControlYq.loginAndWork(userId, tenantId, loadNum, Double.parseDouble(pointX),Double.parseDouble(pointY));
		}
		if(state==0){
			BusiGrabControlOut.updateUserInfoRest(userId, 0);
//			自动派单的逻辑
//			busiMatchControlYq.rest(userId);
		}
		Map<String, Object> mapContent = new HashMap<String, Object>();
		mapContent.put("info", "Y");
		return mapContent;

	}

	/**
	 * 拉包工上传身份证
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> uploadIDCard(Map<String, Object> inParam) throws Exception {
		Session session = SysContexts.getEntityManager();
		BaseUser base = SysContexts.getCurrentOperator();
		long userId = base.getUserId();
		String frontIdCard = DataFormat.getStringKey(inParam, "frontIdCard");
		String backIdCard = DataFormat.getStringKey(inParam, "backIdCard");
		CmUserInfoPull cmUserInfoPull = getByUserPull(userId);
		if (cmUserInfoPull == null) {
			throw new BusinessException("没有该用户信息");
		}
		if (StringUtils.isNotEmpty(frontIdCard)) {
			cmUserInfoPull.setFrontIdCard(frontIdCard);
		}
		if (StringUtils.isNotEmpty(backIdCard)) {
			cmUserInfoPull.setBackIdCard(backIdCard);
		}
		// 身份证正反面Id都不为空拉包工状态改为审核中
		if (cmUserInfoPull.getFrontIdCard() != null && cmUserInfoPull.getBackIdCard() != null) {
			cmUserInfoPull.setPullState(SysStaticDataEnumYunQi.PULL_STATE.AUDIT);
		}
		session.update(cmUserInfoPull);
		Map map = new HashMap();
		map.put("info", "Y");
		return map;

	}

	/**
	 * 根据userId查找拉包工
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public CmUserInfoPull getByUserPull(long userId) throws Exception {
		Session session = SysContexts.getEntityManager();
		Criteria ca = session.createCriteria(CmUserInfoPull.class);
		ca.add(Restrictions.eq("userId", userId));
		List<CmUserInfoPull> list = ca.list();
		if (list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 拉包工列表
	 * 
	 * @param inParam
	 * @return
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> queryCmUserInfoPull(Map<String, Object> inParam) {
		CmUserInfoPullSV pullSV = (CmUserInfoPullSV) SysContexts.getBean("cmUserInfoPullSV");
		IPage page =   PageUtil.gridPage(pullSV.queryCmUserInfoPull(inParam));
		List<Object[]> list = (List<Object[]>)page.getThisPageElements();
		List<CmUserInfoPullParamOut> outList = new ArrayList<CmUserInfoPullParamOut>();
		if (list != null && list.size() > 0) {
			for (Object[] temp : list) {
				CmUserInfoPullParamOut out = new CmUserInfoPullParamOut();
				out.setUserId(temp[0] != null ? Long.parseLong(String.valueOf(temp[0])) : -1);
				out.setTenantName(temp[1] != null ? String.valueOf(temp[1]) : "");
				out.setUserName(temp[2] != null ? String.valueOf(temp[2]) : "");
				out.setBillId(temp[3] != null ? String.valueOf(temp[3]) : "");
				String address = (temp[4] != null ? String.valueOf(temp[4]) : "")
						+ (temp[5] != null ? String.valueOf(temp[5]) : "")
						+(temp[12] != null ? String.valueOf(temp[12]) : "");
				out.setAddress(address);
				long paymentType =temp[6] != null ? Long.valueOf(String.valueOf(temp[6])) : -1;
				out.setPaymentTypeString(SysStaticDataUtil.getSysStaticDataCodeName("RECEI_TYPE_YQ", String.valueOf(paymentType)));
				out.setBankName(temp[7] != null ? String.valueOf(temp[7]) : "");
				
				
				out.setPullStateString(temp[10] != null
						? SysStaticDataUtil.getSysStaticDataCodeName("PULL_STATE_YQ", String.valueOf(temp[10])) : "");
				out.setCooperationMode(temp[11] != null ? SysStaticDataUtil.getSysStaticDataCodeName("COOPERATION_MODE_YQ", String.valueOf(temp[11])) : "");
				
				if(paymentType == SysStaticDataEnumYunQi.RECEI_TYPE_YQ.BANK_RECEI_TYPE_YQ){
					out.setAccountName(temp[14] != null ? String.valueOf(temp[14]) : "");//持卡人/帐号名
					out.setBankICard(temp[9] != null ? String.valueOf(temp[9]) : "");//卡号/帐号
				}else{
					out.setAccountName(temp[13] != null ? String.valueOf(temp[13]) : "");
					out.setBankICard(temp[8] != null ? String.valueOf(temp[8]) : "");//卡号/帐号
				}
				String jobNumber = temp[15] != null ? String.valueOf(temp[15]) : "";
				String city = temp[17] != null ? String.valueOf(temp[17]) : "";
				out.setJobNumber(CommonUtil.jobNumberRepByCity(jobNumber, city));
				out.setDefaultSingularNum(temp[16] != null ? String.valueOf(temp[16]) : "");
				outList.add(out);
			}
		}
		page.setThisPageElements(outList);
		Pagination<Object[]> pages = new Pagination<Object[]>(page);
		Map<String, Object> map = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		return map;
	}
	/**
	 * 拉包公司拉黑用户
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public String isBlackPull(Map<String,Object> inParam)throws Exception{
		long userId = DataFormat.getLongKey(inParam,"userId");
		String remark = DataFormat.getStringKey(inParam, "remark");
		CmUserInfoPull pull = getByUserPull(userId);
		if(pull == null){
			throw new BusinessException("获取拉包工用户信息错误！");
		}
		//查询拉包工用户信息
		CmUserInfoTF cmUserInfoTF = (CmUserInfoTF) SysContexts.getBean("cmUserInfoTF");
		CmUserInfo cmUserInfo = cmUserInfoTF.getUserInfo(userId, SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL);
		if(cmUserInfo == null){
			throw new BusinessException("获取用户信息错误！");
		}
		
		//保存拉黑表
		CmPullBlackSV cmPullBlackSV = (CmPullBlackSV) SysContexts.getBean("cmPullBlackSV");
		CmPullBlack black = cmPullBlackSV.getCmPullBlackByUserId(userId);
		//是否已经存在拉黑关系了
		if(black == null){
			black = new CmPullBlack();
			black.setBill(cmUserInfo.getLoginAcct());
			black.setName(cmUserInfo.getUserName());
			black.setCreateDate(new Date());
			black.setRemark(remark);
			black.setStatus(SysStaticDataEnumYunQi.STS.VALID);
			black.setType(SysStaticDataEnumYunQi.BLACK_TYPE.BLACK_COM);
			black.setUserId(userId);
		}else if(black != null){
			black.setRemark(remark);
			black.setType(SysStaticDataEnumYunQi.BLACK_TYPE.BLACK_COM);
			black.setStatus(SysStaticDataEnumYunQi.STS.VALID);
		}
		cmPullBlackSV.doSavePull(black);
		return "Y";
	}
	/**
	 * 通过userId查询拉包工信息
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> getCmUserInfoPull(Map<String,Object> inParam)throws Exception{
		
		long userId = DataFormat.getLongKey(inParam,"userId");
		if(userId < 0){
			throw new BusinessException("请传入用户编码！");
		}
		CmUserInfoYunQiTF cmUserInfoYunQiTF = (CmUserInfoYunQiTF) SysContexts.getBean("cmUserInfoYunQiTF");
		CmUserInfo cmUserInfo = cmUserInfoYunQiTF.getCmUserInfo(userId);
		CmUserInfoPull pull =  getByUserPull(userId);
		AcPaymentMethodTF acPaymentMethodTF = (AcPaymentMethodTF) SysContexts.getBean("acPaymentMethodTF");
		AcPaymentMethod payment = acPaymentMethodTF.getAcPaymentMethodByUserId(userId);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userInfo", cmUserInfo);
		map.put("pull", pull);
		map.put("payment", payment);
		return map;
	}
	/**
	 * web新增与修改拉包工
	 */
	public String doSavePull(Map<String,Object> inParam) throws Exception{
		long userId = DataFormat.getLongKey(inParam,"userId");
		String userName = DataFormat.getStringKey(inParam, "userName");
		if(StringUtils.isEmpty(userName)){
			throw new BusinessException("请输入拉包工名称！");
		}
		String loginAcct = DataFormat.getStringKey(inParam, "loginAcct");
		if(StringUtils.isEmpty(loginAcct)){
			throw new BusinessException("请输入拉包工手机！");
		}
		if(!CommonUtil.isCheckPhone(loginAcct)){
			throw new BusinessException("请输入正确的手机号码！");
		}
		String jobNumber = DataFormat.getStringKey(inParam, "jobNumber");
		if(StringUtils.isNotEmpty(jobNumber) && jobNumber.length() != 5){
			throw new BusinessException("工号必须为五位数！");
		}
		long city = DataFormat.getLongKey(inParam, "city");
		if(city < 0){
			throw new BusinessException("请选择服务城市！"); 
		}
		long defaultSingularNum = DataFormat.getLongKey(inParam,"defaultSingularNum");
		if(defaultSingularNum < 0){
			throw new BusinessException("请输入最大接单数！");
		}
		long tenantId = DataFormat.getLongKey(inParam,"tenantId");
		BaseUser baseUser = SysContexts.getCurrentOperator();
		if(tenantId < 0){
			if(baseUser.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.CHAIN){
				throw new BusinessException("请选择归属专线公司！");
			}else{
				throw new BusinessException("请选择归属拉包工公司！");
			}
			
		}
		CmUserSV cmUserSV = (CmUserSV) SysContexts.getBean("cmUserSv");
		CmUserInfoPull cmUserInfoPull = null;
		CmUserInfo cmUserInfo = null;
		AcPaymentMethod acPaymentMethod = null;
		CmUserInfoTF cmUserInfoTF = (CmUserInfoTF) SysContexts.getBean("cmUserInfoTF");
		AcPaymentMethodTF acPaymentMethodTF = (AcPaymentMethodTF) SysContexts.getBean("acPaymentMethodTF");
		Integer[] loginType = new Integer[]{SysStaticDataEnum.LOGIN_TYPE.APP,SysStaticDataEnum.LOGIN_TYPE.WEB_AND_APP}; 
		CmUserInfo checkUser = cmUserSV.queryUserByAcctYQ(loginAcct,loginType);
		CmUserInfoPullSV cmUserInfoPullSV = (CmUserInfoPullSV) SysContexts.getBean("cmUserInfoPullSV");
		String jobNumberCity = "";
		if(userId < 0){
			if (checkUser != null && (checkUser.getLoginType() == 2 || checkUser.getLoginType() == 3)) {
				throw new BusinessException("手机号码：" + loginAcct + "已经存在了！");
			}
			//判断工号唯一性，按租户隔离
			if(StringUtils.isNotEmpty(jobNumber)){
				jobNumberCity = city + jobNumber;
				if(cmUserInfoPullSV.checkJobNumber(jobNumberCity,-1)){
					throw new BusinessException("输入的工号【"+jobNumber+"】已经存在了，请重新输入");
				}
			}
			cmUserInfo = new CmUserInfo();
			cmUserInfoPull = new CmUserInfoPull();
			//acPaymentMethod = new AcPaymentMethod();
			BeanUtils.populate(cmUserInfoPull, inParam);
			BeanUtils.populate(cmUserInfo, inParam);
		
			//保存用户信息
			cmUserInfo.setLoginPwd(K.j_s("123456"));
			cmUserInfo.setLoginType(SysStaticDataEnumYunQi.LOGIN_TYPE.WEB_APP);
			cmUserInfo.setState(SysStaticDataEnumYunQi.STS.VALID);
			cmUserInfo.setCreateTime(new Date());
			cmUserInfo.setUserType(SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL);
			
			//保存拉包工信息
			cmUserInfoPull.setPullState(SysStaticDataEnumYunQi.PULL_STATE.NOT_CERTIFIED);
			cmUserInfoPull.setPullWork(SysStaticDataEnumYunQi.PULL_WORK_YUNQI.PULL_WORK0);
			cmUserInfoPull.setSingularNum(0L);
			
		}else{
			cmUserInfo = cmUserInfoTF.getUserInfo(userId, SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL);
			acPaymentMethod = acPaymentMethodTF.getAcPaymentMethodByUserId(userId);
			if(cmUserInfo == null){
				throw new BusinessException("没有该用户的信息！");
			}
			if(!cmUserInfo.getLoginAcct().equals(loginAcct) && checkUser != null && (checkUser.getLoginType() == 2 || checkUser.getLoginType() == 3)){
				throw new BusinessException("该手机已经存在了，请重新填写！");
			}
			//判断工号唯一性，按租户隔离
			if(StringUtils.isNotEmpty(jobNumber)){
				jobNumberCity = city + jobNumber;
				if(cmUserInfoPullSV.checkJobNumber(jobNumberCity,userId)){
					throw new BusinessException("输入的工号【"+jobNumber+"】已经存在了，请重新输入");
				}
			}
			cmUserInfoPull = getByUserPull(userId);
			if(cmUserInfoPull == null){
				throw new BusinessException("没有该拉包工的用户信息！");
			}
			BeanUtils.populate(cmUserInfoPull, inParam);
			BeanUtils.populate(cmUserInfo, inParam);
		}
		//保存用户信息
		
		CmUserInfoYunQiSV cmUserInfYunQiSV = (CmUserInfoYunQiSV) SysContexts.getBean("cmUserInfoYunQiSV");
		cmUserInfYunQiSV.doSaveOrUpdate(cmUserInfo);
		
		//保存拉包工信息
		cmUserInfoPull.setUserId(cmUserInfo.getUserId());
		cmUserInfoPull.setProvinceName(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(cmUserInfoPull.getProvince())).getName());
		cmUserInfoPull.setCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(cmUserInfoPull.getCity())).getName());
		cmUserInfoPull.setDistrictName(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(cmUserInfoPull.getDistrict())).getName());
		cmUserInfoPull.setJobNumber(jobNumberCity);
		cmUserInfoPullSV.doSaveOrUpdate(cmUserInfoPull);
		int paymentType = DataFormat.getIntKey(inParam,"paymentType");
		//保存支付信息
		if(paymentType >= 0){
			String bankHolder = DataFormat.getStringKey(inParam, "bankHolder");
			String bankCode = DataFormat.getStringKey(inParam, "bankCode");
			String bankICard = DataFormat.getStringKey(inParam, "bankICard");
			String bankName = SysStaticDataUtil.getSysStaticDataCodeName("BANK_TYPE_YUNQI", bankCode);
			acPaymentMethodTF.doSavePaymentByPull(acPaymentMethod, cmUserInfo, paymentType, bankName, bankCode, bankICard, bankHolder);
		}
		
		
		BaseUser user = SysContexts.getCurrentOperator();
		// 加租户与拉包工的关系
		if(userId < 0){
			CmUserOrgRelTF cmUserOrgRelTF = (CmUserOrgRelTF) SysContexts.getBean("cmUserOrgRelTF");
			CmUserOrgRel cmUserOrgRel = new CmUserOrgRel();
			cmUserOrgRel.setTenantId(cmUserInfoPull.getTenantId());
			cmUserOrgRel.setUserId(cmUserInfo.getUserId());
			//Organization org = OraganizationCacheDataUtil.getOrganizationByTenantId(cmUserInfoPull.getTenantId());
			cmUserOrgRel.setOrgId(user.getOrgId());
			cmUserOrgRel.setOpDate(new Date());
			cmUserOrgRel.setOpId(user.getUserId());
			cmUserOrgRelTF.doSave(cmUserOrgRel);
			// --------
			
			AcWalletRelSV acWalletRelSV = (AcWalletRelSV) SysContexts.getBean("acWalletRelSV");
			List<AcWalletRel> listRel = acWalletRelSV.checkAcWalletRel(userId, tenantId);
			if(listRel == null || listRel.size() <= 0){
				AcWalletRel acWalletRel = new AcWalletRel();
				acWalletRel.setCreateTime(new Date());
				acWalletRel.setPullTenantId(tenantId);
				acWalletRel.setTenantId(tenantId);
				acWalletRel.setUserId(cmUserInfoPull.getUserId());
				acWalletRelSV.doSave(acWalletRel);
			}
		}
		
		
		return "Y";
	}
	/**
	 * 修改服务地区(app)
	 * @param province
	 * @param city
	 * @param district
	 * @return
	 * @throws Exception
	 */
	public String doUpdateSer(long province,long city,long district)throws Exception{
		long userId =	SysContexts.getCurrentOperator().getUserId();
		CmUserInfoPullSV cmUserInfoPullSV = (CmUserInfoPullSV) SysContexts.getBean("cmUserInfoPullSV");
		String cityOld = "";
		String jobNumber = "";
		CmUserInfoPull pull = getCmUserInfoPull(userId);
		if (StringUtils.isNotEmpty(pull.getJobNumber())) {
			cityOld = String.valueOf(pull.getCity());
			jobNumber = CommonUtil.jobNumberRepByCity(pull.getJobNumber(), cityOld);
			pull.setJobNumber(city+jobNumber);
		}
		pull.setProvince(province);
		pull.setProvinceName(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(province)).getName());
		pull.setCity(city);
		pull.setCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(city)).getName());
		pull.setDistrict(district);
		pull.setDistrictName(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(district)).getName());
		return cmUserInfoPullSV.doSaveOrUpdate(pull);
	}
	
	/**
	 * 查询拉包工是否被平台拉黑
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public boolean queryPullWorkerIsBlack(long userId)throws Exception{
		Session session=SysContexts.getEntityManager();
		Criteria ca=session.createCriteria(CmPullBlack.class);
		ca.add(Restrictions.eq("userId", userId));
		ca.add(Restrictions.eq("type", SysStaticDataEnum.PULL_BLACK_TYPE_YQ.PULL_BLACK_TYPE_PLATFORM));
		ca.add(Restrictions.eq("status", SysStaticDataEnumYunQi.STS.VALID));
		CmPullBlack cmPullBlack=(CmPullBlack)ca.uniqueResult();
		return cmPullBlack!=null?true:false;
	}
	/**
	 * 失效用户
	 * @param userId
	 * @return
	 */
	public String delUserInfoPull(Map<String,Object> inParam){
		CmUserInfoYunQiSV cmUserInfoYunQiSV = (CmUserInfoYunQiSV) SysContexts.getBean("cmUserInfoYunQiSV");
		long userId = DataFormat.getLongKey(inParam,"userId");
		if(userId < 0){
			throw new BusinessException("请传入用户编号！");
		}
		return cmUserInfoYunQiSV.loseCmUserInfo(userId);
	}
	/**
	 * 审核拉包工
	 */
	public String verifyCmUserInfoPull(Map<String,Object> inParam){
		CmUserInfoPullSV cmUserInfoPullSV = (CmUserInfoPullSV) SysContexts.getBean("cmUserInfoPullSV");
		long userId = DataFormat.getLongKey(inParam,"userId");
		if(userId < 0){
			throw new BusinessException("请传入用户编号！");
		}
		int verify = DataFormat.getIntKey(inParam,"verify");
		if(verify < 0){
			throw new BusinessException("请选择审核是否通过！");
		}
		int pullState = -1 ;
		if(verify == 1){
			pullState = SysStaticDataEnumYunQi.PULL_STATE.CERTIFIED;
		}else{
			pullState = SysStaticDataEnumYunQi.PULL_STATE.NOT_APPROVED;
		}
		String remark = DataFormat.getStringKey(inParam, "remark");
		cmUserInfoPullSV.verifyCmUserInfoPull(userId,pullState,remark);
		return "Y";
	}
	/**
	 * 查询拉包工
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> getCmUserInfoPullByBill(Map<String,Object> inParam){
		CmUserInfoPullSV cmUserInfoPullSV = (CmUserInfoPullSV) SysContexts.getBean("cmUserInfoPullSV");
		String loginAcct = DataFormat.getStringKey(inParam, "loginAcct");
		IPage page = PageUtil.gridPage(cmUserInfoPullSV.getCmUserInfoPullByBill(loginAcct, SysContexts.getCurrentOperator().getTenantId(),0,0));
		List<Object[]> list = page.getThisPageElements();
		List<AcAccountWalletTipOut> outList = new ArrayList<AcAccountWalletTipOut>();
		if(list != null && list.size() > 0){
			for (Object[] objects : list) {
				AcAccountWalletTipOut out = new AcAccountWalletTipOut();
				out.setUserId(objects[0] != null ? Long.valueOf(String.valueOf(objects[0])) : -1);
				out.setLoginAcct(objects[1] != null ? String.valueOf(objects[1]) : "");
				out.setUserName(objects[2] != null ? String.valueOf(objects[2]) : "");
				out.setPaymentTypeString(objects[3] != null ? String.valueOf(objects[3]) : "");
				out.setBankCard(objects[4] != null ? String.valueOf(objects[4]) : "");
				out.setBankHolder(objects[5] != null ? String.valueOf(objects[5]) : "");
				out.setAccountNum(objects[6] != null ? String.valueOf(objects[6]) : "");
				out.setNotPresentFee(objects[7] != null ? String.valueOf(CommonUtil.getDoubleFormatLongMoney(Long.valueOf(String.valueOf(objects[7])), 2)) : "");
				out.setCashWithdrawalFee(objects[8] != null ? String.valueOf(CommonUtil.getDoubleFormatLongMoney(Long.valueOf(String.valueOf(objects[8])), 2)) : "");
				out.setAlreadyPresentFee(objects[9] != null ? String.valueOf(CommonUtil.getDoubleFormatLongMoney(Long.valueOf(String.valueOf(objects[9])), 2)) : "");
				//out.setRemark(objects[10] != null ? String.valueOf(objects[10]) : "");
				outList.add(out);
			}
		}
		page.setThisPageElements(outList);
		Pagination<AcAccountWalletTipOut> p = new Pagination<AcAccountWalletTipOut>(page);
		return JsonHelper.parseJSON2Map(JsonHelper.json(p));
	}
	
	
	/**
	 * 审核费用拉包工信息
	 * @param inParam
	 * @return
	 */
	public AcAccountWalletTipOut getAccountPullByUserId(Map<String,Object> inParam){
		CmUserInfoPullSV cmUserInfoPullSV = (CmUserInfoPullSV) SysContexts.getBean("cmUserInfoPullSV");
		long userId = DataFormat.getLongKey(inParam, "userId");
		long accountId = DataFormat.getLongKey(inParam, "accountId");
		List<Object[]> list = cmUserInfoPullSV.getCmUserInfoPullByBill(null, SysContexts.getCurrentOperator().getTenantId(),userId,accountId).list();
		AcAccountWalletTipOut out = new AcAccountWalletTipOut();
		if(list != null && list.size() > 0){
			for (Object[] objects : list) {
				out.setUserId(objects[0] != null ? Long.valueOf(String.valueOf(objects[0])) : -1);
				out.setLoginAcct(objects[1] != null ? String.valueOf(objects[1]) : "");
				out.setUserName(objects[2] != null ? String.valueOf(objects[2]) : "");
				out.setPaymentTypeString(objects[3] != null ? String.valueOf(objects[3]) : "");
				out.setBankCard(objects[4] != null ? String.valueOf(objects[4]) : "");
				out.setBankHolder(objects[5] != null ? String.valueOf(objects[5]) : "");
				out.setAccountNum(objects[6] != null ? String.valueOf(objects[6]) : "");
				out.setNotPresentFee(objects[7] != null ? String.valueOf(CommonUtil.getDoubleFormatLongMoney(Long.valueOf(String.valueOf(objects[7])), 2)) : "");
				out.setCashWithdrawalFee(objects[8] != null ? String.valueOf(CommonUtil.getDoubleFormatLongMoney(Long.valueOf(String.valueOf(objects[8])), 2)) : "");
				out.setAlreadyPresentFee(objects[9] != null ? String.valueOf(CommonUtil.getDoubleFormatLongMoney(Long.valueOf(String.valueOf(objects[9])), 2)) : "");
				out.setRemark(objects[10] != null ? String.valueOf(objects[10]) : "");
			}
		}
		return out;
	}
	/**
	 * 增加订单数
	 * @param userId
	 */
	public CmUserInfoPull addSingularNum(long userId){
		Session session = SysContexts.getEntityManager();
		Query query = session.createSQLQuery(" update cm_user_info_pull set SINGULAR_NUM = IF(SINGULAR_NUM is null or SINGULAR_NUM <= 0,1,SINGULAR_NUM + 1) where user_id =  "+userId);
		int i = query.executeUpdate();
		if (i<=0) {
			log.error("更新失败！没该拉包工编号的信息！");
		}
		session.flush();
		session.clear();
		CmUserInfoPull pull = new CmUserInfoPull();
		Query querySql = session.createSQLQuery("select * from cm_user_info_pull where user_id = :userId").addEntity(CmUserInfoPull.class);
		querySql.setParameter("userId", userId);
		List<Object> list = querySql.list();
		if(list!=null&&list.size()>0){
			pull = (CmUserInfoPull) list.get(0);
		}
		return pull;
	}
	
	/**
	 * 减少订单数
	 * @param userId
	 */
	public CmUserInfoPull reduceSingularNum(long userId){
		Session session = SysContexts.getEntityManager();
		Query query = session.createSQLQuery(" update cm_user_info_pull set SINGULAR_NUM = IF(SINGULAR_NUM is null or SINGULAR_NUM <= 0,0,SINGULAR_NUM -1) where user_id =  "+userId);
		int i =query.executeUpdate();
		if (i<=0) {
			log.error("更新失败！没该拉包工编号的信息！");
		}
		session.flush();
		session.clear();
		CmUserInfoPull pull = new CmUserInfoPull();
		Query querySql = session.createSQLQuery("select * from cm_user_info_pull where user_id = :userId").addEntity(CmUserInfoPull.class);
		querySql.setParameter("userId", userId);
		List<Object> list = querySql.list();
		if(list!=null&&list.size()>0){
			pull = (CmUserInfoPull) list.get(0);
		}
		return pull;
	}
	/**
	 * 注销用户
	 * @param inParam
	 * @return
	 */
	public String delCmUserInfoPull(Map<String,Object> inParam){
		long userId = DataFormat.getLongKey(inParam,"userId");
		if(userId < 0){
			throw new BusinessException("请选择删除的拉包工信息！");
		}
		CmUserInfoPullSV cmUserInfoPullSV = (CmUserInfoPullSV) SysContexts.getBean("cmUserInfoPullSV");
		cmUserInfoPullSV.delCmUserInfoPull(userId);
		return "Y";
	}
}
