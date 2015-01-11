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


import java.util.EventObject;

/** 
 * <code>TextWindowEvents</code> are fired when <code>TextWindows</code> are resized. 
 *
 *@status.stable
 *@author Ethan Royael Nicholas (<a href="mailto:ethan@ethannicholas.com">ethan@ethannicholas.com</a>)
 */
public class TextWindowEvent extends EventObject {
	
    /**
	 * Generated via Eclipse 
	 */
	private static final long serialVersionUID = 2039504759303682084L;

	/** 
	 * The event ID that indicates a resize event. 
	 */
    public static final int RESIZED  = 12000;
    
    private int id;

    /** 
     * Constructs a new <code>TextWindowEvent</code> with the specified source and id.
     *
     *@param source the <code>TextWindow</code> which generated this event
     *@param id the event ID
     */
    public TextWindowEvent(TextWindow source, int id) {
        super(source);
        this.id = id;
    }
    

    /** 
     * Returns the ID of this event.
     */
    public int getID() {
        return id;
    }
}