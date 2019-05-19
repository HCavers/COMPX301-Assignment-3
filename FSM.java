// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

import java.util.ArrayList;

class FSM
{
	// Variable to hold all states of machine
	ArrayList<State> machine;
	
	// Constructor
	public FSM()
	{
		machine = new ArrayList<State>();
	}
	
	// Returns state at position index from machine
	public State getState(int index)
	{
		return machine.get(index);
	}
	
	// Makes and adds new state to machine
	public void addState(int index, char value, int n1, int n2)
	{
		State state = new State(index, value, n1, n2);
		machine.add(state);
	}
}