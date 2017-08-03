package com.wo56.business.base.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.base.vo.OrdImportBillInfo;
import com.wo56.business.org.vo.OrdImportBill;
import com.wo56.common.handler.AbstractTennatDataConfigHandler;


/**
 * "网点信息"sheet页
 * 
 * @author chenjun
 * 
 */
public class OrdImportBillDataConfigHandler extends
		AbstractTennatDataConfigHandler<OrdImportBillInfo> {
	public static final String BUSINESS_NAME = "运单信息";
	public static final String SHEET_NAME = BUSINESS_NAME;

//	public OrdImportBillDataConfigHandler() {
//		super(new OrdImportBillParser());
//	}

	@Override
	/**
	 * 1.插入网点(organization表)
	 * 2.插入租户(sys_tenant_def表)
	 * 3.插入公司账户的3种账户类型(ac_account表),obj_type=1,ACCOUNT_TYPE in(1,2,3)
	 * @author qlfeng
	 */
	public Map<String,Object> doDealConfigData(List<OrdImportBillInfo> orgInfos) {
		// TODO
		// 将TennatOrgInfo数据保存到数据库，然后可以将网点数据(例如，Organization信息)设置到dataContainer字段，后续的Handler可以使用这个dataContainer
//		BaseUser user = SysContexts.getCurrentOperator();
//		Session session = SysContexts.getEntityManager();
//		OrdImportBillSV ordImportBillSV = (OrdImportBillSV) SysContexts.getBean("ordImportBillSV");
		Map<String,Object> errorMap = new HashMap<String, Object>();
//		int j = 0;
//		StringBuffer totalError = new StringBuffer();
//		for(int i=0;i< orgInfos.size();i++){
//			if(StringUtils.isNotEmpty(orgInfos.get(i).getFridges())){
//				Map<String,Object> map = ordImportBillSV.doSaveOrdBill(orgInfos.get(i), user);
//				OrdImportBill dao = (OrdImportBill) map.get("ordImportBill");
//				session.save(dao);
//				String value = "";
//				String str = "第"+(i+1)+"条 ，";
//				for(Map.Entry<String, Object> entry : map.entrySet()){
//					if(!"ordImportBill".equals(entry.getKey())){
//						value =  value + (String) entry.getValue()+"，";
//					}
//				}
//				if(value.lastIndexOf("，") > -1){
//					value = value.substring(0,value.lastIndexOf("，"));
//				}
//				j++;
//				if(StringUtils.isNotEmpty(value)){
//					totalError.append(str);
//					totalError.append(value);
//					totalError.append("；");
//				}
//			}
//			if(i >= 299){
//				break;
//			}
//		}
//		errorMap.put("errorNews", totalError.toString());
//		errorMap.put("success","已经成功录入"+j+"条");
		return errorMap;
		//setDataContainer(temp);
	}

	@Override
	public String sheetName() {
		return SHEET_NAME;
	}

	@Override
	public int sort() {
		return 0;
	}
	private Integer getDeliveryType(String str,String codeType){
		int i = -1;
			List<SysStaticData> date =  SysStaticDataUtil.getSysStaticDataList(-1L, codeType);
			if(date != null && date.size() > 0){
				for(SysStaticData temp : date){
					if(temp.getCodeName().equals(str)){
						i = Integer.parseInt(temp.getCodeValue());
				}
			}
		}
		return i;
	}
}
