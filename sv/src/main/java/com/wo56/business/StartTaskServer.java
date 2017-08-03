package com.wo56.business;

import com.framework.core.scheduler.TaskFrameWork;

public class StartTaskServer	 {

	public static void main(String[] args) throws Exception {
		String param[] = {"SCAN", "ALL"};
		TaskFrameWork work = TaskFrameWork.getInstance();
		work.main(param);
	}
}