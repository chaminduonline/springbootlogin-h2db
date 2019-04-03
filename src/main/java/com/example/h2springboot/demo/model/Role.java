package com.example.h2springboot.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "role")
@Data
public class Role{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String role;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role(String role) {
        this.role= role;
    }

    public Role() {
    }
}
