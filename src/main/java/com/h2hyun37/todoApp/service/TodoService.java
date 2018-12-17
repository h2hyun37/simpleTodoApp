package com.h2hyun37.todoApp.service;

import com.h2hyun37.todoApp.common.ApiResultCode;
import com.h2hyun37.todoApp.dto.*;
import com.h2hyun37.todoApp.entity.Todo;
import com.h2hyun37.todoApp.entity.TodoReference;
import com.h2hyun37.todoApp.exception.TodoRuntimeException;
import com.h2hyun37.todoApp.repository.TodoReferenceRepository;
import com.h2hyun37.todoApp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private TodoReferenceRepository todoReferenceRepository;


	public CreateTodoResponse create(CreateTodoRequestBody request) {
		Long referenceId = request.getReferenceId();

		Todo todo = saveNewTodo(request.getContent(), referenceId);
		return CreateTodoResponse.builder().todo(todo).build();
	}

	// TODO : circular reference 체크
	public UpdateTodoResponse update(UpdateTodoRequestBody request) {
		Todo todo = findById(request.getTodoId());
		todo.setContent(request.getContent());
		todo.setLastModifiedYmdt(ZonedDateTime.now());
		saveTodo(todo);

		return UpdateTodoResponse.builder().todo(todo).build();
	}

	public UpdateTodoResponse updateStatus(UpdateStatusRequestBody request) {
		Todo todo = findById(request.getTodoId());
		todo.setStatus(request.getStatus());
		todo.setLastModifiedYmdt(ZonedDateTime.now());
		saveTodo(todo);

		return UpdateTodoResponse.builder().todo(todo).build();
	}

	public FindTodoResponse findOne(Long todoId) {
		Todo todo = findById(todoId);

		return FindTodoResponse.builder().todo(todo).build();
	}

	public FindTodoListResponse findAll(Pageable pageable) {
		Page<Todo> todoPageList = todoRepository.findAll(pageable);
		List<Todo> todoList = todoPageList.getContent();

		todoList.stream()
				.forEach(todo -> todo.setReferenceList(findReferenceListById(todo.getTodoId())));

		return FindTodoListResponse.builder()
				.pageNumber(todoPageList.getNumber())
				.pageSize(todoPageList.getSize())
				.totalPages(todoPageList.getTotalPages())
				.numberOfElements(todoPageList.getNumberOfElements())
				.totalElements(todoPageList.getTotalElements())
				.todoList(todoList)
				.build();
	}


	private Todo saveTodo(Todo todo) {
		todoRepository.save(todo);
		return todo;
	}

	private Todo saveNewTodo(String content, Long referenceId) {
		Todo todo = saveTodo(new Todo(content));

		TodoReference reference = new TodoReference(todo.getTodoId(), referenceId, todo.getCreatedYmdt());
		todoReferenceRepository.save(reference);

		List<TodoReference> referenceList = new ArrayList<>();
		referenceList.add(reference);
		todo.setReferenceList(referenceList);

		return todo;
	}

	private Todo findById(Long todoId) {
		Optional<Todo> optionalTodo = todoRepository.findById(todoId);
		if (!optionalTodo.isPresent()) {
			throw new TodoRuntimeException(ApiResultCode.NOT_FOUND);
		}

		List<TodoReference> todoReferenceList = findReferenceListById(todoId);

		Todo todo = optionalTodo.get();
		todo.setReferenceList(todoReferenceList);

		return todo;
	}

	private List<TodoReference> findReferenceListById(Long todoId) {
		Optional<List<TodoReference>> optionalTodoReferenceList = todoReferenceRepository.findAllByTodoId(todoId);
		if (!optionalTodoReferenceList.isPresent() || optionalTodoReferenceList.get().size() == 0) {
			throw new TodoRuntimeException(ApiResultCode.NOT_FOUND);
		}
		return optionalTodoReferenceList.get();
	}
}
