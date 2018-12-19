package com.h2hyun37.todoApp.repository;

import com.h2hyun37.todoApp.entity.TodoReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoReferenceRepository extends JpaRepository<TodoReference, Long> {
	public Optional<List<TodoReference>> findAllByTodoId(Long todoId);

	public Optional<List<TodoReference>> findAllByReferenceId(Long referenceId);
}
