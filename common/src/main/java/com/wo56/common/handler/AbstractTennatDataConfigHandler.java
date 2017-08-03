package com.wo56.common.handler;

import java.util.List;
import java.util.Map;


public abstract class AbstractTennatDataConfigHandler<E> {
//	private TennatDataConfigParser<E> parser;
	private Object dataContainer;

	public AbstractTennatDataConfigHandler() {
	}

//	public AbstractTennatDataConfigHandler(TennatDataConfigParser<E> parser) {
//		this.parser = parser;
//	}

	/**
	 * 处理数据(如果需要入库，也是在这里进行)
	 * 
	 * @param configDats: BO层传给TF层的数据
	 */
//	public void dealConfigData(JSONArray configDatas, List<AbstractTennatDataConfigHandler<?>> allHandler) {
////		List<E> vos = parser.parseHttpRequestDataToVo(configDatas);
////		doDealConfigData(vos, allHandler);
//	}
	
	/**
	 * 将vo数据交给各自的handler处理 如果此方法处理后的数据需要给后续用，需要将数据设置到dataContainer字段。
	 * 例如，保存网点数据之后，需要给后续的handler用，则可以设置到dataContainer中，后续的handler就可以使用对应网点的orgId
	 * @param vos
	 */
	public abstract Map<String,Object> doDealConfigData(List<E> vos); 

	/**
	 * 数据配置的名称，对应excel中sheet的名字
	 * 
	 * @return
	 */
	public abstract String sheetName();

	/**
	 * 如果有多个handler，系统会根据这个方法返回的值进行排序
	 * 
	 * @return
	 */
	public abstract int sort();

//	public TennatDataConfigParser<E> getParser() {
//		return parser;
//	}
//
//	public void setParser(TennatDataConfigParser<E> parser) {
//		this.parser = parser;
//	}

	public Object getDataContainer() {
		return dataContainer;
	}

	public void setDataContainer(Object dataContainer) {
		this.dataContainer = dataContainer;
	}
}
