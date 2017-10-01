/*
 * @(#)NumberClock.java	0.01 2002-10-10
 *
 * Copyright 2004 Dragon Software Limit. All rights reserved.
 *
 */

package com.koala.cwt;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

/**
 * The NumberClock class provides a visible number clock which keep stillness, you can
 * set it's value at runtime.It's skin can be replace by your custom images.
 *
 * @see Field
 * @see Method
 * @see Constructor
 * @see ReflectPermission
 *
 * @since 0.1
 */
public class NumberClock extends JPanel {
    private JLabel labels[];
    private ImageIcon icons[];

    public NumberClock(){
        this(null);
    }

    public NumberClock(ImageIcon[] images){
        super();
        if (images == null) {
//            images = getBiggerImages();
            images = getDefaultImages(this);

        }
        icons = new ImageIcon[11];
        if (images.length < 11) {
            for (int i = 0 ; i < 11 ; i++){
                icons[i] = null;
            }
        }
        else {
            for (int i = 0 ; i < 11 ; i++){
                icons[i] = images[i];
            }
        }

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        setLayout(gbl);

        labels = new JLabel[6];
        for (int i = 0 ; i < 6 ; i++){
            labels[i] = new JLabel(icons[0]);
        }

        GUITools.addComponentTo(this, labels[0], gbl, gc, 0, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(this, labels[1], gbl, gc, 1, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(this, new JLabel(icons[10]), gbl, gc, 2, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(this, labels[2], gbl, gc, 3, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(this, labels[3], gbl, gc, 4, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(this, new JLabel(icons[10]), gbl, gc, 5, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(this, labels[4], gbl, gc, 6, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(this, labels[5], gbl, gc, 7, 0, 1, 1, 1.0, 0.0);

    }


    public void setValue(int curTime) {
        if ((curTime < 0) || (curTime >= 99 * 3600))    return;

        int hour = curTime / 3600;
        int tempTime = curTime - hour * 3600;
        int minute = tempTime / 60;
        int second = tempTime - minute * 60;

        int h1 = hour / 10;
        int h2 = hour - h1 * 10;
        int m1 = minute / 10;
        int m2 = minute - m1 * 10;
        int s1 = second / 10;
        int s2 = second - s1 * 10;
//		System.out.println("" + h1 + "," + h2 + "," + m1 + "," + m2 + "," + s1 + "," + s2);
        labels[0].setIcon(icons[h1]);
        labels[1].setIcon(icons[h2]);
        labels[2].setIcon(icons[m1]);
        labels[3].setIcon(icons[m2]);
        labels[4].setIcon(icons[s1]);
        labels[5].setIcon(icons[s2]);
    }

    public static ImageIcon[] getDefaultImages(JComponent observer) {
        int rp = 0xff << 24 | 0xff << 16;
        int bp = 0xff << 24 | 0xff;
        int wp = 0xff << 24 | 0xff << 16 | 0xff << 8 | 0xff;
        int gp = 0xff << 24 | 0xff << 8;

        int[][] imageBits = new int[10][120];
        imageBits[0] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[1] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[2] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,gp,bp,bp,bp,bp,bp,bp,
            bp,gp,bp,bp,bp,bp,bp,bp,
            bp,gp,bp,bp,bp,bp,bp,bp,
            bp,gp,bp,bp,bp,bp,bp,bp,
            bp,gp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[3] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[4] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[5] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,gp,bp,bp,bp,bp,bp,bp,
            bp,gp,bp,bp,bp,bp,bp,bp,
            bp,gp,bp,bp,bp,bp,bp,bp,
            bp,gp,bp,bp,bp,bp,bp,bp,
            bp,gp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[6] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,gp,bp,bp,bp,bp,bp,bp,
            bp,gp,bp,bp,bp,bp,bp,bp,
            bp,gp,bp,bp,bp,bp,bp,bp,
            bp,gp,bp,bp,bp,bp,bp,bp,
            bp,gp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[7] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[8] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp};
        imageBits[9] = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,gp,bp,bp,bp,bp,gp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,bp,bp,bp,bp,gp,bp,
            bp,bp,gp,gp,gp,gp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp};
        int[] sepBits = new int[] {
            bp,bp,bp,bp,
            bp,bp,bp,bp,
            bp,gp,gp,bp,
            bp,gp,gp,bp,
            bp,gp,gp,bp,
            bp,gp,gp,bp,
            bp,bp,bp,bp,
            bp,bp,bp,bp,
            bp,bp,bp,bp,
            bp,gp,gp,bp,
            bp,gp,gp,bp,
            bp,gp,gp,bp,
            bp,gp,gp,bp,
            bp,bp,bp,bp,
            bp,bp,bp,bp};

        ColorModel defaultRGB = ColorModel.getRGBdefault();
        ImageIcon[] images = new ImageIcon[11];
        for (int i = 0; i < 10; i++) {
            MemoryImageSource mis = new MemoryImageSource(
                        8,15,  // width, height of image
                        defaultRGB,	// ColorModel
                        imageBits[i], 	// bits of image
                        0, 		// offset
                        8);	// scansize
            images[i] = new ImageIcon(observer.createImage(mis));
        }
        MemoryImageSource mis = new MemoryImageSource(
                    4,15,  // width, height of image
                    defaultRGB,	// ColorModel
                    sepBits, 	// bits of image
                    0, 		// offset
                    4);	// scansize
        images[10] = new ImageIcon(observer.createImage(mis));
        return images;
    }

    public static ImageIcon[] getBiggerImages(JComponent observer) {
        int rp = 0xff << 24 | 0xff << 16;
        int bp = 0xff << 24 | 0xff;
        int wp = 0xff << 24 | 0xff << 16 | 0xff << 8 | 0xff;
        int gp = 0xff << 24 | 0xff << 8;

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
        int[] sepBits = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,bp,bp,bp,
            bp,bp,bp,gp,gp,bp,bp,bp,
            bp,bp,bp,gp,gp,bp,bp,bp,
            bp,bp,bp,gp,gp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,bp,bp,bp,
            bp,bp,bp,gp,gp,bp,bp,bp,
            bp,bp,bp,gp,gp,bp,bp,bp,
            bp,bp,bp,gp,gp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp};
        int[] linker = new int[] {
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,
            bp,bp,bp,bp,gp,gp,gp,gp,gp,gp,gp,gp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,
            bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp,bp};

        ColorModel defaultRGB = ColorModel.getRGBdefault();
        ImageIcon[] images = new ImageIcon[12];
        for (int i = 0; i < 10; i++) {
            MemoryImageSource mis = new MemoryImageSource(
                16,29,  // width, height of image
                defaultRGB,	// ColorModel
                imageBits[i], 	// bits of image
                0, 		// offset
                16);	// scansize
                images[i] = new ImageIcon(observer.createImage(mis));
        }
        MemoryImageSource mis = new MemoryImageSource(
                8,29,  // width, height of image
                defaultRGB,	// ColorModel
                sepBits, 	// bits of image
                0, 		// offset
                8);	// scansize
        images[10] = new ImageIcon(observer.createImage(mis));
        
        mis = new MemoryImageSource(
                 16,29,  // width, height of image
                 defaultRGB, // ColorModel
                 linker,   // bits of image
                 0,      // offset
                 16);    // scansize
        images[11] = new ImageIcon(observer.createImage(mis));

        return images;
    }
}