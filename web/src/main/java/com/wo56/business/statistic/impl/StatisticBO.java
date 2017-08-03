package com.wo56.business.statistic.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.ExcelFilesUtil;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.SysStaticDataUtil;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ord.vo.in.DoQueryOrdCashIn;
import com.wo56.business.ord.vo.in.QueryDayTotalCashIn;
import com.wo56.business.ord.vo.in.QueryOrgTotalCashDtlIn;
import com.wo56.business.ord.vo.in.SendMessageIn;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.org.vo.in.OrgQueryWithPriParamIn;
import com.wo56.business.org.vo.out.OrganizationTreeOut;
//import com.wo56.business.statistic.vo.StatisticEmailSubscriber;
//import com.wo56.business.statistic.vo.StatisticItem;
//import com.wo56.business.statistic.vo.SysStatisticChartLatitude;
import com.wo56.business.statistic.vo.in.QueryChartIn;
import com.wo56.business.statistic.vo.in.SysStatisticChartLatitudeBatchQueryParamIn;
import com.wo56.business.statistic.vo.in.SysStatisticChartLatitudeDetailByIdParamIn;
import com.wo56.business.statistic.vo.in.SysStatisticChartLatitudeDetailParamIn;
import com.wo56.business.statistic.vo.in.SysStatisticChartLatitudeParamIn;
import com.wo56.business.statistic.vo.in.SysStatisticChartParamIn;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.LogBIConstant;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class StatisticBO extends BaseBO {
	public String queryChartInfo() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryChartIn in = new QueryChartIn();
		BeanUtils.populate(in, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(in, new TypeToken<SimpleOutParamVO<Map>>() {}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 查询当前用户有效的公司/网点
	 * @return
	 * @throws Exception
	 */
	public String getCurrentUserPriOrgInfo() throws Exception {
		OrgQueryWithPriParamIn paramIn = new OrgQueryWithPriParamIn();
		SimpleOutParamVO<OrganizationTreeOut> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<OrganizationTreeOut>>(){}.getType());
		OrganizationTreeOut result = out.getContent();
		
		JSONObject ret = new JSONObject();
		ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_SUCCESS);
		ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, LogBIConstant.RetInfo.RESULT_MESSAGE_SUCCESS_DEFAULT_MESSAGE);
		
		JSONArray orgArr = new JSONArray();
		if (null != result) {
			if (result.getType() != null && result.getType() == 3) {
				JSONObject obj = new JSONObject();
				obj.put("orgId", "-1");
				obj.put("orgName", "全部");
				orgArr.add(obj);
			}
			JSONObject obj = new JSONObject();
			obj.put("orgId", result.getId());
			obj.put("orgName", result.getName());
			orgArr.add(obj);
			JSONArray childs = getChildOrgListForJsonArray(result);
			if (null != childs && childs.size() != 0) {
				orgArr.addAll(childs);
			}
			ret.put("orgs", orgArr);
		}
		return ret.toString();
	}
	
	private JSONArray getChildOrgListForJsonArray(OrganizationTreeOut out) {
		List<OrganizationTreeOut> childOrgList = out.getChildOrgList();
		JSONArray resutls = new JSONArray();
		if (null == childOrgList || childOrgList.size() == 0)
			return resutls;
		for (OrganizationTreeOut childOut : childOrgList) {
			JSONObject obj = new JSONObject();
			obj.put("orgId", childOut.getId());
			obj.put("orgName", childOut.getName());
			resutls.add(obj);
			
			// 获取子组织
			if (null != childOut.getChildOrgList() && childOut.getChildOrgList().size() > 0) {
				JSONArray childResults = getChildOrgListForJsonArray(childOut);
				if (null != childResults && childResults.size() != 0) {
					resutls.addAll(childResults);
				}
			}
		}
		return resutls;
	}
	
	// 加载统计类别
	public String getStatisticType() throws Exception {
		BaseUser baseUser = SysContexts.getCurrentOperator();
		JSONObject ret = new JSONObject();
		ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_SUCCESS);
		ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, LogBIConstant.RetInfo.RESULT_MESSAGE_SUCCESS_DEFAULT_MESSAGE);
		if (null == baseUser) {
			ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_SESSION_FAILURE);
			ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, LogBIConstant.RetInfo.RESULT_MESSAGE_SESSION_FAILURE_DEFAULT_MESSAGE);
			return ret.toString();
		}
		
		SysStatisticChartParamIn proveParamIn = new SysStatisticChartParamIn();
		ListOutParamVO<List> out = CallerProxy.call(proveParamIn, new TypeToken<ListOutParamVO<List>>(){}.getType());
		List list = (List)out.getContent();
		JSONArray array = new JSONArray();
		for (int i = 0; null != list && i < list.size(); i++) {
//			if (!isSystemManagerRole && entities == null) {// 非系统管理员,且没有权限，就跳出循环，没必要执行
//				break;
//			}
			List row = (List) list.get(i);
			double entityId = (Double) row.get(2);
//			boolean hasEntity = false;
//			if (!isSystemManagerRole && entityId != -1) {// 非系统管理员，且统计项对应的entity_id不为-1，则校验权限
//				for (int j = 0; j < entities.size(); j++) {
//					if (entities.get(j).getEntityId() == entityId) { // 说明有权限
//						hasEntity = true;
//						break;
//					}
//				}
//			} else {
//				hasEntity = true;
//			}
//			if (!isSystemManagerRole && !hasEntity)
//				continue;
			double chartId = (Double) row.get(0);
			String chartName = (String) row.get(1);
			JSONObject retRow = new JSONObject();
			retRow.put(LogBIConstant.ChartInfo.CHART_NAME, chartName);
			retRow.put(LogBIConstant.ChartInfo.CHART_ID, chartId);
			retRow.put(LogBIConstant.ChartInfo.CHART_IMG, row.get(3));
			retRow.put(LogBIConstant.ChartInfo.CHART_ACTIVE, StringUtils.EMPTY);
			retRow.put(LogBIConstant.ChartInfo.CHART_DESC, row.get(4));
			array.add(retRow);
		}
		ret.put(LogBIConstant.RetInfo.RESULT_DATA, array);
		return ret.toString();
	}
	
	public String loadStatisticChartLatitude() {
		JSONObject ret = new JSONObject();
		ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_SUCCESS);
		ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, LogBIConstant.RetInfo.RESULT_MESSAGE_SUCCESS_DEFAULT_MESSAGE);
		try {
			BaseUser currOper = SysContexts.getCurrentOperator();
			HttpSession sess = SysContexts.getHttpSession();
//			UserDataInfo userDataInfo = (UserDataInfo) sess.getAttribute("CURRENT_USER");
			if (null == currOper) {
				ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_SESSION_FAILURE);
				ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, LogBIConstant.RetInfo.RESULT_MESSAGE_SESSION_FAILURE_DEFAULT_MESSAGE);
				return ret.toString();
			}
			Session session = SysContexts.getEntityManager();
			Map<String, String[]> map = SysContexts.getRequestParameterMap();
			int chartId = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.CHART_ID);
			if (chartId <= 0) {
				ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_FAILURE);
				ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, "无法获取相应的运营统计信息，请联系系统管理员");
				return ret.toString();
			}
			SysStatisticChartLatitudeDetailParamIn paramIn = new SysStatisticChartLatitudeDetailParamIn();
			paramIn.setChartId(chartId);
			ListOutParamVO<List> out = CallerProxy.call(paramIn, new TypeToken<ListOutParamVO<List>>(){}.getType());
			List list = out.getContent();
			JSONArray array = new JSONArray();
			List<JSONObject> latitudeList = new ArrayList<JSONObject>();
			
			double defaultLatitudeId = 0;
			double defaultLatitudeType = 0;
			boolean isShowOrgSelect = false;// 是否显示组织选择
			boolean isShowTenandSelect = false;// 是否显示组织选择
			boolean isShowLatitudeTypeSelect = false;// 是否显示纬度选择
			boolean containsMultiMonthStatistic = false;// 多月按日统计
			boolean containsMultiMonthCompare = false;// 是否包含多月对比

			int monthNum = 6;
			for (int i = 0; null != list && i < list.size(); i++) {
				Object[] row = (Object[])((List)list.get(i)).toArray(new Object[1]);;
				double latitudeId = (Double) row[0];
				double latitudeChartId = (Double) row[1];
				double associateChartId = (Double) row[2];
				String latitudeName = (String) row[3];
				double latitudeType = (Double) row[4];
				if (latitudeType == LogBIConstant.ChartInfo.LATITUDE_TYPE_6) {// 组织
					isShowOrgSelect = true;
					continue;
				} else if (latitudeType == LogBIConstant.ChartInfo.LATITUDE_TYPE_8) {
					containsMultiMonthStatistic = true;
				} else if (latitudeType == LogBIConstant.ChartInfo.LATITUDE_TYPE_9) {
					containsMultiMonthCompare = true;
				}
				if (associateChartId == 0)
					continue;
				boolean isDefault = (Boolean) row[5];
				if(defaultLatitudeId == 0 && isDefault){
					defaultLatitudeId = latitudeId;
					defaultLatitudeType = latitudeType;
				}
				JSONObject retRow = new JSONObject();
				retRow.put(LogBIConstant.ChartInfo.LATITUDE_LATITUDE_ID, latitudeId);
				retRow.put(LogBIConstant.ChartInfo.LATITUDE_CHART_ID, latitudeChartId);
				retRow.put(LogBIConstant.ChartInfo.LATITUDE_ASSOCIATE_CHART_ID, associateChartId);
				retRow.put(LogBIConstant.ChartInfo.LATITUDE_LATITUDE_NAME, latitudeName);
				retRow.put(LogBIConstant.ChartInfo.LATITUDE_LATITUDE_TYPE, latitudeType);
				retRow.put(LogBIConstant.ChartInfo.LATITUDE_IS_DEFAULT, isDefault);
				latitudeList.add(retRow);
			}
			
			
			if (null != list && list.size() > 0) {
				ret.put(LogBIConstant.ChartInfo.RET_IS_SHOW_LATITUDE, true);
			} else {
				ret.put(LogBIConstant.ChartInfo.RET_IS_SHOW_LATITUDE, false);
			}
			
			if (latitudeList.size() > 0) { // 设置默认的选项
				isShowLatitudeTypeSelect = true;
				if (defaultLatitudeId == 0) {
					defaultLatitudeId = latitudeList.get(0).getInt(LogBIConstant.ChartInfo.LATITUDE_LATITUDE_ID);
				}
				if(defaultLatitudeType == 0){
					defaultLatitudeType = latitudeList.get(0).getInt(LogBIConstant.ChartInfo.LATITUDE_LATITUDE_TYPE);
				}
			}
			
			if (containsMultiMonthStatistic) {
				String[] months = new String[monthNum];
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());;
				for (int i = 0; i < monthNum; i++) {
					if (i != 0) {
						calendar.add(Calendar.MONTH, -1);
					}
					months[i] = DateUtil.formatDate(calendar.getTime(), "yyyy年MM月");
				}
				ret.put(LogBIConstant.ChartInfo.RET_MULTI_MONTH_STATISTIC, months);
			}
			
			if (containsMultiMonthCompare) {
				String[] months = new String[12];
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());;
				for (int i = 0; i < 12; i++) {
					if (i != 0) {
						calendar.add(Calendar.MONTH, -1);
					}
					months[i] = DateUtil.formatDate(calendar.getTime(), "yyyy年MM月");
				}
				ret.put(LogBIConstant.ChartInfo.RET_MULTI_MONTH_COMPARE, months);
			}
			List<Integer> roles =(List<Integer>)currOper.getAttrs().get(EnumConsts.Common.SESSION_ROLES);
			
			Set<Integer> entityIdSet=new HashSet<Integer>();
			if(roles!=null && roles.size()>0){
				if(roles.get(0).intValue()==SysStaticDataEnum.ROLE_TYPE.PLATFORM){
					isShowTenandSelect = true;
				}
			}
			ret.put(LogBIConstant.ChartInfo.RET_DEFAULT_LATITUDE_ID, defaultLatitudeId);
			ret.put(LogBIConstant.ChartInfo.RET_DEFAULT_LATITUDE_TYPE, defaultLatitudeType);
			ret.put(LogBIConstant.ChartInfo.RET_IS_SHOW_ORG_SELECT, isShowOrgSelect);
			ret.put(LogBIConstant.ChartInfo.RET_IS_SHOW_TENANT_SELECT, isShowTenandSelect);
			ret.put(LogBIConstant.ChartInfo.RET_IS_SHOW_LATITUDE_TYPE_SELECT, isShowLatitudeTypeSelect);
			
			array.addAll(latitudeList);
			ret.put(LogBIConstant.ChartInfo.RET_CHART_ASSOCIATE_LATITUDE_INFO, array);
		} catch (Exception e) {
			e.printStackTrace();
			ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_FAILURE);
			ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, e.getMessage());
		}
		return ret.toString();
	}
	
