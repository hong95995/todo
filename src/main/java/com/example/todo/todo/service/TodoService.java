package com.example.todo.todo.service;

import com.example.todo.todo.entity.Todo;
import com.example.todo.todo.exception.BusinessLogicException;
import com.example.todo.todo.exception.Exception;
import com.example.todo.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public Todo createTodo(Todo post) {
        Todo saved = repository.save(post);

        return saved;
    }

    public Todo searchTodo(long id) {
        return findById(id);
    }

    private Todo findById(long id) {
        Optional<Todo> optionalTodo = repository.findById(id);
        Todo finded = optionalTodo.orElseThrow(
                () -> new BusinessLogicException(Exception.TODO_NOT_FOUND)
        );

        return finded;
    }

    public List<Todo> searchTodos() {
        return repository.findAll();
    }


    public Todo updateTodo(Todo todo) {
        Todo finded = findById(todo.getId());

        Optional.ofNullable(todo.getTitle()).ifPresent(title -> finded.setTitle(title));
        Optional.ofNullable(todo.getTodoOrder()).ifPresent(todoOrder -> finded.setTodoOrder(todoOrder));
        Optional.ofNullable(todo.isCompleted()).ifPresent(completed -> finded.setCompleted(completed));

        return repository.save(finded);
    }

    public void deleteTodo(long id) {
        Todo finded = findById(id);
        repository.delete(finded);
    }

    public void deleteTodos() {
        repository.deleteAll();
    }
}
