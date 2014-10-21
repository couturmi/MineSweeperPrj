package package1;

import java.util.Random;

import javax.swing.JOptionPane;

/*************************************************************
 * Handles all of the game activities
 * @author Mitchell Couturier
 * @version 1/29/2014
 *************************************************************/
public class MineSweeperGame {
	
	/** the game board of Cells **/
	private Cell[][] board;
	
	/** status of the game if it is Won, Lost, or NotOverYet **/
	private GameStatus status;
	
	/** the number of row/columns of the board **/
	private int size; 
	
	/** the total amount of Mines on the board **/
	private int totalMineCount;
	
	/** number of times the user wins **/
	private int wins;
	
	/** number of times the user loses **/
	private int losses;
	
	/******************************************************
	 * Returns number of wins of the user
	 ******************************************************/
	public int getWins(){
		return wins;
	}
	
	/******************************************************
	 * Returns number of losses of the user
	 ******************************************************/
	public int getLosses(){
		return losses;
	}
	
	/*******************************************************
	 * Returns the size of the board
	 *******************************************************/
	public int getSize(){
		return size;
	}
	
	/********************************************************
	 * Sets the size of the board
	 ********************************************************/
	public void setSize(int s){
		size = s;
	}
	
	/*******************************************************
	 * Returns the number of mines on the board
	 *******************************************************/
	public int getTotalMineCount(){
		return totalMineCount;
	}
	
	/********************************************************
	 * Sets the number of mines on the board
	 ********************************************************/
	public void setTotalMineCount(int t){
		totalMineCount = t;
	}
	
	/******************************************************
	 * Constructor for MineSweeperGame
	 ******************************************************/
	public MineSweeperGame(){
		promptUser();
				
		status = GameStatus.NotOverYet;
		board = new Cell[size][size];
		wins = 0;
		losses = 0;
		
		setEmpty();
		layMines();
		fillInMineCount();
	}
	
	/*********************************************************
	 * method that is invoke when the user has selected a 
	 * JButton within the 2-Dim array in the MineSweeperPanel
	 * class
	 *********************************************************/
	public void select (int row, int col){
		if (board[row][col].isFlagged()){
			return;
		}
		board[row][col].setExposed(true);
		
		if(board[row][col].isMine()){
			status = GameStatus.Lost;
			return;
		}else{
			status = GameStatus.Won;
			for (int r = 0; r < size; r++){
				for(int c = 0; c < size; c++){
					if(!board[r][c].isExposed() && !board[r][c].isMine()){
						status = GameStatus.NotOverYet;
					}
				}
			}
		}
		//expands around the cell if possible
		expand(row, col);
	}
	
	/**********************************************************
	 * resets the board to a new game
	 **********************************************************/
	public void reset(){
		status = GameStatus.NotOverYet;
		board = new Cell[size][size];
		setEmpty();
		layMines();
		fillInMineCount();
	}
	
	/***********************************************************
	 * prompts the user for board size and mine count
	 ***********************************************************/
	public void promptUser(){
		String x = JOptionPane.showInputDialog(null, "Enter the size of the board\n(between 3 and 30)");
		
		
		try{
			int x1 = Integer.parseInt(x);
			if(x1 >= 3 && x1 <= 30)
				setSize(x1);
			else
				throw new NumberFormatException();
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Not a valid board-size entry.\nSize set to 10.");
			setSize(10);
		}
		
		String y = JOptionPane.showInputDialog(null, "Enter the number of Mines\n(default 10)");
		try{
			int y1 = Integer.parseInt(y);
			if(y1 <= getSize()*getSize() && y1 > 0)
				setTotalMineCount(y1);
			else throw new NumberFormatException();
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Not a valid entry for number of mines.\n Mines set to 10.");
			setTotalMineCount(10);
		}
	}
	
	/***********************************************************
	 * returns the current GameStatus of the game
	 ***********************************************************/
	public GameStatus getGameStatus(){
		return status;
	}
	
	/***********************************************************
	 * returns a Cell for a given row and col on the board so
	 * the panel can make the appropriate display.
	 ***********************************************************/
	public Cell getCell(int row, int col){
		return board[row][col];
	}
	
	/***********************************************************
	 * Clears the entire board to blank Cells
	 ***********************************************************/
	private void setEmpty(){
		for(int row = 0; row < size; row ++){
			for(int col = 0; col < size; col++){
				board[row][col] = new Cell(); //clears the cell
			}
		}
	}
	
	/***********************************************************
	 * Lays Mines is random cells
	 ***********************************************************/
	private void layMines(){
		Random random = new Random();
		int mineCount = 0;
		while(mineCount < totalMineCount){
			int col = random.nextInt(size);
			int row = random.nextInt(size);
			
			if(!board[row][col].isMine()){
				board[row][col].setMine(true);
				mineCount++;
			}
		}
	}
	
	/***********************************************************
	 * Counts the number of mines around each cell
	 ***********************************************************/
	private int countNeighbors(int currentRow, int currentCol){
		int count = 0;
		for (int r = currentRow -1; r <= currentRow +1; r++){
			for(int c = currentCol - 1; c<= currentCol +1; c++){
				if((r >= 0 && r<size) && (c >= 0 && c <size)){
					if(board[r][c].isMine()){
						count++;
					}
				}
			}
		}
		return count;
	}
	
	/**************************************************************
	 * Uses the countNeighbors method to set a number to each selected
	 * cell
	 **************************************************************/
	private void fillInMineCount(){
		for(int r =0; r < size; r++){
			for(int c = 0; c<size; c++){
				if(!board[r][c].isMine()){
					board[r][c].setMineCount(countNeighbors(r,c));
				}
			}
		}
	}
	
	/*************************************************************
	 * Checks all the cells around the selected cell to see if
	 * it can expand outwards
	 *************************************************************/
	public void expand(int row, int col){
		Cell pCell = new Cell();
		//checks if cell should expand
		if(board[row][col].isExposed() && board[row][col].getMineCount()==0){	
			for (int r = row -1; r <= row +1; r++){
				for(int c = col - 1; c<= col +1; c++){
					//does not test Cells that don't exist outside the board
					if((r >= 0 && r<size) && (c >= 0 && c <size)){ 
						pCell = board[r][c];
						//selects if Cell is not already exposed and is not a mine
						if(!pCell.isExposed() && !pCell.isMine()){
							select(r,c);
						}
					}
				}
			}
		}
	}
	
	/**************************************************************
	 * Toggles whether a cell is flagged or not
	 **************************************************************/
	public void toggleFlag(int row, int col){
		Cell mCell = getCell(row, col);
			//toggles flagging cells
			if(!mCell.isFlagged() && !mCell.isExposed())
				getCell(row, col).setFlagged(true);
			else if(mCell.isFlagged() && !mCell.isExposed())
				getCell(row,col).setFlagged(false);
	}
	
	/*****************************************************************
	 * Keeps track of how many wins and how many losses the user has
	 *****************************************************************/
	public void keepScore(){
		if(status == GameStatus.Won)
			wins++;
		if(status == GameStatus.Lost)
			losses++;
	}
}
