package net.froihofer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
public class Employee extends User{
    public Employee() {
    }

    public Employee(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
    }
}
