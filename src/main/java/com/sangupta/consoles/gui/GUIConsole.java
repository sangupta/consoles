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

package com.sangupta.consoles.gui;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import com.sangupta.consoles.IConsole;

public class GUIConsole implements IConsole {

	public void clearScreen() {
		// TODO Auto-generated method stub
		
	}
	
	public void print(char ch) {
		
	}
	
	public void print(char[] cbuf, int off, int len) {
		
	}

	public void print(String string) {
		// TODO Auto-generated method stub
		
	}

	public void println(String string) {
		// TODO Auto-generated method stub
		
	}
	
	public char readChar() {
		return 0;
	}

	public String readLine() {
		// TODO Auto-generated method stub
		return null;
	}

	public char[] readPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	public char[] readPassword(char mask) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setWindowTitle(String title) {
		// TODO Auto-generated method stub
		
	}

	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	public Writer getOutputStream() {
		// TODO Auto-generated method stub
		return null;
	}

	public InputStream getInputStream() {
		// TODO Auto-generated method stub
		return null;
	}

	public void flush() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
