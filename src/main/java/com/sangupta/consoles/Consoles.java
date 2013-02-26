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

import java.awt.GraphicsEnvironment;

import com.sangupta.consoles.gui.GUIConsole;
import com.sangupta.consoles.pure.PureConsole;
import com.sangupta.consoles.ui.UIConsole;

/**
 * Utility class to return a desired console implementation.
 * 
 * @author sangupta
 *
 */
public class Consoles {
	
	public static IConsole getConsole(ConsoleType type) {
		if(type == null) {
			type = ConsoleType.BestEffort;
		}
		
		switch(type) {
			case Pure:
				return new PureConsole();
			
			case UI:
				return new UIConsole();

			case GUI:
				return new GUIConsole();

			case BestEffort:
			default:
				break;
		}
		
		// we handle the best effort mode to build up the console
		if(GraphicsEnvironment.isHeadless()) {
			return new PureConsole();
		}

		return new UIConsole(); 
	}

}
