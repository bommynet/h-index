package net.bommy.uni.thi.machine;

import java.util.ArrayList;

import net.bommy.uni.dua.Set;
import net.bommy.uni.thi.machine.exceptions.NoValidCharException;
import net.bommy.uni.thi.machine.exceptions.NoValidStatusException;
import net.bommy.uni.thi.machine.exceptions.NoValidTransitionException;

/**
 * Represents an deterministic finite automaton (DFA).
 * @author Thomas Laarmann
 * @version 0.0.8 - 20140727
 * @see <a href="http://en.wikipedia.org/wiki/Deterministic_finite_automaton">Wikipedia: deterministic finite automaton</a>
 */
public class DFA {
//### VARIABLES
//#####################################
	//Vars @ Initialization
	/** start status */
	private String start;
	
	/** set of acceptable end status */
	private Set<String> end;
	
	/** set of possible status */
	private Set<String> status;
	
	/** set of possible characters */
	private Set<Character> sigma;
	
	/** list of all possible transitions for current DFA */
	private ArrayList<DeltaTuple> transition;
	
	//Vars @ Setup
	/** current status while DFA is running */
	private String curStatus;
	
	/** current character which will be read now */
	private char curChar;
	
	/** position in word of current character */
	private int curPosition;
	
	/** the word that DFA will test */
	private String curWord;
	
	//Vars @ Flags for ready or not
	/** is initialization done */
	private boolean rdyInit = false;
	
	/** is setup done */
	private boolean rdySetup = false;



//### CONSTRUCTORS
//#####################################
	/**
	 * Creates an new DFA (deterministic finite automaton) with given data.
	 * @param status a finite set of states
	 * @param alphabet a finite set of input symbols
	 * @param start the start state
	 * @param end a set of accept states
	 */
	public DFA(Set<String> status, Set<Character> sigma, String start, Set<String> end) {
		//initialize variables
		this.status = status;
		this.sigma = sigma;
		this.transition = new ArrayList<DeltaTuple>();
		this.start = start;
		this.end = end;
		//set init flag
		this.rdyInit = true;
	}

	/**
	 * Creates an new DFA (deterministic finite automaton) with given data.
	 * @param status a finite set of states
	 * @param alphabet a finite set of input symbols
	 * @param start the start state
	 * @param end the accept state
	 */
	public DFA(Set<String> status, Set<Character> sigma, String start, String end) {
		this(status, sigma, start,
			 new Set<String>( new String[]{end} ));
	}

	/**
	 * Creates an new DFA (deterministic finite automaton) with given data.
	 * @param status a finite set of states
	 * @param alphabet a finite set of input symbols
	 * @param start the start state
	 * @param end the accept state
	 */
	public DFA(String[] status, Character[] sigma, String start, String[] end) {
		this(new Set<String>(status),
			 new Set<Character>(sigma),
			 start,
			 new Set<String>( end ));
	}
	

//### FUNCTIONS
//#####################################
	/**
	 * Setup all needed data and initializes DFA with given word.
	 * @param word word to test
	 */
	public void setup(String word) {
		//just copy the word to do work
		this.curWord = word;
		//if word is empty, set current char to 0, else to first char
		//in given word
		this.curChar = (word.length()==0 ? '\0' : word.charAt(0));
		//if word is empty, set current position to -1, else to 0
		this.curPosition = (word.length()==0 ? -1 : 0);
		//reset current status to the first one
		this.curStatus = this.start;
		//finally all data is setup
		this.rdySetup = true;
	}
	
	/**
	 * Adds a transition to DFA.
	 * @param curStatus current status
	 * @param curChar char that will be read
	 * @param nextStatus following status
	 * @throws NoValidStatusException is thrown if curStatus/nextStatus are no valid status
	 * @throws NoValidCharException  is thrown if curChar is not in sigma
	 * @throws NoValidTransitionException is thrown if an transition from (curStatus,curChar)->xxx
	 *                                    already exists
	 */
	public void addDelta(String curStatus, char curChar, String nextStatus)
			throws NoValidStatusException, NoValidCharException, NoValidTransitionException {
		//check if transition would be valid
		if(!isInStatus(curStatus)) 
			throw new NoValidStatusException(curStatus, status, "addDelta::curStatus");
		if(!isInStatus(nextStatus)) 
			throw new NoValidStatusException(nextStatus, status, "addDelta::nextStatus");
		if(!isInSigma(curChar)) 
			throw new NoValidCharException(curChar, sigma, "addDelta::curChar");
		
		//check if transition (curStatus,curChar)->anything already exists
		for(DeltaTuple d : this.transition) {
			String tmp = d.getNextStatus(curStatus, curChar);
			if(tmp != null)
				throw new NoValidTransitionException(curStatus, curChar, nextStatus, tmp);
		}
		
		//create transition
		DeltaTuple dt = new DeltaTuple(curStatus, curChar, nextStatus);
		this.transition.add(dt);
	}
	
