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

package com.sangupta.consoles;

import java.awt.GraphicsEnvironment;

import com.sangupta.consoles.ansi.AnsiConsole;
import com.sangupta.consoles.gui.GUIConsole;
import com.sangupta.consoles.text.TextConsole;
import com.sangupta.consoles.ui.UIConsole;

/**
 * Utility class to return a desired console implementation.
 * 
 * @author sangupta
 *
 */
public class Consoles {
	
	/**
	 * Return a {@link IConsole} implementation based on the best-effort matching
	 * strategy.
	 * 
	 * @return
	 */
	public static IConsole getConsole() {
		return getConsole(null);
	}
	
	/**
	 * Create the given type of console.
	 * 
	 * @param type
	 * @return
	 */
	public static IConsole getConsole(ConsoleType type) {
		return getConsole(type, ConsolesConstants.DEFAULT_CONSOLE_ROWS, ConsolesConstants.DEFAULT_CONSOLE_COLUMNS);
	}
	
	/**
	 * Create the best-effort console for given rows and columms.
	 * 
	 * @param rows
	 * @param columns
	 * @return
	 */
	public static IConsole getConsole(int rows, int columns) {
		return getConsole(null, rows, columns);
	}
	
	/**
	 * Return a console of the given type.
	 * 
	 * @param type
	 * @return
	 */
	public static IConsole getConsole(ConsoleType type, int rows, int columns) {
		if(type == null) {
			type = ConsoleType.BEST_EFFORT;
		}
		
		switch(type) {
			case TEXT:
				return new TextConsole();
			
			case UI:
				return new UIConsole(rows, columns);

			case TABBED:
				return new GUIConsole(rows, columns);

			case BEST_EFFORT:
				if(isRunningJavaW()) {
					return new UIConsole(rows, columns);
				}
				return new AnsiConsole();
				
			default:
				break;
		}
		
		// we handle the best effort mode to build up the console
		if(GraphicsEnvironment.isHeadless()) {
			return new TextConsole();
		}

		return new GUIConsole(rows, columns); 
	}

	/**
	 * Check if we are running using Java VM or a JavaW VM - this specifies if
	 * we want to launch a UI based console, or a simple plain text/ansi
	 * console.
	 * 
	 * @return <code>true</code> if running inside JAVAW, <code>false</code>
	 *         otherwise
	 */
	private static boolean isRunningJavaW() {
		return System.console() == null;
	}
}