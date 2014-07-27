package net.bommy.uni.dua;

import java.lang.reflect.Array;
import java.util.ArrayList;

import net.bommy.uni.dua.adt.ADTSet;

public class Set<ELEMENTTYPE> implements ADTSet<ELEMENTTYPE> {
	
	private ArrayList<ELEMENTTYPE> elements;
	
	public Set() {
		this.elements = new ArrayList<ELEMENTTYPE>();
	}
	
	public Set(ELEMENTTYPE[] elements) {
		this();
		this.union(elements);
	}

	@Override
	public void union(ADTSet<ELEMENTTYPE> otherSet) {
		this.union(otherSet.toArray());
	}

	@Override
	public void union(ELEMENTTYPE[] arraySet) {
		//if otherSet is empty, there's nothing to do
		if(arraySet == null) return;
		//insert all elements to this set
		for(ELEMENTTYPE element : arraySet) {
			this.insert(element);
		}
	}

	@Override
	public void insert(ELEMENTTYPE element) {
		//if element already exists in set, do nothing
		if( this.isInSet(element) ) {
			System.out.println("Duplicate found ["+ this.getClass().getName() + ".insert(" +
					element.getClass().getSimpleName() +")]: \""+ element +"\"" );
			return;
		}
		//element not in set, so add it
		elements.add(element);
	}

	@Override
	public void remove(ELEMENTTYPE element) {
		//if element isn't in set there's nothing to do
		if( !this.isInSet(element) ) return;
		//element is in array, so find and remove it
		ArrayList<ELEMENTTYPE> tmp = new ArrayList<ELEMENTTYPE>();
		for(ELEMENTTYPE e : elements) {
			if( !e.equals(element) ) {
				tmp.add(e); //if e != element, then add it to new array
			}
		}
		this.elements = tmp;
	}

	@Override
	public boolean isInSet(ELEMENTTYPE element) {
		//find element in elements
		for(ELEMENTTYPE e : elements) {
			if( e.equals(element) ) return true;
		}
		//element isn't in set
		return false;
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public ELEMENTTYPE[] toArray() {
		if( this.size() == 0 ) return null;
		//just convert ArrayList to correct array with type ELEMENTTYPE
		//elements is correct type, so i can uncheck these warnings
		@SuppressWarnings("unchecked")
		Class<ELEMENTTYPE> classET = (Class<ELEMENTTYPE>) elements.get(0).getClass();
		@SuppressWarnings("unchecked")
		ELEMENTTYPE[] array = (ELEMENTTYPE[]) Array.newInstance(classET, elements.size());
		//create new array to return
	    return elements.toArray(array);
	}

}
