package com.wo56.business.statistic.vo;

import java.io.Serializable;
import java.util.List;

public class TableXAxisDataColBean implements Serializable {
	private static final long serialVersionUID = -4922724714069735740L;
	private List<TableXAxisColDataBean> colRows;// 列数据中的行，行的大小要与${@link TableTemplateBean}中的yaxisHeads(注意包括subYAxisHeads)大小保持一致

	public TableXAxisDataColBean() {
	}

	public TableXAxisDataColBean(List<TableXAxisColDataBean> colRows) {
		super();
		this.colRows = colRows;
	}

	public List<TableXAxisColDataBean> getColRows() {
		return colRows;
	}

	public void setColRows(List<TableXAxisColDataBean> colRows) {
		this.colRows = colRows;
	}
}
