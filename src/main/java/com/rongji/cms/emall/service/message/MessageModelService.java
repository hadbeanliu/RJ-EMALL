package com.rongji.cms.emall.service.message;

import java.util.List;
import java.util.Map;

import com.rongji.cms.emall.entity.EmlMessageModel;
import com.rongji.cms.emall.vo.MessageModel;
import com.rongji.rjskeleton.service.CommonService;

public interface MessageModelService extends CommonService<MessageModel, EmlMessageModel> {
	/**
	 * 卖家发送消息模板
	 * @param sign 消息模板标识
	 * @param receiverId 接收者ID
	 * @param data Map<String,String>
	 * @param senderId 发送者ID
	 * @return true发送成功 false发送失败
	 */
	public boolean sendMessge(String sign, String receiverId,Map<String, String> data, String senderId);

	/**
	 * 卖家发送消息模板
	 * @param sign 消息模板标识
	 * @param receiverId 接收者ID
	 * @param data Map<String,String>
	 * @param senderId 发送者ID
	 * @return true发送成功 false发送失败
	 */
	public boolean sellerSendMessge(String sign, String receiverId,Map<String, String> data, String senderId);
	/**
	 * 替换{}占位符中的字符串
	 * @param content 模板内容
	 * @param data<key,value> key模板内容的占位符，value替换的值
	 * @return
	 */
	public String replaceContent(String content, Map<String, String> data);
	
	/**
	 * 编辑内容发送短信
	 * @param content 发送内容
	 * @param phone 手机号码
	 * @return 返回"00" 短信发送成功 否则失败
	 */
	public String sendSms(String content,String phone);
	
	/**
	 * 根据标识获取消息模板
	 * @param sign 消息模板标识
	 * @return MessageModel 
	 */
	public MessageModel getMessageModelBySign(String sign);
	
	/**
	 * 编辑内容发送邮件
	 * @param content 邮件正文内容
	 * @param title 邮件标题
	 * @param to 接收方邮箱
	 * @param from 发送方邮箱 默认为null
	 * @return 返回true 邮件发送成功 false失败
	 */
	public boolean sendEmail(String from,String to,String title,String content);
	
	/**
	 * 根据标识获取消息模板
	 * @param sign 消息模板标识
	 * @return MessageModel 
	 */
	public List<MessageModel> getMessageModelByRecevierType(String recevierType);

}
