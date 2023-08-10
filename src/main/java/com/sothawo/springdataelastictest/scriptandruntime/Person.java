package com.sothawo.springdataelastictest.scriptandruntime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.ScriptedField;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.data.elasticsearch.annotations.FieldType.*;

import java.lang.Integer;

@Document(indexName = "sar-person")
public record Person(
        @Id
        @Nullable
        String id,
        @Field(type = Text)
        String lastName,
        @Field(type = Text)
        String firstName,
        @Field(type = Keyword) String gender,
        @Field(type = Date, format = DateFormat.basic_date)
        LocalDate birthDate,
        @Nullable
        @ScriptedField Integer age
) {
    public Person(String id,String lastName, String firstName, String gender, String birthDate) {
        this(id,
                lastName,
                firstName,
                gender,
                LocalDate.parse(birthDate, DateTimeFormatter.ISO_LOCAL_DATE),
                null);
    }
}
