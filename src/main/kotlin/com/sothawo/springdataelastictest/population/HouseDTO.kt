package com.sothawo.springdataelastictest.population

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
data class HouseDTO(
	val id: String,
	val zip: String?,
	val city: String?,
	val street: String?,
	val streetNumber: String?,
	val persons: List<PersonDTO>,
) {
	data class PersonDTO(
		val id: String,
		val lastName: String?,
		val firstName: String?,
	)
}
