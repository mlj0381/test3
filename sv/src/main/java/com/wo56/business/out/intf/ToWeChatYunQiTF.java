package com.wo56.business.out.intf;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.encrypt.K;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.wo56.business.cm.impl.CmUserSV;
import com.wo56.business.cm.impl.OrganizationYunQiSV;
import com.wo56.business.cm.intf.CmUserInfoYunQiTF;
import com.wo56.business.cm.intf.CmUserOrgRelTF;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.ord.intf.OrdOrdersInfoTF;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.sys.intf.PortalBusiTF;
import com.wo56.business.sys.intf.SysSmsSendTF;
import com.wo56.common.consts.LoginConst;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.sms.vo.SysSmsSend;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.EncryPwdUtil;
import com.wo56.common.utils.GenValidateCode;
import com.wo56.common.utils.SmsUtil;
import com.wo56.common.utils.SmsYQUtil;



public class ToWeChatYunQiTF {
	
	 
    
	/**
	 * 接口编码:700000
	 *微信绑定校验手机号是否注册
	 * @param inParam
	 * 	接口入参：
	 * 			loginAcct		手机号
	 *  接口出参：
	 *  @return result
	 *  		BILL_NOT_EXIST:		该手机未注册，请输入验证码进行注册绑定
	 *  		OPPENID_NOT_SET:	该手机已注册，请输入密码进行绑定
	 *  		NO_AUTH:			不是商家与拉包工不能绑定微信账号
	 *  		
	 * @throws Exception
	 */
	public Map<String, String> checkBillRegister(Map<String, String> inParam) throws Exception {
		
		String loginAcct = DataFormat.getStringKey(inParam, "loginAcct");
		if (!CommonUtil.isCheckPhone(loginAcct)) {
			throw new BusinessException("输入的手机号码不正确！");
		}
		CmUserSV cmUserSV = (CmUserSV) SysContexts.getBean("cmUserSv");
		CmUserInfo cmUser = cmUserSV.queryUserByAcct(loginAcct);
		// 未注册
		Map<String, String> map = new HashMap<String, String>();
		if (cmUser == null) {
			map.put("result","BILL_NOT_EXIST");
		}
		// 注册未绑定
		if (cmUser != null && (cmUser.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS
			|| cmUser.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL)
				&& StringUtils.isEmpty(cmUser.getOpenId())) {
			map.put("result","OPPENID_NOT_SET");
		}
		// 不是商家与拉包工不能绑定微信账号
		if (cmUser != null && (cmUser.getUserType() != SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS
			&& cmUser.getUserType() != SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL)
				&& StringUtils.isEmpty(cmUser.getOpenId())) {
			map.put("result","NO_AUTH");
		}
		return map;

	}
    
   
	/**
	 * 接口编码：700001
	 * 注册手机号码，并绑定手机号
	 * @param inParam
	 * 				接口入参
	 * 				captcha 	验证码
	 * 				loginAcct	手机号
	 * 				openId		微信openId
	 * 				password    密码
	 * @return
	 * 				接口出参
	 * 				tokenId
	 * @throws Exception
	 */
	public Map registerAndBinding(Map<String, String> inParam) throws Exception {
		// 获取验证码
		String captcha = DataFormat.getStringKey(inParam, "captcha");
		String loginAcct = DataFormat.getStringKey(inParam, "loginAcct");
		if (StringUtils.isEmpty(loginAcct)) {
			throw new BusinessException("输入的手机号为空！");
		}
		if (!CommonUtil.isCheckPhone(loginAcct)) {
			throw new BusinessException("输入的手机号码不正确！");
		}
		String openId = DataFormat.getStringKey(inParam, "openId");
		if(StringUtils.isEmpty(openId)){
			throw new BusinessException("微信openId为空！");
		}
		if (StringUtils.isEmpty(captcha)) {
			throw new BusinessException("输入的验证码为空！");
		}
		// 校验验证码
		if (!GenValidateCode.checkCode(loginAcct, GenValidateCode.WECHAT, captcha)) {
			throw new BusinessException("输入的验证码不对，请重新输入！");
		}
		String password = DataFormat.getStringKey(inParam, "password");
		if(StringUtils.isEmpty(password)){
            throw new BusinessException("密码不能为空！");
         }
		// 解密
		password = EncryPwdUtil.pwdDecryp(password);
		if (!CommonUtil.isCheckPwd(password)) {
			throw new BusinessException("密码格式不正确");
		}
		Map<String, Object> saveUserInfo = new HashMap<String, Object>();
		saveUserInfo.put("loginPwd", password);
		saveUserInfo.put("loginAcct", loginAcct);
		saveUserInfo.put("openId", openId);
		CmUserInfo userInfo = doSaveWeChatCmUserInfo(saveUserInfo);
		// 获取tokeId
		BaseUser baseUser = setUserInfoToSession(userInfo);
		Map<String, Object> infoVo = new HashMap<String, Object>();
		infoVo.put("baseUser", baseUser);
		return infoVo;
	}
	/**
	 * 保存微信注册用户
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	private CmUserInfo doSaveWeChatCmUserInfo(Map<String, Object> inParam) throws Exception {
        String loginAcct = DataFormat.getStringKey(inParam, "loginAcct");
        if (StringUtils.isEmpty(loginAcct)) {
             throw new BusinessException("请输入手机号码！");
        }
        String loginPwd = DataFormat.getStringKey(inParam, "loginPwd");
        if (StringUtils.isEmpty(loginPwd)) {
             throw new BusinessException("请输入登陆密码");
        }
        String openId=DataFormat.getStringKey(inParam, "openId");
        loginPwd = K.j_s(loginPwd);
        CmUserInfo cmUserInfo = new CmUserInfo();
        cmUserInfo.setUserType(SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS);
        cmUserInfo.setLoginAcct(loginAcct);
        cmUserInfo.setLoginPwd(loginPwd);
        cmUserInfo.setUserName(loginPwd.substring(loginAcct.length()-4, loginAcct.length()));
        cmUserInfo.setLoginType(SysStaticDataEnumYunQi.LOGIN_TYPE.APP);
        cmUserInfo.setOpenId(openId);
        cmUserInfo.setChannelType(SysStaticDataEnumYunQi.CHANNEL_TYPE.WECHAT_CHANNEL_TYPE);
        cmUserInfo.setState(SysStaticDataEnum.STS.VALID);
        cmUserInfo.setCreateTime(new Date());
        CmUserSV cmUserSV = (CmUserSV) SysContexts.getBean("cmUserSv");
        cmUserSV.doSaveCmUserInfo(cmUserInfo);
        return cmUserInfo;
  }
	
    /**
     * 设置app登录或注册后，放到session的基本信息
     * @param userInfo
     * @return
     * @throws Exception
     */
    private BaseUser setUserInfoToSession(CmUserInfo userInfo) throws Exception{
          BaseUser user=new BaseUser();
          user.setOperId(userInfo.getUserId());
          user.setUserId(userInfo.getUserId());
          user.setUserName(userInfo.getUserName());
          user.setTelphone(userInfo.getLoginAcct());
          user.setUserType(userInfo.getUserType());
          if(user.getUserType()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
        	  CmUserOrgRelTF cmUserOrgRelTF = (CmUserOrgRelTF) SysContexts.getBean("cmUserOrgRelTF");
              List<Organization> list = cmUserOrgRelTF.queryOrgByUserId(userInfo.getUserId());
              if(list != null && list.size() > 0){
                   user.setOrgId(list.get(0).getOrgId());
                   user.setTenantId(list.get(0).getTenantId());
              }
          }
          Map<String, Object> attrs=new HashMap<String, Object>();
          attrs.put(LoginConst.BaseUserAttr.LOGIN_ACCOUNT, userInfo.getLoginAcct());
          user.setAttrs(attrs);
          return user;
    }
    
