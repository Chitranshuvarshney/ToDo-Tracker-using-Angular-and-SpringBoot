package com.bej.muzixservice.service;

import com.bej.muzixservice.domain.MailStructure;
import com.bej.muzixservice.domain.Task;
import com.bej.muzixservice.domain.User;
import com.bej.muzixservice.exception.TaskAlreadyExistsException;
import com.bej.muzixservice.exception.TaskNotFoundException;
import com.bej.muzixservice.exception.UserAlreadyExistsException;
import com.bej.muzixservice.exception.UserNotFoundException;
import com.bej.muzixservice.proxy.EmailProxy;
import com.bej.muzixservice.proxy.UserProxy;
import com.bej.muzixservice.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class TaskServiceImpl implements ITaskService {

    private UserTaskRepository userTaskRepository;
    private UserProxy userProxy;
    private EmailProxy emailProxy;

    // Autowire the userTaskRepository using constructor autowiring
    @Autowired
    public TaskServiceImpl(UserTaskRepository userTaskRepository, UserProxy userProxy, EmailProxy emailProxy) {
        this.userTaskRepository = userTaskRepository;
        this.userProxy = userProxy;
        this.emailProxy = emailProxy;
    }


    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        // Register a new user
        if (userTaskRepository.findById(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        User savedUser = userTaskRepository.save(user);
        if (!(savedUser.getEmail().isEmpty())) {
            ResponseEntity<?> responseEntity = userProxy.saveUser(user);
            String email = emailProxy.sendMail(user.getEmail(), new MailStructure("Registration Done", "Hi,\nCongratulation ! Your Account has been created on To-Do Tracker Application.\nYou can sign in to your account from by using your given credentials.\n\nRegards,\nTeam To-Do Tracker"));
            System.out.println(responseEntity.getBody());
            System.out.println(email);
        }
        return savedUser;
    }

    @Override
    public User saveUserTaskToWishList(Task task, String email) throws TaskNotFoundException, UserNotFoundException {
        // Save the tasks to the play list of user.
        if (userTaskRepository.findById(email).isEmpty()) {
            throw new TaskNotFoundException();
        }

        User user = userTaskRepository.findById(email).get();
        String emailAdd = emailProxy.sendMail(user.getEmail(), new MailStructure("Task Added Success", "Hi,\nA new Task has been added to your To-Do Tracker Dashboard.\nNow, you can view and access easily.\n\nRegards,\nTeam To-Do Tracker"));
        if (user.getTaskList() == null) {
            user.setTaskList(Arrays.asList(task));
        } else {
            List<Task> tasks = user.getTaskList();
            tasks.add(task);
            user.setTaskList(tasks);
        }
        return userTaskRepository.save(user);
    }

    @Override
    public List<Task> getAllUserTasksFromWishList(String email) throws Exception {
        // Get all the tasks for a specific user
        if (userTaskRepository.findById(email).isEmpty()) {
            throw new Exception();
        }
        return userTaskRepository.findById(email).get().getTaskList();

    }

    @Override
    public User deleteTask(String email, String taskId) throws TaskNotFoundException, UserNotFoundException {
        // delete the user task details specified
        boolean emailIsPresent = false;
        if (userTaskRepository.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userTaskRepository.findById(email).get();
        List<Task> products = user.getTaskList();
        emailIsPresent = products.removeIf(x -> x.getTaskId().equals(taskId));
        if (!emailIsPresent) {
            throw new TaskNotFoundException();
        }
        user.setTaskList(products);
        return userTaskRepository.save(user);
    }


    @Override
    public User updateUserTaskWishListWithGivenTask(String email, Task task) throws UserNotFoundException, TaskNotFoundException, TaskAlreadyExistsException {
        // Update the specific task details
        boolean flag = false;
        if (userTaskRepository.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userTaskRepository.findById(email).get();
        List<Task> taskList = user.getTaskList();

        Iterator<Task> taskIterator = taskList.iterator();
        while (taskIterator.hasNext()) {
            Task task1 = taskIterator.next();
            if (task1.getTaskId().equals(task.getTaskId())) {
                if (task.getTaskName() != null)
                    task1.setTaskName(task.getTaskName());
                if (task.getDescription() != null)
                    task1.setDescription(task.getDescription());
                if (task.getCategory() != null)
                    task1.setCategory(task.getCategory());
                if (task.getDueDate() != null)
                    task1.setDueDate(task.getDueDate());
                if (task.getPriority() != null)
                    task1.setPriority(task.getPriority());
                if (task.getStatus() != null)
                    task1.setStatus(task.getStatus());
                if (task.getComments() != null)
                    task1.setComments(task.getComments());
                flag = true;
            }
        }
        if (!flag) {
            throw new TaskNotFoundException();
        }
        user.setTaskList(taskList);
        return userTaskRepository.save(user);
    }
}
