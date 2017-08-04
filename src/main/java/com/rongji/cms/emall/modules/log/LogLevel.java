package com.rongji.cms.emall.modules.log;

/**
 * 日志级别
 * @author lzw
 *
 */
public final class LogLevel {
	
	private final int _id;
	private final String _desc;
	
	/* 日志级别 */ 
	public static final int ID_FATAL = 5; /* 关键错误  */ 
	public static final int ID_ERROR = 4; /* 出错记录  */ 
	public static final int ID_WARN = 3;  /* 警告，已出错，但系统可以自动恢复  */ 
	public static final int ID_INFO = 2;  /* 信息  */ 
	public static final int ID_DEBUG = 1; /* 调试信息，详细信息  */ 
	
	public static final LogLevel ERROR = new LogLevel(4, "错误");
	public static final LogLevel WARN = new LogLevel(3, "警告");
	public static final LogLevel INFO = new LogLevel(2, "信息");
	public static final LogLevel DEBUG = new LogLevel(1, "调试");

	private LogLevel(int _nId, String _sDesc) {
		this._id = _nId;
		this._desc = _sDesc;
	}

	public String getDesc() {
		return this._desc;
	}

	public int getId() {
		return this._id;
	}

	public static LogLevel findById(int _nId) {
		switch (_nId) {
		case 1:
			return DEBUG;
		case 2:
			return INFO;
		case 3:
			return WARN;
		case 4:
			return ERROR;
		}
		return INFO;
	}
	
	public static LogLevel findById(String nId) {
		int id = 0;
		try {
			id = Integer.parseInt(nId);
		} catch (Exception e) {
		}
		return findById(id);
	}
}
