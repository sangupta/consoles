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

package com.sangupta.consoles.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

import com.sangupta.consoles.core.AbstractConsole;
import com.sangupta.consoles.core.InputKey;
import com.sangupta.consoles.core.KeyTrapHandler;

/**
 * A normal console implementation that works in the shell of the operating
 * system. This implementation uses <code>Jline</code> framework.
 * 
 * @author sangupta
 *
 */
public class TextConsole extends AbstractConsole {
	
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Constructor
	 */
	public TextConsole() {
	}

	@Override
	public void clearScreen() {
		// do nothing - not supported
	}
	
	/**
	 * 
	 */
	@Override
	public void print(char ch) {
		System.out.print(ch);
	}
	
	@Override
	public void print(char[] cbuf, int off, int len) {
		for(int index = off; index < off + len; index++) {
			System.out.print(cbuf[index]);
		}
	}

	/**
	 * 
	 */
	@Override
	public void print(String string) {
		System.out.print(string);
	}

	/**
	 * 
	 */
	@Override
	public void println(String string) {
		System.out.println(string);
	}
	
	/**
	 * 
	 */
	@Override
	public char readChar() {
		try {
			return (char) System.in.read();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 */
	@Override
	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public char[] readPassword() {
		return readLine().toCharArray();
	}

	@Override
	public char[] readPassword(final char mask) {
		return readPassword();
	}

	@Override
	public void setWindowTitle(String title) {
		// TODO: throw new RuntimeException("not yet implemented");
		// this.consoleReader.set
	}

	@Override
	protected void shutdownConsole() {
		// do nothing
	}

	@Override
	public Writer getWriter() {
		return new PrintWriter(System.out);
	}

	@Override
	public InputStream getInputStream() {
		return System.in;
	}

	@Override
	public OutputStream getOutputStream() {
		return System.out;
	}

	@Override
	public void flush() throws IOException {
		// do nothing
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