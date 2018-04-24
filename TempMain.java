
public class TempMain {
	
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

}
}
