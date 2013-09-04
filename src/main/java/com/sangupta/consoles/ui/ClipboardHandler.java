package com.sangupta.consoles.ui;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import com.sangupta.consoles.ConsolesConstants;
import com.sangupta.consoles.core.InputKey;

/**
 * Handles tasks with clipboard like copy and paste.
 * 
 * @author sangupta
 *
 */
public class ClipboardHandler {
	
	private SwingTerminal terminal;

	public ClipboardHandler(SwingTerminal terminal) {
		this.terminal = terminal;
	}

	public void processPasteAction() {
		String data = null;
		try {
			data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (HeadlessException e) {
			// eat up
		} catch (UnsupportedFlavorException e) {
			// eat up
		} catch (IOException e) {
			// eat up
		} 
		
		if(data == null) {
			return;
		}
		
		// paste it at current location
		char[] chars = data.toCharArray();
		for(char ch : chars) {
			this.terminal.inputKeys.add(new InputKey(ch));
		}
	}

	/**
	 * Read the text inside the given bounding box.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public void copyTextToClipboard(int x1, int y1, int x2, int y2) {
		StringBuilder builder = new StringBuilder();
		
		for(int row = y1; row <= y2; row++) {
			for(int col = x1; col <= x2; col++) {
				builder.append(this.terminal.screenView[row][col].character);
			}
			
			if(y1 != y2) {
				builder.append(ConsolesConstants.NEW_LINE);
			}
		}
		
		String text = builder.toString();
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
	}

}
