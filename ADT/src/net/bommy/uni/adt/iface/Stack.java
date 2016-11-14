package net.bommy.uni.adt.iface;

/**
 * @author Thomas Borck
 *
 * @param <TYPE>
 */
public interface Stack<TYPE> {

	/**
	 * Legt ein neues Element auf den Stack.
	 * @param element neues Element
	 */
	public void push(TYPE element);
	
	/**
	 * Nimmt das obere Element vom Stack.
	 * @return entferntes Element
	 */
	public TYPE pop();
}
