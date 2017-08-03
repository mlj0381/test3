package com.wo56.business;

import com.framework.core.inter.HttpFrameWork;

public class StartSrvServer	 {

	public static void main(String[] args) throws Exception {
		String[] param = { "HTTP_APP_01" };
		HttpFrameWork work = new HttpFrameWork();
		work.main(param);
	}
}