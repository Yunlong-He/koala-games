package com.koala.cwt.grid;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;

import com.koala.cwt.ImageList;

public class ImageGrid extends Grid {
	protected ImageList imgList;

	public ImageGrid(GridModel m, ImageList list){
		this(m, list, m.getColumns() * list.getImageWidth(), m.getRows() * list.getImageHeight());
	}

	public ImageGrid(GridModel m, ImageList list, int width, int height){
		this(m, list, width, height, 0, 0);
	}

	public ImageGrid(GridModel m, ImageList list, int width, int height, int lb, int tb){
		super(m, width, height, lb, tb, list.getImageWidth(), list.getImageHeight());
		imgList = list;
	}

    public void drawBackground(Graphics g) {
    }

    public Dimension getPreferredSize() {
        int w = getCubicWidth() * getModel().getColumns();
        int h = getCubicHeight() * getModel().getRows();
        return new Dimension(w, h);
    }

    public void drawCubic(Graphics g, int col, int row) {
	    Integer obj = (Integer) getModel().getObject(col, row);
	    if (obj == null) return;

		Image img = imgList.getImage(obj.intValue());
		if (img == null) return;

		g.drawImage(img, 0, 0, getCubicWidth(), getCubicHeight(),this);
	}
}