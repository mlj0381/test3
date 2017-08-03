package com.wo56.business.ac.interfaces;

import java.util.Map;

import com.framework.core.inter.vo.Pagination;


public interface IAcCashProveIntf {
	public Pagination<?> querySfFeeDtl(Map<String,Object> inParam)throws Exception;
	public Pagination<?> queryAccountDtlNew(Map<String,Object> inParam)throws Exception;
	public Map<String,String> dealAcCashProveNew (Map<String,Object> inParam)throws Exception;
	public Pagination<?> queryOutGoingCheckInfo  (Map<String,Object> inParam)throws Exception;
	public Pagination<?> queryAcDtlBatchNew(Map<String,Object> inParam)throws Exception;
	public Pagination<?> queryAcDtlShipBatchNew(Map<String,Object> inParam)throws Exception;
	
}
