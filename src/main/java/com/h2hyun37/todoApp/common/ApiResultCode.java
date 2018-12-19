package com.h2hyun37.todoApp.common;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public enum ApiResultCode {
	SUCCESS(0),
	ERROR(1),
	NOT_FOUND(2),
	UNCOMPLETED_TODO_EXISTS(3);


	private int code;
}
