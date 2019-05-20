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
			if(args.length != 1)
			{
				System.out.println("Usage: java REsearcher <file name>");
				return;
			}
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
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line;
		int count = 0;
		while((line = reader.readLine()) != null)
		{
			String[] array = line.split(",");
			if(array.length == 1)
			{
				int startState = getStartState(array[0]);
				fsm.setStartState(startState);
				line = reader.readLine();
				if(line != null)
				{
					System.err.println("Error: Input incorrect data was found after statrt state value");
					System.exit(0);
				}
			}
			else if(array.length != 4)
			{
				System.err.println(String.format("Error: Line %d is in incorrect format", count));
				System.exit(0);
			}
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
		try
		{
			int stateNum = Integer.parseInt(input[0]);
			int n1 = Integer.parseInt(input[2]);
			int n2 = Integer.parseInt(input[3]);
			char stateValue;
			if(input[1].equals(""))
			{
				stateValue = '0';
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
	
	public static void processLine(String line){}
}