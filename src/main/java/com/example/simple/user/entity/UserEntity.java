package com.example.simple.user.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long index;

    @NotNull
    private String name;

    @NotNull
    private String id;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private LocalDateTime created_at;

    private LocalDateTime updateAt;

    public UserEntity(String name, String id, String password, String email, LocalDateTime createdAt){
        this.name = name;
        this.id = id;
        this.password = password;
        this.email = email;
        this.created_at = createdAt;
    }

}
