package com.koala.games.tetris;

import java.util.Random;

import com.koala.cwt.grid.DefaultGridModel;

public class TetrisModel extends DefaultGridModel {
	public static final Integer EMPTY = new Integer(0);
	public static final Integer NOTEMPTY = new Integer(1);
	
    public static Random randomNumberGenerator = new Random();
 	
	private int curShape,curAngle;
	private int curX,curY;
	
	private int shapes[][][][] = {
			/**
			 *            []
			 *            []
			 * [][][][]   []
			 *            []
			 */
			{
					{ {0,0,0,0}, {0,0,0,0}, {1,1,1,1}, {0,0,0,0} },
					{ {0,0,1,0}, {0,0,1,0}, {0,0,1,0}, {0,0,1,0} },
					{ {0,0,0,0}, {0,0,0,0}, {1,1,1,1}, {0,0,0,0} },
					{ {0,0,1,0}, {0,0,1,0}, {0,0,1,0}, {0,0,1,0} }
			},

			/**
			 *
			 * []      [][]      [][][]     []
			 * [][][]  []            []     []
			 *         []                   [][]
			 */
			{
					{ {0,0,0,0}, {1,0,0,0}, {1,1,1,0}, {0,0,0,0} },
					{ {0,0,0,0}, {0,1,1,0}, {0,1,0,0}, {0,1,0,0} },
					{ {0,0,0,0}, {1,1,1,0}, {0,0,1,0}, {0,0,0,0} },
					{ {0,0,0,0}, {0,0,1,0}, {0,0,1,0}, {0,1,1,0} }
			},

			/**
			 *
			 *     []     [][]     [][][]    []
			 * [][][]       []     []        []
			 *              []               [][]
			 */
			{
					{ {0,0,0,0}, {0,0,1,0}, {1,1,1,0}, {0,0,0,0} },
					{ {0,0,0,0}, {0,1,1,0}, {0,0,1,0}, {0,0,1,0} },
					{ {0,0,0,0}, {1,1,1,0}, {1,0,0,0}, {0,0,0,0} },
					{ {0,0,0,0}, {0,1,0,0}, {0,1,0,0}, {0,1,1,0} }
			},

			/**
			 *
			 *   []      []                   []
		 	 * [][][]    [][]     [][][]    [][]
			 *           []         []        []
			 */
			{
					{ {0,0,0,0}, {0,1,0,0}, {1,1,1,0}, {0,0,0,0} },
					{ {0,0,0,0}, {0,1,0,0}, {0,1,1,0}, {0,1,0,0} },
					{ {0,0,0,0}, {0,0,0,0}, {1,1,1,0}, {0,1,0,0} },
					{ {0,0,0,0}, {0,0,1,0}, {0,1,1,0}, {0,0,1,0} }
			},

			/**
			 *
			 * [][]
			 * [][]
			 *
			 */
			{
					{ {0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0} },
					{ {0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0} },
					{ {0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0} },
					{ {0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0} }
			},

			/**
			 *
			 * [][]        []
			 *   [][]    [][]
			 *           []
			 */
			{
					{ {0,0,0,0}, {1,1,0,0}, {0,1,1,0}, {0,0,0,0} },
					{ {0,0,0,0}, {0,0,1,0}, {0,1,1,0}, {0,1,0,0} },
					{ {0,0,0,0}, {1,1,0,0}, {0,1,1,0}, {0,0,0,0} },
					{ {0,0,0,0}, {0,0,1,0}, {0,1,1,0}, {0,1,0,0} }
			},

			/**
			 *         
			 *   [][]  []     
			 * [][]    [][] 
			 *           []
			 */
			{
					{ {0,0,0,0}, {0,1,1,0}, {1,1,0,0}, {0,0,0,0} },
					{ {0,0,0,0}, {0,1,0,0}, {0,1,1,0}, {0,0,1,0} },
					{ {0,0,0,0}, {0,1,1,0}, {1,1,0,0}, {0,0,0,0} },
					{ {0,0,0,0}, {0,1,0,0}, {0,1,1,0}, {0,0,1,0} }
			}
		};
	
	public TetrisModel(int c,int r){
		super(c, r);	
	}

