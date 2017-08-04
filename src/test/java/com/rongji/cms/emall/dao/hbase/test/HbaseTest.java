package com.rongji.cms.emall.dao.hbase.test;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

/**
 * @author Administrator
 *
 */
public class HbaseTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		System.out.println(1231231);
		System.setProperty("hadoop.home.dir", "E:/hadoop-2.5.2");
		Configuration conf=HBaseConfiguration.create();
//		System.out.println(conf.toString());
		conf.addResource("hbase/hbase-site.xml");
		conf.addResource("hbase/core-site.xml");
		System.out.println(conf.get("hbase.master"));
		
		try {
			HBaseAdmin admin=new HBaseAdmin(conf);
			for (String name : admin.getTableNames()) {
				System.out.println(name);
			}
			
			
			
		} catch (MasterNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
