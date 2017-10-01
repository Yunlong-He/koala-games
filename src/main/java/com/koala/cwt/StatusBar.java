/*
 * @(#)StatusBar.java	0.01 2002-10-10
 *
 * Copyright 2004 Dragon Software Limit. All rights reserved.
 *
 */

package com.koala.cwt;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * The StatusBar, a simple status bar used in window's bottom.
 *
 * @since 0.1
 */
public class StatusBar extends JPanel {
	public static final int MAX_COLUMN = 10;

	Vector  labels;

    public StatusBar(int cols) {
		super();

		if ((cols <= 0) || (cols > MAX_COLUMN)) return;

		labels = new Vector();

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		setLayout(gbl);

		for (int i = 0 ; i < cols ; i++){
			JPanel p = new JPanel();
			p.setBorder(BorderFactory.createLoweredBevelBorder());
			JLabel jl = new JLabel("loading...");
			labels.addElement(jl);
			p.add(jl);
			GUITools.addComponentTo(this,p, gbl, gc, i, 0, 1, 1, 1.0, 1.0);
		}
	}

	public void showStatus(int index, String info){
		if ((index >= labels.size()) || (index < 0))
			return;
		JLabel jl = (JLabel) labels.elementAt(index);
		jl.setText(info);
		repaint();
	}

	public Dimension getPreferredSize() {
		return new Dimension(20,30);
	}

}
