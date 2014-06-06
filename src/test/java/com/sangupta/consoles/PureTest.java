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
 * Test class for Consoles.
 * 
 * @author sangupta
 *
 */
public class PureTest {
	
	public static void main(String[] args) throws InterruptedException {
		IConsole console = Consoles.getConsole(ConsoleType.Text);
		System.out.println("Console: " + console);
		
		console.setWindowTitle("My Console");
		
		console.print("Enter name: ");
		String line = console.readLine();
		System.out.println("Name: " + line);
		
		console.print("Enter password: ");
		char[] pass = console.readPassword();
		System.out.println("Password: " + String.valueOf(pass));

		console.print("Enter password: ");
		pass = console.readPassword('*');
		System.out.println("Password: " + String.valueOf(pass));
		
		console.clearScreen();
		System.out.println("Screen cleared!");
		
	}

}