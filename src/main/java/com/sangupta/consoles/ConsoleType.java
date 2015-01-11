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

package com.sangupta.consoles;

/**
 * Enumeration that defines the various types of consoles available.
 * 
 * @author sangupta
 *
 */
public enum ConsoleType {
	
	/**
	 * Text shell console which is invoked directly from an existing shell
	 * like Windows command line, or Bash etc.
	 * 
	 */
	Text,
	
	/**
	 * Text shell console which behaves almost as a {@link ConsoleType#Text} but
	 * also allows for ANSI based sequences and coloring using Jline framework.
	 */
	Ansi,
	
	/**
	 * A simple console just like the pure console but it runs inside a
	 * swing window to give it a UI frame.
	 * 
	 */
	UI,
	
	/**
	 * A complete console that is built over JavaFX/Swing technology to provide
	 * an Eclipse like-console environment filled with colors and multiple tabs.
	 * 
	 */
	Tabbed,
	
	/**
	 * An implementation that works as a remote console. A server runs that streams
	 * all data to a console attached from a client.
	 */
	Remote,
	
	/**
	 * Show the console based on the current OS, architecture, memory etc.
	 * It will try to load the {@link #GUI} console first, followed by
	 * {@link #UI} console, and lastly the {@link #Pure} console.
	 */
	BestEffort;

	/**
	 * Convert the given string to a {@link ConsoleType} instance.
	 * 
	 * @param type
	 * @return
	 */
	public static ConsoleType fromString(String type) {
		if(type == null || type.trim().length() == 0) {
			throw new IllegalArgumentException("Console type cannot be null/empty");
		}
		
		type = type.trim().toLowerCase();
		if("text".equals(type)) { 
			return Text; 
		}
		
		if("ansi".equals(type)) { 
			return Ansi; 
		}
		
		if("ui".equals(type)) { 
			return UI; 
		}
		
		if("tabbed".equals(type)) { 
			return Tabbed; 
		}
		
		if("remote".equals(type)) { 
			return Remote; 
		}
		
		return BestEffort;
	}
	
}