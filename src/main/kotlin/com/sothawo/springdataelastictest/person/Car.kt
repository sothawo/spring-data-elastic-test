package com.sothawo.springdataelastictest.person

import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
sealed class Car(@Field(type = FieldType.Text) var model: String? = null) {

    override fun toString(): String {
        return "Car{" +
            "model='" + model + '\'' +
            '}'
    }
}

class ElectricCar(@Field(type = FieldType.Integer) var range: Int? = null) : Car() {

    override fun toString(): String {
        return "ElectricCar{" +
            "range=" + range +
            "} " + super.toString()
    }
}

class CombustionCar(@Field(type = FieldType.Keyword) var fuelType: String? = null) : Car() {

    override fun toString(): String {
        return "CombustionCar{" +
            "fuelType='" + fuelType + '\'' +
            "} " + super.toString()
    }
}
