package com.wo56.business.common.vo.in;

import org.apache.commons.lang.StringUtils;

import com.framework.components.citys.Province;
import com.framework.core.util.SysStaticDataUtil;

/**
 * 省份翻译器
 * 
 * @author chenjun
 *
 */
public class CompareFieldValueProvinceTranslator implements CompareFieldValueTranslator<Long> {

	@Override
	public String translator(Long value) {
		if (null == value)
			return StringUtils.EMPTY;
		Province province = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(value));
		if (null != province)
			return province.getName();
		return String.valueOf(value);
	}
}
