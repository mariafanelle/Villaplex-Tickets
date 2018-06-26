import java.text.NumberFormat;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class TheaterSystem extends Application {
	
	Stage window;
	Button button;
	int row1, col1, row2, col2;
	double adultPrice, childPrice;
	NumberFormat formatter;
	String movieTitle1, movieTitle2;
	Family[][] theaterOne, theaterTwo;
	Group listResults;
	int seatsLeft1, seatsLeft2;
	
	RegTicketLine regLine1, regLine2, expressLine;
	Receipts stack;
	ArrayList<Family> families;
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	// start GUI
	public void start(Stage primaryStage) throws Exception
	{
		window = primaryStage;
		
		// set on close request
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		
		// Scene 1: enter details about theater
		GridPane setTheater = new GridPane();
		setTheater.setPadding(new Insets(20, 20, 20, 20));
		setTheater.setVgap(12);
		setTheater.setHgap(10);
		
		Text title = new Text("Welcome to the Villaplex Ticketing System");
		
		GridPane.setConstraints(title, 0, 0);
		
		Label movieOne = new Label("Enter the name of the first movie:");
		GridPane.setConstraints(movieOne, 0, 1);
		TextField movieOneInput = new TextField();
		GridPane.setConstraints(movieOneInput, 1, 1);
		
		Label movieTwo = new Label("Enter the name of the second movie:");
		GridPane.setConstraints(movieTwo, 0, 2);
		TextField movieTwoInput = new TextField();
		GridPane.setConstraints(movieTwoInput, 1, 2);
		
		Label rowLabel1 = new Label("Enter the rows of theater 1:");
		GridPane.setConstraints(rowLabel1, 0, 4);
		TextField rowInput1 = new TextField();
		GridPane.setConstraints(rowInput1, 1, 4);
		
		Label colLabel1 = new Label("Enter the columns of theater 1:");
		GridPane.setConstraints(colLabel1, 0, 5);
		TextField colInput1 = new TextField();
		GridPane.setConstraints(colInput1, 1, 5);
		
		Label rowLabel2 = new Label("Enter the rows of theater 2:");
		GridPane.setConstraints(rowLabel2, 0, 6);
		TextField rowInput2 = new TextField();
		GridPane.setConstraints(rowInput2, 1, 6);
		
		Label colLabel2 = new Label("Enter the columns of theater 2:");
		GridPane.setConstraints(colLabel2, 0, 7);
		TextField colInput2 = new TextField();
		GridPane.setConstraints(colInput2, 1, 7);
		
		Label adultTicketLabel = new Label("Enter the price of the adult ticket:");
		GridPane.setConstraints(adultTicketLabel, 0, 9);
		TextField adultTicketInput = new TextField();
		GridPane.setConstraints(adultTicketInput, 1, 9);
		
		Label childTicketLabel = new Label("Enter the price of the child ticket:");
		GridPane.setConstraints(childTicketLabel, 0, 10);
		TextField childTicketInput = new TextField();
		GridPane.setConstraints(childTicketInput, 1, 10);
		
		Button submitButton = new Button("Submit");
		GridPane.setConstraints(submitButton, 1, 12);
		
		// check if all input is valid
		submitButton.setOnAction(e -> {
		if (Confirm.isInt(rowInput1) == true && 
				Confirm.isInt(rowInput2) == true &&
				Confirm.isInt(colInput1) == true &&
				Confirm.isInt(colInput2) == true &&
				Confirm.isFloat(adultTicketInput) == true &&
				Confirm.isFloat(childTicketInput) == true)
		{
			row1 = Integer.parseInt(rowInput1.getText());
			row2 = Integer.parseInt(rowInput2.getText());
			col1 = Integer.parseInt(colInput1.getText());
			col2 = Integer.parseInt(colInput2.getText());
			adultPrice = Double.parseDouble(adultTicketInput.getText());
			childPrice = Double.parseDouble(childTicketInput.getText());
			
			// set size of theaters
			theaterOne = new Family [row1][col1];
			theaterTwo = new Family [row2][col2];
			
			movieTitle1 = movieOneInput.getText();
			movieTitle2 = movieTwoInput.getText();
			
			// initialize data structures
			regLine1 = new RegTicketLine();
			regLine2 = new RegTicketLine();
			expressLine = new RegTicketLine();
			stack = new Receipts();
			families = new ArrayList<Family>();
			
			seatsLeft1 = row1 * col1;
			seatsLeft2 = row2 * col2;
			
			// change to main menu after validating data
			mainMenu(primaryStage);
		}
		});
		
		setTheater.getChildren().addAll(title, movieOne, movieOneInput, movieTwo, movieTwoInput, rowLabel1, rowInput1, colLabel1, colInput1, rowLabel2, rowInput2, colLabel2, colInput2, adultTicketLabel, adultTicketInput, childTicketLabel, childTicketInput, submitButton);
		setTheater.setAlignment(Pos.CENTER);
		Scene setTheaterScene = new Scene(setTheater, 550, 500);
		
		// set initial scene
		window.setTitle("Villaplex Theater Ticketing System");
		window.setScene(setTheaterScene);
		window.show();
	}
	
	// show main ticketing system options
	public void mainMenu (Stage primaryStage)
	{	
		// set up ticketing system menu
		VBox menu = new VBox(10);
		
		Text welcome = new Text("Welcome to the Villaplex Movies");
		Text nowPlaying = new Text("Now playing in our theaters:");
		Text movieOneLabel = new Text("Movie One: " + movieTitle1);
		Text movieTwoLabel = new Text("Movie Two: " + movieTitle2);
		
		Button option1 = new Button("1. Customer(s) enter(s) the movie theater.");	
		option1.setOnAction(e -> addCustomer(primaryStage));
		Button option2 = new Button("2. Customer is served.");	
		option2.setOnAction(e -> serveCustomer(primaryStage));
		Button option3 = new Button("3. Customer(s) leave(s) the movie theater.");	
		option3.setOnAction(e -> leaveTheater(primaryStage));
		Button option4 = new Button("4. Display info about customers waiting for tickets.");
		option4.setOnAction(e -> printLineInfo(primaryStage));
		Button option5 = new Button("5. Display seating chart for the first film.");
		option5.setOnAction(e -> printTheater(primaryStage, theaterOne));
		Button option6 = new Button("6. Display seating chart for the second film.");
		option6.setOnAction(e -> printTheater(primaryStage, theaterTwo));
		Button option7 = new Button("7. Display the number of tickets sold and total earning.");
		option7.setOnAction(e -> ticketsSold(primaryStage));
		Button option8 = new Button("8. End the program.");
		option8.setOnAction(e -> exit(primaryStage));
		
		menu.getChildren().addAll(welcome, nowPlaying, movieOneLabel, movieTwoLabel, option1, option2, option3, option4, option5, option6, option7, option8);
		menu.setAlignment(Pos.CENTER);
		Scene menuScene = new Scene(menu, 550, 500);
		window.setScene(menuScene);
		
	}
	
	// add a customer
	private void addCustomer(Stage primaryStage)
	{
		
		GridPane addCustomer = new GridPane();
		addCustomer.setPadding(new Insets(20, 20, 20, 20));
		addCustomer.setVgap(12);
		addCustomer.setHgap(10);
		
		Text title = new Text("1. Customer(s) enter(s) the movie theater.");
		
		GridPane.setConstraints(title, 0, 0);
		
		Label partyName = new Label("Enter the name of the party:");
		GridPane.setConstraints(partyName, 0, 1);
		TextField partyNameInput = new TextField();
		GridPane.setConstraints(partyNameInput, 1, 1);
		
		Label adultNum = new Label("How many adults in this party? :");
		GridPane.setConstraints(adultNum, 0, 2);
		TextField adultNumInput = new TextField();
		GridPane.setConstraints(adultNumInput, 1, 2);
		
		Label childrenNum = new Label("How many children in this party? :");
		GridPane.setConstraints(childrenNum, 0, 3);
		TextField childrenNumInput = new TextField();
		GridPane.setConstraints(childrenNumInput, 1, 3);
		
		Label whichMovie = new Label("Which theater do you want? :");
		GridPane.setConstraints(whichMovie, 0, 4);
		ChoiceBox<String> choiceBox = new ChoiceBox<String>();
		choiceBox.getItems().add("1. " + movieTitle1);
		choiceBox.getItems().add("2. " + movieTitle2);
		choiceBox.setValue("1. " + movieTitle1);
		GridPane.setConstraints(choiceBox, 1, 4);
		
		Button submit = new Button("Submit");	
		GridPane.setConstraints(submit, 1, 5);
		submit.setOnAction(e -> {
			
			if (Confirm.isInt(childrenNumInput) == true && 
					Confirm.isInt(adultNumInput) == true)
			{
				int adultSize = Integer.parseInt(adultNumInput.getText());
				int childrenSize = Integer.parseInt(childrenNumInput.getText());
				
				int chooseTheatre = 1;
				
				if (choiceBox.getValue().equals("1. " + movieTitle1))
					chooseTheatre = 1;
				if (choiceBox.getValue().equals("2. " + movieTitle2))
					chooseTheatre = 2;
				
				String famName = partyNameInput.getText();
				
				Family currentFam = new Family(famName, adultSize, childrenSize, chooseTheatre);
				
				boolean inTheater = false;
				
				// look to see if family is already in theater
				for (Family current : families)
				{
					//System.out.println("family: " + current.getName());
					if(current.getName().toLowerCase().equals(famName.toLowerCase()))
						inTheater = true;
				}
				
				// if this is a unique family
				if (inTheater == false)
				{
					families.add(currentFam);
					
					// Put families with children in the Express Line.
				    // Other parties will be divided up between Lines 1 and 2.
					// Place customer in the shortest line; family goes to the reg line when twice as short as express.
					if (childrenSize > 0) 
						if (expressLine.isEmpty())
							expressLine.enqueue(currentFam);
						else if (regLine1.size() < Math.ceil(expressLine.size()/2))
							regLine1.enqueue(currentFam);
						else if (regLine2.size() < Math.ceil(expressLine.size()/2))
							regLine2.enqueue(currentFam);
						else
							expressLine.enqueue(currentFam);
					else
					{
						if (regLine1.isEmpty())
							regLine1.enqueue(currentFam);
						else if (regLine1.size() < regLine2.size())
							regLine1.enqueue(currentFam);
						else 
							regLine2.enqueue(currentFam);
					}	
					
					mainMenu(primaryStage);
				} // else prompt the user to enter a value again
				else 
				{
					Confirm.display("Error", famName + " is already in the theater.  Please enter another name.");
				}
		}
		});
		
		addCustomer.getChildren().addAll(title, partyName, partyNameInput, adultNum, adultNumInput, childrenNum, childrenNumInput, whichMovie, choiceBox, submit);
		addCustomer.setAlignment(Pos.CENTER);
		Scene menuScene = new Scene(addCustomer, 550, 500);
		window.setScene(menuScene);
	}
	
	// add a customer
	private void serveCustomer(Stage primaryStage)
	{
		
		VBox serveCustomer = new VBox(10);
		
		Text title = new Text("2. Customer is served.\n");
		Text whichFirst = new Text("Which queue will be served first?\n");
		
		Button option1 = new Button("1. Reg1");
		Button option2 = new Button("2. Reg2");
		Button option3 = new Button("3. Express");
		
		option1.setOnAction(e -> {
			// reg1 first
			
			if (!regLine1.isEmpty() || !regLine2.isEmpty() || !expressLine.isEmpty())
			{	
				dequeueLine(regLine1, 1);
				dequeueLine(regLine2, 2);
				dequeueLine(expressLine, 3);
				dequeueLine(expressLine, 3);
			}
		
			mainMenu(primaryStage);
		});
		
		option2.setOnAction(e -> {
			// reg2 first
			if (!regLine2.isEmpty() || !expressLine.isEmpty() || !regLine1.isEmpty())
			{
				dequeueLine(regLine2, 2);
				dequeueLine(expressLine, 3);
				dequeueLine(expressLine, 3);
				dequeueLine(regLine1, 1);
			}
			
			mainMenu(primaryStage);
		});
		
		option3.setOnAction(e -> {
			// express first
			if (!regLine1.isEmpty() || !regLine2.isEmpty() || !expressLine.isEmpty())
			{
				dequeueLine(expressLine, 3);
				dequeueLine(regLine1, 1);
				dequeueLine(regLine2, 2);
				dequeueLine(expressLine, 3);
			}
			
			mainMenu(primaryStage);
		});
		
		serveCustomer.getChildren().addAll(title, whichFirst, option1, option2, option3);
		serveCustomer.setAlignment(Pos.CENTER);
		Scene menuScene = new Scene(serveCustomer, 550, 500);
		window.setScene(menuScene);
	}
	
	// dequeue the first of given line
	public void dequeueLine(RegTicketLine line, int type)
	{
		Family current = line.peek();
		
		// if the line is empty, return
		if (current == null)
			return;
				
		boolean isSeated = false;
		String lineType = "line";
		
		// determine type of line for alertbox
		if (type == 1)
			lineType = "Reg1";
		if (type == 2)
			lineType = "Reg2";
		if (type == 3)
			lineType = "Express";
		
		current = line.dequeue();
		
		//System.out.println(type + ", Family: " + current.getName() + ", inLine: " + current.isInLine());
		
		// if someone left the line, account for it
		while (current.isInLine() ==  false) 
		{
			current = line.dequeue();
		}
		
		// serve the next person in line
		if (current.isInLine() == true)
		{
			current.setInLine(false);
			int movieChoice = current.getMovieChoice();
			
			//System.out.println("The " + current.getName() + " family has " + current.getAdultSize() + " adults and " + current.getChildrenSize() + " children.");
			//System.out.println("seatsLef1: " + seatsLeft1 + ", seatsLeft2: " + seatsLeft2);
			// see if there is any seats left for their first choice movie
			if (movieChoice == 1 && seatsLeft1 >= (current.getChildrenSize()+current.getAdultSize()))
			{
				isSeated = seated(theaterOne, current);
				if (isSeated == true)
				{
					seatsLeft1 -= current.getAdultSize() + current.getChildrenSize();
					stack.push(current.getChildrenSize(), current.getAdultSize());
				}
			}
			
			if (movieChoice == 2 && seatsLeft2 >= (current.getChildrenSize()+current.getAdultSize()))
			{
				isSeated = seated(theaterTwo, current);
				if (isSeated == true)
				{
					seatsLeft2 -= current.getAdultSize() + current.getChildrenSize();
					stack.push(current.getChildrenSize(), current.getAdultSize());
				}
			}
			
			// ask if they want to see the other movie
			if (isSeated == false)
			{
				boolean input = Confirm.askChoice("There is not enough seating for that movie.  Would you like to see the other one?");
				if (input == true)
				{
					if (movieChoice == 1 && seatsLeft2 >= (current.getChildrenSize()+current.getAdultSize()))
					{
						isSeated = seated(theaterTwo, current);
						if (isSeated == true)
						{
							seatsLeft2 -= current.getAdultSize() + current.getChildrenSize();
							stack.push(current.getChildrenSize(), current.getAdultSize());
						}
					}
					else
					{
						if (seatsLeft1 >= current.getChildrenSize()+current.getAdultSize())
						{
							isSeated = seated(theaterOne, current);
							if (isSeated == true)
							{
								seatsLeft1 -= current.getAdultSize() + current.getChildrenSize();
								stack.push(current.getChildrenSize(), current.getAdultSize());
							}
						}
					}
						
					// if still not seated
					if (isSeated == false)
						Confirm.display("Theater Full", "Sorry, theater is full.");
					else
						Confirm.display("Success", current.getName() + " was served");
				}
				else
				{
					Confirm.display("Sorry", "Sorry!  Have a nice day!");
				}
			}
			else
			{
				Confirm.display(current.getName() + " served", "The party " + current.getName() + " has been served from the " + lineType + " line.");
			}
		}

	}
	
	
	public boolean seated(Family [][] theater, Family current)
	{
		int count = 0;
		
		for (int row = 0; row < theater.length; row++)
		{
			for (int col = 0; col < theater[0].length; col++)
			{
				if (theater[row][col] == null)
				{
					current.addSeats(row, col);
					theater[row][col] = current;
					count++;
				}
				
				if (count == (current.getAdultSize() + current.getChildrenSize()))
						return true;
			}
		}
		
		return false;
	}
	
	// party leaves the theater
	private void leaveTheater(Stage primaryStage)
	{
		GridPane leaveTheaterGrid = new GridPane();
		leaveTheaterGrid.setPadding(new Insets(20, 20, 20, 20));
		leaveTheaterGrid.setVgap(12);
		leaveTheaterGrid.setHgap(10);
		
		Text title = new Text("3. Customer(s) leave(s) the movie theater.");
		
		GridPane.setConstraints(title, 0, 0);
		
		Label partyName = new Label("Name of party leaving:");
		GridPane.setConstraints(partyName, 0, 1);
		TextField partyNameInput = new TextField();
		GridPane.setConstraints(partyNameInput, 1, 1);
		
		Button submit = new Button("Submit");
		GridPane.setConstraints(submit, 1, 2);
		
		submit.setOnAction(e -> {
			String familyName = partyNameInput.getText();
			int [][] seats;
			Family actualFam = null;
			for (int i = 0; i < families.size(); i++)
			{
				if (families.get(i).getName().equals(familyName)) {
					actualFam = families.get(i);
					families.remove(i);
					actualFam.setInLine(false);
				}
			}
			
			if (actualFam != null)
			{
				if (actualFam.isInLine() == true)
				{
					
				}
				
				if (actualFam.getSeatNumbers()[0][0] == -1)
				{
					seats = actualFam.getSeatNumbers();
					Confirm.display("Successful", "The party " + familyName + " has left the theater.");
				}
				// remove family from theater 1 if they're in there
				else if (actualFam.getMovieChoice() == 1)
				{	
					for (int i = 0; i < actualFam.getSeatNumbers().length; i++)
					{
						seats = actualFam.getSeatNumbers();
						// empty the spots where they sat
						theaterOne[seats[i][0]][seats[i][1]] = null;
					}
					Confirm.display("Successful", "The party " + familyName + " has left the theater.");
				}
				// remove family from theater 2 if they're in there
				else 
				{
					for (int i = 0; i < actualFam.getSeatNumbers().length; i++)
					{
						seats = actualFam.getSeatNumbers();
						// empty the spots where they sat
						theaterTwo[seats[i][0]][seats[i][1]] = null;
					}
					Confirm.display("Successful", "The party " + familyName + " has left the theater.");
				}
			}
			else
			{
				Confirm.display("Party Not Found", "The party " + familyName + " is not in the theater.");
			}
			
			mainMenu(primaryStage);
					
		});


	leaveTheaterGrid.getChildren().addAll(partyName, partyNameInput, submit);
	leaveTheaterGrid.setAlignment(Pos.CENTER);
	Scene menuScene = new Scene(leaveTheaterGrid, 550, 500);
	window.setScene(menuScene);
	}
	
	// print info about families in line
	private void printLineInfo(Stage primaryStage)
	{
		VBox customers = new VBox();
		customers.setAlignment(Pos.CENTER);
		ScrollPane info = new ScrollPane();
		
		Label firstLine = new Label("RegLine1:\n\n");
		VBox regLine1Print = regLine1.printQueue();
		regLine1Print.setAlignment(Pos.CENTER);
		customers.getChildren().addAll(firstLine, regLine1Print);
		
		Label secondLine = new Label("RegLine2:\n\n");
		VBox regLine2Print = regLine2.printQueue();
		regLine2Print.setAlignment(Pos.CENTER);
		customers.getChildren().addAll(secondLine, regLine2Print);
		
		Label thirdLine = new Label("Express Line:\n\n");
		VBox expressLinePrint = expressLine.printQueue();
		expressLinePrint.setAlignment(Pos.CENTER);
		customers.getChildren().addAll(thirdLine, expressLinePrint);
		
		Button ok = new Button("Ok");
		customers.getChildren().add(ok);
		ok.setOnAction(e -> mainMenu(primaryStage));
		
		//customers.maxHeight(Double.POSITIVE_INFINITY);
		customers.setPrefSize(550, 500);
		info.setContent(customers);
		info.setFitToWidth(true);
		Scene menuScene = new Scene(info, 550, 500);
		window.setScene(menuScene);
	}
	
	// print info about families in line
	private void printTheater(Stage primaryStage, Family [][] theater)
	{
		GridPane setTheater = new GridPane();
		setTheater.setPadding(new Insets(10));
		setTheater.setVgap(12);
		setTheater.setHgap(10);
		Button temp;
		
		Button back = new Button("Back");
		back.setOnAction(e -> mainMenu(primaryStage));
		setTheater.add(back, 0, 0);
		
		for (int col1 = 0; col1 < theater[0].length; col1++)
		{
			for (int row1 = 0; row1 < theater.length; row1++)
			{
				if (theater[row1][col1] != null)
					temp = new Button(theater[row1][col1].getName());
				else
					temp = new Button("Empty");
				setTheater.add(temp, col1, row1+1);
			}
		}

		
		setTheater.setAlignment(Pos.CENTER);
		setTheater.maxHeight(Double.POSITIVE_INFINITY);
		ScrollPane theaterScreen = new ScrollPane();
		theaterScreen.setContent(setTheater);
		
		Scene menuScene = new Scene(theaterScreen, 550, 500);
		window.setScene(menuScene);
	}
	
	public void ticketsSold(Stage primaryStage)
	{
		//System.out.println("Sold children: " + stack.soldChildren() + ", Sold Price: " + childPrice);
		//System.out.println("Sold adults: " + stack.soldAdults() + ", Sold Price: " + adultPrice);
		double adultTotal = stack.soldAdults()*adultPrice;
		double childrenTotal = stack.soldChildren()*childPrice;
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		VBox soldInfo = new VBox(5);
		stack.printReceipt();
		
		Text ticketsSold = new Text("Today we have sold:\nAdult Tickets: " + stack.soldAdults() +
				"\nChildren Tickets: " + stack.soldChildren() + 
				"\nAdult Total: " + formatter.format(adultTotal) +
				"\nChildren Total: " + formatter.format(childrenTotal) +
				"\nOverall Total: " + formatter.format(adultTotal + childrenTotal));
		
		Button back = new Button("Back");
		
		back.setOnAction(e -> mainMenu(primaryStage));
		soldInfo.getChildren().addAll(ticketsSold, back);
		soldInfo.setAlignment(Pos.CENTER);
		Scene menuScene = new Scene(soldInfo, 550, 500);
		window.setScene(menuScene);
	}
	
	// close the theater
	private void exit(Stage primaryStage)
	{
		//System.out.println("Theater is now closed");

		// clear all the theater matrixes
		for (int col1 = 0; col1 < theaterOne[0].length; col1++)
		{
			for (int row1 = 0; row1 < theaterOne.length; row1++)
			{
				theaterOne[row1][col1] = null;
			}
		}
		
		for (int col1 = 0; col1 < theaterTwo[0].length; col1++)
		{
			for (int row1 = 0; row1 < theaterTwo.length; row1++)
			{
				theaterTwo[row1][col1] = null;
			}
		}
		
		//clear all the ticket lines
		regLine1.empty();
		regLine2.empty();
		expressLine.empty();

		closeProgram();
		//mainMenu(primaryStage); // used for debugging
	}
	
	
	// exit the program properly
	private void closeProgram()
	{
		//System.out.println("Exited");
		window.close();
	}

}
