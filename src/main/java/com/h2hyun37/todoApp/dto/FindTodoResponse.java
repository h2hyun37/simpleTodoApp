package com.h2hyun37.todoApp.dto;

import com.h2hyun37.todoApp.common.ApiBody;
import com.h2hyun37.todoApp.entity.Todo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FindTodoResponse implements ApiBody {
	private Todo todo;
}