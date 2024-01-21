package com.bej.muzixservice.controller;

import com.bej.muzixservice.domain.Task;
import com.bej.muzixservice.domain.User;
import com.bej.muzixservice.exception.UserAlreadyExistsException;
import com.bej.muzixservice.service.TaskServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TaskServiceImpl taskService;

    @InjectMocks
    private TaskController taskController;
    private Task task1, task2;
    List<Task> taskList;

    User user;

    @BeforeEach
    void setUp() {


        taskList = Arrays.asList(task1,task2);
        user = new User();
        user.setEmail("U1234");
        user.setAge(35);
        user.setUsername("John");
        user.setEmail("John@gmail.com");
        user.setTaskList(taskList);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }
    @AfterEach
    void tearDown() {
        user=null;
    }
    @Test
    public void testRegisterUser() throws Exception {
        when(taskService.registerUser(any())).thenReturn(user);
        mockMvc.perform(post("/api/v3/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(taskService,times(1)).registerUser(any());

    }
    @Test
    public void registerUserFailure() throws Exception {
        when(taskService.registerUser(any())).thenThrow(UserAlreadyExistsException.class);
        mockMvc.perform(post("/api/v3/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user)))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }
    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }
}