package com.searchapp.searchApp.dto;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data

public class UserDto {
    private String name;
    private int age;
    private String country;
    private String mobile;
    private String email;
}
