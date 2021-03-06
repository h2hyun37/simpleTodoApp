package com.h2hyun37.todoApp.service;

import com.h2hyun37.todoApp.TodoAppApplication;
import com.h2hyun37.todoApp.constants.Status;
import com.h2hyun37.todoApp.dto.CreateTodoRequestBody;
import com.h2hyun37.todoApp.dto.UpdateStatusRequestBody;
import com.h2hyun37.todoApp.dto.UpdateTodoRequestBody;
import com.h2hyun37.todoApp.dto.UpdateTodoResponse;
import com.h2hyun37.todoApp.entity.Todo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@Import(TodoAppApplication.class)
public class TodoServiceTest {
	@Autowired
	private TodoService todoService;

	@Test
	public void test_create_without_reference() {
		String content = "test content";
		CreateTodoRequestBody request = getCreateTodoRequest(content, null);
		Todo createdTodo = todoService.create(request).getTodo();

		assertThat(createdTodo, is(notNullValue()));
		assertThat(createdTodo.getTodoId(), is(notNullValue()));
		assertThat(createdTodo.getContent(), is(content));

		log.info("todo : {}", createdTodo);
	}

	@Test
	public void test_create_with_reference() {
		String mainTodoContent = "test content";
		CreateTodoRequestBody mainTodoRequest = getCreateTodoRequest(mainTodoContent, new ArrayList<>());
		Todo mainTodo = todoService.create(mainTodoRequest).getTodo();

		assertThat(mainTodo, is(notNullValue()));


		String subTodoContent = "test with reference";
		List<Long> referenceIdList = new ArrayList<>();
		referenceIdList.add(mainTodo.getTodoId());
		CreateTodoRequestBody subTodoRequest = getCreateTodoRequest(subTodoContent, referenceIdList);
		Todo subTodo = todoService.create(subTodoRequest).getTodo();

		assertThat(subTodo, is(notNullValue()));

		log.info("{}", mainTodo);
		log.info("{}", subTodo);

		Todo mainTodoByFind = todoService.findOne(mainTodo.getTodoId()).getTodo();
		Todo subTodoByFind = todoService.findOne(subTodo.getTodoId()).getTodo();

		Boolean isReferenceExists = subTodoByFind.getReferenceList().stream()
				.filter(todoReference -> todoReference.getReferenceId() == mainTodoByFind.getTodoId())
				.findAny()
				.isPresent();

		assertThat(isReferenceExists, is(Boolean.TRUE));
	}

	@Test
	public void test_update_todo_content() {
		String content = "test content";
		CreateTodoRequestBody request = getCreateTodoRequest(content, null);
		Todo createdTodo = todoService.create(request).getTodo();

		assertThat(createdTodo, is(notNullValue()));

		String updateContent = "updated test content";
		Todo updatedTodo = todoService.update(getUpdateTodoRequest(createdTodo.getTodoId(), updateContent)).getTodo();

		assertThat(updatedTodo, is(notNullValue()));
		assertTrue(StringUtils.equals(updateContent, updatedTodo.getContent()));

	}

	@Test
	public void test_update_status_with_no_reference_todo() {
		String content = "test content";
		CreateTodoRequestBody request = getCreateTodoRequest(content, null);
		Todo createdTodo = todoService.create(request).getTodo();

		UpdateStatusRequestBody updateStatusRequestBody = UpdateStatusRequestBody.builder()
				.status(Status.COMPLETE)
				.todoId(createdTodo.getTodoId())
				.build();
		UpdateTodoResponse response = todoService.updateStatus(updateStatusRequestBody);

		assertThat(response.getTodo().getStatus(),is(Status.COMPLETE));
	}


	private CreateTodoRequestBody getCreateTodoRequest(String content, List<Long> referenceIdList) {
		CreateTodoRequestBody request = CreateTodoRequestBody.builder()
				.content(content)
				.referenceIdList(referenceIdList)
				.build();
		return request;
	}

	private UpdateTodoRequestBody getUpdateTodoRequest(Long todoId, String content) {
		UpdateTodoRequestBody request = UpdateTodoRequestBody.builder()
				.todoId(todoId)
				.content(content)
				.build();
		return request;
	}
}
