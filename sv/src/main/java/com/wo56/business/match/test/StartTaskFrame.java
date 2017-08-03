package com.wo56.business.match.test;

import com.framework.core.scheduler.TaskFrameWork;

public class StartTaskFrame {

	public static void main(String[] args) throws Exception {

		String[] param = { "SCAN", "TIME_OUT" };
		TaskFrameWork.getInstance().main(param);
	}
}
