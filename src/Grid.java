import java.util.Stack;

public class Grid {
    
    private Cell[][] gridCells = new Cell[9][9];
    private Stack<Cell> backtrackLog = new Stack<Cell>();
   
    Grid() {
       for(int row = 0; row < 9; row++) {
           for (int column = 0; column < 9; column++) {
               gridCells[row][column] = new Cell((row), (column));
            }
        }
    }
   
    public void populateGrid() {
    	
    	Cell bestCandidate = gridCells[0][0];
    	
    	while(true) {
    		if (bestCandidate.getRow() == 9 ) {
    			break;
    		}
    		
    		bestCandidate.setNumber(bestCandidate.getRandomCandidate());
    		backtrackLog.push(bestCandidate);
    		
    		if (updateCheckRows(bestCandidate.getRow(), bestCandidate.getNumber()) == false || updateCheckColumns(bestCandidate.getColumn(), bestCandidate.getNumber()) == false || updateCheckMeta(bestCandidate) == false) {
    			bestCandidate.updateRenew();
    			backtrackLog.pop();
    			bestCandidate = backtrackLog.pop();
    		}
    		
    		else {
    			bestCandidate = findBestCandidate();
    		}
    	}
    }
   
    public Cell findBestCandidate() {
        int bestRow = 9;
        int bestColumn = 9;
        int fewestCandidates = 10;
 
        for(int row = 0; row < 9; row++) {
        	for(int column = 0; column < 9; column++) {
        		if(gridCells[row][column].isEmpty() && gridCells[row][column].getNumOfCandidates() < fewestCandidates && gridCells[row][column].getNumOfCandidates() >= 1) {
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
    
    public boolean updateCheckRows(int bestRow, int randomCandidate) {
    	boolean returnValue = true;
    	
        for (int parseColumns = 0; parseColumns < 9; parseColumns++) {
            gridCells[bestRow][parseColumns].updateRemove(randomCandidate);
            
            if (gridCells[bestRow][parseColumns].getNumOfCandidates() == 0) {
            	returnValue = false;
            }
        }
        return returnValue;
    }
    
    public boolean updateCheckColumns(int bestColumn, int randomCandidate) {
    	boolean returnValue = true;
    	
        for (int parseRows = 0; parseRows < 9; parseRows++) {
            gridCells[parseRows][bestColumn].updateRemove(randomCandidate);
            
            if (gridCells[parseRows][bestColumn].getNumOfCandidates() == 0) {
            	returnValue = false;
            }
        }
        return returnValue;
    }
    
    public boolean updateCheckMeta(Cell bestCandidate) {
    	boolean returnValue = true;
    	
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if(gridCells[row][column].getMetaRow() == bestCandidate.getMetaRow() && gridCells[row][column].getMetaColumn() == bestCandidate.getMetaColumn()) {
                    gridCells[row][column].updateRemove(bestCandidate.getNumber());
                    
                    if (gridCells[row][column].getNumOfCandidates() == 0) {
                    	returnValue = false;
                    }
                }
            }
        }
        return returnValue;
    }
    
    public Cell[][] getGrid() {
    	return gridCells;
    }
                    
     public void logCell(Cell usedCell) {
    	 backtrackLog.add(usedCell);
     }
}