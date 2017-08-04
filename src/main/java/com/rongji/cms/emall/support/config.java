package com.rongji.cms.emall.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.rongji.dfish.platform.permission.service.PermissionClient;
import com.rongji.dfish.platform.permission.service.impl.PermissionClientJsonImpl;
import com.rongji.dfish.platform.ums.service.impl.UmsClientJsonImpl;
import com.rongji.dfish.platform.ums.service.impl.UmsxClientJsonImpl;

public class config {
public  String getplatform() {
	InputStream in = this.getClass().getResourceAsStream("/config.properties");
	Properties properties = new Properties();
	try {
		properties.load(in);
	} catch (IOException e) {
	
		e.printStackTrace();
	}
	String clsName = properties.getProperty("platform");
	return clsName;
	
} 
public static UmsClientJsonImpl getUmsCLient() {
	UmsClientJsonImpl ums=new UmsClientJsonImpl();
	ums.setPlatformUrl("http://192.168.0.135:8888/dfish-servman/");
	ums.setAccount("DFISH");
	ums.setPassword("123ABC");
//	ums.do(roleId, userId);
	return ums;
	
}
public static UmsxClientJsonImpl getUmsXCLient() {
	UmsxClientJsonImpl umsx=new UmsxClientJsonImpl();
	umsx.setPlatformUrl("http://192.168.0.135:8888/dfish-servman/");
	umsx.setAccount("DFISH");
	umsx.setPassword("123ABC");
	return umsx;
	
}
public static PermissionClientJsonImpl getPermissionClient() {
	PermissionClientJsonImpl permission=new PermissionClientJsonImpl();
	permission.setPlatformUrl("http://192.168.0.135:8888/dfish-servman/");
	//permission.doesUserHasPermission(arg0, arg1, arg2)
	permission.setAccount("DFISH");
	permission.setPassword("123ABC");
	return permission;
	
}
}
