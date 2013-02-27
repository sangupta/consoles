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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class Renderer extends JComponent {

	/**
	 * Generated via Eclipse
	 */
	private static final long serialVersionUID = -4588816081786680739L;

	/**
	 * Default font for this renderer object.
	 * 
	 */
	private static final Font FONT = new Font("Courier New", Font.PLAIN, 14);
	
	/**
	 * The currently associated {@link FontMetrics} for the current {@link Font}.
	 *  
	 */
	private FontMetrics fontMetrics;
	
	/**
	 * Width of one character in this {@link Renderer} based on the currently selected {@link Font}.
	 * 
	 */
	private int characterWidth;
	
	/**
	 * The number of rows that this renderer needs to support.
	 * 
	 */
	private int numRows;
	
	/**
	 * The number of columns that this renderer needs to support.
	 * 
	 */
	private int numColumns;
	
	/**
	 * The current dimensions of this instance.
	 * 
	 */
	private Dimension dimension;
	
	/**
	 * Reference to the screen view full of information. The exact screen view is kept
	 * by the parent {@link SwingTerminal} instance.
	 * 
	 */
	private TerminalCharacter screenView[][];
	
	/**
	 * Create an instance of {@link Renderer} for the given number of rows and columns.
	 * 
	 * @param columns
	 * @param rows
	 */
	public Renderer(int columns, int rows, TerminalCharacter screenView[][]) {
		this.numColumns = columns;
		this.numRows = rows;
		this.screenView = screenView;
	}
	
	/**
	 * Get the preferred size of this {@link Renderer} instance.
	 * 
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		if(this.dimension != null) {
			return this.dimension;
		}
		
		if(this.fontMetrics == null) {
			this.fontMetrics = getGraphics().getFontMetrics(FONT);
			this.characterWidth = this.fontMetrics.charWidth(' ');
		}
		
		recomputeDimension();
		return this.dimension;
	}
	
	/**
	 * Method that repaints the screen at the current cursor position.
	 * 
	 */
	public void repaintCursorPosition() {
		throw new IllegalStateException("not yet implemented");
	}

	/**
	 * Method that redraws the entire screen state.
	 * 
	 */
	@Override
	protected void paintComponent(Graphics g) {
		try {
		final Graphics2D graphics2D = (Graphics2D) g.create();
		
		// build up the instance
		graphics2D.setFont(FONT);
		graphics2D.fillRect(0, 0, this.dimension.width, this.dimension.height);
		
		// start rendering the characters
		TerminalCharacter currentChar;
		String charString;
		
		for(int row = 0; row < this.numRows; row++) {
			for(int column = 0; column < this.numColumns; column++) {
				currentChar = this.screenView[row][column];
				
				if(currentChar != null) {
					charString = Character.toString(currentChar.character);
					graphics2D.setColor(currentChar.foreground);
					graphics2D.setBackground(currentChar.background);
				} else {
					charString = " ";
				}
				
				graphics2D.drawString(charString, column * this.characterWidth, ((row + 1) * this.fontMetrics.getHeight()) - this.fontMetrics.getDescent());
			}
		}
		
		graphics2D.dispose();
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}
	
	/**
	 * Following method can be used to reset dimension in case number
	 * of rows/columns change on screen.
	 * 
	 */
	private void recomputeDimension() {
		int width = this.numColumns * this.fontMetrics.charWidth(' ');
		int height = this.numRows * this.fontMetrics.getHeight();
		this.dimension = new Dimension(width, height);
	}
	
	/**
	 * Return the {@link FontMetrics} instance associated with this {@link Renderer} object.
	 * 
	 * @return
	 */
	public FontMetrics getFontMetrics() {
		return this.fontMetrics;
	}

	/**
	 * Return the width of one character in the currently set font.
	 * 
	 * @return
	 */
	public int getCharacterWidth() {
		return this.characterWidth;
	}

}
