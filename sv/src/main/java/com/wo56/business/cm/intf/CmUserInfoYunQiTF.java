package com.wo56.business.cm.intf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.encrypt.K;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.JsonHelper;
import com.wo56.business.ac.interfaces.AcPaymentMethodTF;
import com.wo56.business.ac.vo.AcPaymentMethod;
import com.wo56.business.cm.impl.CmUserInfoYunQiSV;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.out.CmUserInfoYunQiOut;
import com.wo56.business.sys.impl.SysAttachSV;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;

public class CmUserInfoYunQiTF {
	/**
	 * 个人中心
	 * app(601007)
	 */
	@SuppressWarnings("unchecked")
	public CmUserInfoYunQiOut getCmUserInfoYunQi(long userId) throws Exception{
		CmUserInfoYunQiSV userSV = (CmUserInfoYunQiSV) SysContexts.getBean("cmUserInfoYunQiSV");
		Query query = userSV.getCmUserInfo(userId);
		List<Object[]> list = query.list();
		SysAttachSV sysAttachSV = (SysAttachSV) SysContexts.getBean("sysAttachSV");
		CmUserInfoYunQiOut out = new CmUserInfoYunQiOut();
		if(list != null && list.size() > 0){
			for (Object[] objects : list) {
				out.setUserId(objects[0] != null ? String.valueOf(objects[0]) : "");
				out.setUserType(objects[1] != null ? String.valueOf(objects[1]) : "");
				out.setBill(objects[2] != null ? String.valueOf(objects[2]) : "");
				out.setUserName(objects[3] != null ? String.valueOf(objects[3]) : "");
				out.setUserPic(objects[4] != null ? String.valueOf(objects[4]) : "");
				out.setPullState(objects[5] != null ? String.valueOf(objects[5]) : "");
				out.setFrontIdCard(objects[6] != null ? String.valueOf(objects[6]) : "");
				out.setBackIdCard(objects[7] != null ? String.valueOf(objects[7]) : "");
				out.setUserPicUrl(sysAttachSV.getAttachFile(out.getUserPic()));
				out.setFrontIdCardUrl(sysAttachSV.getAttachFile(out.getFrontIdCard()));
				out.setBackIdCardUrl(sysAttachSV.getAttachFile(out.getBackIdCard()));
				String jobNumber = objects[8] != null ? String.valueOf(objects[8]) : "";
				String city = objects[9] != null ? String.valueOf(objects[9]) : "";
				out.setJobNumber(CommonUtil.jobNumberRepByCity(jobNumber, city));
				out.setPullWork(objects[10] != null ? objects[10].toString() : "");
			}
		}
		AcPaymentMethodTF acPaymentMethodTF = (AcPaymentMethodTF) SysContexts.getBean("acPaymentMethodTF");
		AcPaymentMethod pay = acPaymentMethodTF.getAcPaymentMethodByUserId(userId);
		if(pay != null){
			out.setBankHolder(pay.getBankHolder() != null ? pay.getBankHolder() : "");
			out.setIdCard(pay.getIdCard() != null ? pay.getIdCard() : "");
			out.setBankCard(pay.getBankCard() != null ? pay.getBankCard() : "");
			out.setBankBillId(pay.getBill() != null ? pay.getBill() : "");
			out.setBankName(pay.getBankName() != null ? pay.getBankName() : "");
			out.setProvince(String.valueOf(pay.getProvince() != null ? pay.getProvince() : ""));
			out.setProvinceName(pay.getProvinceName() != null ? pay.getProvinceName() : "");
			out.setCity(String.valueOf(pay.getCity() != null ? pay.getCity() : ""));
			out.setCityName(pay.getCityName() != null ? pay.getCityName() : "");
			out.setPaymentType(String.valueOf(pay.getPaymentType() != null ? pay.getPaymentType() : ""));
			out.setAccountNum(pay.getAccountNum() != null ? pay.getAccountNum() : "");
			out.setAccountName(pay.getAccountName() != null ? pay.getAccountName() : "");
		}
		
		
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public boolean checkPhone(String billId){
		CmUserInfoYunQiSV userSV = (CmUserInfoYunQiSV) SysContexts.getBean("cmUserInfoYunQiSV");
		List<CmUserInfo> list = userSV.getCmUserInfoBill(billId).list();
		if(list!=null && list.size() > 0){
			return false;
		}
		return true;
	}
	/**
	 * 修改密码 -- 云企
	 * @param userId
	 * @param loginPwd
	 * @param newLoginPwd
	 * @return
	 * @throws Exception
	 */
	public String updateUserPassWord(long userId,String loginPwd,String newLoginPwd)throws Exception{
		CmUserInfoTF userTF = (CmUserInfoTF) SysContexts.getBean("cmUserInfoTF");
		CmUserInfo user = userTF.getUserInfo(userId, 0);
		newLoginPwd = K.j_s(newLoginPwd);
		
		//newLoginPwd = 
		user.setLoginPwd(newLoginPwd);
		CmUserInfoYunQiSV userYunQiSV = (CmUserInfoYunQiSV) SysContexts.getBean("cmUserInfoYunQiSV");
		return userYunQiSV.updateCmUserInfo(user);
	}
	/**
	 * 修改昵称
	 * @param userId
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public String updataUserName(long userId,String userName)throws Exception{
		CmUserInfoYunQiSV userYunQiSV = (CmUserInfoYunQiSV) SysContexts.getBean("cmUserInfoYunQiSV");
		CmUserInfoTF userTF = (CmUserInfoTF) SysContexts.getBean("cmUserInfoTF");
		CmUserInfo user = new CmUserInfo();
		user = userTF.getUserInfo(userId, 0);
		if(user.getUserName().equals(userName)){
			throw new BusinessException("旧的昵称和新的昵称一样，无需修改！");
		}
		user.setUserName(userName);
		return userYunQiSV.updateCmUserInfo(user);
	}
	/**
	 * 上传用户头像
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String uploadUserPic(Map<String,String>inParam)throws Exception{
		BaseUser base= SysContexts.getCurrentOperator();
		long userId=base.getUserId();
		if(userId<0){
			throw new BusinessException("userId不能为空");
		}
		CmUserInfoYunQiSV userYunQiSV = (CmUserInfoYunQiSV) SysContexts.getBean("cmUserInfoYunQiSV");
		CmUserInfoTF userTF = (CmUserInfoTF) SysContexts.getBean("cmUserInfoTF");
		CmUserInfo user = new CmUserInfo();
		user = userTF.getUserInfo(userId,-1);
		String userPic=DataFormat.getStringKey(inParam, "userPicId");
		if(StringUtils.isEmpty(userPic)){
			throw new BusinessException("图片Id不能为空");
		}
		user.setUserPic(userPic);
		return userYunQiSV.updateCmUserInfo(user);
	}
	/**
	 * 获取用户信息
	 */
	public CmUserInfo getCmUserInfo(long userId){
		Session session = SysContexts.getEntityManager(true);
		return  (CmUserInfo) session.get(CmUserInfo.class, userId);
	}
	/**
	 * 手机号获取用户信息
	 * @param bill
	 * @param userType
	 * @return
	 */
	public CmUserInfo getCmUserByLoginAcct(String bill,int userType){
		CmUserInfoYunQiSV userYunQiSV = (CmUserInfoYunQiSV) SysContexts.getBean("cmUserInfoYunQiSV");
		List<CmUserInfo> list = userYunQiSV.getCmUserInfoList(userType, bill);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
