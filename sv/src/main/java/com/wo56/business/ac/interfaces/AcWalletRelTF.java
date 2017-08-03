package com.wo56.business.ac.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.wo56.business.ac.impl.AcWalletRelSV;
import com.wo56.business.ac.vo.AcWalletRel;
import com.wo56.business.ac.vo.out.AcWalletRelOut;
import com.wo56.business.sys.vo.SysTenantDef;
import com.wo56.common.utils.BeanUtil;
import com.wo56.common.utils.CommonUtil;

public class AcWalletRelTF {
	 /**
	  * 我的钱包获取公司
	  * 接口编号：601011
	  * @return
	  */
	@SuppressWarnings("unchecked")
	public List<AcWalletRelOut> getAcWalletRel(){
		AcWalletRelSV walletSV = (AcWalletRelSV) SysContexts.getBean("acWalletRelSV");
		Session session = SysContexts.getEntityManager();
		Query query = walletSV.getAcWalletRel();
		List<Object[]> list = query.list();
		List<AcWalletRelOut> listOut = new ArrayList<AcWalletRelOut>();
		if(list != null && list.size() > 0){
			for (Object[] object : list) {
				AcWalletRel ac = new AcWalletRel();
				SysTenantDef sys = new SysTenantDef();
				AcWalletRelOut out = new AcWalletRelOut();
				ac = (AcWalletRel) object[0];
				sys = (SysTenantDef) object[1];
				session.evict(ac);
				session.evict(sys);
				out.setTenantId(ac.getTenantId() != null ? String.valueOf(ac.getTenantId()) : "");
				out.setTenantName(sys.getName());
				out.setUserId(String.valueOf(ac.getUserId()));
				out.setShowFee(ac.getShowFee() != null ? String.valueOf(CommonUtil.getDoubleFormatLongMoney(ac.getShowFee(), 2)): "0.00");
				listOut.add(out);
			}
		}
		return listOut;
	}
	
}
