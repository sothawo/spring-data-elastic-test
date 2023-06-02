package com.sothawo.springdataelastictest;

import com.sothawo.springdataelastictest.enums.ManufacturerPropertyValueConverter;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

import java.util.Arrays;

public class TestAppRuntimeHints implements RuntimeHintsRegistrar {
	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		hints.reflection().registerTypes( //
				Arrays.asList( //
						TypeReference.of(CustomZonedDateTimeConverter.class), //
						TypeReference.of(ManufacturerPropertyValueConverter.class)
				), //
				builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
						MemberCategory.INVOKE_PUBLIC_METHODS));

		hints.resources().registerPattern("named-queries.properties");
		hints.resources().registerPattern("runtime-fields-person.json");
	}
}
