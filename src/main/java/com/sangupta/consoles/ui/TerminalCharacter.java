/**
 *
 * consoles - Java based console terminals
 * Copyright (c) 2013, Sandeep Gupta
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

package com.sangupta.consoles.ui;

import java.awt.Color;

/**
 * Holds information about one rendered character.
 * 
 * @author sangupta
 *
 */
public class TerminalCharacter {
	
	public char character = ' ';
	
	public Color foreground;
	
	public Color background;
	
	/**
	 * Default constructor
	 * 
	 */
	public TerminalCharacter() {
		
	}
	
	/**
	 * Convenience constructor
	 * 
	 * @param character
	 */
	public TerminalCharacter(char character, Color foreground, Color background) {
		this.character = character;
		this.foreground = foreground;
		this.background = background;
	}

}
