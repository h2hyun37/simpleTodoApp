package com.h2hyun37.todoApp.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse<T extends ApiBody> {
	private ApiHeader header;
	private T data;
}
