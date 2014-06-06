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

package com.sangupta.consoles.swing;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Queue;

import com.sangupta.consoles.core.InputKey;
import com.sangupta.consoles.core.SpecialInputKey;

/**
 * Key listener that listens for keys inside the host frame of {@link SwingTerminal}
 * and sends back them to the terminal so that they can be sent back to the console.
 * 
 * @author sangupta
 *
 */
public class InputKeyListener implements KeyListener {
	
	/**
	 * My reference to the queue containing key-strokes, this
	 * is supplied by the initializing code, and not in this
	 * class.
	 * 
	 */
	private final Queue<InputKey> inputKeys;
	
	/**
	 * Create an instance of this listener
	 * @param inputKeys
	 */
	public InputKeyListener(Queue<InputKey> inputKeys) {
		this.inputKeys = inputKeys;
	}

	/**
	 * Read standard keys from the keyboard.
	 * 
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		char ch = e.getKeyChar();
		boolean altKeyPressed = (e.getModifiersEx() & InputEvent.ALT_DOWN_MASK) != 0;
		boolean ctrlKeyPressed = (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0;
		
		if(ctrlKeyPressed) {
			ch = (char)('a' - 1 + ch);
			
			this.inputKeys.add(new InputKey(ch, altKeyPressed, ctrlKeyPressed));
		} else {
			this.inputKeys.add(new InputKey(e.getKeyChar()));
		}
		
//		System.out.println((int) e.getKeyChar() + ":" + e.getKeyChar());
	}

	/**
	 * Read special keys from the key-board. These keys are not
	 * available to be read via the {@link #keyTyped(KeyEvent)} method.
	 * 
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		final int keyCode = e.getKeyCode();
		
		switch(keyCode) {
			case KeyEvent.VK_LEFT:
				this.inputKeys.add(new InputKey(SpecialInputKey.LeftArrow));
				break;
				
			case KeyEvent.VK_RIGHT:
				this.inputKeys.add(new InputKey(SpecialInputKey.RightArrow));
				break;
				
			case KeyEvent.VK_UP:
				this.inputKeys.add(new InputKey(SpecialInputKey.UpArrow));
				break;
				
			case KeyEvent.VK_DOWN:
				this.inputKeys.add(new InputKey(SpecialInputKey.DownArrow));
				break;
				
			case KeyEvent.VK_INSERT:
				this.inputKeys.add(new InputKey(SpecialInputKey.Insert));
				break;
				
			case KeyEvent.VK_DELETE:
				this.inputKeys.add(new InputKey(SpecialInputKey.Delete));
				break;
				
			case KeyEvent.VK_HOME:
				this.inputKeys.add(new InputKey(SpecialInputKey.Home));
				break;
				
			case KeyEvent.VK_END:
				this.inputKeys.add(new InputKey(SpecialInputKey.End));
				break;
				
			case KeyEvent.VK_PAGE_UP:
				this.inputKeys.add(new InputKey(SpecialInputKey.PageUp));
				break;
				
			case KeyEvent.VK_PAGE_DOWN:
				this.inputKeys.add(new InputKey(SpecialInputKey.PageDown));
				break;
				
			case KeyEvent.VK_F1:
				this.inputKeys.add(new InputKey(SpecialInputKey.F1));
				break;

			case KeyEvent.VK_F2:
				this.inputKeys.add(new InputKey(SpecialInputKey.F2));
				break;

			case KeyEvent.VK_F3:
				this.inputKeys.add(new InputKey(SpecialInputKey.F3));
				break;

			case KeyEvent.VK_F4:
				this.inputKeys.add(new InputKey(SpecialInputKey.F4));
				break;

			case KeyEvent.VK_F5:
				this.inputKeys.add(new InputKey(SpecialInputKey.F5));
				break;

			case KeyEvent.VK_F6:
				this.inputKeys.add(new InputKey(SpecialInputKey.F6));
				break;

			case KeyEvent.VK_F7:
				this.inputKeys.add(new InputKey(SpecialInputKey.F7));
				break;

			case KeyEvent.VK_F8:
				this.inputKeys.add(new InputKey(SpecialInputKey.F8));
				break;

			case KeyEvent.VK_F9:
				this.inputKeys.add(new InputKey(SpecialInputKey.F9));
				break;

			case KeyEvent.VK_F10:
				this.inputKeys.add(new InputKey(SpecialInputKey.F10));
				break;

			case KeyEvent.VK_F11:
				this.inputKeys.add(new InputKey(SpecialInputKey.F11));
				break;

			case KeyEvent.VK_F12:
				this.inputKeys.add(new InputKey(SpecialInputKey.F12));
				break;

		}
		
//		System.out.println((int) e.getKeyChar() + ":" + e.getKeyChar());
	}

	/**
	 * No need to handle this method
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// do nothing
	}

}