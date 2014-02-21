package com.sangupta.consoles.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;

/**
 * An abstract console class.
 * 
 * @author sangupta
 *
 */
public abstract class AbstractSwingConsole {

	/**
	 * Number of default columns in a terminal
	 */
	public static final int DEFAULT_COLUMNS = 80;

	/**
	 * Number of default rows in a terminal
	 */
	public static final int DEFAULT_ROWS = 25;
	
	/**
	 * The host frame
	 */
	protected JFrame hostFrame;
	
	/**
	 * Number of columns
	 */
	protected int numColumns;
	
	/**
	 * Number of rows
	 */
	protected int numRows;
	
	/**
	 * Signals the keyboard input thread to break immediately as 
	 * we are closing down.
	 */
	private boolean closingTerminal = false;
	
	/**
	 * Holds the list of all shutdown hooks that have been added to this terminal.
	 * 
	 */
	protected List<Runnable> shutDownHooks;
	
	/**
	 * 
	 */
	public AbstractSwingConsole() {
		this.hostFrame = new JFrame();
		
		// add the closing handler for the terminal
		this.hostFrame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				closeThisTerminal();
			}
			
		});
	}
	
	/**
	 * Close this very terminal.
	 * 
	 */
	private void closeThisTerminal() {
		if(this.closingTerminal) {
			// already closed
			return;
		}
		
		// start closing
		this.closingTerminal = true;

		// run code for direct implementations
		try {
			closeTerminal();
		} catch(Throwable t) {
			// eat up
		}
		
		// call shutdown hooks
		if(this.shutDownHooks != null && !this.shutDownHooks.isEmpty()) {
			for(Runnable hook : this.shutDownHooks) {
				hook.run();
			}
			
			this.shutDownHooks.clear();
		}
		
		// clean up objects
		this.hostFrame.setVisible(false);
		this.hostFrame.dispose();
	}
	
	/**
	 * Run code to close this terminal
	 */
	protected abstract void closeTerminal();

	/**
	 * Set the title of the window being used.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.hostFrame.setTitle(title);
	}
	
}
