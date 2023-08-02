package com.example.todo.todo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoPostDto {
    private String title;
    private int todoOrder;
    private boolean completed;
}
