package com.wo56.business.sys.vo.out;

import java.util.Date;

import com.framework.core.svcaller.vo.BaseOutParamVO;
import com.framework.core.util.SysStaticDataUtil;

public class SysRoleOut extends BaseOutParamVO{
	private static final long serialVersionUID = 3547589906134075157L;
	
	private String tenantCode;
	private String deptName;
	//private long tenantId;
	/**用户名字*/
	private int roleId;
	private int roleType;
	private String roleTypeName;
	private String roleName;
	//private Date createDate;
	private long lastModifyOperatorId;
	private String userName;
	private int menuType;
	private String menuTypeName;
	
	
	public String getMenuTypeName() {
		if(getMenuType() <= 0){
			return "";
		}
		return SysStaticDataUtil.getSysStaticData("MENU_TYPE", String.valueOf(getMenuType())).getCodeName();
	}
	public void setMenuTypeName(String menuTypeName) {
		this.menuTypeName = menuTypeName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getMenuType() {
		return menuType;
	}
	public void setMenuType(int menuType) {
		this.menuType = menuType;
	}
	public long getLastModifyOperatorId() {
		return lastModifyOperatorId;
	}
	public void setLastModifyOperatorId(long lastModifyOperatorId) {
		this.lastModifyOperatorId = lastModifyOperatorId;
	}
	public String getTenantCode() {
		return tenantCode;
	}
	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getRoleType() {
		return roleType;
	}
	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}
	public String getRoleTypeName() {
		return roleTypeName;
	}
	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
	
}