//	public String showStatisticInfo() throws Exception {
//		JSONObject ret = new JSONObject();
//		ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_SUCCESS);
//		ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, LogBIConstant.RetInfo.RESULT_MESSAGE_SUCCESS_DEFAULT_MESSAGE);
//		try {
//			BaseUser currOper = SysContexts.getCurrentOperator();
//			if (null == currOper) {
//				ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_SESSION_FAILURE);
//				ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, LogBIConstant.RetInfo.RESULT_MESSAGE_SESSION_FAILURE_DEFAULT_MESSAGE);
//				return ret.toString();
//			}
//			Map<String, String[]> map = SysContexts.getRequestParameterMap();
//			int chartId = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.CHART_ID);
//			int latitudeId = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.LATITUDE_LATITUDE_ID);
//			String multiMonthStatistic = DataFormat.getStringKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_MULTI_MONTH_STATISTIC);
//			String multiMonthCompare = DataFormat.getStringKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_MULTI_MONTH_COMPARE);
//			//分批次查询
//			int isBatchQueryFlag = DataFormat.getIntKey(map, "isBatchQueryFlag");
//			
//			// 选择的组织
//			// int selectedOrg = currOper.getOrgId().intValue();
//			int selectedOrg = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_SELECTED_ORG);
//			int selectedTenant = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_SELECTED_TENANT);
//
//			Date queryDate = null;
//			Date customQueryStartTime = null;
//			Date customQueryEndTime = null;
//			int latitudeType = 0;
//			
//			int lastMonthsNumber = 0;// 统计近几个月的数据
//			/************************说明此请求来自“运营分析纬度”**********************/
//			String quertDateStr = DataFormat.getStringKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_QUERY_MONTH);
//			if (latitudeId > 0) {
//				SysStatisticChartLatitudeDetailByIdParamIn ssbi = new SysStatisticChartLatitudeDetailByIdParamIn();
//				ssbi.setLatitudeId(latitudeId);
//				ListOutParamVO<SysStatisticChartLatitude> out = CallerProxy.call(ssbi, new TypeToken<ListOutParamVO<SysStatisticChartLatitude>>(){}.getType());
//				List<SysStatisticChartLatitude> latitude = (List)out.getContent();
//				if (null == latitude || latitude.size() == 0) {
//					ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_FAILURE);
//					ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, "未配置运营分析纬度，请联系系统管理员");
//					return ret.toString();
//				}
//				chartId = latitude.get(0).getAssociateChartId();
//				queryDate = parseQueryDateByLatitudeType(quertDateStr, latitude.get(0).getLatitudeType());
//				latitudeType = latitude.get(0).getLatitudeType();
//				if (latitude.get(0).getLatitudeType() == LogBIConstant.ChartInfo.LATITUDE_TYPE_5) {// 近半年
//					lastMonthsNumber = 6;
//				} else if (latitude.get(0).getLatitudeType() == LogBIConstant.ChartInfo.LATITUDE_TYPE_7) {// 按范围(天)查询
//					customQueryStartTime = DateUtil.parseDate(DataFormat.getStringKey(map, "customQueryStartTime"), "yyyy年MM月dd日");
//					customQueryEndTime = DateUtil.parseDate(DataFormat.getStringKey(map, "customQueryEndTime"), "yyyy年MM月dd日");
//				}
//			}
//			/************************ 说明此请求来自“运营分析纬度” **********************/
//			if (chartId <= 0) {
//				ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_FAILURE);
//				ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, "无法获取相应的运营统计信息，请联系系统管理员");
//				return ret.toString();
//			}
//			
//			Date currentDate = new Date();
//			if (null == queryDate)
//				queryDate = currentDate;
//			
//			// 构建sql查询需要的某些参数
//			Map sqlQueryParams = setValueToSqlQueryParams(currOper, queryDate, currentDate, customQueryStartTime, customQueryEndTime, lastMonthsNumber);
//			
//			// 构建图表处理类需要的相关参数
//			Map handlerParams = new HashMap();
//			handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_LAST_MONTHS_NUMBER, lastMonthsNumber);
//			handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_QUERY_DATE, queryDate);
//			handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_CURRENT_DATE, currentDate);
//			handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_LATITUDE_TYPE, latitudeType);
//			handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_MULTI_MONTH_STATISTIC, multiMonthStatistic);
//			handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_MULTI_MONTH_COMPARE, multiMonthCompare);
//			if (null != customQueryStartTime && null != customQueryEndTime) {
//				handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_CUSTOM_QUERY_START_TIME, customQueryStartTime);
//				handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_CUSTOM_QUERY_END_TIME, customQueryEndTime);
//			}
//			
//			// 设置“宏变量”需要的相关参数
//			Map<String, String> macroVariablesParams = setValueToMacroVariablesParams(queryDate, currentDate, customQueryStartTime, customQueryEndTime, lastMonthsNumber);
//			
//			// TODO 特殊处理
//			String consignorName = DataFormat.getStringKey(map, "consignorName");
//			if (StringUtils.isNotBlank(consignorName)) {
//				macroVariablesParams.put("o.CONSIGNOR_NAME_LIKE"," AND o.CONSIGNOR_NAME like :consignorName");
//				sqlQueryParams.put("consignorName", "%"+consignorName.trim()+"%");
//			} else {
//				macroVariablesParams.put("o.CONSIGNOR_NAME_LIKE", " ");
//			}
//			
//			String carrierCompanyName = DataFormat.getStringKey(map, "carrierCompanyName");
//			if (StringUtils.isNotBlank(carrierCompanyName)) {
//				macroVariablesParams.put("CARRIER_COMPANY_NAME_LIKE"," AND T.CARRIER_COMPANY_NAME like :carrierCompanyName");
//				sqlQueryParams.put("carrierCompanyName", carrierCompanyName.trim());
//			} else {
//				macroVariablesParams.put("CARRIER_COMPANY_NAME_LIKE", " ");
//			}
//			
//			long descOrgId = DataFormat.getLongKey(map, "descOrgId");
//			if (descOrgId>0) {
//				macroVariablesParams.put("DESC_ORG_ID_EQUAL"," AND o.DESC_ORG_ID = :descOrgId");
//				sqlQueryParams.put("descOrgId", descOrgId);
//			} else {
//				macroVariablesParams.put("DESC_ORG_ID_EQUAL", " ");
//			}
//			
//			// 分页处理
//			int pageSize = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_PAGE_SIZE);
//			//-1标识不分页
//			if(pageSize==-1){
//				pageSize = 999999;
//			}
//			int currentPage = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_CURRENT_PAGE);
//			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_PAGE_LIMIT, " limit " + (currentPage - 1) * pageSize + "," + pageSize + " ");
//			// 分页处理
//			
//			dealOrgRelationInfo(currOper, currOper, selectedOrg, sqlQueryParams, handlerParams, macroVariablesParams,selectedTenant);
//			//替换动态查询条件中的宏变量，此变量使用${动态变量名}标识
//			SysStaticData isDynamicCond = SysStaticDataUtil.getSysStaticDataOfId("CHART_TABLE_QUERY_CONDITION", chartId);
//			List<SysStaticData> dynamicConds = SysStaticDataUtil.getSysStaticDataList("CHART_TABLE_QUERY_CONDITION");
//			String conditions = "";
//			if(isDynamicCond!=null){
//				for(int i=0;i<dynamicConds.size();i++){
//					SysStaticData s = dynamicConds.get(i);
//					String queryValue = DataFormat.getStringKey(map, s.getCodeDesc());
//					if(!"".equals(queryValue)){
//						String addSql = s.getCodeTypeAlias();
//						if(addSql.indexOf("${"+s.getCodeDesc()+"}")>-1){
//							String addTempSql = " "+addSql.replace("${"+s.getCodeDesc()+"}", "'" +DataFormat.getStringKey(map, s.getCodeDesc())+ "'") ;
//							conditions = conditions + addTempSql;
//						}
//						else{
//							conditions = conditions + (" "+s.getCodeTypeAlias()+"'" + DataFormat.getStringKey(map, s.getCodeDesc()) + "' ");
//						}
//					}
//				}
//				macroVariablesParams.put("DYNAMIC_CONDITIN", conditions);
//			}
//			Map extMap = new HashMap();
//			extMap.put("isBatchQueryFlag", isBatchQueryFlag);
//			SysStatisticChartLatitudeParamIn paramIn = new SysStatisticChartLatitudeParamIn();
//			paramIn.setChartId(chartId);
//			paramIn.setSqlQueryParams(sqlQueryParams);
//			paramIn.setMacroVariablesParams(macroVariablesParams);
//			paramIn.setHandlerParams(handlerParams);
//			paramIn.setExtMap(extMap);
//			SimpleOutParamVO<JSONObject> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<JSONObject>>(){}.getType());
//			JSONObject chartInfo = out.getContent();
//			ret.put(LogBIConstant.RetInfo.RESULT_DATA, chartInfo);
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_FAILURE);
//			ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, e.getMessage());
//		}
//		return ret.toString();
//	}
	
