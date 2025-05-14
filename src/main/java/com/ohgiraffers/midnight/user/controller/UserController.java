package com.ohgiraffers.midnight.user.controller;

import com.ohgiraffers.midnight.user.domain.entity.User;
import com.ohgiraffers.midnight.user.dto.NicknameAssetDto;
import com.ohgiraffers.midnight.user.dto.SignupRequestDto;
import com.ohgiraffers.midnight.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/temp")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto signupRequestDto) {

        System.out.println("✅ POST /signup 요청 도착");
        System.out.println("요청 받은 이메일: " + signupRequestDto.getEmail());
        boolean isCreated = userService.createUser(signupRequestDto);

        if (isCreated) {
            System.out.println("회원가입 완료");
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패");
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String email,
            @RequestParam String password
    ) {
        return userService.login(email, password)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("이메일 또는 비밀번호가 틀렸습니다."));
    }

    @GetMapping("/rank")
    public ResponseEntity<?> rank(
            @RequestParam String nickname,
            @RequestParam String assets
    ) {
        boolean isUpdated = userService.updateAsset(nickname, assets);
        List<NicknameAssetDto> rankList = userService.rank();

        // rankList가 비어있지 않다면, rankList 반환
        if (rankList != null && !rankList.isEmpty()) {
            return ResponseEntity.ok(rankList);
        } else {
            // 비었거나 오류인 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("랭킹정보가 없습니다.");
        }
    }

}