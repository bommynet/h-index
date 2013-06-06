package net.bommy.uni.swq.hindex;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Diese Klasse repr&auml;sentiert einen Autor und alle seine ver&ouml;ffentlichten
 * Papiere.
 * 
 * @author Thomas Laarmann
 * @version 1.0
 */
public class ListAuthor {
	/** der Name des Autors */
	private String author;
	/** die Liste alles Papers */
	private ArrayList<Paper> paper;
	/** ein Flag zur Überwachung, ob der h-Index neu berechnet werden muss */
	private boolean changed;
	/** Zwischenspeicher für hindex */
	private int hindex;
	
	/**
	 * Verhindert lediglich die Initialisierung ohne Parameter
	 */
	@SuppressWarnings("unused")
	private ListAuthor() {}
	
	/**
	 * Initialisiert ein neues Objekt von ListAuthor, setzt den Namen des Autors fest
	 * und erstellt das erste ver&ouml;ffentlichte Papier. 
	 * @param name Name des Autors
	 * @param title Titel der ersten Ver&ouml;ffentlichung
	 * @param citations Anzahl der Zitierungen 
	 */
	public ListAuthor(String name, String title, int citations) {
		author = name;
		paper = new ArrayList<Paper>();
		paper.add(new Paper(title, citations));
		//h-Index initialisieren als noch nicht berechnet
		hindex = 0;
		setChanged();
	}
	
	/**
	 * Eine neue Ver&ouml;ffentlichung in die Liste des Autors einf&uuml;gen.
	 * @param title Titel der Ver&ouml;ffentlichung
	 * @param citations Anzahl der Zitierungen 
	 */
	public void add(String title, int citations) {
		this.paper.add( new Paper(title, citations) );
		setChanged();
	}

	/**
	 * Gibt den Namen des Autors zur&uuml;ck.
	 * @return name des Autors
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Gibt die Anzahl der Papiere des Autors zur&uuml;ck.
	 * @return Anzahl der Papiere
	 */
	public int getSize() {
		return paper.size();
	}
	
	/**
	 * 
	 * Berechnet den h-Index des Autors und gibt ihn zur&uuml;ck.
	 * @return h-Index des Autors oder -1 bei Fehlern
	 */
	public int getHIndex() {
		//wenn h-Index schon berechnet ist, direkt ausgeben
		if(!(changed)) return hindex;
		//wenn keine Papers existieren, gebe 0 zurück
		if(getSize() <= 0) return 0;
		//unbedingt paper sortieren vor der Auswertung!
		sortieren();
		//und abschließend auswerten
		int ret = 0;
		for(int i=1; i<=getSize(); i++) {
			if(paper.get(i-1).getCitations() < i) break;
			ret++;
		}
		//Zwischenspeicher beschreiben
		changed = false;
		hindex = ret;
		//hindex ausgeben
		return ret;
	}
	
	/**
	 * Wird diese Funktion aufgerufen, wird beim n&auml;chsten Aufruf von
	 * getHIndex() auf jeden Fall eine Neuberechnung durchgef&uuml;hrt.
	 */
	private void setChanged() {
		changed = true;
	}
	
	/**
	 * Die Liste aller paper wird nach Anzahl der Zitierungen absteigend sortiert. 
	 */
	private void sortieren() {
		java.util.Collections.sort(paper, new Comparator<Paper>() {
			@Override
			public int compare(Paper p1, Paper p2) {
				if(p1.getCitations() > p2.getCitations()) return -1;
				if(p1.getCitations() < p2.getCitations()) return 1;
				return 0;
			}
		});
	}
}
