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

package com.sangupta.consoles.core;

/**
 * Defines the screen position of cursor.
 * 
 * @author sangupta
 *
 */
public class ScreenPosition {
	
	private int row;
	
	private int column;
	
	/**
	 * Default construction - sets the position to (0, 0)
	 */
	public ScreenPosition() {
		this.row = 0;
		this.column = 0;
	}
	
	public ScreenPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public void setPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Check if the screen position is same as represented by the row, and
	 * column provided.
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean equals(int row, int column) {
		return this.row == row && this.column == column;
	}
	
	// Usual accessors follow

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	
}
