package com.wo56.common.parser;

import java.util.List;

import net.sf.json.JSONArray;

import com.wo56.common.in.CheckExcelRowDataResult;

public interface TennatDataConfigParser<E>{
	public static final int CHECK_REUSLT_0 = 0;// 校验不通过，需要提示信息，详细见CheckExcelRowDataResult的resultMessage字段
	public static final int CHECK_REUSLT_1 = 1;// 校验通过
	public static final int CHECK_REUSLT_2 = 2;// 校验不通过，忽略数据
	
	public static final CheckExcelRowDataResult DEFAULT_CHECK_RESULT = new CheckExcelRowDataResult(CHECK_REUSLT_1);
	
	/**
	 * 数据配置的名称，对应excel中sheet的名字
	 * 
	 * @return
	 */
	public abstract String sheetName();
	
	/**
	 * 校验行数据
	 * @param rowData: 行数据
	 * @param rowIndex: 行数据的下标，从0开始。0对应Excel中的第一行数据
	 * @param allRowDatas: 所有的行数据
	 * @return
	 */
	public CheckExcelRowDataResult checkExcelRowData(List<String> rowData, int rowIndex, List<List<String>> allRowDatas);
	
	/**
	 * 将Excel中的行数据解析成VO类型的对象
	 * rowData: 需要解析的行数据
	 * @return
	 */
	public E parseExcelRowDataToVo(List<String> rowData);
	
	/**
	 * 将BO层的请求数据解析成VO数据
	 * 
	 * @param httpRequestData
	 * @return
	 */
	public List<E> parseHttpRequestDataToVo(JSONArray httpRequestData);
}
