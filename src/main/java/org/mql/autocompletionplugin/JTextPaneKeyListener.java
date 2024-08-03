package org.mql.autocompletionplugin;

import java.awt.Color;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

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
		// if the popupmenu is visible dont use arrow mouvement in the doc
		// if popup is visible that mean there is items
		if (popupMenu.isVisible()) {
			e.consume(); // Prevent default tab behaviors
			if (e.getKeyCode() == KeyEvent.VK_UP && selected > 0) {
				selected--;
				System.out.println("up pressed");
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN && selected < popupMenu.getComponentCount() - 1) {
				selected++;
				e.consume();
				System.out.println("down pressed");
			}
			selectItem(selected);

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	private void selectItem(int selected) {
		popupMenu.getSelectionModel().setSelectedIndex(selected);
		System.out.println("selected : " + popupMenu.getComponent(popupMenu.getSelectionModel().getSelectedIndex()));
		int itemCount = popupMenu.getComponentCount();
		for(int item = 0; item < itemCount; item++) {
			JMenuItem menuItem = (JMenuItem) popupMenu.getComponent(item);
			if(item == selected) {
				menuItem.setBackground(Color.BLUE);
			}else {
				menuItem.setBackground(UIManager.getColor("MenuItem.background"));
			}
		}
		popupMenu.repaint();

	}
}
