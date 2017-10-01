package com.koala.games.go;


/**
 * class GoPoint represent one point on the board
 * point has following features:
 *  color: empty black or white
 *  holder: belong to which side, black or white, or both
 *  tip: the tip on this point, used to hand comments
 *
 */
public class GoPoint {
	public final static int EMPTY   = GoPlayer.UNKNOWN;
    public final static int BLACK   = GoPlayer.BLACK;
    public final static int WHITE   = GoPlayer.WHITE;

    public final static int NONE 	  = -1;
    public final static int TRIANGLE  = -2;
    public final static int SQUARE    = -3;
    public final static int CIRCLE    = -4;
    public final static int CROSS     = -5;

    public final static int GENERAL   = 0;
    public final static int HIGHLIGHT = 1;

    
    private int state;

	private int holder;

	private int number;
	
	private int tip;
	
	private int style;

	private boolean signed;
	
	public GoPoint(){
		state = EMPTY;
		holder = GoPlayer.UNKNOWN;
		signed = false;
		number = NONE;
		tip = NONE;
		style = GENERAL;
	}
	public int getState() {
		return state;
	}

	public void setState(int s) {
		this.state = s;
	}

	public int getHolder(){
		return holder;
	}

	public void setHolder(int holder) {
		this.holder = holder;
	}
	
    public int getStyle(){
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }
    

	public boolean isSigned(){
		return signed;
	}

	public void setSigned(boolean signed) {
		this.signed = signed;
	}

	public int getNumber() {
	   return number;
    }
    
    public void setNumber(int num) {
        number = num;
    }

	public int getTip() {
	   return tip;
    }
    
    public void setTip(int t) {
        tip = t;
    }

	/**
	 * true if one is black and the other is white
	 */
	public static boolean isEnemy(int c1, int c2) {
        if ( (c1 == GoPoint.EMPTY) || (c2 == GoPoint.EMPTY) || (c1 == c2)) {
   			return false;
   		}
   		else {
		    return true;
	    }
	}

    /**
     * true if one is black and the other is white
     */
    public static boolean isEnemy(GoPoint p1, GoPoint p2){
    	int c1 = p1.getState();
    	int c2 = p2.getState();
        if ((c1 == GoPoint.EMPTY) || (c2 == GoPoint.EMPTY) || (c1 == c2)) {
       		return false;
       	}
       	else {
	       	return true;
		}
    }

    /**
     * true if the two point has same color
     */
    public static boolean isFriend(GoPoint p1, GoPoint p2){
    	int c1 = p1.getState();
    	int c2 = p2.getState();
        if ((c1 == GoPoint.EMPTY) || (c2 == GoPoint.EMPTY) || (c1 == c2)) {
       		return false;
       	}
       	return true;
    }

}
