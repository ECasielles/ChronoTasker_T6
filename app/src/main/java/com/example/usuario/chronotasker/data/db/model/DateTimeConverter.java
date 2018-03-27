package com.example.usuario.chronotasker.data.db.model;

import org.joda.time.DateTime;

import io.objectbox.converter.PropertyConverter;


public class DateTimeConverter implements PropertyConverter {
    @Override
    public Object convertToEntityProperty(Object databaseValue) {
        return new DateTime((long) databaseValue);
    }

    @Override
    public Object convertToDatabaseValue(Object entityProperty) {
        return ((DateTime) entityProperty).getMillis();
    }
}
