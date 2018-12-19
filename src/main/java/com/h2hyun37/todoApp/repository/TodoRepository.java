package com.h2hyun37.todoApp.repository;

import com.h2hyun37.todoApp.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
