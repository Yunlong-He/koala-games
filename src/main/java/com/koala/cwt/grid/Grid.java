package com.koala.cwt.grid;

//import java.awt.Point;
import java.awt.Dimension;
//import java.awt.Color;
//import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

public abstract class Grid extends JPanel implements MouseListener, GridModelListener {
	private GridModel model;

	private int leftBorder, topBorder;

	private int width, height;

	private int cubicWidth, cubicHeight;

	private boolean strechable;

	protected EventListenerList listenerList = new EventListenerList();

	public Grid(GridModel m, int w, int h, int lb, int tb) {
       	this(m, w, h, lb, tb, (w - lb) / m.getColumns(), (h - tb) / m.getRows());
	}

	public Grid(GridModel m, int w, int h, int lb, int tb, int cw, int ch) {
		model = m;
       	model.addGridModelListener(this);
       	addMouseListener(this);

		leftBorder = lb;
		topBorder = tb;

       	width = w;
       	height = h;

       	cubicWidth = cw;
       	cubicHeight = ch;

       	strechable = false;
	}

    public boolean getStrechable() {
        return strechable;
    }

    public void setStrechable(boolean s) {
        strechable = s;
    }

	public GridModel getModel() {
	    return model;
	}

	public int getLeftBorder() {
	    return leftBorder;
	}

	public int getTopBorder() {
	    return topBorder;
	}

	public int getGridWidth() {
	    return width;
	}

	public int getGridHeight() {
	    return height;
	}

	public int getCubicHeight() {
	    return cubicHeight;
	}

	public int getCubicWidth() {
	    return cubicWidth;
	}

	public void setGridWidth(int w) {
	    this.width = w;
	}

	public void setGridHeight(int h) {
	    this.height = h;
	}

	public void setLeftBorder(int lb) {
	    this.leftBorder = lb;
	}

	public void setTopBorder(int tb) {
	    this.topBorder = tb;
	}

	public void setCubicHeight(int h) {
	    this.cubicHeight = h;
	}

	public void setCubicWidth(int w) {
	    this.cubicWidth = w;
	}

	/**
	 * Add a listener to the Grid that's notified each time a change
	 * to the data model occurs.
	 * @param l the GridDataListener
	 */
	public void addGridListener(GridListener l) {
		listenerList.add(GridListener.class, l);
    }


    /**
	 * Remove a listener from the Grid that's notified each time a
	 * change to the data model occurs.
	 * @param l the GridDataListener
	 */
	public void removeGridListener(GridListener l) {
		listenerList.remove(GridListener.class, l);
    }

	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void gridDataChanged(){
		repaint();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

        if (strechable) {
            strech(getSize().width, getSize().height);
//            System.out.println("screen size is [" + getSize().width + "," + getSize().height + "]");
        }

		drawBackground(g);

		int cols = model.getColumns();
		int rows = model.getRows();

		for (int i = 0; i < cols; i++)
		for (int j = 0; j < rows; j++) {
			Graphics cubic_g = g.create(leftBorder + i * cubicWidth,
						topBorder + j * cubicHeight, cubicWidth, cubicHeight);
			drawCubic(cubic_g, i, j);
		}
	}

	public void strech(int width, int height) {
	}

	public abstract void drawBackground(Graphics g);

	public abstract void drawCubic(Graphics g, int col, int row);

	public void mousePressed(MouseEvent evt){
	}
	public void mouseReleased(MouseEvent evt){
	}
	public void mouseEntered(MouseEvent evt){
	}
	public void mouseExited(MouseEvent evt){
	   
	}

	public void mouseClicked(MouseEvent evt){
		GridEvent e = getGridEvent(evt.getX(), evt.getY(), evt);
		if (e == null) return;


		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
	   		if (listeners[i] == GridListener.class) {
				((GridListener) listeners[i+1]).cubicClicked(this, e);
	   		}
		}
	}

	protected GridEvent getGridEvent(int x, int y, MouseEvent e) {
		int cols = model.getColumns();
		int rows = model.getRows();

		int i = (int) (x - leftBorder) / cubicWidth;
		int j = (int) (y - topBorder) / cubicHeight;

      	if ((i < 0) || (i >= cols) || (j < 0) || (j >= rows)) {
      		return null;
      	}

		int xOffset = x - leftBorder - i * cubicWidth;
		int yOffset = y - topBorder - j * cubicHeight;

		return new GridEvent(i, j, xOffset, yOffset, e);

	}

}