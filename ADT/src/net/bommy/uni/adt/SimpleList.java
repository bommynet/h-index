package net.bommy.uni.adt;

import net.bommy.uni.adt.iface.List;

/**
 * @author Thomas Borck
 *
 * @param <TYPE>
 */
public class SimpleList<TYPE> implements List<TYPE> {
	
	/**
	 * 
	 */
	private SimpleListElement<TYPE> _firstElement;
	
	/**
	 * 
	 */
	public SimpleList() {
		_firstElement = null;
	}

	/* (non-Javadoc)
	 * @see net.bommy.uni.adt.List#empty()
	 */
	@Override
	public boolean empty() {
		if(_firstElement == null) return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see net.bommy.uni.adt.List#prepend(java.lang.Object)
	 */
	@Override
	public void prepend(TYPE element) {
		// 1. speichere Referenz zum ersten Element
		// 2. setze neues Element als firstElement
		// 3. setze gespeicherte Referenz als next zum firstElement
		SimpleListElement<TYPE> oldFirstElement = _firstElement;
		_firstElement = new SimpleListElement<TYPE>(element);
		_firstElement.next = oldFirstElement;
	}

	/* (non-Javadoc)
	 * @see net.bommy.uni.adt.List#append(java.lang.Object)
	 */
	@Override
	public void append(TYPE element) {
		// 1. suche das letzte Listenelement (next == null)
		// 2. setze next des Elements auf element
		if(_firstElement == null)
			_firstElement = new SimpleListElement<TYPE>(element);
		else {
			SimpleListElement<TYPE> curr = _firstElement;
			while(curr.next != null) {
				curr = curr.next;
			}
			curr.next = new SimpleListElement<TYPE>(element);
		}
	}

	/* (non-Javadoc)
	 * @see net.bommy.uni.adt.List#head()
	 */
	@Override
	public TYPE head() {
		return _firstElement.element;
	}

	/* (non-Javadoc)
	 * @see net.bommy.uni.adt.List#tail()
	 */
	@Override
	public List<TYPE> tail() {
		// 1. erstelle eine neue, leere Liste
		// 2. wenn das erste Element einen Nachfolger hat, wähle diesen
		// 3. füge jeden Nachfolger in die neue Liste ein
		
		SimpleList<TYPE> newList = new SimpleList<TYPE>();
		
		if(_firstElement != null && _firstElement.next != null) {
			SimpleListElement<TYPE> curr = _firstElement.next;
			while(curr != null) {
				newList.append(curr.element);
				curr = curr.next;
			}
		}
		
		// übergib die neue Liste
		return newList;
	}

}

/**
 * @author Thomas Borck
 *
 * @param <TYPE>
 */
class SimpleListElement<TYPE> {
	/** das zu speichernde Element */
	public TYPE element;
	
	/** Referenz zum Nachfolger */
	public SimpleListElement<TYPE> next;
	
	/**
	 * Erstellt ein neues Element mit einem bestimmten Nachfolger.
	 * @param element neues Element
	 * @param next Nachfolger
	 */
	public SimpleListElement(TYPE element, SimpleListElement<TYPE> next) {
		this.element = element;
		this.next = next;
	}
	
	/**
	 * Erstellt ein neues Element ohne Nachfolger.
	 * @param element neues Element
	 */
	public SimpleListElement(TYPE element) {
		this(element, null);
	}
}