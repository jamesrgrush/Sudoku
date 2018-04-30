package game;
import java.util.Random;
import java.util.Stack;

public class Grid {
    
	private static Cell[][] gridCells = new Cell[9][9];
    private static Cell[][] copyGrid = new Cell[9][9];
    private Stack<Cell> backtrackLog = new Stack<Cell>();
    Random ran = new Random();
   
    Grid() {
       for(int row = 0; row < 9; row++) {
           for (int column = 0; column < 9; column++) {
               gridCells[row][column] = new Cell(row, column);
            }
        }
    }
   
    public void populateGrid() {
    	
    	Cell bestCell = gridCells[0][0];
    	int posVal;
    	
    	while(true) {
    		if (bestCell.getRow() == 10) {
    			break;
    		}
    		
    		if (bestCell.getNumOfCandidates() >= 1) {
    			posVal = bestCell.getRandomCandidate();
    		}
    		
    		else {
    			bestCell.updateRenew();
    			bestCell = backtrackLog.pop();
    			continue;
    		}
    		
    		if (checkGrid(bestCell, posVal) == true) {
    			bestCell.setNumber(posVal);
    			updateGrid(bestCell);
    			backtrackLog.push(bestCell);
    			bestCell = findBestCell();
    		}
    		
    		else {
    			bestCell.updateRemove(bestCell.getNumber());
    		}
    			
    			
    	}
    }
   
    public Cell findBestCell() {
        int bestRow = 9;
        int bestColumn = 9;
        int fewestCandidates = 10;
 
        for(int row = 0; row < 9; row++) {
        	for(int column = 0; column < 9; column++) {
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
        
        catch (ArrayIndexOutOfBoundsException ex) {
        	Cell fullGrid = new Cell(10, 10);
        	return fullGrid;
        }
    }
    
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
    
    public int[][] getGridNums() {
    	/*int[][] copyPuzzleNums = new int[9][9];
    	for(int row = 0; row < 9; row++) {
    		for(int column = 0; column < 9; column++) {
    			copyPuzzleNums[row][column] = puzzleGrid[row][column];
    		}
    	}
    	return copyPuzzleNums;*/
    	int[][] copyPuzzleNums = new int[9][9];
    	for(int row = 0; row < 9; row++) {
    		for(int column = 0; column < 9; column++) {
    			copyPuzzleNums[row][column] = gridCells[row][column].getNumber();
    		}
    	}
    	return copyPuzzleNums;
    }
    
    public Cell getCell(int row, int column) {
    	return gridCells[row][column];
    }
    
    public void trimGrid(int difficulty) {
    	int removeCount = 0;
    	switch(difficulty) {
    		case 0: removeCount = 15;
    		break;
    		case 1: removeCount = 25;
    		break;
    		case 2: removeCount = 30;
    		break;
    	}
    	
    	copyGrid = gridCells.clone();
    	findRemove(removeCount);
    }
    
    public int findRemove(int count) {
    	if(count == 0) {
    		return 0;
    	}
    	
    	Cell removeCell = gridCells[ran.nextInt(9)][ran.nextInt(9)];
    	if(removeCell.isEmpty() == true) {
    		return findRemove(count);
    	}
    	
    	int holdNum = removeCell.getNumber();
    	removeCell.setNumber(0);
    	copyGrid = gridCells.clone();
    	if (numOfSol(0,0,0) == 1) {
        	updateTrim(removeCell, holdNum);
        	copyGrid = gridCells.clone();
        	return findRemove(count - 1);
    	}
    	
    	else {
    		removeCell.setNumber(holdNum);
    		return findRemove(count);
    	}
    }
    
    public static int numOfSol(int row, int column, int countSol) {
    	if (column == 9) {
    		column = 0;
    		++row;
    		if(row == 9) {
    			return countSol + 1;
    		}
    	}
    	
    	if(copyGrid[row][column].isEmpty() == false) {
    		return numOfSol(row, column + 1, countSol);
    	}
    	
    	for(int i = 1; i <= 9 && countSol < 2; i++) {
    		if(checkGrid(copyGrid[row][column], i) == true) {
    			copyGrid[row][column].setNumber(i);
    			countSol = numOfSol(row, column + 1, countSol);
    		}
    	}
    	
    	copyGrid[row][column].setNumber(0);
    	return countSol;
    }
}