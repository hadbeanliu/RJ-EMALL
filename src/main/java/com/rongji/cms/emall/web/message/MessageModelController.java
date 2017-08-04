package com.rongji.cms.emall.web.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongji.cms.emall.entity.EmlMessageModel_;
import com.rongji.cms.emall.service.message.MessageModelService;
import com.rongji.cms.emall.vo.MessageModel;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;

@Controller
@RequestMapping("/admin/message/model")
public class MessageModelController extends CrudAndPageByJsonController<MessageModel> {

	@Autowired
	private MessageModelService messageModelService;
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("消息模板管理界面")
	public String index(){
		return "dsww/001/admin-message-model-list";
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@Override
	protected CommonService<MessageModel, ?> getService() {
		return messageModelService;
	}
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setIdColumn(EmlMessageModel_.id);
	}
	@Override
	protected MessageModel doSaveVo(MessageModel vo) {
		vo.setModelSign("1");
		return super.doSaveVo(vo);
	}

	
}
