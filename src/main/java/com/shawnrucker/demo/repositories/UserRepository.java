package com.shawnrucker.demo.repositories;

import com.shawnrucker.demo.models.User;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

public interface UserRepository extends CouchbaseRepository<User, String> {
    User findByUsername(String username);
}
