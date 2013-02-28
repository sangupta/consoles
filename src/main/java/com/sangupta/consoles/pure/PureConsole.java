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

package com.sangupta.consoles.pure;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import jline.console.ConsoleReader;

import com.sangupta.consoles.IConsole;

/**
 * A normal console implementation that works in the shell of the operating
 * system.
 * 
 * @author sangupta
 *
 */
public class PureConsole implements IConsole {
	
	protected ConsoleReader consoleReader;
	
	public PureConsole() {
		try {
			this.consoleReader = new ConsoleReader();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clearScreen() {
		try {
			this.consoleReader.clearScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public void print(char ch) {
		try {
			this.consoleReader.print(Character.toString(ch));
		} catch(IOException e) {
			throw new RuntimeException("Unable to write string to the console instance", e);
		}
	}
	
	public void print(char[] cbuf, int off, int len) {
		try {
			this.consoleReader.print(String.valueOf(cbuf, off, len));
		} catch (IOException e) {
			throw new RuntimeException("Unable to write string to the console instance", e);
		}
	}

	/**
	 * 
	 */
	public void print(String string) {
		try {
			this.consoleReader.print(string);
		} catch(IOException e) {
			throw new RuntimeException("Unable to write string to the console instance", e);
		}
	}

	/**
	 * 
	 */
	public void println(String string) {
		try {
			this.consoleReader.println(string);
		} catch(IOException e) {
			throw new RuntimeException("Unable to write string to the console instance", e);
		}
	}
	
	/**
	 * 
	 */
	@Override
	public char readChar() {
		return 0;
	}

	/**
	 * 
	 */
	public String readLine() {
		try {
			return this.consoleReader.readLine();
		} catch(IOException e) {
			throw new IOError(e);
		}
	}

	public char[] readPassword() {
		return null;
	}

	public char[] readPassword(char mask) {
		return null;
	}

	public void setWindowTitle(String title) {
		// not supported
	}

	public void shutdown() {
		this.consoleReader.shutdown();
	}

	public Writer getOutputStream() {
		return this.consoleReader.getOutput();
	}

	public InputStream getInputStream() {
		return this.consoleReader.getInput();
	}

	public void flush() throws IOException {
		this.consoleReader.flush();
	}

}
