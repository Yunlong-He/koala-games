package com.koala.cwt.grid;

import java.awt.event.MouseEvent;
import java.awt.Point;

public class GridEvent {

	public int col, row;
	public int xOffset, yOffset;
    public MouseEvent evt;

	public GridEvent(int col,int row, int xOffset, int yOffset, MouseEvent evt) {
		this.col = col;
		this.row = row;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.evt = evt;
	}
}
