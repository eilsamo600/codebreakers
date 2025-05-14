package com.ohgiraffers.midnight.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NicknameAssetDto {
    private String nickname;
    private int assets;

    public NicknameAssetDto(String nickname, int assets) {
        this.nickname = nickname;
        this.assets = assets;
    }

}
