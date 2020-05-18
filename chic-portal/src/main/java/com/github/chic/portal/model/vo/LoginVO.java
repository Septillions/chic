package com.github.chic.portal.model.vo;

import lombok.Data;

@Data
public class LoginVO {
    private String accessToken;
    private String refreshToken;
}