	/**
	 * Checks if the word is currently acceptable. Therefore init & setup has to be done.
	 * <br/>
	 * <table>
	 *   <tr><td>hasStep()</td><td>wordAccepted()</td><td>word in DFA</td></tr>
	 *   <tr><td>false    </td><td>false         </td><td>false      </td></tr>
	 *   <tr><td>false    </td><td>true          </td><td>true       </td></tr>
	 *   <tr><td>true     </td><td>false         </td><td>-          </td></tr>
	 *   <tr><td>true     </td><td>true          </td><td>-          </td></tr>
	 * </table>
	 * <br/>
	 * @return true if word is accepted by DFA
	 */
	public boolean wordAccepted() {
		//DFA not ready
		//if(!this.isReadyToRun()) return false;
		//if empty word is allowed
		if(this.isEndStatus(curStatus) && curPosition == -1) return true;
		//if current status is in set end then it would be
		//an acceptable word
		if(this.isEndStatus(curStatus) && curPosition == curWord.length()-1) return true;
		//otherwise it's not accepted
		return false;
	}
	
	/**
	 * Checks if there are more steps available for the DFA with
	 * given word. Therefore init & setup has to be done.
	 * <br/>
	 * <table>
	 *   <tr><td>hasStep()</td><td>wordAccepted()</td><td>word in DFA</td></tr>
	 *   <tr><td>false    </td><td>false         </td><td>false      </td></tr>
	 *   <tr><td>false    </td><td>true          </td><td>true       </td></tr>
	 *   <tr><td>true     </td><td>false         </td><td>-          </td></tr>
	 *   <tr><td>true     </td><td>true          </td><td>-          </td></tr>
	 * </table>
	 * <br/>
	 * @return true if there're more steps
	 */
	public boolean hasStep() {
		//DFA not ready
		if(!this.isReadyToRun()) return false;
		//end of word reached
		if(this.curPosition == -1 || this.curPosition >= this.curWord.length()) return false;
		//there are possible steps
		return true;
	}
	
	/**
	 * Computes next step for given word and sets new values
	 * to status, character and position.
	 */
	public void nextStep() {
		//DFA not ready
		if(!this.isReadyToRun()) return;
		//set current char to read
		curChar = curWord.charAt( curPosition );
		//search for possible transition
		for(DeltaTuple dt : this.transition) {
			String tmpStatus = dt.getNextStatus(curStatus, curChar);
			if(tmpStatus != null) {
				//set next status
				this.curStatus = tmpStatus;
				//set next position
				this.curPosition += (curPosition==curWord.length()-1 ? 0 : 1);
				//nothing else to do
				return;
			}
		}
		//if this point is reached, there wasn't a valid transition, so
		//the word can't be accepted
		this.rdySetup = false;
	}
	

//### FUNCTIONS: getter and setter
//#####################################
	/**
	 * @return
	 */
	public char getCurCharacter() {
		return this.curChar;
	}
	
	/**
	 * @return
	 */
	public int getCurPosition() {
		return this.curPosition;
	}
	
	/**
	 * @return
	 */
	public String getCurStatus() {
		return this.curStatus;
	}
	
	/**
	 * @return
	 */
	public String getCurWord() {
		return this.curWord;
	}
	

//### FUNCTIONS: true/false
//#####################################
	/**
	 * DFA should only be ready if initialization and setup were done.
	 * @return true if DFA is ready to run
	 */
	public boolean isReadyToRun() {
		return (this.rdyInit && this.rdySetup);
	}
	
	/**
	 * Tests if given character is in alphabet sigma.
	 * @param check character to test
	 * @return true if it is in sigma
	 */
	private boolean isInSigma(char check) {
		return this.sigma.isInSet(check);
	}
	
	/**
	 * Tests if given status is in set of possible status.
	 * @param check status to test
	 * @return true if it is a possible status
	 */
	private boolean isInStatus(String check) {
		return this.status.isInSet(check);
	}
	
	/**
	 * Tests if given status belongs to set of end status.
	 * @param check status to test
	 * @return true if it is in set of end status
	 */
	private boolean isEndStatus(String check) {
		return this.end.isInSet(check);
	}
	

//### FUNCTIONS: Overrides
//#####################################
	@Override
	public String toString() {
		String tmp = "";
		tmp += "Word: \"" + curWord + "\"";
		tmp += " @ [" + curStatus + "]->";
		tmp += "(" + curChar + "," + curPosition + ")";
		tmp += " ==> " + (wordAccepted() ? "" : "not ") + "accepted";
		return tmp;
	}
}






/**
 * Subclass for better handling of transitions.
 * @author Thomas Laarmann
 * @version 0.0.3 - 20140727
 */
class DeltaTuple {
	/** this status is active */
	private String activeStatus;
	
	/** and this char will be read */
	private char charRead;
	
	/** then thats the target status */
	private String nextStatus;
	
	
	/**
	 * Creates an new DeltaTuple with given informations.
	 * @param activeStatus this status is active
	 * @param charRead this char will be read
	 * @param nextStatus this will be the next status
	 */
	public DeltaTuple(String activeStatus, char charRead, String nextStatus) {
		this.activeStatus =  activeStatus;
		this.charRead =  charRead;
		this.nextStatus = nextStatus;
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
	 * @return
	 */
	public String getNextStatus(char charRead) {
		if( charRead == this.charRead )
			return nextStatus;
		else
			return null;
	}
	
	/**
	 * Follow the transition.
	 * @param curStatus
	 * @param charRead
	 * @return
	 */
	public String getNextStatus(String curStatus, char charRead) {
		if( curStatus == this.activeStatus && charRead == this.charRead )
			return nextStatus;
		else
			return null;
	}
}