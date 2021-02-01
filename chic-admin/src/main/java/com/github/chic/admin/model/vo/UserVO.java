package com.github.chic.admin.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {
    private Integer id;
    private String username;
    private String mobile;
    private String nickname;
    private String avatarUrl;
    private LocalDateTime createTime;
}
