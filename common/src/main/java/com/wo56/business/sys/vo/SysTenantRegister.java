package com.wo56.business.sys.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class SysTenantRegister implements Serializable{
	private Long id;
	private String logisticsName;
	private String linkName;
	private String telephone;
	private String address;
	private Date boardTime;
	private String boardName;
	private String remark;
	
	
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public SysTenantRegister(){
		
	}
	public SysTenantRegister(long id, String logisticsName, String linkName,
			String telephone, String address, Date boardTime, String remark, String boardName) {
		super();
		this.id = id;
		this.logisticsName = logisticsName;
		this.linkName = linkName;
		this.telephone = telephone;
		this.address = address;
		this.boardTime = boardTime;
		this.remark = remark;
		this.boardName = boardName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogisticsName() {
		return logisticsName;
	}
	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBoardTime() {
		return boardTime;
	}
	public void setBoardTime(Date boardTime) {
		this.boardTime = boardTime;
	}
	
}
