package com.app.domain.member.constant;

public enum Role {
    User,ADMIN;

    public static Role from(String role) {
        return Role.valueOf(role);
    }
}
