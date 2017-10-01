package com.koala.games.mine;

import java.util.Vector;
import java.util.Random;
import java.awt.Point;

import com.koala.cwt.grid.DefaultGridModel;

public class MineModel extends DefaultGridModel {
	static final int EMPTY = 0;
	static final int ONE   = 1;
	static final int TWO   = 2;
	static final int THREE = 3;
	static final int FOUR  = 4;
	static final int FIVE  = 5;
	static final int SIZE  = 6;
	static final int SEVEN = 7;
    static final int EIGHT = 8;
    static final int MINE  = 9;
    
    static final int BLANK = 10;
    static final int BLAST = 11;
    static final int BAD   = 12;
    static final int FLAG  = 13;
    static final int DOUBT = 14;
    
    static final int NORMAL = 0;
    static final int IN_FLAG = 1;
    static final int IN_DOUBT = 2;
    
    public static Random randomNumberGenerator = new Random();
    int mine_count;

    public MineModel(int c, int r, int mc) {
        super(c, r);
	   
        mine_count = mc;
        
        for (int i = 0; i < cols; i++) 
        for (int j = 0; j < rows; j++) {
            setObject(i, j, new MineBlock(EMPTY, true));      
        }
        
        init();
	}

    public void reset() {
        for (int i = 0 ; i < cols ; i++)
  		for (int j = 0 ; j < rows ; j++){
            MineBlock obj = (MineBlock) getObject(i, j);
            obj.value = EMPTY;
            obj.covered = true;
            obj.state = NORMAL;
        }
        
        init();
        fireDataChanged();
    }

    public void init() {
        Vector v = new Vector();
        for (int i = 0 ; i < cols ; i++)
        for (int j = 0 ; j < rows ; j++){
            v.addElement(new Point(i,j));       
        }
        
		for (int i = 0 ; i < mine_count ; i++) {
			int pv = (int) (randomNumberGenerator.nextDouble() * v.size());
			Point p = (Point) v.elementAt(pv);
            MineBlock obj = (MineBlock) getObject(p.x, p.y);
			obj.value = MINE;
			v.remove(pv);
		}
		
        for (int i = 0 ; i < cols ; i++)
        for (int j = 0 ; j < rows ; j++){
            MineBlock obj = (MineBlock) getObject(i, j);
            if (obj.value == EMPTY) {
                Point neib[] = new Point[8];
                neib[0] = new Point(i - 1, j - 1);
                neib[1] = new Point(i,     j - 1);
                neib[2] = new Point(i + 1, j - 1);
                neib[3] = new Point(i - 1, j);
                neib[4] = new Point(i + 1, j);
                neib[5] = new Point(i - 1, j + 1);
                neib[6] = new Point(i,     j + 1);
                neib[7] = new Point(i + 1, j + 1);

                int tmpValue = 0;
                for (int m = 0 ; m < 8 ; m++){
                    if (!validatePoint(neib[m].x, neib[m].y))
                        continue;
                    MineBlock obj2 = (MineBlock) getObject(neib[m].x, neib[m].y);
                    if (obj2.value == MINE) {
                        tmpValue++;
                    }
                }
                
                obj.value = tmpValue;
            }
        }       
		
	}
	
	public boolean discover(int x, int y) {
        MineBlock obj = (MineBlock) getObject(x, y);
        if (!obj.covered) {
            return true;
        }
        
        if (obj.state == IN_FLAG) {
            return true;
        }
        
        if (obj.value == MINE) {
            obj.value = BLAST;
            obj.covered = false;
            showAll();
            fireDataChanged();
            return false;
        }
        else {
            obj.covered = false;
            if (obj.value == EMPTY) {
                clearSigns();
                obj.signed = true;
                showSpaces(x, y);
            }
            fireDataChanged();
            return true;
        }
    }

