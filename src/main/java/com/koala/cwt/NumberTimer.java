/*
 * @(#)NumberTimer.java	0.01 2002-10-10
 *
 * Copyright 2004 Dragon Software Limit. All rights reserved.
 *
 */

package com.koala.cwt;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * The NumberTimer class provides a visible timer which can lapse forward and backward.
 * It's generally used in playing chesses or matches.
 * It's time unit is second. It's skin can be replace by your custom images.
 * It can notify listeners registered to it when time is expired, but currently it can
 * only do this in backward; in the future, it's expected that you can set a time limit.
 *
 * @see Field
 * @see Method
 * @see Constructor
 * @see ReflectPermission
 *
 * @since 0.1
 */
public class NumberTimer extends NumberClock implements ActionListener {
	public static int INCREASE = 0;
	public static int DECREASE = 1;

	protected int lapseType;
	protected Timer timer;
	protected int curTime;

	Component parent;

    /** Listeners. */
    protected EventListenerList listenerList = new EventListenerList();

	public NumberTimer(Component parent, ImageIcon[] images) {
		super(images);
		this.parent = parent;
		timer = new Timer(1000,this);
	}

	public NumberTimer(Component parent) {
		super();
		this.parent = parent;
		timer = new Timer(1000,this);
	}

	/**
	 * GUITimer subclasses must call this method <b>after</b> time expired.
	 *
	 * @param source The GoBoardModel that changed, typically "this".
	 * @see EventListenerList
     */
    protected void fireTimeExpired(){
		Object[] listeners = listenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == NumberTimerListener.class) {
				((NumberTimerListener)listeners[i+1]).timeExpired(parent);
	    	}
		}
	}

	/**
	 * Add a listener to the GUITimer that's notified each time a change
	 * to the data model occurs.
	 * @param l the GUITimerListener
	 */
	public void addNumberTimerListener(NumberTimerListener l) {
		listenerList.add(NumberTimerListener.class, l);
	}

	/**
	 * Remove a listener from the GUITimer that's notified each time a
	 * change to the data model occurs.
	 * @param l the GUITimerListener
	 */
	public void removeNumberTimerListener(NumberTimerListener l) {
		listenerList.remove(NumberTimerListener.class, l);
	}

	public int getLapseType() {
		return lapseType;
	}

	public void setLapseType(int lapse) {
		lapseType = lapse;
	}

	/**
	 * set max time
	 * @curTime param for max time, on second unit
	 *      it can't be bigger than 24 hour
	 */
	public void setTime(int curTime){
		if ((curTime > 0) || (curTime < 24 * 3600))
			this.curTime = curTime;
		setValue(curTime);
	}
	public void start(){
		timer.start();
	}

	public void stop(){
		timer.stop();
	}

	public void actionPerformed(ActionEvent e){
		if (lapseType == INCREASE) {
			curTime++;
		}
		else if (lapseType == DECREASE) {
			curTime--;
		}

		setValue(curTime);

		if ((curTime <= 0) || (curTime > 24 * 3600))
			fireTimeExpired();
	}

    /**
     * Test method
     */
   	public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            String vers = System.getProperty("java.version");
            if (vers.compareTo("1.1.2") < 0) {
                System.out.println("!!!WARNING: Swing must be run with a " +
                    "1.1.2 or higher version VM!!!");
            }
            JFrame frm = new JFrame("Number Timer Test");
            frm.getContentPane().add(new NumberTimer(frm));
            frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frm.pack();
			frm.show();
        } catch (Throwable t) {
            System.out.println("uncaught exception: " + t);
            t.printStackTrace();
        }
    }

}