package com.geo.test_security.controller.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
