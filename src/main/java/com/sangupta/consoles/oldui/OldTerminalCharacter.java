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

package com.sangupta.consoles.oldui;

import java.awt.Color;

/**
 * Holds information about one rendered character.
 * 
 * @author sangupta
 *
 */
public class OldTerminalCharacter implements Cloneable {
	
	/**
	 * The character to be displayed on screen
	 */
	public char character = ' ';
	
	/**
	 * The foreground color of the character
	 */
	public Color foreground;
	
	/**
	 * The background color of the character
	 */
	public Color background;
	
	/**
	 * Keeps track of whether the character has been highlighted using a mouse
	 * for selection.
	 */
	public boolean highlighted = false;
	
	/**
	 * Default constructor
	 * 
	 */
	public OldTerminalCharacter() {
		
	}
	
	/**
	 * Convenience constructor to create a new {@link OldTerminalCharacter} and
	 * also assign it the properties.
	 * 
	 * @param character
	 */
	public OldTerminalCharacter(char character, Color foreground, Color background) {
		this.character = character;
		this.foreground = foreground;
		this.background = background;
	}
	
	/**
	 * Create a new {@link OldTerminalCharacter} with exactly the same properties as
	 * this one.
	 */
	@Override
	public OldTerminalCharacter clone() {
		OldTerminalCharacter tc = new OldTerminalCharacter();
		
		tc.character = this.character;
		tc.foreground = this.foreground;
		tc.background = this.background;
		tc.highlighted = false;
		
		return tc;
	}

}
