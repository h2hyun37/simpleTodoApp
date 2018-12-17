package com.h2hyun37.todoApp.entity;

import com.h2hyun37.todoApp.constants.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "todoIdGenerator", sequenceName = "TODO_SEQ", initialValue = 1, allocationSize = 1)
@Getter
@ToString
public class Todo {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "todoIdGenerator")
	private Long todoId;

	@Setter
	private String content;

	@Enumerated(EnumType.STRING)
	@Setter
	private Status status;

	private ZonedDateTime createdYmdt;

	@Setter
	private ZonedDateTime lastModifiedYmdt;

	@Transient
	@Setter
	private List<TodoReference> referenceList;

	public Todo() { }

	@Builder
	public Todo(String content, Status status) {
		ZonedDateTime now = ZonedDateTime.now();

		this.content = content;
		this.status = status;
		createdYmdt = now;
		lastModifiedYmdt = now;
		referenceList = new ArrayList<>();
	}

	public Todo(String content) {
		this(content, Status.READY);
	}

	public void addReference(TodoReference todoReference) {
		if (referenceList == null) {
			referenceList = new ArrayList<>();
		}

		referenceList.add(todoReference);
	}
}
