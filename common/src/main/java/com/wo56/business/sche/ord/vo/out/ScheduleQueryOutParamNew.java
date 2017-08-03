package com.wo56.business.sche.ord.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class ScheduleQueryOutParamNew extends BaseOutParamVO{
	private Double weight;// 重量
	private Double volumes;// 体积
	private Integer count;// 件数
	private String collectingMoney;// 代收货款(元)
	private String freightCollect;// 到付
	private String branchAndInstall;// 配安费
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	 
 
	public Double getVolumes() {
		return volumes;
	}
	public void setVolumes(Double volumes) {
		this.volumes = volumes;
	}
	public String getCollectingMoney() {
		return collectingMoney;
	}
	public void setCollectingMoney(String collectingMoney) {
		this.collectingMoney = collectingMoney;
	}
	public String getFreightCollect() {
		return freightCollect;
	}
	public void setFreightCollect(String freightCollect) {
		this.freightCollect = freightCollect;
	}
	public String getBranchAndInstall() {
		return branchAndInstall;
	}
	public void setBranchAndInstall(String branchAndInstall) {
		this.branchAndInstall = branchAndInstall;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

 
 
	
}	