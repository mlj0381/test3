package com.wo56.business.system.vo.out;

import com.framework.core.identity.vo.BaseUser;
import com.framework.core.svcaller.vo.BaseOutParamVO;

/**
 * 业务对象出参
 * @author wengxk
 *
 */
public class LoginParamOut extends BaseOutParamVO {

	private static final long serialVersionUID = 3835937746019601078L;

	private BaseUser content = null;

	public BaseUser getContent() {
		return content;
	}

	public void setContent(BaseUser content) {
		this.content = content;
	}

}
