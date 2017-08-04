package com.rongji.cms.emall.web.areas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongji.cms.emall.entity.EmlAreas;
import com.rongji.cms.emall.entity.EmlAreas_;
import com.rongji.cms.emall.service.areas.AreasService;
import com.rongji.cms.emall.vo.Areas;
import com.rongji.dfish.cache.JsonUtil;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Controller
@RequestMapping("/admin/areas")
public class AreasController extends CrudAndPageByJsonController<Areas>{
	
	private static String areaJson;
	
	private static List<AreasList> areaslist;
	//保存地区数据在在Map<areaCode,areaName>
	private static Map<String, Object> areasmap;
	
	@Autowired
	private AreasService areasService;
	
	@Override
	protected CommonService<Areas, ?> getService() {
		return areasService;
	}

	private static PageInfo PAGE_INFO = new PageInfo();
	static{
		PAGE_INFO.setLength(5);
		PAGE_INFO.setCheckboxMode(true);
		PAGE_INFO.addColumn(EmlAreas_.id, true, true,true);
	}

	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("地区列表")
	public String index() {
		if(areasService.getAll().isEmpty()){
			dataInit();
		}
		return "/dsww/001/admin-areas";
	}
	
	@Override
	protected PageEntity<Areas> getPage(DatatablesMetadata metadata) {
		return areasService.getByDatatables(metadata);
	}

	@Override
	protected Areas doSaveVo(Areas vo) {
		vo.setId(vo.getAreaCode());
		return super.doSaveVo(vo);
	}
	

	@Override
	protected Areas doUpdateVo(Areas vo, MultiValueMap<String, Object> data) {
		
		return super.doUpdateVo(vo, data);
	}
	
	/*
	 * 提供地区数据初始化
	 */
	@RequestMapping("/datainit")
	@RequestMappingDescription("数据初始化")
	@ResponseBody
	public List<AreasList> dataInit() {
		if(areaslist==null){
			areasmap=new HashMap<String, Object>();
			areaslist=new ArrayList<AreasList>();
			String str = UrlUtil.getContentByUrl("http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201504/t20150415_712722.html", true);
			String[] strs=UrlUtil.GetContent(str).split(",");
			String[][] areas=new String[strs.length/2][2];
			String areasstr="";
			for(int i=0 ; i<strs.length/2;i++){
				areas[i][0]=strs[2*i];
				areas[i][1]=strs[2*i+1];
			}
	
			int flag=0;
			for(String[] row : areas){
					for(String area : row)
					{
						if(!area.equals("130682定州市")){
							if(flag++==0){
								areasstr=area;
							}else{
								areasstr=areasstr +"," + area;
							}
						}else{
							areasstr=areasstr+","+area.substring(0, 6)+","+area.substring(6, area.length());
						}
					}
			}
			strs=areasstr.split(",");
			areas=new String[strs.length/2][2];
			for(int i=0 ; i<strs.length/2;i++){
				areas[i][0]=strs[2*i];
				areas[i][1]=strs[2*i+1];
			}
			areasService.SaveAreas(areas);
			for(Areas a : areasService.getAll()){
				AreasList al=new AreasList();
				al.setAreaCode(a.getAreaCode());
				al.setAreaName(a.getAreaName());
				areaslist.add(al);
				areasmap.put(a.getAreaCode(), a.getAreaName());
			}
		}
		return areaslist;
	}
	
//	@RequestMapping("/areasselect")
//	@ResponseBody
//	public <T> String AreasSelect(){
//		if(areasService.getAll().isEmpty()){
//			dataInit();
//		}
//		if(areaJson==null){
//			List<EmlAreas> provs = areasService.getProvince();
//			StringBuilder json = new StringBuilder();
//			json.append("[{\"\":{\"name\":\"省份\",\"citys\":[{\"\":{\"name\":\"城市\",\"citys\":[{\"\":\"区县\"}]}}]}}");
//			int len=provs.size();
//			if(len!=0) json.append(",");
//			for(EmlAreas prov : provs){
//				json.append("{\""+prov.getAreaCode()+"\":{\"name\":\""+prov.getAreaName()+"\", \"citys\":[");
//				List<EmlAreas> citys = areasService.getCity(prov.getAreaCode());
//				int i=citys.size();
//				if(i==0){
//					json.append("]");
//				}
//				for(EmlAreas city :citys){
//					json.append("{\""+city.getAreaCode()+"\":{\"name\":\""+city.getAreaName()+"\", \"citys\":[");
//					List<EmlAreas> dists = areasService.getDistrict(city.getAreaCode());
//					int j=dists.size();
//					if(j==0){
//						json.append("]");
//					}
//					for(EmlAreas dist : dists){
//						json.append("{\""+dist.getAreaCode()+"\":\""+dist.getAreaName()+"\"}");
//						if(j-->1){
//							json.append(",");
//						}else{
//							json.append("]");
//						}
//					}
//					if(i-->1){
//						json.append("}},");
//					}else{
//						json.append("}}]");
//					}
//				}
//				if(len-->1){
//					json.append("}},");
//				}else{
//					json.append("}}]");
//				}
//			}
//			areaJson = json.toString();
//			return areaJson;
//		}else{
//			return areaJson;
//		}
//		
//	}
	
//	@RequestMapping("/areasselect")
//	@ResponseBody
//	public <T> String SelectAreas(){
//		if(areasService.getAll().isEmpty()){
//			dataInit();
//		}
//		if(areaJson==null){
//			List<Map<String, Object>> maplist=new ArrayList<Map<String,Object>>();
//			Map<String, Object> dist;
//			List<EmlAreas> dists;
//			List<EmlAreas> citys;
//			List<EmlAreas> provs = areasService.getProvince();
//			for(EmlAreas prov:provs){
//				Map<String, Object> map = new HashMap<String, Object>();
//				Map<String,Object> provmap = new HashMap<String,Object>();
//				provmap.put("name", prov.getAreaName());
//				citys=areasService.getCityByPCode(prov.getAreaCode());
//				Map<String,Object> city = new HashMap<String, Object>();
//				List<Map<String, Object>> citylist = new ArrayList<Map<String,Object>>();
//				for(EmlAreas c:citys){
//					Map<String, Object> citymap = new HashMap<String, Object>();
//					citymap.put("name", c.getAreaName());
//					dists=areasService.getDistrictByPCode(c.getAreaCode());
//					List<Map<String, Object>> distlist = new ArrayList<Map<String,Object>>();
//					for(EmlAreas d:dists){
//						dist = new HashMap<String, Object>();
//						dist.put(d.getAreaCode(), d.getAreaName());
//						distlist.add(dist);
//						citymap.put("citys", distlist);
//					}
//					city.put(c.getAreaCode(), citymap);
//				}
//				citylist.add(city);
//				provmap.put("citys", citylist);
//				map.put(prov.getAreaCode(), provmap);
//				maplist.add(map);
//			}
//			areaJson=JsonUtil.toJson(maplist);
//			return areaJson;
//		}else{
//			return areaJson;
//		}
//	}
	
