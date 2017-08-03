package com.wo56.business.sys.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import com.framework.components.fdfs.vo.SysAttach;
import com.framework.core.SysContexts;

public class SysAttachSV {
	/**
	 * lyh
	 * 获取图片地址
	 * @param flowId
	 * @return
	 * @throws Exception
	 */
	public String getAttachFile(String flowId) throws Exception{
		Session session = SysContexts.getEntityManager(true);
		if (StringUtils.isNotEmpty(flowId)) {
			SysAttach sysAttach = (SysAttach) session.get(SysAttach.class,Long.valueOf(flowId));
			if(sysAttach != null && sysAttach.getFullPathUrl() != null){
				return sysAttach.getFullPathUrl();
			}
		}
		return "";
	}
}
