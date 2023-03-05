package com.sothawo.springdataelastictest.enums;

import org.springframework.data.elasticsearch.core.mapping.PropertyValueConverter;

public class ManufacturerPropertyValueConverter implements PropertyValueConverter {
	@Override
	public Object write(Object value) {
		if (value instanceof Manufacturer m) {
			return m.getDisplayName();
		}
		return value;
	}

	@Override
	public Object read(Object value) {
		if (value instanceof String s) {
			var manufacturer = Manufacturer.of(s);
			if (manufacturer != null) {
				return manufacturer;
			}
		}
		return value;
	}
}
