package com.likelion.domain.entity;

import com.likelion.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;


    private String userName;

    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public User(String userName, String password, UserRole role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public void update(String password) {
        this.password = password;
    }
}
