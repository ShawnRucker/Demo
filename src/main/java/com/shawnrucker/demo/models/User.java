package com.shawnrucker.demo.models;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Document
public class User {

    @NotNull
    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull
    @Field
    private String username;

    @Field
    @NotNull
    private String password;

    @Field
    private Boolean enabled;

    @Field
    private Set<Role> roles = new HashSet<>();

    @Field
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateofbirth;

    @Field
    private String email;

    @Field
    private Integer dailycaloriegoal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean containsRole(String role) {
        for(Role rl: this.roles) {
            if(rl.getRole().equals(role)){
                return true;
            }
        }
        return false;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDailycaloriegoal() {
        return dailycaloriegoal;
    }

    public void setDailycaloriegoal(Integer dailycaloriegoal) {
        this.dailycaloriegoal = dailycaloriegoal;
    }


}
