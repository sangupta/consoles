package com.sangupta.consoles.core;

import java.awt.Color;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.sangupta.consoles.IConsole;
import com.sangupta.jerry.util.AssertUtils;

/**
 * An abstract implementation of {@link IConsole} from which definite
 * console implementations can derive to provide specific functionality.
 * Includes generic common functionality like providing a unique console
 * id, working with console streams, console properties etc.
 * 
 * @author sangupta
 *
 */
public abstract class AbstractConsole implements IConsole {
	
	/**
	 * Generate a unique console id for every instance
	 */
	private static final String CONSOLE_ID = UUID.randomUUID().toString(); 
	
	/**
	 * Internal reference to the input stream - before Husk will replace it.
	 */
	protected InputStream originalInputStream;
	
	/**
	 * Internal reference to the output stream - before Husk will replace it.
	 */
	protected PrintStream originalOutStream;
	
	/**
	 * Internal reference to the error stream - before Husk will replace it.
	 */
	protected PrintStream originalErrorStream;
	
	/**
	 * Stores console specific properties
	 */
	protected final Map<String, String> consoleProperties = new HashMap<String, String>();
	
	/**
	 * Specifies if the original streams have been backed up or not.
	 * 
	 */
	private boolean backedUp;
	
	@Override
	public final String getConsoleID() {
		return CONSOLE_ID;
	}

	/**
	 * Backup original streams into the current set.
	 * 
	 */
	protected void backUpStreams() {
		if(backedUp) {
			return;
		}
		
		// store original references
		this.originalInputStream = System.in;
		this.originalOutStream = System.out;
		this.originalErrorStream = System.err;
		
		this.backedUp = true;
	}

	@Override
	public void switchStreams() {
		switchStreams(true, true, true);
	}
	
	@Override
	public void switchStreams(boolean inStream, boolean outStream, boolean errorStream) {
		backUpStreams();
		
		// setup Husk references
		if(inStream) {
			System.setIn(this.getInputStream());
		}
		
		if(outStream) {
			System.setOut(new PrintStream(this.getOutputStream()));
		}
		
		if(errorStream) {
			System.setErr(System.out);
		}
	}
	
	@Override
	public void restoreStreams() {
		restoreStreams(true, true, true);
	}
	
	@Override
	public void restoreStreams(boolean inStream, boolean outStream,	boolean errorStream) {
		// reset the original streams
		
		if(inStream) {
			System.setIn(this.originalInputStream);
		}
		
		if(outStream) {
			System.setOut(this.originalOutStream);
		}
		
		if(errorStream) {
			System.setErr(this.originalErrorStream);
		}
	}
	
	@Override
	public final void shutdown() {
		shutdownConsole();
		
		restoreStreams();
	}
	
	protected abstract void shutdownConsole();
	
	public Color getForegroundColor() {
		return null;
	}
	
	public Color getBackgroundColor() {
		return null;
	}
	
	@Override
	public void setColor(Color foreground, Color background) {
		throw new RuntimeException("not yet implemented");
	}

	/**
	 * Retrieve a console specific property. This can be used to pass on
	 * messages between various unrelated independent code pieces.
	 * 
	 * @param name
	 *            the property name
	 * 
	 * @return the value of the property if found, <code>null</code> otherwise
	 * 
	 * @throws IllegalArgumentException
	 *             if name is empty/<code>null</code>
	 */
	public String getConsoleProperty(String name) {
		if(AssertUtils.isEmpty(name)) {
			throw new IllegalArgumentException("Property name cannot be null/empty");
		}
		
		return this.consoleProperties.get(name);
	}
	
	/**
	 * Set a console specific property. This can be used to pass on messages
	 * between various unrelated independent code pieces.
	 * 
	 * @param name
	 *            the property name
	 * 
	 * @param value
	 *            the property value
	 * 
	 * @throws IllegalArgumentException
	 *             if name is empty/<code>null</code>
	 */
	public void setConsoleProperty(String name, String value) {
		if(AssertUtils.isEmpty(name)) {
			throw new IllegalArgumentException("Property name cannot be null/empty");
		}
		
		this.consoleProperties.put(name, value);
	}
	
}
