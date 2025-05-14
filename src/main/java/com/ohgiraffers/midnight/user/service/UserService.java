package com.ohgiraffers.midnight.user.service;

import com.ohgiraffers.midnight.user.domain.entity.User;
import com.ohgiraffers.midnight.user.dto.NicknameAssetDto;
import com.ohgiraffers.midnight.user.dto.SignupRequestDto;
import com.ohgiraffers.midnight.user.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean createUser(SignupRequestDto signupRequestDto) {
        if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
            return false;  // 이메일이 이미 존재하면 실패
        }
        if (userRepository.existsByNickname(signupRequestDto.getNickname())) {
            return false;  // 닉네임이 이미 존재하면 실패
        }
        // User 객체 생성
        User user = new User(signupRequestDto.getNickname(), signupRequestDto.getEmail(), signupRequestDto.getPassword(), 0, LocalDateTime.now(), LocalDateTime.now());
        // 데이터베이스에 저장
        userRepository.save(user);
        return true;
    }


    public Optional<NicknameAssetDto> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> new NicknameAssetDto(user.getNickname(), user.getAssets()));
    }

    public boolean updateAsset(String nickname, String assets) {
        if (!userRepository.existsByNickname(nickname)) {
            return false;  // 닉네임이 존재하지 않으면 실패
        }
        // 닉네임으로 유저를 찾아서
        User user = userRepository.findByNickname(nickname);

        // 현재 유저의 assets와 비교하여 더 큰 값으로 업데이트
        if (user.getAssets() < Integer.parseInt(assets)) {
            user.setAssets(Integer.parseInt(assets));  // assets 값을 갱신
            userRepository.save(user);  // 변경된 user 객체를 저장
            return true;  // 성공적으로 업데이트됨
        }

        return false;  // 새로운 assets가 기존 assets보다 작으면 업데이트하지 않음
    }

    public List<NicknameAssetDto> rank() {
        // DB에서 조회 로직 (예시)
        List<User> users = userRepository.findAll();  // 예시: 모든 유저 가져오기

        // User 객체를 NicknameAssetDto로 변환
        return users.stream()
                .map(user -> new NicknameAssetDto(user.getNickname(), user.getAssets()))
                .sorted(Comparator.comparingInt(NicknameAssetDto::getAssets).reversed())  // assets 내림차순 정렬
                .collect(Collectors.toList());
    }

}
