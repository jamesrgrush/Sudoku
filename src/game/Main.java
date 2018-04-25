package game;
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
	VBox root = new VBox();
	HBox boardAndControls = new HBox();
	HBox numbers = new HBox();
	VBox controls = new VBox();
	GridPane board = new GridPane();
	
	int numSelected;
	
	public static void main(String[] args) {
		Application.launch();

	}
	
		

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Button easyDif = new Button("Easy");
		easyDif.setOnAction(e -> {
			showPuzzle(0);
		});
		Button medDif = new Button("Medium");
		medDif.setOnAction(e -> {
			showPuzzle(1);
		});
		Button hardDif = new Button("Hard");
		hardDif.setOnAction(e -> {
			showPuzzle(2);
		});
		Button num1 = new Button("1");
		num1.setOnAction(e -> {
			numSelected = 1;
		});
		Button num2 = new Button("2");
		num1.setOnAction(e -> {
			numSelected = 2;
		});
		Button num3 = new Button("3");
		num1.setOnAction(e -> {
			numSelected = 3;
		});
		Button num4 = new Button("4");
		num1.setOnAction(e -> {
			numSelected = 4;
		});
		Button num5 = new Button("5");
		num1.setOnAction(e -> {
			numSelected = 5;
		});
		Button num6 = new Button("6");
		num1.setOnAction(e -> {
			numSelected = 6;
		});
		Button num7 = new Button("7");
		num1.setOnAction(e -> {
			numSelected = 7;
		});
		Button num8 = new Button("8");
		num1.setOnAction(e -> {
			numSelected = 8;
		});
		Button num9 = new Button("9");
		num1.setOnAction(e -> {
			numSelected = 9;
		});
		
		

		controls.getChildren().add(new Label("Select Difficulty:"));
		controls.getChildren().addAll(easyDif, medDif, hardDif);
		controls.getChildren().add(new Label("Game Controls:"));
		controls.getChildren().add(new Button("Check Answers"));
		controls.getChildren().add(new Button("Hint"));
		
		numbers.getChildren().add(new Label("Select Number:"));
		numbers.getChildren().addAll(num1, num2, num3, num4, num5, num6, num7, num8, num9);
		
		boardAndControls.getChildren().addAll(board, controls);
		root.getChildren().addAll(boardAndControls, numbers);
		
		primaryStage.setScene(new Scene(root, 500, 300));
		primaryStage.show();
	}
	
	public void setGridNums(int[][] boardNums) {
		for (int metaColumn = 0; metaColumn < 3 ; metaColumn++) {
		    for (int metaRow = 0; metaRow < 3; metaRow++) {

		        GridPane box = new GridPane();
		        box.setStyle("-fx-background-color: black, -fx-control-inner-background; -fx-background-insets: 0, 2; -fx-padding: 2;");
		        for (int column = 0; column < 3; column++) {
		            for (int row = 0 ; row < 3; row++) {
		                Button boardCell = new Button();
		                if(boardNums[row + (metaRow * 3)][column + (metaColumn * 3)] == 0) {
		                	boardCell.setText("");
		                }
		                else {
		                	boardCell.setText(Integer.toString(boardNums[row + (metaRow * 3)][column + (metaColumn * 3)]));
		                }
		                boardCell.setAlignment(Pos.CENTER);
		                boardCell.setStyle("-fx-pref-width: 3em; -fx-pred-height: 3em");
		                GridPane.setConstraints(boardCell, column, row);
		                boardCell.setOnAction(e -> {
		                	boardCell.setText(Integer.toString(numSelected));
		                	grid.getCell((box.getRowIndex(boardCell) + (board.getRowIndex(boardCell) * 3)), (box.getColumnIndex(boardCell) + (board.getColumnIndex(boardCell) * 3))).setPlayerNumber(numSelected);
		                });
		                box.getChildren().add(boardCell);
		            }
		        }

		        GridPane.setConstraints(box, metaColumn, metaRow);
		        board.getChildren().add(box);

		    }
		}
	}
	
	public void showPuzzle(int difficulty) {
		Grid grid = new Grid();
		grid.populateGrid();
		//grid.TrimGrid(difficulty);
		setGridNums(grid.getGridNums());
	}

}
