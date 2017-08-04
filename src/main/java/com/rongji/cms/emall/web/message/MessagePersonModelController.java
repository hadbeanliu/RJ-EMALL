package com.rongji.cms.emall.web.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongji.cms.emall.service.message.MessageModelService;
import com.rongji.cms.emall.service.message.MessagePersonModelService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.vo.MessageModel;
import com.rongji.cms.emall.vo.MessagePersonModel;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.BaseController;

@Controller
@RequestMapping("/seller/message/model")
public class MessagePersonModelController extends BaseController {
	
	@Autowired
	private MessagePersonModelService messagePersonModelService;
	
	@Autowired
	private MessageModelService messageModelService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("卖家消息模板管理界面")
	public String index(Model model){
		
		return "dsww/001/seller-message-model";
	}
	
	@RequestMapping(value="/info")
	@ResponseBody
	public List<MessagePersonModel> getPersonMessageModel(){
		String userId = getCurrUserId();
		List<MessagePersonModel> messagePersonModel = messagePersonModelService.getMessageModelByReceiverType(userId);
		List<MessageModel> messageModel = messageModelService.getMessageModelByRecevierType("2");
		if(messagePersonModel.size()==0){
			boolean isseller = userService.isSeller(userId);
			if(isseller){
				if(messageModel!=null){
					MessagePersonModel sellerMessage = new MessagePersonModel();
					for(MessageModel entity : messageModel){
						sellerMessage.setEmailStatus("0");
						sellerMessage.setInMsgStatus("0");
						sellerMessage.setSmsStatus("0");
						sellerMessage.setMessageModel(entity);
						sellerMessage.setUserId(userId);
						messagePersonModelService.save(sellerMessage);
					}
					messagePersonModel = messagePersonModelService.getMessageModelByReceiverType(userId);
				}
			}
		}else if(messagePersonModel.size()<messageModel.size()){
			boolean isExit=false;
			for(MessageModel messageModelVo : messageModel){
				for(MessagePersonModel messagePersonModelVo : messagePersonModel){
					if(messagePersonModelVo.getMessageModel().getId().equals(messageModelVo.getId())){
						isExit=false;
						break;
					}else{
						isExit=true;
					}
				}
				if(isExit){
					MessagePersonModel saveMessagePersonModel = new MessagePersonModel();
					saveMessagePersonModel.setEmailStatus("0");
					saveMessagePersonModel.setInMsgStatus("0");
					saveMessagePersonModel.setSmsStatus("0");
					saveMessagePersonModel.setUserId(userId);
					saveMessagePersonModel.setMessageModel(messageModelVo);
					messagePersonModelService.save(saveMessagePersonModel);
				}
			}
			messagePersonModel = messagePersonModelService.getMessageModelByReceiverType(userId);
		}
		return messagePersonModel;
	}
	
	@RequestMapping(value="/saveChoice")
	public String savePersonChoice(){
		String userId = getCurrUserId();
		List<MessagePersonModel> messagePersonModel = messagePersonModelService.getMessageModelByReceiverType(userId);
		String[] smsResult = getRequest().getParameterValues("smsList");
		String[] emailResult = getRequest().getParameterValues("emailList");
		//String[] inMsgResult = getRequest().getParameterValues("inMsgList");
		if(smsResult!=null&&emailResult!=null){
			for(MessagePersonModel vo : messagePersonModel){
			     for(int i=0;i<smsResult.length;i++){
			    	    if(vo.getId().equals(smsResult[i])){
			    	    	vo.setSmsStatus("1");
			    	    	messagePersonModelService.save(vo);
			    	    	break;
			    	    }
			    	    if(i==smsResult.length-1){
							vo.setSmsStatus("0");
							messagePersonModelService.save(vo);
						}
				}
			     for(int i=0;i<emailResult.length;i++){
			    	    if(vo.getId().equals(emailResult[i])){
			    	    	vo.setEmailStatus("1");
			    	    	messagePersonModelService.save(vo);
			    	    	break;
			    	    }
			    	    if(i==emailResult.length-1){
							vo.setEmailStatus("0");
							messagePersonModelService.save(vo);
						}
				}
			}
			
		}else{
			for(MessagePersonModel vo : messagePersonModel){
				vo.setEmailStatus("0");
				vo.setSmsStatus("0");
				messagePersonModelService.save(vo);
			}
		}
		return redirectTo("/seller/message/model");
	}
 
}
