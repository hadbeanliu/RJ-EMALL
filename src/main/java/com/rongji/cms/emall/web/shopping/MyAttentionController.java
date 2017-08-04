package com.rongji.cms.emall.web.shopping;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rongji.cms.emall.entity.EmlMyAttention;
import com.rongji.cms.emall.entity.EmlMyAttention_;
import com.rongji.cms.emall.service.shopping.MyAttentionService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.vo.Goods;
import com.rongji.cms.emall.vo.MyAttention;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;


@Controller
@RequestMapping("/buyer/myattention")
public class MyAttentionController extends CrudAndPageByJsonController<MyAttention> {
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("我的关注")
	public String index(Model model) {
		return "dsww/001/buyer-center-myattention";
	}
	
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
	
	@Autowired
	private StoreService storeService;

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
			metadata.addSearch(EmlMyAttention_.attentionType,"1");//商品
//			metadata.addSearch(EmlMyAttention_.attentionType,"2");//店铺
//			metadata.addSearch(EmlMyAttention_.attentionType,"3");//活动
		}
		if(metadata.getSort() == null){//按时间排序
			metadata.setSort( new Sort(new Sort.Order(Direction.DESC, EmlMyAttention_.createTime.getName())) );
		}
		PageEntity<MyAttention> pageEntity = null;
		List<MyAttention> myAttentionsList = new ArrayList<MyAttention>(),
						  myAttentionsList1 = new ArrayList<MyAttention>();
		if("goodsId".equals(metadata.getSearchs().get(0)[0])){
			String title = metadata.getSearchs().get(0)[2];
			metadata.getSearchs().remove(0);//移除掉，自己写搜索数据
			metadata.setSearch("");
			pageEntity = super.getPage(metadata);
			myAttentionsList = pageEntity.getData();
			for (MyAttention myAttention : myAttentionsList) {
				if(myAttention.getGoods().getGoodsTitle().contains(title.trim())){
					myAttentionsList1.add(myAttention);
				}
			}
			pageEntity.setData(myAttentionsList1);
			return pageEntity;
		}
		/**
		 * 判断商品是否存在
		 */
		PageEntity<MyAttention> pe = super.getPage(metadata);
		myAttentionsList = pe.getData();
		for (MyAttention myAttention : myAttentionsList) {
			if(myAttention.getGoods() == null) continue;
			myAttentionsList1.add(myAttention);
		}
		return new PageEntity<MyAttention>(metadata.getDraw(),myAttentionsList1.size(), myAttentionsList1.size(), myAttentionsList1);
	}

	@Override
	protected MyAttention doSaveVo(MyAttention vo) {//添加关注对象
		vo.setUserId(getCurrUserId());
		vo.setCreateTime(new Date());
		if(vo.getStore() != null){
			//已存在该对象
			EmlMyAttention attention = myAttentionService.getMyAttentionByStoreId(vo.getUserId(), vo.getStore().getStoreId());
			if(attention != null){
				vo = myAttentionService.convertToVo(attention);
				vo.setCreateTime(new Date());
				return myAttentionService.update(vo);
			}
		}
		if(vo.getGoods() != null){
			EmlMyAttention attention = myAttentionService.getMyAttentionByGoodsId(vo.getUserId(), vo.getGoods().getGoodsId());
			if(attention != null){
				vo = myAttentionService.convertToVo(attention);
				vo.setCreateTime(new Date());
				return myAttentionService.update(vo);
//				return myAttentionService.convertToVo(attention);
			}
		}
		if(vo.getStore()== null && vo.getGoods() == null) return null;
		return super.doSaveVo(vo);
	}

	@RequestMapping("/del_attention")
	@RequestMappingDescription("删除记录")
	public void doDelete(MyAttention vo) {
		if(vo.getId() != null) super.voDelete(vo.getId());
	}
	
	/**
	 * 
	 * @param goodsIds 一系列商品编号，以逗号隔开
	 * @return
	 */
	@RequestMapping("/get_like")
	@RequestMappingDescription("猜我喜欢")
	@ResponseBody
	public List<Goods> getLike(@RequestParam String goodsIds) {
//		String content = "[{'id':'2015071016000004','value':6.400000095367432},{'id':'2015062915000005','value':6.400000095367432}]";
		//2015062915002005
		String urlstr = "";
		urlstr = "http://192.168.4.150:8008/recommend_api/recommend?ids=" + goodsIds;
		try
		{
			URL url = new URL(urlstr);
			URLConnection con = url.openConnection();
			con.setAllowUserInteraction(false);
			InputStream urlStream = url.openStream();
			@SuppressWarnings("static-access")
			String type = con.guessContentTypeFromStream(urlStream);
			String charSet = null;
			if (type == null)
				type = con.getContentType();
			if (type == null || type.trim().length() == 0
					|| type.trim().indexOf("text/html") < 0)
				return null;

			if (type.indexOf("charset=") > 0)
				charSet = type.substring(type.indexOf("charset=") + 8);

			byte b[] = new byte[10000];
			int numRead = urlStream.read(b);
			String content = new String(b, 0, numRead);
			while (numRead != -1) {
				numRead = urlStream.read(b);
				if (numRead != -1) {
					// String newContent = new String(b, 0, numRead);
					String newContent = new String(b, 0, numRead, charSet);
					content += newContent;
				}
			}
			urlStream.close();//关闭流
			
			List<GuessLikeInfo> guessLikeInfoList =
					new Gson().fromJson(content, new TypeToken<List<GuessLikeInfo>>(){}.getType());
			List<Goods> goodsList= new ArrayList<Goods>();
			for (GuessLikeInfo guessLikeInfo : guessLikeInfoList) {
				Goods e = storeService.getGoodsByGoodsId(guessLikeInfo.getId());
				if( e != null){
					goodsList.add(e);
				}
			}
			
			return goodsList;
//			return content;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
