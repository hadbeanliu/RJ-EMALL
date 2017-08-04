package com.rongji.cms.emall.web.areas;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongji.dfish.base.Utils;

public class UrlUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlUtil.class);
	
	private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();
	
	/**
	 * ��ȡ�İ�header��Ϣ��get����
	 * 
	 * @param url ��Ϊ��
	 * @return ��header��Ϣ
	 */
	public static HttpGet getHttpGet(String url) {
		HttpGet httpget = null;
		if (Utils.isEmpty(url)) {
			httpget = new HttpGet();
		} else {
			httpget = new HttpGet(url);
		}
		httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36");
		httpget.setHeader("Accept-Charset", "utf-8,GB2312;q=0.7,*;q=0.7");

		return httpget;
	}
	
	/**
	 * ���url��ȡҳ������Ĭ��ƥ���ַ�
	 * 
	 * @param url ��ַ
	 * @return ҳ�����ݣ���urlΪ�ջ����޷���ȡ�����򷵻�null
	 * 
	 * @see #getContentByUrl(String, boolean)
	 */
	public static String getContentByUrl(String url) {
		return getContentByUrl(url, true);
	}
	
	/**
	 * ���url��ȡҳ������֧�ֲ�ͬ�ַ�
	 * 
	 * @param url ��ַ
	 * @param matchContentType �Ƿ�ƥ���ַ�
	 * @return ҳ�����ݣ���urlΪ�ջ����޷���ȡ�����򷵻�null
	 */
	public static String getContentByUrl(String url, boolean matchContentType) {
		
		if (url != null) {
			HttpGet httpget = getHttpGet(url);

			CloseableHttpResponse response = null;
			try {
				response = HTTP_CLIENT.execute(httpget);
			} catch (ClientProtocolException e) {
				LOGGER.error("HTTPЭ�����: " + url, e);
			} catch (Exception e) {
				LOGGER.error("����ʧ��: " + url, e);
			}
			
			String content = null;
			if (response != null) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					
					byte[] bytes = null;
					Charset charset = null;
					
					
					try {
						if (matchContentType) {
							ContentType contentType = ContentType.get(entity);
							if (contentType != null) {
								charset = contentType.getCharset();
							}
							if (charset != null) {
								content = EntityUtils.toString(entity);
							} else {
								bytes = EntityUtils.toByteArray(entity);
							}
						} else {
							content = EntityUtils.toString(entity);
						}
						
					} catch (IOException e) {
						LOGGER.error("��ȡ�����쳣: "+ url, e);
					} finally {
						try {
							response.close();
						} catch (IOException e) {
							LOGGER.error("�����޷���ر�: "+ url, e);
						}
					}
					
//					if ( ! matchContentType ) {
//						return content;
//					}
					
					if (matchContentType && charset == null && bytes != null) { // �ַ����ڣ����ܴ�������
						String ct = new String(bytes);
						int headend = ct.indexOf("</head>");
						if (headend < 0) {
							headend = ct.indexOf("</HEAD>");
						}
						if (headend > 0 ) {
							int metaStart = ct.indexOf("<meta");
							if ( metaStart > 0 && metaStart < headend && ct.indexOf("charset") > metaStart ) {
								String cs = ct.substring(metaStart, headend).replaceAll("(?s).*?(?<=charset=[\\'|\\\"]?)([-\\w]+).*", "$1");
								if (Utils.notEmpty(cs)) {
									charset = Charset.forName(cs);
								}
							}
						}
						if (charset == null) {
//							charset = HTTP.DEF_CONTENT_CHARSET;
							charset = Consts.UTF_8;
						}
						int start = 0;
						int len = bytes.length;
						if (charset.equals(Consts.UTF_8)
								&& bytes[0] == (byte) 0xEF && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF) { // �Ƴ�UTF8��bom
							start = 3;
							len = len - 3;
						}
						content = new String(bytes, start, len, charset);
					}
					
					
				}
			}
			
			return content;
		}
		
		return null;
	}
	
	public static String GetContent(String html) {
		//String html = "<ul><li>1.hehe</li><li>2.hi</li><li>3.hei</li></ul>";
		String ss = ">[^<]+<[/]?span>";
		String temp = null;
		Pattern pa = Pattern.compile(ss);
		Matcher ma = null;
		ma = pa.matcher(html);
		String result = null;
		while(ma.find()){
			temp = ma.group();
			if(temp!=null){
				if(temp.startsWith(">")){
					temp = temp.substring(1);
				}
				if(temp.endsWith("<")){
					temp = temp.substring(0, temp.length()-1);
				}
				temp=getTextFromHtml(temp);
				if(!temp.equalsIgnoreCase("")){
					if(result==null){
						result = temp.replaceAll("　", ""); ;
					}
					else{
						result+=","+temp.replaceAll("　", ""); ;
					}
				}
			}
		}
		return result;
	}
	public static String getTextFromHtml(String html){
		String text = null;
		if(html!=null)
			text = html.replaceAll("<[.[^<]]*>", "")
			.replaceAll("(?i)</?(\\w+)[^>]*>", "")
			.replaceAll("(?i)<\\??xml[^>]*>", "")
			.replaceAll("\\s+", "")
	//		.replaceAll("(?i)</?strong>", "")
			.replaceAll("(?i)<p></p>", "")
			.replaceAll("(?i)</?\\w+:\\w+[^>]*>", "")
			.replaceAll("(?is)<!--.*?-->", "")
			.replaceAll("&nbsp;", "");
		return text;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = getContentByUrl("http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201504/t20150415_712722.html", true);
		String[] strs=GetContent(str).split(",");
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
		for(int i=0,j=0;i<strs.length/2;i++){
			System.out.println(areas[i][0]+":::"+areas[i][1]);
		}
	}

}
