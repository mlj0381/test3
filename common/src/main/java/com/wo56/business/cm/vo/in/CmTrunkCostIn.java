package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
/**
 * 新增、删除、查询不分页可以使用这IN
 */
public class CmTrunkCostIn extends PageInParamVO implements IParamIn {
	private String inCode;
	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return this.inCode;
	}
	
	
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}


	public CmTrunkCostIn(String inCode) {
		super();
		this.inCode = inCode;
	}
	
	private long costId;
	private String orgId; //归属组织ID
	private String costDetailId; //费用详情主键
	private String tenantId; 
	private String businessOrgId; //商家ID
	private String paymentType; //支付枚举
	private String costPic;   //干线费用图片ID
	private String costPicUrl; //干线费用图片半URL
	private String costInstallPic; //配安费用图片ID
	private String costInstallPicUrl;  //配安费用图片半URL
	
	private String province; //省枚举
	private String city;  //市枚举
	private String county; //区枚举
	private String weightPrice; //单位分
	private String volumePrice; //单位分
	private String countPrice;  //单位分 
	private String type;  //
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	private String saveString;  //保存的字符串数组
	
	public String getSaveString() {
		return saveString;
	}


	public void setSaveString(String saveString) {
		this.saveString = saveString;
	}


	public String getOrgId() {
		return orgId;
	}


	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	public String getBusinessOrgId() {
		return businessOrgId;
	}


	public void setBusinessOrgId(String businessOrgId) {
		this.businessOrgId = businessOrgId;
	}


	public String getPaymentType() {
		return paymentType;
	}


	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}


	public String getCostPic() {
		return costPic;
	}


	public void setCostPic(String costPic) {
		this.costPic = costPic;
	}


	public String getCostPicUrl() {
		return costPicUrl;
	}


	public void setCostPicUrl(String costPicUrl) {
		this.costPicUrl = costPicUrl;
	}


	public String getCostInstallPic() {
		return costInstallPic;
	}


	public void setCostInstallPic(String costInstallPic) {
		this.costInstallPic = costInstallPic;
	}


	public String getCostInstallPicUrl() {
		return costInstallPicUrl;
	}


	public void setCostInstallPicUrl(String costInstallPicUrl) {
		this.costInstallPicUrl = costInstallPicUrl;
	}


	public String getTenantId() {
		return tenantId;
	}


	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCounty() {
		return county;
	}


	public void setCounty(String county) {
		this.county = county;
	}


	public String getWeightPrice() {
		return weightPrice;
	}


	public void setWeightPrice(String weightPrice) {
		this.weightPrice = weightPrice;
	}


	public String getVolumePrice() {
		return volumePrice;
	}


	public void setVolumePrice(String volumePrice) {
		this.volumePrice = volumePrice;
	}


	public String getCountPrice() {
		return countPrice;
	}


	public void setCountPrice(String countPrice) {
		this.countPrice = countPrice;
	}


	public String getCostDetailId() {
		return costDetailId;
	}


	public void setCostDetailId(String costDetailId) {
		this.costDetailId = costDetailId;
	}


	public long getCostId() {
		return costId;
	}


	public void setCostId(long costId) {
		this.costId = costId;
	}
	 
	
	
	
	

 
}
