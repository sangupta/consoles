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

package com.sangupta.consoles;

/**
 * Enumeration that defines the various types of consoles available.
 * 
 * @author sangupta
 *
 */
public enum ConsoleType {
	
	/**
	 * Pure shell console which is invoked directly from an existing shell
	 * like Windows command line, or Bash etc.
	 * 
	 */
	Pure,
	
	/**
	 * A simple console just like the pure console but it runs inside a
	 * swing window to give it a UI frame.
	 * 
	 */
	UI,
	
	/**
	 * A complete console that is built over JavaFX technology to provide
	 * an Eclipse like-console environment filled with colors and all.
	 * 
	 */
	GUI,
	
	/**
	 * Show the console based on the current OS, architecture, memory etc.
	 * It will try to load the {@link #GUI} console first, followed by
	 * {@link #UI} console, and lastly the {@link #Pure} console.
	 */
	BestEffort;

}
