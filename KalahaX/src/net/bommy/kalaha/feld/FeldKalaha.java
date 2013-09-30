package net.bommy.kalaha.feld;

public class FeldKalaha extends Feld {

	/**
	 * Erstellt ein Kalaha-Feld für den angegebenen Spieler.
	 * @param spieler Nummer des Spielers
	 */
	public FeldKalaha(int spieler) {
		super(spieler, 6);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Erstellt ein Kalaha-Feld für den angegebenen Spieler und
	 * setzt die Feld-Werte auf die Vorgaben.
	 * @param feldId ID des Feldes
	 * @param spieler Nummer des Spielers
	 * @param bohnen Anzahl der Bohnen
	 */
	public FeldKalaha(int feldId, int spieler, int bohnen) {
		super(feldId, 6, spieler, bohnen);
		// TODO Auto-generated constructor stub
	}

}
