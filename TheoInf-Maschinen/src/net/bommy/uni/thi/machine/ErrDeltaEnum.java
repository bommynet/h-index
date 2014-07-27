package net.bommy.uni.thi.machine;

/**
 * @author Thomas Laarmann
 * @version 0.0.1
 */
public enum ErrDeltaEnum {
	/** no errors occur */
	NO_ERROR,
	/** activeStatus in addDelta(...) is invalid */
	NOT_A_STATUS_ACTIVESTATUS,
	/** nextStatus in addDelta(...) is invalid */
	NOT_A_STATUS_NEXTSTATUS,
	/** charRead in addDelta(...) is not in alphabet */
	NOT_IN_ALPHABET, 
	/** charStack in addDelta(...) is not in alphabet */
	NOT_IN_ALPHABET_STACK,
	/** no duplicates are allowed */
	TRANSITION_ALREADY_EXISTS,
	/** it's deterministic and no multiply targets are allowed */
	TRANSITION_HAS_OTHER_TARGET
}
