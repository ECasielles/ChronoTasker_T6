package com.example.usuario.chronotasker.data.db.converter;

import android.databinding.BaseObservable;

import org.joda.time.Period;

import io.objectbox.converter.PropertyConverter;

//TODO: MAJOR FIX
public class PeriodConverter extends BaseObservable implements PropertyConverter<Period, Integer> {

    @Override
    public Period convertToEntityProperty(Integer databaseValue) {
        return databaseValue == null ?
                new Period(0)   :
                new Period().plusMillis(databaseValue);
    }

    @Override
    public Integer convertToDatabaseValue(Period entityProperty) {
        return entityProperty == null ? 0 :
                entityProperty.getMillis();
    }

}