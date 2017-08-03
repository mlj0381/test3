package com.wo56.business.ac.vo;

public class AcUserRel {
	private Long id;
	private Long userId;
	private Long grossIncome;
	
	public AcUserRel(){
		
	}
	public AcUserRel(Long id, Long userId, Long grossIncome) {
		super();
		this.id = id;
		this.userId = userId;
		this.grossIncome = grossIncome;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getGrossIncome() {
		return grossIncome;
	}
	public void setGrossIncome(Long grossIncome) {
		this.grossIncome = grossIncome;
	}
	
}