//	public String showStatisticInfoBatchQuery() throws Exception {
//		JSONObject ret = new JSONObject();
//		ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_SUCCESS);
//		ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, LogBIConstant.RetInfo.RESULT_MESSAGE_SUCCESS_DEFAULT_MESSAGE);
//		try {
//			BaseUser currOper = SysContexts.getCurrentOperator();
//			if (null == currOper) {
//				ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_SESSION_FAILURE);
//				ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, LogBIConstant.RetInfo.RESULT_MESSAGE_SESSION_FAILURE_DEFAULT_MESSAGE);
//				return ret.toString();
//			}
//			Map<String, String[]> map = SysContexts.getRequestParameterMap();
//			int chartId = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.CHART_ID);
//			int latitudeId = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.LATITUDE_LATITUDE_ID);
//			String multiMonthStatistic = DataFormat.getStringKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_MULTI_MONTH_STATISTIC);
//			String multiMonthCompare = DataFormat.getStringKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_MULTI_MONTH_COMPARE);
//			//分批次查询
//			int isBatchQueryFlag = DataFormat.getIntKey(map, "isBatchQueryFlag");
//			
//			// 选择的组织
//			// int selectedOrg = currOper.getOrgId().intValue();
//			int selectedOrg = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_SELECTED_ORG);
//			int selectedTenant = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_SELECTED_TENANT);
//			
//			Date queryDate = null;
//			Date customQueryStartTime = null;
//			Date customQueryEndTime = null;
//			int latitudeType = 0;
//			
//			int lastMonthsNumber = 0;// 统计近几个月的数据
//			/************************说明此请求来自“运营分析纬度”**********************/
//			String quertDateStr = DataFormat.getStringKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_QUERY_MONTH);
//			if (latitudeId > 0) {
//				SysStatisticChartLatitudeDetailByIdParamIn ssbi = new SysStatisticChartLatitudeDetailByIdParamIn();
//				ssbi.setLatitudeId(latitudeId);
//				ListOutParamVO<SysStatisticChartLatitude> out = CallerProxy.call(ssbi, new TypeToken<ListOutParamVO<SysStatisticChartLatitude>>(){}.getType());
//				List<SysStatisticChartLatitude> latitude = (List)out.getContent();
//				if (null == latitude || latitude.size() == 0) {
//					ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_FAILURE);
//					ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, "未配置运营分析纬度，请联系系统管理员");
//					return ret.toString();
//				}
//				chartId = latitude.get(0).getAssociateChartId();
//				queryDate = parseQueryDateByLatitudeType(quertDateStr, latitude.get(0).getLatitudeType());
//				latitudeType = latitude.get(0).getLatitudeType();
//				if (latitude.get(0).getLatitudeType() == LogBIConstant.ChartInfo.LATITUDE_TYPE_5) {// 近半年
//					lastMonthsNumber = 6;
//				} else if (latitude.get(0).getLatitudeType() == LogBIConstant.ChartInfo.LATITUDE_TYPE_7) {// 按范围(天)查询
//					customQueryStartTime = DateUtil.parseDate(DataFormat.getStringKey(map, "customQueryStartTime"), "yyyy年MM月dd日");
//					customQueryEndTime = DateUtil.parseDate(DataFormat.getStringKey(map, "customQueryEndTime"), "yyyy年MM月dd日");
//				}
//			}
//			/************************ 说明此请求来自“运营分析纬度” **********************/
//			if (chartId <= 0) {
//				ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_FAILURE);
//				ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, "无法获取相应的运营统计信息，请联系系统管理员");
//				return ret.toString();
//			}
//			
//			Date currentDate = new Date();
//			if (null == queryDate)
//				queryDate = currentDate;
//			
//			// 构建sql查询需要的某些参数
//			Map sqlQueryParams = setValueToSqlQueryParams(currOper, queryDate, currentDate, customQueryStartTime, customQueryEndTime, lastMonthsNumber);
//			
//			// 构建图表处理类需要的相关参数
//			Map handlerParams = new HashMap();
//			handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_LAST_MONTHS_NUMBER, lastMonthsNumber);
//			handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_QUERY_DATE, queryDate);
//			handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_CURRENT_DATE, currentDate);
//			handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_LATITUDE_TYPE, latitudeType);
//			handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_MULTI_MONTH_STATISTIC, multiMonthStatistic);
//			handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_MULTI_MONTH_COMPARE, multiMonthCompare);
//			if (null != customQueryStartTime && null != customQueryEndTime) {
//				handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_CUSTOM_QUERY_START_TIME, customQueryStartTime);
//				handlerParams.put(LogBIConstant.ChartInfo.HANDLER_PARAM_CUSTOM_QUERY_END_TIME, customQueryEndTime);
//			}
//			
//			// 设置“宏变量”需要的相关参数
//			Map<String, String> macroVariablesParams = setValueToMacroVariablesParams(queryDate, currentDate, customQueryStartTime, customQueryEndTime, lastMonthsNumber);
//			
//			// TODO 特殊处理
//			String consignorName = DataFormat.getStringKey(map, "consignorName");
//			if (StringUtils.isNotBlank(consignorName)) {
//				macroVariablesParams.put("o.CONSIGNOR_NAME_LIKE"," AND o.CONSIGNOR_NAME like :consignorName");
//				sqlQueryParams.put("consignorName", "%"+consignorName.trim()+"%");
//			} else {
//				macroVariablesParams.put("o.CONSIGNOR_NAME_LIKE", " ");
//			}
//			
//			String carrierCompanyName = DataFormat.getStringKey(map, "carrierCompanyName");
//			if (StringUtils.isNotBlank(carrierCompanyName)) {
//				macroVariablesParams.put("CARRIER_COMPANY_NAME_LIKE"," AND T.CARRIER_COMPANY_NAME like :carrierCompanyName");
//				sqlQueryParams.put("carrierCompanyName", carrierCompanyName.trim());
//			} else {
//				macroVariablesParams.put("CARRIER_COMPANY_NAME_LIKE", " ");
//			}
//			
//			long descOrgId = DataFormat.getLongKey(map, "descOrgId");
//			if (descOrgId>0) {
//				macroVariablesParams.put("DESC_ORG_ID_EQUAL"," AND o.DESC_ORG_ID = :descOrgId");
//				sqlQueryParams.put("descOrgId", descOrgId);
//			} else {
//				macroVariablesParams.put("DESC_ORG_ID_EQUAL", " ");
//			}
//			
//			// 分页处理
//			int pageSize = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_PAGE_SIZE);
//			//-1标识不分页
//			if(pageSize==-1){
//				pageSize = 999999;
//			}
//			int currentBatchPage = DataFormat.getIntKey(map, "currentBatchPage");
//			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_PAGE_LIMIT, " limit " + (currentBatchPage - 1) * 50 + "," + 50 + " ");
//			// 分页处理
//			
//			dealOrgRelationInfo(currOper, currOper, selectedOrg, sqlQueryParams, handlerParams, macroVariablesParams,selectedTenant);
//			//替换动态查询条件中的宏变量，此变量使用${动态变量名}标识
//			SysStaticData isDynamicCond = SysStaticDataUtil.getSysStaticDataOfId("CHART_TABLE_QUERY_CONDITION", chartId);
//			List<SysStaticData> dynamicConds = SysStaticDataUtil.getSysStaticDataList("CHART_TABLE_QUERY_CONDITION");
//			String conditions = "";
//			if(isDynamicCond!=null){
//				for(int i=0;i<dynamicConds.size();i++){
//					SysStaticData s = dynamicConds.get(i);
//					String queryValue = DataFormat.getStringKey(map, s.getCodeDesc());
//					if(!"".equals(queryValue)){
//						conditions = conditions + (" "+s.getCodeTypeAlias()+"'" + DataFormat.getStringKey(map, s.getCodeDesc()) + "' ");
//					}
//				}
//				macroVariablesParams.put("DYNAMIC_CONDITIN", conditions);
//			}
//			Map extMap = new HashMap();
//			extMap.put("isBatchQueryFlag", isBatchQueryFlag);
//			SysStatisticChartLatitudeBatchQueryParamIn paramIn = new SysStatisticChartLatitudeBatchQueryParamIn();
//			paramIn.setChartId(chartId);
//			paramIn.setSqlQueryParams(sqlQueryParams);
//			paramIn.setMacroVariablesParams(macroVariablesParams);
//			paramIn.setHandlerParams(handlerParams);
//			paramIn.setExtMap(extMap);
//			SimpleOutParamVO<JSONObject> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<JSONObject>>(){}.getType());
//			JSONObject chartInfo = out.getContent();
//			ret.put(LogBIConstant.RetInfo.RESULT_DATA, chartInfo);
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_FAILURE);
//			ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, e.getMessage());
//		}
//		return ret.toString();
//	}
	
	private void dealOrgRelationInfo(BaseUser currentOper, BaseUser userDataInfo, int selectedOrg, 
			Map sqlQueryParams, Map handlerParams, Map<String, String> macroVariablesParams,int selectedTenant) throws Exception {
		long orgId = selectedOrg <= 0 ? -1 : selectedOrg;
		long parentOrgId = selectedOrg;
		
		//  判断是否有权限
		OrgQueryWithPriParamIn paramIn = new OrgQueryWithPriParamIn();
		SimpleOutParamVO<OrganizationTreeOut> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<OrganizationTreeOut>>(){}.getType());
		OrganizationTreeOut result = out.getContent();
		
		if (orgId == -1) {// 判断是否是公司
			if (result == null || result.getType() == null || result.getType() != 3) {// 如果没有租户的权限，就默认为当前组织
				orgId = currentOper.getOrgId();
			}
		} else if (orgId > 0 && orgId != currentOper.getOrgId()) {// 如果选择的不是自己所在的组织，则判断selectedOrg是否为当前操作员下面的归属组织
			if (result.getId().longValue() != currentOper.getOrgId().longValue()) {// 野人返回的接口有问题
				orgId = currentOper.getOrgId();
			} else {// 判断选择的组织是不是当前操作员所在组织的子孙
				JSONArray childs = getChildOrgListForJsonArray(out.getContent());
				if (null == childs || childs.size() == 0) {
					orgId = currentOper.getOrgId();
				} else {
					boolean isFind = false;
					for (int i = 0; i < childs.size(); i++) {
						JSONObject child = (JSONObject)childs.get(i);
						String childOrgId = String.valueOf(child.get("orgId"));
						if (String.valueOf(orgId).equals(childOrgId)) {
							isFind = true;
							break;
						}
					}
					if (!isFind)
						orgId = currentOper.getOrgId();	
				}
			}
		}
		if (orgId == -1) {// 没有选组织或选择了“所有组织”
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ORG, " ");
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ROOT_ORG, " ");
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ORG_O_POINT, " ");
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ROOT_ORG_O_POINT, " ");
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ORG_OBJ, " ");
			macroVariablesParams.put("I_DESC_ORG_ID", " ");
			macroVariablesParams.put("O_OUTGOING_DISTRIBUTION_ORG_ID", "");
			
			// 设置租户ID
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_TENANTID_O, " AND o.TENANT_ID =:tenantId ");
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_TENANTID_OBJECT, " AND org.TENANT_ID =:tenantId ");
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_TENANTID, " AND TENANT_ID =:tenantId ");
			macroVariablesParams.put("DESC_ORG_ID", " ");
			
		} else {
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ROOT_ORG, " AND ROOT_ORG_ID =:rootOrgId ");
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ROOT_ORG_O_POINT, " AND o.ROOT_ORG_ID =:rootOrgId ");
			
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ORG, " AND ORG_ID =:orgId ");
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ORG_O_POINT, " AND o.ORG_ID =:orgId ");
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ORG_OBJ, " AND org.obj_id =:orgId ");
			
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_TENANTID_O, " ");
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_TENANTID_OBJECT, " ");
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_TENANTID, " ");
			macroVariablesParams.put("DESC_ORG_ID", " AND DESC_ORG_ID = :orgId ");
			if(selectedTenant!=-1){
				macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_CONDITION_TENANTID, " AND o.TENANT_ID =:tenantId ");
			}
			macroVariablesParams.put("I_DESC_ORG_ID", "AND i.DESC_ORG_ID = :orgId ");
			macroVariablesParams.put("O_OUTGOING_DISTRIBUTION_ORG_ID", "AND ((o.org_id = :orgId AND o.DISTRIBUTION_ORG_ID = -1) OR (o.DISTRIBUTION_ORG_ID = :orgId))");
		}
		if (orgId > 0) {
			Organization oragnize = getOragnizeByOrgId(orgId);
			if (null == oragnize) {
				throw new Exception("网点信息不存在，请联系系统管理员");
			}
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_ORG_NAME, oragnize.getOrgName());
			parentOrgId = oragnize.getParentOrgId();
			handlerParams.put("IS_SELECTED_ORG", true);
		}
		
		if (orgId == -1) {// 查询的所有组织，且有权限
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_ORG_NAME, "所有网点");
		} 
		
		// 设置sql参数
		sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_ROOT_ORG_ID, parentOrgId);
		sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_ORG_ID, orgId);
		if(selectedTenant!=-1){
			sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_TENANT_ID, selectedTenant);
		}
		else
		{
			sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_TENANT_ID, currentOper.getTenantId());
		}
		sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_CURRENT_USER_ORG_ID, currentOper.getOrgId());
	}

	private static Organization getOragnizeByOrgId(long orgId) {
		return OraganizationCacheDataUtil.getOrganization(orgId);
	}
	
	private Date parseQueryDateByLatitudeType(String quertDateStr, short latitudeType) throws Exception {
		Date queryDate = null;
		if (StringUtils.isNotBlank(quertDateStr)) {
			try {
				switch (latitudeType) {
					case LogBIConstant.ChartInfo.LATITUDE_TYPE_1:
						queryDate = DateUtil.parseDate(quertDateStr, "yyyy年");
						if (null == queryDate) {
							throw new Exception("请选择需要统计的年份");
						}
						break;
					case LogBIConstant.ChartInfo.LATITUDE_TYPE_2:
						queryDate = DateUtil.parseDate(quertDateStr, "yyyy年");
						if (null == queryDate) {
							throw new Exception("请选择需要统计的年份");
						}
						break;
					case LogBIConstant.ChartInfo.LATITUDE_TYPE_3:
						queryDate = DateUtil.parseDate(quertDateStr, "yyyy年MM月");
						if (null == queryDate) {
							throw new Exception("请选择需要统计的月份");
						}
						break;
					case LogBIConstant.ChartInfo.LATITUDE_TYPE_4:
						queryDate = DateUtil.parseDate(quertDateStr, "yyyy年MM月dd日");
						if (null == queryDate) {
							throw new Exception("请选择需要统计的日期");
						}
						break;
					default:
						break;
				}
			} catch (Exception e) {
				throw new Exception("时间格式不正确，请重新选择");
			}
		}
		return queryDate;
	}
	
	private Map<String, String> setValueToSqlQueryParams(BaseUser userDataInfo, Date queryDate, Date currentDate, Date customQueryStartTime, Date customQueryEndTime, int lastMonthsNumber) throws Exception {
		Map sqlQueryParams = new HashMap();
		sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_ROOT_ORG_ID, userDataInfo.getOrgId());
		sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_ORG_ID, userDataInfo.getOrgId());

		// 查询年份的开始时间和结束时间
		sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_QUERY_YEAR_START_TIME, CommonUtil.getSpecialYearStartTime(queryDate));
		sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_QUERY_YEAR_END_TIME, CommonUtil.getSpecialYearEndTime(queryDate));

		// 查询月的开始时间和结束时间
		sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_QUERY_MONTH_START_TIME, CommonUtil.getSpecialMonthStartTime(queryDate));
		sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_QUERY_MONTH_END_TIME, CommonUtil.getSpecialMonthEndTime(queryDate));
		
		// 查询天的开始时间和结束时间
		sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_QUERY_DAY_START_TIME, CommonUtil.getSpecialDayStartTime(queryDate));
		sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_QUERY_DAY_END_TIME, CommonUtil.getSpecialDayEndTime(queryDate));

		// 自定义查询范围的开始时间/结束时间
		if (null != customQueryStartTime && null != customQueryEndTime) {
			sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_CUSTOM_QUERY_SCOPE_START_TIME, CommonUtil.getSpecialDayStartTime(customQueryStartTime));
			sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_CUSTOM_QUERY_SCOPE_END_TIME, CommonUtil.getSpecialDayEndTime(customQueryEndTime));
		}
		
		// 查询近几个月的开始时间和结束时间
		if (lastMonthsNumber > 0) {
			sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_QUERY_LAST_MONTHS_START_TIME, CommonUtil.getSpecialMonthStartTime(CommonUtil.getLastNumberMonth(queryDate, lastMonthsNumber - 1)));
			sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_QUERY_LAST_MONTHS_END_TIME, queryDate);
		}
		
		sqlQueryParams.put(LogBIConstant.ChartInfo.SQL_QUERY_PARAM_QUERY_MONTH, DateUtil.formatDate(queryDate, DateUtil.YEAR_MONTH_FORMAT2));
		return sqlQueryParams;
	}
	
	private Map<String, String> setValueToMacroVariablesParams(Date queryDate, Date currentDate, Date customQueryStartTime, Date customQueryEndTime, int lastMonthsNumber) throws ParseException, Exception {
		Map<String, String> macroVariablesParams = new HashMap<String, String>();
		macroVariablesParams.put(LogBIConstant.ChartInfo.YYYY_CH, DateUtil.formatDate(queryDate, "yyyy年"));
		macroVariablesParams.put(LogBIConstant.ChartInfo.YYYY_EN, DateUtil.formatDate(queryDate, DateUtil.YEAR_FORMAT));
		
		macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_MONTH_CH, DateUtil.formatDate(queryDate, "yyyy年MM月"));
		macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_MONTH_EN, DateUtil.formatDate(queryDate, DateUtil.YEAR_MONTH_FORMAT));
		
		macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_MONTH_DAY_CH, DateUtil.formatDate(queryDate, "yyyy年MM月dd日"));
		macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_MONTH_DAY_EN, DateUtil.formatDate(queryDate, DateUtil.DATE_FORMAT));
		
		macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_CURRENT_MONTH_YYYYMMM, DateUtil.formatDate(currentDate, DateUtil.YEAR_MONTH_FORMAT2));
		macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_MONTH_YYYYMMM, DateUtil.formatDate(queryDate, DateUtil.YEAR_MONTH_FORMAT2));

		if(lastMonthsNumber > 0){
			Date lastMonthsStartTime = CommonUtil.getSpecialMonthStartTime(CommonUtil.getLastNumberMonth(queryDate, lastMonthsNumber -1));
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_LAST_MONTHS_START_TIME_CH, DateUtil.formatDate(lastMonthsStartTime, "yyyy年MM月"));
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_LAST_MONTHS_START_TIME_EN, DateUtil.formatDate(lastMonthsStartTime, DateUtil.YEAR_MONTH_FORMAT));
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_LAST_MONTHS_END_TIME_CH, DateUtil.formatDate(queryDate, "yyyy年MM月"));
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_QUERY_LAST_MONTHS_END_TIME_EN, DateUtil.formatDate(queryDate, DateUtil.YEAR_MONTH_FORMAT));
		}
		
		if (null != customQueryStartTime && null != customQueryEndTime) {
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_CUSTOM_QUERY_START_TIME_CH, DateUtil.formatDate(customQueryStartTime, "yyyy年MM月dd日"));
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_CUSTOM_QUERY_START_TIME_EN, DateUtil.formatDate(customQueryStartTime, DateUtil.DATE_FORMAT));
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_CUSTOM_QUERY_END_TIME_CH, DateUtil.formatDate(customQueryEndTime, "yyyy年MM月dd日"));
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_CUSTOM_QUERY_END_TIME_EN, DateUtil.formatDate(customQueryEndTime, DateUtil.DATE_FORMAT));
		}
		return macroVariablesParams;
	}
	
	public String showSubStatisticInfo() throws Exception {
		JSONObject ret = new JSONObject();
		ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_SUCCESS);
		ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, LogBIConstant.RetInfo.RESULT_MESSAGE_SUCCESS_DEFAULT_MESSAGE);
		try {
			BaseUser currOper = SysContexts.getCurrentOperator();
			if (null == currOper) {
				ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_SESSION_FAILURE);
				ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, LogBIConstant.RetInfo.RESULT_MESSAGE_SESSION_FAILURE_DEFAULT_MESSAGE);
				return ret.toString();
			}
			Map<String, String[]> map = SysContexts.getRequestParameterMap();
			int chartId = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.CHART_ID);
			
			Map sqlQueryParams = new HashMap();
			Map<String, String> macroVariablesParams = new HashMap<String, String>();
			Map handlerParams = new HashMap();
			Iterator<String> keyIte = map.keySet().iterator();
			Map<String,String> pointUserOptions = new HashMap<String, String>();
			pointUserOptions.put(LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_XINDEX, DataFormat.getStringKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_XINDEX));
			
			while (keyIte.hasNext()) {
				String key = keyIte.next();
				String[] value = map.get(key);
				if (null == value || value.length == 0)
					continue;
				boolean isSpecialParam = false;
				if (key.indexOf("pointUserOptions.macroVariablesParams.") >= 0 || key.indexOf("macroVariablesParams[") >= 0) {
					String macroVariablesParamsKey = key.substring(key.lastIndexOf(".")+1);
					macroVariablesParams.put(macroVariablesParamsKey, value[0]);
					isSpecialParam = true;
				}
				else if (key.indexOf("macroVariablesParams.") >= 0) {
					String macroVariablesParamsKey = key.substring(key.lastIndexOf(".") + 1);
	    			if("DYNAMIC_CONDITIN".equals(macroVariablesParamsKey)){
	    				value[0] = value[0].replace("&apos;", "'");
	    			}
					macroVariablesParams.put(macroVariablesParamsKey, value[0]);
					isSpecialParam = true;
				} else if (key.indexOf("sqlQueryParams.tableRowCol.") >= 0){
					String sqlQueryParamsKey = key.substring(key.lastIndexOf(".") + 1);
					sqlQueryParams.put("tableRowCol." + sqlQueryParamsKey, value[0]);
					isSpecialParam = true;
				} else if (key.indexOf("pointUserOptions.sqlQueryParams.") >= 0 || key.indexOf("sqlQueryParams.") >= 0) {
					String sqlQueryParamsKey = key.substring(key.lastIndexOf(".") + 1);
					sqlQueryParams.put(sqlQueryParamsKey, value[0]);
					isSpecialParam = true;
				}
				else if (key.indexOf("sqlQueryParams.") >= 0) {
					String sqlQueryParamsKey = key.substring(key.lastIndexOf("."));
					sqlQueryParams.put(sqlQueryParamsKey, value[0]);
					isSpecialParam = true;
				}
				else if (key.indexOf("pointUserOptions[handlerParams.") >= 0 || key.indexOf("handlerParams.") >= 0) {
					String handlerParamsKey = key.substring(key.indexOf(".") + 1);
					handlerParams.put(handlerParamsKey, value[0]);
					isSpecialParam = true;
				}
				else if (key.indexOf("handlerParams.") >= 0) {
					String handlerParamsKey = key.substring(key.indexOf(".") + 1, key.lastIndexOf("."));
					handlerParams.put(handlerParamsKey, value[0]);
					isSpecialParam = true;
				}
				else if (key.indexOf("tableRowCol.") >= 0){
					String handlerParamsKey = key.substring(key.indexOf(".") + 1, key.lastIndexOf("."));
					handlerParams.put(handlerParamsKey, value[0]);
					isSpecialParam = true;
				}
				else if(!isSpecialParam && key.indexOf("pointUserOptions.") >= 0) {
					pointUserOptions.put(key.substring(key.indexOf(".") + 1) , value[0]);
				}
			}
			
			if (sqlQueryParams.get("consignorName") != null) {
				macroVariablesParams.put("o.CONSIGNOR_NAME_LIKE"," AND o.CONSIGNOR_NAME like :consignorName");
				sqlQueryParams.put("consignorName", String.valueOf(sqlQueryParams.get("consignorName")).trim());
			} else {
				macroVariablesParams.put("o.CONSIGNOR_NAME_LIKE", " ");
			}
			
			if (sqlQueryParams.get("carrierCompanyName") != null) {
				macroVariablesParams.put("t.CARRIER_COMPANY_NAME_LIKE"," AND T.CARRIER_COMPANY_NAME like :carrierCompanyName");
				sqlQueryParams.put("carrierCompanyName", String.valueOf(sqlQueryParams.get("carrierCompanyName")).trim());
			} else {
				macroVariablesParams.put("t.CARRIER_COMPANY_NAME_LIKE", " ");
			}
			
			
			// 分页处理
			int pageSize = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_PAGE_SIZE);
			int currentPage = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_CURRENT_PAGE);
			String rowData = DataFormat.getStringKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_ROW_DATA);
			handlerParams.put(LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_ROW_DATA, rowData);
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_PAGE_LIMIT, " limit " + (currentPage - 1) * pageSize + "," + pageSize + " ");
			// 分页处理
			SysStatisticChartLatitudeParamIn paramIn = new SysStatisticChartLatitudeParamIn();
			paramIn.setChartId(chartId);
			paramIn.setSqlQueryParams(sqlQueryParams);
			paramIn.setMacroVariablesParams(macroVariablesParams);
			paramIn.setHandlerParams(handlerParams);
			paramIn.setPointUserOptions(pointUserOptions);
			SimpleOutParamVO<JSONObject> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<JSONObject>>(){}.getType());
			JSONObject chartInfo = out.getContent();
			ret.put(LogBIConstant.RetInfo.RESULT_DATA, chartInfo);
		} catch (Exception e) {
			e.printStackTrace();
			ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_FAILURE);
			ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, e.getMessage());
		}
		return ret.toString();
	}
	
	
	public String showSubStatisticInfoBatchQuery() throws Exception {
		JSONObject ret = new JSONObject();
		ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_SUCCESS);
		ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, LogBIConstant.RetInfo.RESULT_MESSAGE_SUCCESS_DEFAULT_MESSAGE);
		try {
			BaseUser currOper = SysContexts.getCurrentOperator();
			if (null == currOper) {
				ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_SESSION_FAILURE);
				ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, LogBIConstant.RetInfo.RESULT_MESSAGE_SESSION_FAILURE_DEFAULT_MESSAGE);
				return ret.toString();
			}
			Map<String, String[]> map = SysContexts.getRequestParameterMap();
			int chartId = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.CHART_ID);
			
			Map sqlQueryParams = new HashMap();
			Map<String, String> macroVariablesParams = new HashMap<String, String>();
			Map handlerParams = new HashMap();
			Iterator<String> keyIte = map.keySet().iterator();
			Map<String,String> pointUserOptions = new HashMap<String, String>();
			pointUserOptions.put(LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_XINDEX, DataFormat.getStringKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_XINDEX));
			
			while (keyIte.hasNext()) {
				String key = keyIte.next();
				String[] value = map.get(key);
				if (null == value || value.length == 0)
					continue;
				boolean isSpecialParam = false;
				if (key.indexOf("pointUserOptions.macroVariablesParams.") >= 0 || key.indexOf("macroVariablesParams[") >= 0) {
					String macroVariablesParamsKey = key.substring(key.lastIndexOf(".")+1);
					macroVariablesParams.put(macroVariablesParamsKey, value[0]);
					isSpecialParam = true;
				}
				else if (key.indexOf("macroVariablesParams.") >= 0) {
					String macroVariablesParamsKey = key.substring(key.lastIndexOf(".") + 1);
					macroVariablesParams.put(macroVariablesParamsKey, value[0]);
					isSpecialParam = true;
				} else if (key.indexOf("sqlQueryParams.tableRowCol.") >= 0){
					String sqlQueryParamsKey = key.substring(key.lastIndexOf(".") + 1);
					sqlQueryParams.put("tableRowCol." + sqlQueryParamsKey, value[0]);
					isSpecialParam = true;
				} else if (key.indexOf("pointUserOptions.sqlQueryParams.") >= 0 || key.indexOf("sqlQueryParams.") >= 0) {
					String sqlQueryParamsKey = key.substring(key.lastIndexOf(".") + 1);
					sqlQueryParams.put(sqlQueryParamsKey, value[0]);
					isSpecialParam = true;
				}
				else if (key.indexOf("sqlQueryParams.") >= 0) {
					String sqlQueryParamsKey = key.substring(key.lastIndexOf("."));
					sqlQueryParams.put(sqlQueryParamsKey, value[0]);
					isSpecialParam = true;
				}
				else if (key.indexOf("pointUserOptions[handlerParams.") >= 0 || key.indexOf("handlerParams.") >= 0) {
					String handlerParamsKey = key.substring(key.indexOf(".") + 1);
					handlerParams.put(handlerParamsKey, value[0]);
					isSpecialParam = true;
				}
				else if (key.indexOf("handlerParams.") >= 0) {
					String handlerParamsKey = key.substring(key.indexOf(".") + 1, key.lastIndexOf("."));
					handlerParams.put(handlerParamsKey, value[0]);
					isSpecialParam = true;
				}
				else if (key.indexOf("tableRowCol.") >= 0){
					String handlerParamsKey = key.substring(key.indexOf(".") + 1, key.lastIndexOf("."));
					handlerParams.put(handlerParamsKey, value[0]);
					isSpecialParam = true;
				}
				else if(!isSpecialParam && key.indexOf("pointUserOptions.") >= 0) {
					pointUserOptions.put(key.substring(key.indexOf(".") + 1) , value[0]);
				}
			}
			
			if (sqlQueryParams.get("consignorName") != null) {
				macroVariablesParams.put("o.CONSIGNOR_NAME_LIKE"," AND o.CONSIGNOR_NAME like :consignorName");
				sqlQueryParams.put("consignorName", String.valueOf(sqlQueryParams.get("consignorName")).trim());
			} else {
				macroVariablesParams.put("o.CONSIGNOR_NAME_LIKE", " ");
			}
			
			if (sqlQueryParams.get("carrierCompanyName") != null) {
				macroVariablesParams.put("t.CARRIER_COMPANY_NAME_LIKE"," AND T.CARRIER_COMPANY_NAME like :carrierCompanyName");
				sqlQueryParams.put("carrierCompanyName", String.valueOf(sqlQueryParams.get("carrierCompanyName")).trim());
			} else {
				macroVariablesParams.put("t.CARRIER_COMPANY_NAME_LIKE", " ");
			}
			
			
			// 分页处理
			int pageSize = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_PAGE_SIZE);
			int currentPage = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_CURRENT_PAGE);
			String rowData = DataFormat.getStringKey(map, LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_ROW_DATA);
			handlerParams.put(LogBIConstant.ChartInfo.HTTP_REQUEST_PARAM_ROW_DATA, rowData);
			int currentBatchPage = DataFormat.getIntKey(map, "currentBatchPage");
			macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_PAGE_LIMIT, " limit " + ((currentPage - 1) * pageSize + (currentBatchPage-1) * 50) + "," + 50 + " ");
			// 分页处理
			SysStatisticChartLatitudeParamIn paramIn = new SysStatisticChartLatitudeParamIn();
			paramIn.setChartId(chartId);
			paramIn.setSqlQueryParams(sqlQueryParams);
			paramIn.setMacroVariablesParams(macroVariablesParams);
			paramIn.setHandlerParams(handlerParams);
			paramIn.setPointUserOptions(pointUserOptions);
			SimpleOutParamVO<JSONObject> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<JSONObject>>(){}.getType());
			JSONObject chartInfo = out.getContent();
			ret.put(LogBIConstant.RetInfo.RESULT_DATA, chartInfo);
		} catch (Exception e) {
			e.printStackTrace();
			ret.put(LogBIConstant.RetInfo.RESULT_CODE, LogBIConstant.RetInfo.RESULT_CODE_FAILURE);
			ret.put(LogBIConstant.RetInfo.RESULT_MESSAGE, e.getMessage());
		}
		return ret.toString();
	}
	
	public void exportTableData() throws Exception {// 导出数据
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		int chartId = DataFormat.getIntKey(map, LogBIConstant.ChartInfo.CHART_ID);
		Iterator<String> keyIte = map.keySet().iterator();
		
		Map sqlQueryParams = new HashMap();
		Map<String, String> macroVariablesParams = new HashMap<String, String>();
		Map handlerParams = new HashMap();
		Map<String,String> pointUserOptions = new HashMap<String, String>();
		
		while (keyIte.hasNext()) {
			String key = keyIte.next();
			String[] value = map.get(key);
			if (null == value || value.length == 0)
				continue;
			if (key.indexOf("macroVariablesParams[") >= 0) {
				String macroVariablesParamsKey = key.substring(key.lastIndexOf("[") + 1, key.lastIndexOf("]"));
    			if("DYNAMIC_CONDITIN".equals(macroVariablesParamsKey)){
    				value[0] = value[0].replace("&apos;", "'");
    			}
				macroVariablesParams.put(macroVariablesParamsKey, java.net.URLDecoder.decode(value[0], "utf-8"));
				
				
			}
			if (key.indexOf("sqlQueryParams[") >= 0) {
				String sqlQueryParamsKey = key.substring(key.lastIndexOf("[") + 1, key.lastIndexOf("]"));
				sqlQueryParams.put(sqlQueryParamsKey, java.net.URLDecoder.decode(value[0], "utf-8"));
			}
			if (key.indexOf("handlerParams[") >= 0) {
				String handlerParamsKey = key.substring(key.lastIndexOf("[") + 1, key.lastIndexOf("]"));
				handlerParams.put(handlerParamsKey, java.net.URLDecoder.decode(value[0], "utf-8"));
			}
			if (key.indexOf("pointUserOptions[") >= 0) {
				pointUserOptions.put(key.substring(key.indexOf("[") + 1, key.lastIndexOf("]")), java.net.URLDecoder.decode(value[0], "utf-8"));
			}
		}
		
		macroVariablesParams.put(LogBIConstant.ChartInfo.MACRO_VARIABLES_PARAMS_PAGE_LIMIT, StringUtils.EMPTY);
		if(macroVariablesParams.get("DYNAMIC_CONDITIN")==null){
			macroVariablesParams.put("DYNAMIC_CONDITIN", "");
		}
		SysStatisticChartLatitudeParamIn paramIn = new SysStatisticChartLatitudeParamIn();
		paramIn.setChartId(chartId);
		paramIn.setSqlQueryParams(sqlQueryParams);
		paramIn.setMacroVariablesParams(macroVariablesParams);
		paramIn.setHandlerParams(handlerParams);
		SimpleOutParamVO<JSONObject> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<JSONObject>>(){}.getType());
		JSONObject result = out.getContent();
		JSONObject chartInfo = (JSONObject) result.get(LogBIConstant.ChartInfo.CHART_INFO);
		JSONArray head = (JSONArray) chartInfo.get(LogBIConstant.ChartInfo.CATEGORIES);
		JSONObject tableInfo = (JSONObject) result.get(LogBIConstant.ChartInfo.TABLE_INFO);
		JSONArray tableDatas = (JSONArray) tableInfo.get(LogBIConstant.ChartInfo.RET_TABLE_RESULT_DATA);
		
		List<List<?>> content = new ArrayList<List<?>>();
		List<String> headRow = new ArrayList<String>();
		for (int i = 0; i < head.size(); i++) {
			headRow.add(head.getString(i));
		}
		content.add(headRow);
		
		List<SysStaticData> staticDatas = SysStaticDataUtil.getSysStaticDataList("TABLE_EXPORT_COL_CONFIG");
		Map countMap = new HashMap();
		for (int i = 0; null != staticDatas && i < staticDatas.size(); i++) {
			SysStaticData data = staticDatas.get(i);
			if (data.getCodeValue().equals(String.valueOf(chartId))) {
				countMap.put("index_" + data.getCodeId(), true);
				countMap.put("totalMethod_" + data.getCodeId(), data.getCodeName());
				countMap.put("formart_" + data.getCodeId(), data.getCodeTypeAlias());
				countMap.put("result_" + data.getCodeId(), null);
			}
		}
		
		int colNum = 0;
		for (int i = 0; i < tableDatas.size(); i++) {
			JSONArray rowArray = (JSONArray) tableDatas.getJSONObject(i).get(LogBIConstant.ChartInfo.RET_TABLE_ROW);
			colNum = rowArray.size();// 列数
			List<Object> row = new ArrayList<Object>();
			for (int j = 0; j < rowArray.size(); j++) {
				if(null == rowArray.getJSONObject(j).get(LogBIConstant.ChartInfo.RET_TABLE_COL_NAME)){
					row.add(StringUtils.EMPTY);
				} else {
					Object col = rowArray.getJSONObject(j).get(LogBIConstant.ChartInfo.RET_TABLE_COL_NAME);
					if (col instanceof Double && ((Double) col).doubleValue() == ((Double) col).longValue()) {
						col = new Long(((Double) col).longValue());
					}
					row.add(col);
					
					// 统计数据
					if (countMap.get("index_" + j) != null && (Boolean) countMap.get("index_" + j)) {// 说明此列需要合计
						String totalMethod = (String) countMap.get("totalMethod_" + j);
						if ("sum".equals(totalMethod)) {// 行内的值合计
							double colTotal = 0.0d;
							if (col instanceof Double) {
								if (null != countMap.get("result_" + j)) {
									colTotal = (((Double) countMap.get("result_" + j)).doubleValue() + ((Double) col).doubleValue());
								} else {
									colTotal = ((Double) col).doubleValue();
								}
							} else if (col instanceof Long) {
								if (null != countMap.get("result_" + j)) {
									colTotal = (((Double) countMap.get("result_" + j)).doubleValue() + ((Long) col).longValue());
								}  else {
									colTotal = ((Long) col).longValue();
								}
							} else if (col instanceof Integer) {
								if (null != countMap.get("result_" + j)) {
									colTotal = (((Double) countMap.get("result_" + j)).doubleValue() + ((Integer) col).intValue());
								} else {
									colTotal = ((Integer) col).intValue();
								}
							}
							countMap.put("result_" + j, new Double(colTotal));
						} else if ("count".equals(totalMethod)) {// 行数合计
							int colTotal = 1;
							if (null != countMap.get("result_" + j)) {
								colTotal = (((Integer) countMap.get("result_" + j)).intValue() + 1);
							}
							countMap.put("result_" + j, new Integer(colTotal));
						}
					}
				}
			}
			content.add(row);
		}
		if (countMap.size() > 0) {
			DecimalFormat df = new DecimalFormat("#.00");
			List row = new ArrayList();
			for (int i = 0; i < colNum; i++) {
				if (countMap.get("index_" + i) != null && (Boolean) countMap.get("index_" + i)) {// 说明此列需要合计
					Object colValue = countMap.get("result_" + i);
					if (null != colValue) {
						Object colStrValue = colValue; 
						if (colValue instanceof Double && ((Double) colValue).doubleValue() == ((Double) colValue).longValue()) {
							colStrValue = String.valueOf(new Long(((Double) colValue).longValue()));
						}
						if (colStrValue instanceof Double) {
							colStrValue = Double.parseDouble(df.format(colStrValue));
						}
						String formart = (String)countMap.get("formart_" + i);
						if (StringUtils.isNotBlank(formart)) {// 需要格式化
							Map<String, String> formartMap = new HashMap<String, String>();
							formartMap.put("value", String.valueOf(colStrValue));
							row.add(CommonUtil.replaceMacroVariables(formart, formartMap, String.valueOf(colStrValue)));
						} else {
							row.add(colValue);
						}
					} else {
						row.add(StringUtils.EMPTY);
					}
				} else {
					row.add(StringUtils.EMPTY);
				}
			}
			content.add(row);
		}
		ExcelFilesUtil.exportExcel(SysContexts.getRequest(), SysContexts.getResponse(), DateUtil.formatDate(CommonUtil.getCurrentDate(), DateUtil.DATETIME12_FORMAT2), content, true);
	}
	
	/**
	 * 查询统计项(邮件发送部分)
	 * @param ids
	 * @param session
	 * @param evict
	 * @return
	 */
