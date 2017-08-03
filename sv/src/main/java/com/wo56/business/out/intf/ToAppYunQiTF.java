package com.wo56.business.out.intf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.components.fdfs.vo.SysAttach;
import com.framework.components.redis.RemoteCacheUtil;
import com.framework.core.SysContexts;
import com.framework.core.encrypt.K;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.interfaces.ISysLogin;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.wo56.business.ac.interfaces.AcAccountWalletTF;
import com.wo56.business.ac.interfaces.AcMyWalletTF;
import com.wo56.business.ac.interfaces.AcPaymentMethodTF;
import com.wo56.business.ac.interfaces.AcWalletRelTF;
import com.wo56.business.ac.vo.AcWalletRel;
import com.wo56.business.ac.vo.out.AcAccountWalletOut;
import com.wo56.business.ac.vo.out.AcMyWalletListOut;
import com.wo56.business.ac.vo.out.AcWalletRelOut;
import com.wo56.business.app.intf.AppPushBillRelatTF;
import com.wo56.business.cm.impl.CmUserInfoYunQiSV;
import com.wo56.business.cm.intf.CmAddressTF;
import com.wo56.business.cm.intf.CmCustomerYQTF;
import com.wo56.business.cm.intf.CmDriverInfoTF;
import com.wo56.business.cm.intf.CmMailListTF;
import com.wo56.business.cm.intf.CmUserInfoPullTF;
import com.wo56.business.cm.intf.CmUserInfoTF;
import com.wo56.business.cm.intf.CmUserInfoYunQiTF;
import com.wo56.business.cm.intf.CmVehicleInfoTF;
import com.wo56.business.cm.intf.OrganizationTF;
import com.wo56.business.cm.intf.OrganizationYunQiTF;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserInfoPull;
import com.wo56.business.cm.vo.CmUserOrgRel;
import com.wo56.business.grabOrder.out.BusiGrabControlOut;
import com.wo56.business.ord.impl.OrderFeeRuleSV;
import com.wo56.business.ord.impl.OrderInfoSV;
import com.wo56.business.ord.intf.OrdDepartInfoYqTF;
import com.wo56.business.ord.intf.OrdIndexStatisticsTF;
import com.wo56.business.ord.intf.OrdOpLogYunQiTF;
import com.wo56.business.ord.intf.OrdOrdersInfoTF;
import com.wo56.business.ord.intf.OrderInfoChildTF;
import com.wo56.business.ord.intf.OrderInfoTF;
import com.wo56.business.ord.vo.OrdBusiPerson;
import com.wo56.business.ord.vo.OrdOrdersInfo;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.route.impl.RouteTowardsSV;
import com.wo56.business.route.intf.RouteTF;
import com.wo56.business.route.vo.RouteAreaRelCfg;
import com.wo56.business.route.vo.RouteTowards;
import com.wo56.business.sche.index.intf.IndexTF;
import com.wo56.business.sys.impl.SysLoginLogSV;
import com.wo56.business.sys.intf.PortalBusiTF;
import com.wo56.business.sys.intf.SysSmsSendTF;
import com.wo56.business.sys.intf.SysTenantRegisterTF;
import com.wo56.common.consts.EnumConstsYQ;
import com.wo56.common.consts.LoginConst;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.EncryPwdUtil;
import com.wo56.common.utils.GenValidateCode;
import com.wo56.common.utils.OraganizationCacheDataUtil;
import com.wo56.common.utils.SmsUtil;

public class ToAppYunQiTF {
	private static final int  userTypes[] = new int[]{3,7};
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final Log log = LogFactory.getLog(ToAppYunQiTF.class);
	private static final int busiTypes[] = new int[]{1,2,3,4};
	 /***
	    * 
	    * 接口编码:601001
	    * 接口入参：
	    * 		  password	                    注册密码（需加密（base64））
		*         billId	                    注册手机号码
		*         userName			姓名
		*         captcha           注册短信验证码
		*         userType          App用户注册类型（1、普通（普通司机）2、货主（普通货主））
		*         pushAppId	                    推送系统APPID
		*         pushChannelId	          推送系统ochannelId
		*         pushUserId	          推送系统user_id
		*         appVerNo	        APP版本号
		*         appOsVer	        APP系统版本号
		*         mobileType	          手机型号
		*         mobileBrand	          手机品牌
		*
		*接口出参
		*         pushRelatId	          关系ID
		*         billId	                    手机号
		*         loginAcct	                    登录账号
		*         userType	                    用户类型	
		*         userPicture	          用户头像url
		*         linkMan	                    用户名称
		*         orgId	                              二级组织编号
		*         rootOrgId	                    顶级组织编号
	    * 
	    * 用户注册接口（注册成功就登录）
	    * 
	    */
	@SuppressWarnings("rawtypes")
	public Map  userRegister(Map<String , String> inParam) throws Exception {
		    String billId = DataFormat.getStringKey(inParam, "billId");
			String password = DataFormat.getStringKey(inParam, "password");
			int userType = DataFormat.getIntKey(inParam, "userType");//注册用户类型
//			String captcha =  DataFormat.getStringKey(inParam, "captcha");//验证码
			String userName = DataFormat.getStringKey(inParam, "userName");
			String pushAppId = DataFormat.getStringKey(inParam, "pushAppId");
			String pushChannelId = DataFormat.getStringKey(inParam, "pushChannelId");
			String pushUserId = DataFormat.getStringKey(inParam, "pushUserId");
			String appVerNo = DataFormat.getStringKey(inParam, "appVerNo");
			String appOsVer = DataFormat.getStringKey(inParam, "appOsVer");
			String mobileType = DataFormat.getStringKey(inParam, "mobileType");
			String mobileBrand = DataFormat.getStringKey(inParam, "mobileBrand");
			AppPushBillRelatTF busiSV = (AppPushBillRelatTF)SysContexts.getBean("appPushBillRelatTF");
			//long tenantId = DataFormat.getLongKey(inParam, "tenantId");
			
//			if(StringUtils.isEmpty(captcha)){
//				throw new BusinessException ("请输入注册短信验证码！");
//			}
			
			if(!CommonUtil.isContains(userTypes, userType)){
				throw new BusinessException ("用户注册类型无效！");
			}
			//解密
			password=EncryPwdUtil.pwdDecryp(password);
			if(!CommonUtil.isCheckPwd(password)){
				throw new BusinessException ("密码长度6～16个字符，字母不区分大小写！");
			}
			//校验验证码
//			if(!GenValidateCode.checkCode(billId,GenValidateCode.REGINSTER, captcha)){
//				throw new BusinessException("输入的验证码不对，请重新输入！");
//			}
			//保存用户信息
			Map<String,Object> saveUserInfo = new HashMap<String,Object>();
			saveUserInfo.put("loginPwd", password);
			saveUserInfo.put("loginAcct", billId);
			saveUserInfo.put("userType", userType);
			saveUserInfo.put("userName", userName);
			saveUserInfo.put("channelType", SysContexts.getChannelType());
			CmUserInfoPullTF cmUserInfoPullTF = (CmUserInfoPullTF) SysContexts.getBean("cmUserInfoPullTF");
			CmUserInfo userInfo = cmUserInfoPullTF.doSaveAppCmUserInfo(saveUserInfo);
			//用户为拉包工时，走的逻辑
			CmUserInfoPull pull = null;
			if(userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
				pull = cmUserInfoPullTF.doSaveOrUpdate(userInfo.getUserId(), DataFormat.getLongKey(inParam,"province"), DataFormat.getLongKey(inParam,"city"), 
						DataFormat.getLongKey(inParam,"district"), DataFormat.getLongKey(inParam,"tenantId"),userInfo);
			}
			//获取tokeId
			String rd=setUserInfoToSession(userInfo);
			//记录推送关系信息
			String pushRelatId = busiSV.dealPushRealet(pushChannelId, billId, pushAppId, pushUserId);
			//出参infoVo
			Map<String,Object> infoVo = new HashMap<String,Object>();
			infoVo.put("userId", userInfo.getUserId());
			infoVo.put("userType", userInfo.getUserType());
			infoVo.put("pushRelatId",pushRelatId);
			infoVo.put("tokenId",rd);
			infoVo.put("billId",billId);
			infoVo.put("loginAcct",userInfo.getLoginAcct());
			infoVo.put("userPic",userInfo.getUserPic() != null ? userInfo.getUserPic() : "");
			infoVo.put("userPicture",userInfo.getUserPic() != null ? getAttachFile(userInfo.getUserPic()) : "");
			infoVo.put("linkMan", userInfo.getUserName());
			infoVo.put("pullWork", pull != null ? pull.getPullWork() != null ? pull.getPullWork() : "" : "");
			infoVo.put("province", pull!=null ? pull.getProvince() != null ? pull.getProvince() : "" : "");
			infoVo.put("provinceName", pull!=null ? pull.getProvinceName() != null ? pull.getProvinceName() : "" : "");
			infoVo.put("city", pull!=null ? pull.getCity() != null ?pull.getCity() : "" : "");
			infoVo.put("cityName", pull!=null ? pull.getCityName() != null ? pull.getCityName() : "" : "");
			infoVo.put("district", pull!=null ? pull.getDistrict() != null ? pull.getDistrict() : "" : "");
			infoVo.put("districtName", pull!=null ? pull.getDistrictName() != null ? pull.getDistrictName() : "" : "");
			String channelType = SysContexts.getChannelType();
			//保存登录日志
			SysLoginLogSV loginLog = (SysLoginLogSV)SysContexts.getBean("sysLoginLogSV");
			loginLog.userLogins( channelType, appVerNo, appOsVer, mobileType, mobileBrand, null, null);
			return infoVo; 
	   }
	
