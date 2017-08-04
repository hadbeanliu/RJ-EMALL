package com.rongji.cms.emall.modules.log;

/**
 * 日志类型
 * @author lzw
 *
 */
public final class LogType {
	
	private final int _id;
	private final String _desc;
	
	/**
	 * 操作日志 内容JsonBean 
	 * @author jayfans
	 *
	 */
	public static class OpertionContent extends LogParsedContent{
		private static final String KEY_TARGET_ID="_key_target_Id_00000001";//操作目标ID key
		private static final String KEY_TARGET_NAME="_key_target_name_00000002";//操作目标名称
		private static final String KEY_OPERUSER_ID="_key_oper_user_id_00000003";//操作者ID
		private static final String KEY_OPERUSER_NAME="_key_oper_user_name_00000004";//操作者名称
		
		public String getTargetId() {
			return this.getCustomField(KEY_TARGET_ID);
		}
		public String setTargetId(String targetId) {
			return this.setCustomField(KEY_TARGET_ID, targetId);
		}
		public String getTargetName() {
			return this.getCustomField(KEY_TARGET_NAME);
		}
		public String setTargetName(String targetName) {
			return this.setCustomField(KEY_TARGET_NAME, targetName);
		}
		public String getOperUserId() {
			return this.getCustomField(KEY_OPERUSER_ID);
		}
		public String setOperUserId(String operUserId) {
			return this.setCustomField(KEY_OPERUSER_ID, operUserId);
		}
		public String getOperUserName() {
			return this.getCustomField(KEY_OPERUSER_NAME);
		}
		public String setOperUserName(String operUserName) {
			return this.setCustomField(KEY_OPERUSER_NAME, operUserName);
		}
	}
	
	/* 日志级别 */ 
	public static final int ID_UNKNOWN = 0; /* 未知日志  */ 
	public static final int ID_COMMON = 1; /* 普通日志  */ 
	public static final int ID_OPER = 2; /* 操作日志  */ 
	public static final int ID_FLOW = 3;  /* 流程日志  */ 
	public static final int ID_SYS_SERVICE = 4;  /* 系统服务  */ 
	public static final int ID_SYS_SECURITY = 5; /* 系统安全  */ 
	
	public static final LogType UNKNOWN = new LogType(0, "未知日志");
	public static final LogType COMMON = new LogType(1, "普通日志");
	public static final LogType SYS_OPERATION = new LogType(2, "系统操作日志");
	public static final LogType LOGIC_OPERATION = new LogType(3, "业务操作日志");
	public static final LogType FLOW = new LogType(4, "流程日志");
	public static final LogType SYS_SERVICE = new LogType(5, "系统服务");
	public static final LogType SYS_SECURITY = new LogType(6, "系统安全");

	private LogType(int _nId, String _sDesc) {
		this._id = _nId;
		this._desc = _sDesc;
	}

	public String getDesc() {
		return this._desc;
	}

	public int getId() {
		return this._id;
	}

	public static LogType findById(int _nId) {
		switch (_nId) {
		case 1:
			return COMMON;
		case 2:
			return SYS_OPERATION;
		case 3:
			return LOGIC_OPERATION;
		case 4:
			return FLOW;
		case 5:
			return SYS_SERVICE;
		case 6:
			return SYS_SECURITY;
		}
		return UNKNOWN;
	}
	
	public static LogType findById(String nId) {
		int id = 0;
		try {
			id = Integer.parseInt(nId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return findById(id);
	}
}
