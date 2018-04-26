package game;
import java.util.Random;
import java.util.Stack;

public class Grid {
    
    private int[][] puzzleGrid = new int[9][9];
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
    	
    	while(true) {
    		if (bestCell.getRow() == 10) {
    			break;
    		}
    		
    		if (bestCell.getNumOfCandidates() > 0) {
    			bestCell.setNumber(bestCell.getRandomCandidate());
    		}
    		
    		else {
    			bestCell.updateRenew();
    			bestCell = backtrackLog.pop();
    			continue;
    		}
    		
    		if (checkGrid(bestCell) == true) {
    			updateGrid(bestCell);
    			backtrackLog.push(bestCell);
    			bestCell = findBestCell();
    		}
    		
    		else {
    			bestCell.updateRemove(bestCell.getNumber());
    			bestCell.setNumber(0);
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
    
    public void updateGrid(Cell cell) {
    	for(int row = 0; row < 9; row++) {
    		gridCells[row][cell.getColumn()].updateRemove(cell.getNumber());
    		
    		for(int column = 0; column < 9; column++) {
    			gridCells[cell.getRow()][column].updateRemove(cell.getNumber());
    			
    			if (gridCells[row][column].getMetaRow() == cell.getMetaRow() && gridCells[row][column].getMetaColumn() == cell.getMetaColumn()) {
    				gridCells[row][column].updateRemove(cell.getNumber());
    			}
            }
        }
    }
    
    public boolean checkGrid(Cell newCell) {
    	boolean returnValue = true;
    	
    	for (int row = 0; row < 9; row++) {
    		if (gridCells[row][newCell.getColumn()] != newCell && gridCells[row][newCell.getColumn()].getNumber() == newCell.getNumber()) {
    			return false;
    		}
    		
    		for (int column = 0; column < 9; column++) {
    			if (gridCells[newCell.getRow()][column] != newCell && gridCells[newCell.getRow()][column].getNumber() == newCell.getNumber()) {
    				return false;
    			}
    			
    			if (gridCells[row][column] != newCell && gridCells[row][column].getMetaRow() == newCell.getMetaRow() && gridCells[row][column].getMetaColumn() == newCell.getMetaColumn() && gridCells[row][column].getNumber() == newCell.getNumber()) {
    				return false;
    			}
    		}
    	}
    	return returnValue;
    }
    
    public void updateUndo(Cell badCell) {
    	for(int row = 0; row < 9; row++) {
    		gridCells[row][badCell.getColumn()].updateAdd(badCell.getNumber());
    		
    		for(int column = 0; column < 9; column++) {
    			gridCells[badCell.getRow()][column].updateAdd(badCell.getNumber());
    			
    			if (gridCells[row][column].getMetaRow() == badCell.getMetaRow() && gridCells[row][column].getMetaColumn() == badCell.getMetaColumn()) {
    				gridCells[row][column].updateRemove(badCell.getNumber());
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
    		case 0: removeCount = 10;
    		case 1: removeCount = 15;
    		case 2: removeCount = 20;
    	}
    	
    	findRemove(removeCount);
    }
    
    public int findRemove(int count) {
    	if(count == 0) {
    		return 0;
    	}
    	
    	Cell removeCell = gridCells[ran.nextInt(9)][ran.nextInt(9)];
    	if(removeCell.isEmpty() == true && numOfSol(0, 0, 0) == 1) {
    		return findRemove(count);
    	}
    	
    	removeCell.setNumber(0);
    	return findRemove(count - 1);
    }
    
    public static int numOfSol(int row, int column, int count) {
    	if (column == 9) {
    		column = 0;
    		if(++row == 9) {
    			return count + 1;
    		}
    	}
    	
    	if(gridCells[row][column].isEmpty() == true) {
    		return numOfSol(column + 1, row, count);
    	}
    	
    	
    }
}