    public boolean exDiscover(int i, int j) {
        System.out.println("exdiscover "+ i + j);
        
        MineBlock obj = (MineBlock) getObject(i, j);

        Point neib[] = new Point[8];
        neib[0] = new Point(i - 1, j - 1);
        neib[1] = new Point(i,     j - 1);
        neib[2] = new Point(i + 1, j - 1);
        neib[3] = new Point(i - 1, j);
        neib[4] = new Point(i + 1, j);
        neib[5] = new Point(i - 1, j + 1);
        neib[6] = new Point(i,     j + 1);
        neib[7] = new Point(i + 1, j + 1);

        int t = 0;
        for (int m = 0 ; m < 8 ; m++){
            if (!validatePoint(neib[m].x, neib[m].y))
                continue;
            MineBlock obj2 = (MineBlock) getObject(neib[m].x, neib[m].y);
            if (obj2.covered && (obj2.state == IN_FLAG)) 
                t++;
        }
        if (t != obj.value) {
            System.out.println("discover failed");
            return true;
        }
        
        for (int m = 0 ; m < 8 ; m++){
            if (!validatePoint(neib[m].x, neib[m].y))
                continue;
            if (!discover(neib[m].x, neib[m].y)) {
                return false;
            }
        }
        
        return true;
    }
    
    public void setState(int c, int r, int state) {
        MineBlock obj = (MineBlock) getObject(c, r);
        if (!obj.covered) {
            return;
        }
        
        obj.state = state;
        fireDataChanged();
    }
    
    public boolean isCovered(int c, int r) {
        MineBlock obj = (MineBlock) getObject(c, r);
        return obj.covered;
    }    

    public int getState(int c, int r) {
        MineBlock obj = (MineBlock) getObject(c, r);
        return obj.state;
    }    
    
    void showAll() {
        for (int i = 0 ; i < cols ; i++)
        for (int j = 0 ; j < rows ; j++){
            MineBlock obj = (MineBlock) getObject(i, j);
            if (obj.value == MINE) {
                obj.covered = false;
            }
            else if (obj.value < MINE) {
                if (obj.covered && (obj.state == IN_FLAG)) {
                    obj.value = BAD;
                    obj.covered = false;
                }
            }
        }        
    }
    
    void showSpaces(int i, int j) {
        Point neib[] = new Point[8];
        neib[0] = new Point(i - 1, j - 1);
        neib[1] = new Point(i,     j - 1);
        neib[2] = new Point(i + 1, j - 1);
        neib[3] = new Point(i - 1, j);
        neib[4] = new Point(i + 1, j);
        neib[5] = new Point(i - 1, j + 1);
        neib[6] = new Point(i,     j + 1);
        neib[7] = new Point(i + 1, j + 1);

        for (int m = 0 ; m < 8 ; m++){
            if (!validatePoint(neib[m].x, neib[m].y))
                continue;
            MineBlock obj2 = (MineBlock) getObject(neib[m].x, neib[m].y);
            if (obj2.signed) 
                continue;
            
            obj2.signed = true;
            obj2.covered = false;
            if (obj2.value == EMPTY) {
                showSpaces(neib[m].x, neib[m].y);
            }
        }
    }

    public boolean isEmpty(int x, int y) {
        if (getValue(x, y) == EMPTY) return true;
        else return false;
    }
    
    public int getValue(int x, int y) {
        MineBlock obj = (MineBlock) getObject(x, y);
        if (obj.covered) {
            if (obj.state == IN_FLAG) {
                return FLAG;
            }
            else if (obj.state == IN_DOUBT) {
                return DOUBT;
            }
            else {
                return BLANK;
            }
        }
        else {
            return obj.value;
        }
    }

    /**
     * clear signs
     */
    private void clearSigns(){
        for (int i = 0 ; i < cols; i++)
        for (int j = 0 ; j < rows; j++){
            MineBlock obj = (MineBlock) getObject(i, j);            
            obj.signed = false;
        }
    }
    
    private boolean validatePoint(int col, int row) {
        if ((col < 0) || (col >= cols) || (row < 0) || (row >= rows))
            return false;
        else
            return true;
    }

    class MineBlock {
        int value;
        boolean covered;
        int state;
        boolean signed;
        
        MineBlock(int v, boolean c) {
            value = v;
            covered = c;
            state = NORMAL;
            signed = false;
        }
    }

}

