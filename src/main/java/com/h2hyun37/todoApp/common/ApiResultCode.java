package com.h2hyun37.todoApp.common;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public enum ApiResultCode {
	SUCCESS(0),
	ERROR(1),
	NOT_FOUND(2);


	private int code;
}
