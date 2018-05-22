package com.example.usuario.chronotasker.data.db.converter;

import android.databinding.BaseObservable;

import org.joda.time.DateTime;

import io.objectbox.converter.PropertyConverter;


public class DateTimeConverter extends BaseObservable implements PropertyConverter<DateTime, Long> {

    @Override
    public DateTime convertToEntityProperty(Long databaseValue) {
        return new DateTime(databaseValue);
    }

    @Override
    public Long convertToDatabaseValue(DateTime entityProperty) {
        return entityProperty.getMillis();
    }

}