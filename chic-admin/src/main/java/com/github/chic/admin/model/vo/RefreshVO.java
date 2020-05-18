package com.github.chic.admin.model.vo;

import lombok.Data;

@Data
public class RefreshVO {
    private String accessToken;
    private String refreshToken;
}
