package com.sothawo.springdataelastictest.fakers;

public class FakePerson extends Faker {

	private static String firstNames[] =
			new String[]{"Alva", "Arya", "Bente", "Charly", "Darian", "Eli", "Elyah", "Freddie", "Hunter", "Isa",
						 "Jannic", "Jesse", "Jorah", "Jule", "Juli", "Junis", "Kaya", "Leo", "Loki", "Lory", "Lou",
						 "Luka", "Lyan", "Lyn", "Marlin", "Marlo", "Mattis", "Merle", "Mikah", "Milou", "Mio", "Nala",
						 "Nika", "Niki", "Nino", "Noah", "Noam", "Noe", "Noel", "Nori", "Robin", "Romy", "Ryan", "Sam",
						 "Samy", "Sky", "Tom", "Tony", "Yona", "Yuri",};
	private static String lastNames[] =
			new String[]{"Albrecht", "Arnold", "Bauer", "Baumann -", "Beck", "Becker", "Berger", "Bergmann", "Brandt",
						 "Braun", "Busch", "Böhm", "Dietrich", "Engel", "Fischer", "Frank", "Franke", "Fuchs", "Graf",
						 "Groß", "Günther", "Haas", "Hahn", "Hartmann", "Heinrich", "Herrmann", "Hoffmann", "Hofmann",
						 "Horn", "Huber", "Jung", "Kaiser", "Keller", "Klein", "Koch", "Kraus", "Krause", "Krämer",
						 "Krüger", "Kuhn", "Köhler", "König", "Kühn", "Lang", "Lange", "Lehmann", "Lorenz", "Ludwig",
						 "Maier", "Martin", "Mayer", "Meier", "Meyer", "Möller", "Müller", "Neumann", "Otto", "Peters",
						 "Pfeiffer", "Pohl", "Richter", "Roth", "Sauer", "Schmid", "Schmidt", "Schmitt", "Schmitz",
						 "Schneider", "Scholz", "Schreiber", "Schröder", "Schubert", "Schulte", "Schulz", "Schulze",
						 "Schumacher", "Schuster", "Schwarz", "Schäfer", "Seidel", "Simon", "Sommer", "Stein", "Thomas",
						 "Vogel", "Vogt", "Voigt", "Wagner", "Walter", "Weber", "Weiß", "Werner", "Winkler", "Winter",
						 "Wolf", "Wolff", "Ziegler", "Zimmermann",};

	private final String firstName;
	private final String lastName;

	public FakePerson(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public static FakePerson person() {
		return new FakePerson(random(firstNames), random(lastNames));
	}
}
