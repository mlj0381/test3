package com.wo56.business.task;

import com.framework.core.scheduler.TaskFrameWork;

public class StartTaskFrame {

	public static void main(String[] args) throws Exception {

		String[] param = { "SCAN", "DEAL_TIME_ORDER" };
		TaskFrameWork.getInstance().main(param);
	}
}
