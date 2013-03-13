package com.sangupta.consoles.core;

import java.io.IOException;
import java.io.OutputStream;

import com.sangupta.consoles.IConsole;

public class ConsoleOutputStream extends OutputStream {
	
	private final IConsole console;

	public ConsoleOutputStream(IConsole console) {
		this.console = console;
	}

	@Override
	public void write(int b) throws IOException {
		this.console.print((char) b);
	}

}
