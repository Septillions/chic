package com.github.chic.portal.model.param;

import lombok.Data;

@Data
public class RefreshParam {
    private String accessToken;
    private String refreshToken;
}
