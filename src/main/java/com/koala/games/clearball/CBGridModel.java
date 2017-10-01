package com.koala.games.clearball;

import java.util.Vector;
import java.util.Random;
import java.awt.Point;

import com.koala.cwt.grid.DefaultGridModel;

public class CBGridModel extends DefaultGridModel {
	static final int EMPTY = 0;
	static final int UMBER = 1;
	static final int CYAN = 2;
	static final int BLUE = 3;
	static final int GREEN = 4;
	static final int YELLOW = 5;
	static final int RED = 6;
	static final int PINK = 7;

   public static Random randomNumberGenerator = new Random();
   int[] preview = new int[3];

   DefaultGridModel pmodel;

   public CBGridModel(int c, int r){
	   super(c, r);
	   
	   reset();

	   pmodel = new DefaultGridModel(3,1);

		createRandomColors();
	}

   public void reset() {
      for (int i = 0 ; i < cols ; i++)
      for (int j = 0 ; j < rows ; j++){
         blocks[i][j] = new Integer(EMPTY);
      }
      fireDataChanged();
   }

   public DefaultGridModel getPreviewModel() {
      return pmodel;
   }

	public void createRandomColors() {
		for (int i = 0 ; i < 3 ; i++) {
			int pv = (int) (7.0 * randomNumberGenerator.nextDouble()) + 1;
			pmodel.setObject(i, 0, new Integer(pv));
		}
	}

	public boolean addRandomPoints(){
	  boolean succeed = true;
		for (int i = 0 ; i < 3 ; i++) {
			Vector v = getEmptyBlocks();

			int count = v.size();
			if (count == 0) return false;

			int b = (int) (count * randomNumberGenerator.nextDouble());
			Point p = (Point) v.elementAt(b);
            Integer obj = (Integer) pmodel.getObject(i,0);
			setValue(p.x, p.y, obj.intValue());
			if (clearFive(p.x, p.y) > 0)
				fireDataChanged();

			if (count == 1) {
			   succeed = false;
			   break;
			}
		}
		fireDataChanged();

		createRandomColors();
		return succeed;
	}

	public int clearFive(int x, int y) {
		Vector v = new Vector();
		v.addElement(new Point(x,y));

		Vector v0 = new Vector();
		int color = getValue(x, y);

		// check horizonal
		for (int i = x + 1 ; i < cols ; i++) {
            if (getValue(i, y) == color)
				v0.addElement(new Point(i,y));
			else break;
		}
		for (int i = x - 1 ; i >= 0 ; i--) {
            if (getValue(i, y) == color)
				v0.addElement(new Point(i,y));
			else break;
		}
		if (v0.size() >= 4) {
			for (int i = 0 ; i < v0.size() ; i++) {
				v.addElement(v0.elementAt(i));
			}
		}
		v0.removeAllElements();

		//check vertical
		for (int i = y + 1 ; i < rows ; i++) {
            if (getValue(x, i) == color)
				v0.addElement(new Point(x,i));
			else break;
		}
		for (int i = y - 1 ; i >= 0 ; i--) {
            if (getValue(x, i) == color)
				v0.addElement(new Point(x,i));
			else break;
		}
		if (v0.size() >= 4) {
			for (int i = 0 ; i < v0.size() ; i++) {
				v.addElement(v0.elementAt(i));
			}
		}
		v0.removeAllElements();

		for (int i = 1 ; i < cols ; i++) {
			int xx = x + i;
			int yy = y + i;
			if ((xx >= cols) || (yy >= rows))
				break;
            if (getValue(xx, yy) == color)
				v0.addElement(new Point(xx,yy));
			else break;
		}
		for (int i = 1 ; i < cols ; i++) {
			int xx = x - i;
			int yy = y - i;
			if ((xx < 0) || (yy < 0))
				break;
            if (getValue(xx, yy) == color)
				v0.addElement(new Point(xx,yy));
			else break;
		}
		if (v0.size() >= 4) {
			for (int i = 0 ; i < v0.size() ; i++) {
				v.addElement(v0.elementAt(i));
			}
		}
		v0.removeAllElements();

		for (int i = 1 ; i < cols ; i++) {
			int xx = x + i;
			int yy = y - i;
			if ((xx >= cols) || (yy < 0))
				break;
            if (getValue(xx, yy) == color)
				v0.addElement(new Point(xx,yy));
			else break;
		}
		for (int i = 1 ; i < cols ; i++) {
			int xx = x - i;
			int yy = y + i;
			if ((xx < 0) || (yy >= rows))
				break;
			if (getValue(xx, yy) == color)
				v0.addElement(new Point(xx,yy));
			else break;
		}
		if (v0.size() >= 4) {
			for (int i = 0 ; i < v0.size() ; i++) {
				v.addElement(v0.elementAt(i));
			}
		}
		v0.removeAllElements();

		if (v.size() > 1) {
			for (int i = 0 ; i < v.size() ; i++) {
				Point p = (Point) v.elementAt(i);
				setValue(p.x, p.y, EMPTY);
			}
			fireDataChanged();
			return v.size();
		}
		return 0;
	}

	protected Vector getEmptyBlocks() {
		Vector v = new Vector();

   	    for (int i = 0 ; i < cols ; i++)
  		for (int j = 0 ; j < rows ; j++){
      	 if (getValue(i, j) == EMPTY) {
      		 v.addElement(new Point(i,j));
      	 }
		}
		return v;
	}
	
    public boolean isEmpty(int x, int y) {
        if (getValue(x, y) == EMPTY) return true;
        else return false;
    }
    
    public int getValue(int x, int y) {
        Integer obj = (Integer) getObject(x, y);
        return obj.intValue();
    }

    public void setValue(int x, int y, int v) {
        Integer obj = new Integer(v);
        setObject(x, y, obj);
    }
}

