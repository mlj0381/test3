package com.wo56.common.parser;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wo56.business.base.vo.OrdImportBillInfo;
import com.wo56.common.in.CheckExcelRowDataResult;



/**
 * "网点信息"解析器
 * @author chenjun
 *
 */
public class OrdImportBillParser implements TennatDataConfigParser<OrdImportBillInfo> {
	public static final String BUSINESS_NAME = "运单信息";
	public static final String SHEET_NAME = BUSINESS_NAME;
	@Override
	public CheckExcelRowDataResult checkExcelRowData(List<String> rowData, int rowIndex, List<List<String>> allRowDatas) {
		return TennatDataConfigParser.DEFAULT_CHECK_RESULT;
	}
	
	/**
	 * 将Excel中的行数据解析成VO类型的对象
	 * @return
	 */
	@Override
	public OrdImportBillInfo parseExcelRowDataToVo(List<String> rowData) {
		return new OrdImportBillInfo(rowData.get(0), rowData.get(1), rowData.get(2),
				 				 rowData.get(3), rowData.get(4), rowData.get(5), 
				 				 rowData.get(6), rowData.get(7), rowData.get(8), 
				 				 rowData.get(9), rowData.get(10),rowData.get(11), 
				 				 rowData.get(12),rowData.get(13),rowData.get(14),
				 				 rowData.get(15),rowData.get(16),rowData.get(17),
				 				 rowData.get(18),rowData.get(19),rowData.get(20)
				 				,rowData.get(21),rowData.get(22));
	}
	
	public List<OrdImportBillInfo> parseHttpRequestDataToVo(JSONArray httpRequestData) {
		List<OrdImportBillInfo> results = new ArrayList<OrdImportBillInfo>();
		for (int i = 0; i < httpRequestData.size(); i++) {
			OrdImportBillInfo orgInfo = (OrdImportBillInfo) JSONObject.toBean((JSONObject) httpRequestData.get(i), OrdImportBillInfo.class);
			results.add(orgInfo);
		}
		return results;
	}

	@Override
	public String sheetName() {
		return SHEET_NAME;
	}
}
