package com.example.todo.todo.mapper;

import com.example.todo.todo.dto.TodoPatchDto;
import com.example.todo.todo.dto.TodoPostDto;
import com.example.todo.todo.dto.TodoResponseDto;
import com.example.todo.todo.entity.Todo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todo todoPostDtoToTodo(TodoPostDto todoPostDto);
    TodoResponseDto todoToTodoResponseDto(Todo todo);
    List<TodoResponseDto> todosToTodoResponseDtos(List<Todo> todos);
    Todo todoPatchDtoToTodo(TodoPatchDto todoPatchDto);
}
