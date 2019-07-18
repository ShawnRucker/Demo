package com.shawnrucker.demo.models;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Document
public class Role {

    public Role() {}

    public Role(String role) {
        this.id="";
        this.role = role;
    }

    @NotNull
    @Id
    private String id = UUID.randomUUID().toString();

    @Field
    private String role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
