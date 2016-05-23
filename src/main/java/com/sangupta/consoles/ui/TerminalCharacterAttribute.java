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

package com.sangupta.consoles.ui;

import java.awt.Color;

/** 
 * Attributes associated with a character in a <code>TextWindow</code>.  <code>TextAttributes</code>
 * are immutable, and the same instance may be shared by many characters.
 *
 * @author sangupta
 */
public class TerminalCharacterAttribute {
	
    private final Color foreground;
    
    private final Color background;
    
    private boolean highlight;

    /** 
     * Constructs a new <code>TextAttributes</code> with the specified foreground color
     * and a black background.
     *
     *@param foreground the foreground color
     *@throws NullPointerException if <code>foreground</code> is <code>null</code>
     */
    public TerminalCharacterAttribute(Color foreground) {
        this(foreground, Color.BLACK);
    }
        

    /** 
     * Constructs a new <code>TextAttributes</code> with the specified foreground and 
     * background colors.
     *
     *@param foreground the foreground color
     *@param background the background color
     *@throws NullPointerException if either <code>foreground</code> or <code>background</code> are <code>null</code>
     */
    public TerminalCharacterAttribute(Color foreground, Color background) {
        if (foreground == null)
            throw new NullPointerException("foreground must be non-null");
        if (background == null)
            throw new NullPointerException("background must be non-null");
        this.foreground = foreground;
        this.background = background;
    }
    
    
    /** 
     * Returns the foreground color of this <code>TextAttributes</code>.
     *
     *@return the foreground color
     */
    public Color getForeground() {
        return foreground;
    }
    

    /** 
     * Returns the background color of this <code>TextAttributes</code>.
     *
     *@return the background color
     */
    public Color getBackground() {
        return background;
    }
    
    
    /** 
     * Converts this object to a <code>String</code> representation.
     *
     *@return a <code>String</code> representing this object
     */
    public String toString() {
        return "TerminalCharacter(" + getForeground() + ", " + getBackground() + ")";
    }


	/**
	 * @return the highlight
	 */
	public boolean isHighlight() {
		return highlight;
	}


	/**
	 * @param highlight the highlight to set
	 */
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
}