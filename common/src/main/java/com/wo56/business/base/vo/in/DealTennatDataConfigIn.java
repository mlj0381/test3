package com.wo56.business.base.vo.in;

import java.util.List;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class DealTennatDataConfigIn implements IParamIn {
	@Override
	public String getInCode() {
		return InterFacesCodeConsts.BASE.DEAL_TENNAT_DATA_CONFIG_IN;
	}

	public DealTennatDataConfigIn() {
	}

	public DealTennatDataConfigIn(List<TennatBusinessConfigData> configDatas) {
		super();
		this.configDatas = configDatas;
	}

	private List<TennatBusinessConfigData> configDatas;

	public List<TennatBusinessConfigData> getConfigDatas() {
		return configDatas;
	}

	public void setConfigDatas(List<TennatBusinessConfigData> configDatas) {
		this.configDatas = configDatas;
	}

}
