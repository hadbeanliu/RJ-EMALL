package com.rongji.cms.emall.service.complaint.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rongji.cms.emall.service.complaint.ComplaintService;
import com.rongji.cms.emall.vo.Complaint;
import com.rongji.cms.emall.vo.MesAuditSearchQuery;
import com.rongji.rjskeleton.service.NoneDaoCommonServiceImpl;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;
@Service
public class ComplaintServiceImpl extends NoneDaoCommonServiceImpl<Complaint> implements ComplaintService{
	@Override
	public PageEntity<Complaint> getByDatatables(DatatablesMetadata metadata) {
		Gson gson=new Gson();
		String json="";
		try {
			String userId = metadata.getSearchs().get(0)[2];
			
			//String userId="0";
			json = sendGet("http://192.168.4.91:8080/cms/api/qa/qa.index?act=getMessageValueBySearch&author="+userId,"");
			List<Complaint> Complaints=gson.fromJson(json, List.class) ;
			if(Complaints.size()>0){
			PageEntity<Complaint> ppsp = new PageEntity<Complaint>(metadata.getDraw(),Complaints.size(), Complaints.size(),Complaints);
			return ppsp;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	
		
	}
	


    public static String sendGet(String url,String data) throws ClientProtocolException, IOException  
    {  
        // 创建HttpClient实例     
        HttpClient httpclient = new DefaultHttpClient();  
        // 创建Get方法实例     
        HttpGet httpgets = new HttpGet(url+data);    
        HttpResponse response = httpclient.execute(httpgets);    
        HttpEntity entity = response.getEntity();    
        if (entity != null) {    
            InputStream instreams = entity.getContent();    
            String str = convertStreamToString(instreams);  
            httpgets.abort();    
            return str;  
        }  
        return null;  
    }  
      
    public static String convertStreamToString(InputStream is) {      
        StringBuilder sb1 = new StringBuilder();      
        byte[] bytes = new byte[4096];    
        int size = 0;    
          
        try {      
            while ((size = is.read(bytes)) > 0) {    
                String str = new String(bytes, 0, size, "UTF-8");    
                sb1.append(str);    
            }    
        } catch (IOException e) {      
            e.printStackTrace();      
        } finally {      
            try {      
                is.close();      
            } catch (IOException e) {      
               e.printStackTrace();      
            }      
        }      
        return sb1.toString();      
    }

	@Override
	public MesAuditSearchQuery getdetailById(String id) {
		Gson gson=new Gson();
		String json="";
		try {
			json = sendGet("http://192.168.4.91:8080/cms/api/qa/qa.index?act=getMessageAuditBySearch&msgId="+id,"");
			MesAuditSearchQuery mesAuditSearchQuery=gson.fromJson(json, MesAuditSearchQuery.class) ;
			//PageEntity<Complaint> ppsp = new PageEntity<Complaint>(metadata.getDraw(),Complaints.size(), Complaints.size(),Complaints);
			return mesAuditSearchQuery;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}  
}
