import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main {

	public static void main(String[] args) {
		Grid grid = new Grid();
		grid.populateGrid();
		Cell[][] copyGrid = grid.getGrid();
		
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				System.out.print(copyGrid[row][column].getNumber());
			}
			System.out.println();
		}

	}
	
	public class ViewGame extends Application {

		@Override
		public void start(Stage primaryStage) throws Exception {
			GridPane sudoku = new GridPane();
			sudoku.setAlignment(Pos.CENTER);
			
		}
		
	}

}
