package com.sangupta.consoles.swing;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.sangupta.consoles.core.InputKey;
import com.sangupta.consoles.core.KeyTrapHandler;
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
	 * List of all key traps
	 */
	protected final ConcurrentMap<InputKey, List<KeyTrapHandler>> keyTraps;
	
	/**
	 * Whether we have key traps or not
	 */
	protected volatile boolean hasKeyTraps = false;
	
	/**
	 * Initialize objects
	 * 
	 */
	public KeyboardHandler(UITerminal terminal) {
		this.terminal = terminal;
		this.keyTraps = new ConcurrentHashMap<InputKey, List<KeyTrapHandler>>();
				
        // setup basic keyboard handling
		// set up key listeners
		this.inputKeys = new ConcurrentLinkedQueue<InputKey>();
		this.terminal.addKeyListener(new InputKeyListener(this.inputKeys));
	}
	
	/**
	 * Read a character from the keyboard waiting for user input.
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
	 * Read a line from the keyboard waiting for user input.
	 * 
	 */
	public String readLine() {
		return this.readString(true, (char) 0);
	}

	/**
	 * Read password as character array from the user.
	 * 
	 */
	public char[] readPassword() {
		return this.readString(false, (char) 0).toCharArray();
	}

	/**
	 * Read the password as a character array.
	 * 
	 * @return the character array representing the password
	 */
	public char[] readPassword(char mask) {
		return this.readString(true, mask).toCharArray();
	}

	/**
	 * Add the keyboard handler trap for the given key.
	 * 
	 * @param inputKey
	 *            the key for which the trap is to be placed
	 * 
	 * @param keyTrapHandler
	 *            the handler that needs to be invoked
	 */
	public void addKeyTrap(InputKey inputKey, KeyTrapHandler keyTrapHandler) {
		if(inputKey == null) {
			throw new IllegalArgumentException("Input key to trap cannot be null");
		}
		
		if(keyTrapHandler == null) {
			throw new IllegalArgumentException("Keytrap handler cannot be null");
		}

		List<KeyTrapHandler> currentHandlers = keyTraps.get(inputKey);
		if(currentHandlers == null) {
			// let's add a new list
			List<KeyTrapHandler> handlers = new ArrayList<KeyTrapHandler>();
			handlers.add(keyTrapHandler);
			
			currentHandlers = keyTraps.putIfAbsent(inputKey, handlers);
			if(currentHandlers == null) {
				// this was the first handler to be added
				this.hasKeyTraps = true;
				return;
			}
		}
		
		// add the item to list
		currentHandlers.add(keyTrapHandler);
		this.hasKeyTraps = true;
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
		if(key != null && this.hasKeyTraps) {
			boolean hasTrap = this.keyTraps.containsKey(key);
			if(hasTrap) {
				List<KeyTrapHandler> handlers = this.keyTraps.get(key);
		
				boolean bubbleEvent = true;
				for(KeyTrapHandler handler : handlers) {
					bubbleEvent = handler.handleKeyInvocation(key);
					if(!bubbleEvent) {
						// TODO: we may need a relook here
						return null;
					}
				}
			}
		}
		
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
		final AtomicInteger position = new AtomicInteger(0);
		
		// show the cursor from start
		this.terminal.setCursorType(CursorType.CURSOR_OVERSTRIKE);
		
		InputKey key;
		while(true) {
			key = this.getKey(false);

			if(this.closingTerminal) {
				break;
			}
			
			// this may happen when one of the handlers cancelled the input
			if(key == null) {
				continue;
			}
			
			this.terminal.setCursorType(CursorType.CURSOR_INVISIBLE);
			
			// ENTER
			if(key.ch == '\n' && !key.altPressed && !key.ctrlPressed) {
				this.terminal.hideCursor();
				
				// TODO: fix this to just move one row
				this.terminal.outputImmediately('\n');
				break;
			}
			
			processEnteredKey(echo, mask, builder, key, position);
			
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
	private void processEnteredKey(boolean echo, char mask, final StringBuilder builder, final InputKey key, final AtomicInteger position) {
		// ESCAPE
		if((int) key.ch == 27) {
			int length = builder.length();
			builder.setLength(0);
			
			// TODO: optimize this call for performance
			for(int index = 0; index < length; index++) {
				this.setRelativeChar(-1, ' ');
			}
			
			position.set(0);
			return;
		}
		
		// BACKSPACE
		if(key.ch == 8) {
			if(builder.length() == 0) {
				return;
			}

			builder.deleteCharAt(builder.length() - 1);
			position.decrementAndGet();
			this.setRelativeChar(-1, ' ');
			return;
		}
		
		// SPECIAL KEYS
		if(key.specialKey != null) {
			
			switch(key.specialKey) {
				
				case LeftArrow:
					this.moveCursor(-1);
					position.decrementAndGet();
					return;
				
				case PageUp:
					this.terminal.pageUp();
					return;
					
				case PageDown:
					this.terminal.pageDown();
					return;
					
				case RightArrow:
					if(position.get() < builder.length()) {
						this.moveCursor(1);
						position.incrementAndGet();
					}
					return;

				default:
					// do nothing
					break;
			}
		}
		
		// all well
		// add the character to string
		position.incrementAndGet();
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
	
	/**
	 * Set the character relative to current cursor position to the given value.
	 * 
	 * @param delta
	 *            the relative character position
	 * 
	 * @param c
	 *            the char to which the character needs to be set
	 * 
	 */
	private synchronized void setRelativeChar(int delta, char c) {
		this.moveCursor(delta);
		this.terminal.outputImmediately(c);
		this.moveCursor(delta);
	}

	/**
	 * Write the given character to the terminal immediately.
	 * 
	 * @param c
	 *            the char to be written
	 */
	private void writeChar(char c) {
		if(c == 0) {
			return;
		}
		
		this.terminal.outputImmediately(c);
	}
	
	/**
	 * Move the cursor left or right by the given amount. Moving also takes care
	 * of the row change if needed.
	 * 
	 * @param delta
	 *            the amount by which the cursor needs to be moved, can be
	 *            negative.
	 * 
	 */
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
    
    /**
     * Shutdown this keyboard handler as the terminal is closing down.
     * 
     */
    public void shutDown() {
    	this.closingTerminal = true;
    }

}
