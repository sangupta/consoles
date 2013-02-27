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

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import com.sangupta.consoles.IConsole;

/**
 * An implementation of the UI console. Mimicks the default shell-based consoles
 * as provided in operating systems such as Windows, Mac and Linux.
 * 
 * @author sangupta
 *
 */
public class UIConsole implements IConsole {
	
	/**
	 * Reference to the internal {@link SwingTerminal} instance.
	 * 
	 */
	protected final SwingTerminal terminal;
	
	/**
	 * Default constructor
	 */
	public UIConsole() {
		this.terminal = new SwingTerminal();
	}

	/**
	 * Clear the screen
	 */
	public void clearScreen() {
		this.terminal.clearTerminal();
	}

	public void print(String string) {
		this.terminal.writeString(string);
	}

	public void println(String string) {
		this.terminal.write(string);
		this.terminal.write("\n");
		this.terminal.refresh();
	}

	public String readLine() {
		return null;
	}

	public char[] readPassword() throws IOException {
		return null;
	}

	public char[] readPassword(char mask) throws IOException {
		return null;
	}

	public void setWindowTitle(String title) {
		this.terminal.setTitle(title);
	}

	public void shutdown() {
		this.terminal.closeTerminal();
	}

	public Writer getOutputStream() {
		return null;
	}

	public InputStream getInputStream() {
		return null;
	}

	public void flush() throws IOException {
		
	}

}
