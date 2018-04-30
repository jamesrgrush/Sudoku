package game;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
	//root > numbers & boardAndControls > board & controls
	VBox root = new VBox();
	HBox boardAndControls = new HBox();
	HBox numbers = new HBox();
	VBox controls = new VBox();
	GridPane board = new GridPane();
	
	//Class level variables
	Grid grid;
	int[][] boardNums = new int[9][9];
	int[][] trimmedBoardNums = new int[9][9];
	int numSelected;
	
	public static void main(String[] args) {
		Application.launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Creating nodes with action events
		
		//Set difficulty of puzzle which determines how many numbers are removed
		Button easyDif = new Button("Easy");
		easyDif.setOnAction(ed -> {
			showPuzzle(0);
		});
		Button medDif = new Button("Medium");
		medDif.setOnAction(md -> {
			showPuzzle(1);
		});
		Button hardDif = new Button("Hard");
		hardDif.setOnAction(hd -> {
			showPuzzle(2);
		});
		
		//Sets the current selected number to the corresponding button so it can be placed in the grid
		Button num1 = new Button("1");
		num1.setOnAction(n1 -> {
			numSelected = 1;
		});
		Button num2 = new Button("2");
		num2.setOnAction(n2 -> {
			numSelected = 2;
		});
		Button num3 = new Button("3");
		num3.setOnAction(n3 -> {
			numSelected = 3;
		});
		Button num4 = new Button("4");
		num4.setOnAction(n4 -> {
			numSelected = 4;
		});
		Button num5 = new Button("5");
		num5.setOnAction(n5 -> {
			numSelected = 5;
		});
		Button num6 = new Button("6");
		num6.setOnAction(n6 -> {
			numSelected = 6;
		});
		Button num7 = new Button("7");
		num7.setOnAction(n7 -> {
			numSelected = 7;
		});
		Button num8 = new Button("8");
		num8.setOnAction(n8 -> {
			numSelected = 8;
		});
		Button num9 = new Button("9");
		num9.setOnAction(n9 -> {
			numSelected = 9;
		});
		
		//Button shows an additional window with feedback on their current grid's validity
		Button checkAns = new Button("Check Answers");
		checkAns.setOnAction(ca -> {
			VBox checkAnsLayout = new VBox();
			Stage secondaryStage = new Stage();
			if(grid.checkAns() == true) {
				Text checkAnsResult = new Text("All current numbers are valid. Good for you!");
				Button closeResults = new Button("Close");
				closeResults.setOnAction(cr -> {
					secondaryStage.close();
				});
				checkAnsLayout.getChildren().addAll(checkAnsResult, closeResults);
				secondaryStage.setScene(new Scene(checkAnsLayout, 100, 200));
				secondaryStage.show();	
			}
			
			else {
				Text checkAnsResult = new Text("One or more of your numbers are incorrect. I'd tell you which is wrong, but what would be the fun in that?");
				Button closeResults = new Button("Close");
				closeResults.setOnAction(cr -> {
					secondaryStage.close();
				});
				checkAnsLayout.getChildren().addAll(checkAnsResult, closeResults);
				secondaryStage.setScene(new Scene(checkAnsLayout, 200, 200));
				secondaryStage.show();	
			}
		});
		
		//Button shows an additional window with the location and value of an empty cell
		Button hint = new Button("Hint");
		hint.setOnAction(h -> {
			Cell hintCell = grid.getHint();
			VBox hintLayout = new VBox();
			Stage hintStage = new Stage();
			if (hintCell.getRow() == 10) {
				Text hintText = new Text("There are no cells to give a hint for. Use the Check Answers button to see if they're correct!");
				Button close = new Button("Close");
				close.setOnAction(ch -> {
					hintStage.close();
				});
				hintLayout.getChildren().addAll(hintText, close);
				hintStage.setScene(new Scene(hintLayout, 200, 200));
				hintStage.show();
			}
			else {
				Text hintText = new Text("The answer to the cell on row: " + (hintCell.getRow() + 1) + " and column: " + (hintCell.getColumn() +1) + " is: " + boardNums[hintCell.getRow()][hintCell.getColumn()]);
				Button close = new Button("Close");
				close.setOnAction(ch -> {
					hintStage.close();
				});
				hintLayout.getChildren().addAll(hintText, close);
				hintStage.setScene(new Scene(hintLayout, 200, 200));
				hintStage.show();
			}
		});
		
		//Adds buttons to corresponding panes
		controls.getChildren().add(new Label("Select Difficulty:"));
		controls.getChildren().addAll(easyDif, medDif, hardDif);
		
		controls.getChildren().add(new Label("Game Controls:"));
		controls.getChildren().addAll(checkAns, hint);
		
		numbers.getChildren().add(new Label("Select Number:"));
		numbers.getChildren().addAll(num1, num2, num3, num4, num5, num6, num7, num8, num9);
		
		//Combines board and controls, and boardAndControls and numbers
		boardAndControls.getChildren().addAll(board, controls);
		root.getChildren().addAll(boardAndControls, numbers);
		
		//primaryStage is shown with all elements added
		primaryStage.setScene(new Scene(root, 500, 300));
		primaryStage.setTitle("Sudoku Final Project");
		primaryStage.show();
	}
	
	//Places the grid's numbers into the JavaFX grid
	public void setGridNums(int[][] trimmedBoardNums) {
		//Stores copy of trimmed grid
		this.trimmedBoardNums = trimmedBoardNums;
		
		for (int metaColumn = 0; metaColumn < 3 ; metaColumn++) {
		    for (int metaRow = 0; metaRow < 3; metaRow++) {
		    	
		    	//Creates a GridPane for the meta 3x3 grid
		        GridPane box = new GridPane();
		        box.setStyle("-fx-background-color: black, -fx-control-inner-background; -fx-background-insets: 0, 2; -fx-padding: 2;");
		        
		        for (int column = 0; column < 3; column++) {
		            for (int row = 0 ; row < 3; row++) {
		            	
		            	//Creates the individual 3x3 grid as buttons inside the meta 3x3 grid
		                Button boardCell = new Button();
		                
		                //Places numbers from the 2D int array in the corresponding buttons
		                if(trimmedBoardNums[row + (metaRow * 3)][column + (metaColumn * 3)] == 0) {
		                	boardCell.setText("");
		                }
		                else {
		                	boardCell.setText(Integer.toString(trimmedBoardNums[row + (metaRow * 3)][column + (metaColumn * 3)]));
		                }
		                
		                //Set style for buttons
		                boardCell.setAlignment(Pos.CENTER);
		                boardCell.setStyle("-fx-pref-width: 3em; -fx-pred-height: 3em");
		                GridPane.setConstraints(boardCell, column, row);
		                //When buttons are selected their number/text is changed to the currently selected number
		                boardCell.setOnAction(bc -> {
		                	boardCell.setText(Integer.toString(numSelected));
		                	grid.getCell((GridPane.getRowIndex(boardCell) + (GridPane.getRowIndex(box) * 3)), (GridPane.getColumnIndex(boardCell) + (GridPane.getColumnIndex(box) * 3))).setNumber(numSelected);
		                });
		                
		                //Each cell is added to the grid
		                box.getChildren().add(boardCell);
		            }
		        }
		        //Each 3x3 box is added to the 3x3 meta grid
		        GridPane.setConstraints(box, metaColumn, metaRow);
		        board.getChildren().add(box);
		    }
		}
	}
	
	//Makes the grid, creates a copy of all the correct numbers, trims off numbers to create puzzle, and applies the numbers to the JavaFX grid
	public void showPuzzle(int difficulty) {
		grid = new Grid();
		grid.populateGrid();
		boardNums = grid.getGridNums();
		grid.trimGrid(difficulty);
		setGridNums(grid.getGridNums());
	}

}