	/**
	 * 接口编号:601000 
	 * @author lyy
	 * 登录app
	 */
	public Map<String, Object> login(Map<String, String> inParam)
			throws Exception {
		String billId = DataFormat.getStringKey(inParam, "billId");
		String password = DataFormat.getStringKey(inParam, "pwd");
		String pushAppId = DataFormat.getStringKey(inParam, "pushAppId");
		String pushChannelId = DataFormat.getStringKey(inParam, "pushChannelId");
		String pushUserId = DataFormat.getStringKey(inParam, "pushUserId");
		String appVerNo = DataFormat.getStringKey(inParam, "appVerNo");
		String appOsVer = DataFormat.getStringKey(inParam, "appOsVer");
		String mobileType = DataFormat.getStringKey(inParam, "mobileType");
		String mobileBrand = DataFormat.getStringKey(inParam, "mobileBrand");
		if(billId==null || billId.equals("")){
			throw new BusinessException("手机号码不能为空！");
		}
		if(StringUtils.isBlank(password)){
			throw new BusinessException("密码不能为空！");
		}
		if(!CommonUtil.isCheckPhone(billId)){
			throw new BusinessException("手机号码格式不正确！");
		}
		PortalBusiTF cmUserInfoTF = (PortalBusiTF)SysContexts.getBean("portalBusiTF");
		//解密
		password=EncryPwdUtil.pwdDecryption(password);
		inParam.put("loginAcct", billId);
		inParam.put("passWord", password);
		inParam.put("loginType", String.valueOf(SysStaticDataEnum.LOGIN_TYPE.APP)+","+String.valueOf(SysStaticDataEnum.LOGIN_TYPE.WEB_AND_APP));
		//该方法去判断
		CmUserInfo cmUserInfo= cmUserInfoTF.appLogin(inParam);
		if(cmUserInfo.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.CHAIN){
			throw new BusinessException("专线公司不能登陆app！");
		}else if(cmUserInfo.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PLATFORM){
			throw new BusinessException("平台用户不能登陆app！");
		}else if(cmUserInfo.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULLCOM){
			throw new BusinessException("拉包公司用户不能登陆app！");
		}else if(cmUserInfo.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.VIRTUAL_USER){
			throw new BusinessException("该账号未注册，请注册再登陆！");
		}
		//获取拉包工信息
		CmUserInfoPullTF pullTF = (CmUserInfoPullTF) SysContexts.getBean("cmUserInfoPullTF");
		if(pullTF.queryPullWorkerIsBlack(cmUserInfo.getUserId())){
			throw new BusinessException("用户已经被平台拉黑，联系工作人员！");
		} 
		
		CmUserInfoPull pull = pullTF.getCmUserInfoPull(cmUserInfo.getUserId());
		Map<String, Object> retMap=new HashMap<String, Object>();
		retMap.put("billId", cmUserInfo.getLoginAcct());
		retMap.put("linkMan", cmUserInfo.getUserName());
		retMap.put("loginAcct", cmUserInfo.getLoginAcct());
		retMap.put("userPic", cmUserInfo.getUserPic() == null ? "" : cmUserInfo.getUserPic());
		retMap.put("userPicture", getAttachFile(cmUserInfo.getUserPic()));
		retMap.put("userType", cmUserInfo.getUserType());
		retMap.put("pullWork", pull!=null ? pull.getPullWork() != null ? pull.getPullWork() : "" : "");
		retMap.put("province", pull!=null ? pull.getProvince() != null ? pull.getProvince() : "" : "");
		retMap.put("provinceName", pull!=null ? pull.getProvinceName() != null ? pull.getProvinceName() : "" : "");
		retMap.put("city", pull!=null ? pull.getCity() != null ?pull.getCity() : "" : "");
		retMap.put("cityName", pull!=null ? pull.getCityName() != null ? pull.getCityName() : "" : "");
		retMap.put("district", pull!=null ? pull.getDistrict() != null ? pull.getDistrict() : "" : "");
		retMap.put("districtName", pull!=null ? pull.getDistrictName() != null ? pull.getDistrictName() : "" : "");
		String rd=setUserInfoToSession(cmUserInfo);
		
		retMap.put("tokenId", rd);
		AppPushBillRelatTF busiSV = (AppPushBillRelatTF)SysContexts.getBean("appPushBillRelatTF");
		//记录推送关系信息
		String pushRelatId = busiSV.dealPushRealet(pushChannelId, billId, pushAppId, pushUserId);
		retMap.put("pushRelatId", pushRelatId);
		//保存登录日志
		String  channelType  = SysContexts.getChannelType();
		SysLoginLogSV loginLog = (SysLoginLogSV)SysContexts.getBean("sysLoginLogSV");
		loginLog.userLogins( channelType, appVerNo, appOsVer, mobileType, mobileBrand, null, null);
		//登陆拉包工时，需要调用接单数
//		if(cmUserInfo.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL && pull.getPullWork() == SysStaticDataEnumYunQi.PULL_WORK_YUNQI.PULL_WORK1){
//			String[] point = GpsUtil.getPositionByBillId(cmUserInfo.getLoginAcct());
//			if(point!=null){
//				double pointX = Double.parseDouble(point[0]);
//				double pointy = Double.parseDouble(point[1]);
//			} 
//			//BusiMatchControlYq.loginAndWork(cmUserInfo.getUserId(), pull.getTenantId(), Integer.parseInt(String.valueOf(pull.getSingularNum())), pointX, pointy);
//		}
		return retMap;
	}
	/**
	 * 获取图片地址
	 * @param flowId
	 * @author lyy
	 */
	private String getAttachFile(String flowId) throws Exception{
		Session session = SysContexts.getEntityManager(true);
		if (StringUtils.isNotEmpty(flowId)) {
			SysAttach sysAttach = (SysAttach) session.get(SysAttach.class,Long.valueOf(flowId));
			if(sysAttach !=null){
				return sysAttach.getFullPathUrl();
			}
		}
		return "";
	}
	/**
	 * 设置app登录或注册后，放到session的基本信息
	 * TODO 设置组织信息
	 * @param userInfo
	 * @author lyy
	 */
	private String setUserInfoToSession(CmUserInfo userInfo) throws Exception{
		BaseUser user=new BaseUser(); 
		Session session = SysContexts.getEntityManager(true);
		user.setOperId(userInfo.getUserId());
		user.setUserId(userInfo.getUserId());
		user.setUserName(userInfo.getUserName());
		user.setTelphone(userInfo.getLoginAcct());
		user.setUserType(userInfo.getUserType());
		
		Criteria ca = session.createCriteria(CmUserOrgRel.class);
		ca.add(Restrictions.eq("userId", userInfo.getUserId()));
		List<CmUserOrgRel> list = ca.list();
		if (list != null && list.size() > 0) {
			user.setOrgId(list.get(0).getOrgId());
			user.setTenantId(list.get(0).getTenantId());
		}
		Map<String, Object> attrs=new HashMap<String, Object>();
		attrs.put(LoginConst.BaseUserAttr.LOGIN_ACCOUNT, userInfo.getLoginAcct());
		user.setAttrs(attrs);
		//从session里面拿组织id和顶级组织id
		
		//支持不同账号同时在线
		Set remToken = RemoteCacheUtil.smembers(ISysLogin.SYS_USER_TOKEN_MAP +user.getUserId());
		Iterator<String> tokenIds= remToken.iterator();
		while (tokenIds.hasNext()) {
			String tokenId=tokenIds.next();
			RemoteCacheUtil.del(ISysLogin.SYS_PARTNER_INTF_TOKEN_PROFIX +tokenId);
		}
		if (StringUtils.isNotEmpty(userInfo.getOpenId())) {
			RemoteCacheUtil.del(EnumConstsYQ.WX_TEKON.WX_USER_TOKEN_PRE +userInfo.getOpenId());
		}
		return SysContexts.setCurrentOperator(user);
	}
	/**
	 * lyh
	 * 
	 * 发送手机验证码
	 * 601003
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map sendValidCode(Map<String, String> inParam) throws Exception{
		String billId=DataFormat.getStringKey(inParam, "billId");
		int busiType=DataFormat.getIntKey(inParam, "busiType");
		
		if(!CommonUtil.isContains(busiTypes, busiType)){
			throw new BusinessException ("发送短信类型无效！");
		}
		
		if(!CommonUtil.isCheckPhone(billId)){
			throw new BusinessException("输入的手机号码不正确！");
		}
		CmUserInfoYunQiTF userTF = (CmUserInfoYunQiTF) SysContexts.getBean("cmUserInfoYunQiTF");
		if(!userTF.checkPhone(billId) && busiType == 1){
			throw new BusinessException("号码已被注册，请登录！");
		}
		if(userTF.checkPhone(billId) && busiType == 3){
			throw new BusinessException("号码未注册，请输入正确的号码！");
		}
		String code= GenValidateCode.setPhoneCode(billId,String.valueOf(busiType));
		//TODO 发送验证
		SmsUtil.sendVaildCode(billId, code);
		Map map = new HashMap();
		map.put("info", "Y");
		return map;
	}
	/**
	 * 新增或修改收货/发货地址
	 * 接口编码：600000
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doSaveAddress(Map<String, String> inParam) throws Exception {
		SysContexts.getCurrentOperator().getUserId();
		CmAddressTF cmAddressTF= (CmAddressTF) SysContexts.getBean("cmAddressTF");
		return cmAddressTF.doSaveAddress(inParam);
	}
	/**
	 * 通讯录新增/修改
	 * 接口编码：600006
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doSaveMailList(Map<String, String> inParam) throws Exception {
		CmMailListTF cnMailListTF=(CmMailListTF)SysContexts.getBean("cmMailListTF");
		return cnMailListTF.doSaveMailList(inParam);
	}
	/**
	 * 拉包工获取专线或公司 -- 云启
	 * @param inParam
	 * @return
	 * @throws Exception
	 * 601004
	 */
	@SuppressWarnings("rawtypes")
	public Map getTenantByOrg(Map<String,String> inParam) throws Exception{
		return ((OrganizationYunQiTF)SysContexts.getBean("organizationYunQiTF")).getTenantByOrg(DataFormat.getLongKey(inParam,"tenantType"));
	}
	/**
	 * 通讯录删除
	 * 接口编码:600008
	 * @param id
	 * @throws Exception
	 */
	public Map deleteMailListById(Map<String, String> inParam) throws Exception {
		long id=DataFormat.getLongKey(inParam,"id");
		CmMailListTF cnMailListTF=(CmMailListTF)SysContexts.getBean("cmMailListTF");
		return cnMailListTF.deleteMailListById(id);
	}
	/**
	 * 通讯录列表
	 * 接口编码：600009
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doQueryMailList(Map<String, String> inParam) throws Exception {
		CmMailListTF cnMailListTF=(CmMailListTF)SysContexts.getBean("cmMailListTF");
		return cnMailListTF.doQueryMailList(inParam);
	}
	/**
	 * 通讯录拉黑
	 * 接口编码：600010
	 * @param inParam
	 * @throws Exception
	 */
	public Map doUpdatePullMailList(Map<String, Object> inParam) throws Exception {
		CmMailListTF cnMailListTF=(CmMailListTF)SysContexts.getBean("cmMailListTF");
		 return cnMailListTF.doUpdatePullMailList(inParam);
	}
	/**
	 * 通讯录模糊查询
	 * 接口编码：600008
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doQueryMailListByVarName(Map<String, String> inParam) throws Exception {
		CmMailListTF cnMailListTF=(CmMailListTF)SysContexts.getBean("cmMailListTF");
		return cnMailListTF.doQueryMailListByVarName(inParam);
	}
	
	/**
	 * 获取首页轮播的图片地址
	 * 601006
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getImageUrl(Map<String, Object> inParam) throws Exception{
		IndexTF indexTF = (IndexTF)SysContexts.getBean("indexTF");
		return indexTF.getImageUrlYQ();
	}
	/**
	 * 601007
	 * 个人中心
	 * @return
	 */
	public Map<String,Object> getCmUserInfoYunQi(Map<String,String> inParam) throws Exception{
		CmUserInfoYunQiTF userTF = (CmUserInfoYunQiTF) SysContexts.getBean("cmUserInfoYunQiTF");
		long userId = SysContexts.getCurrentOperator().getUserId();
		if(userId < 0){
			throw new BusinessException("没有获取到当前登录的用户编号！");
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(userTF.getCmUserInfoYunQi(userId)));
	}
	/**
	 * 发货地址/收货地址列表展示
	 * 接口编码：600002
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doQueryAddress(Map<String, String> inParam) throws Exception {
		CmAddressTF cmAddressTF= (CmAddressTF) SysContexts.getBean("cmAddressTF");
		return cmAddressTF.doQueryAddress(inParam);
	}
	/**
	 * 发货地址/收货地址删除
	 * 接口编码：600001
	 * @param inParam
	 * @throws Exception
	 */
	public Map deleteAddressById(Map<String, String> inParam) throws Exception {
		CmAddressTF cmAddressTF= (CmAddressTF) SysContexts.getBean("cmAddressTF");
		long addressId=DataFormat.getLongKey(inParam,"id");
		return cmAddressTF.deleteAddressById(addressId);
	}
	/**
	 * 查询用户的默认提货地址/收货地址
	 * 接口编码：600005
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map getAdderssDefault(Map<String, String> inParam)throws Exception{
		CmAddressTF cmAddressTF= (CmAddressTF) SysContexts.getBean("cmAddressTF");
		return cmAddressTF.getAdderssDefault(inParam);
	}
	
   /**
   	 * 接口编码:601008
	 * 批量上传经纬度
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public Map  uploadLocation(Map<String , String> inParam) throws Exception {
		 BaseUser user = SysContexts.getCurrentOperator();
		 JSONArray jsonArray = JSONArray.fromObject(inParam.get("gpsList"));  
			//List list = JSONArray.toList(jsonArray);
			if(jsonArray != null && jsonArray.size()>0){
				Iterator it = jsonArray.iterator();
				while(it.hasNext()){
					JSONObject morphDynaBean = (JSONObject) it.next();
					String billId = (String) morphDynaBean.get("billId");
					long userId = user.getUserId();
					String longitude = (String)morphDynaBean.get("longitude");
					String latitude = (String)morphDynaBean.get("latitude");
					String time = (String) morphDynaBean.get("time");
					if(StringUtils.isBlank(billId) || !CommonUtil.isCheckPhone(billId)){
						if(log.isDebugEnabled())
							log.debug("手机号码不正确");
						continue;
					}
					if(StringUtils.isBlank(longitude)){
						if(log.isDebugEnabled())
							log.debug("经度为空");
						continue;
					}
					if(StringUtils.isBlank(latitude)){
						if(log.isDebugEnabled())
							log.debug("纬度为空");
						continue;
					}
					if(userId<0){
						if(log.isDebugEnabled())
							log.debug("用户id为空");
						continue;
					}
					if (user.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL) {
						BusiGrabControlOut.updateUserInfoPoint(userId, Double.valueOf(latitude), Double.valueOf(longitude));
					}
//					Map reMap = new HashMap();
//					reMap.put("billId", billId);
//					reMap.put("longitude", longitude);
//					reMap.put("latitude", latitude);
//					if(StringUtils.isBlank(time)){
//						reMap.put("time", DateUtil.getCurrDateTime());
//					}else{
//						reMap.put("time", time);
//					}
//					Long size = RemoteCacheUtil.rpush(EnumConsts.RemoteCache.UPLOAD_LATITUDE, JsonHelper.json(reMap));
//					RemoteCacheUtil.set(EnumConsts.RemoteCache.BillId_Gps_Position+billId,latitude+"|"+longitude+"|||" +time);
//					GpsUtil.setBillGpsPosition(billId,Double.parseDouble(longitude),Double.parseDouble(latitude));
//					log.info(">>>"+time+">>>"+billId+"上传位置信息["+size+"]："+ longitude +", "+latitude);
				}
			}
			Map rtnMap = new HashMap();
			rtnMap.put("info", "Y"); //上传经纬度成功
		   return rtnMap; 
	 }
	/**
	 * 设置默认地址
	 * 接口编码：600003
	 * @param inParam
	 * @throws Exception
	 */
	public Map doSaveUpdateDefaultAddress(Map<String, Object> inParam) throws Exception{
		CmAddressTF cmAddressTF= (CmAddressTF) SysContexts.getBean("cmAddressTF");
		return cmAddressTF.doSaveUpdateDefaultAddress(inParam);
		
	}
	/**
	 * 拉包工打卡上班/休息
	 * 接口编码：600004
	 * @param inParam
	 * @throws Exception
	 */
	public Map<String,Object> doUpdatePullSate(Map<String, Object> inParam) throws Exception{
		CmUserInfoPullTF cmUserInfoPullTF=(CmUserInfoPullTF)SysContexts.getBean("cmUserInfoPullTF");
		return cmUserInfoPullTF.doUpdatePullSate(inParam);
	}
	/**
	 * 拉包工上传身份证
	 * 接口编码：600007
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> uploadIDCard(Map<String, Object> inParam) throws Exception{
		CmUserInfoPullTF cmUserInfoPullTF=(CmUserInfoPullTF)SysContexts.getBean("cmUserInfoPullTF");
		return cmUserInfoPullTF.uploadIDCard(inParam);
	}
	/**
	 * 新增/修改收款方式
	 * 接口编码：600012
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doSaveAcPaymentMethod(Map<String, String> inParam) throws Exception {
		AcPaymentMethodTF acPaymentMethodTF=(AcPaymentMethodTF)SysContexts.getBean("acPaymentMethodTF");
		return acPaymentMethodTF.doSaveAcPaymentMethod(inParam);
	}
	
			    	
	 
	 /**
	  * 修改密码
	  * 接口编号：601009
	  * @param inParam
	  * @return
	  * @throws Exception
	  */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map updateUserPassWord(Map<String,String> inParam)throws Exception{
		 long userId = SysContexts.getCurrentOperator().getUserId();
		// long userId = DataFormat.getLongKey(inParam,"userId");
		 String newLoginPwd = DataFormat.getStringKey(inParam, "newLoginPwd");
		 String loginPwd = DataFormat.getStringKey(inParam, "oldLoginPwd");
		 String captcha = DataFormat.getStringKey(inParam, "captcha");
		 String billId = DataFormat.getStringKey(inParam, "billId");
		 if(StringUtils.isEmpty(billId)){
			 throw new BusinessException("请输入登录的手机号码");
		 }
		 
		 if(userId < 0){
			 throw new BusinessException("请传入用户编号！");
		 }
		 if(StringUtils.isEmpty(loginPwd)){
			 throw new BusinessException("请输入旧密码！");
		 }
		 if(StringUtils.isEmpty(newLoginPwd)){
			 throw new BusinessException("请输入新的密码！");
		 }
		 if(loginPwd.equals(newLoginPwd)){
			 throw new BusinessException("新密码不能与旧密码相同，请重新输入新密码！");
		 }
		 newLoginPwd=EncryPwdUtil.pwdDecryption(newLoginPwd);
		 loginPwd=EncryPwdUtil.pwdDecryption(loginPwd);
		 /*newLoginPwd = K.j_s(newLoginPwd);
		 loginPwd = K.j_s(loginPwd);*/
		 if(!CommonUtil.isCheckPwd(loginPwd)){
			throw new BusinessException ("旧密码有误，重新输入！");
		 }
		 if(!CommonUtil.isCheckPwd(newLoginPwd)){
			throw new BusinessException ("新密码长度6～16个字符，字母不区分大小写！");
		 }
		 CmUserInfoYunQiTF userTF = (CmUserInfoYunQiTF) SysContexts.getBean("cmUserInfoYunQiTF");
		 Map map = new HashMap();
		 CmUserInfoTF userInfoTF = (CmUserInfoTF) SysContexts.getBean("cmUserInfoTF");
		 CmUserInfo cmUserInfo = userInfoTF.getUserInfo(userId, 0);
		// EncryPwdUtil
		 
		 if(!K.j_s(loginPwd).equals(cmUserInfo.getLoginPwd())){
			 throw new BusinessException("旧密码错误，请重新输入旧密码！");
		 }
		//校验验证码
		 if(!GenValidateCode.checkCode(billId,GenValidateCode.UPDATEPWD, captcha)){
			 throw new BusinessException("输入的验证码不对，请重新输入！");
		 }
		 map.put("info", userTF.updateUserPassWord(userId, loginPwd, newLoginPwd)) ;
		 return map;
	 }
	 /**
	  * 修改昵称
	  * 接口编号：601010
	  * @param inParam
	  * @return
	  * @throws Exception
	  */
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public Map updateUserName(Map<String,String> inParam)throws Exception{
		 String userName = DataFormat.getStringKey(inParam, "userName");
		 long userId = SysContexts.getCurrentOperator().getUserId();
		// long userId = DataFormat.getLongKey(inParam,"userId");
		 if(userId < 0){
			 throw new BusinessException("请传入用户编号！");
		 }
		 if(StringUtils.isEmpty(userName)){
			 throw new BusinessException("请输入昵称！");
		 }
		 if(CommonUtil.containsEmoji(userName)){
			 throw new BusinessException("不能输入表情的类型！");
		 }
		 CmUserInfoYunQiTF userTF = (CmUserInfoYunQiTF) SysContexts.getBean("cmUserInfoYunQiTF");
		 Map map = new HashMap();
		 map.put("info", userTF.updataUserName(userId, userName)) ;
		 map.put("userName", userName);
		 return map;
	 }
	 /**
	  * 我的钱包获取公司
	  * 接口编号：601011
	  * @return
	  */
	 public Map getAcWalletRel(Map<String,String> inParam)throws Exception{
		 AcWalletRelTF acWalletRelTF = (AcWalletRelTF) SysContexts.getBean("acWalletRelTF");
		 List<AcWalletRelOut> list = acWalletRelTF.getAcWalletRel();
		 return JsonHelper.parseJSON2Map(JsonHelper.json(list));
	 }
	 /**
	  * 推送消息查询
	  * 接口编号:601012
	  * @param inParam
	  * @return
	  * @throws Exception
	  */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMessage(Map<String,String> inParam)throws Exception{
		 SysSmsSendTF sendTF = (SysSmsSendTF) SysContexts.getBean("sysSmsSendTF");
		 String billId = SysContexts.getCurrentOperator().getTelphone();
		 int objType = DataFormat.getIntKey(inParam,"type");
		 
		 if(StringUtils.isEmpty(billId)){
			 throw new BusinessException("请传入手机号码！");
		 }
		 int isAll = DataFormat.getIntKey(inParam,"isAll");
		 if(isAll == 1){
			 objType = -1;
		 }
		 IPage p = sendTF.getMessage(billId,-1,objType);
		 Pagination page = new Pagination(p);
		 return  JsonHelper.parseJSON2Map(JsonHelper.json(page));
	 }
	 /**
	  * 推送消息详情
	  * 接口编号:601013
	  */
	 @SuppressWarnings("rawtypes")
	public Map getMessageDet(Map<String,String> inParam)throws Exception{
		 SysSmsSendTF sendTF = (SysSmsSendTF) SysContexts.getBean("sysSmsSendTF");
		 String billId = SysContexts.getCurrentOperator().getTelphone();
		 
		 long smsId = DataFormat.getLongKey(inParam, "smsId");
		 if(smsId < 0){
			 throw new BusinessException("请传入信息编号！");
		 }
		 return  JsonHelper.parseJSON2Map(JsonHelper.json(sendTF.getMessage(billId,smsId,-1)));
	 }
	 
