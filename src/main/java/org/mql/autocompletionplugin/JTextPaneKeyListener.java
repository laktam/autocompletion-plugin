package org.mql.autocompletionplugin;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPopupMenu;

public class JTextPaneKeyListener implements KeyListener {
	private JPopupMenu popupMenu;
	private int selected;

	public JTextPaneKeyListener(JPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
		selected = -1;
	}

	@Override
	public void keyTyped(KeyEvent e) {
//		if (e.getKeyCode() == KeyEvent.SHIFT_DOWN_MASK) {
//			System.out.println("shift pressed");
////			if(selected == -1) {
//			selected++;
//			if (popupMenu.isVisible()) {
//				popupMenu.getSelectionModel().setSelectedIndex(selected);
//				popupMenu.requestFocusInWindow();
//				popupMenu.getComponent(selected).requestFocus();
//			}
////			}
//
//		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_TAB) {
			e.consume(); // Prevent default tab behavior
			if (popupMenu.isVisible()) {
				System.out.println("shift pressed");
				selected++;
				if (popupMenu.isVisible()) {
					popupMenu.getSelectionModel().setSelectedIndex(selected);
					popupMenu.requestFocusInWindow();
					popupMenu.getComponent(selected).requestFocus();
				}
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
