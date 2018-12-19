package com.h2hyun37.todoApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTodoRequestBody {
	@NotBlank
	private String content;

	private List<Long> referenceIdList;
}
