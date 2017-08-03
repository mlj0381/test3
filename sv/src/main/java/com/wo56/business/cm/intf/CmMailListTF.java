package com.wo56.business.cm.intf;

import java.math.BigInteger;
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
import com.framework.core.util.DataFormat;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.cm.impl.CmMailListSV;
import com.wo56.business.cm.vo.CmMailList;
import com.wo56.business.cm.vo.CmPullBlack;
import com.wo56.business.cm.vo.out.CmMailListOut;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;

public class CmMailListTF {


	/**
	 * 通讯录新增/修改
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doSaveMailList(Map<String, String> inParam) throws Exception {
		Session session = SysContexts.getEntityManager();
		CmMailListSV cnMailListSV = (CmMailListSV) SysContexts.getBean("cmMailListSV");
		CmMailList cnMailList = new CmMailList();
		BaseUser base = SysContexts.getCurrentOperator();
		long userId = base.getUserId();
		String bill = DataFormat.getStringKey(inParam, "bill");
		if (StringUtils.isEmpty(bill)) {
			throw new BusinessException("手机号不能为空");
		}
		if(!CommonUtil.isCheckPhone(bill)){
			throw new BusinessException("输入的手机号码不正确！");
		}
		String name = DataFormat.getStringKey(inParam, "name");
		if (StringUtils.isEmpty(name)) {
			throw new BusinessException("联系人名称不能为空");
		}
		Integer type = DataFormat.getIntKey(inParam, "type");
		if (type < 0) {
			throw new BusinessException("联系人类型不能为空");
		}
		long id = DataFormat.getLongKey(inParam, "id");
		if (id > 0) {
			cnMailList = cnMailListSV.getCnMailList(id);
		}
		//添加好友是否已存在该好友
		if(id<0){
			Criteria criteria=session.createCriteria(CmMailList.class);
			criteria.add(Restrictions.eq("bill", bill));
			criteria.add(Restrictions.eq("userId", userId));
			CmMailList crList=(CmMailList)criteria.uniqueResult();
			if(crList!=null){
				throw new BusinessException("已存在该好友");
			}
		}
		cnMailList.setBill(bill);
		cnMailList.setName(name);
		cnMailList.setType(type);
		cnMailList.setUserId(userId);
		cnMailList.setStatus(SysStaticDataEnum.STS.VALID);
		cnMailList.setIsBlack(SysStaticDataEnum.MAILLIST_TYPE_YQ.MAILLIST_TYPE_NOT_BLACK);
		Date date = new Date();
		cnMailList.setCreateDate(date);
		cnMailList.setOpDate(date);
		Map map = new HashMap();
		map.put("info", cnMailListSV.doSaveMailList(cnMailList));
		return map;
	}

	/**
	 * 通讯录列表
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doQueryMailList(Map<String, String> inParam) throws Exception {
		// 根据userId查询通讯录按type分组
		Session session = SysContexts.getEntityManager(true);
		BaseUser base = SysContexts.getCurrentOperator();
		long userId = base.getUserId();
		String sql = "select m.NAME,m.BILL,m.ID,m.type,m.IS_BLACK from cm_mail_list as m where m.USER_ID=:userId  and m.STATUS=1 ";
		Query query = session.createSQLQuery(sql);
		query.setParameter("userId", userId);
		List<Object[]> listObj = query.list();
		Map<String, Object> mapGroup = new HashMap<String, Object>();
		if (listObj != null && listObj.size() > 0) {
			for (Object[] temp : listObj) {
				// 数据设值
				CmMailListOut cmMailListOut = new CmMailListOut();
				if (temp[1] != null) {
					String bill = (String) temp[1];
					cmMailListOut.setBill(bill);
				}
				if (temp[0] != null) {
					String name = (String) temp[0];
					cmMailListOut.setName(name);
				}
				BigInteger id = (BigInteger) temp[2];
				cmMailListOut.setId(id.longValue());
				Integer isBlack;
				if (temp[4] != null) {
					// isBlack = (BigInteger) temp[4];
					isBlack = Integer.valueOf(String.valueOf(temp[4]));
					cmMailListOut.setIsBlack(isBlack.longValue());
				}
				Integer type;
				if(temp[3]!=null){
					type=(Integer)temp[3];
					cmMailListOut.setType(type);
				}
				String title = SysStaticDataUtil.getSysStaticDataCodeName("MAIL_TYPE_YUNQI",temp[3] != null ? String.valueOf(temp[3]) : "" );
				// 数据分组
				List<CmMailListOut> cnMailList = (List<CmMailListOut>) mapGroup.get(title);
				if (cnMailList == null) {
					cnMailList = new ArrayList<CmMailListOut>();
				}
				cnMailList.add(cmMailListOut);
				mapGroup.put(title, cnMailList);

			}
		}
		// 封装分组数据结构
		List<Map> listout = new ArrayList<Map>();
		for (Map.Entry<String, Object> entry : mapGroup.entrySet()) {
			Map m = new HashMap();
			m.put("typeTitle", entry.getKey());
			m.put("typeArray", entry.getValue());
			listout.add(m);
		}
		Map m = new HashMap();
		m.put("item", listout);
		return m;
	}

	/**
	 * 通讯录删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public Map deleteMailListById(long id) throws Exception {
		CmMailListSV cnMailListSV = (CmMailListSV) SysContexts.getBean("cmMailListSV");
		BaseUser base = SysContexts.getCurrentOperator();
		long userId=base.getUserId();
		if (id < 0) {
			throw new BusinessException("未传入联系人ID");
		}
		cnMailListSV.deleteMailListById(id,userId);
		Map map =new HashMap();
		map.put("info", "Y");
		return map;
	}

	/**
	 * 通讯录拉黑
	 * 
	 * @param inParam
	 * @throws Exception
	 */
	public Map doUpdatePullMailList(Map<String, Object> inParam) throws Exception {
		Session session = SysContexts.getEntityManager();
		long id = DataFormat.getLongKey(inParam, "id");
		BaseUser base = SysContexts.getCurrentOperator();
		long userId=base.getUserId();
		// 当前用户Id
		int type = DataFormat.getIntKey(inParam, "type");
		if (type < 0) {
			throw new BusinessException("未传入拉黑类型ID");
		}
		if (id < 0) {
			throw new BusinessException("未传入主键ID");
		}
		CmMailList cnMailList = (CmMailList) session.get(CmMailList.class, id);
		Date date = new Date();
		cnMailList.setIsBlack(type);
		cnMailList.setOpDate(date);
		session.update(cnMailList);
		// 取消拉黑，查询拉黑表是否存在数据
		if (type == SysStaticDataEnum.MAILLIST_TYPE_YQ.MAILLIST_TYPE_NOT_BLACK) {
			Criteria ca = session.createCriteria(CmPullBlack.class);
			ca.add(Restrictions.eq("bill", cnMailList.getBill()));
			ca.add(Restrictions.eq("targetId", userId));
			ca.add(Restrictions.eq("status", SysStaticDataEnumYunQi.STS.VALID));
			CmPullBlack cnPullBlackList = (CmPullBlack) ca.uniqueResult();
			if (cnPullBlackList != null) {
				long pullId=cnPullBlackList.getId();
				String sql ="DELETE FROM  cm_pull_black  WHERE ID=:pullId";
				 Query query=session.createSQLQuery(sql);
				 query.setParameter("pullId", pullId);
				 query.executeUpdate();
			}
		}
		//拉黑
		if (type == SysStaticDataEnum.MAILLIST_TYPE_YQ.MAILLIST_TYPE_IS_BLACK) {
				// 拉黑表新增数据
				CmPullBlack cnPullBlack = new CmPullBlack();
				cnPullBlack.setBill(cnMailList.getBill());
				cnPullBlack.setName(cnMailList.getName());
				cnPullBlack.setTargetId(userId);
				cnPullBlack.setCreateDate(date);
				cnPullBlack.setOpDate(date);
				cnPullBlack.setOpId(userId);
				cnPullBlack.setBusinessId(userId);
				cnPullBlack.setType(SysStaticDataEnum.PULL_BLACK_TYPE_YQ.PULL_BLACK_TYPE_CUSTOMER);
				cnPullBlack.setStatus(SysStaticDataEnum.STS.VALID);
				session.save(cnPullBlack);

		}
		Map map =new HashMap();
		map.put("info", "Y");
		return map;
	}

