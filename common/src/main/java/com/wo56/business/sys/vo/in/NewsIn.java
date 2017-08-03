package com.wo56.business.sys.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;
/**
 * 查询新闻列表
 * @author liyiye
 *
 */
public class NewsIn implements IParamIn {
    private int state; 
    private int type;
    private String _GRID_TYPE;
    private int page;
    private int rows;
    private long tenantId;
    private String title;

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.COMMON.NEWS_LIST;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String get_GRID_TYPE() {
		return _GRID_TYPE;
	}
	public void set_GRID_TYPE(String _GRID_TYPE) {
		this._GRID_TYPE = _GRID_TYPE;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
/*	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}*/
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getTenantId() {
		return tenantId;
	}
	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	

}
