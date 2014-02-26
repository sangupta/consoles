package com.sangupta.consoles.ui;

import com.sangupta.consoles.swing.AbstractSwingComponent;

public class UIConsoleComponent extends AbstractSwingComponent {
	
	public UIConsoleComponent() {
		super(SwingTerminalConstants.DEFAULT_ROWS, SwingTerminalConstants.DEFAULT_COLUMNS);
	}

	@Override
	public boolean supportsResizing() {
		return true;
	}

	@Override
	protected void createChildren() {
		SwingTerminal terminal = new SwingTerminal();
	}

	@Override
	protected void closeTerminal() {
		
	}

}
