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

	private static final Font FONT = new Font("Courier New", Font.PLAIN, 14);
	
	private FontMetrics fontMetrics;
	
	private int characterWidth;
	
	private int numRows;
	
	private int numColumns;
	
	private Dimension dimension;
	
	public Renderer(int columns, int rows) {
		this.numColumns = columns;
		this.numRows = rows;
	}
	
	public Dimension getPreferredSize() {
		if(this.dimension != null) {
			return this.dimension;
		}
		
		if(this.fontMetrics == null) {
			this.fontMetrics = getGraphics().getFontMetrics(FONT);
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
		super.paintComponent(g);
		
		final Graphics2D graphics2D = (Graphics2D)g.create();
		
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
	
	public FontMetrics getFontMetrics() {
		return fontMetrics;
	}

	public int getCharacterWidth() {
		return characterWidth;
	}

}
