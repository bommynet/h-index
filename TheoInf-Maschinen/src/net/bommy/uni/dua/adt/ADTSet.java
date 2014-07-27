package net.bommy.uni.dua.adt;

public interface ADTSet<T> {
	
	/**
	 * Adds the other set to this one. Duplicates will
	 * be ignored.
	 * @param otherSet other set of same type
	 */
	public void union(ADTSet<T> otherSet);
	
	/**
	 * Adds the array to this set. Duplicates will
	 * be ignored.
	 * @param arraySet array of element type
	 */
	public void union(T[] arraySet);
	
	/**
	 * Inserts an element to the set. Duplicates will
	 * be ignored.
	 * @param element element to add
	 */
	public void insert(T element);
	
	/**
	 * Removes an element from set, if it exists.
	 * @param element element to remove
	 */
	public void remove(T element);
	
	/**
	 * Checks if an specific element is in set.
	 * @param element element to check
	 * @return true if it is in set, false either
	 */
	public boolean isInSet(T element);
	
	/**
	 * The size of this set.
	 * @return size as number [0..inf]
	 */
	public int size();
	
	/**
	 * Get this set as array.
	 * @return set as array
	 */
	public T[] toArray();
	
}
