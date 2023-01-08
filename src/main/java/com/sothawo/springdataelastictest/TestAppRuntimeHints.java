package com.sothawo.springdataelastictest;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

import java.time.LocalDate;
import java.util.Arrays;

public class TestAppRuntimeHints implements RuntimeHintsRegistrar {
	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		hints.reflection().registerTypes( //
				Arrays.asList( //
						TypeReference.of(LocalDate.class) //
				), //
				builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
						MemberCategory.INVOKE_PUBLIC_METHODS));


		hints.resources().registerPattern("runtime-fields-person.json");
	}
}
