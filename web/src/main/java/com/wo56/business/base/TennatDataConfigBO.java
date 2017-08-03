package com.wo56.business.base;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.fileupload.FileItem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.framework.core.SysContexts;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.DateUtil;
import com.framework.core.util.ExcelFilesValidate;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.base.vo.in.DealTennatDataConfigIn;
import com.wo56.business.base.vo.in.TennatBusinessConfigData;
import com.wo56.common.handler.TennatDataConfigHelper;
import com.wo56.common.in.CheckExcelRowDataResult;
import com.wo56.common.parser.TennatDataConfigParser;

/**
 * 租户数据管理
 * 
 * @author chenjun
 *
 */
public class TennatDataConfigBO {
	public String dealTennatDataConfig() throws Exception {
		FileItem fileItem = SysContexts.getFileItem("myFile");
		Workbook workbook = WorkbookFactory.create(fileItem.getInputStream());
		Map<String,String> parseResult = new HashMap<String,String>();
		parseResult.put("resultCode", "1");
		
		List<TennatBusinessConfigData> configDatas = new ArrayList<TennatBusinessConfigData>();
		
		
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			String sheetName = workbook.getSheetName(i);
			TennatDataConfigParser<?> parser = TennatDataConfigHelper.getParser(sheetName);
			if (null == parser) {
				parseResult.put("resultCode", "0");
				parseResult.put("resultMessage", "无法解析Excel文件中名字为[" + sheetName + "]的数据");
				break;
			}
			List<List<String>> rows = getExcelContent(workbook, i, 2);
			List<Object> rowObjects = new ArrayList<Object>();
			for (int j = 0; null != rows && j < rows.size(); j++) {
				 
				List<String> row = rows.get(j);
				CheckExcelRowDataResult checkResult = parser.checkExcelRowData(row, j, rows);
				if (TennatDataConfigParser.CHECK_REUSLT_0 == checkResult.getResultCode()) {
					parseResult.put("resultCode", "0");
					parseResult.put("resultMessage", checkResult.getResultMessage());
					return parseResult.toString();
				} else if (TennatDataConfigParser.CHECK_REUSLT_1 == checkResult.getResultCode()) {
					Object rowObject = parser.parseExcelRowDataToVo(row);
					if (null != rowObject)
						rowObjects.add(rowObject);
				}
				
			}
			configDatas.add(new TennatBusinessConfigData(sheetName, rowObjects));
		}
		if (!"1".equals(parseResult.get("resultCode"))) {
			return parseResult.toString();
		}
		if (configDatas.size() == 0) {
			parseResult.put("resultCode", "0");
			parseResult.put("resultMessage", "无法获取Excel中的配置数据，请检查Excel中各Sheet页是否存在数据");
			return parseResult.toString();
		}
		DealTennatDataConfigIn in = new DealTennatDataConfigIn(configDatas);
		try {
			SimpleOutParamVO<Map> out = CallerProxy.call(in, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
			return JsonHelper.json(out.getContent());
		} catch (Exception e) {
			parseResult.put("resultCode", "0");
			parseResult.put("resultMessage", e.getMessage());
			return parseResult.toString();
		}
	}
	
	/**
	 * 框架层@{link ExcelFilesUtil}没有提供此种方法，所以只能自己处理一下
	 * @param workbook
	 * @param sheetIndex
	 * @param beginRow
	 * @return
	 * @throws Exception
	 */
	private static List<List<String>> getExcelContent(Workbook workbook, int sheetIndex, int beginRow) throws Exception {
		return getExcelContent(workbook, sheetIndex, beginRow, null);
	}
	
	private static List<List<String>> getExcelContent(Workbook workbook, int sheetIndex, int beginRow, ExcelFilesValidate[] validates) throws Exception {
		List<List<String>> fileContent = new ArrayList<List<String>>();
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		int rows = sheet.getLastRowNum() + 1;
		if (rows >= beginRow) {
			List<String> rowList = null;
			Row row = null;
			for (int i = beginRow - 1; i < rows; i++) {
				row = sheet.getRow(i);
				rowList = new ArrayList<String>();
				fileContent.add(rowList);
				if (row != null) {
					int cells = row.getLastCellNum();
					for (int j = 0; j < cells; j++) {
						Cell cell = row.getCell(j);
						String cellValue = "";
						if (cell != null) {
							switch (cell.getCellType()) {
								/*case 0:
									if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
										cellValue = DateUtil.formatDateByFormat(cell.getDateCellValue(), DateUtil.DATETIME_FORMAT);
									} else {
										cellValue = String.valueOf(cell.getNumericCellValue());
										if (cellValue.endsWith(".0"))
											cellValue = cellValue.substring(0, cellValue.indexOf(".0"));
									}
									break;*/
								case HSSFCell.CELL_TYPE_STRING:
									cellValue = cell.getStringCellValue();
									break;
								case HSSFCell.CELL_TYPE_NUMERIC:
									if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
										cellValue = DateUtil.formatDateByFormat(cell.getDateCellValue(), DateUtil.DATETIME_FORMAT);
									} else {
										BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
							            String value = big.toString(); 
							            if(value.indexOf(".0") > 0 || value.indexOf(".") < 0){
							            	DecimalFormat df = new DecimalFormat("0");
											cellValue = df.format(cell.getNumericCellValue());
							            }else{
							            	DecimalFormat df = new DecimalFormat("####0.00");
											cellValue = df.format(cell.getNumericCellValue());
							            }
										
									}
									break;
								case HSSFCell.CELL_TYPE_BLANK:
									cellValue = "";
									break;
								case HSSFCell.CELL_TYPE_BOOLEAN:
									cellValue = String.valueOf(cell.getBooleanCellValue());
									break;
								case HSSFCell.CELL_TYPE_ERROR:
									cellValue = String.valueOf(cell.getErrorCellValue());
									break;
								case HSSFCell.CELL_TYPE_FORMULA: // 公式  
									cellValue=String.valueOf(cell.getCellFormula());
									break; 
							}
						}

						if ((validates != null) && (validates.length > j)) {
							if (cellValue == null) {
								throw new Exception("第" + (i + beginRow - 1) + "行,第" + (j + 1) + "列数据校验出错：" + validates[j].getErrorMsg());
							}
							Pattern p = Pattern.compile(validates[j].getPattern());
							Matcher m = p.matcher(cellValue);
							if (!m.matches()) {
								throw new Exception("第" + (i + beginRow - 1) + "行,第" + (j + 1) + "列数据校验出错：" + validates[j].getErrorMsg());
							}
						}
						rowList.add(cellValue);
					}
				}
			}
		}
		return fileContent;
	}
}
