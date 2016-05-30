package com.sangupta.consoles.ui;

import java.awt.event.MouseEvent;

import com.sangupta.consoles.oldui.ScreenPosition;

/**
 * Handles selection of text area using Mouse.
 * 
 * @author sangupta
 *
 */
public class MouseHighlightHandler implements TextMouseListener {
	
	private final UITerminal terminal;
	
	private volatile boolean copyingMode = false;
	
	private ScreenPosition start;
	
	private ScreenPosition current;
	
	public MouseHighlightHandler(UITerminal terminal) {
		this.terminal = terminal;
	}
	
	@Override
	public void mouseClicked(TextMouseEvent e) {
		// check if the user clicked on right button
		if(e.getButton() == MouseEvent.BUTTON3) {
			// this a paste action
			if(!this.copyingMode) {
//				this.clipboardController.processPasteAction();
				System.out.println("Paste clipboard data");
			}
		}
		
		this.copyingMode = false;
	}
	
	@Override
	public void mousePressed(TextMouseEvent e) {
		// check if the user clicked on button 1
		if(e.getButton() == MouseEvent.BUTTON1) {
			// we need to start selection
			int column = e.getX();
			int row = e.getY();
			this.start = new ScreenPosition(row, column);
			
			this.terminal.highlightSelection(row, column, row, column);
		}
	}
	
	@Override
	public void mouseDragged(TextMouseEvent e) {
		if(this.start == null) {
			// nothing to do for us
			return;
		}

		ScreenPosition current = new ScreenPosition(e.getY(), e.getX());
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
		this.terminal.highlightSelection(x1, y1, x2, y2);
	}
	
	@Override
	public void mouseReleased(TextMouseEvent e) {
		// check if the user clicked on button 1
		switch(e.getButton()) {
			case MouseEvent.BUTTON3:
//				copyCurrentSelectionToClipboard();
				break;
		}
	}
}
