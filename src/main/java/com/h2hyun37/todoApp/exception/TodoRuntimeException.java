package com.h2hyun37.todoApp.exception;

import com.h2hyun37.todoApp.common.ApiResultCode;
import lombok.Data;

@Data
public class TodoRuntimeException extends RuntimeException {
	private ApiResultCode resultCode;

	public TodoRuntimeException(ApiResultCode resultCode) {
		this.resultCode = resultCode;
	}
}
