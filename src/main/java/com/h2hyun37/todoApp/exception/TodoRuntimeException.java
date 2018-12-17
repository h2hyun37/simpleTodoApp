package com.h2hyun37.todoApp.exception;

import com.h2hyun37.todoApp.common.ApiResultCode;

public class TodoRuntimeException extends RuntimeException {
	private ApiResultCode code;

	public TodoRuntimeException(ApiResultCode code) {
		this.code = code;
	}
}
