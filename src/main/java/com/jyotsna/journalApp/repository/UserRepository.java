package com.jyotsna.journalApp.repository;

import com.jyotsna.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String userName);
    void deleteByUsername(String username);

}
