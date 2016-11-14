package net.bommy.uni.adt;

import net.bommy.uni.adt.iface.List;
import net.bommy.uni.adt.iface.Stack;

public class SimpleStack<TYPE> implements Stack<TYPE> {
	
	/** Speciherbereich für den Stack */
	private List<TYPE> _stack;
	
	/**
	 * Erstellt einen neuen, leeren Stack.
	 */
	public SimpleStack() {
		_stack = new SimpleList<TYPE>();
	}

	/* (non-Javadoc)
	 * @see net.bommy.uni.adt.Stack#push(java.lang.Object)
	 */
	@Override
	public void push(TYPE element) {
		_stack.prepend(element);
	}

	/* (non-Javadoc)
	 * @see net.bommy.uni.adt.Stack#pop()
	 */
	@Override
	public TYPE pop() {
		// wo keine Elemente sind, kann nichts weggenommen werden
		if(_stack == null) return null;
		
		// erstes Element der Liste entfernen
		TYPE topElement = _stack.head();
		_stack = _stack.tail();
		
		// ...und zurück geben
		return topElement;
	}

}
