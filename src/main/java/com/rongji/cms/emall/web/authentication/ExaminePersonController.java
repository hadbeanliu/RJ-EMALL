package com.rongji.cms.emall.web.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rongji.cms.emall.entity.EmlExamine_;
import com.rongji.cms.emall.service.examine.ExamineService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.vo.Examine;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

/**
 * 认证管理 2015-6-9
 * 
 * @author lzm
 * 
 */
@Controller
@RequestMapping("admin/authentication/examine/person")
public class ExaminePersonController extends CrudAndPageByJsonController<Examine> {


	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setIdColumn(EmlExamine_.id);
	}
	
	@Override
	protected PageEntity<Examine> getPage(DatatablesMetadata metadata) {
		metadata.addSearch(EmlExamine_.userId.getName(), getCurrUserId());
		metadata.addSearch(EmlExamine_.authentications.getName(), "4");
		return super.getPage(metadata);
	}

	@Autowired
	private ExamineService examineService;
	
	@Autowired
	private UserService userService;

	@Override
	protected CommonService<Examine, ?> getService() {
		return examineService;
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

}
