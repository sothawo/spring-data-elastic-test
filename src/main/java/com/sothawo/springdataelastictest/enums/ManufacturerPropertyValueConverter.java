package com.sothawo.springdataelastictest.enums;

import org.springframework.data.elasticsearch.core.convert.AbstractPropertyValueConverter;
import org.springframework.data.mapping.PersistentProperty;

public class ManufacturerPropertyValueConverter extends AbstractPropertyValueConverter {
	public ManufacturerPropertyValueConverter(PersistentProperty<?> property) {
		super(property);
	}

	@Override
	public Object write(Object value) {
		if (value instanceof Manufacturer m) {
			return m.getDisplayName();
		}
		return value;
	}

	@Override
	public Object read(Object value) {
		if (value instanceof String s && Manufacturer.class.isAssignableFrom(getProperty().getType())) {
			var manufacturer = Manufacturer.of(s);
			if (manufacturer != null) {
				return manufacturer;
			}
		}
		return value;
	}
}
