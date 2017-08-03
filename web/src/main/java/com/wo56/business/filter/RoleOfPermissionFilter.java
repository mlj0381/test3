package com.wo56.business.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.core.SysConst;
import com.framework.core.SysContexts;
import com.framework.core.cache.CacheFactory;
import com.framework.core.cache.impl.SysUrlDataCache;
import com.framework.core.exception.AuthException;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.interceptor.proxy.XssHttpServletRequestWrapper;
import com.framework.core.interceptor.vo.SysUrl;
import com.wo56.business.sys.vo.SysRoleGrant;
import com.wo56.common.cache.SysRoleGrantCache;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.PermissionConsts;

public class RoleOfPermissionFilter implements Filter{

	private static Log log = LogFactory.getLog(RoleOfPermissionFilter.class);
	private final Set<String> toFilterURI = new HashSet<String>();
	private final Map<String,Long> filterMap = new HashMap<String,Long>(); //所有需要登陆的url和entity Map
	
	@Override
	public void destroy() {
		log.info("------------RoleOfPermission is destroy--------------");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
		String uri = httpRequest.getRequestURI();
		int endIndex = uri.indexOf(".ajax");
		boolean isAjaxRequest = false;
		if (endIndex > 0) {
			isAjaxRequest = true;
		}
		String fullUrl = uri;
		if (StringUtils.isNotEmpty(httpRequest.getContextPath())) {
			int len = httpRequest.getContextPath().length();
			uri = uri.substring(len);
			fullUrl = fullUrl.substring(len);
		}
		// 解决使用request.getParameter后不能使用读取流的问题
		Map<String, String> map = new HashMap<String, String>();
		String qs = httpRequest.getQueryString();
		if (StringUtils.isNotEmpty(qs)) {
			fullUrl = fullUrl.concat("?").concat(qs);
			String[] sArray = qs.split("&");
			for (String string : sArray) {
				String[] pair = string.split("=");
				if (pair.length > 1) {
					map.put(pair[0], pair[1]);
				} else {
					map.put(pair[0], "");
				}
			}
		}
		if(matchFilterUrl(fullUrl)){
			if(isAjaxRequest){
				HttpSession session = httpRequest.getSession(false);
				if (session == null || session.getAttribute(SysConst.SYS_CURRENT_USER) == null) {
					throw new BusinessException("请登陆以后再尝试请求！");
				}else{
					boolean hasPermission = false;
					try {
						hasPermission = isMatchEntity(fullUrl, session);
					} catch (Exception e) {
						log.error(e);
						throw new BusinessException("AJAX权限处理异常",e);
					}
					if(!hasPermission){
						throw new BusinessException("您没有权限使用该功能!");
					}
				}
			}
		}
		chain.doFilter(request, response);
		return;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		log.info("------------RoleOfPermission is start--------------");
		List<SysUrl> list = (List<SysUrl>) CacheFactory.get(SysUrlDataCache.class.getName(), "SYS_URL");
		if (list != null) {
			for (SysUrl sysUrl : list) {
				//替换成正则表达式
				String url = sysUrl.getUrlPath();
				if (url.contains("*")) {
					url = url.replaceAll("\\?", "\\\\?");
					url = url.replaceAll("\\*", ".*?");
				}
				toFilterURI.add(url);
				if(log.isDebugEnabled()){
					if(sysUrl.getEntityId()>0){
						log.debug("需要拦截的url地址为：["+url+"],对应的entityId为：["+sysUrl.getEntityId()+"]");
					}
				}
				filterMap.put(url, sysUrl.getEntityId());
			}
		}
	}
	
	/**
	 * 匹配需要过滤的URL
	 * @param url
	 * @return　[true]过滤; [false]不过滤
	 */
	private boolean matchFilterUrl(String url) {
		for (String s : toFilterURI) {
			String regExp = "^" +s;
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(url);
			if (m.find())
				return true;
		}
		return false;
	}
	
	private boolean isMatchEntity(String url,HttpSession session) throws Exception{
		Set<Integer> set = entityList(session);
		if(set.size()<=0){
			log.error("缓存加载错误，未加载到用户的entity缓存");
		}
	 	Iterator it = filterMap.entrySet().iterator();
		while(it.hasNext()){
	       	Map.Entry entry = (Map.Entry) it.next();
	        String key = (String) entry.getKey();
	      	Long value = (Long) entry.getValue();
	      	
	       	String regExp = "^" +key;
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(url);
			if (m.find()){
				if(value == 0 || (value != 0 && set.contains(Integer.parseInt(value+"")))){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取用户的所有权限entity
	 * @param session
	 * @return List
	 * @throws Exception 
	 */
	private Set<Integer> entityList(HttpSession session) throws Exception{
		Set<Integer> set = new HashSet<Integer>();
		
		BaseUser baseUser= SysContexts.getCurrentOperator();
		
		if(baseUser==null){
			throw new AuthException("登录超时！");
		}
		List<Integer> roles =(List<Integer>)baseUser.getAttrs().get(EnumConsts.Common.SESSION_ROLES);
		
		if(roles==null || roles.size()<1){
			return null;
		}
		for(Integer role:roles){
			List<SysRoleGrant> roleGrants=(List<SysRoleGrant>)CacheFactory.get(SysRoleGrantCache.class, PermissionConsts.GrantConstant.SYS_ROLE_GRANT_CACHE_KEY+role);
			if(roleGrants!=null && roleGrants.size()>0){
				for(SysRoleGrant grant:roleGrants){
					set.add(grant.getEntityId());
				}
			}
		}
		
		return set;
	}
}
