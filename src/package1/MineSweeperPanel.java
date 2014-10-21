package package1;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

/*********************************************************
 * The GUI that creates the display for the MineSweeper Game.
 * @author Mitchell Couturier
 * @version 1/27/2014
 *********************************************************/

public class MineSweeperPanel extends JPanel{
	
	/** the game board of JButtons **/
	private JButton[][] board;
	
	/** used to check the values of Cells in the game **/
	private Cell iCell;
	
	/** a JButton to exit the game when clicked **/
	private JButton quitButton;
	
	/** a JButton to reset the game Board to new parameters **/
	private JButton resetButton;
	
	/** used to display information about the game **/
	private JLabel title, wins, losses;
	
	/** the actual game that the GUI uses to play MineSweeper **/
	private MineSweeperGame game;
	
	/** the amount of rows/columns for the game **/
	private int boardSize;
	
	/** panels to contain different components of the GUI **/
	private JPanel masterPanel, topPanel, mainPanel, bottomPanel;
	
	/** Button Listener for the JButtons **/
	private ButtonListener m1;
	
	/** Mouse Listener for flagging cells **/
	private RightClicker p1;
	
	/** ImageIcon for the flagging a mine **/
	private ImageIcon flagIcon;
	
	/** ImageIcon for when the user loses to display the location of all mines **/
	private ImageIcon loseMineIcon1, loseMineIcon2;
	
	/** ImageIcon for when the user wins to display the location of all mines **/
	private ImageIcon winIcon;
	
	/** ImageIcon of a blue tile for an empty cell **/
	private ImageIcon emptyIcon;
	
	/** ImageIcon to show the professor where the the Mines are located beforehand **/
	private ImageIcon showMineIcon;
	
	/***********************************************************************
	 * Constructor for the GUI
	 ***********************************************************************/
	public MineSweeperPanel() {

		//instantiates all instance variables
		game = new MineSweeperGame();
		topPanel = new JPanel();
		mainPanel = new JPanel();
		bottomPanel = new JPanel();
		masterPanel = new JPanel();
		quitButton = new JButton("Quit");
		resetButton = new JButton("Set new Values");
		wins = new JLabel("Wins: " + 0);
		losses = new JLabel("Losses: " + 0);
		m1 = new ButtonListener();
		p1 = new RightClicker();
		emptyIcon = new ImageIcon("tile.png");
		showMineIcon = new ImageIcon("showMine.png");
		flagIcon = new ImageIcon("flag.png");
		loseMineIcon1 = new ImageIcon("loseBomb.png");
		loseMineIcon2 = new ImageIcon("loseBomb2.png");
		winIcon = new ImageIcon("win.png");
		title = new JLabel("******MINESWEEPER******");
		title.setFont(new Font("TimesNewRoman", Font.BOLD, 20));
		
		//adds buttons to the 2-dim array board
		createBoard();
		
		//adds the quitButton and resetButton
		quitButton.addActionListener(m1);
		resetButton.addActionListener(m1);
		bottomPanel.add(resetButton);
		bottomPanel.add(quitButton);
		
		//adds the wins and losses labels
		bottomPanel.add(wins);
		bottomPanel.add(losses);
		
		//displays the board
		displayBoard();
		
		//sets the layout and color
		masterPanel.setLayout(new BorderLayout(10,10));
		masterPanel.setBackground(Color.black);
		topPanel.setBackground(Color.orange);
		setBackground(Color.black);
		bottomPanel.setBackground(Color.white);
		bottomPanel.setLayout(new GridLayout(2,2));
		
		//adds all panels to the contentPane
		topPanel.add(title);
		masterPanel.add(BorderLayout.NORTH, topPanel);
		masterPanel.add(BorderLayout.CENTER, mainPanel);
		masterPanel.add(BorderLayout.SOUTH, bottomPanel);
		add(masterPanel);
	}
	
