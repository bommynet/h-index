package net.bommy.uni.thi.machine;

import java.util.ArrayList;

import net.bommy.uni.dua.Set;

/**
 * Represents an pushdown automaton (PDA).
 * @author Thomas Laarmann
 * @version 0.0.1
 * @see <a href="http://en.wikipedia.org/wiki/Pushdown_automaton">Wikipedia: pushdown automaton</a>
 */
public class PDA {
//### VARIABLES
//#####################################
	private Set<String> status;
	private Set<Character> sigma;
	private Set<Character> gamma;
	private ArrayList<DeltaTupleStack> transition;
	private String start;
	private String[] end;
	private char stackInit;
	
	private String activeStatus;
	private PDAStack stack;
	

//### CONSTRUCTORS
//#####################################
	/**
	 * @param status
	 * @param sigma
	 * @param gamma
	 * @param start
	 * @param stackInit
	 * @param end
	 */
	public PDA(Set<String> status, Set<Character> sigma, Set<Character> gamma,
			String start, char stackInit, String[] end) {
		this.status = status;
		this.sigma = sigma;
		this.gamma = gamma;
		this.transition = new ArrayList<DeltaTupleStack>();
		this.start = start;
		this.stackInit = stackInit;
		this.end = end;
		this.stack = new PDAStack();
	}

	/**
	 * @param status
	 * @param sigma
	 * @param gamma
	 * @param start
	 * @param stackInit
	 * @param end
	 */
	public PDA(Set<String> status, Set<Character> sigma, Set<Character> gamma,
			String start, char stackInit, String end) {
		this(status, sigma, gamma, start, stackInit, new String[]{ end });
	}

	/**
	 * @param status
	 * @param sigma
	 * @param gamma
	 * @param start
	 * @param stackInit
	 * @param end
	 */
	public PDA(String[] status, Character[] sigma, Character[] gamma,
			String start, char stackInit, String end) {
		this(new Set<String>(status), new Set<Character>(sigma),
				new Set<Character>(gamma), start, stackInit, new String[]{ end });
	}
	

//### FUNCTIONS
//#####################################
	/**
	 * Runs this PDA with given input and tests if input is an word
	 * for this PDA or not.
	 * @param input 
	 * @return
	 */
	public MachineReturnType runMachine(String input) {
		//setup PDA
		this.activeStatus = this.start;
		this.stack.clear();
		this.stack.push(stackInit);
		
		//test for empty word
		if(input.length() <= 0) {
			for(String s : end) {
				if(s == activeStatus) {
					return new MachineReturnType(input,true); //empty word accepted
				}
			}
			return new MachineReturnType(input,false,0); //empty word *not* accepted;
		}
		
		//run transitions on input
		for(int i=0; i<input.length(); i++) {
			String next = performDelta(activeStatus, input.charAt(i));
			if(next != null) {
				activeStatus = next;
				if(i == input.length()-1) {
					for(String s : end) {
						if(s == activeStatus) 
							return new MachineReturnType(input,true); //word accepted;
						else 
							return new MachineReturnType(input,false); //word *not* accepted;
					}
				}
			}
			else {
				return new MachineReturnType(input,false,i); //no transition found;
			}
		}
		return new MachineReturnType(input,true,-8); //won't be reached
	}
	
	
	/**
	 * Adds a new transition to Delta with given Data.
	 * @param activeStatus this status is active
	 * @param charRead this char will be read
	 * @param charStack this char will be on top of the stack
	 * @param nextStatus this will be the next status
	 * @param nextStack this will be on top of the stack
	 * @param nextStack2 this will be on top-1 of the stack
	 */
	public ErrDeltaEnum addDelta(String activeStatus, char charRead, char charStack, 
			String nextStatus, char nextStack, char nextStack2) {
		//check if input is acceptable
		if( !isStatus(activeStatus) ) 
			return ErrDeltaEnum.NOT_A_STATUS_ACTIVESTATUS;
		if( !isStatus(nextStatus) )
			return ErrDeltaEnum.NOT_A_STATUS_NEXTSTATUS;
		if( !isInSigma(charRead) )
			return ErrDeltaEnum.NOT_IN_ALPHABET;
		if( !isInGamma(charStack) )
			return ErrDeltaEnum.NOT_IN_ALPHABET_STACK;
		if( !isInGamma(nextStack) )
			return ErrDeltaEnum.NOT_IN_ALPHABET_STACK;
		if( !isInGamma(nextStack2) )
			return ErrDeltaEnum.NOT_IN_ALPHABET_STACK;

		//check if tuple not already exists
		for( DeltaTupleStack dt : transition ) {
			if(dt.getStatus() == activeStatus && dt.getNextStatus(charRead,charStack) == nextStatus &&
					dt.getNextStack(charRead,charStack) == new char[]{nextStack,nextStack2})
				return ErrDeltaEnum.TRANSITION_ALREADY_EXISTS;
			else if(dt.getStatus() == activeStatus && dt.getNextStatus(charRead,charStack) != null &&
					dt.getNextStack(charRead,charStack) != new char[]{0,0})
				return ErrDeltaEnum.TRANSITION_HAS_OTHER_TARGET;
		}
		
		//add new tuple
		this.transition.add( new DeltaTupleStack(activeStatus, charRead, charStack, nextStatus, nextStack, nextStack2) );
		return ErrDeltaEnum.NO_ERROR;
	}
	
