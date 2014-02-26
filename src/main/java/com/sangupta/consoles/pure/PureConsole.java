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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import jline.console.ConsoleReader;

import com.sangupta.consoles.core.AbstractConsole;
import com.sangupta.consoles.core.InputKey;
import com.sangupta.consoles.core.KeyTrapHandler;
import com.sangupta.consoles.core.WriterOutputStream;

/**
 * A normal console implementation that works in the shell of the operating
 * system. This implementation uses <code>Jline</code> framework.
 * 
 * @author sangupta
 *
 */
public class PureConsole extends AbstractConsole {

	/**
	 * The JLine based {@link ConsoleReader} instance unique to this console
	 */
	protected ConsoleReader consoleReader;
	
	/**
	 * The output stream for this console reader
	 */
	protected OutputStream myOutputStream;
	
	/**
	 * Constructor
	 */
	public PureConsole() {
		try {
			this.consoleReader = new ConsoleReader();
			
			this.myOutputStream = new WriterOutputStream(this.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clearScreen() {
		try {
			this.consoleReader.clearScreen();
			this.consoleReader.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void print(char ch) {
		try {
			this.consoleReader.print(Character.toString(ch));
			this.consoleReader.flush();
		} catch(IOException e) {
			throw new RuntimeException("Unable to write string to the console instance", e);
		}
	}
	
	@Override
	public void print(char[] cbuf, int off, int len) {
		try {
			this.consoleReader.print(String.valueOf(cbuf, off, len));
			this.consoleReader.flush();
		} catch (IOException e) {
			throw new RuntimeException("Unable to write string to the console instance", e);
		}
	}

	/**
	 * 
	 */
	@Override
	public void print(String string) {
		try {
			this.consoleReader.print(string);
			this.consoleReader.flush();
		} catch(IOException e) {
			throw new RuntimeException("Unable to write string to the console instance", e);
		}
	}

	/**
	 * 
	 */
	@Override
	public void println(String string) {
		try {
			this.consoleReader.println(string);
			this.consoleReader.flush();
		} catch(IOException e) {
			throw new RuntimeException("Unable to write string to the console instance", e);
		}
	}
	
	/**
	 * 
	 */
	@Override
	public char readChar() {
		try {
			return (char) this.consoleReader.readCharacter();
		} catch (IOException e) {
			throw new RuntimeException("Unable to write string to the console instance", e);
		}
	}

	/**
	 * 
	 */
	@Override
	public String readLine() {
		try {
			return this.consoleReader.readLine();
		} catch(IOException e) {
			throw new RuntimeException("Unable to write string to the console instance", e);
		}
	}

	@Override
	public char[] readPassword() {
		try {
			String line = this.consoleReader.readLine((char) 0);
			if(line != null) {
				return line.toCharArray();
			}
			
			return null;
		} catch(IOException e) {
			throw new RuntimeException("Unable to write string to the console instance", e);
		}
	}

	@Override
	public char[] readPassword(final char mask) {
		try {
			String line = this.consoleReader.readLine(mask);
			if(line != null) {
				return line.toCharArray();
			}
			
			return null;
		} catch(IOException e) {
			throw new RuntimeException("Unable to write string to the console instance", e);
		}
	}

	@Override
	public void setWindowTitle(String title) {
		// TODO: throw new RuntimeException("not yet implemented");
		// this.consoleReader.set
	}

	@Override
	protected void shutdownConsole() {
		this.consoleReader.shutdown();
	}

	@Override
	public Writer getWriter() {
		return this.consoleReader.getOutput();
	}

	@Override
	public InputStream getInputStream() {
		return this.consoleReader.getInput();
	}

	@Override
	public OutputStream getOutputStream() {
		// return this.myOutputStream;
		return null;
	}

	@Override
	public void flush() throws IOException {
		this.consoleReader.flush();
	}

	@Override
	public void addShutdownHook(Runnable runnable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addKeyTrap(InputKey inputKey, KeyTrapHandler keyTrapHandler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean supportsResizing() {
		return false;
	}
	
	@Override
	public void setResizingEnabled(boolean enabled) {
		// do nothing as the operation is not supported
	}
}
