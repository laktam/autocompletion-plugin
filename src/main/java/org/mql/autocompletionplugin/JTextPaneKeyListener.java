package org.mql.autocompletionplugin;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPopupMenu;

public class JTextPaneKeyListener implements KeyListener {
	private JPopupMenu popupMenu;

	public JTextPaneKeyListener(JPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE && popupMenu.isVisible()) {
			popupMenu.setVisible(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_TAB) {
			e.consume(); // Prevent default tab behavior
			if (popupMenu.isVisible()) {
//				KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
				System.out.println("shift pressed");
				if (popupMenu.isVisible()) {
					System.out.println("request focus for popupmenu : " + popupMenu.requestFocusInWindow());
					;
				}

			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
