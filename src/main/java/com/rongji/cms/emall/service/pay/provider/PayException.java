package com.rongji.cms.emall.service.pay.provider;

/**
 * 支付异常
 *
 * @since 2015-7-1
 * @author rjf
 *
 */
public class PayException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6132748635248036404L;
	
	public PayException(String msg) {
		super(msg);
	}
	
	public PayException(String msg, Throwable e) {
		super(msg, e);
	}

}
