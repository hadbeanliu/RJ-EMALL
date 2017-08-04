package com.rongji.cms.emall.modules.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 泛型值 
 * @author jayfans
 *
 * @param <T>
 */
class Value<T>{
	private T value = null;
	public Value(T value){
		this.value = value;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
}

/**
 * 日志解析内容类 4 gson
 * @author jayfans
 * 
 */
public class LogParsedContent {
	
	
	public static void main(String[] args) {
		
	}
	
	//解析成功
	public static String PARSER_FLAG_SUCCESS = "0";
	//解析失败
	public static String PARSER_FLAG_FAILURE = "1";
	//其它
	public static String PARSER_FLAG_UNKNOWN = "2";
	
	//解析标志
	private String parserFlag = PARSER_FLAG_FAILURE;
	//默认日志内容
	private String logDefaultContent;
	//自定义数据
	private Map<String,String> customFields = null;
	
	public LogParsedContent(){
		customFields = new HashMap<String,String>();
	}
	
	/**
	 * 设置自定义字段值
	 * @param key
	 * @param value
	 * @return key之前设置的旧值
	 */
	public String setCustomField(String key,String value){
		return customFields.put(key, value);
	}
	
	/**
	 * 获取自定义字段
	 * @param key
	 * @return 返回key所对应的值
	 */
	public String getCustomField(String key){
		return customFields.get(key);
	}
	
	/**
	 * 判断是否存在自定义字段
	 * @param key
	 * @return boolean 
	 */
	public boolean hasCustomField(String key){
		return customFields.containsKey(key);
	}
	
	/**
	 * 获取解析标志
	 * @return PARSER_FLAG
	 */
	public String getParserFlag() {
		return parserFlag;
	}

	/**
	 * 设置解析标志
	 * @param parserFlag
	 */
	public void setParserFlag(String parserFlag) {
		this.parserFlag = parserFlag;
	}
	
	/**
	 * 获取默认日志内容
	 * @return
	 */
	public String getLogDefaultContent() {
		return logDefaultContent;
	}

	/**
	 * 设置默认日志内容 
	 * @param logDefaultContent
	 */
	public void setLogDefaultContent(String logDefaultContent) {
		this.logDefaultContent = logDefaultContent;
	}

	public Map<String, String> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(Map<String, String> customFields) {
		this.customFields = customFields;
	}
}
