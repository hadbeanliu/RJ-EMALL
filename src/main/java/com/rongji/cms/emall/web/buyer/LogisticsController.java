package com.rongji.cms.emall.web.buyer;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongji.cms.emall.entity.EmlCompany_;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.vo.User;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;

/**
 * 认证管理 2015-6-25
 * 
 * @author lzm
 * 
 */
@Controller
@RequestMapping("/buyer")
@RequestMappingDescription("物流信息")
public class LogisticsController extends CrudAndPageByJsonController<User>{

	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setIdColumn(EmlCompany_.id);
	}

	@Autowired
	private UserService userService;

	@Override
	protected CommonService<User, ?> getService() {
		return userService;
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@RequestMapping("/logistics")
	@RequestMappingDescription("获得物流信息json")
	public String logistics(Model model) {

		return "/dsww/001/buyer-center-logisticsdetails";
//		return "/dsww/001/buyer-logistics";
	}
	
	@RequestMapping("/get_logistics")
	@RequestMappingDescription("获得物流信息json")
	@ResponseBody
	public String getLogistics(@RequestParam String order,@RequestParam String comId,Model model) {
//		String order = "710032690831";//需传值
//		String comId = "yuantong"; //需传值
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String showapi_timestamp = sdf.format(new Date());
		String urlstr = "http://route.showapi.com/64-19?" +
				"com=" + comId +
				"&nu=" + order +
				"&showapi_timestamp="+showapi_timestamp +
				"&showapi_sign=e797238d38e94bfc9de4b3c8ef830d16" + 
				"&showapi_appid=4577";
		try
		{
			URL url = new URL(urlstr);
//			URL url = new URL(
//					"http://v.juhe.cn/exp/index?" +
//					"key=JH6b0c3feb0c0126a1913d0b276d9b3796&" +
//					"no="+no+"&com="+com);
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
				return "";

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
			urlStream.close();
			return content;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	@RequestMapping("/get_companyList")
	@RequestMappingDescription("获得物流信息json")
	@ResponseBody
	public String getCompanyList(Model model) {
//		String order = "710032690831";//需传值
//		String comId = "yuantong"; //需传值
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String showapi_timestamp = sdf.format(new Date());
		String urlstr = "http://route.showapi.com/64-20?" +
				"showapi_appid=4577" + 
				"&showapi_timestamp="+showapi_timestamp +
				"&showapi_sign=e797238d38e94bfc9de4b3c8ef830d16";
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
				return "";

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
			urlStream.close();
			return content;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
