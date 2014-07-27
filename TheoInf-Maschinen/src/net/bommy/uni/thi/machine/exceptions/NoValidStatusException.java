package net.bommy.uni.thi.machine.exceptions;

import net.bommy.uni.dua.Set;

public class NoValidStatusException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;
	private Set<String> statusSet;
	private String trigger;

	public NoValidStatusException(String status, Set<String> statusSet,
			String trigger) {
		this.status = status;
		this.statusSet = statusSet;
		this.trigger = trigger;
	}

	public String getStatus() {
		return status;
	}

	public Set<String> getStatusSet() {
		return statusSet;
	}

	public String getTrigger() {
		return trigger;
	}

}
