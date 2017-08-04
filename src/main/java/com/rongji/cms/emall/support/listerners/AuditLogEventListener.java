package com.rongji.cms.emall.support.listerners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

//import com.rongji.cms.emall.modules.log.LogUtil;

/**
 * <h1>审计日志事件监听类</h1>
 * <p>监听配置的POJO类，并记录详细操作日志</p>
 * @author jayfans
 * @version 1.0
 * @since 2013-01-06 
 */
public class AuditLogEventListener implements PreInsertEventListener,PostInsertEventListener,
	PreUpdateEventListener,PostUpdateEventListener,
	PreDeleteEventListener,PostDeleteEventListener{
	
	private static final long serialVersionUID = -8284201456876206569L;
	//审计日志之 插入操作
	public static final String OP_INSERT = "INSERT";  
	//审计日志之 更新操作
	public static final String OP_UPDATE = "UPDATE";  
	//审计日志之 删除操作
	public static final String OP_DELETE = "DELETE";  
     
    /** 
     * 允许或不允许全部时，指定all即可 
     */  
    public static final String ALL = "all";  
      
    private static final Log logger = LogFactory.getLog(AuditLogEventListener.class);  
    

    /**
     * 插入之前
     */
	public boolean onPreInsert(PreInsertEvent event) {
//		System.out.println("iiiiiiii");
		return false;
	}

	/**
	 * 插入之后
	 */
	public void onPostInsert(PostInsertEvent event) {
		long start = System.currentTimeMillis();
		try {
//			if(LogUtil.isLogEntity(event.getEntity(),LogUtil.LOG_OP_INSERT)) {
////				ServiceLocator.getSysLogService().logInsertAuditLog(event);
//			}
//			System.out.println("pppppppppp");
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("插入用时："+(System.currentTimeMillis()-start)+"ms");
	}
    
	/**
	 * 更新处理之前（更新事务还没提交）
	 */
	public boolean onPreUpdate(PreUpdateEvent event) {
//		if(LogUtil.isLogEntity(event.getEntity())) {
//			//继承了审计日志详细接口
//			if(event.getEntity() instanceof AuditLogDetail){
//				if(Utils.notEmpty(event.getOldState())){//旧数据不为空时
//					//TODO:预先去查询数据库，把数据库中的旧值查出来
//				}
//			}
//		}
		return false;
	}
	
	/**
	 * 更新处理之后（更新事务已经提交）
	 */
	public void onPostUpdate(PostUpdateEvent event) {
		long start = System.currentTimeMillis();
		try {
//			if(LogUtil.isLogEntity(event.getEntity())) {
////				ServiceLocator.getSysLogService().logUpdateAuditLog(event);
//			}
//			System.out.println("uuuuuuuu");
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("更新用时："+(System.currentTimeMillis()-start)+"ms");
	}

	/**
	 * 删除处理之前（删除事务还没提交）
	 */
	public boolean onPreDelete(PreDeleteEvent event) {
		return false;
	}
	
	/**
	 * 删除处理之后（删除事务已经提交）
	 */
	public void onPostDelete(PostDeleteEvent event) {
		long start = System.currentTimeMillis();
		try {
//			if(LogUtil.isLogEntity(event.getEntity(),LogUtil.LOG_OP_DELETE)) {
////				ServiceLocator.getSysLogService().logDeleteAuditLog(event);
//			}
//			System.out.println("ddddddd");
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("删除用时："+(System.currentTimeMillis()-start)+"ms");
	}

	@Override
	public boolean requiresPostCommitHanding(EntityPersister persister) {
		// TODO Auto-generated method stub
		return true;
	}

}