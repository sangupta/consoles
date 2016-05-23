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

package com.sangupta.consoles.oldui;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

/**
 * Handles mouse selection events.
 * 
 * @author sangupta
 *
 */
public class MouseHandler extends MouseInputAdapter {

	private SwingTerminal terminal;
	
	private ScreenPosition start;
	
	private ScreenPosition current;
	
	private boolean copyingMode = false;
	
	private final ClipboardHandler clipboardController;
	
	MouseHandler(SwingTerminal swingTerminal) {
		this.terminal = swingTerminal;
		this.clipboardController = new ClipboardHandler(this.terminal);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// check if the user clicked on right button
		if(e.getButton() == MouseEvent.BUTTON3) {
			// this a paste action
			if(!this.copyingMode) {
				this.clipboardController.processPasteAction();
			}
		}
		
		this.copyingMode = false;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// check if the user clicked on button 1
		if(e.getButton() == MouseEvent.BUTTON1) {
			// we need to start selection
			this.start = this.terminal.getScreenPosition(e.getPoint());
			this.terminal.highlightBox(this.start);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// check if the user clicked on button 1
		switch(e.getButton()) {
			case MouseEvent.BUTTON3:
				copyCurrentSelectionToClipboard();
				break;
		}
	}
	
	/**
	 * Copy current selection to clipboard.
	 * 
	 */
	private void copyCurrentSelectionToClipboard() {
		if(this.start == null || this.current == null) {
			return;
		}
		
		this.copyingMode = true;
		// find out the box between the start and current positions
		int x1, x2, y1, y2;
		x1 = Math.min(start.getColumn(), current.getColumn());
		x2 = Math.max(start.getColumn(), current.getColumn());
		
		y1 = Math.min(start.getRow(), current.getRow());
		y2 = Math.max(start.getRow(), current.getRow());
		
		// read text from this as string
		this.clipboardController.copyTextToClipboard(x1, y1, x2, y2);

		// clean up selection
		this.start = null;
		this.current = null;
		this.terminal.unHighlight();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(this.start != null) {
			ScreenPosition current = this.terminal.getScreenPosition(e.getPoint());
			
			if(current.equals(this.current)) {
				// nothing to do
				return;
			}
			
			this.current = current;
			
			if(this.start.equals(current)) {
				return;
			}

			// find out the area that we have and mark all as highlighted
			// or non-highlighted depending if they fall in the area or not
			int x1, x2, y1, y2;
			x1 = Math.min(start.getColumn(), current.getColumn());
			x2 = Math.max(start.getColumn(), current.getColumn());
			
			y1 = Math.min(start.getRow(), current.getRow());
			y2 = Math.max(start.getRow(), current.getRow());
			
			// now find the block to be cleaned
			this.terminal.highlightBox(x1, y1, x2, y2);
		}
	}
	
}