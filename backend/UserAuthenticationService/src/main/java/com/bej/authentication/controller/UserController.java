package com.bej.authentication.controller;

import com.bej.authentication.exception.UserAlreadyExistsException;
import com.bej.authentication.exception.InvalidCredentialsException;
import com.bej.authentication.security.SecurityTokenGenerator;
import com.bej.authentication.service.IUserService;
import com.bej.authentication.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private IUserService userService;
    private SecurityTokenGenerator securityTokenGenerator;
    private ResponseEntity responseEntity;

    @Autowired
    public UserController(IUserService userService, SecurityTokenGenerator securityTokenGenerator) {
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws UserAlreadyExistsException {
        // save a user, return 201 status if user is saved else 500 status
        try {
            responseEntity = new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);

        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException();
        }

        return responseEntity;

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) throws InvalidCredentialsException {
        // Generate the token on login, return 200 status if user is saved else 500 status
        User retrivedUser = userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if (retrivedUser == null) {
            throw new InvalidCredentialsException();
        }

        String token = securityTokenGenerator.createToken(user);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
