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

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Java Swing based terminal that uses {@link JFrame} to render the terminal.
 * 
 * @author sangupta
 *
 */
public class SwingTerminal {
	
	private static final int DEFAULT_COLUMNS = 80;
	
	private static final int DEFAULT_ROWS = 25;
	
	private static final Color BACKGROUND_COLOR = new Color(0, 0, 0);
	
	private static final Color FOREGROUND_COLOR = new Color(255, 255, 255);
	
	private final JFrame hostFrame;
	
	private final Renderer renderer;
	
	/**
	 * Timer that is used to show/hide cursor to achieve cursor blinking
	 * effect
	 */
	private final Timer cursorBlinkTimer;
	
	/**
	 * Indicates the current state of cursor blink - visible and invisible
	 */
	private boolean cursorBlinkVisible = false;
	
	public SwingTerminal() {
		this(DEFAULT_COLUMNS, DEFAULT_ROWS);
	}

	public SwingTerminal(int defaultColumns, int defaultRows) {
		this.hostFrame = new JFrame();
		this.renderer = new Renderer(defaultColumns, defaultRows);
		this.cursorBlinkTimer = new Timer(500, new CursorBlinkAction());
		
		// initialize
		this.hostFrame.getContentPane().add(this.renderer);
		this.hostFrame.pack();
		
		this.hostFrame.setLocationByPlatform(true);
//		this.hostFrame.setBackground(BACKGROUND_COLOR);
//		this.hostFrame.setForeground(FOREGROUND_COLOR);

		this.hostFrame.setSize(this.renderer.getPreferredSize());
//		this.hostFrame.setCursor(Cursor.getDefaultCursor());
//		this.hostFrame.repaint();

		this.hostFrame.setVisible(true);
		this.hostFrame.pack();
	}
	
	public void closeTerminal() {
		this.hostFrame.setVisible(false);
		this.hostFrame.dispose();
	}
	
	public void setTitle(String title) {
		this.hostFrame.setTitle(title);
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
