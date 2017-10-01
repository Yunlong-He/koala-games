
package com.koala.games.go;

public final class GoPlayer{
    public final static int UNKNOWN = -1;
    public final static int BOTH    = -2;
    public final static int BLACK   = 1;
    public final static int WHITE   = 2;

	private String name,rank;
	private int side;
	private int dead;
	private int timeLeft;	// with second unit

	public GoPlayer (int side,String name, String rank,int timeLeft) {
		this.side = side;
		dead = 0;
		this.timeLeft = timeLeft;
		this.name = name;
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public String getRank() {
		return rank;
	}

	public int getDead() {
		return dead;
	}
	public void addDead(int c) {
		dead += c;
	}

	public int getTimeLeft() {
		return timeLeft;
	}
	
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	public static int getOpponent(int p_player) {
        if (p_player == GoPlayer.BLACK) {
			return GoPlayer.WHITE;
		}
		else {
			return GoPlayer.BLACK;
		}
	}
}
