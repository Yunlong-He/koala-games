package com.koala.games.go;


import com.koala.cwt.grid.GridModel;

public interface GoBoardModel {

	public final static int MAX_BOARD_SIZE = 29;
	public final static int DEFAULT_BOARD_SIZE = 19;
	public final static int MIN_BOARD_SIZE = 9;

//-------------------- Board View Interface----------------------------------
    public GridModel getGridModel();

	/**
	 * property get board size
	 */
	public int getBoardSize();

	/**
	 * property get point state
	 */
	public GoPoint getPoint(int x, int y);

    public void restore();
    

//-------------------- Play Interface----------------------------------
	/**
	 * execute a remove action
	 *
	 */
	public void performRemove(int x, int y) throws GoException;

	/**
	 * execute a put action
	 */
	public void performPut(int x, int y, int player) throws GoException;
	
    public void forcePut(int x, int y, int state);
   
    public void setTip(int col, int row, int tip);
	
/*	
	public void setNumber(int col, int row, int number);
	public void showNumbers(int startIndex, int destIndex, int startNumber);
	public void hideNumbers();
*/
	/**
	 * undo a action
	 */
	public void undo();

	/**
	 * get the action just performed
	 */
    public Movement getLastMovement();

    public int getMovementCount();

    public Movement createMatchRecord();

	/**
	 * divide domain to black side and white side
	 * empty body blong to it's closest neibor;
	 * if one empty body's neibor don't have same color, it's in neutrality
	 */
    public void divideDomain();

	/**
	 * clear domain mark so that no point show mark
	 */
	public void clearDomainMarks();

}

	