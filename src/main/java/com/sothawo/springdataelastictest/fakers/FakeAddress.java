package com.sothawo.springdataelastictest.fakers;

public class FakeAddress extends Faker {

	private static String[] streetNames =
			new String[]{"Ahornstraße", "Ahornweg", "Akazienweg", "Am Anger", "Am Bahnhof", "Am Berg", "Am Friedhof",
						 "Am Hang", "Am Sportplatz", "Amselweg", "An der Kirche", "Asternweg", "Bachstraße",
						 "Bahnhofstraße", "Bergstraße", "Bergweg", "Berliner", "Birkenstraße", "Birkenweg",
						 "Blumenstraße", "Blumenweg", "Brunnenweg", "Buchenstraße", "Buchenweg", "Burgstraße",
						 "Burgweg", "Dahlienweg", "Danziger Str", "Dieselstraße", "Dorfplatz", "Dorfstraße",
						 "Drosselweg", "Eichenstraße", "Eichenweg", "Erlenstraße", "Erlenweg", "Eschenweg", "Falkenweg",
						 "Fasanenweg", "Feldstraße", "Feldweg", "Fichtenweg", "Finkenweg", "Fliederweg", "Flurstraße",
						 "Forststraße", "Forstweg", "Friedhofsweg", "Gartenstraße", "Gartenweg", "Ginsterweg",
						 "Goethestraße", "Grabenstraße", "Grenzweg", "Grüner Weg", "Hauptstraße", "Haydnstraße",
						 "Heckenweg", "Heideweg", "Hochstraße", "Hohe Straße", "Hohlweg", "Holunderweg", "Höhenweg",
						 "Im Winkel", "Jahnstraße", "Jägerstraße", "Kantstraße", "Kapellenweg", "Karlstraße",
						 "Kastanienweg", "Kiefernweg", "Kirchberg", "Kirchenweg", "Kirchgasse", "Kirchplatz",
						 "Kirchstraße", "Kirchweg", "Kreuzstraße", "Kreuzweg", "Kurze Straße", "Lange Straße",
						 "Lerchenweg", "Lilienweg", "Lindenallee", "Lindenstraße", "Lindenweg", "Ludwigstraße",
						 "Lärchenweg", "Marienstraße", "Markt", "Marktplatz", "Marktstraße", "Meisenweg",
						 "Mittelstraße", "Mittelweg", "Mozartstraße", "Mörikestraße", "Mühlenstraße", "Mühlenweg",
						 "Mühlgasse", "Mühlstraße", "Mühlweg", "Nelkenstraße", "Nelkenweg", "Neue Straße", "Neuer Weg",
						 "Nordstraße", "Oststraße", "Pappelweg", "Parkstraße", "Pfarrgasse", "Poststraße", "Querstraße",
						 "Rathausplatz", "Ringstraße", "Rosenstraße", "Rosenweg", "Rotdornweg", "Römerstraße",
						 "Sandweg", "Schlehenweg", "Schloßstraße", "Schulstraße", "Schulweg", "Schwalbenweg",
						 "Schwarzer Weg", "Seestraße", "Siedlung", "Sonnenstraße", "Sonnenweg", "Starenweg",
						 "Steinstraße", "Steinweg", "Südstraße", "Talstraße", "Tannenstraße", "Tannenweg",
						 "Teichstraße", "Tulpenstraße", "Tulpenweg", "Uhlandstraße", "Ulmenstraße", "Ulmenweg",
						 "Veilchenweg", "Wacholderweg", "Waldstraße", "Waldweg", "Weidenweg", "Weiherstraße",
						 "Weststraße", "Wiesengrund", "Wiesenstraße", "Wiesenweg",};

	private final String streetName;
	private final String houseNumber;

	public FakeAddress(String streetName, String houseNumber) {
		this.streetName = streetName;
		this.houseNumber = houseNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public static FakeAddress address() {
		return new FakeAddress(random(streetNames), String.valueOf(rand.nextInt(100) + 1));
	}
}
