package com.h2hyun37.todoApp.service;

import com.h2hyun37.todoApp.common.ApiResultCode;
import com.h2hyun37.todoApp.constants.Status;
import com.h2hyun37.todoApp.dto.*;
import com.h2hyun37.todoApp.entity.Todo;
import com.h2hyun37.todoApp.entity.TodoReference;
import com.h2hyun37.todoApp.exception.TodoRuntimeException;
import com.h2hyun37.todoApp.repository.TodoReferenceRepository;
import com.h2hyun37.todoApp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {
	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private TodoReferenceRepository todoReferenceRepository;


	public CreateTodoResponse create(CreateTodoRequestBody request) {
		// 참조리스트가 null이거나 비어있다면 null을 하나 넣어준다. (참조 매핑 테이블에 반드시 1개이상 들어가야 함)
		List<Long> referenceIdList = request.getReferenceIdList();
		if (referenceIdList == null) {
			referenceIdList = new ArrayList<>();
		}

		// 존재하는 todo에 참조를 거는지 확인
		for (Long referenceId : referenceIdList) {
			findById(referenceId);
		}

		Todo todo = saveNewTodo(request.getContent(), referenceIdList);
		return CreateTodoResponse.builder().todo(todo).build();
	}

	public UpdateTodoResponse update(UpdateTodoRequestBody request) {
		Todo todo = findById(request.getTodoId());
		todo.setContent(request.getContent());
		todo.setLastModifiedYmdt(ZonedDateTime.now());
		saveTodo(todo);

		return UpdateTodoResponse.builder().todo(todo).build();
	}

	public UpdateTodoResponse updateStatus(UpdateStatusRequestBody request) {
		// 나를 참조로 한 녀석들이 완료되어야 나도 완료 가능하므로, 완료되지 않은 TODO가 있는지 확인
		Optional<List<Long>> optionalReferencingMeList = findIdListByReferenceId(request.getTodoId());

		if (optionalReferencingMeList.isPresent()) {
			Optional<Todo> uncompletedTodo = optionalReferencingMeList.get().stream()
					.map(referencingMeId -> findById(referencingMeId))
					.filter(todo -> todo.getStatus() != Status.COMPLETE)
					.findAny();

			if (uncompletedTodo.isPresent()) {
				throw new TodoRuntimeException(ApiResultCode.UNCOMPLETED_TODO_EXISTS);
			}
		}

		// 나를 참조로 한 녀석들이 없거나 모두 완료이므로 업데이트 처리
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

	public FindTodoListResponse findAll(FindTodoListRequest findTodoListRequest) {
		Pageable pageable = PageRequest.of(findTodoListRequest.getPage(), findTodoListRequest.getSize());
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

	private Todo saveNewTodo(String content, List<Long> referenceIdList) {
		// 매핑 테이블에 최소한 1개는 입력되야하므로 없으면 null 넣어준다.
		if (referenceIdList == null) {
			referenceIdList = new ArrayList<>();
			referenceIdList.add(null);
		} else if (referenceIdList.size() == 0) {
			referenceIdList.add(null);
		}

		Todo todo = saveTodo(new Todo(content));
		List<TodoReference> referenceList = new ArrayList<>();

		for (Long referenceId : referenceIdList) {
			TodoReference reference = new TodoReference(todo.getTodoId(), referenceId, todo.getCreatedYmdt());
			todoReferenceRepository.save(reference);
			referenceList.add(reference);
		}

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

	/**
	 * todoId 가 참조하고 있는 list 리턴.<br />
	 *
	 * <p>
	 * todoId는 다른 id를 참조하거나 혹은 다른 id를 참조하지 않거나
	 * 1개 이상의 row는 있어야 하기 때문에 없다면 exception 발생
	 * </p>
	 *
	 * @param todoId
	 * @return
	 */
	private List<TodoReference> findReferenceListById(Long todoId) {
		Optional<List<TodoReference>> optionalTodoReferenceList = todoReferenceRepository.findAllByTodoId(todoId);
		if (!optionalTodoReferenceList.isPresent() || optionalTodoReferenceList.get().size() == 0) {
			throw new TodoRuntimeException(ApiResultCode.NOT_FOUND);
		}
		return optionalTodoReferenceList.get();
	}

	/**
	 * referenceId를 참조하고 있는 todoId list 리턴<br />
	 *
	 * 해당 id를 참조하는 list가 없을 수 있기 때문에, return type은 optional.<br />
	 *
	 * @param referenceId
	 * @return
	 */
	private Optional<List<Long>> findIdListByReferenceId(Long referenceId) {
		Optional<List<TodoReference>> optionalTodoReferenceList = todoReferenceRepository.findAllByReferenceId(referenceId);

		if (!optionalTodoReferenceList.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(
				optionalTodoReferenceList.get().stream()
				.map(todoReference -> todoReference.getTodoId())
				.collect(Collectors.toList())
		);

	}
}
