package org.mql.autocompletionplugin;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPopupMenu;

public class JTextPaneKeyListener implements KeyListener {
	private JPopupMenu popupMenu;
	private int selected;

	public JTextPaneKeyListener(JPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
		selected = 0;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE && popupMenu.isVisible()) {
			popupMenu.setVisible(false);
		}
		// if popup is visible that mean there is items
		if (e.getKeyCode() == KeyEvent.VK_UP && popupMenu.isVisible() && selected > 0 ) {
			selected--;
			e.consume(); // Prevent default tab behavior
			// KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
			System.out.println("up pressed");
			popupMenu.getSelectionModel().setSelectedIndex(selected);

		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN && popupMenu.isVisible() && selected < popupMenu.getComponentCount() - 1 ) {
			selected++;
			e.consume(); // Prevent default tab behavior
			// KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
			System.out.println("down pressed");
			popupMenu.getSelectionModel().setSelectedIndex(selected);

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