	 /**
	  * 删除消息
	  * 接口编号:601014
	  */
	 @SuppressWarnings("unchecked")
	public Map delMessage(Map<String,String> inParam)throws Exception{
		 String id = DataFormat.getStringKey(inParam, "id");
		 if(StringUtils.isEmpty(id)){
			 throw new BusinessException("请传入消息编号！");
		 }
		 List<String> list = (List<String>) JSONArray.toCollection(JSONArray.fromObject(id),String.class);
		 SysSmsSendTF sendTF = (SysSmsSendTF) SysContexts.getBean("sysSmsSendTF");
		 Map map = new HashMap();
		 map.put("info", sendTF.delMessage(list));
		 return map;
	 }

	 /**
	  * 上传头像
	  * 接口编码：601015
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map uploadUserPic(Map<String,String>inParam)throws Exception{
		CmUserInfoYunQiTF cmUserInfoYunQiTF=(CmUserInfoYunQiTF)SysContexts.getBean("cmUserInfoYunQiTF");
		Map map=new HashMap();
		map.put("info", cmUserInfoYunQiTF.uploadUserPic(inParam));
		return map;
		 
	 }

	 /**
	  * 验证手机号码
	  * 接口编号：601016
	  * @param map
	  * @return
	  * @throws Exception
	  */
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public Map checkCode(Map<String,String> map) throws Exception{
		String billId = DataFormat.getStringKey(map, "billId");		//校验验证码
		String captcha = DataFormat.getStringKey(map, "captcha");
		Map m = new HashMap();
		if(!GenValidateCode.checkCode(billId,GenValidateCode.REGINSTER, captcha)){
			m.put("info", "N");
		}else{
			m.put("info", "Y");
		}
		return m;
	 }
	 /**
	  * 查询流水(已提现或提现中)
	  * 接口编码：601017
	  * @param inParam
	  * @return
	  */
	 public Map queryAcAccoutnWallet(Map<String,String> inParam){
		 BaseUser user = SysContexts.getCurrentOperator();
		 Session session = SysContexts.getEntityManager(true);
		 long tenantId = DataFormat.getLongKey(inParam, "tenantId");
		 if(tenantId < 0){
			 throw new BusinessException("请选择公司！");
		 }
		 int amountState = DataFormat.getIntKey(inParam,"amountState");
		 if(amountState < 0){
			 throw new BusinessException("请选择提现状态！");
		 }
		 //拉包工钱包没有跟专线建立关系
		 if(user.getUserType()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
			 Criteria  acWalletRel = session.createCriteria(AcWalletRel.class);
			 acWalletRel.add(Restrictions.eq("tenantId", tenantId));
			 acWalletRel.add(Restrictions.eq("pullTenantId", user.getTenantId()));
			 List<AcWalletRel> pullRel = acWalletRel.list();
			 if(pullRel==null || pullRel.size()<=0){
				 throw new BusinessException("拉包工和专线没有建立关系！");
			 } 
		 }
		 AcAccountWalletTF acAccountWalletTF = (AcAccountWalletTF) SysContexts.getBean("acAccountWalletTF");
		 Pagination page = acAccountWalletTF.queryAcAccoutnWallet(tenantId,amountState);
		 return JsonHelper.parseJSON2Map(JsonHelper.json(page));
	 }
	 /**
	  * 已读接口
	  * 接口编码：601018
	  * @param map
	  * @return
	  */
	 @SuppressWarnings("unchecked")
	public Map isReadMessage(Map<String,String> map){
		 SysSmsSendTF sendTF = (SysSmsSendTF) SysContexts.getBean("sysSmsSendTF");
		 long smsId = DataFormat.getLongKey(map,"smsId");
		 Map m = new HashMap();
		 m.put("info", sendTF.isReadMessage(smsId));
		 return m;
	 }
	 /**
	  * 提现详情（601020）
	  * @param inParam
	  * @return
	  */
	 public Map getAcAccountDet(Map<String,String> inParam){
		 AcAccountWalletTF acAccountWalletTF = (AcAccountWalletTF) SysContexts.getBean("acAccountWalletTF");
		 long accountId = DataFormat.getLongKey(inParam,"id");
		 AcAccountWalletOut out = acAccountWalletTF.getAcAccountDet(accountId);
		 return JsonHelper.parseJSON2Map(JsonHelper.json(out));
	 }
	 /**
	  * 待提现信息(601019)
	  * @param inParam
	  * @return
	  */
	 public Map queryAcMyWallet(Map<String,String> inParam){
		 AcMyWalletTF acMyWalletTF = (AcMyWalletTF) SysContexts.getBean("acMyWalletTF");
		 long tenantId = DataFormat.getLongKey(inParam,"tenantId");
		 List<AcMyWalletListOut> list = acMyWalletTF.queryAcMyWallet(tenantId);
		 return JsonHelper.parseJSON2Map(JsonHelper.json(list));
	 }
	 
