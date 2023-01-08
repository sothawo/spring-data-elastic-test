package com.sothawo.springdataelastictest.fakers;

import java.time.LocalDate;

public class FakeBirthDate extends Faker {

	public static LocalDate birthDate() {
		var now = LocalDate.now();
		var year = now.getYear() - 1 - (rand.nextInt(90));
		var month = rand.nextInt(12) + 1;
		var day = rand.nextInt(28) + 1;
		return LocalDate.of(year, month, day);
	}
}
