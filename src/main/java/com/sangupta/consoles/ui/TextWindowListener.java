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

package com.sangupta.consoles.ui;


import java.util.EventListener;

/** 
 * A listener for receiving TextWindowEvents.  TextWindowEvents are
 * fired in response to resize events.
 * 
 *@status.stable
 *@author Ethan Royael Nicholas (<a href="mailto:ethan@ethannicholas.com">ethan@ethannicholas.com</a>)
 */
public interface TextWindowListener extends EventListener {
    /** 
     * Invoked when the number of rows or columns in the
     * TextWindow change.  Some operations such as changing
     * font size may cause the TextWindow's displayed size
     * to alter without changing the number of rows or
     * columns displayed; no event is generated in this
     * case.
     */
    void textWindowResized(TextWindowEvent e);
}
