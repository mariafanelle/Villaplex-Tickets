import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/*
 * RegTicketLine.java
 * 
 * Provides link-based queue structure.
 * 
 */

public class RegTicketLine {

	private Family first;
	private Family last;
	private int n; // length of queue
	
	public RegTicketLine() 
	{
		first = null;
		last = null;
		n = 0;
	}
	
	public void enqueue(Family family)
	{
		if (last == null) 
			first = family;
		else 
		{
			Family temp = last;
			temp.setLink(family);
		}
		last = family;
		n++;
	}
	
	public Family dequeue()
	{
		Family temp = first;
		
		if (!isEmpty()){
			first = temp.getLink();
			if (first == null)
				last = null;
			n--;
		}
		return temp;
		
	}
	
	public boolean isEmpty()
	{
		return first == null;
	}
	
	public int size()
	{
		return n;
	}
	
	public Family peek()
	{
		return first;
	}
	
	public VBox printQueue()
	{
		Family temp = first;
		Text current = new Text();
		VBox queue = new VBox();
		boolean addedToQueue = false;
		
		if (temp == null)
		{
			current = new Text("<Empty>\n");
			queue.getChildren().add(current);
			return queue;
		}
		
		while (temp != null)
		{
			if (temp.isInLine() == true)
			{ 
				current = new Text(temp.getName() + "\n");
				queue.getChildren().add(current);
				addedToQueue = true;
			}
			temp = temp.getLink();
		}
		
		// if nobody actually in queue
		if (addedToQueue == false)
		{
			current = new Text("<Empty>\n");
			queue.getChildren().add(current);
		}
		
		//System.out.println("finished here");
		return queue;
	}
	
	public void empty()
	{
		while (first != null)
		{
			first = first.getLink();
		}
		//System.out.println("This line is empty.");
	}
	
}