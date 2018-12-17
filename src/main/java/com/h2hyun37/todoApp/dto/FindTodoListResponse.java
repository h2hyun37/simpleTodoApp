package com.h2hyun37.todoApp.dto;

import com.h2hyun37.todoApp.common.ApiBody;
import com.h2hyun37.todoApp.entity.Todo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FindTodoListResponse implements ApiBody {
	private List<Todo> todoList;
	private int pageNumber; // 현재 페이지 번호
	private int pageSize;   // 페이지 사이즈
	private int totalPages;  // 전체 페이지 갯수
	private int numberOfElements; // 현재 페이지 나올 데이터 수
	private long totalElements; // 전체 데이터 수
}