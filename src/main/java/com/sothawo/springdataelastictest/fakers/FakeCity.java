package com.sothawo.springdataelastictest.fakers;

public class FakeCity extends Faker {
	private final static String[] names =
			new String[]{"Aachen", "Augsburg", "Bergisch Gladbach", "Berlin", "Bielefeld", "Bochum", "Bonn", "Bottrop",
						 "Braunschweig", "Bremen", "Bremerhaven", "Chemnitz", "Darmstadt", "Dortmund", "Dresden",
						 "Duisburg", "Düsseldorf", "Erfurt", "Erlangen", "Essen", "Frankfurt", "Freiburg", "Fürth",
						 "Gelsenkirchen", "Göttingen", "Hagen", "Halle", "Hamburg", "Hamm", "Hannover", "Heidelberg",
						 "Heilbronn", "Herne", "Ingolstadt", "Jena", "Karlsruhe", "Kassel", "Kiel", "Koblenz",
						 "Krefeld", "Köln", "Leipzig", "Leverkusen", "Ludwigshafen", "Lübeck", "Magdeburg", "Mainz",
						 "Mannheim", "Moers", "Mülheim", "München", "Münster", "Neuss", "Nürnberg", "Oberhausen",
						 "Offenbach", "Oldenburg", "Osnabrück", "Paderborn", "Pforzheim", "Potsdam", "Recklinghausen",
						 "Regensburg", "Remscheid", "Reutlingen", "Rostock", "Saarbrücken", "Solingen", "Stuttgart",
						 "Trier", "Ulm", "Wiesbaden", "Wolfsburg", "Wuppertal", "Würzburg",};

	private final String name;

	public FakeCity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static FakeCity city() {
		return new FakeCity(random(names));
	}
}
