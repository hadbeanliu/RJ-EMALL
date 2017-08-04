package com.rongji.cms.emall.service.message.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.transaction.Transactional;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import com.rongji.cms.emall.dao.message.MessageModelDao;
import com.rongji.cms.emall.entity.EmlMessageModel;
import com.rongji.cms.emall.service.message.MessageModelService;
import com.rongji.cms.emall.service.message.MessagePersonModelService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.support.EmallConfig;
import com.rongji.cms.emall.vo.MessageModel;
import com.rongji.cms.emall.vo.User;
import com.rongji.dfish.base.Utils;
import com.rongji.rjskeleton.service.CommonServiceImpl;

@Service
public class MessageModelServiceImpl extends CommonServiceImpl<MessageModel, EmlMessageModel, MessageModelDao> implements MessageModelService {

	static String url = "http://192.168.14.202:8980/message/index.sp?act=";
	
	@Autowired
	private JavaMailSender mailSender;
		
	@Autowired
	private MessagePersonModelService messagePersonModelService;
	
	@Autowired
	private UserService userService;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	protected EmlMessageModel createEntity() {
		return new EmlMessageModel();
	}

	@Override
	protected MessageModel createVo() {
		return new MessageModel();
	}

	@Override
	@Transactional
	public <S extends MessageModel> S save(S vo) {
		vo.setCreateTime(new Date());
		vo.setCreateUser("00000001");
		return super.save(vo);
	}

	public boolean sellerSendMessge(String sign, String receiverId, Map<String, String> data, String senderId) {
		boolean smsResult = false;
		boolean emailResult = false;
		boolean inMSGResult = false;
		User user = userService.getUserById(receiverId);
		MessageModel messageModel = getMessageModelBySign(sign);
		String contents = this.replaceContent(messageModel.getContent(), data);
		String title = this.replaceContent(messageModel.getTitle(), data);
		if(messagePersonModelService.isActiveSmsBySign(sign,senderId)&&user.getMobiletel()!=null&&!"".equals(user.getMobiletel())){
			String result = this.sendSms(contents, user.getMobiletel());
			if("00".equals(result)){
				smsResult = true;
			}
		}
		if(messagePersonModelService.isActiveEmailBySign(sign, senderId)&&user.getEmail()!=null&&!"".equals(user.getEmail())){
			emailResult = this.sendEmail(null, user.getEmail(), title, contents);
		}
		if(smsResult || emailResult || inMSGResult){
			return true;
		}else{
			return false;
		}
		
	}
	
	public String replaceContent(String content, Map<String, String> data) {
		Matcher m = Pattern.compile("\\{(.+?)\\}").matcher(content);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {  
            String name = m.group(1);//键名  
            String value = (String) data.get(name);//键值  
            if (value == null) {  
                value = "";  
            } 
            m.appendReplacement(sb, value);  
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public String sendSms(String content, String phone) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String reslut="";
		try {  
			content= content.replaceAll(" ", "%20");
            // 创建httpget.    
            HttpGet httpget = new HttpGet(url+"sendMes&phone="+phone+"&content="+content);  
            //System.out.println("executing request " + httpget.getURI());  
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // 获取响应实体    
                HttpEntity entity = response.getEntity();  
                // 打印响应状态    
                if (entity != null) {  
                    //System.out.println("Response content: " + EntityUtils.toString(entity));
                    reslut = EntityUtils.toString(entity);
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
		return reslut;
	}

	public MessageModel getMessageModelBySign(String sign) {
		return  convertToVo(getDao().getMessageModelBySign(sign));
	}

	public boolean sendEmail(String from, String to, String title, String content) {
		 MimeMessage message = mailSender.createMimeMessage();
	        try {  
	            //这里的MimeMessageHelper是用来封装邮件的一些基本信息  
	            MimeMessageHelper helper=new MimeMessageHelper(message,true,"UTF-8"); 
	            String sendemail = EmallConfig.defaultMailUsername();
	            helper.setFrom(Utils.notEmpty(from)?from:sendemail);//发件人的地址  
	            helper.setTo(to);//收件人的地址  
	            helper.setSubject(title);//邮件的主题  
	            MimeBodyPart body=new MimeBodyPart();//邮件体  
	          
	            body.setContent(content,"text/html;charset=UTF-8");
	            MimeMultipart mp=new MimeMultipart();  
	            mp.addBodyPart(body);  
	            message.setContent(mp);  
	           
	            mailSender.send(message);//发送邮件  
	            return true;
	          
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            return false;
	        }  

	}

	public List<MessageModel> getMessageModelByRecevierType(String recevierType) {
		return  convertToVos(getDao().getMessageModelByRecevierType(recevierType));
	}

	public boolean sendMessge(String sign, String receiverId,Map<String, String> data, String senderId) {
		boolean smsResult = false;
		boolean emailResult = false;
		boolean inMSGResult = false;
		User user = userService.getUserById(receiverId);
		MessageModel messageModel = getMessageModelBySign(sign);
		String contents = this.replaceContent(messageModel.getContent(), data);
		String title = this.replaceContent(messageModel.getTitle(), data);
//		if(user.getMobiletel()!=null&&!"".equals(user.getMobiletel())){
//			String result = this.sendSms(contents, user.getMobiletel());
//			if("00".equals(result)){
//				smsResult = true;
//			}
//		}
		if(user.getEmail()!=null&&!"".equals(user.getEmail())){
			emailResult = this.sendEmail(null, user.getEmail(), title, contents);
		}
		if(smsResult || emailResult || inMSGResult){
			return true;
		}else{
			return false;
		}
	}
	
	

}
