package net.bommy.uni.thi.machine;

/**
 * Subclass as return type for DEA.
 * @author Thomas Laarmann
 * @version 0.0.2
 */
public class MachineReturnType {
	/** input string that was tested */
	private String input;
	
	/** was string accepted or not */
	private boolean isAccepted;
	
	/** if not accepted, where's the difference; else it's -1 */
	private int positionOfDiff;
	
	/**
	 * Creates data set for runMachine.
	 * @param input word that was tested
	 * @param isAccepted was it accepted?
	 * @param positionOfDiff if not, at which position is the difference, else it's -1
	 */
	public MachineReturnType(String input, boolean isAccepted, int positionOfDiff) {
		this.input = input;
		this.isAccepted = isAccepted;
		this.positionOfDiff = positionOfDiff;
	}
	
	/**
	 * Creates data set for runMachine, with difference set to -1 by default.
	 * @param input word that was tested
	 * @param isAccepted was it accepted?
	 */
	public MachineReturnType(String input, boolean isAccepted) {
		this(input, isAccepted, -1);
	}

	/**
	 * Getter for the word that was tested.
	 * @return
	 */
	public String getInput() {
		return input;
	}

	/**
	 * Getter for flag if word was accepted.
	 * @return
	 */
	public boolean isAccepted() {
		return isAccepted;
	}

	/**
	 * Getter for position of difference.
	 * @return
	 */
	public int getPositionOfDiff() {
		return positionOfDiff;
	}
	
	@Override
	public String toString() {
		return (isAccepted() ? "accepted" : "rejected");
	}
}