	 /**
	  * 下单接口
	  * 接口编码：602003
	  * @param map
	  * @return
	  */
	 @SuppressWarnings("unchecked")
	public Map saveOrders(Map<String,Object> inParamMap) throws Exception{
		 OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		 Map m = new HashMap();
		 m.put("tips", ordersInfoTF.saveOrders(inParamMap));
		 m.put("isConfirm",2);
		 return m;
	 }
	 

	 /**
	  * 物流信息查询
	  * 接口编码：602002
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map queryLog(Map<String, Object> inParam) throws Exception {
		OrdOpLogYunQiTF ordOpLogYunQiTF=(OrdOpLogYunQiTF)SysContexts.getBean("ordOpLogYunQiTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordOpLogYunQiTF.queryLog(inParam)));
		 
	 }
	 /**
	  * 查看未读数（601022）
	  * @param inParam
	  * @return
	  * @throws Exception
	  */
	public Map queryNotRead(Map<String,String> inParam)throws Exception{
		SysSmsSendTF sysSmsSendTF = (SysSmsSendTF) SysContexts.getBean("sysSmsSendTF");
		return sysSmsSendTF.queryNotRead();
	}
	
	 /**
	  * 首页查询（602001）
	  * @param inParam
	  * @return
	  * @throws Exception
	  */
	public Map indexQuery(Map<String,Object> inParam)throws Exception{
		String arg = DataFormat.getStringKey(inParam, "arg");
		Map<String,Object> map = new HashMap<String,Object>();
		if(CommonUtil.isCheckPhone(arg)){
			map.put("consigneeBill", arg);
		}else{
			map.put("orderNo", arg);
		}
		map.put("isIndexQuery", 1);
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return ordersInfoTF.doQuery(map);
	}
	
