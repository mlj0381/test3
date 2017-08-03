package com.wo56.business.common.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class ContainerParamOut  extends BaseOutParamVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8328823316327553054L;
	private String containerId;

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	private String containerName;
	private String containerType;
}
