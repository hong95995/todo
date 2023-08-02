package com.example.todo.todo.controller;

import com.example.todo.todo.dto.TodoPatchDto;
import com.example.todo.todo.dto.TodoPostDto;
import com.example.todo.todo.dto.TodoResponseDto;
import com.example.todo.todo.entity.Todo;
import com.example.todo.todo.mapper.TodoMapper;
import com.example.todo.todo.service.TodoService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.example.todo.todo.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.example.todo.todo.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoMapper mapper;
    @MockBean
    private TodoService service;
    @Autowired
    private Gson gson;

    @Test
    public void postTodoTest() throws Exception {

        TodoPostDto post = new TodoPostDto();
        Todo todo = new Todo();
        TodoResponseDto response = new TodoResponseDto();

        post.setTitle("테스트");
        post.setTodoOrder(3);
        post.setCompleted(false);

        todo.setId(1L);
        todo.setTitle("테스트");
        todo.setTodoOrder(3);
        todo.setCompleted(false);

        response.setId(1L);
        response.setTitle("테스트");
        response.setTodoOrder(3);
        response.setCompleted(false);

        given(mapper.todoPostDtoToTodo(Mockito.any(TodoPostDto.class))).willReturn(todo);
        given(service.createTodo(Mockito.any(Todo.class))).willReturn(todo);
        given(mapper.todoToTodoResponseDto(Mockito.any(Todo.class))).willReturn(response);

        ResultActions actions = mockMvc.perform(
                post("/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(post))
        )
                .andExpect(status().isCreated())
                .andDo(document(
                        "post-todo",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                              List.of(
                                      fieldWithPath("title").type(JsonFieldType.STRING).description("할 일"),
                                      fieldWithPath("todoOrder").type(JsonFieldType.NUMBER).description("할 일 순서"),
                                      fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료 여부")
                              )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("할 일 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("할 일"),
                                        fieldWithPath("todoOrder").type(JsonFieldType.NUMBER).description("할 일 순서"),
                                        fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료 여부")
                                        )
                        )
                ));
    }

    @Test
    public void getTodo() throws Exception {
        long id = 1L;
        String title = "타이틀";
        int todoOrder = 3;
        boolean completed = false;

        Todo todo = new Todo();
        TodoResponseDto response = new TodoResponseDto();

        response.setId(id);
        response.setTitle(title);
        response.setTodoOrder(todoOrder);
        response.setCompleted(completed);

        given(service.searchTodo(Mockito.anyLong())).willReturn(todo);
        given(mapper.todoToTodoResponseDto(Mockito.any(Todo.class))).willReturn(response);

        ResultActions actions =
                mockMvc.perform(
                        get("/{id}")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                        .andExpect(status().isOk())
                        .andDo(document("get-todo",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        parameterWithName("id").description("할 일 식별자")
                                ),
                                responseFields(
                                        List.of(
                                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("할 일 식별자"),
                                                fieldWithPath("title").type(JsonFieldType.STRING).description("할 일"),
                                                fieldWithPath("todoOrder").type(JsonFieldType.NUMBER).description("할 일 순서"),
                                                fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료 여부")
                                        )
                                )
                                ));

    }

    @Test
    public void patchTodo() throws Exception {
        long id = 1L;
        String title = "타이틀";
        int todoOrder = 3;
        boolean completed = false;

        TodoPatchDto patch = new TodoPatchDto();
        Todo todo = new Todo();
        TodoResponseDto responseDto = new TodoResponseDto();

        patch.setTitle(title);
        patch.setTodoOrder(todoOrder);
        patch.setCompleted(completed);

        responseDto.setId(id);
        responseDto.setTitle(title);
        responseDto.setTodoOrder(todoOrder);
        responseDto.setCompleted(completed);

        given(mapper.todoPatchDtoToTodo(Mockito.any(TodoPatchDto.class))).willReturn(todo);
        given(service.updateTodo(Mockito.any(Todo.class))).willReturn(todo);
        given(mapper.todoToTodoResponseDto(Mockito.any(Todo.class))).willReturn(responseDto);

        ResultActions actions =
                mockMvc.perform(
                        patch("/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(patch))
                )
                        .andExpect(status().isOk())
                        .andDo(document(
                                "patch-todo",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        parameterWithName("id").description("할 일 식별자")
                                ),
                                requestFields(
                                        List.of(
                                                fieldWithPath("title").type(JsonFieldType.STRING).description("할 일"),
                                                fieldWithPath("todoOrder").type(JsonFieldType.NUMBER).description("할 일 순서"),
                                                fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료 여부")
                                        )
                                ),
                                responseFields(
                                        List.of(
                                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("할 일 식별자"),
                                                fieldWithPath("title").type(JsonFieldType.STRING).description("할 일"),
                                                fieldWithPath("todoOrder").type(JsonFieldType.NUMBER).description("할 일 순서"),
                                                fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료 여부")
                                        )
                                )
                        ));
    }
}
