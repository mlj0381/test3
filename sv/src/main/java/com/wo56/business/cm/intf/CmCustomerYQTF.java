package com.wo56.business.cm.intf;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.wo56.business.cm.impl.CmCustomerYQSV;
import com.wo56.business.cm.vo.CmCustomer;
import com.wo56.common.consts.SysStaticDataEnumYunQi;

public class CmCustomerYQTF implements ICmCustomerIntf{
	/**
	 * 发货人或者收货人模糊匹配
	 * @param inParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> likeCmCustomerByName(Map<String,String> inParam){
		CmCustomerYQSV sv = (CmCustomerYQSV) SysContexts.getBean("cmCustomerYQSV");
		Criteria ca = sv.likeCmCustomerByName(inParam);
		if (ca == null) {
			return new HashMap<String, Object>();
		}
		IPage<CmCustomer> page = PageUtil.gridPage(ca);
		List<CmCustomer> list = page.getThisPageElements();
		List<Map<String,String>> listOut = new ArrayList<Map<String,String>>();
		if (list != null && list.size() > 0) {
			for (CmCustomer cmCustomer : list) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("id", String.valueOf(cmCustomer.getId()));
				map.put("name", cmCustomer.getName());
				map.put("bill", cmCustomer.getBill());
				map.put("address", cmCustomer.getAddress());
				listOut.add(map);
			}
		}
		Pagination p = new Pagination(page);
		page.setThisPageElements(listOut);
		return JsonHelper.parseJSON2Map(JsonHelper.json(listOut));
	}
	/**
	 * 开单保存修改发货人或者收货人（app）
	 * @param id
	 * @param type
	 * @param name
	 * @param bill
	 * @param address
	 * @return
	 */
	public void doSaveOrUpdateByOrder(long id,int type,String name,String bill,String address){
		CmCustomerYQSV cmCustomerYQSV = (CmCustomerYQSV) SysContexts.getBean("cmCustomerYQSV");
		CmCustomer cmCustomer = new CmCustomer();
		CmCustomer consignor = null;
		CmCustomer consignee = null;
		BaseUser user = SysContexts.getCurrentOperator();
		boolean flag = true;
		List<CmCustomer> list = cmCustomerYQSV.getCmCustomerList(name, bill);
		if(list != null && list.size() > 0){
			flag = false;
		}
		if(id > 0){
			cmCustomer = cmCustomerYQSV.getCmCustomer(id);
			if(cmCustomer != null){
				cmCustomer.setName(name);
				cmCustomer.setBill(bill);
				cmCustomer.setAddress(address);
				cmCustomer.setLinkmanName(name);
				flag = false;
			}else{
				flag = true;
			}
		}
		if(id < 0 && flag){
			CmCustomerData(cmCustomer, type, name, bill, address, user);
		}else{
			if(list.size() > 1){
				for (CmCustomer cmCustomer2 : list) {
					if(cmCustomer2.getType() == SysStaticDataEnumYunQi.CUSTOMER_TYPE.CONSIGNEE){
						consignee = cmCustomer2;
					}else if(cmCustomer2.getType() == SysStaticDataEnumYunQi.CUSTOMER_TYPE.CONSIGNOR){
						consignor = cmCustomer2;
					}
				}
				if(type == SysStaticDataEnumYunQi.CUSTOMER_TYPE.CONSIGNEE){
					consignee.setName(name);
					consignee.setBill(bill);
					consignee.setAddress(address);
					consignee.setLinkmanName(name);
					consignee.setParentId(consignor.getId());
					cmCustomer = consignee;
				}else if(type == SysStaticDataEnumYunQi.CUSTOMER_TYPE.CONSIGNOR){
					consignor.setName(name);
					consignor.setBill(bill);
					consignor.setAddress(address);
					consignor.setLinkmanName(name);
					consignor.setParentId(consignee.getId());
					cmCustomer = consignor;
				}
			}else if(list.size() == 1 && list.get(0).getType() == type){
				cmCustomer = list.get(0);
				cmCustomer.setName(name);
				cmCustomer.setBill(bill);
				cmCustomer.setAddress(address);
				cmCustomer.setLinkmanName(name);
			}else if(list.size() == 1 && list.get(0).getType() != type){
				CmCustomerData(cmCustomer, type, name, bill, address, user);
				if(type == SysStaticDataEnumYunQi.CUSTOMER_TYPE.CONSIGNEE){
					cmCustomer.setParentId(list.get(0).getId());
				}else if(type == SysStaticDataEnumYunQi.CUSTOMER_TYPE.CONSIGNOR){
					cmCustomer.setParentId(list.get(0).getId());
				}
			}
		}
		cmCustomerYQSV.doSaveOrUpdate(cmCustomer);
	}
	/**
	 * 数据set
	 * @param cmCustomer
	 * @param type
	 * @param name
	 * @param bill
	 * @param address
	 * @param user
	 */
	private void CmCustomerData(CmCustomer cmCustomer,int type,String name,String bill,String address,BaseUser user){
		cmCustomer.setType(type);
		cmCustomer.setName(name);
		cmCustomer.setBill(bill);
		cmCustomer.setTenantId(user.getTenantId());
		cmCustomer.setOrgId(user.getOrgId());
		cmCustomer.setState(SysStaticDataEnumYunQi.STS.VALID);
		cmCustomer.setCreateDate(new Date());
		cmCustomer.setAddress(address);
		cmCustomer.setLinkmanName(name);
		cmCustomer.setZxTenantId(user.getTenantId());
		cmCustomer.setParentId(0L);
	}
	/**
	 * 分页
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> pageCmCustomer(Map<String,Object> inParam){
		CmCustomerYQSV cmCustomerYQSV = (CmCustomerYQSV) SysContexts.getBean("cmCustomerYQSV");
		IPage<CmCustomer> p = PageUtil.gridPage(cmCustomerYQSV.pageCmCustomer(inParam));
		Pagination<CmCustomer> page = new Pagination<CmCustomer>(p);
		return JsonHelper.parseJSON2Map(JsonHelper.json(page));
	}
}
