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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Java Swing based terminal that uses {@link JFrame} to render the terminal.
 * 
 * @author sangupta
 *
 */
public class SwingTerminal {
	
	/**
	 * Number of default columns in a terminal
	 */
	private static final int DEFAULT_COLUMNS = 80;
	
	/**
	 * Number of default rows in a terminal
	 */
	private static final int DEFAULT_ROWS = 25;
	
	/**
	 * Default background color for a terminal
	 */
	private static final Color BACKGROUND_COLOR = new Color(0, 0, 0);
	
	/**
	 * Default foreground color for a terminal
	 */
	private static final Color FOREGROUND_COLOR = new Color(255, 255, 255);
	
	/**
	 * Reference to the internal {@link JFrame} instance.
	 */
	private final JFrame hostFrame;
	
	/**
	 * Reference to the internal {@link Renderer} instance. The renderer
	 * picks up the screen view and displays in the current frame instance.
	 * The renderer is responsible to display one screen-size of information.
	 * This screen-view is provided by this {@link SwingTerminal} instance.
	 * 
	 */
	private final Renderer renderer;
	
	/**
	 * An instance of the empty character represented by a <b>SPACE</b> character
	 * in the currently set foreground/background color.
	 * 
	 */
	private final TerminalCharacter emptyCharacter;
	
	/**
	 * Holds one screen-view of information for this console.
	 * 
	 */
	private final TerminalCharacter screenView[][];
	
	/**
	 * Timer that is used to show/hide cursor to achieve cursor blinking
	 * effect
	 */
	private final Timer cursorBlinkTimer;
	
	/**
	 * Indicates the current state of cursor blink - visible and invisible
	 */
	private boolean cursorBlinkVisible = false;
	
	/**
	 * Default constructor - uses the default number of rows and columns
	 * to construct and instance.
	 * 
	 */
	public SwingTerminal() {
		this(DEFAULT_COLUMNS, DEFAULT_ROWS);
	}

	/**
	 * Construct an instance of Swing based terminal instance with the given
	 * number of rows and columns. The pixel-size of the instance is obtained
	 * by the height/width of a character in the font associated with the
	 * renderer instance.
	 * 
	 * @param defaultColumns
	 * @param defaultRows
	 */
	public SwingTerminal(int defaultColumns, int defaultRows) {
		this.hostFrame = new JFrame();
		this.cursorBlinkTimer = new Timer(500, new CursorBlinkAction());
		
		this.emptyCharacter = new TerminalCharacter();
		this.screenView = new TerminalCharacter[defaultRows][defaultColumns];
		
		this.renderer = new Renderer(defaultColumns, defaultRows, this.screenView);
		
		// initialize
		this.hostFrame.getContentPane().add(this.renderer);
		this.hostFrame.pack();
		
		this.hostFrame.setLocationByPlatform(true);
		this.hostFrame.setResizable(false);

		this.hostFrame.setSize(this.renderer.getPreferredSize());
		
		// add the closing handler for the terminal
		this.hostFrame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				closeTerminal();
			}
			
		});

		this.hostFrame.setVisible(true);
		this.hostFrame.pack();
	}
	
	/**
	 * Close this terminal and dispose of all associated resources.
	 * 
	 */
	public void closeTerminal() {
		this.hostFrame.setVisible(false);
		this.hostFrame.dispose();
	}
	
	/**
	 * Set the title of the window being used.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.hostFrame.setTitle(title);
	}
	
	/**
	 * Write a string to the terminal and repaint.
	 * 
	 * @param string
	 */
	public void writeString(String string) {
		if(string == null) {
			return;
		}

		this.write(string);
		this.refresh();
	}
	
	/**
	 * Write a string to the renderer. This will not repaint the
	 * renderer. Method {@link SwingTerminal#refresh()} must be called
	 * to explicitly make the string visible.
	 * 
	 * @param string
	 */
	public void write(String string) {
		if(string == null) {
			return;
		}
		
		int length = string.length();
		char[] chars = string.toCharArray();
		for(int index = 0; index < length; index++) {
			this.screenView[0][index] = new TerminalCharacter(chars[index], FOREGROUND_COLOR, BACKGROUND_COLOR);
		}
	}
	
	/**
	 * Clear this terminal. This basically means that we need to render the empty
	 * character in the entire screen space.
	 * 
	 */
	public void clearTerminal() {
		this.emptyCharacter.background = BACKGROUND_COLOR;
		this.emptyCharacter.foreground = FOREGROUND_COLOR;
		this.emptyCharacter.character = ' ';
		
		for(int row = 0; row < this.screenView.length; row++) {
			for(int column = 0; column < this.screenView[row].length; column++) {
				this.screenView[row][column] = this.emptyCharacter;
			}
		}
		
		this.moveCursor(0, 0);
	}
	
	/**
	 * Move the cursor to the designated position on screen.
	 * 
	 * @param row
	 * @param column
	 */
	public void moveCursor(int row, int column) {
		
	}
	
	/**
	 * Refresh the internal renderer
	 */
	public void refresh() {
		this.renderer.repaint();
	}

	// Other included classes follow
	
	/**
	 * Action listener that mimicks cursor blinking effect
	 * by showing/hiding cursor every few milli-seconds (500ms by
	 * default).
	 * 
	 * @author sangupta
	 *
	 */
	private class CursorBlinkAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			cursorBlinkVisible = !cursorBlinkVisible;
			renderer.repaintCursorPosition();
		}
		
	}
}
