package org.mql.autocompletionplugin;

import java.awt.Color;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

public class JTextPaneKeyListener implements KeyListener {
	private JPopupMenu popupMenu;
	private int selected;
	private Document document;
	private int offset;
	private String prefix;

	public JTextPaneKeyListener(JPopupMenu popupMenu, Document document) {
		this.prefix = "";
		this.offset = 0;
		this.document = document;
		this.popupMenu = popupMenu;
		selected = 0;
		this.popupMenu.addPopupMenuListener(new PopupMenuListener() {
             @Override
             public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            	 System.out.println("pop up menu visible ################");
            	 selected = 0;
            	// selected the first element
     			((JMenuItem) popupMenu.getComponent(0)).setOpaque(true);
     			((JMenuItem) popupMenu.getComponent(0)).setBackground(new Color(192, 221, 251));
             }

             @Override
             public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
             }

             @Override
             public void popupMenuCanceled(PopupMenuEvent e) {
             }
         });
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
		if (popupMenu.isVisible() ) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				e.consume(); // Prevent default tab behaviors
				if (selected > 0) {
					selected--;
					System.out.println("up pressed");
					selectItem(selected);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				e.consume(); // Prevent default tab behaviors
				if (selected < popupMenu.getComponentCount() - 1) {
					selected++;
					System.out.println("down pressed");
					selectItem(selected);
				}
			}else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				e.consume();
				String suggestion = ((JMenuItem) popupMenu.getComponent(selected)).getText();
				try {
					// insert only the part that is not already written
					suggestion =  suggestion.substring(prefix.length());
					document.insertString(offset + 1, suggestion, null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	private void selectItem(int selected) {
//		SwingUtilities.invokeLater(() -> {
			popupMenu.getSelectionModel().setSelectedIndex(selected);
			System.out.println("selected : "
					+ ((JMenuItem) popupMenu.getComponent(selected)).getText());
			int itemCount = popupMenu.getComponentCount();
			for (int item = 0; item < itemCount; item++) {
				JMenuItem menuItem = (JMenuItem) popupMenu.getComponent(item);
				if (item == selected) {
					menuItem.setOpaque(true);
					menuItem.setBackground(new Color(192, 221, 251));
				} else {
					menuItem.setOpaque(true);
					menuItem.setBackground(UIManager.getColor("MenuItem.background"));
				}
			}
			popupMenu.repaint();
//		});
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
