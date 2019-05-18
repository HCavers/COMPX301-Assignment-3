// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)
import java.util.List;
import java.util.ArrayList;

class Dequeue
{
	private ArrayList<Integer> stack;
	private ArrayList<Integer> queue;
	
	public Dequeue()
	{
		stack = new ArrayList<Integer>();
		queue = new ArrayList<Integer>();
	}
	
	// Adds element to the stack portion of the dequeue
	public void push(int input)
	{
		stack.add(input);
	}
	
	// Removes and returns top element from stack portion of dequeue
	public int pop()
	{
		int value = stack.get(0);
		stack.remove(0);
		return value;
	}
	
	// Adds element to the queue portion of the dequeue
	public void put(int input)
	{
		queue.add(input);
	}
	
	// Resets the position of the stack pointer
	public void resetSP()
	{
		ArrayList<Integer> temp = stack;
		stack = queue;
		queue = temp;
	}
	
	// Checks if the stack portion of the dequeue is empty
	public boolean stackEmpty()
	{
		if(stack.size() == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}