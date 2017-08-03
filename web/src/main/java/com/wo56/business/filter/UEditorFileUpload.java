package com.wo56.business.filter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.components.fdfs.FastDFSHelper;
import com.framework.components.fdfs.impl.SysAttachFileBO;
import com.framework.components.fdfs.vo.SysAttach;
import com.framework.core.SysContexts;
import com.framework.core.inter.impl.BaseHttpServlet;

public class UEditorFileUpload extends BaseHttpServlet {
	private static final long serialVersionUID = -9070887913845279839L;
	private static transient Log logger = LogFactory.getLog(UEditorFileUpload.class);

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String responseText = StringUtils.EMPTY;
		if ("config".equals(action)) {// 初始化ueditor时，需要加载配置返回给前端，然后前端会根据此配置了初始化相关的组件(图片、文件上传等组件)
			responseText = getAllConfig();
		} else{
			try {
				responseText = uploadFileToFtpServerNew(request, getActionTempDir(action));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		response.getOutputStream().write(responseText.getBytes());
	}
	
	private String uploadFileToFtpServerNew(HttpServletRequest request, String action) throws Exception {
		SysAttachFileBO sysAttachFile = (SysAttachFileBO) SysContexts.getBean("attach");
		JSONObject ret = new JSONObject();
		FastDFSHelper client = FastDFSHelper.getInstance();
		ServletFileUpload upload = new ServletFileUpload();
		FileItemIterator iterator = upload.getItemIterator(request);
		while (iterator.hasNext()) {
			FileItemStream item = iterator.next();
			if ("upfile".equals(item.getFieldName())) {// 目前写死，后续根据action获取
				InputStream stream = item.openStream();
				// 先不处理校验的问题
				if (stream.available() != 0) {// 如果文件域没有选择文件，则忽略处理
					String originalFileName = item.getName(); // 得到上传的文件名称
					String destFileName = String.valueOf(System.currentTimeMillis());
					String suffix = StringUtils.EMPTY;
					if (logger.isInfoEnabled()) {
						logger.info(">>>>>>>>>>>>上传的文件类型为>>>>>>>>>>>>>" + suffix);
					}
					if(originalFileName.lastIndexOf(".") >= 0){
						suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
						destFileName = destFileName + suffix;
					}
					SysAttach sysAttach = sysAttachFile.doUpload(stream, destFileName, stream.available());
					ret.put("original", originalFileName);
					ret.put("name", destFileName);
					ret.put("url", client.getHttpURL(sysAttach.getStorePath(), true));
					ret.put("size", stream.available());
					ret.put("type", suffix);
					ret.put("state", "SUCCESS");
				}
			}
		}
		return ret.toString();
	}
	
	
	private String getActionTempDir(String action) {
		String localTempDir = System.getProperty("java.io.tmpdir");// 本地临时目录
		if (!localTempDir.endsWith(File.separator)) {
			localTempDir += File.separator;
		}
		String parentLocalTempDir = localTempDir + "ueditor" + File.separator;
		File parentFile = new File(parentLocalTempDir);
		if(!parentFile.exists()){
			parentFile.mkdir();
		}
		String actionLocalTempDir = parentLocalTempDir + action + File.separator;
		File actionFile = new File(actionLocalTempDir);
		if (!actionFile.exists()) {
			actionFile.mkdir();
		}
		return actionLocalTempDir; 
	}
	
	private String getAllConfig(){// 目前写死，后续弄成可配置
		JSONObject ret = new JSONObject();
		// 图片上传模块
		ret.put("imageActionName", "uploadimage");
		ret.put("imageFieldName", "upfile");
		ret.put("imageMaxSize", "2048000");
		ret.put("imageAllowFiles", new String[]{".png", ".jpg", ".jpeg", ".gif", ".bmp"});
		ret.put("imageCompressEnable", true);
		ret.put("imageCompressBorder", 1600);
		ret.put("imageInsertAlign", "none");
		ret.put("imageUrlPrefix", "");
		ret.put("imagePathFormat", "/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}");
		
		// 涂鸦模块
		ret.put("scrawlActionName", "uploadscrawl");
		ret.put("scrawlFieldName", "upfile");
		ret.put("scrawlPathFormat", "/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}");
		ret.put("scrawlMaxSize", "2048000");
		ret.put("scrawlUrlPrefix", "");
		ret.put("scrawlInsertAlign", "none");
		
		/* 截图工具上传 */
		ret.put("snapscreenActionName", "uploadimage");
		ret.put("snapscreenPathFormat", "/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}");
		ret.put("snapscreenUrlPrefix", "");
		ret.put("snapscreenInsertAlign", "none");

		/* 上传视频配置 */
		ret.put("videoActionName", "uploadvideo");
		ret.put("videoFieldName", "upfile");
		ret.put("videoPathFormat", "/ueditor/jsp/upload/video/{yyyy}{mm}{dd}/{time}{rand:6}");
		ret.put("videoUrlPrefix", "");
		ret.put("videoMaxSize", 102400000);
		ret.put("videoAllowFiles", new String[]{".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg", ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid"});

	    /* 上传文件配置 */
		ret.put("fileActionName", "uploadfile");
		ret.put("fileFieldName", "upfile");
		ret.put("filePathFormat", "/ueditor/jsp/upload/file/{yyyy}{mm}{dd}/{time}{rand:6}");
		ret.put("fileUrlPrefix", "");
		ret.put("fileMaxSize", 51200000);
		ret.put("fileAllowFiles", new String[] { ".png", ".jpg", ".jpeg", ".gif", ".bmp", ".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb",
			".mpeg", ".mpg", ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid", ".rar", ".zip", ".tar", ".gz",
			".7z", ".bz2", ".cab", ".iso", ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf", ".txt", ".md", ".xml" });
		return ret.toString();
	}
}
