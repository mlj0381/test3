package com.wo56.common.utils;


public class CheckUtils {

//	public static long checkUser(long userId){
//		if(userId<=0){
//			throw new BusinessException("请传入操作人id");
//		}
//		Session session = SysContexts.getEntityManager();
//		CmUserInfo cmUserInfo = (CmUserInfo) session.get(CmUserInfo.class, userId);
//		if(cmUserInfo == null){
//			throw new BusinessException("找不到您的用户信息!");
//		}
//		if(cmUserInfo.getOrgId() == null || cmUserInfo.getOrgId().longValue()<=0){
//			throw new BusinessException("用户信息异常");
//		}
//		return cmUserInfo.getOrgId().longValue();
//	}
}
