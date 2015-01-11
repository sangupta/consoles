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

package com.sangupta.consoles.util;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class FontUtils {
	
	private static final String[] fontsToLookFor = {  "Menlo" // OS X
													, "Lucida Console" // Windows
												   };

	public static Font getPlatformFont() {
		if(GraphicsEnvironment.isHeadless()) {
			return null;
		}
		
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fonts = g.getAvailableFontFamilyNames();
        
        String fontFound = null;
        for(String fontName : fonts) {
        	for(String name : fontsToLookFor) {
        		if(name.equals(fontName)) {
        			fontFound = name;
        			break;
        		}
        	}
        }
        
        return new Font(fontFound, 0, 14);
	}

}