	/**************************************************************************
	 * Creates the board for the GUI
	 **************************************************************************/
	private void createBoard(){
		boardSize = game.getSize();
		board = new JButton[boardSize][boardSize];
		
		//creates board
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col <boardSize; col++){
				board[row][col] = new JButton ("", emptyIcon);
				board[row][col].setPreferredSize(new Dimension(27,27));
				board[row][col].setMargin(new Insets(0,0,0,0));
				board[row][col].addActionListener(m1);
				board[row][col].addMouseListener(p1);
				mainPanel.add(board[row][col]);
			}
		}
		
		//sets the layout of the panel
		mainPanel.setLayout(new GridLayout(boardSize,boardSize));
	}
	
	/***************************************************************************
	 * Clears the current board for the GUI
	 ***************************************************************************/
	private void clearBoard(){
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col <boardSize; col++){
				mainPanel.remove(board[row][col]);
			}
		}
	}
	
	/*****************************************************************************
	 * Sets the appropriate icon to the JButtons within the GIU
	 *****************************************************************************/
	private void displayBoard(){
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				iCell = game.getCell(row,col);
				board[row][col].setText("");
				board[row][col].setIcon(emptyIcon);
				if(iCell.isMine()){
					board[row][col].setIcon(showMineIcon);
				}
				if(iCell.isExposed()){					
					board[row][col].setIcon(null);
					board[row][col].setEnabled(false);
					if(iCell.getMineCount()==0)
						board[row][col].setText("");
					else
						board[row][col].setText("" + iCell.getMineCount());
				}else{
					board[row][col].setEnabled(true);
				}
				if(iCell.isFlagged())
					board[row][col].setIcon(flagIcon);
				else if(!iCell.isFlagged() && !iCell.isMine() && !iCell.isExposed())
					board[row][col].setIcon(emptyIcon);
				else if(!iCell.isFlagged() && iCell.isMine())
					board[row][col].setIcon(showMineIcon);
			}
		}
		//resizes the JFrame to match the board size
		MineSweeper.reset();
	}
	
	/******************************************************************************
	 * Shows the location of all mines when the user loses
	 ******************************************************************************/
	private void showMines(){
		if(game.getGameStatus() == GameStatus.Lost){
			Cell pCell = new Cell();
			for(int row = 0; row < boardSize; row++){
				for(int col = 0; col < boardSize; col++){
					pCell = game.getCell(row,col);
					if(pCell.isMine()){
						//displays all the mines
						board[row][col].setIcon(loseMineIcon2);
						//sets a image to the losing Mine
						if(pCell.isExposed()){
							board[row][col].setEnabled(true);
							board[row][col].setIcon(loseMineIcon1);
						}
					}
				}
			}
		}else if(game.getGameStatus() == GameStatus.Won){
			Cell pCell = new Cell();
			for(int row = 0; row < boardSize; row++){
				for(int col = 0; col < boardSize; col++){
					pCell = game.getCell(row,col);
					if(pCell.isMine()){
						board[row][col].setIcon(winIcon);
					}
				}
			}
		}
	}
	
	/******************************************************************************
	 * ActionListener class for the GUI
	 ******************************************************************************/
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//checks which button was selected
			for(int row =0; row < boardSize; row++){
				for(int col = 0; col < boardSize; col++){
					if(board[row][col] == e.getSource()){	//checks which cell is selected
						game.select(row, col);
					}
				}
			}
			displayBoard();
			
			//if user loses game
			if (game.getGameStatus() == GameStatus.Lost){
				displayBoard();
				showMines();
				game.keepScore();
				losses.setText("Losses: " + game.getLosses());
				JOptionPane.showMessageDialog(null, "You Just Lost \n The game will now reset");
				game.reset();
				displayBoard();
			}
			
			//if user wins game
			if(game.getGameStatus() ==  GameStatus.Won){
				showMines();
				game.keepScore();
				wins.setText("Wins: " + game.getWins());
				JOptionPane.showMessageDialog(null, "You Win!!\nAll the mines have been found.\nThe game will now reset.");
				game.reset();
				displayBoard();
			}
			
			//terminates program if quitButton is selected
			if(e.getSource()==quitButton){
				System.exit(1);
			}
			
			//Sets new board statistics if resetButton is selected
			if(e.getSource() == resetButton){
				game.promptUser();
				clearBoard();
				game.reset();
				createBoard();
				displayBoard();
			}
		}
	}
	
	/******************************************************************************
	 * Action Listener for flagging cells by right clicking
	 ******************************************************************************/
	private class RightClicker extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			if(e.isMetaDown()){ 								//checks if right clicked
				for(int row =0; row < boardSize; row++){
					for(int col = 0; col < boardSize; col++){
						if(board[row][col] == e.getSource()){	//checks which cell if selected
							game.toggleFlag(row, col);
						}
					}
				}
			}
			displayBoard();
		}
	}
}
