package com.wo56.business.demo;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.core.base.BaseBO;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.NoExceptionSvCallBackImpl;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.system.vo.in.LoginParam;
import com.wo56.business.system.vo.out.LoginParamOut;

public class DemoBO extends BaseBO {
	
	/**
	 * 输出JSON串，直接返回到前台
	 * @return
	 * @throws Exception
	 */
	public String getInterJson() throws Exception {
		LoginParam param = new LoginParam();
		param.setLoginAcct("admin");
		param.setPwd("A");
		//直接将后台的JSON串输出到前台
		String out = CallerProxy.call(param);
		return out;
	}

	/**
	 * 输出反序列化后的对象（简单对象），可以获取对象属性进行判断
	 * @return
	 * @throws Exception
	 */
	public String getInterWithCondition() throws Exception {
		LoginParam param = new LoginParam();
		param.setLoginAcct("admin");
		param.setPwd("B");
		SimpleOutParamVO<BaseUser> out = CallerProxy.call(param, new TypeToken<SimpleOutParamVO<BaseUser>>(){}.getType());
		if (out.getContent() != null && out.getContent().getTelphone().equals("13800138000")) {
			return out.getContent().toString();
		}
		return out.toString();
	}
	
	/**
	 * 输出反序列化后的对象（简单对象），可以获取对象属性进行判断， SV有业务异常，WEB不抛异常，特殊处理
	 * @return
	 * @throws Exception
	 */
	public String getInterWithOutException() throws Exception {
		LoginParam param = new LoginParam();
		param.setLoginAcct("admin");
		param.setPwd("B");
		//特殊情况，如：应用服务SV层抛异常了，但前台不抛异常，需要继续处理前台逻辑的情景; 或者根据不同业务错误类型进行不同业务处理的情景
		LoginParamOut out = CallerProxy.call(param, LoginParamOut.class, new NoExceptionSvCallBackImpl());
		if (out.getErrorCode().equals("10001")) {
			System.out.println("业务层出错了，但前台不抛异常，根据业务错误特殊处理");
			//do A
		} else if (out.getErrorCode().equals("10002")) {
			//do B
		}
		//可以对返回对象做一些判断处理，调多个接口的情况
		return null;
	}
	
	/**
	 * 输出反序列化后的对象（列表对象），可以获取对象属性进行判断
	 * @return
	 * @throws Exception
	 */
	public String getInterWithList() throws Exception {
		LoginParam param = new LoginParam();
		param.setLoginAcct("admin");
		param.setPwd("List");
		//直接将后台的JSON串输出到前台
		ListOutParamVO<BaseUser> out = CallerProxy.call(param, new TypeToken<ListOutParamVO<BaseUser>>(){}.getType());
		List<BaseUser> list = out.getContent();
		if (list != null) {
			for (BaseUser baseUser : list) {
				//再调其它接口获取其它信息
				System.out.println(baseUser.getUserName());
			}
		}
		return "";
	}
	
	public String getInterWithMap() throws Exception {
		LoginParam param = new LoginParam();
		param.setLoginAcct("admin");
		param.setPwd("map");
		//直接将后台的JSON串输出到前台
		SimpleOutParamVO<Map> out = CallerProxy.call(param, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		Map map = out.getContent();
		System.out.println(map.get("test"));
		return "";
	}
	
	/**
	 * 输出反序列化后的对象（分页列表对象），可以获取对象属性进行判断
	 * @return
	 * @throws Exception
	 */
	public String getInterWithPage() throws Exception {
		LoginParam param = new LoginParam();
		param.setLoginAcct("admin");
		param.setPwd("page");
		PageOutParamVO<SysStaticData> out = CallerProxy.call(param, new TypeToken<PageOutParamVO<SysStaticData>>(){}.getType());
		System.out.println(out.getContent().getCount());
		for (SysStaticData data : out.getContent().getItems()) {
			System.out.println(data.getCodeName());
		}
		return "ok";
	}

	private static final Log log = LogFactory.getLog(DemoBO.class);

	public static void main(String[] args) throws Exception {
		DemoBO bo = new DemoBO();
		System.out.println(bo.getInterWithMap());
//			System.out.println(bo.getInterJson());
//			System.out.println(bo.getInterWithList());
//			System.out.println(bo.getInterWithPage());
//			System.out.println(bo.getInterWithOutException());
//			System.out.println(bo.getInterWithCondition());
	}
}
