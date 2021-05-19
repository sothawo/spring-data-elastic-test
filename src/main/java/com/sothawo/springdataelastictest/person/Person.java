/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.Faker;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.ScriptedField;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "person")
@Mapping(runtimeFieldsPath = "/runtime-fields-person.json")
public class Person implements Persistable<Long> {

    private static final Faker FAKER = new Faker(Locale.GERMANY);

    @Id
    @Nullable
    @Field(type = FieldType.Long, name="id")
    private Long internalId;

    @Nullable
    @Version
    Long version;

    @Field(type = FieldType.Text, fielddata = true)
    @Nullable
    private String lastName;

    @Field(type = FieldType.Text, fielddata = true)
    @Nullable
    private String firstName;

    @Field(type = FieldType.Date, format = DateFormat.basic_date)
    @Nullable
    private LocalDate birthDate;

    @ReadOnlyProperty // do not write to prevent ES from automapping
    @Nullable
    private Integer age;

    @Field(type = FieldType.Nested)
    @Nullable
    private List<Car> cars;

    @CreatedDate
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    @Nullable
    private Instant created;
    @CreatedBy
    @Nullable
    private String createdBy;
    @LastModifiedDate
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    @Nullable
    private Instant lastModified;
    @LastModifiedBy
    @Nullable
    private String lastModifiedBy;

    @Nullable
    public Long getInternalId() {
        return internalId;
    }

    public void setInternalId(@Nullable Long internalId) {
        this.internalId = internalId;
    }

    @Nullable
    public Long getId() {
        return internalId;
    }


    @Nullable
    public Long getVersion() {
        return version;
    }

    public void setVersion(@Nullable Long version) {
        this.version = version;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Nullable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Nullable
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Nullable
    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    @Nullable
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Nullable
    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    @Nullable
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Nullable
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(@Nullable List<Car> cars) {
        this.cars = cars;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return internalId == null || (createdBy == null && created == null);
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + internalId +
            ", lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", birthDate=" + birthDate +
            ", cars=" + cars +
            ", created=" + created +
            ", createdBy='" + createdBy + '\'' +
            ", lastModified=" + lastModified +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            '}';
    }

    public static Person create(long id) {
        Person person = new Person();
        person.setInternalId(id);
        person.setFirstName(FAKER.name().firstName());
        person.setLastName(FAKER.name().lastName());
        var birthday = FAKER.date().birthday();
        var instant = Instant.ofEpochMilli(birthday.getTime());
        var localDate = LocalDate.ofInstant(instant, ZoneId.of("UTC"));
        person.setBirthDate(localDate);
        return person;
    }

}
