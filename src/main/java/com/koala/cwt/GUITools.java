/*
 * @(#)GUITools.java	0.01 2002-10-10
 *
 * Copyright 2004 Dragon Software Limit. All rights reserved.
 *
 */
package com.koala.cwt;

import java.awt.*;

/**
 * The GUITools class provides a group of mathods to facilitate your work with GUI.
 *
 * @since 0.1
 */
public class GUITools {

	/**
	 * add one component to container
	 */
	public static void addComponentTo(Container box,Component cp,GridBagLayout gbl,GridBagConstraints gc,int gridx,int gridy,int gridwidth,int gridheight,double weightx,double weighty){
		gc.gridx = gridx;
		gc.gridy = gridy;
		gc.gridwidth = gridwidth;
		gc.gridheight = gridheight;
		gc.weightx = weightx;
		gc.weighty = weighty;
		gbl.setConstraints(cp,gc);
		box.add(cp);
	}

}