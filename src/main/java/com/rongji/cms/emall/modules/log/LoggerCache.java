package com.rongji.cms.emall.modules.log;

//import org.apache.log4j.Logger;

public class LoggerCache {
//	static Logger logger = Logger.getLogger(LoggerCache.class.getName());
//	private static LoggerCache instance;
//	private Cache cache = null;
//
//	private LoggerCache() {
//		initCache();
//	}
//
//	public static LoggerCache getInstance() {
//		if (instance == null)
//			instance = new LoggerCache();
//		return instance;
//	}
//
//	public void saveLogEntity(CommLogs paramCommLogs) {
//		if (paramCommLogs != null)
//			this.cache
//					.put(new Element(paramCommLogs.getRowId(), paramCommLogs));
//	}
//
//	public void removeLogEntity(String paramString) {
//		if ((paramString == null) || (paramString.trim().equals("")))
//			logger.error("LogEntity id is null or \"\"！");
//		else
//			try {
//				Element localElement = this.cache.get(paramString);
//				if (localElement != null)
//					this.cache.remove(paramString);
//			} catch (CacheException localCacheException) {
//				logger.error("removeLogEntity(id)", localCacheException);
//			}
//	}
//
//	public void updateLogEntity(CommLogs paramCommLogs) {
//		if (paramCommLogs != null) {
//			removeLogEntity(paramCommLogs.getRowId());
//			saveLogEntity(paramCommLogs);
//		}
//	}
//
//	public CommLogs getLogEntity(String paramString) {
//		if ((paramString == null) || (paramString.trim().equals("")))
//			logger.error("LogEntity id is null or \"\"！");
//		CommLogs localCommLogs = null;
//		try {
//			Element localElement = this.cache.get(paramString);
//			if (localElement != null)
//				localCommLogs = (CommLogs) localElement.getValue();
//		} catch (CacheException localCacheException) {
//			logger.error("getLogEntity(id)", localCacheException);
//		}
//		return localCommLogs;
//	}
//
//	public Iterator getAll() {
//		return this.cache.getKeys().iterator();
//	}
//
//	private void initCache() {
//		this.cache = new Cache(StringUtil.getUUID(), 500, true, true, 5L, 2L);
//		try {
//			CacheManager.getInstance().addCache(this.cache);
//		} catch (CacheException localCacheException) {
//			logger.error("initCache()", localCacheException);
//		}
//	}
//
//	public void clearCache() {
//		CacheManager localCacheManager;
//		try {
//			localCacheManager = CacheManager.getInstance();
//			localCacheManager.removeCache("LogEntity");
//			localCacheManager.shutdown();
//		} catch (CacheException localCacheException) {
//			logger.error("clearCache()", localCacheException);
//		}
//	}
}
