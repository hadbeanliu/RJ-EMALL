package com.rongji.cms.emall.modules.log;

//import static com.rongji.dfish.util.Utils.notEmpty;

//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.UUID;
//
//import org.apache.log4j.Logger;
//import org.dom4j.Element;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.rongji.cms.commons.ConfigConstants;
//import com.rongji.cms.commons.ObjectTypes;
//import com.rongji.cms.commons.OperateTypes;
//import com.rongji.cms.commons.ThreadLocalFactory;
//import com.rongji.cms.persistence.CmsLog;
//import com.rongji.cms.service.ServiceLocator;
//import com.rongji.cms.webapp.system.business.CodeListMethods;
//import com.rongji.dfish.framework.ConfigNode;
//import com.rongji.dfish.framework.SystemData;
//import com.rongji.dfish.util.SingleThreadRunner;
//import com.rongji.dfish.util.Utils;
//import com.rongji.dfish.util.XMLTools;

/**
 * 日志工具类
 * @author jayfans
 *
 */
public class LogUtil {
//	
//	//添加的包含代码
////	String preGroupIdUUID = LogUtil.setLogGroupId();
////	try {
////		//Content
////		
////		log.setLogType(""+LogType.LOGIC_OPERATION.getId());
////		log.setLogId(FrameworkHelper.getNewId("CmsLog", "logId", "00000001"));
////		//绑定日志组编号
////		if(LogUtil.hasLogGroupId()){
////			//添加logId
////			LogUtil.addLogId(log.getLogId(),LogType.LOGIC_OPERATION);
////		}
////		
////	}finally{
////		//处理组ID
////		LogUtil.associateLogGroupId(LogType.LOGIC_OPERATION);
////		//恢复之前组ID
////		ThreadLocalFactory.put(LogUtil.LOG_GROUP_KEY, preGroupIdUUID);
////	}
////	
//	
//	
//	public static void main(String[] args) throws ClassNotFoundException {
//		//20130112000001
////		for(int i=1;i<1000;i++){
////			System.out.println(""+(20130112000001l+i));
////		}
////		System.out.println(UUID.randomUUID().toString().replace("-",""));
////		SystemData.getInstance().setLogConfig(new XMLTools("D:\\MyEclipse6.5Workspace\\RJ-CMS7\\WebRoot\\WEB-INF\\config\\cms_log_config.xml"));
////		CmsArticle ca = new CmsArticle();
////		boolean logEntity = isLogEntity(ca,LogUtil.LOG_OP_UPDATE);;
////		long start = System.currentTimeMillis();
////		for(int i=0;i<10000;i++){
////			logEntity = isLogEntity(ca,LogUtil.LOG_OP_UPDATE);
////		}
////		System.out.println(""+(System.currentTimeMillis()-start));
////		System.out.println(logEntity);
////		boolean logField = false;
////		start = System.currentTimeMillis();
////		for (int i = 0; i < 10000; i++) {
////			logField = isLogField(ca.getClass().getName(), "__"+i, LOG_OP_DELETE);
////		}
////		System.out.println(""+(System.currentTimeMillis()-start));
////		start = System.currentTimeMillis();
////		for (int i = 0; i < 10000; i++) {
////			logField = isLogField(ca.getClass().getName(), "__"+i, LOG_OP_DELETE);
////		}
////		System.out.println(""+(System.currentTimeMillis()-start));
////		System.out.println(logEntity);
////		System.out.println(logField);
////		long start = System.currentTimeMillis();
////		LogParsedContent logParsedContent = new LogParsedContent();
////		String json= "122342342342342342342342342333333332222222222222222222222222222222222222222222222222222222222222222222122342342342342342342342342333333332222222222222222222222222222222222222222222222222222222222222222222122342342342342342342342342333333332222222222222222222222222222222222222222222222222222222222222222222122342342342342342342342342333333332222222222222222222222222222222222222222222222222222222222222222222122342342342342342342342342333333332222222222222222222222222222222222222222222222222222222222222222222122342342342342342342342342333333332222222222222222222222222222222222222222222222222222222222222222222122342342342342342342342342333333332222222222222222222222222222222222222222222222222222222222222222222122342342342342342342342342333333332222222222222222222222222222222222222222222222222222222222222222222122342342342342342342342342333333332222222222222222222222222222222222222222222222222222222222222222222122342342342342342342342342333333332222222222222222222222222222222222222222222222222222222222222222222";
////		logParsedContent.setCustomField("all", json);
////		logParsedContent.setCustomField("all2", json);
////		logParsedContent.setCustomField("all3", json);
////		logParsedContent.setCustomField("field1", json);
////		logParsedContent.setCustomField("field2", json);
////		logParsedContent.setCustomField("field3", json);
////		logParsedContent.setCustomField("field4", json);
////		json = gson.toJson(logParsedContent);
////		System.out.println(json);
////		for (int i = 0; i < 100000; i++) {
////			if(i==1){
////				
////			}else{
////				
////			}
////	    	try {
////	    		//logParsedContent = gson.fromJson(json,new TypeToken<LogParsedContent>(){}.getType());
////			} catch (Exception e) {
////				//System.out.println("GSON解析日志内容出现异常信息："+e.getMessage());
////			}
////		}
////		System.out.println(""+(System.currentTimeMillis()-start));
////		boolean isEqual = false;
////		Double db = null;
////		String str = null;
////		Integer integer = new Integer("123");
////		Date date = new Date();
////		for(int i=0;i<10000;i++){
////			isEqual = isFieldEqual(123,integer);
////		}
////		System.out.println(isEqual);
////		System.out.println(""+(System.currentTimeMillis()-start));
//	}
//	//log4j
//	private static final Logger logger = Logger.getLogger(LogUtil.class);
//	
//	//GSON解析对象
//	private static Gson gson = new Gson();
//	
//	//配置缓存
//	private static Map<String,Map<String,String>> auditEntityConfig = null;
//	
//	//配置缓存--POJO
//	private static Map<String,Boolean> auditEntityConfigCache = new HashMap<String,Boolean>();
//	
//	//配置缓存--字段
//	private static Map<String,Boolean> auditFieldConfigCache = new HashMap<String,Boolean>();
//	
//	//日志时间格式化对象
//	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	
//	//审计日志字段策略属性名
//	private static final String TACTICS = "tactics";
//	
//	//审计日志操作之INSERT
//	public static final String LOG_OP_INSERT = "INSERT";
//	
//	//审计日志操作之UPDATE
//	public static final String LOG_OP_UPDATE = "UPDATE";
//	
//	//审计日志操作之DELETE
//	public static final String LOG_OP_DELETE = "DELETE";
//	
//	//允许或不允许全部时，指定all即可 
//    public static final String ALL = "ALL";  
//    
//    //日志组关键字
//    public static final String LOG_GROUP_KEY = "log_group_id";
//    
//    //日志组单线程执行者关键字
//    public static final String LOG_GROUP_SINGLE_THREAD_KEY = "log_group_SingleThreadRunner";
//    
//    /**
//     * 根据日志获取日志内容解析对象
//     * @param log
//     * @return
//     */
//    public static LogParsedContent getLogParsedContent(CmsLog log){
//    	if(log==null||Utils.isEmpty(log.getLogId())||Utils.isEmpty(log.getLogContent())){
//    		return null;
//    	}
//    	Map<String,LogParsedContent> cache = (Map<String,LogParsedContent>)ThreadLocalFactory.get("CmsLogParseContentCache");
//    	if(cache==null){
//    		cache = new HashMap<String, LogParsedContent>();
//    		ThreadLocalFactory.put("CmsLogParseContentCache",cache);
//    	}
//    	LogParsedContent logParsedContent2 = cache.get(log.getLogId());
//    	if(logParsedContent2!=null){
//    		return logParsedContent2;
//    	}
//    	LogParsedContent logParsedContent = null;
//    	try {
//    		logParsedContent = gson.fromJson(log.getLogContent(),new TypeToken<LogParsedContent>(){}.getType());
//    		logParsedContent.setParserFlag(LogParsedContent.PARSER_FLAG_SUCCESS);
//		} catch (Exception e) {
//			logParsedContent = new LogParsedContent();
//			logParsedContent.setLogDefaultContent(log.getLogContent());
//			logParsedContent.setParserFlag(LogParsedContent.PARSER_FLAG_FAILURE);
//			logger.warn("[LOG_ID:"+log.getLogId()+"] GSON解析日志内容出现异常信息："+e.getMessage());
//		}
//		cache.put(log.getLogId(), logParsedContent);
//		return logParsedContent;
//    }
//    
//    
//    /**
//     * 获取日志显示标题
//     * @param logContent
//     * @return
//     */
//    public static String getLogTitle(CmsLog log){
//    	if(log==null){
//    		return "日志不存在！";
//    	}
//    	String logContent = log.getLogContent();
//    	if(Utils.isEmpty(logContent)){
//    		return "日志内容为空！";
//    	}
//    	String logTitle = null;
//    	LogParsedContent logParsedContent = getLogParsedContent(log);
//		int logTypeId =0;
//		try {
//			logTypeId = Integer.parseInt(log.getLogType());
//		} catch (Exception e) {
//			logger.warn("[LOG_ID:"+log.getLogId()+"] logType:["+log.getLogType()+"]转化为整型数值失败："+e.getMessage());
//		}
//		LogType logType = LogType.findById(logTypeId);
//    	if(LogParsedContent.PARSER_FLAG_SUCCESS.equals(logParsedContent.getParserFlag())){
//    		//TODO:解析日志内容，组装标题
//    		if(LogType.SYS_OPERATION == logType){//操作日志
//    			LogType.OpertionContent lo = new LogType.OpertionContent();
//    			lo.setCustomFields(logParsedContent.getCustomFields());
//    			int typeId = 0;
//    			try {
//    				typeId = Integer.parseInt(log.getLogType());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				String showUpdateDetail = "";
//				//操作日志之更新操作
//    			if(OperateTypes.OPER_UPDATE.equals(log.getLogOpType())){
//    				showUpdateDetail = "&nbsp;<a href='javascript:void(0);' title='查看操作日志详细信息' onclick=\"VM(this).cmd('showLogDetail','"+log.getLogId()+"','"+(log.getGroupId()!=null?log.getGroupId():"")+"')\" ><img style='margin-top:-5px;' src='img/b/logDetail.gif' /></a>";
//    			}
//    			String operTitle = "["+lo.getOperUserName()+"]"+CodeListMethods.getCodeNameByTypeAndCode("0011", log.getLogOpType())+ObjectTypes.getObjName(null,log.getLogObjType());
//    			//FIXME:组装标题
//    			if(ObjectTypes.OBJ_TYPE_ARTICLE.getId().equals(log.getLogObjType())){//文章操作日志时
//    				String title = "";
//    				if(Utils.notEmpty(log.getLogObjId())){
//        				if(log.getLogObjId().contains("CAAR")){
//        					title = "[<a href='javascript:void(0);' title='"+lo.getTargetName().replaceAll("'", "‘").replaceAll("\"", "“")+"' onclick=\"VM(this).cmd('openPreview','','"+log.getLogObjId().replace("CAAR", "")+"');\" >"+lo.getTargetName()+"</a>]";
//        				}else{
//        					title = "[<a href='javascript:void(0);' title='"+lo.getTargetName().replaceAll("'", "‘").replaceAll("\"", "“")+"' onclick=\"VM(this).cmd('openPreview','"+log.getLogObjId()+"','');\" >"+lo.getTargetName()+"</a>]";
//        				}
//    				}
//    				logTitle = ""+operTitle+title+showUpdateDetail;
//    			}else if(ObjectTypes.OBJ_TYPE_MESSAGE.getId().equals(log.getLogObjType())||
//    					ObjectTypes.OBJ_TYPE_MESSAGE_HANDLE.getId().equals(log.getLogObjType())){//留言操作日志
//    				String title = "";
//    				if(log.getLogOpType()!=null&&!log.getLogOpType().contains("DEL")&&Utils.notEmpty(log.getLogObjId())){
//    					title = "[<a href='javascript:void(0);' title='"+lo.getTargetName().replaceAll("'", "‘").replaceAll("\"", "“")+"' onclick=\"VM(this).cmd('openMsgPreview','"+log.getLogObjId()+"','');\" >"+lo.getTargetName()+"</a>]";
//        				
//    				}else{
//    					title = "["+lo.getTargetName()+"]";
//    				}
//    				logTitle = ""+operTitle+title+showUpdateDetail;	
//    				
//    			}else{
//    				logTitle = ""+operTitle+"["+lo.getTargetName()+"]"+showUpdateDetail;
//    			}
//    		}else if(LogType.LOGIC_OPERATION == logType){//逻辑操作日志
//    			LogType.OpertionContent lo = new LogType.OpertionContent();
//    			lo.setCustomFields(logParsedContent.getCustomFields());
//    			String operName = lo.getCustomField("OperName");
//    			if(Utils.isEmpty(operName)){
//    				operName = CodeListMethods.getCodeNameByTypeAndCode("0011", log.getLogOpType())+ObjectTypes.getObjName(null,log.getLogObjType());
//    			}
//    			//FIXME:组装标题
//    			if(ObjectTypes.OBJ_TYPE_ARTICLE.getId().equals(log.getLogObjType())){//文章操作日志时
//    				String title = "";
//    				if(Utils.notEmpty(log.getLogObjId())){
//    					if(log.getLogObjId().contains("CAAR")){
//        					title = "[<a href='javascript:void(0);' title='"+lo.getTargetName().replaceAll("'", "‘").replaceAll("\"", "“")+"' onclick=\"VM(this).cmd('openPreview','','"+log.getLogObjId().replace("CAAR", "")+"');\" >"+lo.getTargetName()+"</a>]";
//        				}else{
//        					title = "[<a href='javascript:void(0);' title='"+lo.getTargetName().replaceAll("'", "‘").replaceAll("\"", "“")+"' onclick=\"VM(this).cmd('openPreview','"+log.getLogObjId()+"','');\" >"+lo.getTargetName()+"</a>]";
//        				}
//    				}
//    				logTitle = "["+lo.getOperUserName()+"]"+operName+title;
//    			}else if(ObjectTypes.OBJ_TYPE_MESSAGE.getId().equals(log.getLogObjType())||
//    					ObjectTypes.OBJ_TYPE_MESSAGE_HANDLE.getId().equals(log.getLogObjType())){//留言操作日志
//    				String title = "";
//    				if(log.getLogOpType()!=null&&!log.getLogOpType().contains("DEL")&&Utils.notEmpty(log.getLogObjId())){
//    					title = "[<a href='javascript:void(0);' title='"+lo.getTargetName().replaceAll("'", "‘").replaceAll("\"", "“")+"' onclick=\"VM(this).cmd('openMsgPreview','"+log.getLogObjId()+"','');\" >"+lo.getTargetName()+"</a>]";
//        				
//    				}else{
//    					title = "["+lo.getTargetName()+"]";
//    				}
//    				logTitle = "["+lo.getOperUserName()+"]"+operName+title;
//    			}else{
//    				System.out.println("operName:"+lo.getTargetName());
//    				logTitle = "["+lo.getOperUserName()+"]"+operName+"["+lo.getTargetName()+"]";
//    			}
//        		if(LogType.LOGIC_OPERATION == logType&&Utils.notEmpty(log.getGroupId())&&!"0".equals(log.getGroupId())){//逻辑操作日志
//        			if(log.getLogOpType()!=null&&log.getLogOpType().contains("ADD")||log.getLogOpType().contains("DEL")){//
////        				logTitle += "&nbsp;&nbsp;<a href='javascript:void(0);' onclick=\"VM(this).cmd('showLogDetail','"+log.getLogId()+"','"+(log.getGroupId()!=null?log.getGroupId():"")+"')\" ><img src='img/b/logDetail.gif' /></a>";
//        			}else{
//        				logTitle += "&nbsp;<a href='javascript:void(0);' title='查看操作日志详细信息' onclick=\"VM(this).cmd('showLogDetail','"+log.getLogId()+"','"+(log.getGroupId()!=null?log.getGroupId():"")+"')\" ><img style='margin-top:-5px;' src='img/b/logDetail.gif' /></a>";//+logTitle;
////        				logTitle += "&nbsp;&nbsp;<a href='javascript:void(0);' onclick=\"VM(this).cmd('showLogDetail','"+log.getLogId()+"','"+(log.getGroupId()!=null?log.getGroupId():"")+"','simple')\" ><img src='img/b/logDetail.gif' /></a>";
//        			}
//        		}
//    		}else{//未知日志
//    			logTitle = logParsedContent.getLogDefaultContent();
//    		}
//    	}else{
//    		logTitle = logParsedContent.getLogDefaultContent();
//    		if(LogType.LOGIC_OPERATION == logType&&Utils.notEmpty(log.getGroupId())&&!"0".equals(log.getGroupId())){//逻辑操作日志
//    			if(log.getLogOpType()!=null&&log.getLogOpType().contains("ADD")||log.getLogOpType().contains("DEL")){//
////    				logTitle += "&nbsp;&nbsp;<a href='javascript:void(0);' onclick=\"VM(this).cmd('showLogDetail','"+log.getLogId()+"','"+(log.getGroupId()!=null?log.getGroupId():"")+"')\" ><img src='img/b/logDetail.gif' /></a>";
//    			}else{
//    				logTitle += "&nbsp;<a href='javascript:void(0);' title='查看操作日志详细信息' onclick=\"VM(this).cmd('showLogDetail','"+log.getLogId()+"','"+(log.getGroupId()!=null?log.getGroupId():"")+"')\" ><img style='margin-top:-5px;' src='img/b/logDetail.gif' /></a>";//+logTitle;
////    				logTitle += "&nbsp;&nbsp;<a href='javascript:void(0);' onclick=\"VM(this).cmd('showLogDetail','"+log.getLogId()+"','"+(log.getGroupId()!=null?log.getGroupId():"")+"','simple')\" ><img src='img/b/logDetail.gif' /></a>";
//    			}
//    		}
//    	}
//    	return logTitle;
//    }
//    
//    /**
//     * 向ThreadLocal设置组ID
//     * @return
//     */
//    public static String setLogGroupId(){
//    	//设置UUID
//    	String currGroupId = UUID.randomUUID().toString().replace("-","");
//    	String preGroupIdUUID = (String) ThreadLocalFactory.put(LOG_GROUP_KEY, currGroupId);
//    	ThreadLocalFactory.registerSingleThreadRunner(currGroupId+LOG_GROUP_SINGLE_THREAD_KEY);
//    	return preGroupIdUUID;
//    }
//    
//    
//    /**
//     * 判断当前线程是否存在日志组编号 
//     * @return
//     */
//    public static boolean hasLogGroupId(){
//    	String currGroupId = (String)ThreadLocalFactory.get(LOG_GROUP_KEY);
//    	return Utils.notEmpty(currGroupId);
//    }
//    
//    /**
//     * 添加运行对象到日志单线程队列(策略：FIFO)
//     * @param taskRunnable
//     */
//    public static void pushLogTaskQueue(Runnable taskRunnable){
//    	if(taskRunnable==null){
//    		return ;
//    	}
//    	String currGroupId = (String)ThreadLocalFactory.get(LOG_GROUP_KEY);
//    	if(Utils.notEmpty(currGroupId)){
//    		SingleThreadRunner str = (SingleThreadRunner)ThreadLocalFactory.getRegSingleThreadRunner(currGroupId+LOG_GROUP_SINGLE_THREAD_KEY);
//    		if(str!=null){
//    			str.add(taskRunnable);
//    		}else{
//    			logger.error("当前日志组编号单线程执行器为空！无法添加任务队列！");
//    		}
//    	}else{
//    		logger.error("无法添加任务到当前日志组编号单线程执行器！");
//    	}
//    }
//    
//    /**
//     * 绑定日志组ID,必须包含指定类型，如果不包含，则不绑定
//     * @param containerType 指定类型
//     * @return
//     * @throws Exception 
//     */
//    public static String associateLogGroupId(LogType containerType) throws Exception{
//    	final String  currGroupId = (String)ThreadLocalFactory.get(LOG_GROUP_KEY);
//    	if(Utils.isEmpty(currGroupId)){
//    		return null;
//    	}
//    	if(containerType==null){
//    		return currGroupId;
//    	}
//    	Map<String,CmsLog> logIdsMap = new HashMap<String, CmsLog>();
//    	List<CmsLog> logs = (List<CmsLog>)ThreadLocalFactory.get(currGroupId);
//		if(Utils.notEmpty(logs)&&logs.size()>1){
//			boolean hasType = false;
//			for(CmsLog log : logs){
//				if(Utils.isEmpty(log.getLogId())){
//					hasType = false;
//					logger.error("无法绑定日志关系，存在logId为空(未保存成功)的日志！");
//					break;
//				}
//				logIdsMap.put(log.getLogId(), log);
//				if(log.getLogType().equals(""+containerType.getId())){
//					hasType = true;
//				}
//			}
//			List<String> list = new ArrayList<String>();
//			for(Iterator it = logIdsMap.keySet().iterator();it.hasNext();){
//				String next = (String)it.next();
//				list.add(next);
//			}
//			final List<String> finalLogIds = list;
//			if(hasType){//
//				//绑定日志关系
//				LogUtil.pushLogTaskQueue(new Runnable(){
//					@Override
//					public void run() {
//						try {
//							int associateLogs = ServiceLocator.getSysLogService().associateLogs(finalLogIds,currGroupId);
//							if(associateLogs!=finalLogIds.size()&&associateLogs>0){//如果绑定条数不一致,则清除绑定
//								int unAssociateLogs = ServiceLocator.getSysLogService().unAssociateLogs(currGroupId);
//								if(unAssociateLogs<=0){
//									logger.error("解除日志绑定失败!将导致脏数据!请手动设置 groupId="+currGroupId +" 的日志groupId=''！");
//								}
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				});
//			}
//		}
//		ThreadLocalFactory.put(currGroupId,null);
//		ThreadLocalFactory.put(LOG_GROUP_KEY, null);
//		return currGroupId;
//    }
//    
//    /**
//     * 添加日志到当前组ID中去
//     * @param logId
//     */
//    public static void addCmsLog2Group(CmsLog log){
//    	if(log == null){
//    		return ;
//    	}
//    	String currGroupId = (String)ThreadLocalFactory.get(LOG_GROUP_KEY);
//		List<CmsLog> logs = (List<CmsLog>)ThreadLocalFactory.get(currGroupId);
//		if(logs==null){
//			logs = new ArrayList<CmsLog>();
//			ThreadLocalFactory.put(currGroupId,logs);
//		}
//		logs.add(log);
//    }
//    
//    /**
//     * 清除组ID
//     */
//    public static void clearLogGroupId(){
//    	String currGroupId = (String)ThreadLocalFactory.get(LOG_GROUP_KEY);
//    	ThreadLocalFactory.put(currGroupId,null);
//		ThreadLocalFactory.put(LOG_GROUP_KEY, null);
//    }
//    
//    /**
//     * 判断是否要记录审计日志
//     * @param object
//     * @return
//     */
//    public static boolean isLogEntity(Object object){
//    	if(object !=null){
//    		return isLogEntity(object.getClass().getName());
//    	}
//    	return false;
//    }
//    
//    /**
//     * 判断是否要记录审计日志
//     * @param object
//     * @return
//     */
//    public static boolean isLogEntity(Object object,String op){
//    	if(object !=null){
//    		return isLogEntity(object.getClass().getName(),op);
//    	}
//    	return false;
//    }
//    
//    /**
//     * 判断是否要记录审计日志
//     * @param classFullName
//     * @return
//     */
//    public static boolean isLogEntity(String classFullName){
//    	if(Utils.notEmpty(classFullName)){
//    		Boolean boolean1 = auditEntityConfigCache.get(classFullName);
//    		if(boolean1!=null){
//    			return boolean1.booleanValue();
//    		}
//    		Map<String, Map<String,String>> auditEntityConfig = getAuditEntityConfig();
//    		boolean containsKey = auditEntityConfig.containsKey(classFullName);
//    		auditEntityConfigCache.put(classFullName, containsKey);
//    		return containsKey;
//    	}
//    	return false;
//    }
//	/**
//	 * 判断是否要记录审计日志
//	 * @param classFullName
//	 * @param LogUtil.OP
//	 * @return
//	 */
//	public static boolean isLogEntity(String classFullName,String op){
//		if(Utils.isEmpty(classFullName)||Utils.isEmpty(op)){
//			return false;
//		}
//		boolean isLogEntyty = false;
//		if(isLogEntity(classFullName)){
//			Boolean boolean1 = auditEntityConfigCache.get(classFullName+"_"+op);
//    		if(boolean1!=null){
//    			return boolean1.booleanValue();
//    		}
//			Map<String, Map<String,String>> auditEntityConfig = getAuditEntityConfig();
//			Map<String, String> config = auditEntityConfig.get(classFullName);
//			if(config!=null){
//				if(config.containsKey(op.toLowerCase() + "Allow")){
//					isLogEntyty = true;
//				}else if(config.containsKey(op.toLowerCase() + "Deny")&&!"ALL".equals(config.get(op.toLowerCase() + "Deny_"+TACTICS))){
//					isLogEntyty = true;
//				}
//			}
//			auditEntityConfigCache.put(classFullName+"_"+op, isLogEntyty);
//		}
//		return isLogEntyty;
//	}
//	
//	/**
//	 * 判断基个类的字段是否可审计
//	 * @param classFullName
//	 * @param fieldName
//	 * @param logOp
//	 * @return
//	 */
//    public static boolean isLogField(String classFullName,String fieldName,String logOp) {  
//    	boolean isLog = false;
//    	if(isLogEntity(classFullName)){
//    		String fieldCacheKey = classFullName+"."+fieldName+"_"+logOp;
//        	Boolean boolean1 = auditFieldConfigCache.get(fieldCacheKey);
//        	if(boolean1!=null){
//        		return boolean1.booleanValue();
//        	}
//        	Map<String,String> entityConfig = auditEntityConfig.get(classFullName);  
//            if(entityConfig != null){
//            	String tactics = entityConfig.get(logOp.toLowerCase() + "Allow_"+TACTICS);  
//                if(tactics!=null){  
//                	String allowFields = entityConfig.get(logOp.toLowerCase() + "Allow");  
//                    if(ALL.equals(tactics)
//                            || containsField(allowFields,fieldName)){  
//                        //配置ALL，所有允许  
//                    	isLog = true;  
//                    }
//                }else{ 
//                	tactics = entityConfig.get(logOp.toLowerCase() + "Deny_"+TACTICS);  
//                    if(tactics != null){  
//                    	String denyFields = entityConfig.get(logOp.toLowerCase() + "Deny");  
//                        if(ALL.equals(tactics)  
//                                || containsField(denyFields,fieldName)){  
//                            //配置ALL，所有不允许  
//                        	isLog = false;  
//                        }else{
//                        	isLog = true;
//                        }
//                    }
//                }  
//            }
//            auditFieldConfigCache.put(fieldCacheKey, isLog);
//    	}
//        //缺省不记录  
//        return isLog;  
//    }  
//	
//	/**
//	 * 获取审计日志配置
//	 * @return
//	 */
//	public static Map<String,Map<String,String>> getAuditEntityConfig(){
//		if(auditEntityConfig!=null){
//			return auditEntityConfig;
//		}
//		auditEntityConfig = new HashMap<String,Map<String,String>>();
//		ConfigNode auditEntitysConfigNode = ConfigConstants.getSystemConfigNode("cms.config.auditEntitys");
////		List<Element> selectNodes = SystemData.getInstance().getLogConfig().getDoc().selectNodes("/config/auditEntitys/auditEntity");
//		if(auditEntitysConfigNode!=null){
//			for(ConfigNode configNode : auditEntitysConfigNode.getSubNodes()){
//				String className = configNode.getAttributeValue("class");
//				String enable = configNode.getAttributeValue("enable");
//				if(Utils.notEmpty(className)&&"true".equals(enable)){
//					List<ConfigNode> elements = configNode.getSubNodes();
//					Map<String,String> config = null;
//					if(Utils.notEmpty(elements)){
//						config = new HashMap<String, String>();
//						for(ConfigNode el : elements){
//							config.put(el.getName()+"_"+TACTICS, el.getAttributeValue(TACTICS));
//							if(Utils.notEmpty(el.getSubNodes())){//字段配置
//								config.put(el.getName(), el.getSubNodes().get(0).getValue());
//							}else{
//								config.put(el.getName(), "");
//							}
//						}
//					}
//					auditEntityConfig.put(className.trim(),config);
//				}
//			}
//		}
//		return auditEntityConfig;
//	}
//	
//    /** 
//     * 配置中是否包含当前字段 
//     * @Title: containsField  
//     * @param fields 
//     * @param field 
//     * @return boolean 
//     * @throws 
//     */ 
//    private static boolean containsField(String fields, String field) {  
//    	if(Utils.isEmpty(fields)){
//    		return false;
//    	}
//        String[] fs = fields.split(",");  
//        for(String f : fs){  
//            if(f.equals(field)){  
//                return true;  
//            }  
//        }  
//        return false;  
//    } 
//    
//    /**
//     * 判断字段值是否相等
//     * @param oldFieldValue
//     * @param newFieldValue
//     * @return boolean
//     */
//    public static boolean isFieldEqual(Object oldFieldValue ,Object newFieldValue){
//    	if(oldFieldValue==null&&newFieldValue==null){
//    		return true;
//    	}else if(oldFieldValue!=null&&newFieldValue==null){
//    		if(oldFieldValue instanceof String){
//    			if(oldFieldValue.toString().length()<=0){
//    				return true;
//    			}
//    		}
//    		return false;
//    	}else if(oldFieldValue==null&&newFieldValue!=null){
//    		if(newFieldValue instanceof String){
//    			if(newFieldValue.toString().length()<=0){
//    				return true;
//    			}
//    		}
//    		return false;
//    	}else{
//    		if(oldFieldValue instanceof Timestamp){
//    			oldFieldValue = ((Timestamp)oldFieldValue).getTime();
//    		}else if(oldFieldValue instanceof Date){
//    			oldFieldValue = ((Date)oldFieldValue).getTime();
//    		}
//    		if(newFieldValue instanceof Timestamp){
//    			newFieldValue = ((Timestamp)newFieldValue).getTime();
//    		}else if(newFieldValue instanceof Date){
//    			newFieldValue = ((Date)newFieldValue).getTime();
//    		}
//    		return oldFieldValue.equals(newFieldValue)||newFieldValue == oldFieldValue;
//    	}
//    }
//	/**
//	 * 是否支持操作日志记录的对象类型（不支持SET,LIST元素，即one to many字段）
//	 * @param object
//	 * @return
//	 */
//    public static boolean isSupportObjectType(Object object){
//    	//TODO:不支持SET，LIST hibernate元素，会报错，可能是懒加载的问题，但具体还不清楚 by linzhiwei 2013-01-15
//    	if(object instanceof Set||object instanceof List){
//    		return false;
//    	}
//    	return true;
//    }
////	/**
////	 * 取得日志配置，
////	 * 
////	 * @param key
////	 *            关键字 如sysarg.attach.freetaskAttachPath等
////	 * @param defaultValue
////	 *            默认值。如果系统配置信息没有的时候，则显示默认值
////	 * @return
////	 */
////	public static String getLogConfig(String key, String defaultValue) {
////		String value = SystemData.getInstance().getLogConfig().getProperty(key);// properties.getProperty(key);
////		if (notEmpty(value))
////			return value;
////		return defaultValue;
////	}
//
////	/**
////	 * 将系统配置保存至配置文件中（xml）
////	 * 
////	 * @param key
////	 * @param value
////	 */
////	public static void setLogConfig(String key, String value) {
////		SystemData.getInstance().getLogConfig().setProperty(key, value);
////		auditEntityConfig = null;
////		auditFieldConfigCache = new HashMap<String, Boolean>();
////		auditEntityConfigCache = new HashMap<String, Boolean>();
////	}
//
//	/**
//	 * 更新日志配置缓存
//	 * 
//	 */
//	public static void resetLogConfig() {
//		auditEntityConfig = null;
//		auditFieldConfigCache = new HashMap<String, Boolean>();
//		auditEntityConfigCache = new HashMap<String, Boolean>();
//		System.out.println("重置log配置文件成功！");
//	}
}
