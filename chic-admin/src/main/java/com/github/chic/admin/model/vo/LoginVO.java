package com.github.chic.admin.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class LoginVO {
    @ApiModelProperty(value = "Admin")
    private Admin admin;
    @ApiModelProperty(value = "Token")
    private Token token;

    @Data
    public static class Admin {
        @ApiModelProperty(value = "ID")
        private Long id;
        @ApiModelProperty(value = "用户名")
        private String username;
        @ApiModelProperty(value = "昵称")
        private String nickname;
        @ApiModelProperty(value = "头像")
        private String avatarUrl;
    }

    @Data
    public static class Token {
        @ApiModelProperty(value = "accessToken")
        private String accessToken;
        @ApiModelProperty(value = "accessToken 过期时间")
        private Date accessExpire;
        @ApiModelProperty(value = "refreshToken")
        private String refreshToken;
        @ApiModelProperty(value = "refreshToken 过期时间")
        private Date refreshExpire;
    }
}
