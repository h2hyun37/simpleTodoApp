package com.h2hyun37.todoApp.repository;

import com.h2hyun37.todoApp.TodoAppApplication;
import com.h2hyun37.todoApp.constants.Status;
import com.h2hyun37.todoApp.entity.Todo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@Import(TodoAppApplication.class)
public class TodoRepositoryTest {
	@Autowired
	private TodoRepository todoRepository;

	@Test
	public void save() {
		Todo todo = new Todo("test todo", Status.READY);
		todoRepository.save(todo);
	}
}
