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
public class ConsolesTest {
	
	public static void main(String[] args) throws Exception {
		final IConsole console = Consoles.getConsole(ConsoleType.BestEffort);
		
		console.switchStreams();
		console.setWindowTitle("My Console");
		
		do {
			console.print("Enter name: ");
			String line = console.readLine();
			System.out.println("Line: " + line);
			
			if("exit".equals(line)) {
				break;
			}
		} while(true);
		
		console.shutdown();
		
//		BufferedReader reader = new BufferedReader(new InputStreamReader(console.getInputStream()));
//		line = reader.readLine();
//		System.out.println("Line2:" + line);
//		Thread.sleep(5000);
//		
//		line = console.readLine();
//		System.out.println("Line: " + line);
	}

}
