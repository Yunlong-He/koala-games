/*
 * @(#)ImagePanel.java	0.01 2002-10-10
 *
 * Copyright 2004 Dragon Software Limit. All rights reserved.
 *
 */

package com.koala.cwt;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;

public class ImagePanel extends JPanel {
    private Image img;

    public ImagePanel(Image img){
        super();
        this.img = img;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, getSize().width, getSize().height, this);
    }


}