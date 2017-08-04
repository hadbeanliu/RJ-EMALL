package com.rongji.cms.emall.modules.log;

//import com.rongji.cms.commons.ObjectTypes;

/**
 * <h3>操作日志记录接口</h3>
 * <p>继承该接口可实现操作日志的记录</p>
 * @author jayfans
 *
 */
public interface AuditLogDetail {
	/**
	 * 获取对象类型
	 * @return
	 */
//	public ObjectTypes getObjectType();
	
	/**
	 * 获取日志对象的主键
	 * @return
	 */
	public String getLogObjectId();
	
	/**
	 * 获取对象名称
	 * @return
	 */
	public String getLogObjectName();
	
	/**
	 * 根据字段名与字段值，获取字段值的真实值
	 * <p> 一般就是返回value本身，除非该字段值是一个对象，可能要转为对象ID作为字段的真实值，或者进行数据格式化</p>
	 * <p>如:cmsArticle对象 转为 arId</p>
	 * <p>如:Date对象 时间格式化输出显示</p>
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public String getValueValue(String propertyName,Object value);
	
	/**
	 * 根据字段名与字段值，获取字段值的可读性字符串
	 * <p>如:字符串的是否：根据"1"获取"是"</p>
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public String getValueString(String propertyName,Object value);
	
	/**
	 * 根据字段名，获取字段的显示名称
	 * @param paramName
	 * @return
	 */
	public String getFieldName(String propertyName);
	
	
}
