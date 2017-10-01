package com.koala.games.fillpit;

import java.util.Vector;
import java.util.Random;
import java.awt.Point;

import com.koala.cwt.grid.DefaultGridModel;

public class FPGridModel extends DefaultGridModel {
   static final int COLOR_COUNT = 7;

	static final int EMPTY = 0;
	static final int BLUE  = 1;
	static final int CYAN  = 2;
	static final int GREEN = 3;
	static final int RED   = 4;
	static final int SMILE = 5;
	static final int WHITE = 6;
	static final int YELLOW= 7;

	static final int _SMILE = 29;
	static final int _GRAY = 30;

   public static Random randomNumberGenerator = new Random();
   int[] preview = new int[3];

   DefaultGridModel pmodel;

   public FPGridModel(int c, int r) {
	   super(c, r);

	   pmodel = new DefaultGridModel(3,1);

		createRandomColors();
	}

   public DefaultGridModel getPreviewModel() {
      return pmodel;
   }

   public void reset() {
   	for (int i = 0 ; i < cols ; i++)
  		for (int j = 0 ; j < rows ; j++){
  		   setValue(i, j, EMPTY);
      }
      fireDataChanged();
   }

	public void createRandomColors() {
		for (int i = 0 ; i < 3 ; i++) {
			int pv = (int) (randomNumberGenerator.nextDouble() * COLOR_COUNT) + 1;
			pmodel.setObject(i, 0, new Integer(pv));
		}
	}

	public boolean addRandomPoints(){
		for (int i = 0 ; i < 3 ; i++) {
		      Integer obj = (Integer) pmodel.getObject(i, 0);
			int color = obj.intValue();
			Vector v = getAllowedPits(color);

			int count = v.size();
			if (count == 0) return false;

         int b = 0;
         if (count > 1) {
			   b = (int) (count * randomNumberGenerator.nextDouble());
			}

			Point p = (Point) v.elementAt(b);
			addOneBall(p.x, p.y, color);
		}
		createRandomColors();
		return userCanMove();
	}

	public int addOneBall(int x, int y, int ballColor) {
//	   System.out.println("add one ball " + ballColor + " to " + x + "," + y);
	   if (getValue(x, y) == EMPTY) {
	      setValue(x, y, ballColor);
	   }
	   else {
	      int count = getBallCount(x,y);
	      int color = getBallColor(x,y);

	      if (color != ballColor) {
	         if (ballColor == SMILE) {
	         }
	         else if (color == SMILE) {
	            setValue(x, y, (count-1) * COLOR_COUNT + ballColor);
	         }
	         else return -1;
	      }

   	   if (count == 4) {
   	      setValue(x, y, EMPTY);
   	      fireDataChanged();
   	      return 5;
   	   }
   	   else {
	         setValue(x, y, getValue(x, y) + COLOR_COUNT);
	      }
	   }
      fireDataChanged();
		return 0;
	}

	protected Vector getAllowedPits(int ballColor) {
		Vector v = new Vector();
      int minBalls = 4;

   	for (int i = 0 ; i < cols ; i++)
  		for (int j = 0 ; j < rows ; j++){
  		   int count = getBallCount(i, j);
  		   int color = getBallColor(i, j);

  		   if (color != EMPTY) {
  		      if (color != ballColor)
  		         continue;
  		   }

  		   if (count > minBalls)
  		      continue;

  		   if (count < minBalls) {
  		      v.removeAllElements();
  		      minBalls = count;
  		   }

     		v.addElement(new Point(i,j));
		}
		return v;
	}

	public int getBallCount(int x, int y) {
//	   System.out.println(blocks[x][y]);
      if (getValue(x, y) == EMPTY) return 0;
  		return ((getValue(x, y) - 1) / COLOR_COUNT) + 1;
	}

	public int getBallColor(int x, int y) {
      if (getValue(x, y) == EMPTY)
         return EMPTY;

	   int cc = getValue(x , y) % COLOR_COUNT;
	   if (cc == EMPTY)
	      cc += COLOR_COUNT;

	   return cc;
	}

	protected boolean userCanMove() {
   	for (int i = 0 ; i < cols ; i++)
  		for (int j = 0 ; j < rows ; j++){
//  		   if (blocks[i][j] == EMPTY) continue;
  		   if (getBallCount(i,j) == 1) {
     		   if (getValue(i,j) == SMILE) return true;
  		      if (canMove(i,j)) return true;
  		   }
      }
      return false;
	}

	protected boolean canMove(int x, int y) {
    	int a[] = {x - 1 , x + 1 , x , x};
    	int b[] = {y , y , y - 1 , y + 1};

      for (int i = 0 ; i < 4 ; i++) {
    		int xx = a[i];
	     	int yy = b[i];

    		if ((xx < 0) || (xx >= cols) || (yy < 0) || (yy >= rows))
     			continue;

     		if (getValue(xx, yy) == EMPTY) return true;
     		if (getBallColor(xx,yy) == SMILE) return true;
     		if (getBallColor(xx,yy) == getValue(x, y)) return true;
     	}
     	return false;
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

