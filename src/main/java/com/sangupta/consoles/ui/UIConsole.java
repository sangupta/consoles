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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import com.sangupta.consoles.core.AbstractConsole;
import com.sangupta.consoles.core.ConsoleInputStream;
import com.sangupta.consoles.core.ConsoleOutputStream;
import com.sangupta.consoles.core.ConsoleWriter;
import com.sangupta.consoles.core.InputKey;
import com.sangupta.consoles.core.KeyTrapHandler;
import com.sangupta.consoles.swing.KeyboardHandler;
import com.sangupta.consoles.swing.SwingTerminalConstants;

/**
 * A Java Swing based UI terminal that mimics that actual OS terminals.
 * Supports ANSI based colors and key sequences.
 * 
 * @author sangupta
 *
 */
public class UIConsole extends AbstractConsole {
	
	/**
	 * A {@link Writer} stream that can be used to output to the
	 * console.
	 */
	protected final ConsoleWriter consoleWriter;
	
	/**
	 * An {@link InputStream} implementation that can be used to read
	 * from the console.
	 */
	protected final ConsoleInputStream consoleInputStream;

	/**
	 * An {@link OutputStream} implementation that can be used to write
	 * to the console.
	 * 
	 */
	protected final ConsoleOutputStream consoleOutputStream;
	
	/**
	 * The terminal instance being used
	 * 
	 */
	protected final UITerminal terminal;
	
	/**
	 * The wrapper JFrame to be used
	 * 
	 */
	protected final JFrame frame;
	
	/**
	 * The scrolling pane to be used
	 * 
	 */
	protected final JScrollPane scrollPane;
	
	/**
	 * Bind keyboard related functionality to terminal
	 */
	protected final KeyboardHandler keyboardHandler;
	
	/**
	 * Holds the list of all shutdown hooks that have been added to this terminal.
	 * 
	 */
	protected List<Runnable> shutDownHooks;
	
	/**
	 * Are we closing this terminal?
	 */
	protected volatile boolean closingTerminal = false;
	
	/**
	 * Default console to be constructed
	 * 
	 */
	public UIConsole() {
		this(SwingTerminalConstants.DEFAULT_ROWS, SwingTerminalConstants.DEFAULT_COLUMNS, SwingTerminalConstants.MAX_DEFAULT_ROWS);
	}
	
	/**
	 * Construct the console
	 * 
	 * @param rows
	 * @param columns
	 * @param scrollback
	 */
	public UIConsole(int rows, int columns, int scrollback) {
		// set system look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
				
		this.terminal = new UITerminal(columns, rows, scrollback);
		this.keyboardHandler = new KeyboardHandler(this.terminal);
		
		this.frame = new JFrame();

		// TODO: we may need to change this depending on how
		// we run the shutdown hooks
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.scrollPane = new JScrollPane(this.terminal);
		this.frame.getContentPane().add(scrollPane);
		
		// set up streams to work with
		this.consoleWriter = new ConsoleWriter(this);
		this.consoleInputStream = new ConsoleInputStream(this);
		this.consoleOutputStream = new ConsoleOutputStream(this);
		
		// show frame now
		showFrame();
	}
	
	/**
	 * Function that actually shows the frame after all
	 * components have been set up.
	 * 
	 */
	private void showFrame() {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.pack(); // force peer to be created so that insets are known
                frame.setSize(frame.getPreferredSize());
                frame.setVisible(true);
                
                // workaround for 1.3.1: requestFocus not working unless run in another invokeLater
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        terminal.requestFocus();
                    }
                });
            }
        });
	}

	@Override
	public void clearScreen() {
		this.terminal.clearScreen();
	}

	@Override
	public void print(char ch) {
		this.terminal.output(ch);
	}

	@Override
	public void print(char[] cbuf, int off, int len) {
		this.terminal.output(cbuf, off, len);
	}

	@Override
	public void print(String string) {
		this.terminal.output(string);
	}

	@Override
	public void println(String string) {
		this.terminal.output(string);
		this.terminal.output('\n');
	}

	@Override
	public char readChar() {
		return this.keyboardHandler.readChar();
	}

	@Override
	public String readLine() {
		return this.keyboardHandler.readLine();
	}

	@Override
	public char[] readPassword() {
		return this.keyboardHandler.readPassword();
	}

	@Override
	public char[] readPassword(char mask) {
		return this.keyboardHandler.readPassword(mask);
	}

	@Override
	public void setWindowTitle(String title) {
		this.frame.setTitle(title);
	}

	/**
	 * @see AbstractConsole#getWriter()
	 */
	@Override
	public Writer getWriter() {
		return this.consoleWriter;
	}

	/**
	 * @see AbstractConsole#getInputStream()
	 */
	@Override
	public InputStream getInputStream() {
		return this.consoleInputStream;
	}
	
	/**
	 * @see AbstractConsole#getOutputStream()
	 */
	@Override
	public OutputStream getOutputStream() {
		return this.consoleOutputStream;
	}

	/**
	 * @see AbstractConsole#flush()
	 */
	@Override
	public void flush() throws IOException {
		this.consoleWriter.flush();
	}

	@Override
	public void addShutdownHook(Runnable runnable) {
		if(this.shutDownHooks == null) {
			synchronized (this) {
				if(this.shutDownHooks == null) {
					this.shutDownHooks = new ArrayList<Runnable>();
				}
			}
		}
		
		this.shutDownHooks.add(runnable);
	}

	@Override
	public void addKeyTrap(InputKey inputKey, KeyTrapHandler keyTrapHandler) {
	}

	@Override
	public boolean supportsResizing() {
		return true;
	}

	@Override
	public void setResizingEnabled(boolean enabled) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void shutdownConsole() {
		if(this.closingTerminal) {
			// already closed
			return;
		}
		
		// start closing
		this.keyboardHandler.shutDown();
		this.closingTerminal = true;

		// call shutdown hooks
		if(this.shutDownHooks != null && !this.shutDownHooks.isEmpty()) {
			for(Runnable hook : this.shutDownHooks) {
				hook.run();
			}
			
			this.shutDownHooks.clear();
		}
		
		// clean up objects
		// TODO: work this up
//		this.terminal.dispose();
		this.terminal.close();
		this.frame.setVisible(false);
		this.frame.dispose();
	}
	
	@Override
	public Color getForegroundColor() {
		return this.terminal.getTextColor().getForeground();
	}
	
	public Color getBackgroundColor() {
		return this.terminal.getTextColor().getBackground();
	}
	
	@Override
	public void setColor(Color foreground, Color background) {
		this.terminal.setTextColor(foreground, background);
	}

}
