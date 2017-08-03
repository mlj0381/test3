package com.wo56.business.sche.credit.vo;

public class CalculateResultDetail {
	private long itemId;// 项目ID
	private long itemValue;// 项目值
	private long itemScore;// 项目分数
	public long getItemScore() {
		return itemScore;
	}
	public void setItemScore(long itemScore) {
		this.itemScore = itemScore;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public long getItemValue() {
		return itemValue;
	}
	public void setItemValue(long itemValue) {
		this.itemValue = itemValue;
	}
}
