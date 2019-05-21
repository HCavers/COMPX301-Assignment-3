//output is int,char,int,int

public class REcompiler {
	public static int inputIndex = 0;
	public static int j = 0;
	char[] p;
	int state = 0;
	char[] ch;
	int[] next1;
	int[] next2;
	public static void main(String[] args) {	
		String regEx = args[0];
		regEx += '\0';
		char[] regString = regEx.toCharArray();
		System.err.println(regString);
		parse(regString);
	}
	
	void set_state(int s, char c, int n1, int n2){
		ch[s]=c;next1[s]=n1;next2[s]=n2;
	}

	int compileExpression()
	{
	  int r;

	  r=compileTerm();
	  if(isvocab(p[j])||p[j]=='[') {
		  compileExpression();
	  }
	  return(r);
	}

	int compileTerm()
	{
	  int r, t1,t2,f;

	  f=state-1; r=t1=compileFactor();
	  if(p[j]=='*'){
	    set_state(state,' ',state+1,t1);
	    j++; r=state; state++;
	  }
	  if(p[j]=='+'){
	    if(next1[f]==next2[f])
		next2[f]=state;
	    next1[f]=state;
	    f=state-1;
	    j++;r=state;state++; 
	    t2=compileTerm();
	    set_state(r,' ',t1,t2);
	    if(next1[f]==next2[f])
		next2[f]=state;
	    next1[f]=state;
	  }
	  return(r);
	}

	int compileFactor()
	{
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
	      else
		error();
	    }
	    else
	      error();
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
	      else error();
	    }
	    else error();
	}

	static void parse(char[] regString) {
		
	   expression(regString);
	   if(regString[inputIndex] != '\0' ) {
		   error();
	   }
	   System.err.println("REACHED END");
	}
	
	static void error() {
		System.err.println("you messed up boi");
	}
	
	static boolean isvocab(char currChar) {
		return Character.isLetterOrDigit(currChar);
	}
}
