package com.example.todo.todo.controller;

import com.example.todo.todo.dto.TodoPatchDto;
import com.example.todo.todo.dto.TodoPostDto;
import com.example.todo.todo.dto.TodoResponseDto;
import com.example.todo.todo.entity.Todo;
import com.example.todo.todo.mapper.TodoMapper;
import com.example.todo.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class TodoController {

    private final TodoMapper mapper;
    private final TodoService service;

    public TodoController(TodoMapper mapper, TodoService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity postTodo(@RequestBody TodoPostDto todoPostDto) {
        Todo post = mapper.todoPostDtoToTodo(todoPostDto);
        Todo saved = service.createTodo(post);
        TodoResponseDto response = mapper.todoToTodoResponseDto(saved);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTodo(@PathVariable("id") long id) {
        Todo searched = service.searchTodo(id);
        TodoResponseDto response = mapper.todoToTodoResponseDto(searched);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getTodos() {
        List<Todo> todos = service.searchTodos();
        List<TodoResponseDto> response = mapper.todosToTodoResponseDtos(todos);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patchTodo(@PathVariable("id") long id, @RequestBody TodoPatchDto todoPatchDto) {
        Todo todo = mapper.todoPatchDtoToTodo(todoPatchDto);
        todo.setId(id);
        Todo patched = service.updateTodo(todo);
        TodoResponseDto responseDto = mapper.todoToTodoResponseDto(patched);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTodo(@PathVariable("id") long id) {
        service.deleteTodo(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity deleteTodos() {
        service.deleteTodos();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
