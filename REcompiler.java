public class REcompiler {
	public static void main(String[] args) {	
		int inputIndex = 0;
		String regEx = args[0];
		char[] regString = regEx.toCharArray();
		parse(regString,inputIndex);
	}
	
	static void expression(char[] regString, int inputIndex) {
	  term(regString,inputIndex);
	  if(isvocab(regString[inputIndex])||regString[inputIndex]=='(')
		expression(regString,inputIndex);
	}

	static void term(char[] regString, int inputIndex) {
	  factor(regString, inputIndex);
	  if(regString[inputIndex]=='*') inputIndex++;
	  if(regString[inputIndex]=='+'){    inputIndex++;     term(regString, inputIndex); }
	}

	static void factor(char[] regString, int inputIndex) {
	  if(isvocab(regString[inputIndex])) inputIndex++;
	  else
	    if(regString[inputIndex]=='('){
	      inputIndex++;    expression(regString,inputIndex);
	      if(regString[inputIndex]==')') inputIndex++;
	      else error();
	    }
	    else error();
	}

	static void parse(char[] regString, int inputIndex) {
	   expression(regString,inputIndex);
	   if(regString[inputIndex] != '\0') error();
	}
	
	static void error() {
		System.err.println("you messed up boi");
	}
	
	static boolean isvocab(char currChar) {
		return Character.isLetterOrDigit(currChar);
	}
}