	public boolean clockwise(){
		curAngle++;
		if (curAngle == 4)
			curAngle = 0;
			
		if (shapeOutOfBounds() || shapeCollided()){
			curAngle--;
			if (curAngle == -1)
				curAngle = 3;
			return false;
		}
		fireDataChanged();
		return true;
	}
	
	public boolean antiClockwise(){
		curAngle--;
		if (curAngle == -1)
			curAngle = 3;
			
		if (shapeOutOfBounds() || shapeCollided()){
			curAngle++;
			if (curAngle == 4)
				curAngle = 0;
			return false;
		}
		fireDataChanged();
		return true;
	}
	
	public boolean goLeft(){
		curX--;
		if (shapeOutOfBounds() || shapeCollided()){
			curX++;
			return false;
		}
		fireDataChanged();
		return true;
	}
	
	public boolean goRight(){
		curX++;
		if (shapeOutOfBounds() || shapeCollided()){
			curX--;
			return false;
		}
		fireDataChanged();
		return true;
	}
	
	public boolean goDown(){
		curY++;
		if (shapeOutOfBounds() || shapeCollided()){
			curY--;
			solidShape();
			return false;
		}
		fireDataChanged();
		return true;
	}
	
	public int removeFullLines(){
		int lines = 0;
		for (int i = 0 ; i < rows ; i++){
			boolean full = true;
			for (int j = 0 ; j < 10 ; j++){
	        	if (blocks[i][j] == EMPTY){
        			full = false;
        			break;
        		}
        	}
    		if (full){
    			deleteLine(i);
    			lines = lines + 1;
    		}
    	}
    	if (lines > 0) 	fireDataChanged();
    	return lines;
	}

	public boolean createNewShape(){
		int s = (int) (7.0 * randomNumberGenerator.nextDouble());
		curShape = s;
		curAngle = 0;
		curX = 4;
		curY = 0;
		fireDataChanged();
		
		if (shapeCollided()) 
			return false;
		
		return true;
	}

	/**
	 * methods implemented for GridModel
	 */
	
	public int getColumns(){
		return cols;
	}
	public int getRows(){
		return rows;
	}

	public int getState(int x, int y){
//		System.out.println("getState():" + x + ":" + y);
		int xx = x - curX;
		int yy = y - curY;
		
		if ((xx >= 0) && (xx < 4) && (yy >= 0) && (yy < 4)) {
			if (shapes[curShape][curAngle][yy][xx] == NOTEMPTY.intValue()) {
				return NOTEMPTY.intValue();
			}
		}
		
		return ((Integer)blocks[y][x]).intValue();
	}

	/**
	 * private method 
	 */
	 
	private boolean shapeOutOfBounds(){
		for (int i = 0; i < 4; i++)
		for (int j = 0; j < 4; j++){
			if (shapes[curShape][curAngle][i][j] == NOTEMPTY.intValue()) {
				int yy = i + curY;
				int xx = j + curX;
				if ((xx < 0) || (xx >= cols) || (yy < 0) || (yy >= rows)) return true;
			}
		}

		return false;
	}

	private boolean shapeCollided(){
		for (int i = 0; i < 4; i++)
		for (int j = 0; j < 4; j++){
//			System.out.println(":" + curShape + ":" + curAngle + ":" + curX + ":" + curY);
			if ((shapes[curShape][curAngle][i][j] == NOTEMPTY.intValue()) && (blocks[i + curY][j + curX] == NOTEMPTY))
					return true;
		}
		return false;
	}

	/**
	 * make shape solid with background
	 */
	private void solidShape(){
		for (int i = 0; i < 4; i++)
		for (int j = 0; j < 4; j++){
			if (shapes[curShape][curAngle][i][j] == NOTEMPTY.intValue()) 
			blocks[i+curY][j+curX] = NOTEMPTY;
		}
	}

	private void deleteLine(int index){
		for (int i = index ; i > 0 ; i--){
//			System.out.println("copying line " + (j-1) + " to line " + j);
			for (int j = 0 ; j < 10 ; j++){
				blocks[i][j] = blocks[i-1][j];
			}
		}

		for (int j = 0 ; j < 10 ; j++){
			blocks[0][j] = EMPTY;
		}
	}

}
