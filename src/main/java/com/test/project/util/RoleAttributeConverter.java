package com.test.project.util;

import com.test.project.model.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleAttributeConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        return role.getAlias();
    }

    @Override
    public Role convertToEntityAttribute(String s) {
        for (Role role : Role.values()) {
            if (role.getAlias().equals(s)) return role;
        }

        throw new IllegalArgumentException("Unknown code " + s);
    }
}
