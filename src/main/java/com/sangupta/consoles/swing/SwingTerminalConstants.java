/**
 *
 * consoles - Java based console terminals
 * Copyright (c) 2013-2016, Sandeep Gupta
 * 
 * http://www.sangupta/projects/consoles
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.sangupta.consoles.swing;

import java.awt.Color;

import com.sangupta.consoles.ConsoleUtils;
import com.sangupta.consoles.IConsole;
import com.sangupta.consoles.ui.TerminalCharacterAttribute;

/**
 * Various constants to be used with the Swing based implementations
 * of {@link IConsole}.
 * 
 * @author sangupta
 *
 */
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
	
	/**
	 * The delay in re-painting the screen
	 */
	public static final int REPAINT_DELAY = 15;
    
	/**
	 * The delay in blinking the cursor
	 */
	public static final int CURSOR_BLINK_DELAY = 500;
    
	/**
	 * Default {@link TerminalCharacterAttribute}
	 */
	public static TerminalCharacterAttribute DEFAULT_TERMINAL_CHAR_ATTRIBUTES = new TerminalCharacterAttribute(DEFAULT_FOREGROUND_COLOR); 

	/**
	 * Default {@link Color} of the cursor
	 */
	public static final Color DEFAULT_CURSOR_COLOR = ConsoleUtils.invert(DEFAULT_TERMINAL_CHAR_ATTRIBUTES.getBackground());

}