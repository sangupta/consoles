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

import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

/**
 * Defines the contract for all shell implementations. Shell
 * implementations can be JLine-based, or Swing-based or entirely
 * GUI based.
 * 
 * @author sangupta
 *
 */
public interface IConsole extends Flushable {
	
	/**
	 * Clear the entire screen.
	 * 
	 */
	public void clearScreen();
	
	/**
	 * Write the character to the screen.
	 * 
	 * @param ch
	 */
	public void print(char ch);

	/**
	 * Write a given character array to console.
	 * 
	 * @param cbuf
	 * @param off
	 * @param len
	 */
	public void print(char[] cbuf, int off, int len);
	
	/**
	 * Write the message to screen
	 * 
	 * @param string
	 */
	public void print(String string);
	
	/**
	 * Write the message to screen and move to next line
	 * on screen.
	 * 
	 * @param string
	 */
	public void println(String string);
	
	/**
	 * Read a character from the console.
	 * 
	 * @return
	 */
	public char readChar();
	
	/**
	 * Read a string from the console.
	 * 
	 * @return
	 */
	public String readLine();
	
	/**
	 * Read a password from console
	 * 
	 * @return
	 */
	public char[] readPassword() throws IOException;
	
	/**
	 * Read a password from console and mask it with the
	 * given character.
	 * 
	 * @param mask
	 * @return
	 */
	public char[] readPassword(char mask) throws IOException;
	
	/**
	 * Set the title of the window that is open, if we can.
	 * 
	 * @param title
	 */
	public void setWindowTitle(String title);
	
	/**
	 * Destroy this shell instance
	 * 
	 */
	public void shutdown();

	/**
	 * Return the output stream handler for this console
	 * 
	 * @return
	 */
	public Writer getOutputStream();

	/**
	 * Return the input stream handler of this console
	 * 
	 * @return
	 */
	public InputStream getInputStream();

}
