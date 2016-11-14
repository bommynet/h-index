package net.bommy.uni.adt.iface;

/**
 * @author Thomas Borcl
 * @version 1.0 - 161110
 * @param <TYPE> Datentyp der Listenelemente
 */
public interface List<TYPE> {
	/**
	 * Gibt an, ob die Liste aktuell leer ist.
	 * @return true / false
	 */
	public boolean empty();
	
	/**
	 * Fügt ein Element am Anfang der Liste ein.
	 * @param element einzufügendes Element
	 */
	public void prepend(TYPE element);
	
	/**
	 * Hängt ein Element ans Ende der Liste an.
	 * @param element anzuhängendes Element
	 */
	public void append(TYPE element);
	
	/**
	 * Gibt des erste Element der Liste zurück.
	 * @return
	 */
	public TYPE head();
	
	/**
	 * Gibt die Liste ohne das erste Element zurück.
	 * @return
	 */
	public List<TYPE> tail();
}
