
/*
 * (c) Copyright 2019 sothawo
 */

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class Miscellaneous {
	public static void main(String[] args) throws NoSuchFieldException {

		printAnnoValuesForField(NameOnly.class);
		printAnnoValuesForField(ValueOnlyImplizit.class);
		printAnnoValuesForField(ValueOnlyExplizit.class);

	}

	private static void printAnnoValuesForField(Class<?> aClass) throws NoSuchFieldException {

		java.lang.reflect.Field field = aClass.getDeclaredField("field");

		AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(field, Field.class.getName());

		System.out.println(aClass.getSimpleName() + ", attributes: " + attributes);
	}

	static class NameOnly {
		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		@Field(name = "oops") String field;
	}

	static class ValueOnlyImplizit {
		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		@Field("oops") String field;
	}

	static class ValueOnlyExplizit {
		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		@Field(value = "oops") String field;
	}
}
