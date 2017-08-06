package com.nbsaw.miaohu.converter;

import com.nbsaw.miaohu.type.SexType;

import javax.persistence.AttributeConverter;

public class SexTypeConverter implements AttributeConverter<SexType,String> {
    @Override
    public String convertToDatabaseColumn(SexType attribute) {
        return attribute.getValue();
    }

    @Override
    public SexType convertToEntityAttribute(String dbData) {
        return SexType.fromString(dbData);
    }
}