	/**
	 * 通讯录模糊查询
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doQueryMailListByVarName(Map<String, String> inParam) throws Exception {
		Session session = SysContexts.getEntityManager(true);
		String varName = DataFormat.getStringKey(inParam, "varName");
		if (StringUtils.isEmpty(varName)) {
			throw new BusinessException("联系人名称或手机号不能为空");
		}
		BaseUser base = SysContexts.getCurrentOperator();
		long userId = base.getUserId();
		if (userId < 0) {
			throw new BusinessException("用户id不能为空");
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select m.NAME,m.BILL,m.ID,m.TYPE,m.IS_BLACK from cm_mail_list as m where m.USER_ID=:userId");
		sb.append(" AND (m.NAME like :varName OR m.Bill like :varName)");
		Query query = session.createSQLQuery(sb.toString());
		query.setParameter("userId", userId);
		query.setParameter("varName", "%" + varName + "%");
		List<Object[]> list = query.list();
		List<CmMailListOut> outList = new ArrayList<CmMailListOut>();
		if(list.size()>0&&list!=null){
			for (Object[] temp : list) {
				CmMailListOut cmMailListOut=new CmMailListOut();
				BigInteger id = (BigInteger) temp[2];
				cmMailListOut.setId(id.longValue());
				cmMailListOut.setBill(String.valueOf(temp[1]));
				cmMailListOut.setName(String.valueOf(temp[0]));
				Integer type;
				if(temp[3]!=null){
					type=(Integer)temp[3];
					cmMailListOut.setType(type);
				}
				Integer isBlack;
				if (temp[4] != null) {
					isBlack = Integer.valueOf(String.valueOf(temp[4]));
					cmMailListOut.setIsBlack(isBlack.longValue());
				}
				outList.add(cmMailListOut);
			}
		}
		Map map=new HashMap();
		map.put("item", outList);
		return map;
	}

}
