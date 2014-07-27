package net.bommy.uni.thi.machine.exceptions;

public class NoValidTransitionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;
	private char character;
	private String nextStatus;
	private String existNextStatus;

	public NoValidTransitionException(String status, char character,
			String nextStatus, String existNextStatus) {
		this.status = status;
		this.character = character;
		this.nextStatus = nextStatus;
		this.existNextStatus = existNextStatus;
	}

	public String getStatus() {
		return status;
	}

	public char getCharacter() {
		return character;
	}

	public String getNextStatus() {
		return nextStatus;
	}

	public String getExistNextStatus() {
		return existNextStatus;
	}

}