    /**
     * 接口编码:700002
     * 微信绑定已经注册的手机号
     * 
     * @param inParam
     * 			接口入参
     * 				loginAcct	手机号码
     * 				password    密码
     * 				openId      微信用户唯一标识
     * @return
     * 				接口出参
     * 				tokenId
     * @throws Exception
     */
    public Map<String, Object> bindBill(Map<String, String> inParam) throws Exception {
          Session session = SysContexts.getEntityManager();
          String loginAcct=DataFormat.getStringKey(inParam, "loginAcct");
          if(StringUtils.isEmpty(loginAcct)){
               throw new BusinessException("手机号码不能为空！");
          }
          String password = DataFormat.getStringKey(inParam, "password");
          if(StringUtils.isEmpty(password)){
               throw new BusinessException("密码不能为空！");
          }
          String openId=DataFormat.getStringKey(inParam, "openId");
//          if(StringUtils.isEmpty(openId)){
//               throw new BusinessException("微信用户唯一标识不能为空！");
//          }
          if(!CommonUtil.isCheckPhone(loginAcct)){
               throw new BusinessException("手机号码格式不正确！");
          }
          PortalBusiTF cmUserInfoTF = (PortalBusiTF)SysContexts.getBean("portalBusiTF");
          //解密
          password=EncryPwdUtil.pwdDecryption(password);
          inParam.put("loginAcct", loginAcct);
          inParam.put("passWord", password);
          inParam.put("loginType", String.valueOf(SysStaticDataEnum.LOGIN_TYPE.APP)+","+String.valueOf(SysStaticDataEnum.LOGIN_TYPE.WEB_AND_APP));
          //登录
          CmUserInfo cmUserInfo= cmUserInfoTF.appLogin(inParam);
          Map<String, Object> retMap=new HashMap<String, Object>();
          if(cmUserInfo!=null){
               //保存微信openId
               cmUserInfo.setOpenId(openId);
               session.update(cmUserInfo);
               BaseUser baseUser=setUserInfoToSession(cmUserInfo);
               retMap.put("baseUser", baseUser);
          }
          return retMap;
    }

