// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026) 

class State
{
	// Variables
	private int stateNum;		// Index of the state
	private char stateValue;	// Value state accepts
	private int nextState1;		// Index of left next state
	private int nextState2;		// Index of right next state
	
	// Constructor
	// Can be used when only states index and accepted value are known
	public State(int num, char value)
	{
		stateNum = num;
		stateValue = value;
	}
	
	// Can be used when all values of a state are known
	public State(int num, char value, int n1, int n2)
	{
		stateNum = num;
		stateValue = value;
		nextState1 = n1;
		nextState2 = n2;
	}
	
	// Properties
	// Returns index of state
	public int getIndex()
	{
		return stateNum;
	}
	
	// Returns accepted value of state
	public char getAcceptedValue()
	{
		return stateValue;
	}
	
	// Returns index of left next state 
	public int getNextState1()
	{
		return nextState1;
	}
	
	// Return index of right next state
	public int getNextState2()
	{
		return nextState2;
	}
	
	// Allows left next state to be set as long as index is greater than 0
	public void setNextState1(int n)
	{
		if(n > 0)
		{
			nextState1 = n;
		}
		else
		{
			throw new IndexOutOfBoundsException("Index must be greater than zero");
		}
	}
	
	// Allows right next state to be set as long as index is greater than 0
	public void setNextState2(int n)
	{
		if(n > 0)
		{
			nextState2 = n;
		}
		else
		{
			throw new IndexOutOfBoundsException("Index must ne greater than zero");
		}
	}
	
	// Returns if state is a branching state
	public boolean isBranch()
	{
		if(stateValue == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// Returns if state accepts the input given
	public boolean acceptsInput(char input)
	{
		if(stateValue == input)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// Prints contents of state to standard out (For debugging purposes)
	public void print()
	{
		String s1 = String.format("State Index: %s", stateNum);
		String s2 = String.format("State Value: %s", stateValue);
		String s3 = String.format("1st Next State: %s", nextState1);
		String s4 = String.format("2nd Next State: %s", nextState2);
		String line = String.format("%s \t %s \t %s \t %s", s1, s2, s3, s4);
		System.out.println(line);
	}
}