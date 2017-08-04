package com.rongji.cms.emall.web.shopping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rongji.cms.emall.entity.EmlMyBrowse_;
import com.rongji.cms.emall.service.shopping.MyBrowseService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.vo.MyBrowse;
import com.rongji.cms.emall.vo.MyBrowseCookie;
import com.rongji.dfish.base.crypt.CryptFactory;
import com.rongji.dfish.base.crypt.StringCryptor;
import com.rongji.dfish.cache.meta.HashMeta;
import com.rongji.dfish.cache.meta.ListMeta;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;


@Controller
@RequestMapping("/buyer/mybrowse")
public class MyBrowseController extends CrudAndPageByJsonController<MyBrowse> {
	private StringCryptor SC = CryptFactory.getStringCryptor(CryptFactory.BLOWFISH, CryptFactory.UTF8, CryptFactory.BASE64, "LOGIN_KEY_CODE");
	
	private String myBrowseFlag="myBrowse.";//浏览标识
	private String myBrowseListFlag="myBrowseList.";//记录浏览记录
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("我的浏览")
	public String index(Model model) {
		return "/dsww/001/buyer-center-myview";
	}
	
	@Override
	protected MyBrowse doSaveVo(MyBrowse vo) {
		if (vo != null && vo.getId() == null) {
			vo.setCreateTime(new Date());
			vo.setUserId(getCurrUserId());
		}
		return super.doSaveVo(vo);
	}
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setIdColumn(EmlMyBrowse_.id);
	}
	
	@Autowired
	private MyBrowseService myBrowseService;
	
	@Autowired
	private StoreService storeService;

	@Override
	protected CommonService<MyBrowse, ?> getService() {
		return myBrowseService;
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@Override
	protected PageEntity<MyBrowse> getPage(DatatablesMetadata metadata) {
		 
		List<MyBrowse> myBrowses = new ArrayList<MyBrowse>();//获取浏览数据
//		String userId = metadata.getSearchs().get(0)[1];
		ListMeta myBrowseList = new ListMeta(myBrowseListFlag + "userId:" + getCurrUserId());
		int start = metadata.getStart();//首页
		int currPage = start/metadata.getLenght() + 1;//当前第几页
	//	int end = metadata.getLenght()*currPage - 1; //结束页
	//	int size = Integer.valueOf(myBrowseList.size().toString());
	//	System.out.println("首页="+start+" 当前第几页="+currPage+" 最后一页"+end);
		int size = 0;//当前页的总数
		int perNum = 1;
		Gson json = new Gson();
		HashMeta mybrowseMeta = new HashMeta(myBrowseFlag + "userId:" + getCurrUserId());
		Map<String,String> myBrowseAllMap = mybrowseMeta.getAll();
		
		List<MyBrowseGoodList> goodsList = new ArrayList<MyBrowseGoodList>();
		List<String> listStrings = myBrowseList.gets(0, -1);
		for (int j = 0 ; j < listStrings.size(); j++ ) {//对n条数据的n-1条进行循环判断
			String myBrowseKey = listStrings.get(j);
			MyBrowseCookie myBrowseCookie = json.fromJson(myBrowseAllMap.get(myBrowseKey), MyBrowseCookie.class);
			MyBrowse myBrowse = new MyBrowse();
			myBrowse.setId(myBrowseKey);//浏览Id就是第一个时间的商品的Id
			myBrowse.setCreateTime(myBrowseCookie.getCreateTime());//初始化不同时间的第一条数据
			myBrowse.setUserId(getCurrUserId());
			List<MyBrowseGoodList> goodsList1 = new ArrayList<MyBrowseGoodList>();
			MyBrowseGoodList browseGoodList = new MyBrowseGoodList();
			browseGoodList.setBrowseDate(myBrowseCookie.getCreateTime());
			browseGoodList.setGoods(storeService.getGoodsByGoodsId(myBrowseKey));
			if(goodsList.size() == 0) {//第一条数据
				goodsList.add(browseGoodList);
				myBrowse.setGoodsList(goodsList);
				myBrowse.setPerNum(perNum);
				myBrowses.add(myBrowse);
				size ++;
			}else{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
				String date1 = formatter.format(myBrowses.get(myBrowses.size() - 1).getCreateTime());//上一次数据的时间
				String date2 = formatter.format(myBrowseCookie.getCreateTime());
				if(date1.equals(date2)){
					myBrowses.get(myBrowses.size() - 1).getGoodsList().add(browseGoodList);
					perNum ++;
					myBrowses.get(myBrowses.size() - 1).setPerNum(perNum);
				}else{
					perNum = 1;
					goodsList1.add(browseGoodList);
	//				goodsList.add(storeService.getGoodsByGoodsId(myBrowseKey));
					myBrowse.setGoodsList(goodsList1);
					myBrowse.setPerNum(perNum);
					myBrowses.add(myBrowse);
				}
	
				size ++;
			}
			
		}
		List<MyBrowse> showBrowses = new ArrayList<MyBrowse>();
		int x = metadata.getLenght()*currPage > myBrowses.size()? myBrowses.size() : metadata.getLenght()*currPage;
		for (int i = (currPage - 1)*metadata.getLenght(); i < x; i++) {//通过length值，来得到新的浏览数据
			
			showBrowses.add(myBrowses.get(i));
			if(i == x - 1){
				myBrowses.get(i).setCurrLast("1");
			}
		}
		//												当前页总数              所有数据的总数                    数据data--当前的还是总的？
		return new PageEntity<MyBrowse>(metadata.getDraw(),size, myBrowses.size(), showBrowses);
	//	return new PageEntity<MyBrowse>(metadata.getDraw(),size, myBrowseList.size(),myBrowses);
	}
	
	@RequestMapping(value="saveMyBrowseToCookie")
	@ResponseBody
	@RequestMappingDescription("添加浏览商品记录")
    public void saveMyBrowseToCookie(HttpServletResponse response){
		String goodsId= getRequest().getParameter("goodsId");//商品ID  
		String residenceTime= getRequest().getParameter("residenceTime");//停留时间	
	 	Gson gson=new Gson(); 
	 	//设置cookie信息
		MyBrowseCookie mbc=new MyBrowseCookie();
		mbc.setGoodsId(goodsId);
		mbc.setTimes(1);
		mbc.setResidenceTime(residenceTime);	
		mbc.setCreateTime(new Date());
		int maxAge = 2700000;//30天
		Date endTime = getEndTime(maxAge);
		mbc.setEndTime(endTime);
		if(getCurrUserId()!=null){
			getMyBrowse(true,response);
			HashMeta myBrowseMap=new HashMeta(myBrowseFlag+"userId:"+getCurrUserId());	
			ListMeta myBrowseList=new ListMeta(myBrowseListFlag+"userId:"+getCurrUserId());
			myBrowseList.remove(goodsId);
			myBrowseList.lAdd(goodsId);
			if(myBrowseMap.get(goodsId)!=null){
				MyBrowseCookie myBrowseCookie=gson.fromJson(myBrowseMap.get(goodsId), MyBrowseCookie.class);
				myBrowseCookie.setCreateTime(new Date());
				myBrowseCookie.setTimes(myBrowseCookie.getTimes()+1);
				myBrowseCookie.setEndTime(endTime);
				myBrowseCookie.setResidenceTime(myBrowseCookie.getResidenceTime()+","+residenceTime);
				myBrowseMap.set(goodsId, gson.toJson(myBrowseCookie));
			}else{
				myBrowseMap.set(goodsId, gson.toJson(mbc));
			}
		}else{	       
			//新增cookie信息
			Map<String,MyBrowseCookie> myBrowseMap=new LinkedHashMap<String,MyBrowseCookie>();			
			//获取浏览记录
			myBrowseMap=getMyBrowse(false,response);	
			//更新或新增
			if(myBrowseMap!=null){
				if(myBrowseMap.get(goodsId)!=null){
					mbc.setTimes(mbc.getTimes()+1);
					myBrowseMap.remove(goodsId);
			    }
			}else{
				myBrowseMap=new LinkedHashMap<String,MyBrowseCookie>();
			}
			myBrowseMap.put(goodsId, mbc);
	    	//生成cookie信息
			String cookieInfo = gson.toJson(myBrowseMap);	
			//加密解密
			String encryptCookie = SC.encrypt(cookieInfo);
			Cookie code = new Cookie("mybrowse"+getRequest().getSession(),encryptCookie);
			code.setMaxAge(maxAge);
			code.setDomain(getRequest().getRemoteHost());
			code.setPath(getRequest().getContextPath());
			response.addCookie(code);	
		}
    }    
	
  
	@RequestMapping(value="getMyBrowse")
	@RequestMappingDescription("获取浏览记录")
    public Map<String,MyBrowseCookie> getMyBrowse(boolean isUser,HttpServletResponse response){
    	Gson gson=new Gson();
    	Cookie[] cookies = getRequest().getCookies();
    	Map<String,MyBrowseCookie> mbc=null;
    	if(cookies!=null&&cookies.length>0){
			String mybrowse = null;
			for(Cookie cookie : cookies){				
				if(("mybrowse"+getRequest().getSession()).equals(cookie.getName())){
					mybrowse = cookie.getValue();
					break;
				}
			}
			//解密
			if(!"".equals(mybrowse)&&mybrowse!=null){
				//有效的cookie
				String deMybrowse = SC.decrypt(mybrowse);
				if(!"".equals(deMybrowse)){					
					mbc = gson.fromJson(deMybrowse,new TypeToken<Map<String,MyBrowseCookie>>(){}.getType());
					List<String> expireGoodsRecord=new ArrayList<String>();//记录过期记录
					 for (Map.Entry<String, MyBrowseCookie> entry : mbc.entrySet()) {
						 MyBrowseCookie myBrowseCookie=entry.getValue();
						 //是否为近30天
						 if(new Date().getTime()-myBrowseCookie.getEndTime().getTime()>=0){
							 expireGoodsRecord.add(entry.getKey());
						 }
					 }
					 //删除过期记录
					 for (String goodsId : expireGoodsRecord) {
						 mbc.remove(goodsId);
					}					
				}
			}
		}  
    	if(isUser){
			if (mbc != null) {
				HashMeta myBrowseMap = new HashMeta(myBrowseFlag + "userId:" + getCurrUserId());
				ListMeta myBrowseList = new ListMeta(myBrowseListFlag + "userId:" + getCurrUserId());
				for (Map.Entry<String, MyBrowseCookie> entry : mbc.entrySet()) {
					String goodsId = entry.getKey();
					if (myBrowseMap.get(goodsId) != null) {
						MyBrowseCookie myBrowseCookie = entry.getValue();
						myBrowseCookie.setTimes(myBrowseCookie.getTimes() + 1);
						myBrowseCookie.setCreateTime(new Date());
						myBrowseMap.set(goodsId, gson.toJson(myBrowseCookie));
						myBrowseList.remove(goodsId);
					}
					myBrowseList.lAdd(goodsId);
					Cookie code = new Cookie("mybrowse"	+ getRequest().getSession(), null);
					code.setMaxAge(0);
					code.setDomain(getRequest().getRemoteHost());
					code.setPath(getRequest().getContextPath());
					response.addCookie(code);
					return null;
				}
			}
    	}
    	return mbc;
    }   
	
	
	
	@Override
	@RequestMapping("/v_dels")
	@RequestMappingDescription("批量删除记录")
	public void voDeletes(@RequestParam String voIds) {
		String[]  ids = voIds.split(",");
		for (String goodsId : ids) {
//			getService().delete(id);
			if (getCurrUserId() != null) {			
				HashMeta myBrowseMap = new HashMeta(myBrowseFlag + "userId:" + getCurrUserId());
				ListMeta myBrowseList = new ListMeta(myBrowseListFlag + "userId:" + getCurrUserId());
				myBrowseList.remove(goodsId);
				myBrowseMap.remove(goodsId);
//				return "{\"	Code\":\"200\",\"Msg\":\"删除记录成功!\"}";
			} 
//			return "{\"	Code\":\"404\",\"Msg\":\"删除失败,请先登录!\"}";
		}
//		super.voDeletes(voIds);
	}

	/**
	 * 获取过期时间 
	 * 
	 * @param cookieTime
	 * @return
	 */
	private Date getEndTime(int endTime){
		Date date = null;
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.SECOND, endTime);
		date = instance.getTime();
		return date;
	}
	
}
