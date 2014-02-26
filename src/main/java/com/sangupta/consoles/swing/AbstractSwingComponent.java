package com.sangupta.consoles.swing;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.sangupta.consoles.core.InputKey;
import com.sangupta.consoles.core.KeyTrapHandler;
import com.sangupta.consoles.ui.SwingTerminalConstants;

/**
 * An abstract console class.
 * 
 * @author sangupta
 *
 */
public abstract class AbstractSwingComponent implements SwingTerminalConstants {

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
	 * Boolean value indicating if we are done initialising and creating the
	 * instance of this object.
	 */
	private boolean initialized = false;

	/**
	 * Holds the list of all shutdown hooks that have been added to this terminal.
	 * 
	 */
	protected List<Runnable> shutDownHooks;
	
	/**
	 * Store all key trap handlers here
	 */
	protected final ConcurrentMap<InputKey, List<KeyTrapHandler>> keyTrapHandlers;
	
	/**
	 * Indicates whether we have added any keytraps or not
	 */
	protected boolean hasKeyTraps = false;
	
	/**
	 * Number of rows that should be present on screen.
	 */
	private int numScreenRows;
	
	/**
	 * Number of rows that are can be held in buffer.
	 */
	private int numBufferRows;
	
	/**
	 * Number of columns that should be present on screen.
	 */
	private int numScreenColumns;
	
	/**
	 * Number of columns that can be held in buffer
	 */
	private int numBufferColumns;

	/**
	 * 
	 */
	public AbstractSwingComponent(int rows, int columns) {
		setupLookAndFeel();
		
		// create the frame
		this.hostFrame = new JFrame();

		// set the layout
		BorderLayout bl = new BorderLayout();
		this.hostFrame.setLayout(bl);

		this.keyTrapHandlers = new ConcurrentHashMap<InputKey, List<KeyTrapHandler>>();
		
		// validate number of rows and columns
		if(columns <= 0) {
			columns = DEFAULT_COLUMNS;
		}
		if(rows <= 0) {
			rows = DEFAULT_ROWS;
		}
		this.numScreenRows = rows;
		this.numScreenColumns = columns;
		this.numBufferColumns = SwingTerminalConstants.MAX_DEFAULT_COLUMNS;
		this.numBufferRows = SwingTerminalConstants.MAX_DEFAULT_ROWS;
				
		// create the children
		createChildren();
		
		// add the closing handler for the terminal
		this.hostFrame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				closeThisTerminal();
			}
			
		});
		
		// pack and show
		this.hostFrame.pack();
		
		// check for resizing
		this.hostFrame.setResizable(this.supportsResizing());
		
		// finally show up
		this.hostFrame.setVisible(true);
		
		// set that we are completely initialized
		this.initialized = true;
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
	 * Setup native look and feel
	 * 
	 */
	protected void setupLookAndFeel() {
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
	}
	
	/**
	 * Whether this console supports resizing or not.
	 * 
	 * @return
	 */
	public abstract boolean supportsResizing();
	
	/**
	 * Create the children for this console
	 */
	protected abstract void createChildren();
	
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
	
	/**
	 * Add a shutdown hook to this terminal instance.
	 * 
	 * @param runnable
	 */
	public void addShutdownHook(Runnable runnable) {
		if(this.closingTerminal) {
			throw new IllegalStateException("We are already closing this terminal");
		}
		
		if(this.shutDownHooks == null) {
			this.shutDownHooks = new ArrayList<Runnable>(); 
		}
		
		this.shutDownHooks.add(runnable);
	}
}
