package com.sangupta.consoles.core;

import java.io.IOException;
import java.io.Writer;

import com.sangupta.consoles.IConsole;

public class ConsoleWriter extends Writer {
	
	private final IConsole console;
	
	private boolean closed;
	
	public ConsoleWriter(IConsole console) {
		this.console = console;
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		if(this.closed) {
			return;
		}
		
		this.console.print(cbuf, off, len);
	}

	@Override
	public void flush() throws IOException {
		this.console.flush();
	}

	@Override
	public void close() throws IOException {
		this.closed = true;
	}

}