//	public StatisticItem queryStatisticItem(int id, Session session, boolean evict) {
//		if (id <= 0)
//			return null;
//		Criteria ca = session.createCriteria(StatisticItem.class);
//		ca.add(Restrictions.eq("id", id));
//		ca.add(Restrictions.eq("state", LogBIConstant.StatisticConstant.STATE_1));
//		StatisticItem item = (StatisticItem) ca.uniqueResult();
//		if (null != item && evict) {
//			session.evict(item);
//		}
//		return item;
//	}
	
	/**
	 * 查询统计项(邮件发送部分)
	 * @param ids
	 * @param session
	 * @param evict
	 * @return
	 */
//	public List<StatisticItem> queryStatisticItem(Integer[] ids, int timeDimensionType, Session session, boolean evict) {
//		if (null == ids || ids.length == 0)
//			return null;
//		Criteria ca = session.createCriteria(StatisticItem.class);
//		ca.add(Restrictions.in("id", ids));
//		ca.add(Restrictions.eq("timeDimensionType", timeDimensionType));
//		ca.add(Restrictions.eq("state", LogBIConstant.StatisticConstant.STATE_1));
//		List<StatisticItem> list = ca.list();
//		if (null != list && evict) {
//			session.evict(list);
//		}
//		return list;
//	}
	
	/**
	 * 查询订阅者数据(邮件发送部分)
	 * @param ids
	 * @param session
	 * @param evict
	 * @return
	 */
