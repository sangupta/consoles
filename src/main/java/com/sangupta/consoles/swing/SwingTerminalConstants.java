package com.sangupta.consoles.swing;

import java.awt.Color;

import com.sangupta.consoles.ConsoleUtils;
import com.sangupta.consoles.ui.TerminalCharacterAttributes;

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
	public static final int MAX_DEFAULT_ROWS = 1000;
	
	/**
	 * Default background color for a terminal
	 */
	public static final Color BACKGROUND_COLOR = new Color(0, 0, 0);
	
	/**
	 * Default foreground color for a terminal
	 */
	public static final Color DEFAULT_FOREGROUND_COLOR = new Color(192, 192, 192);
	
	/**
	 * Default tab stops, 4 chars per tab
	 */
	public static final int TAB_STOP = 4;
	
	public static final int REPAINT_DELAY = 15;
    
	public static final int CURSOR_BLINK_DELAY = 500;
    
	public static TerminalCharacterAttributes DEFAULT_TERMINAL_CHAR_ATTRIBUTES = new TerminalCharacterAttributes(DEFAULT_FOREGROUND_COLOR); 

	public static final Color DEFAULT_CURSOR_COLOR = ConsoleUtils.invert(DEFAULT_TERMINAL_CHAR_ATTRIBUTES.getBackground());

}
