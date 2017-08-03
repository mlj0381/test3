package com.wo56.business.ord.intf;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.wo56.business.ord.vo.OrdChildOpLog;
import com.wo56.business.ord.vo.OrdDepartInfo;
import com.wo56.business.ord.vo.OrderInfoChild;
import com.wo56.business.org.vo.Organization;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class OrdChildOpLogTF {
	private final static String  orgName ="${orgName}";
	
	private final static String  phone ="${phone}";
	
	private final static String desOrgName = "${desOrgName}";
	
	private final static String name = "${name}";
	
	/**干线配载[增信1]网点新增配载*/
    private final static String TRANSPORT_IN="干线配载["+orgName+"]网点新增配载";
    /**配载对外日记*/
    private final static String TRANSPORT_ON="干线配载["+orgName+"]网点新增配载";
    
    private final static String DEPART_IN = "发车,前往["+desOrgName+"]网点,上一站["+orgName+"]网点,客服电话：["+phone+"]";
    
    private final static String DEPART_ON = "发车,前往["+desOrgName+"]网点,上一站["+orgName+"]网点,客服电话：["+phone+"]";
    
    private final static String CANCEL_DEPART_IN = "取消发车";
    
    private final static String CANCEL_DRPART_NO = "取消发车";
    
    private final static String ASTERN_IN = "到车,["+desOrgName+"]网点,上一站["+orgName+"]网点,客服电话：["+phone+"]";
    
    private final static String ASTERN_ON = "到车,["+desOrgName+"]网点,上一站["+orgName+"]网点,客服电话：["+phone+"]";
    
    private final static String CANCEL_ASTERN_IN = "取消到车";
    
    private final static String CANCEL_ASTERN_ON = "取消到车";
    
    private final static String CANCEL_ARRIVAL_IN ="取消到货";
    
    private final static String CANCEL_ARRIVAL_ON = "取消到货";
    
    private final static String BILLING_IN = "已揽货,客服电话：["+phone+"]";
    
    private final static String BILLING_ON = "已揽货,客服电话：["+phone+"]";
    
    private final static String CANCEL_TRANSPORT_IN = "取消配载";
    
    private final static String CANCEL_TRANSPORT_ON = "取消配载";
    
    /**干线到货对外的内容展示模板*/
    private final static String GX_ARRIVER_GOODS_OUT = "已到达["+desOrgName+"]网点,上一站["+orgName+"]网点,客服电话：["+phone+"]";
    /**干线到货对内的内容展示模板*/
    private final static String GX_ARRIVER_GOODS_IN = "已到达["+desOrgName+"]网点,上一站["+orgName+"]网点,客服电话：["+phone+"]";
    
    private final static String DISPATCHING_IN = "["+orgName+"]网点“"+name+"”进行配送成功";
    
    private final static String DISPATCHING_ON = "["+orgName+"]网点“"+name+"”进行配送成功";
    
    private final static String DO_SIGN_IN = "“"+name+"”进行签收";
    
    private final static String DO_SIGN_ON = "货物已签收";
    /**
     * 
     * @param orderInfoChild
     * @param type 1、发车；2、取消发车；3、到车；4、取消到车；5、取消到货；6、配载；7、开单；8、取消配载；9、到货；10、配送；11、签收
     * @param baseUser
     * @param ordDepartInfo
     */
    public void departLog(OrderInfoChild orderInfoChild,int type,BaseUser baseUser,OrdDepartInfo ordDepartInfo){
    	OrdChildOpLog ordOpLog = new OrdChildOpLog();
    	ordOpLog.setChildOrderId(orderInfoChild.getChildOrderId());
    	ordOpLog.setChildTrackingNum(orderInfoChild.getChildTrackingNumAli());
    	ordOpLog.setCreateDate(new Date());
  		ordOpLog.setOpId(baseUser.getUserId());
  		ordOpLog.setOpName(baseUser.getUserName());
  		ordOpLog.setOrderId(orderInfoChild.getOrderId());
  		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
  		Organization organization = OraganizationCacheDataUtil.getOrganization(baseUser.getOrgId());
  		
  		String inContent = "";
  		String onContent = "";
  		int opType = 0;
  		if (type == 1) {//发车
  			inContent = DEPART_IN.replace(desOrgName, ordDepartInfo.getDescOrgIdName()!=null ? ordDepartInfo.getDescOrgIdName():"")
  					.replace(orgName, ordDepartInfo.getSourceOrgIdName())
  					.replace(phone, organization.getSupportStaffPhone() != null ? organization.getSupportStaffPhone() : "");
  			onContent = DEPART_ON.replace(desOrgName, ordDepartInfo.getDescOrgIdName()!=null?ordDepartInfo.getDescOrgIdName():"" )
  					.replace(orgName, ordDepartInfo.getSourceOrgIdName())
  					.replace(phone, organization.getSupportStaffPhone() != null ? organization.getSupportStaffPhone() : "");
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.PUT_OUT;
		}else if(type == 2){//取消发车
			inContent = CANCEL_DEPART_IN;
  			onContent = CANCEL_DRPART_NO;
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.PUT_OUT_CANCEL;
		}else if(type == 3){//到车
			inContent = ASTERN_IN.replace(desOrgName, ordDepartInfo.getDescOrgIdName())
					.replace(orgName, ordDepartInfo.getSourceOrgIdName())
					.replace(phone, organization.getSupportStaffPhone() != null ? organization.getSupportStaffPhone() : "");
  			onContent = ASTERN_ON.replace(desOrgName, ordDepartInfo.getDescOrgIdName())
					.replace(orgName, ordDepartInfo.getSourceOrgIdName())
					.replace(phone, organization.getSupportStaffPhone() != null ? organization.getSupportStaffPhone() : "");
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.ASTRREN;
		}else if(type == 4){//取消到车
			inContent = CANCEL_ASTERN_IN;
  			onContent = CANCEL_ASTERN_ON;
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.CANCEL_ASTRREN;
		}else if(type == 5){//取消到货
			inContent = CANCEL_ARRIVAL_IN;
  			onContent = CANCEL_ARRIVAL_ON;
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.CANCEL_ARRIVAL;
		}else if(type == 6){//配载
			inContent = TRANSPORT_IN.replace(orgName,ordDepartInfo.getSourceOrgIdName()!= null ? ordDepartInfo.getSourceOrgIdName() : "");
  			onContent = TRANSPORT_ON.replace(orgName, ordDepartInfo.getSourceOrgIdName()!= null ? ordDepartInfo.getSourceOrgIdName() : "");
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.DEPART_OP;
		}else if (type == 7){//开单
			inContent = BILLING_IN.replace(phone, organization.getSupportStaffPhone()!=null?organization.getSupportStaffPhone():"");
  			onContent = BILLING_ON.replace(phone, organization.getSupportStaffPhone()!=null?organization.getSupportStaffPhone():"");
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.OPEN_ORDERS;
		}else if(type == 8){
			inContent = CANCEL_TRANSPORT_IN;
  			onContent = CANCEL_TRANSPORT_ON;
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.DEPART_OP;
		}else if(type == 9){//到货
			inContent = GX_ARRIVER_GOODS_IN.replace(desOrgName, ordDepartInfo.getDescOrgIdName() != null ? ordDepartInfo.getDescOrgIdName() : "")
					.replace(orgName, ordDepartInfo.getSourceOrgIdName() != null ? ordDepartInfo.getSourceOrgIdName() : "")
					.replace(phone, organization.getSupportStaffPhone() != null ? organization.getSupportStaffPhone() : "");
  			onContent = GX_ARRIVER_GOODS_OUT.replace(desOrgName, ordDepartInfo.getDescOrgIdName() != null ? ordDepartInfo.getDescOrgIdName() : "")
  					.replace(orgName, ordDepartInfo.getSourceOrgIdName() != null ? ordDepartInfo.getSourceOrgIdName() : "")
  					.replace(phone, organization.getSupportStaffPhone() != null ? organization.getSupportStaffPhone() : "");
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.GX_ARRIVE_GOODS;
		}else if(type == 10){//配送
			inContent = DISPATCHING_IN.replace(orgName, organization.getOrgName()).replace(name, baseUser.getUserName());
  			onContent = DISPATCHING_ON.replace(orgName, organization.getOrgName()).replace(name, baseUser.getUserName());
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.DISTRIBUTION_ORDERS;
		}else if(type == 11){
			inContent = DO_SIGN_IN.replace(name, baseUser.getUserName());
  			onContent = DO_SIGN_ON;
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.SIGN_ORDERS;
		}
  		ordOpLog.setOpType(opType);
  		ordOpLog.setInContent(inContent);
  		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
  		ordOpLog.setOutContent(onContent);
  		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
  		doSave(ordOpLog);
    }
	
	/**
	 * 保存日记信息
	 * @param ordChildOpLog
	 */
	private void doSave(OrdChildOpLog ordChildOpLog){
		Session session = SysContexts.getEntityManager();
		session.save(ordChildOpLog);
	};
}
