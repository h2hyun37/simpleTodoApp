package com.h2hyun37.todoApp.common;



public class ApiResponseFactory {
	public static <T extends ApiBody> ApiResponse<T> makeResult(ApiResultCode apiResultCode, T data) {
		ApiHeader header = new ApiHeader();
		header.setCode(apiResultCode);

		ApiResponse<T> apiResponse = new ApiResponse<>();
		apiResponse.setHeader(header);
		apiResponse.setData(data);
		return apiResponse;
	}
}
