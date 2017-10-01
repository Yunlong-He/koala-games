package com.koala.cwt;

import java.awt.image.MemoryImageSource;
import java.awt.image.ColorModel;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class ImageCharFactoryImpl extends ImageCharFactory {
//    int rp = 0xff << 24 | 0xff << 16;
//    int bp = 0xff << 24 | 0xff;
//	int wp = 0xff << 24 | 0xff << 16 | 0xff << 8 | 0xff;
//	int gp = 0xff << 24 | 0xff << 8;
	
	// images for number from 0 to 9
	ImageIcon[] numbers = new ImageIcon[10];
	
	public ImageCharFactoryImpl() {
    	int bp = 0xff << 24 | 0xff;
		int gp = 0xff << 24 | 0xff << 8;
		init(bp,gp);
	}

	public ImageIcon getImageChar(char c) {
		switch(c) {
			case 0:
				return numbers[0];
			case 1:
				return numbers[1];
			default:
				return null;			
		}
	}

	void init(int bp, int gp) {
        int[][] imageBits = new int[10][120];
        imageBits[0] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,gp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,gp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,gp,bp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,gp,bp,gp,gp,bp,bp,
            bp,bp,gp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[1] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[2] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,gp,bp,bp,
            bp,bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,gp,gp,gp,gp,gp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,bp,bp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,bp,
            bp,bp,gp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[3] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,gp,bp,bp,
            bp,bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,gp,gp,gp,gp,gp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,gp,gp,gp,gp,gp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[4] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,gp,gp,gp,gp,gp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[5] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,gp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,gp,gp,gp,gp,gp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[6] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,gp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,bp,bp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,gp,bp,gp,gp,bp,bp,
            bp,bp,gp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[7] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,gp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,gp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,gp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[8] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,gp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,gp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,gp,bp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,bp,bp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,gp,bp,gp,gp,bp,bp,
            bp,bp,gp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[9] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,gp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,gp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,gp,bp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,bp,gp,gp,gp,gp,gp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,gp,gp,gp,gp,gp,bp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,gp,gp,gp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,bp,gp,gp,bp,bp,
            bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp};

        ColorModel defaultRGB = ColorModel.getRGBdefault();
		Toolkit tk = Toolkit.getDefaultToolkit();
        for (int i = 0; i < 10; i++) {
            MemoryImageSource mis = new MemoryImageSource(
                16,29,  // width, height of image
                defaultRGB,	// ColorModel
                imageBits[i], 	// bits of image
                0, 		// offset
                16);	// scansize
            numbers[i] = new ImageIcon(tk.createImage(mis));
        }
	}
	            	
}