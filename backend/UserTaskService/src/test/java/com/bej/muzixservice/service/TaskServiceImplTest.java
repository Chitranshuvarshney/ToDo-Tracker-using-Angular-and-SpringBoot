package com.bej.muzixservice.service;

import com.bej.muzixservice.domain.Task;
import com.bej.muzixservice.domain.User;
import com.bej.muzixservice.repository.UserTaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @Mock
    private UserTaskRepository userTaskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;
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
    }
    @AfterEach
    void tearDown() {
        task1 = null;
        task2 = null;
        user = null;
    }


    @Test
    public void getAllUserTasksFromWishListSuccess() throws Exception {
        when(userTaskRepository.findById(anyString())).thenReturn(Optional.ofNullable(user));
        when(userTaskRepository.findById(anyString())).thenReturn(Optional.of(user));
        assertEquals(taskList,taskService.getAllUserTasksFromWishList(user.getEmail()));
        verify(userTaskRepository,times(2)).findById(anyString());

    }


}