package com.h2hyun37.todoApp.advice;

import com.h2hyun37.todoApp.common.ApiResponse;
import com.h2hyun37.todoApp.common.ApiResponseFactory;
import com.h2hyun37.todoApp.common.ApiResultCode;
import com.h2hyun37.todoApp.exception.TodoRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {
	@ExceptionHandler
	public ApiResponse<?> handleException(TodoRuntimeException exception) {
		return ApiResponseFactory.makeResult(exception.getResultCode(), null);
	}

	@ExceptionHandler
	public ApiResponse<?> handleException(Exception exception) {
		log.error(exception.getMessage(), exception);
		return ApiResponseFactory.makeResult(ApiResultCode.ERROR,null);
	}
}
