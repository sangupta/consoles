package com.sangupta.consoles.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.sangupta.consoles.swing.AbstractSwingComponent;
import com.sangupta.consoles.ui.SwingTerminalConstants;


/**
 * This class adds much more functionality to the swing console
 * by providing a menu bar, a toolbar and a provision for tabbed
 * interface.
 * 
 * @author sangupta
 *
 */
public class GUIConsoleComponent extends AbstractSwingComponent {
	
	public GUIConsoleComponent() {
		super(SwingTerminalConstants.DEFAULT_ROWS, SwingTerminalConstants.DEFAULT_COLUMNS);
	}

	@Override
	public boolean supportsResizing() {
		return true;
	}

	@Override
	protected void createChildren() {
		final JMenuBar mb = new JMenuBar();
        
		final JMenu m = new JMenu("File");
        mb.add(m);
        
        final JMenu dm = new JMenu("Debug");
        mb.add(dm);
        
        this.hostFrame.setJMenuBar(mb);
	}

	@Override
	protected void closeTerminal() {
		
	}
	
}
