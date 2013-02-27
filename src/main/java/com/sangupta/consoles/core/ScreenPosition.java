/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright (C) 2009-2010 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/

package com.sangupta.consoles.core;

/**
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
