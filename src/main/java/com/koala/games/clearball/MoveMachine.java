package com.koala.games.clearball;

import java.awt.Point;

import com.koala.cwt.grid.DefaultGridModel;
import com.koala.cwt.grid.GridExplorer;
import com.koala.cwt.grid.GridModelListener;
import com.koala.cwt.grid.GridModel;

public class MoveMachine implements GridModel{
    public static final int MAX_POTENTIAL = 1024;
    public static final int MIN_POTENTIAL = -1024;

    CBGridModel grid;
    GridExplorer walker;

    int balls[][];
    int next[];

    public MoveMachine(CBGridModel model) {
        grid = model;
        walker = new GridExplorer(this);

        balls = new int[grid.getColumns()][grid.getRows()];
        next = new int[3];
    }


    public Move getBestMove() {
        Move bestMove = null;

        initValue();

        for (int i = 0; i < grid.getColumns(); i++ )
        for (int j = 0; j < grid.getRows(); j++ ) {
            Move mv = getBestMove(i, j);
            if (mv == null) {
                continue;
            }

            if (bestMove == null) {
                bestMove = mv;
                continue;
            }

            System.out.println("block " + i + "," + j + " has best move to " + mv.endX + "," + mv.endY);

            if (mv.potential < bestMove.potential) {
                bestMove = mv;
            }
        }

        return bestMove;
    }

    public Move getBestMove(int x, int y) {
        if (isEmpty(x, y)) return null;

        int color = getValue(x, y);
//        int potential = getPotentialValue();
        int potential = 100000;

        int endX = -1;
        int endY = -1;

        for (int i = 0; i < grid.getColumns(); i++ )
        for (int j = 0; j < grid.getRows(); j++ ) {
            if (!isEmpty(i, j)) continue;
            if ((x == i) && (y == j)) continue;
            Point[] path = walker.getShortestPath(x, y, i, j);
            if (path == null) {
                continue;
            }

            setValue(x, y, CBGridModel.EMPTY);
            setValue(i, j, color);

            int p = getPotentialValue();
            if (p < potential) {
                endX = i;
                endY = j;
                potential = p;
            }
            System.out.println("after move, potential is " + p + "[" + i + "," + j + "]");

            setValue(i, j, CBGridModel.EMPTY);
            setValue(x, y, color);
        }

        if (endX == -1) {
            return null;
        }

        Move mv = new Move();
        mv.startX = x;
        mv.startY = y;
        mv.endX = endX;
        mv.endY = endY;
        mv.potential = potential;
        return mv;
    }

    public int getPotentialValue() {
        return (new PotentialModel_1(this)).getPotentialValue();
    }


/////////////////////////////////////////////////////////////
// interface for GridModel
/////////////////////////////////////////////////////////////

    public int getValue(int x, int y) {
        return balls[x][y];
    }

    public void setValue(int x, int y, int v) {
        balls[x][y] = v;
    }

    public boolean isNextColor(int c) {
        if ((c == next[0]) || (c == next[1]) || (c == next[2])) {
            return true;
        }

        return false;
    }

    public boolean isEmpty(int x, int y) {
        if (balls[x][y] == CBGridModel.EMPTY) {
            return true;
        }

        return false;
    }

    public void initValue() {
        for (int i = 0; i < grid.getColumns(); i++ )
        for (int j = 0; j < grid.getRows(); j++ ) {
            balls[i][j] = grid.getValue(i,j);
        }

        DefaultGridModel pv = grid.getPreviewModel();
        next[0] = ((Integer)pv.getObject(0,0)).intValue();
        next[1] = ((Integer)pv.getObject(1,0)).intValue();
        next[2] = ((Integer)pv.getObject(2,0)).intValue();

    }

    public void fireDataChanged(){}
    public void addGridModelListener(GridModelListener l){}
    public void removeGridModelListener(GridModelListener l){}
    public int getColumns(){return grid.getColumns();}
    public int getRows() {return grid.getRows();}
    public Object getObject(int col, int row){ return null;}
    public void setObject(int col, int row, Object c){}
    public void reset(int c, int r){}

    public class Move {
        int startX;
        int startY;

        int endX;
        int endY;

        int potential;
    }

}