	 /**
	  * 列表查询（602004）
	  * @param inParam
	  * @return
	  * @throws Exception
	  */
	public Map doQueryOrder(Map<String,Object> inParam)throws Exception{
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return ordersInfoTF.doQuery(inParam);
	}
	/**
	 * 开单（601024）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> doSaveOrderInfo(Map<String,String> inParam)throws Exception{
		OrderInfoTF orderInfoTF = (OrderInfoTF) SysContexts.getBean("orderInfoTF");
		return orderInfoTF.doSaveOrderInfo(inParam) ;
	}
	/**
	 * @param inParam
	 * 运费算费（601025）
	 * @return
	 * @throws Exception
	 */
	public Map freightCount(Map<String,String> inParam)throws Exception{
		OrderFeeRuleSV orderFeeRuleSV = (OrderFeeRuleSV) SysContexts.getBean("orderFeeRuleSV");
		String weight = DataFormat.getStringKey(inParam,"weight");
		long arrivedOrgId = DataFormat.getLongKey(inParam,"arrivedOrgId");
		if(arrivedOrgId<0){
			throw new BusinessException("请选择中转站【到达网点】!");
		}
		String volume = DataFormat.getStringKey(inParam,"volume");
		Map map = new HashMap();
		Long freight = orderFeeRuleSV.freightCount(weight, arrivedOrgId, volume);
		String sFreight = freight != null && freight > 0 ? CommonUtil.parseDouble(freight) : "";
		map.put("freight", sFreight);
		return map;
	}
	/**
	 * 小费算费（601026）
	 */
	@SuppressWarnings("unchecked")
	public Map tipCount(Map<String,String> inParam)throws Exception{
		OrderFeeRuleSV orderFeeRuleSV = (OrderFeeRuleSV) SysContexts.getBean("orderFeeRuleSV");
		String weight = DataFormat.getStringKey(inParam,"weight");
		long arrivedOrgId = DataFormat.getLongKey(inParam,"arrivedOrgId");
		if(arrivedOrgId<0){
			throw new BusinessException("请选择中转站【到达网点】!");
		}
		String freight = DataFormat.getStringKey(inParam,"freight");
		Map map = new HashMap();
		Long tip =  orderFeeRuleSV.tipCount(weight,arrivedOrgId , CommonUtil.getStringByLong(freight));
		String sTip = tip != null && tip > 0 ? CommonUtil.parseDouble(tip) : "";
		map.put("tip",sTip);
		return map;
	}
	
