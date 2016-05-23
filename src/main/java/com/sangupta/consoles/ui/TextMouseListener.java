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


import java.util.EventListener;

/**
 * A listener which may be notified when mouse actions occur on a <code>TextWindow</code>
 *
 *@status.stable
 *@see TextMouseEvent 
 *@see TextMouseAdapter
 */
public interface TextMouseListener extends EventListener {
    /**
     * Invoked when the mouse has been clicked on a <code>TextWindow</code>.
     *
     *@param e the <code>TextMouseEvent</code> object encapsulating the event
     */
    void mouseClicked(TextMouseEvent e);

    /**
     * Invoked when the mouse has been pressed on a <code>TextWindow</code>.
     *
     *@param e the <code>TextMouseEvent</code> object encapsulating the event
     */
    void mousePressed(TextMouseEvent e);

    /**
     * Invoked when the mouse has been released on a <code>TextWindow</code>.
     *
     *@param e the <code>TextMouseEvent</code> object encapsulating the event
     */
    void mouseReleased(TextMouseEvent e);
}