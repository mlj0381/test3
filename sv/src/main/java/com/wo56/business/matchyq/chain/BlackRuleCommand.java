package com.wo56.business.matchyq.chain;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.components.match.chain.MatchCommand;
import com.framework.components.match.common.MatchDataUtils;
import com.framework.components.match.vo.BaseMatchObject;
import com.framework.core.cache.CacheFactory;
import com.wo56.business.cm.vo.CmPullBlack;
import com.wo56.business.matchyq.impl.BusiMatchConstsYq;
import com.wo56.common.consts.SysStaticDataEnum;
/**
 * 拉包工拉黑的逻辑
 * 
 * 如果拉包工 被平台拉黑，不需要进行处理
 * 如果拉包工 被拉包公司拉黑，不需要进行处理
 * 如果拉包工 被订单的专线 拉黑，不需要进行处理
 * 如果拉包工 被订单的创建人 拉黑，不需要进行处理
 * 
 * @author liyiye
 *
 */
public class BlackRuleCommand extends MatchCommand {
	private static final Log log = LogFactory.getLog(BlackRuleCommand.class);
	private static final String FILE_TENANTID = BusiMatchConstsYq.Fields.tenantId;
	private static final String FILE_CREATEID = BusiMatchConstsYq.Fields.createId;
	
	@Override
	public boolean doMatchRule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		//订单的创建人
		String createId=MatchDataUtils.getAObjAttr(zObj, FILE_CREATEID);
		//订单指定的专线
		String tenantId=MatchDataUtils.getAObjAttr(zObj, FILE_TENANTID);
		//拉包工的id
		long pullUserId=zObj.getId();
		CmPullBlack cmPullBlack=(CmPullBlack) CacheFactory.get(CmPullBlack.class.getName(), SysStaticDataEnum.PULL_BLACK_TYPE_YQ.PULL_BLACK_TYPE_PLATFORM+"_"+pullUserId);
		if(cmPullBlack!=null){
			//拉包工被平台拉黑，不需要进行处理
			return false;
		}
		
		cmPullBlack=(CmPullBlack) CacheFactory.get(CmPullBlack.class.getName(), SysStaticDataEnum.PULL_BLACK_TYPE_YQ.PULL_BLACK_TYPE_PULLPAGCOMPANY+"_"+pullUserId);
		if(cmPullBlack!=null){
			//拉包工被拉包公司拉黑，不需要进行处理
			return false;
		}
		
		cmPullBlack=(CmPullBlack) CacheFactory.get(CmPullBlack.class.getName(), SysStaticDataEnum.PULL_BLACK_TYPE_YQ.PULL_BLACK_TYPE_SPECIAL+"_"+tenantId+"_"+pullUserId);
		if(cmPullBlack!=null){
			//拉包工被订单的专线公司拉黑，不需要进行处理
			return false;
		}
		
		cmPullBlack=(CmPullBlack) CacheFactory.get(CmPullBlack.class.getName(), SysStaticDataEnum.PULL_BLACK_TYPE_YQ.PULL_BLACK_TYPE_CUSTOMER+"_"+createId+"_"+pullUserId);
		if(cmPullBlack!=null){
			//拉包工被订单的创建人（客户）拉黑，不需要处理
			return false;
		}
		
		
		return true;
	}

	@Override
	public long transVaule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		return 1;
	}
}
