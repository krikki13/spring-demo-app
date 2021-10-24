package com.kristjan.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests hello world controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class HelloWorldControllerTest  {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests that hello returns Hello world!
     */
    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(get("/hello")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello world!")));
    }
}
