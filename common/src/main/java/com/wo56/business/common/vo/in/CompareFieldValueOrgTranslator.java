package com.wo56.business.common.vo.in;

import org.apache.commons.lang.StringUtils;

import com.wo56.business.org.vo.Organization;
import com.wo56.common.utils.OraganizationCacheDataUtil;

/**
 * 组织翻译器
 * 
 * @author chenjun
 *
 */
public class CompareFieldValueOrgTranslator implements CompareFieldValueTranslator<Long> {

	@Override
	public String translator(Long value) {
		if (null == value)
			return StringUtils.EMPTY;
		if(value==-1){
			return "无";
		}
		Organization organization = OraganizationCacheDataUtil.getOrganization(value);
		if (null != organization)
			return organization.getOrgName();
		return String.valueOf(value);
	}
}
