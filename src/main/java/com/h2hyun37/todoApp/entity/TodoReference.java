package com.h2hyun37.todoApp.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@SequenceGenerator(name = "todoReferenceSeqGenerator", sequenceName = "TODO_REF_SEQ", initialValue = 1, allocationSize = 1)
@Table(indexes = {
				@Index(name = "IDX_TODOID1", columnList = "todoId"),
				@Index(name = "IDX_REFERENCEID1", columnList = "referenceId")
		})
@Getter
@ToString
public class TodoReference {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "todoReferenceSeqGenerator")
	private Long seq;

	private Long todoId;

	private Long referenceId;

	private ZonedDateTime createdYmdt;

	public TodoReference() { }

	@Builder
	public TodoReference(Long todoId, Long referenceId, ZonedDateTime createdYmdt) {
		this.todoId = todoId;
		this.referenceId = referenceId;
		this.createdYmdt = createdYmdt;
	}
}
