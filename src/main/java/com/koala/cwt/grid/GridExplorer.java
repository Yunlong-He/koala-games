package com.koala.cwt.grid;

import java.awt.Point;
import java.util.Vector;
import java.util.StringTokenizer;

public class GridExplorer {

	GridModel grid;
	boolean signs[][];

	int cols, rows;

   /**
    *
    */
	public GridExplorer(GridModel _grid) {
		grid = _grid;

		cols = grid.getColumns();
		rows = grid.getRows();
		signs = new boolean[cols][rows];
	}

	public synchronized Point[] getShortestPath(int xStart, int yStart, int xEnd, int yEnd) {
		clearAllSigns();

		Vector frontLine = new Vector();
//		Vector walked = new Vector();

		GridPoint gp = new GridPoint(0,xStart,yStart, xEnd, yEnd);
		frontLine.add(gp);
		signs[xStart][yStart] = true;

		boolean reachEnd = false;
		String path = null;

		while (path == null) {
			if (frontLine.size() <= 0) {
				return null;
			}

			GridPoint p = (GridPoint) frontLine.elementAt(0);
			frontLine.removeElementAt(0);

       	    int a[] = {p.x - 1 , p.x + 1 , p.x , p.x};
       	    int b[] = {p.y , p.y , p.y - 1 , p.y + 1};

            for (int i = 0 ; i < 4 ; i++) {
       		int xx = a[i];
		    int yy = b[i];

       		if ((xx < 0) || (xx >= cols) || (yy < 0) || (yy >= rows))
        			continue;

        		if ((xx == xEnd) && (yy == yEnd)) {
        			path = p.path;
        			break;
        		}

        		if (signs[xx][yy]) continue;
        		if (!grid.isEmpty(xx,yy)) continue;

				GridPoint tp = new GridPoint(p.index + 1, xx, yy, xEnd, yEnd);
				tp.path = p.path + tp.x + ","+ tp.y + ";";

				// insert point to front line
				boolean inserted = false;
				for (int j = 0; j < frontLine.size(); j++) {
					GridPoint pp = (GridPoint) frontLine.elementAt(j);
					if (pp.distance >= tp.distance) {
						frontLine.add(j, tp);
						inserted = true;
						break;
					}
				}
				if (!inserted) {
					frontLine.add(tp);
				}
				signs[xx][yy] = true;

			} // end for
		} // end while


      path = "" + xStart + "," + yStart + ";" + path + xEnd + "," + yEnd;
//		System.out.println(path);

		StringTokenizer st = new StringTokenizer(path, ";");
		Point[] points = new Point[st.countTokens()];
		if (points == null) return null;
		int cur = 0;
		while(st.hasMoreTokens()) {
			String strPoint = st.nextToken();
			int pos = strPoint.indexOf(',');
			if ((pos < 0) || ((pos+1) >= strPoint.length())) break;
			String strX = strPoint.substring(0,pos);
			String strY = strPoint.substring(pos+1);
			points[cur] = new Point(Integer.parseInt(strX), Integer.parseInt(strY));
			cur++;
		}

		if ((cur+1) < points.length) return null;

		return points;
	}

	void clearAllSigns() {
		for (int i = 0; i < cols; i++)
		for (int j = 0; j < rows; j++)
			signs[i][j] = false;
	}

   /**
    * when searching path, GridPoint represents one point in the path
    */
	class GridPoint extends Point {
	   // path from start point to current point
		String path;

		// distance from current point to end point
		int distance;

		int index;

		public GridPoint(int _index, int xs, int ys, int xe, int ye) {
			super(xs,ys);
            index = _index;
            distance = index + Math.abs(xe-xs) + Math.abs(ye-ys);
			path = "";
		}
	}

}