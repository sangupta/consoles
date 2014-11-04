package com.sangupta.consoles.swing;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.sangupta.consoles.core.InputKey;
import com.sangupta.consoles.ui.UITerminal;

/**
 * Class that handles all functionality related to
 * keyboard and its keystrokes. The way the console
 * behaves and gathers stuff is coded in this class.
 * 
 * @author sangupta
 *
 */
public class KeyboardHandler {
	
	/**
	 * The {@link UITerminal} to use
	 */
	protected final UITerminal terminal;
	
	/**
	 * If we are closing this terminal or not
	 */
	protected volatile boolean closingTerminal;

	/**
	 * Holds the list of key-strokes as they keep coming in
	 */
	protected final Queue<InputKey> inputKeys;
	
	/**
	 * Initialize objects
	 * 
	 */
	public KeyboardHandler(UITerminal terminal) {
		this.terminal = terminal;
				
        // setup basic keyboard handling
		// set up key listeners
		this.inputKeys = new ConcurrentLinkedQueue<InputKey>();
		this.terminal.addKeyListener(new InputKeyListener(this.inputKeys));
	}
	
	/**
	 * 
	 */
	public char readChar() {
		InputKey key = this.getKey(true);
		if(key != null) {
			return key.ch;
		}
		
		return (char) 0;
	}

	/**
	 * 
	 */
	public String readLine() {
		return this.readString(true, (char) 0);
	}

	/**
	 * 
	 */
	public char[] readPassword() {
		return this.readString(false, (char) 0).toCharArray();
	}

	/**
	 * 
	 */
	public char[] readPassword(char mask) {
		return this.readString(true, mask).toCharArray();
	}

	public InputKey getKey(boolean echo) {
		InputKey key = null;
		while(key == null) {
			if(this.closingTerminal) {
				return null;
			}
			
			key = this.inputKeys.poll();
		}
		
		// check if we have a key trap handler over this key
//		if(key != null && this.hasKeyTraps && this.keyTrapHandlers != null) {
//			boolean hasTrap = this.keyTrapHandlers.containsKey(key);
//			if(hasTrap) {
//				List<KeyTrapHandler> handlers = this.keyTrapHandlers.get(key);
//		
//				boolean bubbleEvent = true;
//				for(KeyTrapHandler handler : handlers) {
//					bubbleEvent = handler.handleKeyInvocation(key);
//					if(!bubbleEvent) {
//						continue;
//					}
//				}
//			}
//		}
		
		if(echo) {
			this.terminal.outputImmediately(key.ch);
		}
		
		return key;
	}
	
	/**
	 * Method that reads a string from the terminal and sends it back.
	 * 
	 */
	public String readString(boolean echo, char mask) {
		final StringBuilder builder = new StringBuilder();
		
		// show the cursor from start
		this.terminal.setCursorType(CursorType.CURSOR_OVERSTRIKE);
		
		InputKey key;
		while(true) {
			key = this.getKey(false);

			if(this.closingTerminal) {
				break;
			}
			
			this.terminal.setCursorType(CursorType.CURSOR_INVISIBLE);
			
			// ENTER
			if(key.ch == '\n' && !key.altPressed && !key.ctrlPressed) {
				this.terminal.hideCursor();
				
				// TODO: fix this to just move one row
				this.terminal.outputImmediately('\n');
				break;
			}
			
			processEnteredKey(echo, mask, builder, key);
			
			this.terminal.setCursorType(CursorType.CURSOR_OVERSTRIKE);
		}
		
		// hide the cursor again
		this.terminal.hideCursor();
		return builder.toString();
	}

	/**
	 * Process the entered key to be appended to a string input.
	 * 
	 * @param echo
	 * @param mask
	 * @param builder
	 * @param key
	 */
	private void processEnteredKey(boolean echo, char mask, final StringBuilder builder, InputKey key) {
		// ESCAPE
		if((int) key.ch == 27) {
			int length = builder.length();
			builder.setLength(0);
			
			// TODO: optimize this call for performance
			for(int index = 0; index < length; index++) {
				this.setRelativeChar(-1, ' ');
			}
			return;
		}
		
		// BACKSPACE
		if(key.ch == 8) {
			if(builder.length() > 0) {
				builder.deleteCharAt(builder.length() - 1);
			} else {
				return;
			}
			
			this.setRelativeChar(-1, ' ');
			return;
		}
		
		// SPECIAL KEYS
		if(key.specialKey != null) {
			
			switch(key.specialKey) {
				
				case LeftArrow:
					this.moveCursor(-1);
					return;
				
				case PageUp:
					this.terminal.pageUp();
					return;
					
				case PageDown:
					this.terminal.pageDown();
					return;

				default:
					// do nothing
					break;
			}
		}
		
		// all well
		// add the character to string
		builder.append(key.ch);
		
		// display on screen as needed
		if(echo) {
			if(mask == 0) {
				writeChar(key.ch);
			} else {
				writeChar(mask);
			}
		}
	}
	
	private synchronized void setRelativeChar(int delta, char c) {
		this.moveCursor(delta);
		this.terminal.outputImmediately(c);
		this.moveCursor(delta);
	}

	private void writeChar(char c) {
		if(c == 0) {
			return;
		}
		
		this.terminal.outputImmediately(c);
	}
	
    public void moveCursor(int delta) {
        int newCursorX = this.terminal.getCursorX() + delta;
        int newCursorY = this.terminal.getCursorY();
        while (newCursorX < 0) {
            newCursorY--;
            newCursorX += this.terminal.getColumns();
        }
        while (newCursorX >= this.terminal.getColumns()) {
            newCursorY++;
            newCursorX -= this.terminal.getColumns();
        }
        while (newCursorY >= this.terminal.getRows()) {
        	this.terminal.insertRow(this.terminal.getRows() - 1);
            newCursorY--;
        }
        
        this.terminal.setCursorPosition(newCursorX, newCursorY);
    }
    
    public void shutDown() {
    	this.closingTerminal = true;
    }
}
