package com.h2hyun37.todoApp.controller.view;


import com.h2hyun37.todoApp.dto.FindTodoListRequest;
import com.h2hyun37.todoApp.dto.FindTodoListResponse;
import com.h2hyun37.todoApp.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/view")
public class TodoViewController {
	private static final String SIZE_PER_PAGE = "5";

	@Autowired
	private TodoService todoService;


	@GetMapping("/list")
	public String list(Model model,
	                   @RequestParam(required = false, defaultValue = "0") String page) {
		FindTodoListRequest request = FindTodoListRequest.builder()
				.page(Integer.valueOf(page))
				.size(Integer.valueOf(SIZE_PER_PAGE))
				.build();

		FindTodoListResponse response = todoService.findAll(request);

		model.addAttribute("todoList", response.getTodoList());
		model.addAttribute("pageNumber", response.getPageNumber());
		model.addAttribute("pageSize", response.getPageSize());
		model.addAttribute("totalPages", response.getTotalPages());
		model.addAttribute("numberOfElements", response.getNumberOfElements());
		model.addAttribute("totalElements", response.getTotalElements());

		return "list";
	}
}
