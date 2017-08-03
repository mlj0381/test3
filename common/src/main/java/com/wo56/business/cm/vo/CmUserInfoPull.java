package com.wo56.business.cm.vo;

public class CmUserInfoPull {
	private Long id;
	private Long userId;
	private Long province;
	private String provinceName;
	private Long city;
	private String cityName;
	private Long district;
	private String districtName;
	private Long tenantId;
	private Integer pullState;
	private String frontIdCard;
	private String backIdCard;
	private Integer pullWork;
	private Long singularNum;
	private Integer cooperationMode;
	private String address;
	private String remark;
	private String jobNumber;
	private Long defaultSingularNum;
	
	
	public Long getDefaultSingularNum() {
		return defaultSingularNum;
	}
	public void setDefaultSingularNum(Long defaultSingularNum) {
		this.defaultSingularNum = defaultSingularNum;
	}
	public String getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getCooperationMode() {
		return cooperationMode;
	}
	public void setCooperationMode(Integer cooperationMode) {
		this.cooperationMode = cooperationMode;
	}
	public Long getSingularNum() {
		return singularNum;
	}
	public void setSingularNum(Long singularNum) {
		this.singularNum = singularNum;
	}
	public String getFrontIdCard() {
		return frontIdCard;
	}
	public void setFrontIdCard(String frontIdCard) {
		this.frontIdCard = frontIdCard;
	}
	public String getBackIdCard() {
		return backIdCard;
	}
	public void setBackIdCard(String backIdCard) {
		this.backIdCard = backIdCard;
	}
	public Integer getPullWork() {
		return pullWork;
	}
	public void setPullWork(Integer pullWork) {
		this.pullWork = pullWork;
	}
	public Integer getPullState() {
		return pullState;
	}
	public void setPullState(Integer pullState) {
		this.pullState = pullState;
	}
	public CmUserInfoPull(){
		
	}
	public CmUserInfoPull(Long id, Long userId, Long province,
			String provinceName, Long city, String cityName, Long district,
			String districtName, Long tenantId) {
		super();
		this.id = id;
		this.userId = userId;
		this.province = province;
		this.provinceName = provinceName;
		this.city = city;
		this.cityName = cityName;
		this.district = district;
		this.districtName = districtName;
		this.tenantId = tenantId;
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
	public Long getProvince() {
		return province;
	}
	public void setProvince(Long province) {
		this.province = province;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public Long getCity() {
		return city;
	}
	public void setCity(Long city) {
		this.city = city;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Long getDistrict() {
		return district;
	}
	public void setDistrict(Long district) {
		this.district = district;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	
}
