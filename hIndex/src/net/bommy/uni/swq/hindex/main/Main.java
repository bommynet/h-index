package net.bommy.uni.swq.hindex.main;

import java.util.LinkedList;

import net.bommy.uni.swq.hindex.HIndex;
import net.bommy.uni.swq.hindex.dummy.PaperCitations;

/**
 * Existiert nur, um die Funktionen von HIndex zu pr&uuml;fen.
 * @author Thomas Laarmann
 */
public class Main {
	private HIndex hi = new HIndex( Main.createDummy() );
	
	public static void main(String[] args) {
		Main m = new Main();
		m.berechne();
	}
	
	/**
	 * 
	 */
	public void berechne() {
		printIt(3, "Horst Eberhardt", "");
		printIt(3, "Horst Eberhardt", "ohne Neuberechnung");
		printIt(1, "Marianne Anna Kanaba", "");
		printIt(5, "Wiki1", "");
		printIt(2, "Wiki2", "");
		printIt(4, "Wiki3", "");
		printIt(0, "Selbst", "");
		printIt(-1, "FEHLER", "Autor existiert nicht!");
	}
	
	/**
	 * @param soll
	 * @param author
	 * @param info
	 */
	public void printIt(int soll, String author, String info) {
		String tmp = author + " => ";
		tmp += "soll[" + soll + "] | ";
		tmp += "ist[" + hi.getHIndex(author) + "]";
		if(!(info.equals(""))) {
			tmp += " [" + info + "]";
		}
		System.out.println( tmp );
	}
	
	/**
	 * Erzeugt eine LinkedList<PaperCitations> als Dummy.
	 * @return die erstellte Liste
	 */
	public static LinkedList<PaperCitations> createDummy() {
		LinkedList<PaperCitations> dummy = new LinkedList<PaperCitations>();
		
		//Einfaches Beispiel SOLL h-Index=3
		dummy.add(PaperCitations.create("Horst Eberhardt", "Kekse in der Spüle", 6));
		dummy.add(PaperCitations.create("Horst Eberhardt", "Karnickelalarm", 1));
		dummy.add(PaperCitations.create("Horst Eberhardt", "Strukturen to go", 32));
		dummy.add(PaperCitations.create("Horst Eberhardt", "Rekursives nullen", 6));
		
		//Einfaches Beispiel SOLL h-Index=1
		dummy.add(PaperCitations.create("Marianne Anna Kanaba", "Pommes 94", 112));
		
		//Wiki-Beispiel SOLL h-Index=5
		dummy.add( PaperCitations.create("Wiki1", "Papier3", 3) );
		dummy.add( PaperCitations.create("Wiki1", "Papier7", 7) );
		dummy.add( PaperCitations.create("Wiki1", "Papier2", 2) );
		dummy.add( PaperCitations.create("Wiki1", "Papier1", 1) );
		dummy.add( PaperCitations.create("Wiki1", "Papier10", 10) );
		dummy.add( PaperCitations.create("Wiki1", "Papier9", 9) );
		dummy.add( PaperCitations.create("Wiki1", "Papier8", 8) );
		dummy.add( PaperCitations.create("Wiki1", "Papier5", 5) );
		dummy.add( PaperCitations.create("Wiki1", "Papier4", 4) );
		dummy.add( PaperCitations.create("Wiki1", "Papier6", 6) );
		
		//Wiki-Beispiel SOLL h-Index=2
		dummy.add( PaperCitations.create("Wiki2", "Papier3", 2) );
		dummy.add( PaperCitations.create("Wiki2", "Papier7", 2) );
		dummy.add( PaperCitations.create("Wiki2", "Papier2", 2) );
		dummy.add( PaperCitations.create("Wiki2", "Papier1", 2) );
		dummy.add( PaperCitations.create("Wiki2", "Papier10", 200) );
		dummy.add( PaperCitations.create("Wiki2", "Papier9", 2) );
		dummy.add( PaperCitations.create("Wiki2", "Papier8", 2) );
		dummy.add( PaperCitations.create("Wiki2", "Papier5", 200) );
		dummy.add( PaperCitations.create("Wiki2", "Papier4", 2) );
		dummy.add( PaperCitations.create("Wiki2", "Papier6", 2) );
		
		//Wiki-Beispiel SOLL h-Index=4
		dummy.add( PaperCitations.create("Wiki3", "Papier3", 65) );
		dummy.add( PaperCitations.create("Wiki3", "Papier7", 58) );
		dummy.add( PaperCitations.create("Wiki3", "Papier2", 55) );
		dummy.add( PaperCitations.create("Wiki3", "Papier1", 17) );
		dummy.add( PaperCitations.create("Wiki3", "Papier10", 2) );
		dummy.add( PaperCitations.create("Wiki3", "Papier9", 2) );
		dummy.add( PaperCitations.create("Wiki3", "Papier8", 1) );
		dummy.add( PaperCitations.create("Wiki3", "Papier5", 1) );
		dummy.add( PaperCitations.create("Wiki3", "Papier4", 0) );
		dummy.add( PaperCitations.create("Wiki3", "Papier6", 0) );
		
		//Wiki-Beispiel SOLL h-Index=0
		dummy.add( PaperCitations.create("Selbst", "Papier3", 0) );
		dummy.add( PaperCitations.create("Selbst", "Papier7", 0) );
		dummy.add( PaperCitations.create("Selbst", "Papier2", 0) );
		dummy.add( PaperCitations.create("Selbst", "Papier1", 0) );
		dummy.add( PaperCitations.create("Selbst", "Papier10", 0) );
		dummy.add( PaperCitations.create("Selbst", "Papier9", 0) );
		dummy.add( PaperCitations.create("Selbst", "Papier8", 0) );
		dummy.add( PaperCitations.create("Selbst", "Papier5", 0) );
		dummy.add( PaperCitations.create("Selbst", "Papier4", 0) );
		dummy.add( PaperCitations.create("Selbst", "Papier6", 0) );
		
		return dummy;
	}
}
