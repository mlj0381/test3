package com.wo56.common.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.wo56.business.common.vo.in.BooleanFieldComparator;
import com.wo56.business.common.vo.in.CompareField;
import com.wo56.business.common.vo.in.CompareFieldDiffResult;
import com.wo56.business.common.vo.in.DoubleFieldComparator;
import com.wo56.business.common.vo.in.FieldComparator;
import com.wo56.business.common.vo.in.FloatFieldComparator;
import com.wo56.business.common.vo.in.IntegerFieldComparator;
import com.wo56.business.common.vo.in.LongFieldComparator;
import com.wo56.business.common.vo.in.ShortFieldComparator;
import com.wo56.business.common.vo.in.StringFieldComparator;

/**
 * 对象字段值比较工具类
 * 
 * @author chenjun
 *
 */
public class ObjectFieldValueCompareUtil {

	// 所支持的比较域的类型列表
	private static final List<FieldComparator> supportedCompareFields = new ArrayList<FieldComparator>();
	
	static {
		supportedCompareFields.add(new StringFieldComparator());
		supportedCompareFields.add(new LongFieldComparator());
		supportedCompareFields.add(new IntegerFieldComparator());
		supportedCompareFields.add(new DoubleFieldComparator());
		supportedCompareFields.add(new BooleanFieldComparator());
		supportedCompareFields.add(new FloatFieldComparator());
		supportedCompareFields.add(new ShortFieldComparator());
	}

	/**
	 * 比较oldObject和newObject相关字段域(由fields参数决定)的值，返回域值不同的结果
	 * 
	 * @param oldObject
	 * @param newObject
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	public static List<CompareFieldDiffResult> compare(Object oldObject, Object newObject, List<CompareField> fields) throws Exception {
		if (null == oldObject && newObject == null) {
			return null;
		}
		if (!oldObject.getClass().toString().equals(newObject.getClass().toString())) {
			throw new Exception("比较的两个对象类型不一致");
		}
		if (fields == null || fields.size() == 0)
			return null;
		Class compareClass = oldObject.getClass();
		List<CompareFieldDiffResult> results = new ArrayList<CompareFieldDiffResult>();
		for (int i = 0; null != fields && i < fields.size(); i++) {
			CompareField field = fields.get(i);
			Method setMethod = compareClass.getMethod(getGetterMethodName(field.getFieldId()));
			String fieldClass = setMethod.getReturnType().getName();
			if (!isSupportedField(fieldClass)) {// 不支持此种类型的“域”比较
				continue;
			}
			Object fieldOldValue = setMethod.invoke(oldObject);
			Object fieldNewValue = setMethod.invoke(newObject);
			if (fieldOldValue == null && fieldNewValue == null) {// 都等于null，无须比较
				continue;
			}
			if(fieldOldValue instanceof Long || fieldNewValue instanceof Long){
				//Long 对象时
				//如果 null 改为 0
				//如果 0 改为 null
				if((fieldOldValue == null || ((Long) fieldOldValue == 0)) && (fieldNewValue == null || ((Long) fieldNewValue) == 0)){
					continue;
				}
			}
			
			if(fieldOldValue instanceof String || fieldNewValue instanceof String){
				//String 对象时
				//如果 null 改为 ""
				//如果"" 改为 null
				if((fieldOldValue == null || "".equals(fieldOldValue)) && (fieldNewValue == null || "".equals(fieldNewValue))){
					continue;
				}
			}
			
			
			// 判断是否相等
			if (fieldOldValue == null || fieldNewValue == null) {
				results.add(new CompareFieldDiffResult(field.getFieldId(), field.getFieldName(), 
						convertToStringWithTranslator(field, fieldOldValue), convertToString(fieldOldValue),
						convertToStringWithTranslator(field, fieldNewValue), convertToString(fieldNewValue)));
			} else {
				FieldComparator fieldCompare = getFieldCompare(fieldClass);
				if (!fieldCompare.compare(fieldOldValue, fieldNewValue)) {
					results.add(new CompareFieldDiffResult(field.getFieldId(), field.getFieldName(), 
							convertToStringWithTranslator(field, fieldOldValue), convertToString(fieldOldValue),
							convertToStringWithTranslator(field, fieldNewValue), convertToString(fieldNewValue)));
				}
			}
		}
		return results;
	}
	
	/**
	 * 转换域值为String类型
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	private static String convertToString(Object value) {
		if (null == value)
			return StringUtils.EMPTY;
		return String.valueOf(value);
	}
	
	/**
	 * 转换域值为String类型
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	private static String convertToStringWithTranslator(CompareField field, Object value) {
		if (null == value)
			return StringUtils.EMPTY;
		if (field.getTranslator() != null) {
			return field.getTranslator().translator(value);
		}
		return String.valueOf(value);
	}
	
	/**
	 * 获取字段域比较器
	 * @param fieldClass
	 * @return
	 */
	private static FieldComparator getFieldCompare(String fieldClass) {
		for (int i = 0; i < supportedCompareFields.size(); i++) {
			FieldComparator fieldCompare = supportedCompareFields.get(i);
			if (fieldCompare.getFileValueClassName().equals(fieldClass))
				return fieldCompare;
		}
		return null;
	}

	/**
	 * 判断这个“域”是否支持比较
	 * 
	 * @param fieldClass
	 * @return
	 */
	private static boolean isSupportedField(String fieldClass) {
		for (int i = 0; i < supportedCompareFields.size(); i++) {
			FieldComparator fieldCompare = supportedCompareFields.get(i);
			if (fieldCompare.getFileValueClassName().equals(fieldClass))
				return true;
		}
		return false;
	}

	/**
	 * 获取“域”对应的getter
	 * 
	 * @param field
	 * @return
	 */
	private static String getGetterMethodName(String field) {
		return "get" + convert2ClassFieldName(field);
	}

	private static String convert2ClassFieldName(String field) {
		String tmpName = field.trim();
		return tmpName.substring(0, 1).toUpperCase() + tmpName.substring(1);
	}
}
