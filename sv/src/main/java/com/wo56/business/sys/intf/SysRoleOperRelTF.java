package com.wo56.business.sys.intf;

import com.framework.core.SysContexts;
import com.wo56.business.sys.impl.SysRoleOperRelSV;
import com.wo56.business.sys.vo.SysRoleOperRel;


public class SysRoleOperRelTF {

	/**
	 * 
	 * @param roleOperRel
	 */
	public void saveSysRoleOperRel(SysRoleOperRel roleOperRel){
		SysRoleOperRelSV sysRoleSV = (SysRoleOperRelSV)SysContexts.getBean("sysRoleOperRelSV");
		sysRoleSV.saveSysRoleOperRel(roleOperRel);
	}
	/**
	 * @param userId
	 * @return
	 */
	public SysRoleOperRel querySysRoleOperRel(Long userId){
		SysRoleOperRelSV sysRoleSV = (SysRoleOperRelSV)SysContexts.getBean("sysRoleOperRelSV");
		return sysRoleSV.querySysRoleOperRel(userId);
	}
}
