/*
 * @(#)NumberClock.java	0.01 2002-10-10
 *
 * Copyright 2004 Dragon Software Limit. All rights reserved.
 *
 */

package com.koala.cwt;

import java.awt.*;
import javax.swing.*;

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
public class ImageNumber extends JPanel {
    private JLabel labels[];
    private ImageIcon icons[] = new ImageIcon[11];
    
    int value;

    public ImageNumber(){
        this(null);
    }

    public ImageNumber(ImageIcon[] images){
        super();
        if (images == null) {
            images = NumberClock.getBiggerImages(this);
            for (int i = 0 ; i < 10 ; i++){
                icons[i] = images[i];
            }        
            icons[10] = images[11]; //linker, minus
        }
        else if (images.length < 11) {
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
        GUITools.addComponentTo(this, labels[2], gbl, gc, 2, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(this, labels[3], gbl, gc, 3, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(this, labels[4], gbl, gc, 4, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(this, labels[5], gbl, gc, 5, 0, 1, 1, 1.0, 0.0);
        
        value = 0;

    }


    public void setValue(int value) {
        this.value = value;
        
        int tmpValue = value;
        if (value < 0) tmpValue = -value;

        int v1 = tmpValue % 10;
        int v2 = (tmpValue % 100) / 10;
        int v3 = (tmpValue % 1000) / 100;
        int v4 = (tmpValue % 10000) / 1000;
        int v5 = (tmpValue % 100000) / 10000;
        int v6 = (tmpValue % 1000000) / 100000;
        
        if (value < 0) v6 = 10;
        
        labels[0].setIcon(icons[v6]);
        labels[1].setIcon(icons[v5]);
        labels[2].setIcon(icons[v4]);
        labels[3].setIcon(icons[v3]);
        labels[4].setIcon(icons[v2]);
        labels[5].setIcon(icons[v1]);
    }
    
    public int getValue() {
        return value;
    }

}