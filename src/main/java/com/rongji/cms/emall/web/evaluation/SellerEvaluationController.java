package com.rongji.cms.emall.web.evaluation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rongji.cms.emall.entity.EmlEvaluation_;
import com.rongji.cms.emall.service.evaluation.SellerEvaluationService;
import com.rongji.cms.emall.vo.Evaluation;
import com.rongji.dfish.base.Utils;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageUseServiceByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;
@Controller
@RequestMapping("/seller/evaluation")
public class SellerEvaluationController extends CrudAndPageUseServiceByJsonController<Evaluation, SellerEvaluationService> {
	
		
	@Autowired
	private SellerEvaluationService evaluationService;
	
	private Map<String, Object> info = new HashMap<String, Object>();
	private Gson gson=new Gson();
	
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
	@RequestMappingDescription("商铺评价管理")
	public String index(Model model) {
		String userId = getCurrUserId();
		model.addAttribute("userId", userId);
		return "/dsww/001/seller-evaluation";		
	}	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@Override
	protected PageEntity<Evaluation> getPage(DatatablesMetadata metadata) {
		String userId = getCurrUserId();
		metadata.addSearch("userId",userId);
		return evaluationService.getByDatatables(metadata);
	}	
	
	@RequestMapping(value="getDynamicInfoByShopIdAndWithinDays")
	@ResponseBody
	@RequestMappingDescription("获取店铺规定时间内的动态评分")
	public String getDynamicInfoByShopIdAndWithinDays(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String storeId=Utils.getParameter(request, "storeId");//店铺ID
 	    String withinDays=Utils.getParameter(request, "withinDays");//几天内
 	    if (Utils.isEmpty(storeId)||storeId=="") {
 	    	info.put("Code", "404");
 	    	info.put("Msg", "店铺ID不许为空!"); 	    
		    return append4jsonP(request, gson.toJson(info));
		}
		if (Utils.isEmpty(withinDays)) {
			withinDays = "180";
		}
 	    info.put("Code", "200");
 	    info.put("Msg", "成功获取店铺规定时间内的动态评分");
 	    info.put("data", evaluationService.getDynamicInfoByStoreIdAndWithinDays(storeId, Integer.valueOf(withinDays)));
		return  append4jsonP(request, gson.toJson(info));
	}
	
	@RequestMapping(value="setExplainComment")
	@ResponseBody
	@RequestMappingDescription("卖家解释")
	public String setExplainComment(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String userId=Utils.getParameter(request, "userId");//买家用户ID
		String objId=Utils.getParameter(request, "objId");//商品ID
		String cmtId=Utils.getParameter(request, "cmtId");//评论ID 	  
 	    String explain=Utils.getParameter(request, "explain");//解释内容
 	    if (Utils.isEmpty(objId)||Utils.isEmpty(cmtId)||Utils.isEmpty(explain)) {
 	    	info.put("Code", "404");
 	    	info.put("Msg", "参数不许为空!"); 	    
		    return append4jsonP(request, gson.toJson(info));
		}		
		return  append4jsonP(request, gson.toJson(evaluationService.setExplainComment(objId,cmtId,userId,explain)));
	}
	
	@RequestMapping(value="setAdditionalExplain")
	@ResponseBody
	@RequestMappingDescription("卖家追加解释")
	public String setAdditionalExplain(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String userId=Utils.getParameter(request, "userId");//买家用户ID
		String objId=Utils.getParameter(request, "objId");//商品ID
		String cmtId=Utils.getParameter(request, "cmtId");//评论ID
 	    String additionalExplain=Utils.getParameter(request, "additionalExplain");//追加解释
 	   if (Utils.isEmpty(objId)||Utils.isEmpty(cmtId)||Utils.isEmpty(additionalExplain)) {
	    	info.put("Code", "404");
	    	info.put("Msg", "参数不许为空!"); 		    
		    return append4jsonP(request, gson.toJson(info));
		}		
		return  append4jsonP(request, gson.toJson(evaluationService.setAdditionalExplain(objId,cmtId,userId,additionalExplain)));
	}
	
