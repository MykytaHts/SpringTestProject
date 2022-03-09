package com.test.project.util;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;

@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<String, LocalDate> {

    @Override
    public LocalDate convertToDatabaseColumn(String s) {
        return LocalDate.parse(s);
    }

    @Override
    public String convertToEntityAttribute(LocalDate localDate) {
        return localDate.toString();
    }
}
