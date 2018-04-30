package game;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Grid {
    
	private static Cell[][] gridCells = new Cell[9][9];
    private static Cell[][] copyGrid = new Cell[9][9];
    private Stack<Cell> backtrackLog = new Stack<Cell>();
    Random ran = new Random();
   
    //Creates a cell object for each section of the 9x9 grid
    Grid() {
       for(int row = 0; row < 9; row++) {
           for (int column = 0; column < 9; column++) {
               gridCells[row][column] = new Cell(row, column);
            }
        }
    }
   
    //Fills the grid with valid numbers
    public void populateGrid() {
    	
    	//Must specify cell to start with
    	Cell bestCell = gridCells[0][0];
    	int posVal;
    	
    	while(true) {
    		//When a cell is returned with row = 10 then that signals that the grid is full and the loop breaks
    		if (bestCell.getRow() == 10) {
    			break;
    		}
    		
    		if (bestCell.getNumOfCandidates() >= 1) {
    			posVal = bestCell.getRandomCandidate();
    		}
    		
    		//If there are no possible candidates for the cell then it gets all its candidates back and the last cell used becomes the bestCell
    		else {
    			bestCell.updateRenew();
    			bestCell = backtrackLog.pop();
    			//End  the current iteration of the loop since a new bestCell has been set
    			continue;
    		}
    		
    		//If the number chosen for the cell is valid, its number is set, the grid is updated, the cell is added to the stack, and a new bestCell is found
    		if (checkGrid(bestCell, posVal) == true) {
    			bestCell.setNumber(posVal);
    			updateGrid(bestCell);
    			backtrackLog.push(bestCell);
    			bestCell = findBestCell();
    		}
    		
    		//If the number isn't valid then the number is removed from that cells list of candidates
    		else {
    			bestCell.updateRemove(bestCell.getNumber());
    		}
    			
    			
    	}
    }
   
    //Returns the cell with the fewest valid candidates
    public Cell findBestCell() {
        int bestRow = 9;
        int bestColumn = 9;
        int fewestCandidates = 10;
 
        for(int row = 0; row < 9; row++) {
        	for(int column = 0; column < 9; column++) {
        		//Checks that the specified cell is empty before comparing values
        		if(gridCells[row][column].isEmpty() == true && gridCells[row][column].getNumOfCandidates() < fewestCandidates) {
            		bestRow = row;
            		bestColumn = column;
            		fewestCandidates = gridCells[row][column].getNumOfCandidates();
            	}
            }
        }
        
        try {
        return gridCells[bestRow][bestColumn];
        }
        //Returns cell with row = 10 if the grid is full
        catch (ArrayIndexOutOfBoundsException ex) {
        	Cell fullGrid = new Cell(10, 10);
        	return fullGrid;
        }
    }
    
    //Updates all the relevant cell's candidate list after a new number is placed
    public static void updateGrid(Cell cell) {
    	for(int row = 0; row < 9; row++) {
    		if(gridCells[row][cell.getColumn()] != cell) {
    			gridCells[row][cell.getColumn()].updateRemove(cell.getNumber());
    		}
    		
    		for(int column = 0; column < 9; column++) {
    			if(gridCells[cell.getRow()][column] != cell) {
    				gridCells[cell.getRow()][column].updateRemove(cell.getNumber());
    			}
    			
    			if (gridCells[row][column].getMetaRow() == cell.getMetaRow() && gridCells[row][column].getMetaColumn() == cell.getMetaColumn() && gridCells[row][column] != cell) {
    				gridCells[row][column].updateRemove(cell.getNumber());
    			}
            }
        }
    }
    
    //Checks if a number is a valid placement on the board
    public static boolean checkGrid(Cell newCell, int posVal) {
    	for (int row = 0; row < 9; row++) {
    		if (gridCells[row][newCell.getColumn()] != newCell && gridCells[row][newCell.getColumn()].getNumber() == posVal) {
    			return false;
    		}
    		
    		for (int column = 0; column < 9; column++) {
    			if (gridCells[newCell.getRow()][column] != newCell && gridCells[newCell.getRow()][column].getNumber() == posVal) {
    				return false;
    			}
    			
    			if (gridCells[row][column] != newCell && gridCells[row][column].getMetaRow() == newCell.getMetaRow() && gridCells[row][column].getMetaColumn() == newCell.getMetaColumn() && gridCells[row][column].getNumber() == posVal) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    //Updates cell's candidate list after a number has been removed
    public static void updateTrim(Cell trimmedCell, int oldNum) {
    	for(int row = 0; row < 9; row++) {
    		if(gridCells[row][trimmedCell.getColumn()] != trimmedCell) {
    			gridCells[row][trimmedCell.getColumn()].updateAdd(oldNum);
    		}
    		
    		for(int column = 0; column < 9; column++) {
        		if(gridCells[trimmedCell.getRow()][column] != trimmedCell) {
        			gridCells[trimmedCell.getRow()][column].updateAdd(oldNum);
        		}
    			
    			if (gridCells[row][column].getMetaRow() == trimmedCell.getMetaRow() && gridCells[row][column].getMetaColumn() == trimmedCell.getMetaColumn() && gridCells[row][column] != trimmedCell) {
    				gridCells[row][column].updateAdd(oldNum);
    			}
            }
        }
    }
    
    //Returns a 2D int array of the current numbers of all Cells
    public int[][] getGridNums() {
    	int[][] copyPuzzleNums = new int[9][9];
    	for(int row = 0; row < 9; row++) {
    		for(int column = 0; column < 9; column++) {
    			copyPuzzleNums[row][column] = gridCells[row][column].getNumber();
    		}
    	}
    	return copyPuzzleNums;
    }
    
    //Returns the cell at the specified row and column from the grid
    public Cell getCell(int row, int column) {
    	return gridCells[row][column];
    }
    
    //Sets number of cells to be removed from the grid
    public void trimGrid(int difficulty) {
    	int removeCount = 0;
    	switch(difficulty) {
    		case 0: removeCount = 1;
    		break;
    		case 1: removeCount = 20;
    		break;
    		case 2: removeCount = 25;
    		break;
    	}
    	
    	copyGrid = gridCells.clone();
    	findRemove(removeCount);
    }
    
    //Finds cell to remove after checking for unique solution
    public int findRemove(int count) {
    	if(count == 0) {
    		return 0;
    	}
    	
    	//Get a random cell
    	Cell removeCell = gridCells[ran.nextInt(9)][ran.nextInt(9)];
    	//If that cell isn't empty then call method again with the same count
    	if(removeCell.isEmpty() == true) {
    		return findRemove(count);
    	}
    	
    	//Hold the current cells number before setting it to 0 and make a copy of gridCells so the original grid can remain intact
    	int holdNum = removeCell.getNumber();
    	removeCell.setNumber(0);
    	copyGrid = gridCells.clone();
    	//If there is a unique solution then update the grid and its copy, and call the method again with 1 less count
    	if (numOfSol(0,0,0) == 1) {
        	updateTrim(removeCell, holdNum);
        	copyGrid = gridCells.clone();
        	return findRemove(count - 1);
    	}
    	
    	//If there are multiple solutions then replace the cells original number and call the method with the same count
    	else {
    		removeCell.setNumber(holdNum);
    		return findRemove(count);
    	}
    }
    
    //Count the number of unique solutions
    public static int numOfSol(int row, int column, int countSol) {
    	//If the column is at the max index, set it to 0 and increase row
    	if (column == 9) {
    		column = 0;
    		++row;
    		//If the row is at the max index, a full solution was found and the solution count is increased
    		if(row == 9) {
    			return countSol + 1;
    		}
    	}
    	
    	//Skip empty cells
    	if(copyGrid[row][column].isEmpty() == false) {
    		return numOfSol(row, column + 1, countSol);
    	}
    	
    	//If the number is valid then set the number and continue to the next cell
    	for(int i = 1; i <= 9 && countSol < 2; i++) {
    		if(checkGrid(copyGrid[row][column], i) == true) {
    			copyGrid[row][column].setNumber(i);
    			countSol = numOfSol(row, column + 1, countSol);
    		}
    	}
    	//When backtracking set the number back to 0 and return the current solution count
    	copyGrid[row][column].setNumber(0);
    	return countSol;
    }
    
    //Checks the entire grid for invalid number placements
    public boolean checkAns() {
    	if (checkColAndRows() == true && checkMetaBox() == true) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public boolean checkColAndRows() {
    	//These store the current values of the numbers in each row/column
    	ArrayList<Integer> rowVals = new ArrayList<Integer>();
    	ArrayList<Integer> columnVals = new ArrayList<Integer>();
    	
    	for (int row = 0; row < 9; row++) {
    		//Clear the ArrayList when each row/column is fully processed
    		rowVals.clear();
    		columnVals.clear();
    		
    		for (int column = 0; column < 9; column++) {
    			//If the ArrayList already contains the number then the grid is invalid
    			if (rowVals.contains(gridCells[row][column].getNumber())) {
    				return false;
    			}
    			else {
    				rowVals.add(gridCells[row][column].getNumber());
    			}
    			
    			//To check rows and columns simultaneously, their values are fliped 
    			if (columnVals.contains(gridCells[column][row].getNumber())) {
    				return false;
    			}
    			else {
    				columnVals.add(gridCells[column][row].getNumber());
    			}
    		}
    	}
    	return true;
    }
    
    //Checks for invalid numbers in the meta 3x3 grid
    public boolean checkMetaBox() {
    	
    	ArrayList<Integer> boxVals = new ArrayList<Integer>();
    	for (int countMetaRow = 0; countMetaRow < 3; countMetaRow++) {
    		
    		for (int countMetaColumn = 0; countMetaColumn < 3; countMetaColumn++) {
    			boxVals.clear();
    			
    			for (int row = 0 + (countMetaRow * 3); row < 3 + (countMetaRow * 3); row++) {
    		
    				for (int column = 0 + (countMetaColumn * 3); column < 3 + (countMetaColumn * 3); column++) {
    						
    					if (boxVals.contains(gridCells[row][column].getNumber())) {
    						return false;
    					}
    					else {
    						boxVals.add(gridCells[row][column].getNumber());
    					}
    				}
    			}
    		}
    	}
    	return true;
    }
    
    //Finds the first empty cell and returns it
    public Cell getHint() {
    	for (int row = 0; row < 9; row++) {
    		for (int column = 0; column < 9; column++) {
    			if (gridCells[row][column].isEmpty()) {
    				return gridCells[row][column];
    			}
    		}
    	}
    	Cell fullGridCell = new Cell(10, 10);
    	return fullGridCell;
    }
}