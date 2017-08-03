package com.wo56.business.sys.vo;

/**
 * SysPrintItem entity. @author MyEclipse Persistence Tools
 */

public class SysPrintItem implements java.io.Serializable {

	// Fields

	private Long id;
	private Long configId;
	private Integer itemType;
	private String itemDesc;
	private String objectKey;
	private String specialObjectValue;
	private String formatFunc;
	private Integer posType;
	private String relObjectKey;
	private Double offsetValue;
	private Double topOffset;
	private Double leftOffset;
	private Double itemWidth;
	private Double itemHeight;
	private Integer fontSize;
	private Integer fontBold;
	private Integer state;

	// Constructors

	/** default constructor */
	public SysPrintItem() {
	}

	/** minimal constructor */
	public SysPrintItem(Integer itemType, String itemDesc, String objectKey,
			Integer posType, Double itemWidth, Double itemHeight) {
		this.itemType = itemType;
		this.itemDesc = itemDesc;
		this.objectKey = objectKey;
		this.posType = posType;
		this.itemWidth = itemWidth;
		this.itemHeight = itemHeight;
	}

	/** full constructor */
	public SysPrintItem(Integer itemType, String itemDesc, String objectKey,
			String specialObjectValue, String formatFunc, Integer posType,
			Double offsetValue, Double topOffset, Double leftOffset,
			Double itemWidth, Double itemHeight, Integer fontSize,
			Integer fontBold) {
		this.itemType = itemType;
		this.itemDesc = itemDesc;
		this.objectKey = objectKey;
		this.specialObjectValue = specialObjectValue;
		this.formatFunc = formatFunc;
		this.posType = posType;
		this.offsetValue = offsetValue;
		this.topOffset = topOffset;
		this.leftOffset = leftOffset;
		this.itemWidth = itemWidth;
		this.itemHeight = itemHeight;
		this.fontSize = fontSize;
		this.fontBold = fontBold;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public Integer getItemType() {
		return this.itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public String getItemDesc() {
		return this.itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getObjectKey() {
		return this.objectKey;
	}

	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	public String getSpecialObjectValue() {
		return this.specialObjectValue;
	}

	public void setSpecialObjectValue(String specialObjectValue) {
		this.specialObjectValue = specialObjectValue;
	}

	public String getFormatFunc() {
		return this.formatFunc;
	}

	public void setFormatFunc(String formatFunc) {
		this.formatFunc = formatFunc;
	}

	public Integer getPosType() {
		return this.posType;
	}

	public void setPosType(Integer posType) {
		this.posType = posType;
	}

	public String getRelObjectKey() {
		return relObjectKey;
	}

	public void setRelObjectKey(String relObjectKey) {
		this.relObjectKey = relObjectKey;
	}

	public Double getOffsetValue() {
		return this.offsetValue;
	}

	public void setOffsetValue(Double offsetValue) {
		this.offsetValue = offsetValue;
	}

	public Double getTopOffset() {
		return this.topOffset;
	}

	public void setTopOffset(Double topOffset) {
		this.topOffset = topOffset;
	}

	public Double getLeftOffset() {
		return this.leftOffset;
	}

	public void setLeftOffset(Double leftOffset) {
		this.leftOffset = leftOffset;
	}

	public Double getItemWidth() {
		return this.itemWidth;
	}

	public void setItemWidth(Double itemWidth) {
		this.itemWidth = itemWidth;
	}

	public Double getItemHeight() {
		return this.itemHeight;
	}

	public void setItemHeight(Double itemHeight) {
		this.itemHeight = itemHeight;
	}

	public Integer getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public Integer getFontBold() {
		return this.fontBold;
	}

	public void setFontBold(Integer fontBold) {
		this.fontBold = fontBold;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}