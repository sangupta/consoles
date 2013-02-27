package com.sangupta.consoles.core;

public class InputKey {
	
	public char ch;
	
	public boolean altPressed;
	
	public boolean ctrlPressed;
	
	public InputKey() {
		
	}
	
	public InputKey(char ch, boolean altPressed, boolean ctrlPressed) {
		this.ch = ch;
		this.altPressed = altPressed;
		this.ctrlPressed = ctrlPressed;
	}

	public InputKey(char ch) {
		this.ch = ch;
		this.altPressed = false;
		this.ctrlPressed = false;
	}
}
