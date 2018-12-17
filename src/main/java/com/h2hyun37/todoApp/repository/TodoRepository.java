package com.h2hyun37.todoApp.repository;

import com.h2hyun37.todoApp.entity.Todo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	// List<Todo> findAllByReferenceId(String ci, Pageable pageable);
}
