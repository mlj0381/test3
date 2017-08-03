package com.wo56.business.sys.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import redis.clients.jedis.JedisCommands;

import com.framework.components.fdfs.FastDFSHelper;
import com.framework.components.fdfs.impl.SysAttachFileBO;
import com.framework.components.fdfs.vo.SysAttach;
import com.framework.components.redis.RedisHelper;
import com.framework.components.redis.RemoteCacheUtil;
import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysCfg;
import com.framework.core.exception.BusinessException;
import com.framework.core.util.DateUtil;
import com.framework.core.util.Resource;
import com.framework.core.util.SysCfgUtil;
import com.wo56.business.ord.vo.OrdOrdersInfo;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.utils.GpsUtil;
import com.wo56.common.utils.QrCodeUtil;

public class SysBaseBusiSV {
	private static final Log log = LogFactory.getLog(SysBaseBusiSV.class);
    private static final int IMA_WIDTH = 350;  
    private static final int IMA_HEIGHT = 350;  
	
	/**
	 * 处理地理位置信息
	 * @param  billId    用户手机
	 * @param  nand      纬度
	 * @param  eand      经度
	 * @param  list      纬度集合
	 * @param  userType  用户类型
	 * @param  userId    用户ID
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void locationDeal(String billId,String latitude,String longitude, List list,Integer userType,Long userId ){
		JedisCommands jedis = RedisHelper.getInstance().getJedis();
		try {
			if(Double.parseDouble(longitude)>0 && Double.parseDouble(latitude)>0){
				Map reMap = new HashMap();
				String time = DateUtil.getCurrDateTime();
				reMap.put("billId", billId);
				reMap.put("longitude", longitude);
				reMap.put("latitude", latitude);
				reMap.put("time", time);
				//Long size = RemoteCacheUtil.rpush(EnumConsts.RemoteCache.UPLOAD_LATITUDE, JsonHelper.json(reMap));
				RemoteCacheUtil.set(EnumConsts.RemoteCache.BillId_Gps_Position+billId,latitude+"|"+longitude+"|||" +time);
				GpsUtil.setBillGpsPosition(billId,Double.parseDouble(longitude),Double.parseDouble(latitude));
				//log.info(">>>"+time+">>>"+billId+"上传位置信息["+size+"]："+ longitude +", "+latitude);
			}
		}catch (Exception e) {
			log.error("经纬度放入缓存出错！！！");
			throw new BusinessException(e.getMessage());
			
		}finally{
			if(jedis!=null){
				RedisHelper.getInstance().returnResource(jedis);
			}
		}
	}
}
