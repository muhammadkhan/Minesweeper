package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import model.Board;

/**
 * The visual representation of the gameboard
 *
 */
public class BoardDisplay extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int HEIGHT = 656;
	private CellDisplay[][] cds;
	private Board corrBoard;
	
	/**
	 * Constructs a new graphical representation of the gameboard with default settings.
	 * @param b the corresponding board associated with this visual representation
	 */
	public BoardDisplay(Board b){
		corrBoard = b;
		cds = new CellDisplay[Board.SIZE][Board.SIZE];
		setBackground(Color.GREEN);
		setPreferredSize(new Dimension(0, HEIGHT));
		setLayout(new GridLayout(Board.SIZE, Board.SIZE));
		for(int r = 0; r < Board.SIZE; r++){
			for(int c = 0; c < Board.SIZE; c++){
				CellDisplay cd = new CellDisplay(b.getGrid()[r][c],r, c);
				add(cd);
				cds[r][c] = cd;
			}
		}
	}
	
	/**
	 * Checks to see whether the given grid coordinate is valid
	 * @param r the row-coordinate to check
	 * @param c the column-coordinate to check
	 * @return true if the coordinate {@code (r,c)} is valid, and false otherwise
	 */
	public boolean inRange(int r, int c){
		return (0 <= r && r < Board.SIZE && 0 <= c && c < Board.SIZE);
	}
	
	/**
	 * Returns the grid of cell representations
	 * @return a two-dimensional array of CellDisplay objects
	 */
	public CellDisplay[][] getGrid(){
		return cds;
	}
	
	/**
	 * Returns the corresponding Board that goes with {@code this}
	 * @return the Board associated with {@code this}
	 */
	public Board getCorrBoard(){
		return corrBoard;
	}
}
