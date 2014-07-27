import net.bommy.uni.thi.machine.DFA;
import net.bommy.uni.thi.machine.PDA;


public class Main {

	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		String[] status = {"Z0","Z1","Z2"};
		Character[] chars = {'a','b','c'};
		Character[] chars2 = {'+','X','#'};
		
		
		DFA dea = new DFA(status, chars, "Z0", new String[]{ "Z2" });
		//DFA dea = new DFA(status, chars, "Z0", new String[]{"Z0","Z2"});
		try {
			dea.addDelta("Z0", 'a', "Z1");
			dea.addDelta("Z1", 'a', "Z1");
			dea.addDelta("Z2", 'a', "Z1");
			dea.addDelta("Z1", 'b', "Z2");
			dea.addDelta("Z2", 'c', "Z0");
			//dea.addDelta("Z2", 'a', "Z1"); //error: already exists
			//dea.addDelta("Z2", 'c', "Z1"); //error: other target
			//dea.addDelta("Z2", 'd', "Z1"); //error: unexpected char
			//dea.addDelta("Z3", 'c', "Z1"); //error: unexpected status
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		String[] input = new String[]{"aaababcab","aaabbacab","","aaa1"};
		
		//int i = 0;
		for(int i=0; i<input.length; i++) {
			dea.setup(input[i]);

			while( dea.hasStep() ) {
				dea.nextStep();
			}
			System.out.println( dea );
			System.out.println();
		}
		
		
		
		/*PDA pda = new PDA(status, chars, chars2, "Z0", '#', "Z2");
		pda.addDelta("Z0", 'a', '#', "Z0", '\0', 'X');
		pda.addDelta("Z0", 'a', 'X', "Z0", '+', 'X');
		pda.addDelta("Z0", 'a', '+', "Z0", '+', '+');
		pda.addDelta("Z0", 'b', '+', "Z1", '\0', '\0');
		pda.addDelta("Z0", 'b', 'X', "Z2", '\0', '\0');
		pda.addDelta("Z1", 'b', '+', "Z1", '\0', '\0');
		pda.addDelta("Z1", 'b', 'X', "Z2", '\0', '\0');
		ret = pda.runMachine("aaaabbbb");
		System.out.println( "PDA Test A: ('"+ret.getInput()+"',"+ret.isAccepted()+","+ret.getPositionOfDiff()+")" );
		ret = pda.runMachine("aaaabbbbbb");
		System.out.println( "PDA Test B: ('"+ret.getInput()+"',"+ret.isAccepted()+","+ret.getPositionOfDiff()+")" );
		ret = pda.runMachine("aaaabbbcb");
		System.out.println( "PDA Test C: ('"+ret.getInput()+"',"+ret.isAccepted()+","+ret.getPositionOfDiff()+")" );*/
	}
}
