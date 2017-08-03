package com.wo56.business.common.vo.in;



/**
 * 组织翻译器
 * 
 * @author chenjun
 *
 */
public class CompareFieldValueBooleanTranslator implements CompareFieldValueTranslator<Boolean> {

	@Override
	public String translator(Boolean value) {
		if (value == false)
			return "否";
		return "是";

	}


}
