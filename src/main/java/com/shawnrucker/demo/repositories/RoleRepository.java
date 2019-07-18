package com.shawnrucker.demo.repositories;

import com.shawnrucker.demo.models.Role;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

public interface RoleRepository extends CouchbaseRepository<Role, String> {
    Role findByRole(String role);
}
