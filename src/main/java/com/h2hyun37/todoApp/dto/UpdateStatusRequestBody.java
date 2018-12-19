package com.h2hyun37.todoApp.dto;

import com.h2hyun37.todoApp.constants.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusRequestBody {
	private Long todoId;

	@NotNull
	private Status status;
}
