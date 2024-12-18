package au.com.rainmore.centus.controllers.books;

import au.com.rainmore.centus.services.books.BookService;
import au.com.rainmore.centus.services.books.dto.BookDto;
import au.com.rainmore.centus.utils.PagingUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;


    @Test
    void test_list_return_401_without_authentication() throws Exception {
        Pageable pageable = PagingUtils.DEFAULT_PAGEABLE;
        Page<BookDto> page = new PageImpl<>(List.of(), pageable, 0);
        given(bookService.findAllDto(anyString(), any(Pageable.class))).willReturn(page);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/books")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void test_list_without_any_request_parameters() throws Exception {
        Pageable pageable = PagingUtils.DEFAULT_PAGEABLE;
        Page<BookDto> page = new PageImpl<>(List.of(), pageable, 0);
        given(bookService.findAllDto(any(), any(Pageable.class))).willReturn(page);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/books")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(SecurityMockMvcRequestPostProcessors.user("test-account")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.empty").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.last").isBoolean())
        ;
    }

    @Test
    void test_list_with_pageable_request_parameters() throws Exception {
        Pageable pageable = PageRequest.of(2, 20);
        Page<BookDto> page = new PageImpl<>(List.of(), pageable, 1);
        given(bookService.findAllDto(any(), eq(pageable))).willReturn(page);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/books?_page=2&_size=20")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(SecurityMockMvcRequestPostProcessors.user("test-account")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.empty").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.last").isBoolean())
        ;
    }

    @Test
    void test_list_with_cors() throws Exception {
        Pageable pageable = PagingUtils.DEFAULT_PAGEABLE;
        Page<BookDto> page = new PageImpl<>(List.of(), pageable, 1);
        given(bookService.findAllDto(any(), any(Pageable.class))).willReturn(page);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/books")
                        .header(HttpHeaders.ORIGIN, "http://localhost:5173/")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(SecurityMockMvcRequestPostProcessors.user("test-account")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().stringValues(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Access-Control-Allow-Headers, Access-Control-Allow-Origin, Vary, Cache-Control, Pragma, Expires, Content-Type, Content-Language, Jwt-Token"))
                .andExpect(header().stringValues(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:5173/"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.empty").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.last").isBoolean())
        ;
    }
}
