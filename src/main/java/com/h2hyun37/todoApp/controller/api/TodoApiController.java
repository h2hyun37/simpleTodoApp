package com.h2hyun37.todoApp.controller.api;

import com.h2hyun37.todoApp.common.ApiResponse;
import com.h2hyun37.todoApp.common.ApiResponseFactory;
import com.h2hyun37.todoApp.common.ApiResultCode;
import com.h2hyun37.todoApp.dto.*;
import com.h2hyun37.todoApp.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
public class TodoApiController {
	@Autowired
	private TodoService todoService;

	@PostMapping(path = "/todos")
	public ApiResponse<?> createTodo(@Valid @RequestBody CreateTodoRequestBody createTodoRequestBody) {
		CreateTodoResponse response = todoService.create(createTodoRequestBody);

		return ApiResponseFactory.makeResult(ApiResultCode.SUCCESS, response);
	}

	@PutMapping("/todos/{todoId}")
	public ApiResponse<?> updateTodo(@PathVariable Long todoId,
	                                 @Valid @RequestBody UpdateTodoRequestBody updateTodoRequestBody) {
		updateTodoRequestBody.setTodoId(todoId);
		UpdateTodoResponse response = todoService.update(updateTodoRequestBody);

		return ApiResponseFactory.makeResult(ApiResultCode.SUCCESS, response);
	}

	@PutMapping("/todos/{todoId}/status")
	public ApiResponse<?> updateStatus(@PathVariable Long todoId,
	                                   @Valid @RequestBody UpdateStatusRequestBody updateStatusRequestBody) {
		updateStatusRequestBody.setTodoId(todoId);
		UpdateTodoResponse response = todoService.updateStatus(updateStatusRequestBody);

		return ApiResponseFactory.makeResult(ApiResultCode.SUCCESS, response);
	}

	@GetMapping("/todos/{todoId}")
	public ApiResponse<?> findById(@PathVariable Long todoId) {
		FindTodoResponse response = todoService.findOne(todoId);

		return ApiResponseFactory.makeResult(ApiResultCode.SUCCESS, response);
	}

	@GetMapping("/todos")
	public ApiResponse<?> findAllBy(@Valid @ModelAttribute FindTodoListRequest findTodoListRequest) {
		FindTodoListResponse response = todoService.findAll(findTodoListRequest);

		return ApiResponseFactory.makeResult(ApiResultCode.SUCCESS, response);
	}

}
