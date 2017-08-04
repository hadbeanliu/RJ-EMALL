package com.rongji.cms.emall.service.message;

import java.util.List;

import com.rongji.cms.emall.entity.EmlMessagePersonModel;
import com.rongji.cms.emall.vo.MessagePersonModel;
import com.rongji.rjskeleton.service.CommonService;

public interface MessagePersonModelService extends CommonService<MessagePersonModel, EmlMessagePersonModel>{
	
	/**
	 * 获取用户的消息模板列表
	 * @param userId
	 * @return
	 */
	public List<MessagePersonModel> getMessageModelByReceiverType(String userId);
	/**
	 * 根据标识判断用户短信消息模板是否启用
	 * @param sign 消息模板标识
	 * @return true 启用 false不启用 
	 */
	public boolean isActiveSmsBySign(String sign, String userId);
	/**
	 * 根据标识判断用户邮件消息模板是否启用
	 * @param sign 消息模板标识
	 * @return true 启用 false不启用 
	 */
	public boolean isActiveEmailBySign(String sign, String userId);
	/**
	 * 根据标识判断用户站内信消息模板是否启用
	 * @param sign 消息模板标识
	 * @return true 启用 false不启用 
	 */
	public boolean isActiveInMsgBySign(String sign, String userId);

}
