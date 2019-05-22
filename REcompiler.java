import java.util.Arrays;

//output is int,char,int,int

public class REcompiler {
	public static int inputIndex = 0;
	public static int j = 0;
	static char[] p;
	static int state = 0;
	static char[] ch = new char[8];
	static int[] next1 = new int[8];
	static int[] next2 = new int[8];
	static int startState;
	public static void main(String[] args) {	
		String regEx = args[0];
		regEx += '\0';
		char[] regString = regEx.toCharArray();
		p = regEx.toCharArray();
		System.err.println(regString);
		//parse(regString);
		compileParse();
//		System.out.println(Arrays.toString(ch));
//		System.out.println(Arrays.toString(next1));
//		System.out.println(Arrays.toString(next2));
		
		int length = ch.length * 4;
		String[] outputStates = new String[length];
		
		for(int i = 0;i < length/4;i++) {
			outputStates[i*4] = String.valueOf(i);
			outputStates[i*4 + 1] = String.valueOf(ch[i]);
			outputStates[i*4 + 2] = String.valueOf(next1[i]);
			outputStates[i*4 + 3] = String.valueOf(next2[i]);
			
			//outputStates[i] =  String.valueOf(i) + "," + ch[i] + "," + next1[i] + "," + next2[i];
		}
//		String[] startStates = new String[4];
//		startStates[0] = String.valueOf(startState);
//		startStates[1] = String.valueOf(ch[startState]);
//		startStates[2] = String.valueOf(next1[startState]);
//		startStates[3] = String.valueOf(next2[startState]);
		System.out.println(Arrays.toString(outputStates));
//		System.out.println(Arrays.toString(startStates));
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
	  if(isvocab(p[j])||p[j]=='[') {
		  compileExpression();
	  }
	  return(r);
	}

	static int compileTerm() {
	  int r, t1,t2,f;

	  f=state-1;
	  r=t1=compileFactor();
//	  if(p[j] == '?') {
//		  set_state(state,' ',);
//	  }
	  if(p[j]=='*'){
	    set_state(state,' ',state+1,t1);
	    j++;
	    r=state;
	    state++;
	  }
	  if(p[j]=='+'){
	    if(next1[f]==next2[f])
		next2[f]=state;
	    next1[f]=state;
	    f=state-1;
	    j++;r=state;
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
	  //int r;

	  if(isvocab(p[j])){
	    set_state(state,p[j],state+1,state+1);
	    j++;r=state; state++;
	  }
	  else
	    if(p[j]=='['){
	      j++; r=compileExpression();
	      if(p[j]==']')
		j++;
	      else {
	    	  
	      		error();
	      }
	    	  
	    }
	    else {
	    
		      error();
	    }
	    	
	  return(r);
	}
	
	static void expression(char[] regString) {
	  term(regString);
	  if(isvocab(regString[inputIndex])||regString[inputIndex]=='(') {
		  expression(regString);
	  }
	}

	static void term(char[] regString) {
	  factor(regString);
	  if(regString[inputIndex]=='*') {
		  inputIndex++;
	  }
	  if(regString[inputIndex]=='+'){ 
		  inputIndex++;
		  term(regString);
	  }
	}

	static void factor(char[] regString) {
	  if(isvocab(regString[inputIndex])) {
		  inputIndex++;
	  }
	  else
	    if(regString[inputIndex]=='('){
	      inputIndex++; 
	      expression(regString);
	      if(regString[inputIndex]==')') {
	    	  inputIndex++;
	      }
	      else
	    	  
	    	  error();
	    }
	    else {
	    	error();
	    }
	    	
	}

	static void parse(char[] regString) {
		
	   expression(regString);
	   if(regString[inputIndex] != '\0' ) {
		  
		   error();
	   }
	   
	}
	
	static void compileParse() {
		startState= compileExpression();
		if( p[j] != '\0') {
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
