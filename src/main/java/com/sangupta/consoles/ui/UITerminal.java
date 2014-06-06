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

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.sangupta.consoles.swing.CursorType;
import com.sangupta.consoles.swing.SwingTerminalConstants;

/**
 * 
 * @author sangupta
 *
 */
public class UITerminal extends JComponent implements TextWindow, Scrollable {
	
    /**
	 * Generated via Eclipse
	 */
	private static final long serialVersionUID = -1998522392760699027L;

	/**
	 * Avoid creating temporary object every time
	 */
	private final Rectangle visibleRect = new Rectangle();

    /** 
     * Maximum number of rows which will ever be remembered. 
     */
    private final int scrollback;
    
    /** 
     * Number of rows currently being remembered. 
     */
    private volatile int rows;
    
    /** 
     * Number of rows being displayed at one time. 
     */
    private volatile int logicalRows;
    
    /** 
     * Number of columns in each row. 
     */
    private volatile int columns;

    /** 
     * Number of columns actually being displayed. 
     */
    private volatile int logicalColumns;
    
    private TerminalCharacterAttribute currentTextColor = SwingTerminalConstants.DEFAULT_TERMINAL_CHAR_ATTRIBUTES;

    private volatile int charWidth = 16;
    private int charHeight = 16;
    private int baseline = 9;

    private char[][] chars;                // [rows][columns]
    private TerminalCharacterAttribute[][] attributes; // [rows][columns]
    
    private CursorType cursorType;
    private boolean cursorState;
    private Color cursorColor;
    private int cursorX = -1;
    private int cursorY = -1;

    private boolean valid;
    
    /**
     * Indicates if we need to go to the bottom of
     * the screen
     */
    private boolean snapToBottom;
    
    /**
     * Indicates if we need to go to the top of the
     * screen
     */
    private boolean snapToTop;
    
    private Rectangle dirtyRegion;
    