	/**
	 * 打点小费算费（601035）
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map tipCountByOrderNo(Map<String,String> inParam)throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		OrderFeeRuleSV orderFeeRuleSV = (OrderFeeRuleSV) SysContexts.getBean("orderFeeRuleSV");
		String weight = DataFormat.getStringKey(inParam,"weight");
		String freight = DataFormat.getStringKey(inParam,"freight");
		Map map = new HashMap();
		String orderNo = DataFormat.getStringKey(inParam, "orderNo");
		if(StringUtils.isEmpty(orderNo)){
			throw new BusinessException("请填写订单号！");
		}
		OrdOrdersInfoTF ordOrdersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		OrdOrdersInfo order = ordOrdersInfoTF.getOrdOrdersInfoByOrderNo(orderNo);
		if(order == null){
			throw new BusinessException("无该单号的信息！");
		}
		if(order.getOrderState() != SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING && order.getOrderState() != SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP){
			throw new BusinessException("该单号不是拉包中或待提货的状态，不能打点小费！");
		}
		
		//获取线路结束网点
		Session session = SysContexts.getEntityManager(true);
		Criteria caRoute = session.createCriteria(RouteAreaRelCfg.class);
		caRoute.add(Restrictions.eq("orgId", user.getOrgId()));
		caRoute.add(Restrictions.eq("cityId", order.getCityId()));
		List<RouteAreaRelCfg> routeList = caRoute.list();
		long endOrgId = 0;
		if(routeList != null && routeList.size() > 0){
			endOrgId = Long.valueOf(routeList.get(0).getDestOrgId());
		}
		Long tip =  orderFeeRuleSV.tipCount(weight, endOrgId,StringUtils.isNotEmpty(freight) ? CommonUtil.getStringByLong(freight) : 0);
		String sTip = tip != null && tip > 0 ? CommonUtil.parseDouble(tip) : "0.00";
		map.put("tip",sTip);
		
		OrdBusiPerson busi = (OrdBusiPerson) session.get(OrdBusiPerson.class, order.getOrderId());
		map.put("pullName", busi.getWorkerName() != null ? busi.getWorkerName() : "");
		return map;
	}
	/**
	 * 保费计算（601027）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map premiumCount(Map<String,String> inParam)throws Exception{
		OrderFeeRuleSV orderFeeRuleSV = (OrderFeeRuleSV) SysContexts.getBean("orderFeeRuleSV");
		long arrivedOrgId = DataFormat.getLongKey(inParam,"arrivedOrgId");
		if(arrivedOrgId<0){
			throw new BusinessException("请选择中转站【到达网点】!");
		}
		int number = DataFormat.getIntKey(inParam,"number");
		Map map = new HashMap();
		Long premium = orderFeeRuleSV.premiumCount(arrivedOrgId, number);
		String sPremium = premium != null && premium > 0 ? CommonUtil.parseDouble(premium) : "";
		map.put("premium", sPremium);
		return map;
	}
	
	 /**
	  * 下单详情（602006）
	  * @param inParam
	  * @return
	  * @throws Exception
	  */
	public Map orderDetail(Map<String,Object> inParam)throws Exception{
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.orderDetail(inParam)));
	}
	
	

	 /**
	  * 开单回显信息（602016）
	  * @param inParam
	  * @return
	  * @throws Exception
	  */
	public Map getByIdOrderInfo(Map<String,Object> inParam)throws Exception{
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.getByIdOrderInfo(inParam)));
	}
	
	 /**
	  * 催单（602007）
	  * @param inParam
	  * @return
	  * @throws Exception
	  */
	public Map reminderOrder(Map<String,Object> inParam)throws Exception{
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.reminderOrder(inParam)));
	}
	
	/****
	 * 发货 602008
	 * @param inParam
	 * */
	public Map<String,Object>  sendGoods(Map<String,Object> inParam) throws Exception{ 
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.sendGoods(inParam)));
	}
	
	/****
	 * 关联运单号 602013
	 * @param inParam
	 * */
	public Map<String,Object>  linkageBill(Map<String,Object> inParam) throws Exception{ 
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.linkageBill(inParam)));
	}
	
	
	/****
	 * 取消下单 602015
	 * @param inParam
	 * */
	public Map<String,Object>  cancerOrders(Map<String,Object> inParam) throws Exception{ 
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.cancerOrders(inParam)));
	}
	
	/****
	 * 打小费 602011
	 * @param inParam
	 * */
	public Map<String,Object>  payTips(Map<String,Object> inParam) throws Exception{ 
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.payTips(inParam)));
	}
	
	/****
	 * 提货  602010
	 * @param inParam
	 * */
	public Map<String,Object>  pickUp(Map<String,Object> inParam) throws Exception{ 
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.pickUp(inParam)));
	}
	
	/****
	 * 所有提货  602022
	 * @param inParam
	 * */
	public Map<String,Object>  pickUpAll(Map<String,Object> inParam) throws Exception{ 
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.pickUpAll(inParam)));
	}
	
	/****
	 * 签收  602012
	 * @param inParam
	 * */
	public Map<String,Object>  signUp(Map<String,Object> inParam) throws Exception{ 
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).doSign(inParam);
	}
	
	/****
	 * 编辑下单信息  602014
	 * @param inParam
	 * */
	public Map<String,Object>  updateOrderInfo(Map<String,Object> inParam) throws Exception{ 
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.updateOrderInfo(inParam)));
	}
	
	
	
	/****
	 * 配送  602009
	 * @param inParam
	 * */
	public Map<String,Object>  deliveryOrder(Map<String,Object> inParam) throws Exception { 
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.deliveryOrder(inParam)));
	}
	
	/****
	 * 下单物流公司查询（602017）
	 * @param inParam
	 * */
	public Map<String,Object>  queryOrgList(Map<String,Object> inParam) throws Exception { 
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.queryOrgList(inParam)));
	}
	
	/****
	 * 干线到货（602018）
	 * @param inParam
	 * */
	public Map<String,Object>  gxArriveGoods(Map<String,Object> inParam) throws Exception { 
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.gxArriveGoods(inParam)));
	}
	/**
	 * 查看公司下的提现金额
	 * 601028
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> applyAccount(Map<String,String> inParam) throws Exception{
		long tenantId  =  DataFormat.getLongKey(inParam,"tenantId");
		int amountState = SysStaticDataEnumYunQi.AMOUNT_STATE.CASH_WITHDRAWAL;
		AcAccountWalletTF acAccountWalletTF = (AcAccountWalletTF) SysContexts.getBean("acAccountWalletTF");
		Map<String,String> map = new HashMap<String,String>();
		map.put("amount", acAccountWalletTF.applyAmount(tenantId, amountState));
		return map;
	}
	/**
	 * 查询未提现金额合计
	 * 601029
	 * @param inParam
	 * @return
	 */
	public Map<String,String> presentAmount(Map<String,String> inParam){
		long tenantId  =  DataFormat.getLongKey(inParam,"tenantId");
		AcMyWalletTF acMyWalletTF = (AcMyWalletTF) SysContexts.getBean("acMyWalletTF");
		Map<String,String> map = new HashMap<String,String>();
		map.put("amount", acMyWalletTF.presentAmount(tenantId));
		return map;
	}
	/**
	 * 账单查询
	 * 601030
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> billAcMyWallet(Map<String,String> inParam) throws Exception{
		String accountTime = DataFormat.getStringKey(inParam, "accountTime");
		AcMyWalletTF acMyWalletTF = (AcMyWalletTF) SysContexts.getBean("acMyWalletTF");
		IPage p = acMyWalletTF.billAcMyWallet(accountTime);
		Pagination page = new Pagination(p);
		return JsonHelper.parseJSON2Map(JsonHelper.json(page));
	}
	/**
	 * 提现申请
	 * 601031
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> applyAcAccountWallet(Map<String,String> inParam) throws Exception{
		AcAccountWalletTF acAccountWalletTF = (AcAccountWalletTF) SysContexts.getBean("acAccountWalletTF");
		String ids = DataFormat.getStringKey(inParam, "ids");
		long tenantId = DataFormat.getLongKey(inParam,"tenantId");
		List<String> list = (List<String>) JSONArray.toCollection(JSONArray.fromObject(ids),String.class);
		return acAccountWalletTF.applyAcAccountWallet(list, tenantId);
	}
	/**
	 * 首页统计
	 * 接口编码：602005
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map indexStatistics(Map<String, Object> inParam) throws Exception {
		OrdIndexStatisticsTF ordIndexStatisticsTF=(OrdIndexStatisticsTF)SysContexts.getBean("ordIndexStatisticsTF");
		return ordIndexStatisticsTF.indexStatistics(inParam);
	}
	
	/*****
	 * 首页查询(专线角色)（602019）
	 */
	public Map<String,Object> zxIndexQuery(Map<String,String> inParam) throws Exception{
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		String arg = DataFormat.getStringKey(inParam, "arg");
		BaseUser user = SysContexts.getCurrentOperator();
		Map<String,Object> map = new HashMap<String,Object>();
		if(CommonUtil.isCheckPhone(arg)){
			map.put("consigneeBill", arg);
		}else{
			if (user.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION) {
				map.put("trackingNum", arg);
			}else{
				map.put("orderNo", arg);
			}
		}
		map.put("isIndexQuery", 1);
		map.put("merchanOrDistri", DataFormat.getIntKey(inParam, "merchanOrDistri"));
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.zxQuery(map)));
	}
	
	/*****
	 * 列表查询(专线角色)（602020）
	 */
	public Map<String,Object> zxDoQuery(Map<String,Object> inParam) throws Exception{
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		String arg = DataFormat.getStringKey(inParam, "arg");
		if(CommonUtil.isCheckPhone(arg)){
			inParam.put("consigneeBill", arg);
		}else{
			inParam.put("trackingNum", arg);
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.zxQuery(inParam)));
	}
	
	/*****
	 * 任务详情(专线角色)（602021）
	 */
	public Map<String,Object> zxOrderView(Map<String,Object> inParam) throws Exception{
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.zxOrderView(inParam)));
	}
	/**
	 * 接口编号:601032
	 * @author lyy
	 * 退出app
	 */
	public Map<String, Object> loginOut(Map<String, String> inParam) throws Exception{
		BaseUser baseUser = SysContexts.getCurrentOperator();
		if(baseUser != null && baseUser.getUserId()!=null){
			//删除手机的推送关系
			String billId=baseUser.getTelphone();
			AppPushBillRelatTF ordersInfoTF = (AppPushBillRelatTF) SysContexts.getBean("appPushBillRelatTF");
			ordersInfoTF.delRealetByBill(billId);
			SysContexts.setCurrentOperator(null);
		}
		return null;
	}
	/*****
	 * 确认取消（602023）
	 */
	public Map<String,Object> confirmCancer(Map<String,Object> inParam) throws Exception{
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.confirmCancer(inParam)));
	}
	

	/*****
	 * 接口名称 判断是否可以编辑下单信息（602025）
	 *
	 */
	public Map<String,Object> isUpdateOrderInfo(Map<String,Object> inParam) throws Exception{
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.isUpdateOrderInfo(inParam)));
	}
	/**
	 * 	修改服务区（601033）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> updatePullSer(Map<String,String> inParam)throws Exception{
		CmUserInfoPullTF cmUserInfoPullTF = (CmUserInfoPullTF) SysContexts.getBean("cmUserInfoPullTF");
		long provice = DataFormat.getLongKey(inParam,"province");
		long city = DataFormat.getLongKey(inParam,"city");
		long district = DataFormat.getLongKey(inParam,"district");
		Map<String,String> map = new HashMap<String, String>();
		map.put("info", cmUserInfoPullTF.doUpdateSer(provice, city, district));
		return map;
	}
	/*****
	 * 报表数据统计
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> statisticsData(Map<String,Object> inParam)throws Exception{
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.statisticsData(inParam)));
	}
	
	/**
	 * 忘记密码（601034）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> forgetPassWord(Map<String,String> inParam)throws Exception{
		String billId = DataFormat.getStringKey(inParam, "billId");
		if(StringUtils.isEmpty(billId)){
			throw new BusinessException("请输入登陆手机号码！");
		}
		if(!CommonUtil.isCheckPhone(billId)){
			throw new BusinessException("请输入正确的手机号码！");
		}
		String captcha = DataFormat.getStringKey(inParam, "captcha");
		if(StringUtils.isEmpty(captcha)){
			throw new BusinessException("请输入验证码！");
		}
		CmUserInfoYunQiSV cmUserInfoYunQiSV = (CmUserInfoYunQiSV) SysContexts.getBean("cmUserInfoYunQiSV");
		CmUserInfo cmUserInfo = cmUserInfoYunQiSV.getCmUserInfoByPhone(billId);
		if(cmUserInfo == null){
			throw new BusinessException("没有该手机号码的登陆用户！");
		}
		String password = DataFormat.getStringKey(inParam, "password");
		if(StringUtils.isEmpty(password)){
			throw new BusinessException("请输入新密码！");
		}
		//解密
		password=EncryPwdUtil.pwdDecryp(password);
		if(!CommonUtil.isCheckPwd(password)){
			throw new BusinessException ("密码长度6～16个字符，字母不区分大小写！");
		}
		
		//校验验证码
		if(!GenValidateCode.checkCode(billId,GenValidateCode.FORGETPW, captcha)){
			throw new BusinessException("输入的验证码不对，请重新输入！");
		}
		cmUserInfo.setLoginPwd(K.j_s(password));
		Map<String,String> map = new HashMap<String, String>();
		map.put("info", cmUserInfoYunQiSV.updateCmUserInfo(cmUserInfo));
		return map;
	}
	
	
	/**
	 * lyh
	 * app 查询系统版本信息接口
	 * 601036
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> getVerInfo(Map<String, String> inParam)
			throws Exception {
		PortalBusiTF cmUserInfoTF = (PortalBusiTF)SysContexts.getBean("portalBusiTF");
		Map retMap = cmUserInfoTF.getVerInfo(inParam);
		return retMap;
	}
	
	/**
	 * 查询中转站网点（601037）
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> getRouteTowardsByDesCity(Map<String,Object> inParam){
		RouteTowardsSV routeTowardsSV = (RouteTowardsSV) SysContexts.getBean("routeTowardsSV");
		BaseUser user = SysContexts.getCurrentOperator();
		long desCity = DataFormat.getLongKey(inParam,"desCity");
		long desProvince = DataFormat.getLongKey(inParam, "desProvince");
		long desRegion = DataFormat.getLongKey(inParam, "desRegion");
		long orgId = user.getOrgId();
		if(desCity < 0){
			throw new BusinessException("请传入到达城市ID");
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(routeTowardsSV.getRouteTowardsByDesCity(user.getTenantId(), desCity,desProvince,desRegion,orgId)));
	}
	
	/**
	 * 获取当前开单员的归属专线网点（601038）
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> getCarriByOrgId(Map<String,Object> inParam){
		BaseUser user = SysContexts.getCurrentOperator();
		Map<String,Object> map = new HashMap<String, Object>();
		if(user.getTenantId() == null && user.getTenantId() < 0){
			return map;
		}
		Organization org = OraganizationCacheDataUtil.getOrganizationByTenantId(user.getTenantId());
		map.put("carrierId", org.getTenantId());
		map.put("carrierName", org.getOrgName());
		return map;
	}
	
	/**
	 * 查询租户下所有网点(除了当前网点)
	 * @param inParam
	 * @return
	 * 601039
	 * @throws Exception 
	 */
	public Map<String,Object> getCurOrg(Map<String,String> inParam) throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		RouteTF routeTF = (RouteTF) SysContexts.getBean("routeTF");
		inParam.put("beginOrgId",user.getOrgId().toString());
		List<RouteTowards> orgList=routeTF.getTowards(inParam);
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		if(orgList != null && orgList.size() > 0){
			for (RouteTowards routeToward : orgList) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("arrivedOrgId", routeToward.getEndOrgId().toString());
				map.put("arrivedOrgName", OraganizationCacheDataUtil.getOrgName(routeToward.getEndOrgId()));
				list.add(map);
			}
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(list));
	}
	/**
	 * 工号查询拉包工下单号(601040)
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> orderNoByJobNumber(Map<String,String> inParam){
		OrdOrdersInfoTF tf = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return tf.orderNoByJobNumber(DataFormat.getStringKey(inParam, "jobNumber"));
	}
	/**
	 * 发货人或者收货人模糊匹配（601041）
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> likeCmCustomerByName(Map<String,String> inParam){
		CmCustomerYQTF tf = (CmCustomerYQTF) SysContexts.getBean("cmCustomerYQTF");
		return tf.likeCmCustomerByName(inParam);
	}
	/**
	 * 获取当前开单员可以送达的到这城市（601042）
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> getCurOrgCity(Map<String,Object> inParam){
		return ((RouteTF)SysContexts.getBean("routeTF")).getCurOrgCity();
	}
	/**
	 * 登记物流公司信息（601043）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> doSaveTenantRegister(Map<String,Object> inParam) throws Exception{
		return ((SysTenantRegisterTF) SysContexts.getBean("sysTenantRegisterTF")).doSaveRegister(inParam);
	}
	/**
	 * 统计选择数据(601044)
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getSelectOrderCount(Map<String,Object> inParam) throws Exception{
		return CommonUtil.getSelectOrderCount(DataFormat.getStringKey(inParam, "merchanOrDistri"));
	}
	/**
	 * 统计查询(601045)
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getConuntOrderInfo(Map<String,Object> inParam) throws Exception{
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).getConuntOrderInfo(inParam);
	}
	
	/**
	 * 签收下详情（601046）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> signOrdersInfo(Map<String,Object> inParam) throws Exception{
		return ((OrdOrdersInfoTF)SysContexts.getBean("ordersInfoTF")).signOrdersInfo(inParam);
	}
	/**
	 * 抢单列表(601047)
	 */
	public Map<String,Object> grabOrdersList(Map<String,Object> inParam){
		return ((OrdOrdersInfoTF)SysContexts.getBean("ordersInfoTF")).grabOrdersList(inParam);
	}
	/**
	 * 抢单操作(601048)
	 */
	public Map<String,Object> grabOrders(Map<String,Object> inParam) throws Exception{
		return ((OrdOrdersInfoTF)SysContexts.getBean("ordersInfoTF")).grabOrders(inParam);
	}
	/**
	 * 取消抢单订单的操作(602026)
	 */
	public Map<String,Object> cancelGrabOrders(Map<String,Object> inParam) throws Exception{
		return ((OrdOrdersInfoTF)SysContexts.getBean("ordersInfoTF")).cancelGrabOrders(inParam);
	}
	/**
	 * 查看物流跟踪详情(602027)
	 * @throws Exception 
	 */
	public Map<String,String> isShowOrdersInfo(Map<String,Object> inParam) throws Exception{
		return ((OrdOrdersInfoTF)SysContexts.getBean("ordersInfoTF")).isShowOrdersInfo(inParam);
	}
	
	/**
	 * 获取运单号(601069)
	 * @return
	 * @throws Exception 
	 */
	public Map<String,String> getTrackingNum(Map<String,Object> inParam) throws Exception{
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).trackingNum();
	}
	/**
	 * 获取子运单待配载信息（601049）
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> childStowagePlan(Map<String,Object> inParam){
		return ((OrderInfoChildTF)SysContexts.getBean("orderInfoChildTF")).childStowagePlan(inParam);
	}
	/**
	 * 保存与配载（601050）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> doSaveOrUpdateDepart(Map<String,Object> inParam) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		map.put("info", ((OrdDepartInfoYqTF)SysContexts.getBean("ordDepartInfoYqTF")).doSaveOrUpdateDepart(inParam));
		return map;
	}
	/**
	 * 发车配载信息（601051）
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> getordDepartInfos(Map<String,Object> inParam){
		return ((OrdDepartInfoYqTF)SysContexts.getBean("ordDepartInfoYqTF")).getordDepartInfos(inParam);
	}
	
	/**
	 * 获取配载批次详情【601056】
	 * @param inParam
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> getOrdDepartInfo(Map<String,Object> inParam) throws Exception{
		return ((OrdDepartInfoYqTF)SysContexts.getBean("ordDepartInfoYqTF")).getOrdDepartInfo(inParam);
	}
	
	/**
	 * 获取车辆信息(601053)
	 * @throws Exception 
	 */
	public Map<String,Object> getVehicleInfo(Map<String,Object> inParam) throws Exception{
		return ((CmVehicleInfoTF)SysContexts.getBean("cmVehicleTF")).getVehicleInfo(inParam);
	}
	
	
	/**
	 * 获取配送网点信息(601055)
	 * @throws Exception 
	 */
	public Map<String,Object> getEndOrgInfo(Map<String,Object> inParam) throws Exception{
		return ((OrganizationTF)SysContexts.getBean("organizationTF")).getEndOrgInfo(inParam);
		
	}
	/**
	 * 查询待配送列表【601067】
	 * @throws Exception 
	 */
	public Map<String,Object> queryDispatchingList(Map<String,Object> inParam) throws Exception{
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).queryDispatchingList(inParam);
	}
	
	/**
	 * 获取司机信息(601054)
	 * @throws Exception 
	 */
	public Map<String,Object> getDriverInfo(Map<String,Object> inParam) throws Exception{
		return ((CmDriverInfoTF)SysContexts.getBean("cmDriverInfoTF")).getDriverInfo(inParam);
	}
	
	/**
	 * 待签收查询列表【601070】
	 * @throws Exception 
	 */
	public Map<String,Object> querySignInfoList(Map<String,Object> inParam) throws Exception{
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).querySignInfoList(inParam);
	}


	/**
	 * 未到货批次列表信息【601062】
	 * @throws Exception 
	 */
	public Map<String,Object> queryFloationCargoList(Map<String,Object> inParam) throws Exception{
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).queryFloationCargoList(inParam);
	}

	/**
	 * 扫描获取待配载数据【601071】
	 * @param childTrackingNum
	 * @return
	 */
	public Map<String,Object> sweepChildTrackingNum(Map<String,Object> inParam){
		return ((OrderInfoChildTF)SysContexts.getBean("orderInfoChildTF")).sweepChildTrackingNum(DataFormat.getStringKey(inParam, "childTrackingNum"));
	}

	
	/**
	 * 到货批次列表信息【601065】
	 * @throws Exception 
	 */
	public Map<String,Object> queryArrivalList(Map<String,Object> inParam) throws Exception{
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).queryArrivalList(inParam);
	}
	/**
	 * 到货操作【601063】
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> arrivalGoods(Map<String,Object> inParam) throws Exception{
		return ((OrdDepartInfoYqTF)SysContexts.getBean("ordDepartInfoYqTF")).arrivalGoods(inParam);
	}
	/**
	 * 扫描到货操作【601064】
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> sweepArrivalGoods(Map<String,Object> inParam) throws Exception{
		return ((OrdDepartInfoYqTF)SysContexts.getBean("ordDepartInfoYqTF")).sweepArrivalGoods(inParam);
	}
	/**
	 * 撤销到货【601066】
	 * @param inParam
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> cancelArrival(Map<String,Object> inParam) throws Exception{
		return ((OrdDepartInfoYqTF)SysContexts.getBean("ordDepartInfoYqTF")).cancelArrival(inParam);
	}
	/**
	 * 到车配载信息（601059）
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> getDepartAndArrivalInfos(Map<String,Object> inParam){
		return ((OrdDepartInfoYqTF)SysContexts.getBean("ordDepartInfoYqTF")).getDepartAndArrivalInfos(inParam);
	}
	/**
	 * 删除配载（601052）
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> delDepartInfos(Map<String,Object> inParam){
		return ((OrdDepartInfoYqTF)SysContexts.getBean("ordDepartInfoYqTF")).delDepartInfos(inParam);
	}
	

	/**
	 * 配送【601068】
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> dispatching(Map<String,Object> inParam) throws Exception{
		return ((OrderInfoChildTF)SysContexts.getBean("orderInfoChildTF")).dispatching(inParam);
	}
	
	
	/**
	 * 发车【601057】
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> doDepart (Map<String,Object> inParam) throws Exception{
		return ((OrdDepartInfoYqTF)SysContexts.getBean("ordDepartInfoYqTF")).doDepart(inParam);
	}
		
	/**
	 * 到车【601060】
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> doArrvite (Map<String,Object> inParam) throws Exception{
		return ((OrdDepartInfoYqTF)SysContexts.getBean("ordDepartInfoYqTF")).doArrvite(inParam);
	}
	
	/**
	 * 取消到车【601061】
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> cancelArrvite (Map<String,Object> inParam) throws Exception{
		return ((OrdDepartInfoYqTF)SysContexts.getBean("ordDepartInfoYqTF")).cancelArrvite(inParam);
	}
	/**
	 * 取消发车【601058】
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> cancelDepart (Map<String,Object> inParam) throws Exception{
		return ((OrdDepartInfoYqTF)SysContexts.getBean("ordDepartInfoYqTF")).cancelDepart(inParam);
	}
	/****
	 * 签收【601072】最新
	 * @param inParam
	 * */
	public Map<String,Object> doSign(Map<String,Object> inParam) throws Exception{ 
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).doSign(inParam);
	}
	
	/****
	 * 设置品名/包装【601073】
	 * @param inParam
	 * */
	public Map<String,Object> setDefaultPackageAndproduct(Map<String,Object> inParam) throws Exception{ 
		return ((CmUserInfoTF)SysContexts.getBean("cmUserInfoTF")).setDefaultPackageAndproduct(inParam);
	}
	
	/****
	 * 查询品名/包装【601074】
	 * @param inParam
	 * */
	public Map<String,Object> queryDefaultPackageAndproduct(Map<String,Object> inParam) throws Exception{ 
		return ((CmUserInfoTF)SysContexts.getBean("cmUserInfoTF")).queryDefaultPackageAndproduct(inParam);
	}
	/**
	 * 签收回显【601075】
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getOrdSign(Map<String,Object> inParam) throws Exception{
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		if (orderId < 0) {
			throw new BusinessException("请传入运单Id");
		}
		OrderInfoTF orderInfoTF = (OrderInfoTF) SysContexts.getBean("orderInfoTF");
		return orderInfoTF.getOrdSign(orderId);
	}
	
	/**
	 * 取消订单【602028】
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,String>  cancelOrderInfo(Map<String,Object> inParam) throws Exception{
		String trackingNum = DataFormat.getStringKey(inParam, "trackingNum");
		Map<String,String> map = new HashMap<String, String>();
		OrderInfoTF orderInfoTF = (OrderInfoTF) SysContexts.getBean("orderInfoTF");
		map.put("flag", orderInfoTF.cancelOrderInfo(inParam));
		return map;
		
	}
 }