//	public List<StatisticEmailSubscriber> queryStatisticEmailSubscriber(Set<Integer> subscriberIds, Session session, boolean evict) {
//		if (null == subscriberIds || subscriberIds.size() == 0)
//			return null;
//		Criteria ca = session.createCriteria(StatisticEmailSubscriber.class);
//		ca.add(Restrictions.in("id", subscriberIds));
//		ca.add(Restrictions.eq("state", LogBIConstant.StatisticConstant.STATE_1));
//		List<StatisticEmailSubscriber> list = ca.list();
//		if (null != list && evict) {
//			session.evict(list);
//		}
//		return list;
//	}
	
	/**
	 * 查询有效的订阅关系
	 * @param ids
	 * @param session
	 */
	public void queryEffectSubscribeRelToMap(Integer[] ids, Session session, Map<Integer, Set<Integer>> itemMap, Map<Integer, Set<Integer>> subscriberMap) {
		String sql = "SELECT A.ITEM_ID, A.SUBSCRIBER_ID FROM STATISTIC_EMAIL_SUBSCRIBE_REL A, STATISTIC_EMAIL_SUBSCRIBER B "
			+ " WHERE A.ITEM_ID IN (:itemIds) AND A.STATE = 1 AND A.SUBSCRIBER_ID = B.ID AND B.STATE = 1 ORDER BY A.SEQ ";
		Query query = session.createSQLQuery(sql);
		query.setParameterList("itemIds", ids);
		List resultRows = query.list();
		for (int i = 0; null != resultRows && i < resultRows.size(); i++) {
			Object[] row = (Object[]) resultRows.get(i);
			Integer itemId = (Integer) row[0];
			Integer subscriberId = (Integer) row[1];
			// 统计项对应的订阅人
			Set<Integer> itemSet = itemMap.get(itemId);
			if (null == itemSet)
				itemSet = new HashSet<Integer>();
			itemSet.add(subscriberId);
			itemMap.put(itemId, itemSet);

			// 订阅人订阅的统计项
			Set<Integer> subscriberIdSet = subscriberMap.get(subscriberId);
			if (null == subscriberIdSet)
				subscriberIdSet = new HashSet<Integer>();
			subscriberIdSet.add(itemId);
			subscriberMap.put(subscriberId, subscriberIdSet);
		}
	}
	
	public void querySubscriberOrgMapping(Set<Integer> ids, Map<Integer, Integer> subscriberOrgMap, Session session) {
		String sql = "SELECT A.ID, B.ORG_ID FROM (SELECT ID, USER_ID FROM STATISTIC_EMAIL_SUBSCRIBER WHERE IS_CAN_GET_ALL_ORG_DATA != 1 AND ID IN (:ids) AND STATE = 1) A, USER_DATA_INFO B WHERE A.USER_ID = B.USER_ID "; 
		Query query = session.createSQLQuery(sql);
		query.setParameterList("ids", ids);
		List resultRows = query.list();
		for (int i = 0; null != resultRows && i < resultRows.size(); i++) {
			Object[] row = (Object[]) resultRows.get(i);
			Integer id = (Integer) row[0];
			Integer orgId = (Integer) row[1];
			if(orgId == null)
				continue;
			subscriberOrgMap.put(id, orgId);
		}
	}
	
	
	
	/**
	 * 
	 * 接口编码：120070
	 * 现金收支日报表
	 * */
	public String doQueryOrgCash() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		DoQueryOrdCashIn query = new DoQueryOrdCashIn();
		String checkDate = DataFormat.getStringKey(map, "checkDate");
		String endDate = DataFormat.getStringKey(map, "endDate");
		Date queryDate=null;
		Date queryEndDate=null;
		BeanUtils.populate(query, map);
		if(StringUtils.isNotEmpty(checkDate)  ){
			if(query.getSelectDate()!=null && query.getSelectDate()==2){
				queryDate = DateUtil.parseDate(checkDate, "yyyy年MM月dd日");
			}else{
				queryDate = DateUtil.parseDate(checkDate, "yyyy年MM月");
			}
			query.setCheckDate(queryDate);
		}
		if(StringUtils.isNotEmpty(endDate)){
			queryEndDate = DateUtil.parseDate(endDate, "yyyy年MM月dd日");
			query.setEndDate(queryEndDate);
		}
		SimpleOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	/**
	 * 
	 * 接口编码：120070
	 * 现金收支日报表
	 * */
	public String queryDayTotalCash() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryDayTotalCashIn query = new QueryDayTotalCashIn();
		String checkDate = DataFormat.getStringKey(map, "checkDate");
		String endDate = DataFormat.getStringKey(map, "endDate");
		Date queryDate=null;
		Date queryEndDate=null;
		BeanUtils.populate(query, map);
		if(StringUtils.isNotEmpty(checkDate)  ){
			if(query.getSelectDate()!=null && query.getSelectDate()==2){
				queryDate = DateUtil.parseDate(checkDate, "yyyy年MM月dd日");
			}else{
				queryDate = DateUtil.parseDate(checkDate, "yyyy年MM月");
			}
			query.setCheckDate(queryDate);
		}
		if(StringUtils.isNotEmpty(endDate)){
			queryEndDate = DateUtil.parseDate(endDate, "yyyy年MM月dd日");
			query.setEndDate(queryEndDate);
		}
		PageOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	/**
	 * 
	 * 接口编码：170008
	 * 应收应付汇总报表详情展示
	 * */
	public String queryOrgTotalFeeDtl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryOrgTotalCashDtlIn query = new QueryOrgTotalCashDtlIn();
		String checkDate = DataFormat.getStringKey(map, "checkDate");
		String endDate = DataFormat.getStringKey(map, "endDate");
		Date queryDate=null;
		Date queryEndDate=null;
		BeanUtils.populate(query, map);
		if(StringUtils.isNotEmpty(checkDate)  ){
			if(query.getSelectDate()!=null && query.getSelectDate()==2){
				queryDate = DateUtil.parseDate(checkDate, "yyyy年MM月dd日");
			}else{
				queryDate = DateUtil.parseDate(checkDate, "yyyy年MM月");
			}
			query.setCheckDate(queryDate);
		}
		if(StringUtils.isNotEmpty(endDate)){
			queryEndDate = DateUtil.parseDate(endDate, "yyyy年MM月dd日");
			query.setEndDate(queryEndDate);
		}
		PageOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	
	/**
	 * 
	 * 接口编码：170009
	 * 应收应付汇总报表详情展示
	 * */
	public String getSendMessage() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SendMessageIn query = new SendMessageIn();
		String checkDate = DataFormat.getStringKey(map, "checkDate");
		Date queryDate=null;
		BeanUtils.populate(query, map);
		if(StringUtils.isNotEmpty(checkDate)  ){
			queryDate = DateUtil.parseDate(checkDate, "yyyy年MM月");
			query.setCheckDate(queryDate);
		}
		PageOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	
	/**
	 * 查询租户信息
	 * @return
	 * @throws Exception
	 */
	public String getTenantInfo() throws Exception {
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList("STATISTIC_QUERY_TENANT");
		if(sysStaticData==null){
			return "";
		}
		return JsonHelper.json(sysStaticData);
	}
	
}