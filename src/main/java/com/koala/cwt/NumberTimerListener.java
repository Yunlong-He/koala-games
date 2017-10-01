/*
 * @(#)NumberTimerListener.java	0.01 2002-10-10
 *
 * Copyright 2004 Dragon Software Limit. All rights reserved.
 *
 */

package com.koala.cwt;

import java.util.EventListener;
import java.awt.Component;

/**
 * The NumberTimerListener interface specified listener to my visible number timer.
 *
 * @since 0.1
 */
public interface NumberTimerListener extends EventListener {

	public void timeExpired(Component parent);

}