package com.sothawo.springdataelastictest.enums;

public enum Manufacturer {
	YAMAHA("Yamaha", "Japan"),
	TAKAMINE("Takamine", "Japan"),
	FENDER("Fender", "United States"),
	GUILD("Guild", "United States"),
	HOEFNER("Höfner", "Germany")
	;

	private final String displayName;
	private final String country;

	Manufacturer(String displayName, String country) {
		this.displayName = displayName;
		this.country = country;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getCountry() {
		return country;
	}
}
