package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the game board.
 */
public class Board {
	
	private Cell[][] grid;
	private int minesLeft;
	/**
	 * The total amount of mines that will be placed at the start of the game.
	 */
	public int TOTALMINES;
	/**
	 * The size of the gameboard.
	 */
	public static final int SIZE = 16;
	private Set<Cell> mineCells;
	private Set<Cell> flagCells;
	private boolean solved = false;
	
	/**
	 * Constructs a new board
	 * 
	 * @param m the number of mines to be placed on the board
	 */
	public Board(int m){
		TOTALMINES = m;
		minesLeft = m;
		mineCells = new HashSet<Cell>();
		flagCells = new HashSet<Cell>();
		grid = new Cell[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++)
				grid[i][j] = new Cell(i, j);
		}
	}
	
	/**
	 * Checks to see whether the board has been solved
	 * @return true if all mine cells have been flagged and false otherwise
	 */
	public boolean hasBeenSolved(){
		return solved;
	}
	
	/**
	 * Returns the grid associated with this board
	 * @return the grid of cells
	 */
	public Cell[][] getGrid(){
		return grid;
	}
	
	/**
	 * Places mines randomly in the grid.
	 * Checks to prevent duplicates.
	 */
	public void placeMines(){
		int i = 1;
		//using a while loop b/c if the cell already has a mine, then we want to redo this:
		while(i <= TOTALMINES){
			int r = (int)(Math.random()*(SIZE - 1));
			int c = (int)(Math.random()*(SIZE - 1));
			if(!grid[r][c].hasMine()){
				grid[r][c].placeMine();
				mineCells.add(grid[r][c]);
				i++;
			}
		}
		setSurrNums();
	}
	
	/**
	 * Iterates along all non-mine cells, then iterates each cell's surrounding cells
	 * and sets how many mines are surrounding each cell
	 *
	 */
	private void setSurrNums(){
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				int num = 0;
				if(!grid[i][j].hasMine()){
					//iterate along all surrounding squares
					for(int r = i - 1; r <= i + 1; r++){
						for(int c = j - 1; c <= j + 1; c++){
							if(0 <= r && r <= SIZE - 1 && 0 <= c && c <= SIZE - 1 && grid[r][c].hasMine())
								num++;
						}
					}
					grid[i][j].setNumMinesAround(num);
				}
			}
		}
	}
	
	/**
	 * 
	 * Places a flag on the cell.
	 * Decrements the number of remaining mines for this board.
	 * NOTE: just because the number of remaining mines is decreased, it does not mean that
	 * each flag is actually a mine
	 *
	 * @param c the cell that is to be flagged
	 */
	public void placeFlag(Cell c){
		if(minesLeft >= 1){
			c.flag();
			flagCells.add(c);
			minesLeft--;
		}
		checkWin();
	}
	
	private void checkWin(){
		if(minesLeft == 0 && flagCells.equals(mineCells)){
			//at this point, the two sets are identical, meaning every flagged cell contains
			//a mine
			
			solved = true;
		}
	}
	
	/**
	 * 
	 * Undoes the effects of {@link #placeFlag(Cell) placeFlag()}
	 *
	 * @param c the cell that is to be unflagged.
	 */
	public void removeFlag(Cell c){
		c.unflag();
		flagCells.remove(c);
		minesLeft++;
	}
	
	/**
	 * 
	 * Returns the number of mines left on the board.
	 *
	 * @return the number of mines remaining on the board
	 */
	public int getMinesLeft(){
		return minesLeft;
	}
	
	/**
	 * 
	 * Returns the set of all cells that have mines.
	 *
	 * @return a set of all cells that have mines
	 */
	public Set<Cell> getCellsWithMines(){
		return mineCells;
	}
	
	/**
	 * 
	 * Returns the set of all cells that have been flagged.
	 *
	 * @return a set of all cells that have been flagged
	 */
	public Set<Cell> getFlaggedCells(){
		return flagCells;
	}
}