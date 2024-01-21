package com.bej.muzixservice.repository;

import com.bej.muzixservice.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserTaskRepository extends MongoRepository<User,String> {

}
