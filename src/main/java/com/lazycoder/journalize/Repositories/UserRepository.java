package com.lazycoder.journalize.Repositories;

import com.lazycoder.journalize.Entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);
    User deleteByUsername(String username);
}

