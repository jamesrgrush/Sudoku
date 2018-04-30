package game;
import java.util.ArrayList;
import java.util.Random;

public class Cell {
	Random ran = new Random();
	
    private int row, column, metaRow, metaColumn, number;
    //Stores list of valid numbers for the cell
    private ArrayList<Integer> candidates = new ArrayList<Integer>();
    
    Cell(int row, int column) {
        this.row = row;
        this.column = column;
        findMetaPos();
        //Adds all candidates (1-9) to the ArrayList
        for (int i = 1; i < 10; i++) {
            candidates.add(i);
        }
    }
    
    public int getRow() {
        return row;
    }
    
    public int getColumn() {
        return column;
    }
    
    public int getMetaRow() {
        return metaRow;
    }
    
    public int getMetaColumn() {
        return metaColumn;
    }
    
    public int getNumber() {
        return number;
    }
    
    public int getNumOfCandidates() {
    		return candidates.size();
    	
    }
    
    //Gets a random candidate from the ArrayList and returns it
    public int getRandomCandidate() {
        return candidates.get(ran.nextInt(candidates.size()));
    	
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
    //Calculates the location of the cell in the meta 3x3 grid
    public void findMetaPos() {
       if (row <= 2) {
           metaRow = 0;
        }
       
       else if (row <= 5) {
           metaRow = 1;
        }
        
        else {
            metaRow = 2;
        }
        
        if (column <= 2) {
           metaColumn = 0;
        }
       
       else if (column <= 5) {
           metaColumn = 1;
        }
        
        else {
            metaColumn = 2;
        }
    }
    
    public boolean isEmpty() {
        return number == 0;
    }
    
    //Removes the number from the cell's list of candidates if its not already absent
    public void updateRemove(int usedCandidate) {
    	if (candidates.contains(usedCandidate)) {
    		candidates.remove(candidates.indexOf(usedCandidate));
    	}
    }
    
    //Adds the number to the cell's list of candidates if its not already present
    public void updateAdd(int prevCandidate) {
    	if (candidates.contains(prevCandidate) == false) {
    		candidates.add(prevCandidate);
    	}
    }
    
    //Removes all the cell's candidates and then adds all candidates (1-9) to avoide suplicate entries
    public void updateRenew() {
    	candidates.clear();
    	 for (int i = 1; i < 10; i++) {
             candidates.add(i);
    	 }
    }
}
