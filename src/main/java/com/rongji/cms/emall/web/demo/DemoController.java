package com.rongji.cms.emall.web.demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rongji.cms.emall.entity.EmlOrderGoods_;
import com.rongji.cms.emall.service.order.OrderGoodsService;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.OrderGoods;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;

/**
 * 新功能的例子入口，后期将迁移到demo工程中,
 * <p>
 * 本例子采用Thymeleaf做为模版引擎对应的模版路径为：/WEB-INF/views/thymeleaf，即程序查找开始路径。url的后缀为：htm
 *
 * @since 2015-5-21
 * @author rjf
 *
 */
@Controller
@RequestMapping("/demo")
public class DemoController extends CrudAndPageByJsonController<OrderGoods> {
	
	
	/**
	 * 用于定制数据的绑定
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 浏览器提交的是order＝000001，后台对应的是一个对象。则需要定制转换器，用于本Controller的所有请求
		binder.registerCustomEditor(Order.class, new OrderPropertyEditorSupport());
	}

	/**
	 * 新工程的可参考的例子入口
	 * 
	 * {@link RequestMapping}不填路径默认查找：/WEB-INF/views/thymeleaf/demo/index.htm，对应模版地址为：/demo/index。
	 * <p>
	 * 这里由一个需要注意的地方: 当在没有填写路径的情况下先查找<code>/demo.htm</code>，若不存在则查找<code>/demo/index.htm</code>
	 */
	@RequestMapping
	@RequestMappingDescription("例子入口")
	public String index(Model model) {
		model.addAttribute("users", datas.values());
		return "/demo/index";
	}
	
	@RequestMapping("/clear") // 地址：/demo/clear.htm
	@RequestMappingDescription("清空数据")
	public String clear() {
		datas.clear();
		
//		return RequestUtil.REDIRECT_URL_PREFIX + "/demo";
		return redirectTo("/demo");
	}
	
	
	@RequestMapping("/form") // 地址：/demo/form.htm
	@RequestMappingDescription("表单界面")
	public String form(Model model, HttpSession session) {
		
		// 这里添加的值将会在模版中使用，通过<a data-th-text="${message}"></a>生成结果：<a>请选择上传文件</a>
		model.addAttribute("message", "请选择上传文件");
		
		return "/demo/form";
	}
	
	@RequestMapping("/save_form")
	@RequestMappingDescription("保存表单数据")
	public String saveForm(DemoUser du, RedirectAttributes redirectAttributes, HttpSession session) {
		
		// 避免重复提交有三种方式。
		// 1.0 js在保存成功后disable操作
		// 2.0 POST转GET这样浏览器将无法使用F5重复提交，Post-Redirect-Get
		// 3.0 添加一个Token，通过判断这个Tonken是否已使用来标识是否为同一提交, 不建议使用
		
		System.out.println(getRequest().getCharacterEncoding());
		System.out.println(getRequest().getContentType());
		
		du.setId(String.valueOf(new Date().getTime()));
		MultipartFile file = du.getFile(); // 获取上传文件
		if (file != null) {
			du.setFilePath(file.getOriginalFilename());
		}
		datas.put(du.getId(), du);
		
		redirectAttributes.addFlashAttribute("message", "保存[ "+ du.getUsername() +" ]成功");// 2.1 redirect后使用redirectAttributes传递数据
		
		
		// session.setAttribute("SAVED_TOKEN", "1"); 3.1 添加Tokey
		
		return redirectTo("/demo"); // 2.2 Post转Get的方式避免重复提交 Post-Redirect-Get (PRG) pattern
	}
	
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setLength(5);
		PAGE_INFO.setCheckboxMode(true);
		PAGE_INFO.setIdColumn(EmlOrderGoods_.id);
	}
	
	@Autowired
	private OrderGoodsService orderGoodsService;

	@Override
	protected CommonService<OrderGoods, ?> getService() {
		return orderGoodsService;
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}
	
	
	/**
	 * 保存测试数据，用来代替Service
	 */
	private Map<String, DemoUser> datas = new HashMap<String, DemoUser>();

}