    private final Timer cursorTimer = new Timer(SwingTerminalConstants.CURSOR_BLINK_DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cursorState = !cursorState;
                repaintChar(cursorX, cursorY);
            }
        });

    private final Timer repaintTimer = new Timer(SwingTerminalConstants.REPAINT_DELAY, new ActionListener() {
				
		public void actionPerformed(ActionEvent e) {
			synchronized (UITerminal.this) {
				final JScrollPane scrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, UITerminal.this);

				if (!UITerminal.this.valid || UITerminal.this.snapToBottom) {
					if (!UITerminal.this.valid) {
						invalidate();
						if (scrollPane != null) {
							scrollPane.validate();
						}
					}

					if (UITerminal.this.snapToBottom = true) {
						if (scrollPane != null) {
							JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
							verticalScrollBar.setValue(verticalScrollBar.getMaximum() - verticalScrollBar.getVisibleAmount());
						}

						UITerminal.this.snapToBottom = false;
					}
				}

				if (UITerminal.this.snapToTop) {
					if (scrollPane != null) {
						JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
						verticalScrollBar.setValue(verticalScrollBar.getMinimum());
					}

					UITerminal.this.snapToTop = false;
				}

				if (UITerminal.this.dirtyRegion != null) {
					repaint(dirtyRegion.x * charWidth, dirtyRegion.y * charHeight, dirtyRegion.width * charWidth, dirtyRegion.height * charHeight);
					UITerminal.this.dirtyRegion = null;
				}
			}
		}
    	
	});
    
    // start the timer
    { 
    	repaintTimer.setRepeats(false); 
    }


	/**
	 * Constructs a new <code>Java2DTextWindow</code> of the specified size. The
	 * <code>columns</code> and <code>rows</code> parameters merely control the
	 * component's initial and preferred sizes; changes to the component's size
	 * will result in these values being recomputed.
	 * 
	 * <p>
	 * The <code>scrollback</code> parameter controls the maximum number of rows
	 * that the component will remember at any given time, regardless of the
	 * number actually displayed. Therefore, the user will be able to scroll
	 * back <code>(scrollback - rows)</code> rows. The <code>rows</code>
	 * property, as previously mentioned, may change as the component is
	 * resized, but the <code>scrollback</code> property is hidden and
	 * immutable.
	 * </p>
	 */
	public UITerminal(int columns, int rows, int scrollback) {
		setFont(new Font("Monospaced", 0, 12));
		setGridSize(columns, rows, columns);
		this.scrollback = scrollback;
		this.logicalRows = rows;
		setCursorColor(SwingTerminalConstants.DEFAULT_CURSOR_COLOR);
		cursorTimer.start();
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
	}
    
	/**
	 * Set the font to be used within the console.
	 * 
	 * @param font
	 *            the {@link Font} that needs to be setup
	 * 
	 * @throws IllegalArgumentException
	 *             if the supplied {@link Font} is <code>null</code>
	 */
	public void setFont(Font font) {
		if(font == null) {
			throw new IllegalArgumentException("Font to be set cannot be null");
		}
		
		FontRenderContext context = new FontRenderContext(null, false, false);
		Rectangle2D bounds = font.getStringBounds("M", context);
		charWidth = (int) bounds.getWidth();
		charHeight = (int) bounds.getHeight();
		baseline = (int) font.getLineMetrics("g", context).getAscent();
		super.setFont(font);
		revalidate();
	}

	private synchronized void setGridSize(int columns, int rows, int logicalColumns) {
		while (cursorY >= rows) {
			scrollByOneRow();
		}

		this.logicalColumns = logicalColumns;
		if (columns == this.columns) { // not changing number of columns, use
										// more efficient algorithm
			int minRows = Math.min(rows, this.rows);
			char[][] oldChars = chars;
			if (oldChars == null) {
				oldChars = new char[0][0];
			}

			chars = new char[rows][columns];
			System.arraycopy(oldChars, 0, chars, 0, minRows);

			for (int i = this.rows; i < rows; i++) {
				char[] newRow = new char[columns];
				Arrays.fill(newRow, ' ');
				chars[i] = newRow;
			}

			TerminalCharacterAttribute[][] oldAttributes = attributes;
			if (oldAttributes == null) {
				oldAttributes = new TerminalCharacterAttribute[0][0];
			}

			attributes = new TerminalCharacterAttribute[rows][columns];
			System.arraycopy(oldAttributes, 0, attributes, 0, minRows);
			for (int i = this.rows; i < rows; i++) {
				TerminalCharacterAttribute[] newRow = new TerminalCharacterAttribute[columns];
				Arrays.fill(newRow, this.currentTextColor);
				attributes[i] = newRow;
			}
		} else { // changing number of columns, use slower algorithm
			int minRows = Math.min(rows, this.rows);
			int minColumns = Math.min(columns, this.columns);
			char[][] oldChars = chars;
			if (oldChars == null) {
				oldChars = new char[0][0];
			}

			chars = new char[rows][columns];
			for (int i = 0; i < minRows; i++) {
				char[] newRow = new char[columns];
				System.arraycopy(oldChars[i], 0, newRow, 0, minColumns);
				if (columns > minColumns) {
					Arrays.fill(newRow, minColumns, columns, ' ');
				}

				chars[i] = newRow;
			}

			for (int i = oldChars.length; i < rows; i++) {
				char[] newRow = new char[columns];
				Arrays.fill(newRow, ' ');
				chars[i] = newRow;
			}

			TerminalCharacterAttribute[][] oldAttributes = attributes;
			if (oldAttributes == null) {
				oldAttributes = new TerminalCharacterAttribute[0][0];
			}

			attributes = new TerminalCharacterAttribute[rows][columns];
			for (int i = 0; i < minRows; i++) {
				TerminalCharacterAttribute[] newRow = new TerminalCharacterAttribute[columns];
				System.arraycopy(oldAttributes[i], 0, newRow, 0, minColumns);
				attributes[i] = newRow;
			}

			for (int i = oldAttributes.length; i < rows; i++) {
				attributes[i] = new TerminalCharacterAttribute[columns];
			}
		}

		this.columns = columns;
		this.rows = rows;
		fireResized();
		valid = false; // avoid overhead of calling invalidate() in a tight loop
		repaint();
	}

	public synchronized int getRows() {
		return logicalRows;
	}

	public synchronized int getColumns() {
		return logicalColumns;
	}

	public synchronized Dimension getMinimumSize() {
		int parentHeight = getParent().getHeight();
		Insets parentInsets = getParent().getInsets();
		parentHeight -= parentInsets.top + parentInsets.bottom;
		return new Dimension(logicalColumns * getCharWidth(), Math.max(parentHeight, rows * getCharHeight()));
	}

	public synchronized Dimension getPreferredSize() {
		return getMinimumSize();
	}

	public synchronized Dimension getMaximumSize() {
		return getPreferredSize();
	}

	public synchronized int getCursorX() {
		return cursorX;
	}

	public synchronized int getCursorY() {
		return cursorY + logicalRows - rows;
	}

	public synchronized void setCursorPosition(int cursorX, int cursorY) {
		cursorY += rows - logicalRows;
		repaintChar(this.cursorX, this.cursorY);
		this.cursorX = cursorX;
		this.cursorY = cursorY;
		cursorState = true;
		cursorTimer.restart();
		repaintChar(cursorX, cursorY);
	}

	public int getCharWidth() {
		return charWidth;
	}

	public int getCharHeight() {
		return charHeight;
	}

	public CursorType getCursorType() {
		return cursorType;
	}

	public void setCursorType(CursorType cursorType) {
		this.cursorType = cursorType;
	}

	public void addTextWindowListener(TextWindowListener l) {
		listenerList.add(TextWindowListener.class, l);
	}

	public void removeTextWindowListener(TextWindowListener l) {
		listenerList.remove(TextWindowListener.class, l);
	}

	protected void fireResized() {
		TextWindowEvent e = new TextWindowEvent(this, TextWindowEvent.RESIZED);
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TextWindowListener.class) {
				TextWindowListener listener = ((TextWindowListener) listeners[i + 1]);
				listener.textWindowResized(e);
			}
		}
	}

	public void addKeyListener(KeyListener l) {
		listenerList.add(KeyListener.class, l);
	}

	public void removeKeyListener(KeyListener l) {
		listenerList.remove(KeyListener.class, l);
	}

	protected void processKeyEvent(KeyEvent e) {
		fireKeyEvent(e);
		if (e.getID() == KeyEvent.KEY_TYPED) {
			scrollRectToVisible(new Rectangle(0, cursorY * getCharHeight(), 1, (cursorY + 1) * getCharHeight() - 1));
		}
	}

	private void fireKeyEvent(KeyEvent e) {
		super.processKeyEvent(e);
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == KeyListener.class) {
				KeyListener listener = ((KeyListener) listeners[i + 1]);

				switch (e.getID()) {
				case KeyEvent.KEY_PRESSED:
					listener.keyPressed(e);
					break;

				case KeyEvent.KEY_RELEASED:
					listener.keyReleased(e);
					break;

				case KeyEvent.KEY_TYPED:
					listener.keyTyped(e);
					break;
				}
			}
		}
	}

	public void addTextMouseListener(TextMouseListener l) {
		listenerList.add(TextMouseListener.class, l);
	}

	public void removeTextMouseListener(TextMouseListener l) {
		listenerList.remove(TextMouseListener.class, l);
	}

	public void addTextMouseMotionListener(TextMouseMotionListener l) {
		enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
		listenerList.add(TextMouseMotionListener.class, l);
	}

	public void removeTextMouseMotionListener(TextMouseMotionListener l) {
		listenerList.remove(TextMouseMotionListener.class, l);
	}

	protected void processMouseEvent(MouseEvent e) {
		if (e.getID() == MouseEvent.MOUSE_PRESSED) {
			requestFocus();
		}

		fireMouseEvent(e);
	}

	protected void processMouseMotionEvent(MouseEvent e) {
		super.processMouseMotionEvent(e);
		fireMouseMotionEvent(e);
	}

	private TextMouseEvent createTextMouseEvent(MouseEvent event) {
		int bias = event.getX() % getCharWidth() < (getCharWidth() / 2) ? TextMouseEvent.BIAS_LEFT : TextMouseEvent.BIAS_RIGHT;
		return new TextMouseEvent((Component) event.getSource(), event.getID(), event.getWhen(), event.getModifiers(), event.getX() / getCharWidth(),
				event.getY() / getCharHeight() - rows + logicalRows, event.getClickCount(), event.isPopupTrigger(), bias);
	}

	private void fireMouseEvent(MouseEvent event) {
		TextMouseEvent textMouseEvent = null;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TextMouseListener.class) {
				if (textMouseEvent == null) {
					textMouseEvent = createTextMouseEvent(event);
				}

				TextMouseListener listener = ((TextMouseListener) listeners[i + 1]);
				switch (event.getID()) {
				case MouseEvent.MOUSE_PRESSED:
					listener.mousePressed(textMouseEvent);
					break;
				case MouseEvent.MOUSE_RELEASED:
					listener.mouseReleased(textMouseEvent);
					break;
				case MouseEvent.MOUSE_CLICKED:
					listener.mouseClicked(textMouseEvent);
					break;
				}
			}
		}
	}

	private void fireMouseMotionEvent(MouseEvent event) {
		TextMouseEvent textMouseEvent = null;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TextMouseMotionListener.class) {
				if (textMouseEvent == null) {
					textMouseEvent = createTextMouseEvent(event);
				}

				TextMouseMotionListener listener = ((TextMouseMotionListener) listeners[i + 1]);
				switch (event.getID()) {
				case MouseEvent.MOUSE_MOVED:
					listener.mouseMoved(textMouseEvent);
					break;

				case MouseEvent.MOUSE_DRAGGED:
					listener.mouseDragged(textMouseEvent);
					break;
				}
			}
		}
	}

	protected void processFocusEvent(FocusEvent event) {
		super.processFocusEvent(event);
		cursorTimer.restart();
		cursorState = event.getID() == FocusEvent.FOCUS_GAINED;
		repaintChar(cursorX, cursorY);
	}

	protected boolean processKeyBinding(KeyStroke keyStroke, KeyEvent event, int condition, boolean pressed) {
		// for up and down arrow, we need to prevent our scrollpane from
		// handling them so that
		// they can be used for command recall. for page up and page down, it
		// would usually
		// be okay for the scrollpane to handle them, but we need to deliver
		// them so that
		// whatever program is running gets a chance to interpret them.
		if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyCode() == KeyEvent.VK_PAGE_UP
				|| event.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
			return true;
		}

		return super.processKeyBinding(keyStroke, event, condition, pressed);
	}

	/**
	 * Update the cursor appropriately for just having output character
	 * <code>chr</code>, <code>\n</code> is the only control character handled
	 * here, everything else should be done at a higher level.
	 * 
	 * @param chr
	 */
	protected final void advance(char chr) {
		cursorState = true;
		cursorTimer.restart();
		switch (chr) {
		case '\n':
			cursorX = 0;
			if (++cursorY >= rows)
				scrollByOneRow();
			break;
		case '\r':
			break;
		default:
			if (++cursorX >= logicalColumns) {
				cursorX = 0;
				if (++cursorY >= rows)
					scrollByOneRow();
			}
		}
		repaintChar(cursorX, cursorY);
	}

	private void outputRaw(char c, TerminalCharacterAttribute attr, boolean immediate) {
		// assert Thread.holdsLock(this);
		if (attr == null) {
			throw new NullPointerException("attributes may not be null");
		}

		if (c == '\t') {
			outputRaw(' ', attr, false);
			outputRaw(' ', attr, false);
			outputRaw(' ', attr, false);
			outputRaw(' ', attr, false);
		} else if (c == '\r') {
			; // do nothing
		} else if (c == '\n') {
			advance(c);
		} else {
			try {
				int startingCursorX = this.cursorX;
				int startingCursorY = this.cursorY;
				chars[startingCursorY][startingCursorX] = c;
				attributes[startingCursorY][startingCursorX] = attr;
				advance(c);

				if (!immediate) {
					repaintChar(startingCursorX, startingCursorY);
				} else {
					paintImmediately(startingCursorX * charWidth, startingCursorY * charHeight, charWidth, charHeight);

					paintImmediately(cursorX * charWidth, cursorY * charHeight, charWidth, charHeight);
				}
			} catch (ArrayIndexOutOfBoundsException e) { // can happen if thread
															// was terminated in
															// the middle of
															// output
				if (cursorX < 0) {
					cursorX = 0;
				} else if (cursorX >= logicalColumns) {
					cursorX = logicalColumns - 1;
				}

				if (cursorY < 0) {
					cursorY = 0;
				} else if (cursorY >= rows) {
					cursorY = rows - 1;
				}

				outputRaw(c, attr, false); // cursor should be in valid
											// location, try again
			}
		}
	}

	public synchronized void output(char c) {
		outputRaw(c, this.currentTextColor, false);
	}

	public synchronized void output(char c, TerminalCharacterAttribute attributes) {
		outputRaw(c, attributes, false);
	}

	public synchronized void output(int x, int y, char c) {
		output(x, y, c, this.currentTextColor);
	}

	public synchronized void output(int x, int y, char c, TerminalCharacterAttribute attributes) {
		chars[y][x] = c;
		this.attributes[y][x] = attributes;
		repaintChar(x, y);
	}

	public synchronized void output(char[] c, int offset, int length) {
		output(c, offset, length, this.currentTextColor);
	}

	public synchronized void output(char[] c, int offset, int length, TerminalCharacterAttribute attributes) {
		for (int i = offset; i < length; i++)
			output(c[i], attributes);
	}

	public synchronized void output(String s) {
		output(s, this.currentTextColor);
	}

	public synchronized void output(String s, TerminalCharacterAttribute attributes) {
		int length = s.length();
		for (int i = 0; i < length; i++)
			output(s.charAt(i), attributes);
	}

	public synchronized void outputImmediately(char c) {
		outputRaw(c, this.currentTextColor, true);
	}

	public synchronized void outputImmediately(char c, TerminalCharacterAttribute attributes) {
		outputRaw(c, attributes, true);
	}

	// hasn't been tested except with last row, may not work in other cases
	public synchronized void insertRow(int row) {
		// don't use copyArea to do the scroll -- copyArea
		// would have to run for each line scrolled, while
		// this approach doesn't update anything until the
		// next paint. This is at least an order of
		// magnitude faster in general usage.

		char[] spareCharRow;
		TerminalCharacterAttribute[] spareAttributeRow;

		if (rows < scrollback) {
			spareCharRow = new char[columns];
			spareAttributeRow = new TerminalCharacterAttribute[columns];
			if (row == rows - 1)
				row++;
			setGridSize(columns, rows + 1, logicalColumns);
			System.arraycopy(chars, row, chars, row + 1, rows - row - 1);
			System.arraycopy(attributes, row, attributes, row + 1, rows - row - 1);
		} else {
			spareCharRow = chars[0];
			if (row > 0) {
				System.arraycopy(chars, 1, chars, 0, row);
			}

			spareAttributeRow = attributes[0];
			if (row > 0) {
				System.arraycopy(attributes, 1, attributes, 0, row);
			}
		}

		Arrays.fill(spareCharRow, ' ');
		chars[row] = spareCharRow;

		Arrays.fill(spareAttributeRow, this.currentTextColor);
		attributes[row] = spareAttributeRow;

		repaint();
	}

	private synchronized void scrollByOneRow() {
		int oldRows = rows;
		snapToBottom = true;
		insertRow(rows - 1);

		if (rows == oldRows) {
			cursorY--; // otherwise, grid just got bigger & no need to move
						// cursor
		}
	}

	protected void paintRun(Graphics g, int row, int start, int end, TerminalCharacterAttribute attributes) {
		if (attributes == null) {
			attributes = this.currentTextColor;
		}

		int startX = start * charWidth;
		int startY = row * charHeight;
		Color background = attributes.getBackground();
		if (!background.equals(this.currentTextColor.getBackground())) {
			g.setColor(background);
			g.fillRect(startX, startY, (end - start) * charWidth, charHeight);
		}
		g.setColor(attributes.getForeground());
		g.drawChars(chars[row], start, end - start, startX, startY + baseline);
	}

	public synchronized void repaint() {
		dirtyRegion = new Rectangle(0, 0, getWidth(), getHeight());

		if (!repaintTimer.isRunning()) {
			repaintTimer.start();
		}
	}

	/**
	 * Hide the cursor immediately so that we do not have any left-over of any
	 * cursor painted on screen.
	 * 
	 */
	public synchronized void hideCursor() {
		this.setCursorType(CursorType.CURSOR_INVISIBLE);
		this.repaintChar(this.getCursorX(), this.getCursorY());
	}

	public synchronized void repaintChar(int x, int y) {
		if (dirtyRegion == null) {
			dirtyRegion = new Rectangle(x, y, 1, 1);
		} else {
			SwingUtilities.computeUnion(x, y, 1, 1, dirtyRegion);
		}

		if (!repaintTimer.isRunning()) {
			repaintTimer.start();
		}
	}

	public void paint(Graphics graphics) {
		int startRow = 0;
		int endRow = rows;
		int startColumn = 0;
		int endColumn = columns;

		Rectangle clip = graphics.getClipBounds();
		if (clip == null) {
			computeVisibleRect(visibleRect);
			clip = visibleRect;
		}

		graphics.setColor(this.currentTextColor.getBackground());
		graphics.fillRect(clip.x, clip.y, clip.width, clip.height);

		startRow = clip.y / charHeight;
		endRow = Math.min(rows, startRow + (clip.height + charHeight - 1) / charHeight + 1);
		startColumn = clip.x / charWidth;
		endColumn = Math.min(columns, startColumn + (clip.width + charWidth - 1) / charWidth + 1);

		for (int i = startRow; i < endRow; i++) {
			int start = startColumn;
			TerminalCharacterAttribute currentAttributes = this.currentTextColor;

			for (int j = startColumn; j < endColumn; j++) {
				if (currentAttributes != attributes[i][j]) {
					if (start != j) {
						paintRun(graphics, i, start, j, currentAttributes);
					}

					start = j;
					currentAttributes = attributes[i][j];
				}
			}

			paintRun(graphics, i, start, endColumn, currentAttributes);
		}

		paintCursor(graphics);
	}

	/**
	 * Return the {@link Color} for the current cursor.
	 * 
	 * @return the {@link Color} for the cursor
	 * 
	 */
	public Color getCursorColor() {
		return this.cursorColor;
	}

	/**
	 * Set the color of the cursor.
	 * 
	 * @param cursorColor
	 *            the {@link Color} for the cursor
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@link Color} supplied is <code>null</code>
	 */
	public void setCursorColor(Color cursorColor) {
		if (cursorColor == null) {
			throw new IllegalArgumentException("cursorColor may not be null");
		}

		this.cursorColor = cursorColor;
	}

	public void paintCursor(Graphics graphics) {
		if (cursorState && hasFocus()) {
			if (cursorType == null) {
				return;
			}

			switch (cursorType) {
			case CURSOR_OVERSTRIKE:
			case CURSOR_INSERT:
				int x = cursorX * getCharWidth();
				int y = cursorY * getCharHeight();
				graphics.setColor(getCursorColor());
				graphics.setXORMode(this.currentTextColor.getBackground());
				graphics.drawLine(x, y, x, y + getCharHeight() - 1);
				break;

			case CURSOR_INVISIBLE:
				break;

			default:
				throw new RuntimeException("Not a valid value for CursorType - consult the developer");
			}
		}
	}

	public synchronized void doLayout() {
		Rectangle rectangle = getVisibleRect();
		int columns = rectangle.width / getCharWidth();
		logicalRows = rectangle.height / getCharHeight();

		if (columns != logicalColumns || logicalRows > this.rows) {
			setGridSize(Math.max(columns, this.columns), Math.max(logicalRows, this.rows), columns);
		}

		if (cursorX == -1 || cursorY == -1) {
			setCursorPosition(0, 0);
			scrollRectToVisible(new Rectangle(0, cursorY * getCharHeight(), columns * getCharWidth(), logicalRows * getCharHeight()));
		}
	}

	/**
	 * Validate the container and all its sub-components.
	 * 
	 */
	public void validate() {
		valid = true;
		super.validate();
	}

	public Dimension getPreferredScrollableViewportSize() {
		return new Dimension(columns * getCharWidth(), logicalRows * getCharHeight());
	}

	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		// quick hack, need to implement properly
		return (logicalRows - 3) * getCharHeight();
	}

	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	public boolean getScrollableTracksViewportWidth() {
		return true;
	}

	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		// quick hack, need to implement properly
		return getCharHeight();
	}

	/**
	 * Move one page up in the screen.
	 * 
	 */
	public void pageUp() {
		Rectangle rect = getVisibleRect();
		rect.y -= getScrollableBlockIncrement(rect, JScrollBar.VERTICAL, -1);
		scrollRectToVisible(rect);
	}

	/**
	 * Move one page down in the screen.
	 * 
	 */
	public void pageDown() {
		Rectangle rect = getVisibleRect();
		rect.y += getScrollableBlockIncrement(rect, JScrollBar.VERTICAL, 1);
		scrollRectToVisible(rect);
	}

	/**
	 * Clear contents of the entire screen.
	 * 
	 */
	public synchronized void clearScreen() {
		// TODO: optimize this
		for (int i = 0; i < this.chars.length; i++) {
			Arrays.fill(this.chars[i], ' ');
			Arrays.fill(this.attributes[i], this.currentTextColor);
		}

		// move to top
		this.setCursorPosition(0, 0 - rows + logicalRows);
		this.snapToTop = true;

		// repaint to clear everything
		repaint();
	}

	/**
	 * Close this terminal instance
	 * 
	 */
	public void close() {
		this.repaintTimer.stop();
		this.cursorTimer.stop();
	}
	
	public TerminalCharacterAttribute getTextColor() {
		return this.currentTextColor;
	}

	/**
	 * Set the current text color.
	 * 
	 * @param foreground
	 * @param background
	 */
	public void setTextColor(Color foreground, Color background) {
		this.currentTextColor = new TerminalCharacterAttribute(foreground, background);
	}
	
}
