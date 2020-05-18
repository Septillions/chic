package com.github.chic.portal.model.vo;

import lombok.Data;

@Data
public class RefreshVO {
    private String accessToken;
    private String refreshToken;
}
