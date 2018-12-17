package com.h2hyun37.todoApp.dto;

import com.h2hyun37.todoApp.common.ApiBody;
import com.h2hyun37.todoApp.entity.Todo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTodoResponse implements ApiBody {
	private Todo todo;
}