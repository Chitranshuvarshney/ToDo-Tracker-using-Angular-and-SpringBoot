package com.bej.muzixservice.service;

import com.bej.muzixservice.domain.Task;
import com.bej.muzixservice.domain.User;
import com.bej.muzixservice.exception.TaskAlreadyExistsException;
import com.bej.muzixservice.exception.TaskNotFoundException;
import com.bej.muzixservice.exception.UserAlreadyExistsException;
import com.bej.muzixservice.exception.UserNotFoundException;

import java.util.List;

public interface ITaskService {

    User registerUser(User user) throws UserAlreadyExistsException;
    User saveUserTaskToWishList(Task task, String email) throws TaskNotFoundException, UserNotFoundException;
    List<Task> getAllUserTasksFromWishList(String email) throws Exception;
    User deleteTask(String email, String taskId) throws TaskNotFoundException, UserNotFoundException;
    User updateUserTaskWishListWithGivenTask(String email, Task task) throws UserNotFoundException, TaskNotFoundException, TaskAlreadyExistsException;
}
