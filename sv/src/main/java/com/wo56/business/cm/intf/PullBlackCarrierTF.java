package com.wo56.business.cm.intf;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.cm.vo.CmPullBlack;
import com.wo56.business.cm.vo.out.PullBlackCarrierAddOut;
import com.wo56.business.cm.vo.out.PullBlackCarrierOut;
import com.wo56.business.org.vo.Organization;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class PullBlackCarrierTF implements IPullBlackCarrierIntf {

    @Override
    public Map<String, Object> doQueryPullBlackCarrier(Map<String, Object> inParam) throws Exception {
        Session session = SysContexts.getEntityManager(true);
        String carrierName = DataFormat.getStringKey(inParam, "carrierName");
        String carrierAccount = DataFormat.getStringKey(inParam, "carrierAccount");
        String state = String.valueOf(SysStaticDataEnum.ORG_STATE.COMMON);
        long parentOrgId = EnumConsts.ROOT_ORG_ID;
        String inUserName="";
        String inUserId="";
        String inputParamJson=StringEscapeUtils.unescapeHtml(DataFormat.getStringKey(inParam, "inputParamJson"));
        if(StringUtils.isNotEmpty(inputParamJson)){
            Map inputParamMap=JsonHelper.parseJSON2Map(inputParamJson);
            inUserName= DataFormat.getStringKey(inputParamMap, "carrierName");
            if(StringUtils.isNotEmpty(inUserName)){
                carrierName=inUserName;
            }
            inUserId = DataFormat.getStringKey(inputParamMap, "carrierAccount");
            if(StringUtils.isNotEmpty(inUserId)){
                carrierAccount=inUserId;
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT b.ID,b.BILL,p.TENANT_ID,b.CREATE_DATE,");
        sb.append(" (SELECT u.USER_NAME FROM  cm_user_info AS u  WHERE u.USER_ID=b.TARGET_ID) AS creatorName ,u.USER_NAME");
        sb.append(" ,b.REMARK,b.TYPE FROM cm_pull_black AS b , cm_user_info_pull AS p,organization AS o ,cm_user_info AS u ");
        sb.append(" WHERE b.USER_ID=u.USER_ID AND p.USER_ID= b.USER_ID AND o.TENANT_ID=p.TENANT_ID  ");
        sb.append(" AND u.user_type=7 AND b.STATUS=:state AND u.state=:state AND o.PARENT_ORG_ID=:parentOrgId");
        if (StringUtils.isNotEmpty(carrierName)) {
            sb.append(" AND u.USER_NAME=:carrierName ");
        }
        if (StringUtils.isNotEmpty(carrierAccount)) {
            sb.append(" AND b.BILL=:carrierAccount ");

        }
        Query query = session.createSQLQuery(sb.toString());
        query.setParameter("state", state);
        query.setParameter("parentOrgId", parentOrgId);
        if (StringUtils.isNotEmpty(carrierName)) {
            query.setParameter("carrierName", carrierName);
        }
        if (StringUtils.isNotEmpty(carrierAccount)) {
            query.setParameter("carrierAccount", carrierAccount);
        }
        IPage page = PageUtil.gridPage(query);
        List<Object[]> list = (List<Object[]>) page.getThisPageElements();
        List<PullBlackCarrierOut>rtnList=new ArrayList<PullBlackCarrierOut>();
        if(list!=null&&list.size()>0){
            for(Object[] obj:list){
                PullBlackCarrierOut out=new PullBlackCarrierOut();
                if(obj[0]!=null){
                	 long pullId=Long.parseLong(String.valueOf(obj[0]));
                     out.setPullId(pullId);
                }
                if(obj[1]!=null){
                     out.setCarrierAccount(String.valueOf(obj[1]));
                }
                if(obj[2]!=null){
                	BigInteger tenantId = (BigInteger) obj[2];
                	Organization organization=OraganizationCacheDataUtil.getOrganizationByTenantId(tenantId.longValue());
                	if(organization!=null&&organization.getOrgName()!=null){
                		out.setCompanyName(organization.getOrgName());
                	}
                }
                if(obj[3]!=null){
                    //创建时间
                    out.setCreateDate((Date)obj[3]);
                }
                if(obj[4]!=null){
                    //创建人
                   out.setCreatorName(String.valueOf(obj[4]));

                }
                if(obj[5]!=null){
                	//拉包工名字
                	out.setCarrierName(String.valueOf(obj[5]));
                }
                if(obj[6]!=null){
                	out.setRemark(String.valueOf(obj[6]));
                }
                //拉黑级别
                if(obj[7]!=null){
                	String pullLevel= SysStaticDataUtil.getSysStaticDataCodeName("BLACK_TYPE",obj[7] != null ? String.valueOf(obj[7]) : "" );
                	out.setPullLevel(pullLevel);
                }
                rtnList.add(out);
            }
        }
        page.setThisPageElements(rtnList);
        Pagination<Object[]> pages = new Pagination<Object[]>(page);
        Map<String, Object>rtnMap = new HashMap<String, Object>();
        rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(pages));
        return rtnMap;
    }


     /**
      * 按账号查询拉包工
      */
    public Map<String, Object> doQueryPullBlackCarrierByAccount(Map<String, Object> inParam) throws Exception {
        Session session = SysContexts.getEntityManager(true);
        String carrierAccount = DataFormat.getStringKey(inParam, "bill");
        BaseUser user = SysContexts.getCurrentOperator();
        if(StringUtils.isEmpty(carrierAccount)){
            throw new BusinessException("请输入拉包工账号！");
        }
        StringBuffer sb=new StringBuffer();
        sb.append(" SELECT u.USER_ID,u.USER_NAME,p.COOPERATION_MODE,p.TENANT_ID,p.PROVINCE,p.CITY,p.DISTRICT,u.LOGIN_ACCT ");
        sb.append(" FROM cm_user_info AS u, cm_user_info_pull AS p  ");
        sb.append(" WHERE u.USER_ID=p.USER_ID  AND  u.LOGIN_ACCT=:carrierAccount ");
        if(user.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULLCOM){
        	sb.append(" AND u.user_id in (select user_id from cm_user_org_rel where tenant_id = :tenantId)");
        }
        Query query=session.createSQLQuery(sb.toString());
        if(StringUtils.isNotEmpty(carrierAccount)){
            query.setParameter("carrierAccount", carrierAccount);
        }
        if(user.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULLCOM){
        	query.setParameter("tenantId", user.getTenantId());
        }
        List<Object[]>list=(List<Object[]>)query.list();
        Map<String,Object>map=new HashMap<String,Object>();
        PullBlackCarrierAddOut addOut=new PullBlackCarrierAddOut();
        if(list!=null&&list.size()>0){
            for(Object[] obj:list){
                if(obj[0]!=null){
                    long userId=Long.parseLong(String.valueOf(obj[0]));
                    addOut.setUserId(userId);
                }
                if(obj[1]!=null){
                    String userName=String.valueOf(obj[1]);
                    addOut.setUserName(userName);
                }
                if (obj[2] != null) {
                    // he
                    Integer cooperationMode = Integer.valueOf(String.valueOf(obj[2]));
                    if (cooperationMode == 2) {
                        String cooperationModeName = SysStaticDataUtil.getSysStaticDataCodeName("COOPERATION_MODE_YQ",
                                String.valueOf(cooperationMode));
                        addOut.setCooperationMode(cooperationModeName);
                    }
                    if (cooperationMode == 1) {
                        String cooperationModeName = SysStaticDataUtil.getSysStaticDataCodeName("COOPERATION_MODE_YQ",
                                String.valueOf(cooperationMode));
                        addOut.setCooperationMode(cooperationModeName);
                    }
                }
                if (obj[3] != null) {
                    BigInteger tenantId = (BigInteger) obj[3];
                    Organization organization = OraganizationCacheDataUtil.getOrganizationByTenantId(tenantId.longValue());
                        if (organization != null && organization.getOrgName() != null) {
                            String companyName = organization.getOrgName();
                            addOut.setCompanyName(companyName);
                        }
                }
                String provinceId = "";
                if (obj[4] != null) {
                    provinceId = String.valueOf(obj[4]);
                }
                String cityId = "";
                if (obj[5] != null) {
                    cityId = String.valueOf(obj[5]);
                }
                String districtId = "";
                if (obj[6] != null) {
                    districtId = String.valueOf(obj[6]);
                }
                // 服务区域
                String provinceName = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", provinceId).getName();
                String districtName = SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", districtId).getName();
                String cityName = SysStaticDataUtil.getCityDataList("SYS_CITY", cityId).getName();
                String serviceArea = "";
                serviceArea = serviceArea.concat(provinceName != null ? provinceName : "").concat(cityName != null ? cityName : "").concat(districtName != null ? districtName : "");
                addOut.setServiceArea(serviceArea);
                if(obj[7]!=null){
                    String userAcc=String.valueOf(obj[7]);
                    addOut.setCarrierAccount(userAcc);
                }
            }
        }else{
            throw new BusinessException("没有该账号信息");
        }
        map.put("item", addOut);
        return map;
    }

    @Override
    public Map<String, String> doSavePullBlackCarrierByAccount(Map<String, Object> inParam) throws Exception {
        String bill=DataFormat.getStringKey(inParam, "bill");
        if(StringUtils.isEmpty(bill)){
            throw new BusinessException("拉包工账号不能为空！");
        }
        long userId=DataFormat.getLongKey(inParam,"userId");
        if(userId<0){
        	throw new BusinessException("拉包工userId不能为空！");
        }
        Session session = SysContexts.getEntityManager();
        Session sessionQuery = SysContexts.getEntityManager(true);
        BaseUser user=SysContexts.getCurrentOperator();
        //查询是否被拉黑
        StringBuffer sb=new StringBuffer();
        sb.append(" SELECT u.USER_ID,u.LOGIN_ACCT,p.STATUS,p.ID FROM cm_pull_black AS p , cm_user_info AS u ");
        sb.append(" WHERE u.USER_ID=p.USER_ID AND u.USER_TYPE=:userType");
        sb.append(" AND u.LOGIN_ACCT=:bill  AND u.USER_ID=:userId");
        Query query=sessionQuery.createSQLQuery(sb.toString());
        query.setParameter("bill", bill);
        query.setParameter("userId", userId);
        query.setParameter("userType", SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL);
        List<Object[]>list=query.list();
        if(list!=null&&list.size()>0){
        	for(Object[] obj :list){
        		if(obj[2]!=null && (Integer)obj[2]==1){
        			throw new BusinessException("该账号已被拉黑");
        		} 
        	}
            
        }else{
            //拉黑处理
            String remark=DataFormat.getStringKey(inParam, "remark");
            String userName=DataFormat.getStringKey(inParam, "userName");
            // 拉黑表新增数据
            CmPullBlack cnPullBlack = new CmPullBlack();
            cnPullBlack.setBill(bill);
            if(StringUtils.isNotBlank((userName))){
                cnPullBlack.setName(userName);
            }
            cnPullBlack.setTargetId(user.getOperId());
            cnPullBlack.setCreateDate(new Date());
            cnPullBlack.setOpDate(new Date());
            cnPullBlack.setUserId(userId);
            cnPullBlack.setOpId(user.getOperId());
            cnPullBlack.setBusinessId(user.getTenantId());
            cnPullBlack.setType(SysStaticDataEnumYunQi.BLACK_TYPE.BLACK_PLAT);
            cnPullBlack.setStatus(SysStaticDataEnum.STS.VALID);
            if(StringUtils.isNotEmpty(remark)){
                cnPullBlack.setRemark(remark);
            }
            session.save(cnPullBlack);

        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("isOk", "Y");
        return map;
    }


    @Override
    public Map<String, String> doUpdatePullBlackCarrierByAccount(Map<String, Object> inParam) throws Exception {
        Session session = SysContexts.getEntityManager();
        BaseUser user=SysContexts.getCurrentOperator();
        long pullId=DataFormat.getLongKey(inParam,"pullId");
        if(pullId<0){
            throw new BusinessException("未传入主键Id");
        }
        CmPullBlack cmPullBlack=(CmPullBlack) session.get(CmPullBlack.class, pullId);
        if(cmPullBlack!=null && cmPullBlack.getBusinessId()!=null &&cmPullBlack.getBusinessId().longValue()!=user.getTenantId().longValue()){
            throw new BusinessException("只能取消平台拉黑的拉包工");
        }
        //平台取消平台拉黑的拉包工
        String sql ="DELETE FROM  cm_pull_black  WHERE ID=:pullId";
        Query query=session.createSQLQuery(sql);
        query.setParameter("pullId", pullId);
        query.executeUpdate();
        Map<String, String> map = new HashMap<String, String>();
        map.put("isOk", "Y");
        return map;
    }

}
