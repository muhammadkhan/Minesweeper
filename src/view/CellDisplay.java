package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.Board;
import model.Cell;

/**
 * Represents the visual representation of the cell in the Minesweeper
 * simulation.
 *
 */
public class CellDisplay extends JButton {

	private static final long serialVersionUID = 1L;
	private JLabel minesLabel;
	private Color curColor;
	private int rLoc, cLoc;
	private Cell corrCell;
	private static Map<Integer, Color> numColors;
	static{
		numColors = new HashMap<Integer, Color>();
		numColors.put(1, Color.BLUE);
		numColors.put(2, new Color(0x527D5D)); //shade of green
		numColors.put(3, Color.RED);
		numColors.put(4, new Color(148, 0, 211)); //purple
		numColors.put(5, new Color(0xb03060)); //maroon
		numColors.put(6, new Color(0x40e0d0)); //turquoise
		numColors.put(7, Color.BLACK);
		numColors.put(8, Color.GRAY);
	}
	
	
	/**
	 * Constructs a new cell on the screen with default parameters (green, uncovered)
	 */
	public CellDisplay(Cell cl, int r, int c){
		corrCell = cl;
		rLoc = r;
		cLoc = c;
		curColor = Color.GREEN;
		setBackground(Color.GREEN);
		addMouseListener(new CellDisplayListener());
		minesLabel = new JLabel();
		minesLabel.setForeground(numColors.get(corrCell.getNumMinesAround()));
		minesLabel.setFont(new Font("Serif", Font.BOLD, 13));
		add(minesLabel);
		
	}
	
	/**
	 * Returns the cell's color on the screen
	 * @return the color of the cell
	 */
	public Color getColor(){
		return curColor;
	}
	
	/**
	 * Will change the color of the cell to the specified color
	 * @param c the new color of the cell
	 */
	public void setColor(Color c){
		curColor = c;
		setBackground(c);
	}
	
	/**
	 * Returns the corresponding cell that goes with this visual representation
	 * @return the Cell that is associated with {@code this}
	 */
	public Cell getCorrCell(){
		return corrCell;
	}
	
	/**
	 * Sets the number to be displayed in the cell that indicates the number of surrounding mines
	 * @param m the number to display
	 */
	public void setMinesLabel(int m){
		minesLabel.setText(m + "");
	}
	
	/**
	 * 
	 * The listener for a CellDisplay
	 *
	 */
	private class CellDisplayListener extends MouseAdapter{
		
		@Override
		public void mouseClicked(MouseEvent e){
			//must make sure it is enabled, because MouseListeners actually still work
			//even when the button is disabled
			if(CellDisplay.this.isEnabled()){
				BoardDisplay bd = (BoardDisplay)(CellDisplay.this.getParent());
				if(SwingUtilities.isLeftMouseButton(e)){
					simClick(CellDisplay.this);
					if(!CellDisplay.this.corrCell.hasMine() && !CellDisplay.this.corrCell.isFlagged()){
						//uncover zero to 3 (randomly) surrounding
						int z28 = (int)(Math.random()*4);
						for(int r = rLoc - z28; r <= rLoc + z28; r++){
							for(int c = cLoc - z28; c <= cLoc + z28; c++){
								if(bd.inRange(r, c) && !bd.getGrid()[r][c].getCorrCell().hasMine()){
									simClick(bd.getGrid()[r][c]);
								}
							}
						}
					}
				} else if(SwingUtilities.isRightMouseButton(e)){
					if(!CellDisplay.this.corrCell.isFlagged()){
						if(bd.getCorrBoard().getMinesLeft() > 0){
							bd.getCorrBoard().placeFlag(CellDisplay.this.corrCell);
							setIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("images/flag-red.png")));
						}
					} else{
						bd.getCorrBoard().removeFlag(CellDisplay.this.corrCell);
						setIcon(null);
					}
					GamePanel gp = (GamePanel)(bd.getParent());
					gp.getLP().minesLeftLabel().setText("Number of Mines Left: " + bd.getCorrBoard().getMinesLeft());
				}
				
				if(bd.getCorrBoard().hasBeenSolved()){
					//GAME WON
					GameWindow gw = (GameWindow)(bd.getTopLevelAncestor());
					int again = JOptionPane.showConfirmDialog(gw, "Would you like to play again?", "GAME OVER - YOU WIN", JOptionPane.YES_NO_OPTION);
					gw.dispose();
					if(again == JOptionPane.YES_OPTION)
						control.Main.main(null);
				}
			}
		}
		
		private void simClick(CellDisplay cd){
			//TODO make better
			if(!cd.getCorrCell().hasMine() && !cd.getCorrCell().isFlagged()){
				cd.setColor(new Color(0xcdc9c9));
				if(cd.getCorrCell().getNumMinesAround() > 0)
					cd.setMinesLabel(cd.getCorrCell().getNumMinesAround());
				cd.setEnabled(false);
			} else if(cd.getCorrCell().hasMine() && !cd.getCorrCell().isFlagged()){
				//TODO what happens when you click a mine
				BoardDisplay bd = (BoardDisplay)(cd.getParent());
				for(int r = 0; r < Board.SIZE; r++){
					for(int c = 0; c < Board.SIZE; c++){
						CellDisplay curCD = bd.getGrid()[r][c];
						
						if(curCD.getCorrCell().hasMine() && !curCD.getCorrCell().isFlagged())
							curCD.setIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("images/mini_mine.jpg")));
						else if(curCD.getCorrCell().isFlagged() && !curCD.getCorrCell().hasMine())
							curCD.setIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("images/x.png")));
						else
							curCD.setEnabled(false);
					}
				}
				GameWindow gw = (GameWindow)(bd.getTopLevelAncestor());
				int again = JOptionPane.showConfirmDialog(gw, "Would you like to play again?", "GAME OVER - YOU LOST", JOptionPane.YES_NO_OPTION);
				gw.dispose();
				if(again == JOptionPane.YES_OPTION)
					control.Main.main(null);
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e){
			if(!CellDisplay.this.getColor().equals(new Color(0xcdc9c9)) && CellDisplay.this.getIcon() == null && CellDisplay.this.isEnabled())
				CellDisplay.this.setColor(new Color(0x007711));
		}
		
		@Override
		public void mouseExited(MouseEvent e){
			if(!CellDisplay.this.getColor().equals(new Color(0xcdc9c9)))
				CellDisplay.this.setColor(Color.GREEN);
		}
	}

}
