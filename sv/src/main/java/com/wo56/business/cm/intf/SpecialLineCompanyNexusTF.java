package com.wo56.business.cm.intf;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.wo56.business.cm.vo.CmOrgRel;
import com.wo56.business.cm.vo.in.CmOrgRelIn;
import com.wo56.business.cm.vo.out.SpecialLineCompanyNexusOut;
import com.wo56.business.org.vo.Organization;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class SpecialLineCompanyNexusTF implements ISpecialLineCompanyNexusIntf{
	
	/**
	 * 查询专线拉包公司关系
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object>doQuerySpecialLineCompanyNexus(Map<String, Object> inParam) throws Exception{
		long companyId=DataFormat.getLongKey(inParam,"companyId");
		long lineId=DataFormat.getLongKey(inParam,"lineId");
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		
		String type = String.valueOf(SysStaticDataEnum.USER_TYPE.PLATFORM);
		String state = String.valueOf(SysStaticDataEnum.ORG_STATE.COMMON);
		Session session = SysContexts.getEntityManager(true);
		BaseUser user=SysContexts.getCurrentOperator();
		long userId=user.getUserId();
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT c.LINE_ORG_ID,c.BUSI_ORG_ID ,c.CREATE_DATE,c.OP_NAME,c.REL_ID FROM");
		sb.append(" cm_org_rel c,cm_user_info AS u ,organization AS o");
		sb.append(" WHERE   c.OP_ID=u.USER_ID  AND c.LINE_ORG_ID=o.ORG_ID  ");
		sb.append(" AND u.USER_TYPE =:type AND c.STATE =:state AND o.STATE =:state AND o.PARENT_ORG_ID =:parentOrgId  ");
		
		if(user.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PLATFORM){
			sb.append(" AND c.OP_ID = :userId ");
			
		}
		
		if(companyId>0){
			sb.append(" AND c.BUSI_ORG_ID=:companyId ");
		}
		if(user.getUserType() == SysStaticDataEnum.USER_TYPE.PULL_PAG_COMPANY){
			sb.append(" AND c.BUSI_TENANT_ID = :tenantId ");
		}
		if(lineId>0){
			sb.append(" AND c.LINE_ORG_ID=:lineId ");
		}
		Query query=session.createSQLQuery(sb.toString());
		if(companyId>0){
			query.setParameter("companyId", companyId);
			
		}
		
		if(user.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PLATFORM){
			query.setParameter("userId", userId);
			
		}
		if(lineId>0){
			query.setParameter("lineId", lineId);
		}
		if(user.getUserType() == SysStaticDataEnum.USER_TYPE.PULL_PAG_COMPANY){
			query.setParameter("tenantId", user.getTenantId());
		}
		if (StringUtils.isNotEmpty(state)) {
			query.setParameter("state", state);
		}
		if(StringUtils.isNotEmpty(type)){
			query.setParameter("type", type);
		}
		query.setParameter("parentOrgId", parentOrgId);
		IPage page = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>) page.getThisPageElements();
		List<SpecialLineCompanyNexusOut>rtnList=new ArrayList<SpecialLineCompanyNexusOut>();
		if(list!=null&&list.size()>0){
			for(Object[] obj:list){
				SpecialLineCompanyNexusOut out=new SpecialLineCompanyNexusOut();
				if(obj[0]!=null){
					//专线公司名称
					long speciaLineId=Long.parseLong(String.valueOf(obj[0]));
					Organization oraganization=OraganizationCacheDataUtil.getOrganization(speciaLineId);
					out.setSpeciaLineName(oraganization.getOrgName());
					// 专线公司电话
					out.setSpecialLineBill(oraganization.getOrgPrincipalPhone() != null ? oraganization.getOrgPrincipalPhone() : "");
					//专线联系人
					out.setSpecialLinePrincipal(oraganization.getOrgPrincipal() != null ? oraganization.getOrgPrincipal() : "");
				}
				if(obj[1]!=null){
					long carrierCompanyId=Long.parseLong(String.valueOf(obj[1]));
					Organization oraganization=OraganizationCacheDataUtil.getOrganization(carrierCompanyId);
					out.setCarrierCompanyName(oraganization.getOrgName() != null ? oraganization.getOrgName() : "");
					out.setCarrierCompanyPrincipal(oraganization.getOrgPrincipal() != null ? oraganization.getOrgPrincipal() : "");
					out.setCarrierCompanyBill(oraganization.getOrgPrincipalPhone() != null ? oraganization.getOrgPrincipalPhone() : "");
					
				}
				if(obj[2]!=null){
					out.setCreateDate((Date)obj[2]);
				}
				if(obj[3]!=null){
					out.setCreatorName(String.valueOf(obj[3]));
				}
				if(obj[4]!=null){
					out.setRelId(Long.parseLong(String.valueOf(obj[4])));
				}
				rtnList.add(out);
			}
		}
		page.setThisPageElements(rtnList);
		Pagination<Object[]> pages = new Pagination<Object[]>(page);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(pages));
		return rtnMap;
	}
	/**
	 * 新增专线与拉包公司关系
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, String>doSaveSpecialLineCompanyNexus(Map<String, Object> inParam) throws Exception{
		Session session = SysContexts.getEntityManager();
		Session query = SysContexts.getEntityManager(true);
		CmOrgRelIn cmOrgRelIn=new CmOrgRelIn();
		BeanUtils.populate(cmOrgRelIn, inParam);
		if( cmOrgRelIn.getCompanyId()<0){
			throw new BusinessException("拉包公司组织Id不能为空");
		}
		if(cmOrgRelIn.getLineId()<0){
			throw new BusinessException("专线组织Id不能为空");
		}
		BaseUser user = SysContexts.getCurrentOperator();
		Organization orgCompany = (Organization) query.get(Organization.class, cmOrgRelIn.getCompanyId());
		Organization orgLine=(Organization) query.get(Organization.class, cmOrgRelIn.getLineId());
		if (!getCheckCmOrgRel(cmOrgRelIn.getLineId(), cmOrgRelIn.getCompanyId())) {
			throw new BusinessException("已经存在关系，不能重复添加！");
		}
		if(cmOrgRelIn.getLineId()>0){
			CmOrgRel cor = new CmOrgRel();
	        // 选择归属网点
	        cor.setLineOrgId(cmOrgRelIn.getLineId());
	        cor.setLineTenantId(orgLine.getTenantId());
	        cor.setBusiOrgId(cmOrgRelIn.getCompanyId());
	        cor.setBusiTenantId(orgCompany.getTenantId());
	        cor.setOpDate(new Date(System.currentTimeMillis()));
	        cor.setOpId(user.getUserId());
	        cor.setOpName(user.getUserName());
	        cor.setCreateDate(new Date());
	        cor.setState(SysStaticDataEnum.STS.VALID);
	        session.save(cor);
		}
        Map<String, String> map = new HashMap<String, String>();
		map.put("isOk", "Y");
		return map;
	}
	/**
	 * 查询是否已存在关系
	 * @param lineOrgId
	 * @param busiOrgId
	 * @return
	 */
	public boolean getCheckCmOrgRel(Long lineOrgId, Long busiOrgId) {
		Session session = SysContexts.getEntityManager(true);
		int state = SysStaticDataEnum.STS.VALID;
		BaseUser user = SysContexts.getCurrentOperator();
		String sql = "select * from cm_org_rel c where c.LINE_ORG_ID = :lineOrgId and c.BUSI_ORG_ID = :busiOrgId and c.state = :state AND c.OP_ID = :tenantId ";
		Query query = session.createSQLQuery(sql);
		query.setParameter("lineOrgId", lineOrgId);
		query.setParameter("state", state);
		query.setParameter("busiOrgId", busiOrgId);
		query.setParameter("tenantId", user.getOperId());
		List<Object> cor = query.list();
		if (cor != null && cor.size() > 0) {
			return false;
		}
		return true;
	}
	/**
	 * 删除专线与拉包公司关系
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,String>deleteSpecialLineCompanyNexus(Map<String, Object> inParam) throws Exception{
		Session session=SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		long opId=user.getOperId();
		long relId=DataFormat.getIntKey(inParam, "relId");
		if(relId<0){
			throw new BusinessException("关系Id不能为空！");
		}
		String sql="DELETE FROM cm_org_rel WHERE rel_id=:relId AND op_id=:opId";
		Query query=session.createSQLQuery(sql);
		query.setParameter("relId", relId);
		query.setParameter("opId", opId);
		query.executeUpdate();
		Map<String, String> map = new HashMap<String, String>();
		map.put("isOk", "Y");
		return map;
	}
	@Override
	public Map<String,String> queryByIdSpecialLineCompanyNexus(Map<String, Object> inParam) throws Exception {
		Session session=SysContexts.getEntityManager(true);
		BaseUser user=SysContexts.getCurrentOperator();
		long relId=DataFormat.getIntKey(inParam, "relId");
		if(relId<0){
			throw new BusinessException("关系Id不能为空");
		}
		Criteria ca=session.createCriteria(CmOrgRel.class);
		ca.add(Restrictions.eq("relId", relId));
		ca.add(Restrictions.eq("opId", user.getOperId()));
		CmOrgRel cmOrgRel=(CmOrgRel) ca.uniqueResult();
		Map <String,String>map=new HashMap<String,String>();
		map.put("lineId", cmOrgRel.getLineOrgId().toString());
		map.put("companyId", cmOrgRel.getBusiOrgId().toString());
		return map;
	}
	@Override
	public Map<String, String> doUpdateSpecialLineCompanyNexus(Map<String, Object> inParam) throws Exception {
		Session session = SysContexts.getEntityManager();
		Session query = SysContexts.getEntityManager(true);
		long relId=DataFormat.getLongKey(inParam,"relId");
		long companyId=DataFormat.getLongKey(inParam,"companyId");
		long lineId=DataFormat.getLongKey(inParam,"lineId");
		BaseUser user = SysContexts.getCurrentOperator();
		if(relId<0){
			throw new BusinessException("id不能为空");
		}
		if(companyId<0){
			throw new BusinessException("请选择拉包公司");
		}
		if(lineId<0){
			throw new BusinessException("请选择专线");
		}
		CmOrgRel cmOrgRel=(CmOrgRel) session.get(CmOrgRel.class, relId);
		if(cmOrgRel.getLineOrgId()==lineId&&cmOrgRel.getBusiOrgId()==companyId){
			throw new BusinessException("未作修改，请重新选择！");
		}
		Organization orgCompany = (Organization) query.get(Organization.class, companyId);
		Organization orgLine=(Organization) query.get(Organization.class, lineId);
		if (!getCheckCmOrgRel(lineId, companyId)) {
			throw new BusinessException("已经存在关系，不能重复添加！");
		}
		cmOrgRel.setLineOrgId(lineId);
		cmOrgRel.setLineTenantId(orgLine.getTenantId());
		cmOrgRel.setBusiOrgId(companyId);
		cmOrgRel.setBusiTenantId(orgCompany.getTenantId());
		cmOrgRel.setOpDate(new Date(System.currentTimeMillis()));
		cmOrgRel.setOpId(user.getOperId());
		cmOrgRel.setOpName(user.getUserName());
		cmOrgRel.setCreateDate(new Date());
		cmOrgRel.setState(SysStaticDataEnum.STS.VALID);
	    session.update(cmOrgRel);
	    Map<String, String> map = new HashMap<String, String>();
	  	map.put("isOk", "Y");
		return map;
	}
	

}
