package com.koala.cwt;

import javax.swing.ImageIcon;

public abstract class ImageCharFactory {
	
	public static ImageCharFactory getInstance() {
		return new ImageCharFactoryImpl();
	}
	
	public abstract ImageIcon getImageChar(char c);
	
}