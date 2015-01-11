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

package com.sangupta.consoles.ui;

import java.awt.Component;
import java.awt.event.MouseEvent;

/** 
 * <code>TextMouseEvents</code> are fired in response to mouse clicks on <code>TextWindows</code>
 * which feature mouse support.  The coordinates of the mouse click indicate the character which
 * was clicked (not the actual pixel coordinates), and there is also a bias which indicates
 * whether the left or right side of the character was clicked. 
 *
 *@status.stable
 *@see TextMouseEvent 
 *@see TextMouseAdapter
 *@author Ethan Royael Nicholas (<a href="mailto:ethan@ethannicholas.com">ethan@ethannicholas.com</a>)
 */
public class TextMouseEvent extends MouseEvent {
	
    /**
	 * Generated via Eclipse
	 */
	private static final long serialVersionUID = -3656135324165734941L;

	/** Indicates that the mouse is positioned toward the left side of the character. */
    public static final int BIAS_LEFT  = 0;

    /** Indicates that the mouse is positioned toward the right side of the character. */
    public static final int BIAS_RIGHT = 1;
    
    private int bias;
    
    /** 
     * Constructs a new <code>TextMouseEvent</code> with the specified source component, type, 
     * modifiers, coordinates, and click count.
     *
     *@param source the Component that originated the event
     *@param id the integer that identifies the event
     *@param when a long int that gives the time the event occurred
     *@param modifiers the modifier keys down during event (e.g. shift, ctrl, alt, meta) Either extended _DOWN_MASK or old _MASK modifiers should be used, but both models should not be mixed in one event. Use of the extended modifiers is preferred.
     *@param x the horizontal x coordinate for the mouse location
     *@param y the vertical y coordinate for the mouse location
     *@param clickCount the number of mouse clicks associated with event
     *@param popupTrigger a boolean, true if this event is a trigger for a popup menu
     */
    public TextMouseEvent(Component source, int id, long when, int modifiers, int x, int y, 
                                int clickCount, boolean popupTrigger, int bias) {
        super(source, id, when, modifiers, x, y, clickCount, popupTrigger);
        if (bias != BIAS_LEFT && bias != BIAS_RIGHT)
            throw new IllegalArgumentException("bias must be BIAS_LEFT or BIAS_RIGHT");
        this.bias = bias;
    }
    

    /** 
     * Returns the side of the character on which the mouse was pressed, either <code>BIAS_LEFT</code>
     * or <code>BIAS_RIGHT</code>.
     */
    public int getBias() {
        return bias;
    }
}