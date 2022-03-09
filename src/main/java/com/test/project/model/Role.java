package com.test.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Role {
    MANAGER("M"),
    USER("U");

    @Getter
    private final String alias;
}
