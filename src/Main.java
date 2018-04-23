import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
		primaryStage.setScene(new Scene(board));
		primaryStage.show();
	}

}
