package com.bej.muzixservice.repository;

import com.bej.muzixservice.domain.Task;
import com.bej.muzixservice.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class TaskRepositoryTest {
    @Autowired
    private UserTaskRepository userTaskRepository;

    private  List<Task> taskList;
    private User user;


    @BeforeEach
    void setUp() {

        user = new User();
        user.setEmail("U1234");
        user.setAge(35);
        user.setUsername("John");
        user.setEmail("John@gmail.com");
        user.setTaskList(taskList);

    }

    @AfterEach
    void tearDown() {
        userTaskRepository.deleteAll();
    }

    @Test
    void givenUserTaskToSaveShouldReturnSavedTask() {
        userTaskRepository.save(user);
        User user1 = userTaskRepository.findById(user.getEmail()).get();

        assertNotNull(user1);
        assertEquals(user1.getEmail(), user.getEmail());
    }
    @Test
    public void givenTaskToDeleteShouldDeleteTask() {
        userTaskRepository.insert(user);
        User user1 = userTaskRepository.findById(user.getEmail()).get();
        userTaskRepository.delete(user1);
        assertEquals(Optional.empty(), userTaskRepository.findById(user.getEmail()));
    }


}