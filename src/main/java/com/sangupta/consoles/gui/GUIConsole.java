/**
 *
 * consoles - Java based console terminals
 * Copyright (c) 2013-2016, Sandeep Gupta
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

package com.sangupta.consoles.gui;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import com.sangupta.consoles.core.AbstractConsole;
import com.sangupta.consoles.core.InputKey;
import com.sangupta.consoles.core.KeyTrapHandler;

/**
 * 
 * @author sangupta
 *
 */
public class GUIConsole extends AbstractConsole {
	
	public GUIConsole(int rows, int columns) {
	}

	@Override
	public void clearScreen() {
		
	}

	@Override
	public void print(char ch) {
		
	}
	
	@Override
	public void print(char[] cbuf, int off, int len) {
		
	}

	@Override
	public void print(String string) {
		
	}

	@Override
	public void println(String string) {
		
	}
	
	@Override
	public char readChar() {
		return 0;
	}

	@Override
	public String readLine() {
		return null;
	}

	@Override
	public char[] readPassword() {
		return null;
	}

	@Override
	public char[] readPassword(char mask) {
		return null;
	}

	@Override
	public void setWindowTitle(String title) {
	}

	@Override
	protected void shutdownConsole() {
		
	}

	@Override
	public Writer getWriter() {
		return null;
	}

	@Override
	public InputStream getInputStream() {
		return null;
	}

	@Override
	public OutputStream getOutputStream() {
		return null;
	}

	@Override
	public void flush() throws IOException {
		
	}

	@Override
	public void addShutdownHook(Runnable runnable) {
		
	}

	@Override
	public void addKeyTrap(InputKey inputKey, KeyTrapHandler keyTrapHandler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean supportsResizing() {
		return true;
	}
	
	@Override
	public void setResizingEnabled(boolean enabled) {
		// TODO: fix this
	}
}