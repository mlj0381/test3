package com.wo56.business.sche.serve.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class ServeVisitSaveParamIn extends PageInParamVO implements IParamIn {

	/**   
	 * 运单号.  
	 */
	private String wayBill;

	/**   
	 * 服务态度.  
	 */
	private Integer level;

	/**   
	 * 访问内容.  
	 */
	private String question;

	/**   
	 * 回访状态.  
	 */
	private Integer state;
	
	private String stateName;

	/**   
	 * 操作人ID.  
	 */
	private Long opId;

	/**   
	 * 操作人姓名.  
	 */
	private String opName;

	/**   
	 * 拓展字段1.  
	 */
	private String ext1;

	/**   
	 * 拓展字段2.  
	 */
	private String ext2;

	/**   
	 * 拓展字段3.  
	 */
	private String ext3;

	/**   
	 * 拓展字段4.  
	 */
	private String ext4;

	/**   
	 * 拓展字段5.  
	 */
	private String ext5;
	
	private long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWayBill() {
		return wayBill;
	}

	public void setWayBill(String wayBill) {
		this.wayBill = wayBill;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public String getExt4() {
		return ext4;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public String getExt5() {
		return ext5;
	}

	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.SERVE.VISIT_SAVE;
	}

}
