package com.bej.muzixservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND , reason = "Task with specified id is not found")
public class TaskNotFoundException extends Exception {
}
