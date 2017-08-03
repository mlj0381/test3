package com.wo56.business.ac.vo.in;

import java.util.HashMap;
import java.util.Map;



/**
 * 
 * @author zjy 
 * （1） 表示必传 （0）  非必传
 * 
 * */
public class AcOrgIdFeeParamIn {

	/**网点id (1)**/
	private long orgId;
	/**收费类型  1起始网点2途中网点3到达网点 (1)**/
	private int  collectType;
	/***目的区域id (1)**/
	private long areaId;
	/**开单人租户id （1）*/
	private long tenantId;
	/**重量 （1）*/
	private double weight;
    /**体积 （1）*/
	private double volume;
	/**
	 * 是否有电梯（key=1004 value=1注：1 表示有 0 表示没有）
	 * 楼层（key=1005 value=楼层） 
	 * 代收货款（key=1006 value=金额（单位:分））
	 * 是否存在配送区域 （key='isMap',value=N N 表示没有）
	 * 值定义
	 * 是否有电梯、楼层建立在上楼费存在的情况 （1）**/
	private Map map;
	/***是否计算装卸费*/
	private boolean isCalHandling;
	
	
	
	public boolean isCalHandling() {
		return isCalHandling;
	}
	public void setCalHandling(boolean isCalHandling) {
		this.isCalHandling = isCalHandling;
	}
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	public int getCollectType() {
		return collectType;
	}
	public void setCollectType(int collectType) {
		this.collectType = collectType;
	}
	public long getAreaId() {
		return areaId;
	}
	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}
	public long getTenantId() {
		return tenantId;
	}
	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public Map getMap() {
		if(map==null){
			map=new HashMap();
		}
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	
	
	
	
}
