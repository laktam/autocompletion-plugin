package org.mql.autocompletionplugin;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPopupMenu;

public class JPopupMenuKeyListener implements KeyListener {
	private int selected;
	private JPopupMenu popupMenu;

	public JPopupMenuKeyListener(JPopupMenu popupMenu) {
		selected = -1;
		this.popupMenu = popupMenu;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
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

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void reset() {
		selected = 0;
	}

}
