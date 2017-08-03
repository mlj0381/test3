package com.wo56.business.ord.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class QueryMatchInfoSumOut extends BaseOutParamVO {
	private String freightDb;// 运费freight
	private String stevedoringDb;// 装卸费
	private Integer orderNum;// 总票数
	private Double volume;// 总体积
	private Double weight;// 总重量

	public String getFreightDb() {
		return freightDb;
	}

	public void setFreightDb(String freightDb) {
		this.freightDb = freightDb;
	}

	public String getStevedoringDb() {
		return stevedoringDb;
	}

	public void setStevedoringDb(String stevedoringDb) {
		this.stevedoringDb = stevedoringDb;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

}
