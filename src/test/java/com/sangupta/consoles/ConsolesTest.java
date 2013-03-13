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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Writer;

/**
 * Test class for Consoles.
 * 
 * @author sangupta
 *
 */
public class ConsolesTest {
	
	public static void main(String[] args) throws Exception {
		IConsole console = Consoles.getConsole(ConsoleType.BestEffort);
		console.setWindowTitle("My Console");
		
		console.print("Enter name: ");
		String line = console.readLine();
		System.out.println("Line: " + line);

		Writer writer = console.getWriter();
		writer.write("another line that i wanted to print");
		
//		BufferedReader reader = new BufferedReader(new InputStreamReader(console.getInputStream()));
//		line = reader.readLine();
//		System.out.println("Line2:" + line);
//		Thread.sleep(5000);
//		
//		line = console.readLine();
//		System.out.println("Line: " + line);
	}

}
