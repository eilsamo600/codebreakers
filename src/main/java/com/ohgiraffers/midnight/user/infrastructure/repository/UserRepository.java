package com.ohgiraffers.midnight.user.infrastructure.repository;

import com.ohgiraffers.midnight.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer>{
    boolean existsByEmail(String email);  // 이메일 중복 체크 메소드
    boolean existsByNickname(String nickname);  // 닉네임 중복 체크 메소드
    Optional<User> findByEmail(String email);
    User findByNickname(String nickname);
}

