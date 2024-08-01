package org.mql.autocompletionplugin;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPopupMenu;

public class JPopMenuKeyListener implements KeyListener {
	private JPopupMenu popupMenu;
	private int selected;

	public JPopMenuKeyListener(JPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
		selected = -1;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			System.out.println("shift pressed");
//			if(selected == -1) {
			selected++;
			popupMenu.getSelectionModel().setSelectedIndex(selected);
//			}

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
