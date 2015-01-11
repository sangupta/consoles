/**
 *
 * consoles - Java based console terminals
 * Copyright (c) 2013-2015, Sandeep Gupta
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

package com.sangupta.consoles.core;

/**
 * Represent one key stroke that was keyed-in by the user.
 * 
 * @author sangupta
 *
 */
public class InputKey {
	
	/**
	 * The character that was keyed in
	 */
	public char ch;
	
	/**
	 * Whether the ALT key or OPTION key was pressed or not
	 */
	public boolean altPressed = false;
	
	/**
	 * Whether the CONTROL key was pressed or not
	 * 
	 */
	public boolean ctrlPressed = false;
	
	/**
	 * Was this input a {@link SpecialInputKey}
	 */
	public SpecialInputKey specialKey = null;
	
	/**
	 * Construct for simple character keyed in
	 * 
	 * @param ch
	 */
	public InputKey(char ch) {
		this.ch = ch;
	}
	
	/**
	 * Construct for a special key input
	 * 
	 * @param key
	 */
	public InputKey(SpecialInputKey key) {
		this.specialKey = key;
		this.ch = (char) 0;
	}

	/**
	 * Construct instance when CONTROL keys were pressed along with
	 * 
	 * @param ch
	 * @param altPressed
	 * @param ctrlPressed
	 */
	public InputKey(char ch, boolean altPressed, boolean ctrlPressed) {
		this.ch = ch;
		this.altPressed = altPressed;
		this.ctrlPressed = ctrlPressed;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof InputKey)) {
			return false;
		}
		
		InputKey key = (InputKey) obj;
		if(this.specialKey != null) {
			return this.specialKey == key.specialKey;
		}
		
		return (this.ch == key.ch) && (this.ctrlPressed == key.ctrlPressed) && (this.altPressed == key.altPressed);
	}
	
	@Override
	public int hashCode() {
		if(this.specialKey != null) {
			this.specialKey.hashCode();
		}
		
		return this.ch;
	}
	
}