    /**
     * 接口编码：700003
     * 微信注册发送验证码
     * @param inParam
     * 			接口入参
     * 				loginAcct 	发送手机号
     * 
     * @return
     * 			接口出参
     * 				info 		"Y"成功
     * @throws Exception
     */
    public Map<String,String>  sendCaptcha(Map<String, String> inParam) throws Exception {
          String loginAcct=DataFormat.getStringKey(inParam, "loginAcct");
          //判断手机号是否注册过
          CmUserInfoYunQiTF userTF = (CmUserInfoYunQiTF) SysContexts.getBean("cmUserInfoYunQiTF");
  		  if(!userTF.checkPhone(loginAcct)){
  			throw new BusinessException("号码已被注册，请登录！");
  		  }
          // 生成验证码
          String code= GenValidateCode.setPhoneCode(loginAcct,GenValidateCode.WECHAT);
          //发送验证码
          SmsYQUtil.registerSendVerificationCode(-1, code, loginAcct);
          Map map=new HashMap();
          map.put("info", "Y");
          return map;
    }
    
    /**
     * 接口编码：700005
     * 微信网点查询
     * @param inParam
     * 			接口入参 
     * 				orgName		网点关键字
     * 			接口出参
     * 				orgId				网点Id
     * 				provinceName 		省
     * 				regionName 			市
     * 				orgPrincipal		网点联系人
     * 				departmentAddress 	详细地址	
     * 				orgPrincipalPhone	联系电话
     * @return
     * @throws Exception
     */
    public Map queryOrg(Map<String,String>inParam)throws Exception{
    	OrganizationYunQiSV orgYunQiSV = (OrganizationYunQiSV) SysContexts.getBean("organizationYunQiSV");
    	return orgYunQiSV.queryOrg(inParam);
    }
    
