package com.rongji.cms.emall.web.shopping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rongji.cms.emall.entity.EmlMyAttention_;
import com.rongji.cms.emall.service.shopping.MyAttentionService;
import com.rongji.cms.emall.vo.MyAttention;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;


@Controller
@RequestMapping("/buyer/myattention/storeattention")
public class StoreAttentionController extends CrudAndPageByJsonController<MyAttention> {
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setLength(5);
		PAGE_INFO.setCheckboxMode(true);
		PAGE_INFO.setIdColumn(EmlMyAttention_.id);
	}
	
	@Autowired
	private MyAttentionService myAttentionService;

	@Override
	protected CommonService<MyAttention, ?> getService() {
		return myAttentionService;
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@Override
	protected PageEntity<MyAttention> getPage(DatatablesMetadata metadata) {
		String userId=getCurrUserId();
		if(userId!=null){
			metadata.addSearch(EmlMyAttention_.userId,userId);
			metadata.addSearch(EmlMyAttention_.attentionType,"2");//店铺
		}
		if(metadata.getSort() == null){//按时间排序
			metadata.setSort( new Sort(new Sort.Order(Direction.DESC, EmlMyAttention_.createTime.getName())) );
		}
		PageEntity<MyAttention> pageEntity = null;
		List<MyAttention> myAttentionsList = new ArrayList<MyAttention>(),
						  myAttentionsList1 = new ArrayList<MyAttention>();
		if("storeId".equals(metadata.getSearchs().get(0)[0])){
			String title = metadata.getSearchs().get(0)[2];
			metadata.getSearchs().remove(0);//移除掉，自己写搜索数据
			metadata.setSearch("");
			pageEntity = super.getPage(metadata);
			myAttentionsList = pageEntity.getData();
			for (MyAttention myAttention : myAttentionsList) {
				if(myAttention.getStore().getStoreName().contains(title.trim())){
					myAttentionsList1.add(myAttention);
				}
			}
			pageEntity.setData(myAttentionsList1);
			return pageEntity;
		}
		return super.getPage(metadata);
	}

	
}
