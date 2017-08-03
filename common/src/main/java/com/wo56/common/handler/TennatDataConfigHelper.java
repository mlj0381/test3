package com.wo56.common.handler;

import java.util.ArrayList;
import java.util.List;

import com.wo56.common.parser.OrdImportBillParser;
import com.wo56.common.parser.TennatDataConfigParser;



public class TennatDataConfigHelper {
	
	private static List<AbstractTennatDataConfigHandler<?>> allHandler = new ArrayList<AbstractTennatDataConfigHandler<?>>();
	
	private static List<TennatDataConfigParser<?>> allParser=new ArrayList<TennatDataConfigParser<?>>();
	
	static {
		allParser.add(new OrdImportBillParser());
	}
	public static void addHandler(AbstractTennatDataConfigHandler<?>  handler){
		allHandler.add(handler);
	}
	
//	public static List<AbstractTennatDataConfigHandler<?>> getAllHandlers() {
//		
////		allHandler.add(new OrgInfoTennatDataConfigHandler());
////		allHandler.add(new EmployeeInfoTennatDataConfigHandler());
////		allHandler.add(new CmCarrierCompanyConfigHandler());
////		allHandler.add(new CmCustomerConfigHandler());
////		allHandler.add(new CmConsigneeCustomerConfigHandler());
//		//allHandler.add(new OrdImportBillDataConfigHandler());
//		
//		Collections.sort(allHandler, new Comparator<AbstractTennatDataConfigHandler<?>>() {// 排序
//			@Override
//			public int compare(AbstractTennatDataConfigHandler<?> handler1, AbstractTennatDataConfigHandler<?> handler2) {
//				return handler1.sort() - handler2.sort();
//			}
//		});
//		return allHandler;
//	}
	
	public static TennatDataConfigParser<?> getParser(String sheetName) {
		for (TennatDataConfigParser<?> parser : allParser) {
			if (null != parser.sheetName() && parser.sheetName().equals(sheetName)) {
				return parser;
			}
		}
		return null;
	}
	
	public static AbstractTennatDataConfigHandler<?> getHandler(String sheetName) {
		for (AbstractTennatDataConfigHandler<?> handler : allHandler) {
			if (null != handler.sheetName() && handler.sheetName().equals(sheetName)) {
				return handler;
			}
		}
		return null;
	}
	
	
	public static Object getHandlerDataContainer(List<AbstractTennatDataConfigHandler<?>> allHandlers, String businessName) {
        AbstractTennatDataConfigHandler<?> handler = getHandler( businessName);
		if (handler != null)
			return handler.getDataContainer();
		return null;
	}
}
