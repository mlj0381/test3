package com.wo56.business.matchyq.chain;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.components.match.chain.MatchCommand;
import com.framework.components.match.common.MatchDataUtils;
import com.framework.components.match.vo.BaseMatchObject;
import com.framework.core.cache.CacheFactory;
import com.wo56.business.cm.vo.CmOrgRel;
import com.wo56.business.matchyq.impl.BusiMatchConstsYq;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
/**
 * 订单下单指定专线公司，拉包工是属于专线公司的，优先级最高
 * 
 * 订单下单指定专线公司，拉包工是属于拉包公司的，并且拉包公司跟专线是有合作关系，优先级中
 * 
 * 订单下单指定专线公司，拉包工不是上面两个情况的，优先级最低
 * 
 * 租户的匹配命令，
 * 当A 跟 Z的租户一样的时候，返回 2
 * 当A 跟 Z的租户 是有合作关系，返回1
 * 当A 跟 Z的租户 没有上面两个情况 ，返回0
 * 
 * 该命令的配置比例为90%
 * 规则的公式:1000*${V}
 * 
 * @author liyiye
 *
 */
public class TenantRuleCommand extends MatchCommand {
	private static final Log log = LogFactory.getLog(TenantRuleCommand.class);
	private static final String field = BusiMatchConstsYq.Fields.tenantId;
	
	
	@Override
	public boolean doMatchRule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		if(log.isDebugEnabled()){
			log.debug("租户匹配：A:["+aObj.getId()+
					","+MatchDataUtils.getZObjAttr(aObj, field)
					+"],B:["+zObj.getId()+","+MatchDataUtils.getZObjAttr(zObj, field)+"]");
		}
		return true;
	}

	@Override
	public long transVaule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		String zTenantId = MatchDataUtils.getZObjAttr(zObj, field);
		String aTenantId=MatchDataUtils.getZObjAttr(aObj, field);
		if (StringUtils.isNotEmpty(zTenantId) && StringUtils.isNotEmpty(aTenantId) && zTenantId.equals(aTenantId)) {
			return 2;
		} 
		CmOrgRel cmOrgRel=(CmOrgRel) CacheFactory.get(CmOrgRel.class.getName(), SysStaticDataEnumYunQi.CACHE.CM_ORG_REL+zTenantId+"_"+aTenantId);
		if(cmOrgRel!=null){
			return 1;
		}
		
		return 0;
	}
}
