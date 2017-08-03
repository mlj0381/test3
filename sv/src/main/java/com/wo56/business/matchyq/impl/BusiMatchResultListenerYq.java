package com.wo56.business.matchyq.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.components.match.intf.MatchResultListener;
import com.framework.components.match.vo.BaseMatchObject;
import com.framework.core.SysContexts;
import com.wo56.business.ord.intf.OrdOrdersInfoTF;
//import com.wo56.business.route.impl.RouteCostSV;

public class BusiMatchResultListenerYq implements MatchResultListener {
	
	private static final Log log = LogFactory.getLog(BusiMatchResultListenerYq.class);
	private static OrdOrdersInfoTF infoTF =(OrdOrdersInfoTF)SysContexts.getBean("ordersInfoTF");
			
	@Override
	public void onMatchSuccess(BaseMatchObject aObj, List<BaseMatchObject> zList) throws Exception {
		
		if(aObj!=null && zList!=null && zList.size()==1){
			BaseMatchObject worKerId=zList.get(0);
			if(worKerId!=null){
				if(log.isDebugEnabled()){
					log.debug("匹配成功订单编号:["+aObj.getId()+"],拉包工:["+worKerId.getId()+"]");
				}
				infoTF.matchSuceeful(aObj.getId(), worKerId.getId());
			}
		}
	}

	@Override
	public void onMatchFail(BaseMatchObject aObj, String message) throws Exception {
		if(log.isDebugEnabled()){
			log.debug("匹配失败订单编号:["+aObj.getId()+"]");
		}
	}
}
