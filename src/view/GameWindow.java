package view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import model.Board;

/**
 * Represents the window containing all the game's componenents
 */
public class GameWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	/**
	 * The sidelength of the window (in pixels)
	 */
	public static final int SIZE = 704;
	private GamePanel gp;
	private Board gameBoard;
	
	
	/**
	 * Constructs the window on which game is to be played.
	 * @param b the Board that this game uses
	 */
	public GameWindow(Board b){
		gameBoard = b;
		gp = new GamePanel(gameBoard);
		setTitle("MineScan");
		this.setIconImage(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("images/mine.png")).getImage());
		setSize(SIZE, SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(gp);
		setVisible(true);
		setResizable(false);
	}
	
	/**
	 * Returns the main panel on which everything is laid out
	 * @return the main panel
	 */
	public GamePanel getPanel(){
		return gp;
	}
	
	/**
	 * Returns the board which will be played
	 * @return the Board
	 */
	public Board getBoard(){
		return gameBoard;
	}
}
