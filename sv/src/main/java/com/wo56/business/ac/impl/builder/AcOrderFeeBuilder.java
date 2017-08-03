package com.wo56.business.ac.impl.builder;

import com.wo56.business.ac.vo.AcOrderFee;
import com.wo56.common.consts.AccountManageConsts;

public class AcOrderFeeBuilder {
	
	private AcOrderFee acOrderFee;
 
		//支付状态1、未收2、已收3、已支4、未支
		private int paySts;
		//收支类型:1收入2支出
		private int payType;
		//费用金额
		private long amount;
		//对方对象类型：1操作员2业务员3开单员4网点5  客户 6中转方7发货方8收货方9司机10师傅
		private int objType;
		//对方对象ID(业务员ID、客户ID、网点ID)
		private long objId;
		//费用科目
		private int constantValue;
		//对方对象名称
		private String objName;
		/**   
		 * 判断对象和对方对象是否是同一租户：1同租户2：不同租户 
		 */
		private int tenantType;
		/**   
		 * 反转后的核销网点ID （1：如果对方对象是网点或协议发货方，存在网点就保存对方对象网点2：其他情况就写反转之前的核销网点）
		 */
		private long reversalOrgId;
		
		/**   
		 * 是否要反转： 1:需要反转 2：不需要反转
		 */
		private long isReversal;
		/**   
		 * 反转后的对方对象名称
		 */
		private String reversalName;
		/**   
		 * 对象类型
		 */
		private int otype;
		

	/**
	 * @param constantValue     费用科目
	 * @param paySts            支付状态1、未收2、已收3、已支4、未支
	 * @param payType           收支类型:1收入2支出
	 * @param amount            费用金额
	 * @param objType           对方对象类型
	 * @param objId             对方对象ID
	 * @param objName           对方对象名称
	 * @param tenantType        判断对象和对方对象是否是同一租户：1同租户2：不同租户 
	 * @param reversalOrgId     反转后的核销网点ID （1：如果对方对象是网点或协议发货方，存在网点就保存对方对象网点2：其他情况就写反转之前的核销网点）
	 * @param isReversal        是否要反转： 1:需要反转 2：不需要反转
	 * @param reversalName      反转后的对方对象名称
	 */
	public AcOrderFeeBuilder(int paySts, int payType,long amount, int objType, long objId, int constantValue,
			     String objName, int tenantType, long reversalOrgId,
			                   long isReversal, String reversalName) {
		this.paySts = paySts;
		this.payType = payType;
		this.amount = amount;
		this.objType = objType;
		this.objId = objId;
		this.constantValue = constantValue;
		this.objName = objName;
		this.tenantType = tenantType;
		this.reversalOrgId = reversalOrgId;
		this.isReversal = isReversal;
		this.reversalName = reversalName;
	}

	public AcOrderFee createAcOrderFee() throws Exception {
	     acOrderFee = new AcOrderFee();
		 //读取配置
//		 AcFeeConfig afc = AcFeeCacheDataUtil.getAcFeeConfigByConstantValue(constantValue);
 		 AcOrderFee acOrderFee = new AcOrderFee();
//		 BeanUtils.copyProperties(acOrderFee, afc);
 		 acOrderFee.setFeeType(constantValue);
 		 acOrderFee.setConstantValue(constantValue);
		 //核销数据类型
		 acOrderFee.setBusiType(AccountManageConsts.AcAccountDtl.BUSI_TYPE_CASH);//lyh都是现金账户
		 acOrderFee.setPaySts(paySts);
		 acOrderFee.setPayType(payType);
		 
		//费用
		acOrderFee.setAmount(amount);
		
		//对方对象
		acOrderFee.setObjType(objType);
		acOrderFee.setObjId(objId);
		acOrderFee.setObjName(objName);
	 
		//反转新增
		acOrderFee.setReversalOrgId(reversalOrgId);
		acOrderFee.setTenantType(tenantType);
		acOrderFee.setIsReversal(isReversal);
		acOrderFee.setReversalName(reversalName);
		
 		//对象
 		acOrderFee.setOtype(otype);
		return acOrderFee;
	}
	
	
	/**
	 * @param constantValue     费用科目
	 * @param paySts            支付状态1、未收2、已收3、已支4、未支
	 * @param payType           收支类型:1收入2支出
	 * @param amount            费用金额
	 * @param objType           对方对象类型
	 * @param objId             对方对象ID
	 * @param objName           对方对象名称
	 * @param tenantType        判断对象和对方对象是否是同一租户：1同租户2：不同租户 
	 * @param reversalOrgId     反转后的核销网点ID （1：如果对方对象是网点或协议发货方，存在网点就保存对方对象网点2：其他情况就写反转之前的核销网点）
	 * @param isReversal        是否要反转： 1:需要反转 2：不需要反转
	 * @param reversalName      反转后的对方对象名称
	 * @param otype             对象类型
	 */
	public AcOrderFeeBuilder(int paySts, int payType,long amount, int objType, long objId, int constantValue,
			     String objName, int tenantType, long reversalOrgId,
			                   long isReversal, String reversalName,int otype) {
		this.paySts = paySts;
		this.payType = payType;
		this.amount = amount;
		this.objType = objType;
		this.objId = objId;
		this.constantValue = constantValue;
		this.objName = objName;
		this.tenantType = tenantType;
		this.reversalOrgId = reversalOrgId;
		this.isReversal = isReversal;
		this.reversalName = reversalName;
		this.otype = otype;
	}

}
