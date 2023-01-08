package com.sothawo.springdataelastictest.fakers;

import java.util.Random;

public class Faker {
	protected static Random rand = new Random();

	protected static String random(String[] array) {
		return array[rand.nextInt(array.length)];
	}
}
