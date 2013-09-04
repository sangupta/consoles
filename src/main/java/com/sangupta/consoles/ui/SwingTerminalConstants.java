package com.sangupta.consoles.ui;

import java.awt.Color;

public interface SwingTerminalConstants {

	/**
	 * Number of default columns in a terminal
	 */
	public static final int DEFAULT_COLUMNS = 80;
	
	/**
	 * Number of maximum columns in the buffer in a terminal
	 */
	public static final int MAX_DEFAULT_COLUMNS = 80;
	
	/**
	 * Number of default rows in a terminal
	 */
	public static final int DEFAULT_ROWS = 25;
	
	/**
	 * Number of maximum rows in the buffer in a terminal
	 */
	public static final int MAX_DEFAULT_ROWS = 200;
	
	/**
	 * Default background color for a terminal
	 */
	public static final Color BACKGROUND_COLOR = new Color(0, 0, 0);
	
	/**
	 * Default foreground color for a terminal
	 */
	public static final Color FOREGROUND_COLOR = new Color(192, 192, 192);
	
	/**
	 * Default tab stops, 4 chars per tab
	 */
	public static final int TAB_STOP = 4;
	

}