	@RequestMapping(value="getHigerInfo")
	@ResponseBody
	@RequestMappingDescription("获取所有平均分")
	public String getHigerInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String withinDays=Utils.getParameter(request, "withinDays");//几天内	
 	   if (Utils.isEmpty(withinDays)) {
	    	info.put("Code", "404");
	    	info.put("Msg", "参数不许为空!"); 		    
		    return append4jsonP(request, gson.toJson(info));
		}		
		return  append4jsonP(request, gson.toJson(evaluationService.getHigerInfo(Integer.valueOf(withinDays))));
	}
	
	@RequestMapping(value="getDynamicGoodInfo")
	@ResponseBody
	@RequestMappingDescription("分页获取好评评论信息")
	public String getDynamicGoodInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String start=Utils.getParameter(request, "start");//开始索引
		String objId=Utils.getParameter(request, "objId");//商品ID
		String rows=Utils.getParameter(request, "rows");//每页显示条数
 	   if (Utils.isEmpty(start)||Utils.isEmpty(objId)||Utils.isEmpty(rows)) {
	    	info.put("Code", "404");
	    	info.put("Msg", "参数不许为空!"); 		    
		    return append4jsonP(request, gson.toJson(info));
		}		
		return  append4jsonP(request, gson.toJson(evaluationService.getDynamicGoodInfo(objId,start,rows)));
	}
	
	@RequestMapping(value="getDynamicInInfo")
	@ResponseBody
	@RequestMappingDescription("分页获取中评评论信息")
	public String getDynamicInInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String start=Utils.getParameter(request, "start");//开始索引
		String objId=Utils.getParameter(request, "objId");//商品ID
		String rows=Utils.getParameter(request, "rows");//每页显示条数
 	   if (Utils.isEmpty(start)||Utils.isEmpty(objId)||Utils.isEmpty(rows)) {
	    	info.put("Code", "404");
	    	info.put("Msg", "参数不许为空!"); 		    
		    return append4jsonP(request, gson.toJson(info));
		}		
		return  append4jsonP(request, gson.toJson(evaluationService.getDynamicInInfo(objId,start,rows)));
	}
	
	@RequestMapping(value="getDynamicBadInfo")
	@ResponseBody
	@RequestMappingDescription("分页获取差评评论信息")
	public String getDynamicBadInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String start=Utils.getParameter(request, "start");//开始索引
		String objId=Utils.getParameter(request, "objId");//商品ID
		String rows=Utils.getParameter(request, "rows");//每页显示条数
 	   if (Utils.isEmpty(start)||Utils.isEmpty(objId)||Utils.isEmpty(rows)) {
	    	info.put("Code", "404");
	    	info.put("Msg", "参数不许为空!"); 		    
		    return append4jsonP(request, gson.toJson(info));
		}		
		return  append4jsonP(request, gson.toJson(evaluationService.getDynamicBadInfo(objId,start,rows)));
	}
	
	@RequestMapping(value="getDynamicPictureInfo")
	@ResponseBody
	@RequestMappingDescription("分页获取带有图片评论信息")
	public String getDynamicPictureInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String start=Utils.getParameter(request, "start");//开始索引
		String objId=Utils.getParameter(request, "objId");//商品ID
		String rows=Utils.getParameter(request, "rows");//每页显示条数
 	   if (Utils.isEmpty(start)||Utils.isEmpty(objId)||Utils.isEmpty(rows)) {
	    	info.put("Code", "404");
	    	info.put("Msg", "参数不许为空!"); 		    
		    return append4jsonP(request, gson.toJson(info));
		}		
		return  append4jsonP(request, gson.toJson(evaluationService.getDynamicPictureInfo(objId,start,rows)));
	}
	
	@RequestMapping(value="getUserCommentInfo")
	@ResponseBody
	@RequestMappingDescription("分页获取用户评论信息")
	public String getUserCommentInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String start=Utils.getParameter(request, "start");//开始索引
		String objId=Utils.getParameter(request, "objId");//商品ID
		String rows=Utils.getParameter(request, "rows");//每页显示条数
 	   if (Utils.isEmpty(start)||Utils.isEmpty(objId)||Utils.isEmpty(rows)) {
	    	info.put("Code", "404");
	    	info.put("Msg", "参数不许为空!"); 		    
		    return append4jsonP(request, gson.toJson(info));
		}		
		return  append4jsonP(request, gson.toJson(evaluationService.getUserCommentInfo(objId,start,rows)));
	}	
}
