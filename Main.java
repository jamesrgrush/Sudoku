import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
	Grid grid;
	public static void main(String[] args) {
		Application.launch();

	}
	
		

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		VBox root = new VBox();
		HBox boardAndControls = new HBox();
		GridPane board = new GridPane();
		
		


		for (int blockColumn = 0; blockColumn < 3 ; blockColumn++) {
		    for (int blockRow = 0; blockRow < 3; blockRow++) {

		        GridPane box = new GridPane();
		        box.setStyle("-fx-background-color: black, -fx-control-inner-background; -fx-background-insets: 0, 2; -fx-padding: 2;");
		        for (int column = 0; column < 3; column++) {
		            for (int row = 0 ; row < 3; row++) {
		                Label cellNumber = new Label("0");
		                cellNumber.setAlignment(Pos.CENTER);
		                cellNumber.setStyle("-fx-pref-width: 2em;");
		                GridPane.setConstraints(cellNumber, column, row);
		                box.getChildren().add(cellNumber);
		            }
		        }

		        GridPane.setConstraints(box, blockColumn, blockRow);
		        board.getChildren().add(box);

		    }
		}
		boardAndControls.getChildren().add(board);
		boardAndControls.getChildren().add(new Label("Select Difficulty:"));
		boardAndControls.getChildren().add(new Button("Easy"));
		boardAndControls.getChildren().add(new Button("Medium"));
		boardAndControls.getChildren().add(new Button("Hard"));
		boardAndControls.getChildren().add(new Label("Game Controls:"));
		boardAndControls.getChildren().add(new Button("Check Answers"));
		boardAndControls.getChildren().add(new Button("Hint"));
		boardAndControls.getChildren().add(new Label("Select Number:"));
		boardAndControls.getChildren().add(new Button("1"));
		boardAndControls.getChildren().add(new Button("2"));
		boardAndControls.getChildren().add(new Button("3"));
		boardAndControls.getChildren().add(new Button("4"));
		boardAndControls.getChildren().add(new Button("5"));
		boardAndControls.getChildren().add(new Button("6"));
		boardAndControls.getChildren().add(new Button("7"));
		boardAndControls.getChildren().add(new Button("8"));
		boardAndControls.getChildren().add(new Button("9"));









		
		root.getChildren().add(boardAndControls);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

}