    /**
     * 接口编码：700006
     * 微信订单记录
     * @param inParam
     * @return
     * 			接口出参
     * 				cityName   			到站城市
     * 				orderStateName		订单状态
     * 				consigneeName		收货人
     *				consigneeBill		收货人电话	
     *				deliveryAddress		收货地址
     *				createDate			创建时间
     *				orderId				订单id
     * @throws Exception
     */
    public Map queryOrders(Map<String,String>inParam)throws Exception{
    	OrdOrdersInfoTF ordOrdersInfoTF=(OrdOrdersInfoTF)SysContexts.getBean("ordersInfoTF");
    	return ordOrdersInfoTF.queryOrders(inParam);
    	
    }
    /**
     * 接口编码：700008
     * 微信订单详情
     * @param inParam
     * 			接口入参
     * 					orderId 	订单号
     * @return	
     * 			接口出参
     * 					orderId				订单号
     * 					trackingNum			运单号
     * 					cityName			到站城市
     * 					createDate			创建时间
     * 					orderState			订单状态
     * 					consigneeName		收货人名字
     * 					consigneeBill		收货人手机号
     * 					deliveryAddress		收货地址
     * 					carrierName			拉包工名称
     * 					carrierBill			拉包工电话
     * 					companyName			物流公司名称
     * 					consignorName		发货人名称
     * 					consignorBill		发货人手机号
     * 					address				发货地址
     * 					serviceTypeName		配送方式
     * 					paymentTypeName		付款方式名称
     * 					planPickupTime		预约提货时间
     * 		
     * @throws Exception
     */
    public Map queryOrdersInfo(Map<String,Object> inParam)throws Exception{
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordersInfoTF.queryOrdersInfo(inParam)));
	}
    
    /**
     * 判断用户的openId是否绑定
     * @param inParam
     * @return
     * @throws Exception
     */
	public Map<String, Object> checkOpenIdExist(Map<String, String> inParam) throws Exception {
		
		String openId = DataFormat.getStringKey(inParam, "openId");
		if (com.framework.core.util.CommonUtil.isEmpty(openId)) {
			throw new BusinessException("openId为空！");
		}
		CmUserSV cmUserSV = (CmUserSV) SysContexts.getBean("cmUserSv");
		CmUserInfo cmUser = cmUserSV.queryUserByOpenId(openId);
		// 未注册
		Map<String, Object> map = new HashMap<String, Object>();
		if (cmUser == null) {
			map.put("result","N");
		}else{
			map.put("result","Y");
			map.put("userId", String.valueOf(cmUser.getUserId()));
			map.put("userName", cmUser.getUserName());
			map.put("telphone", cmUser.getLoginAcct());
			map.put("userType", cmUser.getUserType());
			if(cmUser.getUserType()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
	        	  CmUserOrgRelTF cmUserOrgRelTF = (CmUserOrgRelTF) SysContexts.getBean("cmUserOrgRelTF");
	              List<Organization> list = cmUserOrgRelTF.queryOrgByUserId(cmUser.getUserId());
	              if(list != null && list.size() > 0){
	            	  map.put("orgId", String.valueOf(list.get(0).getOrgId()));
	            	  map.put("tenantId", String.valueOf(list.get(0).getTenantId()));
	              }
	          }
			Map<String, Object> attrs=new HashMap<String, Object>();
	        attrs.put(LoginConst.BaseUserAttr.LOGIN_ACCOUNT, cmUser.getLoginAcct());
	        map.put("attrs", attrs);
		}
		
		return map;

	}
    /**
     * 700012
     * @param inParam
     * @return
     */
	public Map<String,Object> getWeChatSms(Map<String,Object> inParam){
		return ((SysSmsSendTF)SysContexts.getBean("sysSmsSendTF")).getWeChatSms();
	}
	/**
	 * 700013
	 * 移进历史表
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> removeSysSmsSend(Map<String,Object> inParam)throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("info", ((SysSmsSendTF)SysContexts.getBean("sysSmsSendTF")).removeSysSmsSend(inParam));
		return map;
	}
}
