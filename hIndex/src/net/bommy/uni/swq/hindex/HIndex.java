package net.bommy.uni.swq.hindex;

import java.util.ArrayList;
import java.util.LinkedList;

//####################################################
//### eigene Implementierung von PaperCitations ######
//### muss evtl. entfernt werden #####################
import net.bommy.uni.swq.hindex.dummy.PaperCitations;
//####################################################

/**
 * Diese Klasse berechnet den h-Index f&uuml;r &uuml;bergebene Autoren.
 * @author Thomas Laarmann
 * @version 1.0
 */
public class HIndex {
	/** unsortierte Liste aller eingelesenen Autoren */
	private ArrayList<ListAuthor> author;

	/**
	 * Initialisiert ein neues Objekt von HIndex und erwartet hierbei eine bereits
	 * gef&uuml;llte LinkedList von PaperCitations. Diese wird ausgelesen und in die
	 * interne Liste von Autoren &uuml;bertragen.
	 * @param liste die erwartete LinkedList<PaperCitations>
	 */
	public HIndex(LinkedList<PaperCitations> liste) {
		//ArrayList initialisieren
		author = new ArrayList<ListAuthor>();
		//PaperCitations auslesen und einsortieren
		for(PaperCitations pc : liste) {
			int vorhanden = containsAuthor(pc.author);
			//Author bereits vorhanden
			if( vorhanden >= 0 ) {
				author.get(vorhanden).add( pc.title, pc.citations );
			}
			//Author noch nicht vorhanden
			else {
				author.add(new ListAuthor( pc.author, pc.title, pc.citations ));
			}
		}
	}
	
	/**
	 * Berechnet den h-Index des &uuml;bergebenen Autors und gibt diesen zur&uuml;ck.
	 * @param author Name des Autors
	 * @return h-Index des Autors oder -1 wenn der Autor nicht existiert
	 */
	public int getHIndex(String author) {
		for(ListAuthor a : this.author) {
			if(author.equals( a.getAuthor() )) return a.getHIndex();
		}
		return -1;
	}
	
	/**
	 * Durchsucht die Liste der vorhandenen Autoren und gibt die Position des Autoren
	 * in der Liste als Index zur&uuml;ck.
	 * @param author Name des gesuchten Autors
	 * @return Position als Index oder -1 falls der Autor noch nicht existiert
	 */
	private int containsAuthor(String author) {
		//wenn die Liste noch leer ist, brauch sie auch nicht durchsucht werden
		if(this.author.size() <= 0) return -1;
		//suchen nach Übereinstimmung
		for(int i=0; i<this.author.size(); i++) {
			if(author.equals( this.author.get(i).getAuthor() )) return i;
		}
		return -1;
	}
}
