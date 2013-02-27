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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Queue;

import com.sangupta.consoles.core.InputKey;

/**
 * Key listener that listens for keys inside the host frame of {@link SwingTerminal}
 * and sends back them to the terminal so that they can be sent back to the console.
 * 
 * @author sangupta
 *
 */
public class InputKeyListener implements KeyListener {
	
	private final Queue<InputKey> inputKeys;
	
	public InputKeyListener(Queue<InputKey> inputKeys) {
		this.inputKeys = inputKeys;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char ch = e.getKeyChar();
		boolean altKeyPressed = (e.getModifiersEx() & InputEvent.ALT_DOWN_MASK) != 0;
		boolean ctrlKeyPressed = (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0;
		
		if(ctrlKeyPressed) {
			ch = (char)('a' - 1 + ch);
			
			this.inputKeys.add(new InputKey(ch, altKeyPressed, ctrlKeyPressed));
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.inputKeys.add(new InputKey(e.getKeyChar()));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// do nothing
	}

}
