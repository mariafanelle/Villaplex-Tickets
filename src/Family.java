/*
 * Family.java
 * Creates an object that holds the family's information
 */

public class Family {

	private String name;
	private int adultSize;
	private int childrenSize;
	private int [][] seats;
	private int movieChoice;
	private Family next;
	private boolean inLine;
	
	
	public Family(String name, int adultSize, int childrenSize, int movieChoice)
	{
		this.name = name;
		this.adultSize = adultSize;
		this.childrenSize = childrenSize;
		this.movieChoice = movieChoice;
		this.next = null;
		this.seats = new int [adultSize+childrenSize][2];
		this.inLine = true;
		
		for (int i = 0; i < seats.length; i++)
		{
			this.seats[i][0] = -1;
		}
		
	}
	
	public void addSeats(int row, int col)
	{
		for (int i = 0; i < adultSize+childrenSize; i++) 
		{
			if (seats[i][0] == -1)
			{
				seats[i][0] = row;
				seats[i][1] = col;
				return;
			}
		}
	}
	
	public int[][] getSeatNumbers()
	{
		return this.seats;
	}

	
	public int getMovieChoice()
	{
		return movieChoice;
	}
	
	public void setMovieChoice(int choice)
	{
		this.movieChoice = choice;
	}
	
	public Family getLink()
	{
		return this.next;
	}
	
	public void setLink(Family nextFamily)
	{
		this.next = nextFamily;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getAdultSize()
	{
		return adultSize;
	}
	
	public int getChildrenSize()
	{
		return childrenSize;
	}
	
	public boolean isInLine()
	{
		return inLine;
	}
	
	public void setInLine(boolean inLine)
	{
		this.inLine = inLine;
	}
	
}
