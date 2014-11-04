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
