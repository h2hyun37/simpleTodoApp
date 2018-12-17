package com.h2hyun37.todoApp.controller.api;

import com.h2hyun37.todoApp.common.ApiResponse;
import com.h2hyun37.todoApp.common.ApiResponseFactory;
import com.h2hyun37.todoApp.common.ApiResultCode;
import com.h2hyun37.todoApp.dto.*;
import com.h2hyun37.todoApp.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class TodoController {
	@Autowired
	private TodoService todoService;

	@PostMapping(path = "/api/todos")
	public ApiResponse<?> createTodo(@Valid @RequestBody CreateTodoRequestBody createTodoRequestBody) {
		CreateTodoResponse response = todoService.create(createTodoRequestBody);

		return ApiResponseFactory.makeResult(ApiResultCode.SUCCESS, response);
	}

	@PutMapping("/api/todos/{todoId}")
	public ApiResponse<?> updateTodo(@PathVariable Long todoId,
	                                 @Valid @RequestBody UpdateTodoRequestBody updateTodoRequestBody) {
		updateTodoRequestBody.setTodoId(todoId);
		UpdateTodoResponse response = todoService.update(updateTodoRequestBody);

		return ApiResponseFactory.makeResult(ApiResultCode.SUCCESS, response);
	}

	@PutMapping("/api/todos/{todoId}/status")
	public ApiResponse<?> updateStatus(@PathVariable Long todoId,
	                                   @Valid @RequestBody UpdateStatusRequestBody updateStatusRequestBody) {
		updateStatusRequestBody.setTodoId(todoId);
		UpdateTodoResponse response = todoService.updateStatus(updateStatusRequestBody);

		return ApiResponseFactory.makeResult(ApiResultCode.SUCCESS, response);
	}

	@GetMapping("/api/todos/{todoId}")
	public ApiResponse<?> findById(@PathVariable Long todoId) {
		FindTodoResponse response = todoService.findOne(todoId);

		return ApiResponseFactory.makeResult(ApiResultCode.SUCCESS, response);
	}

	@GetMapping("/api/todos")
	public ApiResponse<?> findAllBy(@PageableDefault(size = 5) Pageable pageable) {
		FindTodoListResponse response = todoService.findAll(pageable);

		return ApiResponseFactory.makeResult(ApiResultCode.SUCCESS, response);
	}

}
