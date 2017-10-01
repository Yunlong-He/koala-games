package com.koala.games.clearball;

public abstract class Cleaner {
	
	CBGridModel grid;

    public Cleaner(CBGridModel model) {
        grid = model;
    }


    public abstract Move getBestMove();

    public class Move {
        public int startX;
        public int startY;

        public int endX;
        public int endY;
        
        public Object memo;
    }

}

