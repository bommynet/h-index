package net.bommy.uni.thi.machine.exceptions;

import net.bommy.uni.dua.Set;

public class NoValidCharException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private char character;
	private Set<Character> characterSet;
	private String trigger;

	public NoValidCharException(char character, Set<Character> characterSet,
			String trigger) {
		this.character = character;
		this.characterSet = characterSet;
		this.trigger = trigger;
	}

	public char getCharacter() {
		return character;
	}

	public Set<Character> getCharSet() {
		return characterSet;
	}

	public String getTrigger() {
		return trigger;
	}
}
