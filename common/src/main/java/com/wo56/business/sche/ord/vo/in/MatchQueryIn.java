package com.wo56.business.sche.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;

public class MatchQueryIn implements IParamIn{

	 private String inCode;
	 private String id;
	 private String percent;
	 private String similarGroup;
	 private Integer min;
	 private Integer max;
	 private Integer score;
	 private String color;
	 
	 
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getInCode() {
		return inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getSimilarGroup() {
		return similarGroup;
	}
	public void setSimilarGroup(String similarGroup) {
		this.similarGroup = similarGroup;
	}
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	 
	 
	 
	 
}
