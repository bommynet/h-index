package net.bommy.uni.swq.hindex.dummy;

/**
 * Kurz erstellt zur Funktionspr&uuml;fung...
 * @author Thomas Laarmann
 */
public class PaperCitations {
	public String author;
	public String title;
	public int citations;
	
	public PaperCitations() {
		author = "";
		title = "";
		citations = 0;
	}
	
	public static PaperCitations create(String author, String title, int citations) {
		PaperCitations p = new PaperCitations();
		p.author = author;
		p.title = title;
		p.citations = citations;
		return p;
	}
}
