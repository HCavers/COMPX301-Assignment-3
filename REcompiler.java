import java.util.Arrays;

//output is int,char,int,int

public class REcompiler {
	public static int inputIndex = 0;
	static char[] regString;
	static int state = 0;
	static char[] ch = new char[8];
	static int[] next1 = new int[8];
	static int[] next2 = new int[8];
	static int startState;
	public static void main(String[] args) {	
		String regEx = args[0];
		regEx += '\0';
		regString = regEx.toCharArray();
		compileParse();
		int length = ch.length;
				
		for(int i = 0;i < length;i++) {
			System.out.println(String.valueOf(i) + "," + String.valueOf(ch[i]) + "," + String.valueOf(next1[i]) + "," + String.valueOf(next2[i]));
		}
		System.out.println(startState);
	}
	
	static void set_state(int s, char c, int n1, int n2){
		ch[s]=c;
		next1[s]=n1;
		next2[s]=n2;
	}

	static int compileExpression()	{
	  int r;

	  r=compileTerm();
	  if(isvocab(regString[inputIndex])||regString[inputIndex]=='[' || regString[inputIndex] == '(') {
		  compileExpression();
	  }
	  return(r);
	}

	static int compileTerm() {
	  int r, t1,t2,f;

	  f=state-1;
	  r=t1=compileFactor();
	  if(regString[inputIndex]=='*'){
	    set_state(state,' ',state+1,t1);
	    inputIndex++;
	    r=state;
	    state++;
	  }
	  if(regString[inputIndex]=='+'){
	    if(next1[f]==next2[f])
		next2[f]=state;
	    next1[f]=state;
	    f=state-1;
	    inputIndex++;r=state;
	    state++; 
	    t2=compileTerm();
	    set_state(r,' ',t1,t2);
	    if(next1[f]==next2[f])
		next2[f]=state;
	    next1[f]=state;
	  }
	  return(r);
	}

	static int compileFactor() {
	  int r= 0;
	  if(isvocab(regString[inputIndex])){
	    set_state(state,regString[inputIndex],state+1,state+1);
	    inputIndex++;r=state; state++;
	  }
	  else
	    if(regString[inputIndex]=='[' || regString[inputIndex] == '('){
	    	inputIndex++; r=compileExpression();
	      if(regString[inputIndex]==']' || regString[inputIndex] == ')')
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
	
	static void compileParse() {
		startState= compileExpression();
		if( regString[inputIndex] != '\0') {
			error(); // In C, zero is false, not zero is true
		}
		set_state(state,' ',0,0);
	}
	
	static void error() {
		System.err.println("Error: Input is not a wellformed regexp");
	}
	
	static boolean isvocab(char currChar) {
		return Character.isLetterOrDigit(currChar);
	}
}