	/**
	 * Removes an transition from Delta with given data and returns the removed
	 * tuple. If transition doesn't exists, it will return null. 
	 * @param activeStatus this status is active
	 * @param charRead this char will be read
	 * @param charRead this char will be on top of the stack
	 * @param nextStatus this was the next status
	 * @param nextStack this was top of stack
	 * @param nextStack2 this was top-1 of stack
	 * @return removed DeltaTuple or null
	 */
	public DeltaTupleStack removeDelta(String activeStatus, char charRead, char charStack, 
			String nextStatus, char nextStack, char nextStack2) {
		//check if tuple exists
		for( DeltaTupleStack dt : transition ) {
			if(dt.getStatus() == activeStatus && dt.getNextStatus(charRead,charStack) == nextStatus &&
					dt.getNextStack(charRead,charStack) == new char[]{nextStack,nextStack2}) {
				this.transition.remove( dt );
				return dt;
			}
		}
		//no tuple was removed, so there's nothing to return
		return null;
	}
	
	/**
	 * Follows the transition from status to next status based on character read. If there
	 * wasn't a transition for that tuple, it will return null.
	 * @param activeStatusthis status is active
	 * @param charRead this char will be read
	 * @return next status or null
	 */
	private String performDelta(String activeStatus, char charRead) {
		//get stack char
		if(stack.empty()) return null;
		char charStack = stack.pop();
		
		//searching tuple
		for( DeltaTupleStack dt : transition ) {
			//System.out.println("("+activeStatus+" "+charRead+" "+charStack+") => "+
			//		dt.getNextStatus(charRead,charStack));
			if(dt.getStatus() == activeStatus && dt.getNextStatus(charRead,charStack) != null) {
				char[] nstack = dt.getNextStack(charRead, charStack);
				if(nstack[1] != 0) stack.push( nstack[1] );
				if(nstack[0] != 0) stack.push( nstack[0] );
				return dt.getNextStatus(charRead,charStack);
			}
		}
		
		//transition not found
		//stack.push(charStack);
		return null;
	}
	
	/**
	 * Checks if the given string is an status in DEA.
	 * @param check status to check
	 * @return true if check is an status in DEA, false either
	 */
	private boolean isStatus(String check) {
		return status.isInSet(check);
	}
	
	/**
	 * Checks if the given char is in alphabet.
	 * @param check char to check
	 * @return true if check is an char in alphabet, false either
	 */
	private boolean isInSigma(char check) {
		return sigma.isInSet(check);
	}
	
	/**
	 * Checks if the given char is in alphabet.
	 * @param check char to check
	 * @return true if check is an char in alphabet, false either
	 */
	private boolean isInGamma(char check) {
		if( check == '\0' ) return true;
		return gamma.isInSet(check);
	}
}



/**
 * Subclass for better handling of transitions.
 * @author Thomas Laarmann
 * @version 0.0.2
 */
class DeltaTupleStack {
	/** this status is active */
	private String activeStatus;
	
	/** this char will be read */
	private char charRead;
	
	/** this char is on top of the stack */
	private char charStack;
	
	/** then thats the target status */
	private String nextStatus;
	
	/** then thats the top stack char  */
	private char nextStack;
	
	/** then thats the top-1 stack char  */
	private char nextStack2;
	
	
	/**
	 * Creates an new DeltaTuple with given informations.
	 * @param activeStatus this status is active
	 * @param charRead this char will be read
	 * @param charStack this char is on top of the stack
	 * @param nextStatus this will be the next status
	 * @param nextStack this will be on top of the stack next
	 * @param nextStack2 this will be on top-1 of the stack next
	 */
	public DeltaTupleStack(String activeStatus, char charRead, char charStack,
			String nextStatus, char nextStack, char nextStack2) {
		this.activeStatus =  activeStatus;
		this.charRead =  charRead;
		this.charStack =  charStack;
		this.nextStatus = nextStatus;
		this.nextStack = nextStack;
		this.nextStack2 = nextStack2;
	}
	
	/**
	 * Returns the (active) Status, what will be needed to follow the
	 * transition.
	 * @return
	 */
	public String getStatus() {
		return activeStatus;
	}
	
	/**
	 * Follow the transition.
	 * @param charRead
	 * @param charStack
	 * @return
	 */
	public String getNextStatus(char charRead, char charStack) {
		if( charRead == this.charRead && charStack == this.charStack )
			return nextStatus;
		else
			return null;
	}
	
	/**
	 * Follow the transition.
	 * @param charRead
	 * @param charStack
	 * @return
	 */
	public char[] getNextStack(char charRead, char charStack) {
		if( charRead == this.charRead && charStack == this.charStack )
			return new char[] { nextStack, nextStack2 };
		else
			return new char[] { 0, 0 };
	}
}



/**
 * A simple stack as needed.
 * @author Thomas Laarmann
 * @version 0.0.1
 */
class PDAStack {
	
	private ArrayList<Character> stack;
	
	/**
	 * 
	 */
	public PDAStack() {
		stack = new ArrayList<Character>();
	}
	
	/**
	 * Adds a new char on top of the stack.
	 * @param c new char
	 */
	public void push(char c) {
		stack.add(c);
	}
	
	/**
	 * Removes the char at top of the stack and returns it.
	 * @return char on top of the stack
	 */
	public char pop() {
		if(empty()) return (Character) null;
		return stack.remove(stack.size()-1);
	}
	
	/**
	 * Just says if the stack is empty or not.
	 * @return
	 */
	public boolean empty() {
		return stack.isEmpty();
	}
	
	/**
	 * Reset all data in stack.
	 * @return
	 */
	public void clear() {
		stack.clear();
	}
}