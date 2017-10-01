package com.koala.games.go;

import java.awt.*;
import javax.swing.*;


public class ColorIcon implements Icon {
	private Color color;
	private int w, h;

	public ColorIcon() {
		this(Color.gray, 50, 15);
	}
	public ColorIcon(Color color, int w, int h) {
		this.color = color;
		this.w = w;
		this.h = h;
	}
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(Color.black);
		g.drawRect(x, y, w-1, h-1);
		g.setColor(color);
		g.fillRect(x+1, y+1, w-2, h-2);
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getIconWidth() {
		return w;
	}
	public int getIconHeight() {
		return h;
	}
}