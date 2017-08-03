package com.wo56.business.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.exception.BusinessException;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.ExcelFilesUtil;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.SysStaticDataUtil;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.common.vo.in.ExportDataIn;
import com.wo56.common.utils.CommonUtil;
/**
 * 导出Excel基础类
 *  
 * 
 * 
 */


public class CommonExportBO {
	/**
	 * 
	 * 
	 * @param sqlKey  静态数据使用的查询SQL(codeValue)    codeType ="EXCEL_EXPORT_KEY"    
	 * @param sqlParams {     //查询参数
	 *               key1: value1, key1代表 :key1   value代表 :key1 的值
	 *               key2: value2
	 *          } 
	 *          
	 *           key要以数据库配置的参数一致
	 *            
	 * @param  excelName Excel文件名       
	 *  
	 * 
	 * 
	 */
  
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void commonExport() throws Exception {	
		Map<String, String[]> paramMap = SysContexts.getRequestParameterMap();
		
		String sqlKey =  DataFormat.getStringKey(paramMap, "sqlKey"); 
		String sqlParams =  DataFormat.getStringKey(paramMap, "sqlParams");
		SysStaticData staticData = SysStaticDataUtil.getSysStaticData("EXCEL_EXPORT_KEY", sqlKey);
		if(staticData == null || staticData.getFlowId() <= 0){
			throw new BusinessException("查找导出模板失败，请确认模版是否已配置！");
		}
		ExportDataIn exportDataIn = new ExportDataIn();
		exportDataIn.setSqlParams(sqlParams);
		exportDataIn.setSqlKey(sqlKey);

		Map mapTF = new HashMap();
	    try {
	    	SimpleOutParamVO<Map> out = CallerProxy.call(exportDataIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
			mapTF = out.getContent();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("系统异常，导出Excel文档失败！");
		}
		
		
		//组装数据
		List<List<?>> content = new LinkedList<List<?>>(); //导出的Excel数据结构
		
		List<String> lableName = new LinkedList<String>(); //列名
		
		String lableNameStr = staticData.getCodeName();
		
		if(StringUtils.isNotEmpty(lableNameStr)){
			String [] strs = lableNameStr.split(",");
			for(String s : strs){
				lableName.add(s);
			}
			content.add(lableName);
		}else{
			throw new BusinessException("导出失败，请确认Excel列名是否已配置！");
		}
		
		List<String> contentValue = new ArrayList<String>();//内容
		
		List lists = (List) mapTF.get("items");
		for( int i = 0 ;i< lists.size();i++){
			contentValue = new ArrayList<String>();//内容
			Map<String,Object> map = (Map<String,Object>) lists.get(i);
			for (int j = 0;j < map.size();j++) {
				  //{"COL_0":175,"COL_2":"","COL_1":"2016-07-02 11:13:19","COL_4":1,"COL_3":1,"COL_6":68.35,"COL_5":500,"COL_8":2,"COL_11":"13800138030","COL_7":5,"COL_10":"司机1","COL_9":"粤Y50002"}
				  //为了实现查询顺序（）
				  Object value = map.get("COL_"+j);
				  if(value != null){
					  contentValue.add(String.valueOf(value));
				  }else{
					  contentValue.add("");
				  }
			}
			content.add(contentValue);
		}
		ExcelFilesUtil.exportExcel(SysContexts.getRequest(), SysContexts.getResponse(), DateUtil.formatDate(CommonUtil.getCurrentDate(), DateUtil.DATETIME12_FORMAT2), content);
	}
	
	/**
	 *
	 * 实体对象查询导出
	 * 
	 * @param TFBeanName      com.wo56.business.common.vo.in.ExportDataIn
	 * @param params          查询数据参数（json字符串） 例如：{"org":"1"}
	 * @param excelLables     Excel 列名   格式以逗号分开
	 * @param excelKeys       Excel 数据   格式以英文逗号分开 要和列名相对应 -- 要以实体对象的字段相对应 格式: 订单号,金额
	 * @param excelName       Excel  文件名
	 * @param   
	 *  
	 * 
	 * 
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void commonExportNew () throws Exception {	
		Map<String, String[]> paramMap = SysContexts.getRequestParameterMap();
		
		String TFBeanName =  DataFormat.getStringKey(paramMap, "TFBeanName"); 
		String excelName =  DataFormat.getStringKey(paramMap, "excelName");
		String excelLables =  DataFormat.getStringKey(paramMap, "excelLables");
		String excelKeys =  DataFormat.getStringKey(paramMap, "excelKeys");
		String params =  DataFormat.getStringKey(paramMap, "params");
		
		if(StringUtils.isEmpty(TFBeanName)){
			throw new BusinessException("导出失败，请确输入查询对象！");
		}
        if(StringUtils.isEmpty(excelKeys)){
        	throw new BusinessException("导出失败，请确输入查询对象KEY！");
		}
		if(StringUtils.isEmpty(excelLables)){
			throw new BusinessException("导出失败，请确输入查询对象列名！");
		}
		//组装数据
		List<List<?>> content = new LinkedList<List<?>>(); //导出的Excel数据结构
		
		List<String> lableName = new LinkedList<String>(); //列名
		
		String[] lables = excelLables.split(",");
		for(String s : lables ){
			lableName.add(s);
		}
		content.add(lableName);
		
		if(params.contains("&quot")){
			params = params.replaceAll("&quot;", "\"");
		} 
		
		//反射获取data数据
		Map<String, Object> beanParam = JsonHelper.parseJSON2Map(params);
		Class bean =  Class.forName(TFBeanName);
		IParamIn iParamIn = (IParamIn) bean.newInstance();
		BeanUtils.populate(iParamIn, beanParam);
		
		Map dataMap = null;
	    try {
	    	SimpleOutParamVO<Map> out = CallerProxy.call(iParamIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    	dataMap = out.getContent();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("系统异常，导出Excel文档失败！");
		}
	    if(dataMap == null){
	    	throw new BusinessException("导出Excel文档失败！");
	    }
		List<Map<String,Object>> items = (List<Map<String, Object>>) dataMap.get("items");
		//数据处理必须在VO里面处理
		String[] keys = excelKeys.split(",");
		List<String> contentValue = null;
		for(Map<String,Object> m : items ){
			contentValue = new ArrayList<String>();//内容 
			for(String k : keys ){
				 Object value = m.get(k);
				 if(value != null && !"null".equals(value+"")){
					 contentValue.add(value+"");
				 }else{
					 contentValue.add("");
				 }
			}
			content.add(contentValue);
		}
		if(StringUtils.isEmpty(excelName)){
			excelName = DateUtil.formatDate(CommonUtil.getCurrentDate(), DateUtil.DATETIME12_FORMAT2);
		}
		ExcelFilesUtil.exportExcel(SysContexts.getRequest(), SysContexts.getResponse(), excelName, content);
	}
	
}
