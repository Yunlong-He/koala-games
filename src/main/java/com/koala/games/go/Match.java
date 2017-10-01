package com.koala.games.go;

public class Match{
	public static final int NO_TIME_LIMIT = 0;

	/**
	 * board size can be bigger than Go.MIN_BOARD_SIZE and smaller than Go.MAX_BOARD_SIZE
	 * default size is Go.DEFAULT_BOARD_SIZE
	 */
	public int boardSize;

	/**
	 * time limit on second unit
	 *
	 * for example:
	 *	11100 = 3 * 3600 + 5 * 60
	 * then it represent 3 hour and 5 minute
	 */
	public int timeLen;

	/**
	 * chessmen count put before game start
	 */
	public int preputCount;

	/**
	 * ��Ŀ��domain gived by black side to white side
	 */
	public double komi;

	/**
	 * ���룺limiting times on second unit
	 */
	public int yomi;

	/**
	 * ���������limit times
	 */
	public int yomiTimes;

	public String matchName;
	public String blackName;
	public String blackRank;
	public String whiteName;
	public String whiteRank;
	public String place;
	public String matchDate;
	public String source;
	public String result;
	public String comment;

	private Movement root;		//�����¼

	private String fileName;

	public Match(){
		boardSize = GoBoardModel.DEFAULT_BOARD_SIZE;
		timeLen = NO_TIME_LIMIT;
		preputCount = 0;
		komi = 5.5;
		yomi = 0;
		yomiTimes = 0;

		matchName = "";
		blackName = "";
		blackRank = "";
		whiteName = "";
		whiteRank = "";
		place = "";
		matchDate = "";
		source = "";
		result = "";
		comment = "";

		fileName = "";
		root = null;
	}
	
	public void setMovements(Movement root) {
	   this.root = root;
    }

	public int getMovementCount() {
		return root.getChildCount();
	}

	public Movement getMovementAt(int index) {
        return (Movement) root.getChildAt(index);
	}
	
	public Movement getRootMovement() {
		return root;
	}

}