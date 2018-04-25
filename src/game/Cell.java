package game;
import java.util.ArrayList;
import java.util.Random;

public class Cell {
	Random ran = new Random();
	
    private int row, column, metaRow, metaColumn, number, playerNumber;
    private ArrayList<Integer> candidates = new ArrayList<Integer>();
    
    Cell(int row, int column) {
        this.row = row;
        this.column = column;
        findMetaPos();
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
    
    public int getRandomCandidate() {
    	try {
        return candidates.get(ran.nextInt(candidates.size()));
    	}
    	catch (IllegalArgumentException ex) {
    		System.out.println(this.getNumOfCandidates());
    		for (int i = 0; i <= candidates.size(); i++) {
    			System.out.println(candidates.get(i));
    		}
    		return 0;
    	}
    }
    
    public int getPlayerNumber() {
    	return playerNumber;
    }
    
    public void setPlayerNumber(int playerNumber) {
    	this.playerNumber = playerNumber;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
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
    
    public void updateRemove(int usedCandidate) {
    	if (candidates.contains(usedCandidate)) {
    		candidates.remove(candidates.indexOf(usedCandidate));
    	}
    }
    
    public void updateAdd(int prevCandidate) {
    	if (candidates.contains(prevCandidate) == false) {
    		candidates.add(prevCandidate);
    	}
    }
    
    
    public void updateRenew() {
    	candidates.clear();
    	 for (int i = 1; i < 10; i++) {
             candidates.add(i);
    	 }
    }
}
