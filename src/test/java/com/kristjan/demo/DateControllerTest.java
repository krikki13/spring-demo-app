package com.kristjan.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kristjan.demo.controller.DateController;
import com.kristjan.demo.model.DateModel;
import com.kristjan.demo.repository.DateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests storing and retrieving dates.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class DateControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DateRepository repository;

    @InjectMocks
    private DateController controller;

    private ObjectMapper mapper;
    private final LocalDate testDate = LocalDate.of(2021, 10, 23);

    @BeforeEach
    public void setup() {
        openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    /**
     * Tests that date is saved successfully when ID is null.
     */
    @Test
    public void testAddNewDateWithNullId() throws Exception {
        testAddDateSuccessfully(mapper.writeValueAsString(
                new DateModel(null, testDate)));
    }

    /**
     * Tests that date is saved successfully when ID is 0.
     */
    @Test
    public void testAddNewDateWithZeroId() throws Exception {
        testAddDateSuccessfully(mapper.writeValueAsString(
                new DateModel(0L, testDate)));
    }

    /**
     * Tests that date is saved successfully when ID is not defined in JSON.
     */
    @Test
    public void testAddNewDateWithoutId() throws Exception {
        ObjectNode json = mapper.createObjectNode();
        json.put("date", testDate.toString());
        testAddDateSuccessfully(mapper.writeValueAsString(json));
    }

    private void testAddDateSuccessfully(String json) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/date")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<DateModel> argCaptor = ArgumentCaptor.forClass(DateModel.class);
        verify(repository, times(1)).save(argCaptor.capture());
        verifyNoMoreInteractions(repository);

        DateModel receivedDate = argCaptor.getValue();
        assertNull(receivedDate.getId());
        assertTrue(receivedDate.getDate().isEqual(testDate));
    }

    /**
     * Tests that error occurs when ID is set when using POST method.
     */
    @Test
    public void testAddNewDateWithSetId() throws Exception {
        String json = mapper.writeValueAsString(new DateModel(42L, testDate));

        mockMvc.perform(MockMvcRequestBuilders.post("/date")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that error occurs when invalid date format is used.
     */
    @Test
    public void testAddNewDateUsingUnsupportedFormat() throws Exception {
        ObjectNode jsonNode = mapper.createObjectNode();
        jsonNode.put("date", "10.10.2021");
        String json = mapper.writeValueAsString(jsonNode);

        mockMvc.perform(MockMvcRequestBuilders.post("/date")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that getting dates works.
     */
    @Test
    public void testGetAllDates() throws Exception {
        List<DateModel> dates = List.of(new DateModel(1L, testDate));
        when(repository.findAll()).thenReturn(dates);

        mockMvc.perform(get("/date")).andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].date").value(testDate.toString()));
    }
}
