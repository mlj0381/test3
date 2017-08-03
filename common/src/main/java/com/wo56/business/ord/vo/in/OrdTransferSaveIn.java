package com.wo56.business.ord.vo.in;

import java.util.Date;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdTransferSaveIn extends PageInParamVO implements IParamIn {

	private static final long serialVersionUID = -3107996059755769605L;

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.TRANSFER_SAVE;
	}

	/** 内部订单号*/
	private Long orderId;
	/**承运商ID.*/
	private Long carrierCompanyId;
	/**承运商名称*/
	private String carrierCompanyName;
	/** 外发单号.*/
	private Long outgoingTrackingNum;
	/**本地联系人.*/
	private String linkerName;
	/**联系电话.*/
	private String linkerPhone;
	/**提货电话.*/
	private String deliveryPhone;
	/**提货地址.*/
	private String deliveryAddress;
	/** 外发费*/
	private Long outgoingFee;
	/**创建时间.*/
	private Date createDate;
	/**操作员.*/
	private Long opId;
	/** 新增中转外发纪录时，当前订单的状态 */
	private Integer currentOrderState;
	/** 应收 */
	private Long shouldReceivables;
	/** 现金已收；0:未收; 1:已收 */
	private Integer isReceivedShouldReceivables;
	/** 应付 */
	private Long shouldPay;
	/** 现金已付；0:未付; 1:已付 */
	private Integer isReceivedShouldPay;
	/**备注.*/
	private String remarks;
	/**扩展字段.*/
	private String ext1;
	/** 扩展字段 */
	private String ext2;
	private String opIdName;
	/** 到付款 */
	private Long freightCollect;
	/** 核销状态 1、已核销;2、未核销 */
	private int checkSts;
	/** 核销时间 */
	private Date checkDate;
	
	
	/**
	 * ord_seller_order
	 */
	private Long sellerOrderId;
	private Integer lineType;
	private Integer source;
	private Integer level;
	private String prodId;
	private String prodName;
	private String prodAbout;
	private String stere;
	private String weight;
	private Integer count;
	private Long provinceId;
	private String provinceName;
	private Long cityId;
	private String cityName;
	private Long countyId;
	private String countyName;
	private Long townId;
	private String townName;
	private String receiverTel;
	private String receiverName;
	private String receiverAddress;
	private Integer transportType;
	private Long orgId;
	private String orgName;
	private String orgTel;
	private String orgRemark;
	private Integer orderType;
	private String trackNum;
	private Long lineOrgId;
	private Long lineTenantId;
	private Integer state;
	private String lineContractor;
	private String lineTel;
	private String lineRemark;
	private Long lineContractorId;
	private Date opDate;
	private Long notifyId;
	private Long tenantId;
	
}
