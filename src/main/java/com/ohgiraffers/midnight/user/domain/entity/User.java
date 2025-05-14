package com.ohgiraffers.midnight.user.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "users")  // 'users' 테이블과 매핑
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;  // 로그인 시 사용할 ID

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;  // 사용자의 이메일

    @Column(nullable = false)
    private String password;  // 비밀번호

    @Column(nullable = false)
    @ColumnDefault("0")
    private int assets = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    protected User(){
    }

    public User( String nickname, String email, String password, int assets, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.assets = assets;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
