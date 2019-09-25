package com.example.common;

/**
 * @Desc: 自定义异常
 * @author: GanHaiqiang
 * @date: 2019-09-24 14:48
 */
public class AppException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

	public AppException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public AppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public AppException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public AppException(Throwable cause) {
		super(cause);
	}
}