	/*
	 * 生成结构json数据，级联使用数据
	 */
	@RequestMapping("/areasselect")
	@ResponseBody
	public <T> String Select(){
		if(areasService.getAll().isEmpty()){
			dataInit();
		}
		if(areaJson==null){
			List<Map<String, Object>> maplist=new ArrayList<Map<String,Object>>();
			Map<String, Object> dist;
			List<EmlAreas> provs = areasService.getProvince();
			List<EmlAreas> citys=areasService.getCity();
			List<EmlAreas> dists=areasService.getDistrict();
			//为第一行添加一个提示性的选择
			EmlAreas head=new EmlAreas();
			head.setAreaCode("");
			head.setAreaName("省份");
			head.setParentCode("1");
			provs.add(0, head);
			head=new EmlAreas();
			head.setAreaCode("");
			head.setAreaName("城市");
			head.setParentCode("");
			citys.add(0, head);
			head=new EmlAreas();
			head.setAreaCode("");
			head.setAreaName("区县");
			head.setParentCode("");
			dists.add(0, head);
			for(EmlAreas prov:provs){
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String,Object> provmap = new HashMap<String,Object>();
				provmap.put("name", prov.getAreaName());
				Map<String,Object> city = new HashMap<String, Object>();
				List<Map<String, Object>> citylist = new ArrayList<Map<String,Object>>();
				String code_string="",name_string="";
				for(EmlAreas c:citys){
					if(c.getParentCode().equals(prov.getAreaCode())){
						if(c.getAreaName().equals("市辖区") || c.getAreaName().equals("县")){
							code_string += c.getAreaCode()+"_";
							name_string = prov.getAreaName().substring(0, prov.getAreaName().length()-1);
						}
					}
				}
				if(code_string.equals("")){
					for(EmlAreas c:citys){
						if(c.getParentCode().equals(prov.getAreaCode())){
							Map<String, Object> citymap = new HashMap<String, Object>();
							citymap.put("name", c.getAreaName());
							List<Map<String, Object>> distlist = new ArrayList<Map<String,Object>>();
							for(EmlAreas d:dists){
								if(d.getParentCode().equals(c.getAreaCode()) && !d.getAreaName().equals("市辖区")){
									dist = new HashMap<String, Object>();
									dist.put(d.getAreaCode(), d.getAreaName());
									distlist.add(dist);
									citymap.put("citys", distlist);
								}
							}
							city.put(c.getAreaCode(), citymap);
						}
					}
				}else{
					Map<String, Object> citymap = new HashMap<String, Object>();
					citymap.put("name", name_string);
					List<Map<String, Object>> distlist = new ArrayList<Map<String,Object>>();
					for(EmlAreas d:dists){
						if(code_string.indexOf(d.getParentCode())!=-1 && !d.getAreaName().equals("市辖区")){
							dist = new HashMap<String, Object>();
							dist.put(d.getAreaCode(), d.getAreaName());
							distlist.add(dist);
							citymap.put("citys", distlist);
						}
					}
					city.put(code_string, citymap);
				}
				citylist.add(city);
				provmap.put("citys", citylist);
				map.put(prov.getAreaCode(), provmap);
				maplist.add(map);
			}
			areaJson=JsonUtil.toJson(maplist);
			return areaJson;
		}else{
			return areaJson;
		}
	}
}
