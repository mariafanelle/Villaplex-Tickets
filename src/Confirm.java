import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 * Confirm.java
 * 
 * 1. Confirms if user input is correct.
// 2. Provides pop-up messages
 */

public class Confirm {
	
	static boolean answer;

	public static void display (String header, String message)
	{
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(header);
		window.setMinWidth(250);
		Label label = new Label();
		label.setText(message);
		
		Button close = new Button("Close");
		close.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, close);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	
	public static boolean askChoice (String message)
	{
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Theater Full");
		window.setMinWidth(250);
		
		Label ask = new Label();
		ask.setText(message);
		
		Button yes = new Button("Yes");
		Button no = new Button("No");
		
		yes.setOnAction(e -> {
			answer = true;
			window.close();
		});

		no.setOnAction(e -> {
			answer = false;
			window.close();
		});
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(ask, yes, no);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}
	
	// check if textfield input is an int
	public static boolean isInt(TextField input)
	{
		try
		{
			int num = Integer.parseInt(input.getText());
			//System.out.println("It is an integer number.");
			return true;
		} catch (NumberFormatException e) 
		{
			display("Error", "Please change your input to an integer value.");
			return false;
		}
	}

	// check if textfield input can be a floating point number
	public static boolean isFloat(TextField input)
	{
		try
		{
			double num = Double.parseDouble(input.getText());
			//System.out.println("It is a floating point number.");
			return true;
		} catch (NumberFormatException e) 
		{
			Confirm.display("Error", "Please change your input to a floating point value.");
				return false;
			}
	}
}
