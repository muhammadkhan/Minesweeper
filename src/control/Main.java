package control;

import model.Board;

import view.GameWindow;

/**
 * The class with the main() function to start the game.
 */
public class Main {

	/**
	 * The method run to start the game
	 */
	public static void main(String[] args) {
		int mines = (int)(Math.random()*Board.SIZE*Board.SIZE/4) + 30;
		Board b = new Board(mines);
		b.placeMines();
		new GameWindow(b);
	}

}
