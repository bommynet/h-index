package net.bommy.uni.swq.hindex;

/**
 * Diese Klasse repr&auml;sentiert ein ver&ouml;ffentlichtes Papier eines Autors.
 * 
 * @author Thomas Laarmann
 * @version 1.0
 */
public class Paper {
	private String title;
	private int citations;
	
	/**
	 * Verhindert lediglich die Initialisierung ohne Parameter
	 */
	@SuppressWarnings("unused")
	private Paper() {}
	
	/**
	 * Initialisiert ein neues Paper-Objekt.
	 * @param title Titel des ver&ouml;ffentlichten Papiers
	 * @param citations Anzahl der Zitate
	 */
	public Paper(String title, int citations) {
		this.title = title;
		this.citations = citations;
	}

	/**
	 * Gibt den Titel des ver&ouml;ffentlichten Papiers zur&uuml;ck.
	 * @return Titel des Papiers
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gibt die Anzahl der Ver&ouml;ffentlichungen des Papiers zur&uuml;ck.
	 * @return Anzahl der Ver&ouml;ffentlichungen
	 */
	public int getCitations() {
		return citations;
	}
}
