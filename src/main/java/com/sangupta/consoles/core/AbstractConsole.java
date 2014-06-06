package com.sangupta.consoles.core;

import java.awt.Color;
import java.io.InputStream;
import java.io.PrintStream;

import com.sangupta.consoles.IConsole;

public abstract class AbstractConsole implements IConsole {
	
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
	 * Specifies if the original streams have been backed up or not.
	 * 
	 */
	private boolean backedUp;

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

}
