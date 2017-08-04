package com.rongji.cms.emall.web.evaluation;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rongji.cms.emall.entity.EmlEvaluation_;
import com.rongji.cms.emall.service.evaluation.BuyerEvaluationService;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.vo.Evaluation;
import com.rongji.cms.emall.web.buyer.BuyerController;
import com.rongji.dfish.base.Utils;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageUseServiceByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;
@Controller
@RequestMapping("/buyer/evaluation")
public class BuyerEvaluationController extends CrudAndPageUseServiceByJsonController<Evaluation, BuyerEvaluationService> {
	
	@Autowired
	private BuyerEvaluationService buyerevaluationService;
	
	private Map<String, Object> info = new HashMap<String, Object>();
	private Gson gson=new Gson();

	@Autowired
	private OrderService orderService;
	
	private static final Logger logger = LoggerFactory.getLogger(BuyerController.class);
	/**
	 * 如果是JsonP的格式访问，则返回Jsonp格式，或则返回Json格式
	 * 
	 * @param request
	 * @param json
	 * @return
	 */
	private String append4jsonP(HttpServletRequest request, String json) {
		String callBack = Utils.getParameter(request, "callback");
		if (Utils.notEmpty(callBack)) {
			return callBack + "(" + json + ")";
		}
		return json;

	}	
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setIdColumn(EmlEvaluation_.id);
	}

	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("用户评价管理")
	public String index(Model model) {
		String userId = getCurrUserId();
		if (userId != null) {			
				return "/dsww/001/buyer-evaluation";
		} else {
			return "/login";
		}		
	}	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@Override
	protected PageEntity<Evaluation> getPage(DatatablesMetadata metadata) {
		String userId = getCurrUserId();
		metadata.addSearch("userId",userId);
		return buyerevaluationService.getByDatatables(metadata);
	}
	
	@RequestMapping(value="setAdditionalComment")
	@ResponseBody
	@RequestMappingDescription("买家追加评论")
	public String setExplainComment(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String objId=Utils.getParameter(request, "objId");//商品ID
		String cmtId=Utils.getParameter(request, "cmtId");//评论ID
 	    String userId=Utils.getParameter(request, "userId");//用户ID
 	    String additionalComment=Utils.getParameter(request, "additionalComment");//解释内容
 	    String sunAdditionalCommentPicture=Utils.getParameter(request, "sunAdditionalCommentPicture");//追加图片
 	    if (Utils.isEmpty(objId)||Utils.isEmpty(cmtId)||Utils.isEmpty(additionalComment)||Utils.isEmpty(userId)) {
 	    	info.put("Code", "404");
 	    	info.put("Msg", "参数不许为空!"); 	    
		    return append4jsonP(request, gson.toJson(info));
		}		
		return  append4jsonP(request, gson.toJson(buyerevaluationService.setAdditionalComment(objId, cmtId, userId, additionalComment, sunAdditionalCommentPicture)));
	}
	
	@RequestMapping(value="isSunPicture")
	@ResponseBody
	@RequestMappingDescription("是否可晒图")
	public String issunPicture(){
		String objId=Utils.getParameter(getRequest(), "objId");//商品ID
		String cmtId=Utils.getParameter(getRequest(), "cmtId");//评论ID
 	    String userId=Utils.getParameter(getRequest(), "userId");//用户ID
 	    if(Utils.isEmpty(objId)||Utils.isEmpty(cmtId)||Utils.isEmpty(userId)) {  
 		   	info.put("Code", "404");
	    	info.put("Msg", "参数不许为空!"); 	    
		    return append4jsonP(getRequest(), gson.toJson(info));
		}		
		return append4jsonP(getRequest(), gson.toJson(buyerevaluationService.isSunPicture(objId, cmtId, userId)));
	}
	
	@RequestMapping("/publish")
	@RequestMappingDescription("买家评价页")
	public String confirmOrder(@RequestParam String data, Model model) {
		
		if (data != null && !data.trim().isEmpty()) {
			
			try {
				data = URLDecoder.decode(data, "utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("评价订单信息编码错误: " + data, e);
				throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_NOT_FOUND);
			}			
            String orderId=data.replace("\"","");
		    model.addAttribute("orders", buyerevaluationService.getEvaluationOrder(orderId, getCurrUserId()));
			model.addAttribute("userId",getCurrUserId());	
		}
		return "/dsww/001/evaluate-publish";
	}
	
	
}
