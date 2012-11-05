package model;

/**
 * Represents a cell in the minesweeper board
 */
public class Cell {
	
	private boolean has_mine, flagged, uncovered;
	private int gR, gC, num_surr_mines = 0;
	
	/**
	 * 
	 * Constructs a new cell object with default values (unflagged, no mine, uncovered) and sets
	 * its location accordingly given the specified parameters
	 *
	 * @param x the row-coordinate of the cell (between 0 and 15 inclusive)
	 * @param y the column-coordinate of the cell (between 0 and 15 inclusive)
	 */
	public Cell(int x, int y){
		has_mine = false;
		flagged = false;
		uncovered = false;
		//mods included to prevent arrayoutofbounds exceptions
		gR = x % (Board.SIZE - 1);
		gC = y % (Board.SIZE - 1);
	}
	
	/**
	 * 
	 * Returns the number of mines surrounding this cell.
	 *
	 * @return the number of mines in the at most 8 surrounding cells.
	 */
	public int getNumMinesAround(){
		return num_surr_mines;
	}
	
	/**
	 * 
	 * Sets the number of mines surrounding this cell.
	 * Only to be called once by the program, and never by the user.
	 *
	 * @param n the number of mines surrounding the cell
	 */
	public void setNumMinesAround(int n){
		num_surr_mines = n;
	}
	
	/**
	 * 
	 * Returns the location of this cell in grid coordinates as an array.
	 *
	 * @return The location of the cell as a two-element array {@code a} such that: </br>
	 *         a[0] is the cell's row on the board, and </br>
	 *         a[1] is the cell's column on the board
	 */
	public int[] getLoc(){
		int[] ret = new int[2];
		ret[0] = gR;
		ret[1] = gC;
		return ret;
	}
	
	/**
	 * 
	 * Checks to see whether this cell contains a mine
	 *
	 * @return {@code true} if the cell has a mine.
	 */
	public boolean hasMine(){
		return has_mine;
	}
	
	/**
	 * 
	 * Places a mine in this cell.
	 * Not to be called by the client.
	 *
	 */
	public void placeMine(){
		has_mine = true;
	}
	
	/**
	 * 
	 * Checks to see whether a flag has been placed on this cell
	 *
	 * @return {@code true} if the cell has a flag
	 */
	public boolean isFlagged(){
		return flagged;
	}
	
	/**
	 * 
	 * Places a flag on the cell.
	 * This method will be called when the client clicks on a cell in the GUI
	 *
	 */
	public void flag(){
		flagged = true;
	}
	
	/**
	 * 
	 * Removes the flag on the cell.
	 * Precondition: the cell is already flagged.
	 *
	 */
	public void unflag(){
		flagged = false;
	}
	
	/**
	 * 
	 * Checks to see whether the cell has already been uncovered by the client.
	 *
	 * @return {@code true} if the cell is uncovered.
	 */
	public boolean isUncovered(){
		return uncovered;
	}
	
	/**
	 * 
	 * Will uncover the cell.
	 * To be called by the client when cell is left-clicked
	 *
	 */
	public void uncover(){
		uncovered = true;
	}
}
