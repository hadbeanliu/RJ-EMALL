package com.rongji.cms.emall.service.message.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.message.MessagePersonModelDao;
import com.rongji.cms.emall.entity.EmlMessageModel;
import com.rongji.cms.emall.entity.EmlMessagePersonModel;
import com.rongji.cms.emall.service.message.MessageModelService;
import com.rongji.cms.emall.service.message.MessagePersonModelService;
import com.rongji.cms.emall.vo.MessageModel;
import com.rongji.cms.emall.vo.MessagePersonModel;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;

@Service
public class MessagePersonModelServiceImpl extends CommonServiceImpl<MessagePersonModel, EmlMessagePersonModel, MessagePersonModelDao> implements MessagePersonModelService{

	@Autowired
	private MessageModelService messageModelService;
	
	@Override
	protected MessagePersonModel createVo() {
		return new MessagePersonModel();
	}

	@Override
	protected EmlMessagePersonModel createEntity() {
		return new EmlMessagePersonModel();
	}
	
	
	@Override
	public MessagePersonModel convertToVo(EmlMessagePersonModel entity) {
		MessagePersonModel vo = createVo();
		ConvertUtil.copyPropertiesByIgnore(entity, vo, true, "messageModel");
		MessageModel messageModel = messageModelService.convertToVo(entity.getMessageModel());
		vo.setMessageModel(messageModel);
		return vo;
	}
	
	@Override
	public EmlMessagePersonModel convertToEntity(MessagePersonModel vo) {
		EmlMessagePersonModel entity = createEntity();
		ConvertUtil.copyPropertiesByIgnore(vo, entity, true, "messageModel");
		EmlMessageModel messageModel = messageModelService.convertToEntity(vo.getMessageModel());
		entity.setMessageModel(messageModel);
		return entity;
	}
	
	@Override
	public List<MessagePersonModel> getMessageModelByReceiverType(String userId) {
		return convertToVos(getDao().getMessageModelByReceiverType(userId));
	}

	@Override
	public boolean isActiveSmsBySign(String sign, String userId) {
		EmlMessagePersonModel entity = getDao().getActiveBySign(sign, userId);
		if(entity!=null&&"1".equals(entity.getSmsStatus())){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean isActiveEmailBySign(String sign, String userId) {
		EmlMessagePersonModel entity = getDao().getActiveBySign(sign, userId);
		if(entity!=null&&"1".equals(entity.getEmailStatus())){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean isActiveInMsgBySign(String sign, String userId) {
		EmlMessagePersonModel entity = getDao().getActiveBySign(sign, userId);
		if(entity!=null&&"1".equals(entity.getInMsgStatus())){
			return true;
		}else{
			return false;
		}
	}

}
