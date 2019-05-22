// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;

class REsearcher
{
	private static FSM fsm = new FSM();
	
	public static void main(String[] args)
	{
		try{
			// Check if correct number of aruguments have been supplied
			if(args.length != 1)
			{
				System.out.println("Usage: java REsearcher <file name>");
				return;
			}
			// Load FSM get file and process the file
			loadFSM();
			File file = new File(args[0]);
			processFile(file);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	// Loads the finite state machine from input gotten from standard in
	public static void loadFSM() throws IOException
	{
		// Set up variable to be able to read input line by line
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line;
		int count = 0;
		// While there is input
		while((line = reader.readLine()) != null)
		{
			String[] array = line.split(",");
			// If input only contains 1 item must be start state
			if(array.length == 1)
			{
				// Get and set start state
				int startState = getStartState(array[0]);
				fsm.setStartState(startState);
				// If there is more input then input is in incorrect format so error
				line = reader.readLine();
				if(line != null)
				{
					System.err.println("Error: Input incorrect data was found after statrt state value");
					System.exit(0);
				}
			}
			// If there are not four items then incorrect format so error
			else if(array.length != 4)
			{
				System.err.println(String.format("Error: Line %d is in incorrect format", count));
				System.exit(0);
			}
			// Otherwise load state
			else
			{
				State state = getState(array, count);
				fsm.addState(state);
			}
			count++;
		}
	}
	
	// Gets the value of the start state from the input given
	public static int getStartState(String input)
	{
		// Is it's own method to catch this specific exception to print error message about this error
		try
		{
			int startState = Integer.parseInt(input);
			return startState;
		}
		catch(Exception ex)
		{
			System.err.println("Error: Start state was in incorrect format");
			System.exit(0);
			return 0;
		}
	}
	
	// Returns a state populated with data from input given
	public static State getState(String[] input, int lineNum)
	{
		// Is it's own method to catch this specific exception to print error message about this error
		try
		{
			int stateNum = Integer.parseInt(input[0]);
			int n1 = Integer.parseInt(input[2]);
			int n2 = Integer.parseInt(input[3]);
			char stateValue;
			if(input[1].equals(""))
			{
				// Value used to specify branch state
				stateValue = 0;
			}
			else
			{
				stateValue = input[1].charAt(0);
			}
			State state = new State(stateNum, stateValue, n1, n2);
			return state;
		}
		catch(Exception ex)
		{
			System.err.println(String.format("Error: Data in incorrect format line: %s", lineNum));
			System.exit(0);
			return null;
		}
	}
	
	// Loops through file and calls the process line method for each line
	public static void processFile(File file) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line;
		while((line = reader.readLine()) != null)
		{
			processLine(line);
		}
	}
	
	// Loops through each char in line checking if pattern 
	// occurs and prints line to standard out if it does
	public static void processLine(String line)
	{
		for(int i = 0; i < line.length(); i++)
		{
			if(patternSearch(line, i) == true)
			{
				System.out.println(line);
				return;
			}
		}
	}
	
	// Searches for pattern in input from position startIndex
	public static boolean patternSearch(String input, int startIndex)
	{
		int index = startIndex;
		Dequeue deq = initialiseDequeue();
		while(true)
		{
			// If stack portion of dequeue is empty then pattern does not match
			if(deq.stackEmpty())
			{
				return false;
			}
			if(deq.stackLength() == 1)
			{
				// If the final state is the only state in the stack portion
				// of the dequeue then a match has been found
				int value = deq.pop();
				if(value == fsm.getFinalState())
				{
					return true;
				}
				deq.push(value);
			}
			// If input has run out and still have states in stack portion of 
			// dequeue then no pattern match
			if(index >= input.length())
			{
				return false;
			}
			// Loop until stack is empty
			while(!(deq.stackEmpty()))
			{
				// Get state from top of stack
				int stateIndex = deq.pop();
				State state = fsm.getState(stateIndex);
				// if state is not branch
				if(!(state.isBranch()))
				{
					// Check if it accepts value or if state is wildcard
					char value = input.charAt(index);
					if(state.acceptsInput(value) || state.getAcceptedValue() == '.')
					{
						// If next states are the same only add on to queue portion of dequeue
						if(state.getNextState1() == state.getNextState2())
						{
							deq.put(state.getNextState1());
						}
						// Otherwise add both
						else
						{
							deq.put(state.getNextState1());
							deq.put(state.getNextState2());
						}
					}
				}
				// If branch state add next states to stack portion of dequeue
				else
				{
					// If next states are the same only add on to stack portion of dequeue
					if(state.getNextState1() == state.getNextState2())
					{
						deq.push(state.getNextState1());
					}
					// Otherwise add both
					else
					{
						deq.push(state.getNextState1());
						deq.push(state.getNextState2());
					}
				}
			}
			// Move stack pointer so stack is now queue
			deq.resetSP();
			// Move value along input by one
			index++;
		}
	}
	
	// Returns a new Dequeue with the index of the start state in the stack
	public static Dequeue initialiseDequeue()
	{
		Dequeue deq = new Dequeue();
		deq.push(fsm.getStartState());
		return deq;
	}
}