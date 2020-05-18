package com.github.chic.admin.model.param;

import lombok.Data;

@Data
public class RefreshParam {
    private String accessToken;
    private String refreshToken;
}
