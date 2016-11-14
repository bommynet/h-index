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
	 * F�gt ein Element am Anfang der Liste ein.
	 * @param element einzuf�gendes Element
	 */
	public void prepend(TYPE element);
	
	/**
	 * H�ngt ein Element ans Ende der Liste an.
	 * @param element anzuh�ngendes Element
	 */
	public void append(TYPE element);
	
	/**
	 * Gibt des erste Element der Liste zur�ck.
	 * @return
	 */
	public TYPE head();
	
	/**
	 * Gibt die Liste ohne das erste Element zur�ck.
	 * @return
	 */
	public List<TYPE> tail();
}
