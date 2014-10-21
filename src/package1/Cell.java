package package1;

/*********************************************************
 * Class that represents each cell in the MineSweeper game
 * @author Mitchell Couturier
 * @version 1/27/2014
 *********************************************************/

public class Cell {
	
	/** number of mines around the cell **/
	private int mineCount;
	
	/** whether or not the cell is flagged **/
	private boolean isFlagged;
	
	/** whether or not the cell is already exposed **/
	private boolean isExposed;
	
	/** whether or not the cell is a Mine **/
	private boolean isMine;
	
	/*****************************************************
	 * Constructor to set properties to default values
	 *****************************************************/
	public Cell(){
		mineCount = 0;
		isFlagged = false;
		isExposed = false;
		isMine = false;
	}
	
	/********************************************************
	 * Constructor to initialize all of the cell's properties
	 ********************************************************/
	public Cell(int count, boolean flagged, boolean exposed, boolean mine){
		mineCount = count;
		isFlagged = flagged;
		isExposed = exposed;
		isMine = mine;
	}
	
	/*******************************************************
	 * Get method for mineCount
	 *******************************************************/
	public int getMineCount() {
		return mineCount;
	}

	/*******************************************************
	 * Set method for mineCount
	 *******************************************************/
	public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}

	/********************************************************
	 * Returns boolean value of isFlagged
	 ********************************************************/
	public boolean isFlagged() {
		return isFlagged;
	}

	/********************************************************
	 * Set method for isFlagged
	 ********************************************************/
	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	/********************************************************
	 * Returns boolean value of isExposed
	 ********************************************************/
	public boolean isExposed() {
		return isExposed;
	}

	/********************************************************
	 * Set method for isExposed
	 ********************************************************/
	public void setExposed(boolean isExposed) {
		this.isExposed = isExposed;
	}

	/********************************************************
	 * Returns boolean value of isMine
	 ********************************************************/
	public boolean isMine() {
		return isMine;
	}

	/********************************************************
	 * Set method for isMine
	 ********************************************************/
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}
}
