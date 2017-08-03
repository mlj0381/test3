package com.wo56.business;

import com.framework.core.inter.HttpFrameWork;

public class StartWebServer	 {

	public static void main(String[] args) throws Exception {
		String[] param = { "HTTP_WEB_01" };
		HttpFrameWork work = new HttpFrameWork();
		work.main(param);
	}
}