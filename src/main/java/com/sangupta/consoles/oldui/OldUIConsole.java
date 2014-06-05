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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import com.sangupta.consoles.core.AbstractConsole;
import com.sangupta.consoles.core.ConsoleInputStream;
import com.sangupta.consoles.core.ConsoleOutputStream;
import com.sangupta.consoles.core.ConsoleWriter;
import com.sangupta.consoles.core.InputKey;
import com.sangupta.consoles.core.KeyTrapHandler;

/**
 * An implementation of the UI console. Mimicks the default shell-based consoles
 * as provided in operating systems such as Windows, Mac and Linux.
 * 
 * @author sangupta
 *
 */
public class OldUIConsole extends AbstractConsole {
	
	/**
	 * A {@link Writer} stream that can be used to output to the
	 * console.
	 */
	protected final ConsoleWriter consoleWriter;
	
	/**
	 * An {@link InputStream} implementation that can be used to read
	 * from the console.
	 */
	protected final ConsoleInputStream consoleInputStream;

	/**
	 * An {@link OutputStream} implementation that can be used to write
	 * to the console.
	 * 
	 */
	protected final ConsoleOutputStream consoleOutputStream;
	
	/**
	 * Reference to the internal {@link SwingTerminal} instance.
	 * 
	 */
	protected final SwingTerminal terminal;
	
	/**
	 * Default constructor
	 */
	public OldUIConsole() {
		this.terminal = new SwingTerminal();
		this.consoleWriter = new ConsoleWriter(this);
		this.consoleInputStream = new ConsoleInputStream(this);
		this.consoleOutputStream = new ConsoleOutputStream(this);
	}

	/**
	 * 
	 * @param rows
	 * @param columns
	 */
	public OldUIConsole(int rows, int columns) {
		this.terminal = new SwingTerminal(columns, rows);
		this.consoleWriter = new ConsoleWriter(this);
		this.consoleInputStream = new ConsoleInputStream(this);
		this.consoleOutputStream = new ConsoleOutputStream(this);
	}

	/**
	 * Clear the screen
	 */
	@Override
	public void clearScreen() {
		this.terminal.clearTerminal();
	}

	/**
	 * 
	 */
	@Override
	public void print(String string) {
		this.terminal.writeString(string);
	}

	@Override
	public void print(char[] cbuf, int off, int len) {
		this.terminal.write(cbuf, off, len);
	}

	/**
	 * 
	 */
	@Override
	public void println(String string) {
		this.terminal.write(string);
		this.terminal.write("\n");
		this.terminal.refresh();
	}
	
	/**
	 * 
	 */
	@Override
	public void print(char ch) {
		int ascii = (int) ch;
		if(ascii == 10) {
			this.terminal.write("\n");
			return;
		}
		
		this.terminal.writeChar(ch);
	}
	
	/**
	 * 
	 */
	@Override
	public char readChar() {
		InputKey key = this.terminal.getKey();
		if(key != null) {
			return key.ch;
		}
		
		return (char) 0;
	}

	/**
	 * 
	 */
	@Override
	public String readLine() {
		return this.terminal.readString(true, (char) 0);
	}

	/**
	 * 
	 */
	@Override
	public char[] readPassword() {
		return this.terminal.readString(false, (char) 0).toCharArray();
	}

	/**
	 * 
	 */
	@Override
	public char[] readPassword(char mask) {
		return this.terminal.readString(true, mask).toCharArray();
	}

	/**
	 * 
	 */
	@Override
	public void setWindowTitle(String title) {
		this.terminal.setTitle(title);
	}

	/**
	 * 
	 */
	@Override
	protected void shutdownConsole() {
		this.terminal.closeTerminal();
	}

	/**
	 * 
	 */
	@Override
	public Writer getWriter() {
		return this.consoleWriter;
	}

	/**
	 * 
	 */
	@Override
	public InputStream getInputStream() {
		return this.consoleInputStream;
	}
	
	/**
	 * 
	 */
	@Override
	public OutputStream getOutputStream() {
		return this.consoleOutputStream;
	}

	/**
	 * 
	 */
	public void flush() throws IOException {
		this.consoleWriter.flush();
	}

	@Override
	public void addShutdownHook(Runnable runnable) {
		this.terminal.addShutdownHook(runnable);
	}
	
	@Override
	protected void finalize() throws Throwable {
		this.shutdown();
	}

	@Override
	public void addKeyTrap(InputKey inputKey, KeyTrapHandler keyTrapHandler) {
		this.terminal.addKeyTrap(inputKey, keyTrapHandler);
	}

	@Override
	public boolean supportsResizing() {
		return true;
	}
	
	@Override
	public void setResizingEnabled(boolean enabled) {
//		this.terminal.setResizingEnabled(enabled);
	}
}
