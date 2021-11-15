package com.github.chic.admin.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class LoginVO {
    private Admin admin;
    private Token token;

    @Data
    public static class Admin {
        private Long id;
        private String username;
        private String nickname;
        private String avatarUrl;
    }

    @Data
    public static class Token {
        private Date accessExpire;
        private String accessToken;
        private Date refreshExpire;
        private String refreshToken;
    }
}
