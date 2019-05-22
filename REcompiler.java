public class REcompiler {
	public static int inputIndex = 0; 
	static char[] regString; 
	// Stores the current character in the integer inputIndex and the entire string in the array regString
	static int state = 0; 
	static char[] ch = new char[21474]; 
	static int[] next1 = new int[21474]; 
	static int[] next2 = new int[21474];
	static int startState;
	// Creates the variables that store information about the states
	
	public static void main(String[] args) {	
		String regEx = args[0];
		regEx += '\0'; // Reads the input and adds a terminating character to the end
		regString = regEx.toCharArray();
		compileParse(); // Compiles the input
					
		for(int i = 0;i <= state;i++) {
			System.out.println(String.valueOf(i) + "," + String.valueOf(ch[i]) + "," + String.valueOf(next1[i]) + "," + String.valueOf(next2[i]));
			// Prints out each state to output
		}
		System.out.println(startState); // Prints the start state so the searcher knows where to start
	}
	
	static void set_state(int s, char c, int n1, int n2){ 
		ch[s]=c;
		next1[s]=n1;
		next2[s]=n2;
		// Stores the state information in the relevant variables
	}

	static int compileExpression()	{ // Tries to regexp match for expression
	  int r;

	  r=compileTerm();
	  if(isvocab(regString[inputIndex]) || regString[inputIndex] == '(') {
		  compileExpression();
	  }
	  return(r);
	}

	static int compileTerm() { // Tries to regexp match for term
	  int r, t1,t2,f;

	  f=state-1;
	  r=t1=compileFactor();
	  if(regString[inputIndex]=='*'){ // checks for closure and if so create branch
	    set_state(state,' ',state+1,t1);
	    inputIndex++;
	    r=state;
	    state++;
	  }
	  if(regString[inputIndex]=='+'){ // checks for + and if so create concatenation 
	    if(next1[f]==next2[f])
		next2[f]=state;
	    next1[f]=state;
	    f=state-1;
	    inputIndex++;
	    r=state;
	    state++; 
	    t2=compileTerm();
	    set_state(r,' ',t1,t2);
	    if(next1[f]==next2[f])
		next2[f]=state;
	    next1[f]=state;
	  }
	  return(r);
	}

	static int compileFactor() { // Tries to regexp match for factor
	  int r= 0;
	  if(isvocab(regString[inputIndex])){
	    set_state(state,regString[inputIndex],state+1,state+1);
	    inputIndex++;r=state; state++;
	  }
	  else
	    if( regString[inputIndex] == '('){ // Checks for opening bracket
	    	inputIndex++; r=compileExpression();
	      if(regString[inputIndex] == ')') // Checks for the corresponding closing bracket
	    	  inputIndex++;
	      else {
	    	  error();
	      }
	    	  
	    }
	    else {
	    	error();
	    }
	    	
	  return(r);
	}
	
	static void compileParse() { // Compiles the regexp string into a finite state machine
		startState= compileExpression();
		if( regString[inputIndex] != '\0') { // Checks if the character is not the terminating character
			error(); 
		}
		set_state(state,' ',0,0); // Print the final state;
	}
	
	static void error() {
		System.err.println("Error: Input is not a wellformed regexp");
		// If the input string is not in the right format then an error message is displayed
	}
	
	static boolean isvocab(char currChar) {
		return Character.isLetterOrDigit(currChar);
	}
}
