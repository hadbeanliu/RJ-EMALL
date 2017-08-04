package com.rongji.cms.emall.service.pay.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

import com.rongji.cms.emall.vo.Payment;

public class PayUtil {
	
	public static byte[] getCertByte(Payment payment) {
		if (payment != null && payment.getCertFile() != null && !payment.getCertFile().isEmpty()) {
			InputStream certFile = null;
			byte[] cert = null;
			
			try {
				certFile = payment.getCertFile().getInputStream();
				cert = IOUtils.toByteArray(certFile);
			} catch (IOException e) {
				throw new PayException("支付配置信息证书读取失败: " + payment.getCertFile().getOriginalFilename(), e);
			} finally {
				if (certFile != null) {
					try {
						certFile.close();
					} catch (IOException e) {
						throw new PayException("支付配置信息证书文件关闭失败: " + payment.getCertFile().getOriginalFilename(), e);
					}
				}
			}
			
			if (cert == null) {
				throw new PayException("支付配置信息证书不允许为空");
			}
			
			return cert;
		}
		
		return null;
	}
	
	public static String getCert(Payment payment) {
		if (payment != null && payment.getCertFile() != null && !payment.getCertFile().isEmpty()) {
			InputStream certFile = null;
			String cert = null;
			
			try {
				certFile = payment.getCertFile().getInputStream();
				InputStreamReader is=new InputStreamReader(certFile);
				BufferedReader br=new BufferedReader(is);
				String read = null;
				StringBuilder sb = new StringBuilder();
				while((read = br.readLine()) != null) {
					sb.append(read);
				}
				cert = sb.toString();
			} catch (IOException e) {
				throw new PayException("支付配置信息证书读取失败: " + payment.getCertFile().getOriginalFilename(), e);
			} finally {
				if (certFile != null) {
					try {
						certFile.close();
					} catch (IOException e) {
						throw new PayException("支付配置信息证书文件关闭失败: " + payment.getCertFile().getOriginalFilename(), e);
					}
				}
			}
			
			if (cert == null || cert.trim().isEmpty()) {
				throw new PayException("支付配置信息证书不允许为空");
			}
			
		}
		
		return null;
	}

}
