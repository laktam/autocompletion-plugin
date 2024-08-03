package org.mql.autocompletionplugin;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPopupMenu;

public class JPopupMenuKeyListener implements KeyListener {
	private int selected;
	private JPopupMenu popupMenu;

	public JPopupMenuKeyListener(JPopupMenu popupMenu) {
		selected = 0;
		this.popupMenu = popupMenu;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (popupMenu.isVisible()) {
				System.out.println("up pressed");

				if (popupMenu.isVisible()) {
					selected++;
					popupMenu.getSelectionModel().setSelectedIndex(selected);
					popupMenu.getComponent(selected).requestFocus();
					
				}
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			System.out.println("down pressed");
			if (popupMenu.isVisible()) {
				selected--;
				popupMenu.getSelectionModel().setSelectedIndex(selected);
				popupMenu.getComponent(selected).requestFocus();
			}
		}
	}

	public void reset() {
		selected = 0;
	}

}
