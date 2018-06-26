/*
 * Receipts.java
 * Provides linked-based stack structure & individual receipt objects
*/

public class Receipts {
	
	private Receipt top;
	private int total;

	public class Receipt
	{
		private Receipt next;
		private int numChildren, numAdult;
		
		// create receipt nodes
		public Receipt(int numChildren, int numAdult)
		{
			this.numChildren = numChildren;
			this.numAdult = numAdult;
			this.next = null;
		}
		
		public Receipt getLink()
		{
			return this.next;
		}
		
		public void setLink(Receipt nextLink)
		{
			this.next = nextLink;
		}
		
		public int getChildren()
		{
			return this.numChildren;
		}
		
		public int getAdult()
		{
			return this.numAdult;
		}
		
	}
	
	public Receipts()
	{
		// initialize empty Stack
		top = null;
		total = 0;
		
	}
	
	public boolean isEmpty()
	{
		return top == null;
	}
	
	// a link-based stack is never full
	// can always take input
	public boolean isFull()
	{
		return false;
	}
	
	public void push(int numChildren, int numAdult)
	{
		System.out.print("Pushed " + numChildren + " children and " + numAdult + " adults.");
		if(top == null) 
		{
			top = new Receipt(numChildren, numAdult);
		}
		else 
		{
			Receipt temp = new Receipt(numChildren, numAdult);
			temp.setLink(top);
			top = temp;
			
		}
		total++;
	}
	
	public void pop()
	{
		if(top == null) 
		{
			//System.out.println("No family information available.");
		}
		else 
		{
			Receipt temp = top;
			top = top.getLink();
		}
		total--;
	}
	
	public int amountOfReceipts()
	{
		return total;
	}
	
	public void printReceipt() {
		Receipt temp = top;
		
		if (temp == null)
			//System.out.println("There are no receipts.");
		
		while (temp != null)
		{
			//System.out.println("Children: " + temp.getChildren() + ", Number of adults: " + temp.getAdult());
			temp = temp.getLink();
		}
	}
	
	public int soldChildren()
	{
		Receipt temp = top;
		
		if (temp == null)
		{
			return 0;
		}
		
		int childrenCnt = 0; // test numbers
		
		while (temp != null)
		{
			childrenCnt += temp.getChildren();
			temp = temp.getLink();
		}
		return childrenCnt;
	}
	
	public int soldAdults() 
	{
		Receipt temp = top;
		
		if (temp == null)
		{
			return 0;
		}
		
		int adultCnt = 0; // test numbers
		
		while (temp != null)
		{
			adultCnt += temp.getAdult();
			temp = temp.getLink();
		}
		return adultCnt;
	}
}
