package package1;

import javax.swing.JFrame;

/****************************************************************************
 * Runs the MineSweeper Game
 * @author Mitchell Couturier
 * @version 1/27/2014
 ****************************************************************************/

public class MineSweeper {
	
	/** frame that displays the Mine Sweeper Game **/
	private static JFrame frame;
	
	/************************************************************************
	 * Displays the main program frame
	 ************************************************************************/
	public static void main(String[] args){
		frame = new JFrame("MineSweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MineSweeperPanel panel = new MineSweeperPanel();
		frame.getContentPane().add(panel);
		
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	/***********************************************************************
	 * Called in the MineSweeperPanel class to resize the frame when needed
	 ***********************************************************************/
	public static void reset(){
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
}
