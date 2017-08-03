package com.wo56.business.sche.match;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.framework.components.match.vo.MatchChainDef;
import com.framework.components.match.vo.MatchSimilarRule;
import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.multitab.CommonCol;
import com.framework.core.multitab.CommonVO;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;

public class MatchWeightTF {

	/**
	 * 查询匹配项
	 * */
	public Map<String,Object> doQueryItem( Map<String,Object> inParam){
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery("SELECT ID,CHAIN_NAME,SIMILAR_GROUP,PERCENT,REMARK,color FROM match_chain_def WHERE STATE=1 order by sort asc");
		List list = query.list();
		CommonVO commVO = null;
		for (int i = 0; i < list.size(); i++) {
			Object[] objAry = (Object[]) list.get(i);   
	    	commVO = new CommonVO();
			commVO.addColunm(new CommonCol("id", objAry[0]));
	    	commVO.addColunm(new CommonCol("chainName", objAry[1]));
	    	commVO.addColunm(new CommonCol("similarGroup", objAry[2]));
	    	commVO.addColunm(new CommonCol("percent", objAry[3]));
	    	commVO.addColunm(new CommonCol("remark", objAry[4]));
	    	commVO.addColunm(new CommonCol("color", objAry[5]));
	    	list.set(i, commVO);
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(list));
	}
	

	/**
	 * 查询匹配信用值
	 * */
	public Map<String,Object> doQueryChain( Map<String,Object> inParam){
		String similarGroup = DataFormat.getStringKey(inParam,"similarGroup");
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery("SELECT ID ,MIN,MAX,SCORE,similar_Group FROM Match_Similar_Rule WHERE STATE=1 and similar_Group=:similarGroup");
		query.setParameter("similarGroup", similarGroup);
		List list = query.list();
		CommonVO commVO = null;
		for (int i = 0; i < list.size(); i++) {
			Object[] objAry = (Object[]) list.get(i);   
	    	commVO = new CommonVO();
			commVO.addColunm(new CommonCol("id", objAry[0]));
	    	commVO.addColunm(new CommonCol("min", objAry[1]));
	    	commVO.addColunm(new CommonCol("max", objAry[2]));
	    	commVO.addColunm(new CommonCol("score", objAry[3]));
	    	commVO.addColunm(new CommonCol("similarGroup", objAry[4]));
	    	list.set(i, commVO);
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(list));
		
	}
	
	/**
	 * 修改匹配项的权重比
	 * */
	public Map<String,Object> modifyPercent( Map<String,Object> inParam){
		String id  = DataFormat.getStringKey(inParam, "id");
		String percent  = DataFormat.getStringKey(inParam, "percent");
		String color  = DataFormat.getStringKey(inParam, "color");
		Session session = SysContexts.getEntityManager();
		String[] idArr=null;
		String[] percentArr=null;
		String[] colorArr=null;
		if(StringUtils.isNotEmpty(id)){
			idArr = id.split(",");
			percentArr=percent.split(",");
			colorArr=color.split(",");
		}
		if(idArr.length>0){
			for (int i = 0; i < idArr.length; i++) {
				MatchChainDef matchChainDef = (MatchChainDef) session.get(MatchChainDef.class, Long.parseLong(idArr[i]+""));
				if(matchChainDef!=null){
					matchChainDef.setPercent(Integer.parseInt(percentArr[i]+""));
					//matchChainDef.setColor(colorArr[i]);
					session.saveOrUpdate(matchChainDef);
				}
			}
		}
		
		Map rtn = new HashMap();
		rtn.put("isOK", "Y");
		return rtn;
	}
	
	/**
	 * 新增匹配项的信用比
	 * */
	public Map<String,Object> doSave( Map<String,Object> inParam){
		Integer min  = DataFormat.getIntKey(inParam, "min");
		Integer max  = DataFormat.getIntKey(inParam, "max");
		Integer score  = DataFormat.getIntKey(inParam, "score");
		long id  = DataFormat.getLongKey(inParam, "id");
		if(min<-1){
			throw new BusinessException("最小值不能为空");
		}
		if(max<-1){
			throw new BusinessException("最大值不能为空");
		}
		if(score<-1){
			throw new BusinessException("分数不能为空");
		}
		Session session = SysContexts.getEntityManager();
		String similarGroup  = DataFormat.getStringKey(inParam, "similarGroup");
		if(id>0){
			MatchSimilarRule matchSimilarRule = (MatchSimilarRule) session.get(MatchSimilarRule.class, id);
			if(matchSimilarRule==null){
				throw new BusinessException("找不到匹配信用项分");
			}
			matchSimilarRule.setMin(min);
			matchSimilarRule.setMax(max);
			matchSimilarRule.setScore(score);
			session.saveOrUpdate(matchSimilarRule);
		}else{
			MatchSimilarRule similarRule = new MatchSimilarRule();
			similarRule.setMin(min);
			similarRule.setMax(max);
			similarRule.setScore(score);
			similarRule.setState(true);//
			similarRule.setSimilarType("THRESHOLD");
			similarRule.setSimilarGroup(similarGroup);
			session.saveOrUpdate(similarRule);
		}
		Map rtn = new HashMap();
		rtn.put("isOK", "Y");
		return rtn;
	}
	
	
	
}
