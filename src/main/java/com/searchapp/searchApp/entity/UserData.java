package com.searchapp.searchApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityScan
@Table(name = "user_data")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int userId;
    private String name;
    private int age;
    private String country;
    private String mobile;
    private String email;
}
