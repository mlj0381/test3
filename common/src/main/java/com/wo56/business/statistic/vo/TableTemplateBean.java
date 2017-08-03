package com.wo56.business.statistic.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 表格模版bean
 * 
 * @author chenjun
 *
 */
public class TableTemplateBean implements Serializable {
	private static final long serialVersionUID = -2476888563939103048L;

	private String title;// table标题
	private String xaxisTip;// x轴提示
	private String[] xaxisHeads;// x轴的头部

	private String yaxisTip;// y轴提示
	private List<TableYAxisHeadBean> yaxisHeads;// y轴标题
	private List<TableXAxisDataColBean> xaxisDataCols;// x轴数据，大小应该与xaxisHeads长度保持一致
	private long total;
	private String totalTip;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getXaxisTip() {
		return xaxisTip;
	}

	public void setXaxisTip(String xaxisTip) {
		this.xaxisTip = xaxisTip;
	}

	public String[] getXaxisHeads() {
		return xaxisHeads;
	}

	public void setXaxisHeads(String[] xaxisHeads) {
		this.xaxisHeads = xaxisHeads;
	}

	public String getYaxisTip() {
		return yaxisTip;
	}

	public void setYaxisTip(String yaxisTip) {
		this.yaxisTip = yaxisTip;
	}

	public List<TableYAxisHeadBean> getYaxisHeads() {
		return yaxisHeads;
	}

	public void setYaxisHeads(List<TableYAxisHeadBean> yaxisHeads) {
		this.yaxisHeads = yaxisHeads;
	}

	public List<TableXAxisDataColBean> getXaxisDataCols() {
		return xaxisDataCols;
	}

	public void setXaxisDataCols(List<TableXAxisDataColBean> xaxisDataCols) {
		this.xaxisDataCols = xaxisDataCols;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public String getTotalTip() {
		return totalTip;
	}

	public void setTotalTip(String totalTip) {
		this.totalTip = totalTip;
	}
}
