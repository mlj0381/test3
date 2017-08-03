package com.wo56.business.system.vo.out;

import com.framework.core.inter.vo.Pagination;
import com.framework.core.svcaller.vo.BaseOutParamVO;

/**
 * 业务对象出参
 * @author wengxk
 *
 */
public class DemoPageParamOut extends BaseOutParamVO {

	private static final long serialVersionUID = 3835937746019601078L;

	private Pagination content = null;

	public Pagination getContent() {
		return content;
	}

	public void setContent(Pagination content) {
		this.content = content;
	}

}
