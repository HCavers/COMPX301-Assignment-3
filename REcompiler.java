//output is int,char,int,int

public class REcompiler {
	public static int inputIndex = 0;
	public static void main(String[] args) {	
		String regEx = args[0];
		regEx += '\0';
		char[] regString = regEx.toCharArray();
		System.err.println(regString);
		parse(regString);
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
