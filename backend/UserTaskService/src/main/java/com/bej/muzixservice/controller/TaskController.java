package com.bej.muzixservice.controller;

import com.bej.muzixservice.domain.Task;
import com.bej.muzixservice.domain.User;
import com.bej.muzixservice.exception.TaskAlreadyExistsException;
import com.bej.muzixservice.exception.TaskNotFoundException;
import com.bej.muzixservice.exception.UserAlreadyExistsException;
import com.bej.muzixservice.service.ITaskService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v3/")
public class TaskController {
    private ITaskService taskService;
    private ResponseEntity<?> responseEntity;

    @Autowired
    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws UserAlreadyExistsException {
        // Register a new user and save to db, return 201 status if user is saved else 500 status
        try {
            responseEntity = new ResponseEntity<>(taskService.registerUser(user), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            responseEntity = new ResponseEntity<>("Error in registering user!!", HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PostMapping("user/task")
    public ResponseEntity<?> saveTask(@RequestBody Task task, HttpServletRequest request) throws TaskAlreadyExistsException {
        // add a task to a specific user, return 201 status if task is saved else 500 status

        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("id from claims :: " + claims.getSubject());
            String id = claims.getSubject();
            System.out.println("id :: " + id);
            responseEntity = new ResponseEntity<>(taskService.saveUserTaskToWishList(task, id), HttpStatus.CREATED);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Could not save task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("user/tasks")
    public ResponseEntity<?> getAllTasks(HttpServletRequest request) {

        // display all the tasks of a specific user, extract user id from claims,
        // return 200 status if success else return 500 status
        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("id from claims :: " + claims.getSubject());
            String id = claims.getSubject();
            System.out.println("id :: " + id);
            responseEntity = new ResponseEntity<>(taskService.getAllUserTasksFromWishList(id), HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Failed to fetch Tasks!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping("user/task/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable String taskId, HttpServletRequest request) throws TaskNotFoundException {
        // delete a task based on email and task id, extract email from claims
        // return 200 status if deleted else return 500 status

        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("id from claims :: " + claims.getSubject());
            String id = claims.getSubject();
            System.out.println("id :: " + id);
            responseEntity = new ResponseEntity<>(taskService.deleteTask(id, taskId), HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("The task you deleted, is not present in database!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping("user/task")
    public ResponseEntity<?> updateTask(@RequestBody Task task, HttpServletRequest request) {
        // update a task based on email and task id, extract email from claims
        // return 200 status if updated else return 500 status

        try {
            String id = getEmailFromClaims(request);
            responseEntity = new ResponseEntity<>(taskService.updateUserTaskWishListWithGivenTask(id, task), HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Could not update!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    private String getEmailFromClaims(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        System.out.println("email from claims :: " + claims.getSubject());
        return claims.getSubject();
    }
}
