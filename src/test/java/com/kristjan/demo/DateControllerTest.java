package com.kristjan.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kristjan.demo.controller.DateController;
import com.kristjan.demo.model.DateModel;
import com.kristjan.demo.repository.DateRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class DateControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DateRepository repository;

    @InjectMocks
    private DateController controller;

    @Before
    public void setup() {
        openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        DateModel testDate = new DateModel(1L, LocalDate.of(2021, 10, 23));
        Mockito.when(repository.save(Mockito.any(DateModel.class))).thenReturn(testDate);

        Map<String, String> json = Stream.of(new String[][] {
                { "date", "2021-10-23" },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

        String x = new ObjectMapper().writeValueAsString(json);
        System.out.println(x);

        mockMvc.perform(MockMvcRequestBuilders.post("/date")
                .content(x)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<DateModel> dtoCaptor = ArgumentCaptor.forClass(DateModel.class);
        verify(repository, times(1)).save(dtoCaptor.capture());
        verifyNoMoreInteractions(repository);

        DateModel dtoArgument = dtoCaptor.getValue();
        assertNull(dtoArgument.getId());
        assertTrue(dtoArgument.getDate().isEqual(testDate.getDate()));
    }
}
