package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Board;

/**
 * Represents the main panel on which everything in the window is displayed
 *
 */
public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private BoardDisplay bd;
	private LowerPanel lp;
	
	/**
	 * Constructs a new panel to place in the game window.
	 * @param b the board that is to be played in the game
	 */
	public GamePanel(Board b){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.RED);
		bd = new BoardDisplay(b);
		lp = new LowerPanel();
		add(bd);
		add(lp);
	}
	
	/**
	 * Returns the visual gameboard in the panel
	 * @return the BoardDisplay that is placed in {@code this}
	 */
	public BoardDisplay getBoardDisplay(){
		return bd;
	}
	
	/**
	 * Returns the panel on the bottom which displays how many mines are left to be found
	 * @return the lower panel that contains the number of mines left
	 */
	public LowerPanel getLP(){
		return lp;
	}
	
	/**
	 * The bottom panel that displays the number of mines left
	 *
	 */
	class LowerPanel extends JPanel{
		
		private static final long serialVersionUID = 1L;
		private JLabel minesLeft;
		
		/**
		 * Constructs a new default panel on the bottom
		 */
		public LowerPanel(){
			setBackground(Color.BLUE);
			minesLeft = new JLabel("Number of Mines Left: " + GamePanel.this.bd.getCorrBoard().TOTALMINES);
			minesLeft.setFont(new Font(minesLeft.getFont().getFontName(), minesLeft.getFont().getStyle(), 20));
			minesLeft.setForeground(Color.WHITE);
			add(minesLeft);
		}
		
		/**
		 * Returns a reference to the label that displays the text
		 * @return the JLabel which contains the necessary information
		 */
		public JLabel minesLeftLabel(){
			return minesLeft;
		}
	}
}
